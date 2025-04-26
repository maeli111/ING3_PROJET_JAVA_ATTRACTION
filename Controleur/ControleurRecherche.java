package Controleur;

import Vue.VueRecherche;
import Modele.Admin;
import Modele.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

        // Déterminer l'ordre SQL selon le filtre
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
                orderBy = "nom ASC"; // par défaut trié par nom
        }

        // Requête SQL : NE sélectionne QUE nom + prix
        String sql = "SELECT nom, prix FROM attraction ORDER BY " + orderBy;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Créer un modèle avec uniquement deux colonnes : Nom, Prix
            DefaultTableModel model = new DefaultTableModel(
                    new Object[]{"Nom", "Prix (€)"}, 0
            );

            // Remplir le tableau
            while (rs.next()) {
                String nom = rs.getString("nom");
                double prix = rs.getDouble("prix");

                model.addRow(new Object[]{nom, prix});
            }

            // Mettre à jour la JTable
            vue.mettreAJourTable(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(vue, "Erreur de connexion à la base de données : " + e.getMessage());
            e.printStackTrace();
        }
    }
}
