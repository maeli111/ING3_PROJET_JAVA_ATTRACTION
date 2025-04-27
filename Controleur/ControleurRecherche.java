package Controleur;

import Vue.*;
import DAO.*;
import Modele.*;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ControleurRecherche {
    private VueRecherche vue;

    // connexion avec la bdd
    private static final String URL = "jdbc:mysql://localhost:3306/java_attraction";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // constructeur
    public ControleurRecherche(VueRecherche vue, Client client, Admin admin) {
        this.vue = vue;

        vue.getRechercherBtn().addActionListener(e -> lancerRecherche(client, admin));
        vue.getLoupeBtn().addActionListener(e -> ouvrirRecherche(client, admin));
        vue.getAccueil().addActionListener(e -> retourAccueil(client, admin));
        vue.getInformations().addActionListener(e -> allerInformations(client, admin));
        vue.getCalendrier().addActionListener(e -> allerCalendrier(client, admin));
        vue.getCompte().addActionListener(e -> allerCompte(client, admin));

    }

    // lance la recherche selon le critère sélectionné
    private void lancerRecherche(Client client, Admin admin) {
        String filtre = (String) vue.getFiltreCombo().getSelectedItem();
        String sql = "";

        // si on sélectionne type
        if ("Type".equals(filtre)) {
            // on va chercher les différents types présents dans la bdd
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT DISTINCT type_attraction FROM attraction")) {

                java.util.List<String> types = new java.util.ArrayList<>();
                while (rs.next()) {
                    types.add(rs.getString("type_attraction"));
                }

                // s'il n'y a pas de types
                if (types.isEmpty()) {
                    JOptionPane.showMessageDialog(vue, "Aucun type trouvé !");
                    return;
                }

                // l'utilisateur choisi le type d'attractions qu'il veut
                String typeChoisi = (String) JOptionPane.showInputDialog(
                        vue,
                        "Choisissez un type d'attraction :",
                        "Type d'attraction",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        types.toArray(),
                        types.get(0)
                );

                if (typeChoisi == null) {
                    return; // l'utilisateur a annulé
                }

                // requête sql qui va filtrer les attractions par type
                sql = "SELECT id_attraction, nom, prix, type_attraction, capacite FROM attraction WHERE type_attraction = '" + typeChoisi + "'";

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(vue, "Erreur lors de la récupération des types : " + e.getMessage());
                e.printStackTrace();
                return;
            }

        }

        // si on sélectionne prix
        else if ("Prix".equals(filtre)) {
            try {
                // on demande à l'utilisateur de saisir un prix minimum et un maximum
                String minStr = JOptionPane.showInputDialog(vue, "Prix minimum (€) :", "0");
                if (minStr == null) return;
                double prixMin = Double.parseDouble(minStr);

                String maxStr = JOptionPane.showInputDialog(vue, "Prix maximum (€) :", "100");
                if (maxStr == null) return;
                double prixMax = Double.parseDouble(maxStr);

                // on vérifie que le prix minimum soit bien inférieur ou égal au prix max
                if (prixMin > prixMax) {
                    JOptionPane.showMessageDialog(vue, "Le prix minimum doit être inférieur ou égal au prix maximum !");
                    return;
                }

                // requête sql qui va filtrer les attractions dont le prix est dans la plage choisie par l'utilisateur
                sql = "SELECT id_attraction, nom, prix, type_attraction, capacite FROM attraction WHERE prix BETWEEN " + prixMin + " AND " + prixMax;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vue, "Veuillez entrer des nombres valides !");
                return;
            }

        }

        // si on sélectionne un autre critère
        else {
            String orderBy = "";

            // on trie les attraction selon le critère sélectionné
            switch (filtre) {
                case "Prix croissant":
                    orderBy = "prix ASC";
                    break;
                case "Prix décroissant":
                    orderBy = "prix DESC";
                    break;
                case "Capacité croissante":
                    orderBy = "capacite ASC";
                    break;
                case "Capacité décroissante":
                    orderBy = "capacite DESC";
                    break;
                default:
                    orderBy = "nom ASC";
            }

            // requête sql qui va les trier
            sql = "SELECT id_attraction, nom, prix, type_attraction, capacite FROM attraction ORDER BY " + orderBy;
        }

        // exécution de la requête SQL
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            vue.viderResultats(); // vide les anciens résultats

            DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");

            // on regarde tous les résultats et on crée un bouton pour chaque attraction
            while (rs.next()) {
                int id = rs.getInt("id_attraction");
                String nom = rs.getString("nom");
                double prix = rs.getDouble("prix");
                String type = rs.getString("type_attraction");
                int capacite = rs.getInt("capacite");

                // création du bouton avec les infos
                String texteBouton = nom + " — " + prix + "€ — " + type + " — Capacité: " + capacite;

                JButton attractionBtn = new JButton(texteBouton);

                // envoie l'utilisateur sur la page des infos de l'attraction s'il clique dessus
                attractionBtn.addActionListener(e -> {
                    AttractionDao attractionDao = new AttractionDao(daoFactory);
                    Attraction attraction = attractionDao.chercher(id);

                    if (attraction != null) {
                        VueAttraction vueAttraction = new VueAttraction(attraction, client, admin);
                        new ControleurAttraction(vueAttraction,attraction,client, admin);
                        vueAttraction.setVisible(true);
                        vue.dispose();
                    } else {
                        JOptionPane.showMessageDialog(vue, "Attraction introuvable !");
                    }
                });

                vue.ajouterResultat(attractionBtn);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vue, "Erreur de connexion à la base de données : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // méthodes pour aller dans les différentes pages de l'application

    private void ouvrirRecherche(Client client, Admin admin) {
        vue.setVisible(false);
        VueRecherche vueRecherche = new VueRecherche(client, admin);
        new ControleurRecherche(vueRecherche, client, admin); // Si vous avez un contrôleur dédié
        vueRecherche.setVisible(true);
    }

    private void retourAccueil(Client client, Admin admin) {
        vue.setVisible(false);
        VueAccueil vueAccueil = new VueAccueil(client, admin);
        new ControleurAccueil(vueAccueil, client, admin);
        vueAccueil.setVisible(true);
    }

    private void allerInformations(Client client, Admin admin) {
        vue.setVisible(false);
        VuePlusInfos vueInfos = new VuePlusInfos(client, admin);
        new ControleurPlusInfos(vueInfos, client, admin);
        vueInfos.setVisible(true);
    }

    private void allerCalendrier(Client client, Admin admin) {
        vue.setVisible(false);
        VueCalendrier vueCalendrier = new VueCalendrier(client, admin);
        new ControleurCalendrier(vueCalendrier, client, admin);
        vueCalendrier.setVisible(true);
    }

    private void allerCompte(Client client, Admin admin) {
        vue.setVisible(false);
        if (client == null && admin == null) {
            VueLogin login = new VueLogin();
            new ControleurLogin(login);
            login.setVisible(true);
        } else if (client != null) {
            VueClient vueClient = new VueClient(client);
            new ControleurClient(vueClient, client);
            vueClient.setVisible(true);
        } else {
            VueAdmin vueAdmin = new VueAdmin(admin);
            new ControleurAdmin(vueAdmin, admin);
            vueAdmin.setVisible(true);
        }
        vue.dispose();
    }


}