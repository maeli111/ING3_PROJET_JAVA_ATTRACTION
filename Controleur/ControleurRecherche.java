package Controleur;

import Vue.*;
import DAO.*;
import Modele.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ControleurRecherche {
    private VueRecherche vue;
    private Client client;
    private Admin admin;

    private static final String URL = "jdbc:mysql://localhost:3306/java_attraction";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public ControleurRecherche(VueRecherche vue, Client client, Admin admin) {
        this.vue = vue;
        this.client = client;
        this.admin = admin;

        vue.getRechercherBtn().addActionListener(e -> lancerRecherche());
    }

    private void lancerRecherche() {
        String filtre = (String) vue.getFiltreCombo().getSelectedItem();
        String orderBy = "";

        switch (filtre) {
            case "Prix croissant":
                orderBy = "prix ASC";
                break;
            case "Prix décroissant":
                orderBy = "prix DESC";
                break;
            case "Type":
                orderBy = "type_attraction ASC";
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

        String sql = "SELECT id_attraction, nom, prix, type_attraction, capacite FROM attraction ORDER BY " + orderBy;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            vue.viderResultats(); // <-- Avant de remplir, on vide les anciens résultats

            while (rs.next()) {
                int id = rs.getInt("id_attraction");
                String nom = rs.getString("nom");
                double prix = rs.getDouble("prix");
                String type = rs.getString("type_attraction");
                int capacite = rs.getInt("capacite");

                String texteBouton = nom + " — " + prix + "€ — " + type + " — Capacité: " + capacite;

                JButton attractionBtn = new JButton(texteBouton);

                DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");

                // Action quand on clique sur le bouton
                attractionBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AttractionDao attractionDao = new AttractionDao(daoFactory); // utilise ton daoFactory
                        Attraction attraction = attractionDao.chercher(id); // on utilise ton chercher(id)

                        if (attraction != null) {
                            VueAttraction vueAttraction = new VueAttraction(attraction);
                            vueAttraction.setVisible(true);
                            vue.dispose(); // optionnel : ferme la vue actuelle
                        } else {
                            JOptionPane.showMessageDialog(vue, "Attraction introuvable !");
                        }
                    }
                });


                vue.ajouterResultat(attractionBtn);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vue, "Erreur de connexion à la base de données : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
