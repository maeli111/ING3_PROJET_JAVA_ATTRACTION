package Controleur;

import Modele.*;
import Vue.*;
import DAO.*;

import javax.swing.*;
import java.util.ArrayList;

public class ControleurAdmin {
    private VueAdmin vue;
    private Admin admin;
    private DaoFactory daoFactory;
    private AttractionDao attractionDao;

    // Constructeur
    public ControleurAdmin(VueAdmin vue, Admin admin) {
        this.admin = admin;
        this.vue = vue;

        daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");
        attractionDao = new AttractionDao(daoFactory);

        ajouterListeners();
        vue.setVisible(true);
    }

    private void ajouterListeners() {

        // Déconnexion de l'admin et mène à la page Login
        vue.getDeconnexionButton().addActionListener(e -> {
            vue.dispose();
            VueLogin vueLogin = new VueLogin();
            new ControleurLogin(vueLogin);
            vueLogin.setVisible(true);
        });

        // mène à la page qui permet de modifier/ajouter/supprimer les attractions
        vue.getAttractionsButton().addActionListener(e -> {
            vue.dispose();
            VueAdminAttraction vueAdminAttraction = new VueAdminAttraction(admin);
            new ControleurAdminAttraction(vueAdminAttraction, admin);
            vueAdminAttraction.setVisible(true);
        });

        // mène à la page qui permet de modifier/ajouter/supprimer les réductions soit des client soit des attractions
        vue.getReductionsButton().addActionListener(e -> {
            String[] options = {"Réductions clients", "Réductions attractions"};
            int choix = JOptionPane.showOptionDialog(
                    null,
                    "Souhaitez-vous modifier les réductions pour les clients ou les attractions ?",
                    "Choix du type de réductions",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            vue.dispose();

            if (choix == 0) { // Si l'admin choisit Réductions clients
                VueAdminRC vueAdminRC = new VueAdminRC(admin);
                new ControleurAdminRC(vueAdminRC, admin);
                vueAdminRC.setVisible(true);
            } else if (choix == 1) { // Si l'admin choisit Réductions attractions
                VueAdminRA vueAdminRA = new VueAdminRA(admin);
                new ControleurAdminRA(vueAdminRA, admin);
                vueAdminRA.setVisible(true);
            }
        });

        //mène à la page qui permet de modifier/ajouter/supprimer des clients
        vue.getDossiersClientsButton().addActionListener(e -> {
            vue.dispose();
            VueAdminClient vueAdminClient = new VueAdminClient(admin);
            new ControleurAdminClient(vueAdminClient, admin);
            vueAdminClient.setVisible(true);
        });

        // Permet de modifier l'attraction du mois (affichée dans accueil)
        vue.getAttractionDuMoisButton().addActionListener(e -> {
            vue.getAttractionMoisPanel().setVisible(true);

            // On précise l'attraction actuelle du mois
            Attraction attractionActuelle = attractionDao.getAttractionMois();
            if (attractionActuelle != null) {
                vue.getAttractionActuelleLabel().setText(
                        "Attraction actuelle : " + attractionActuelle.getId_attraction() + " - " + attractionActuelle.getNom()
                );
            } else {
                vue.getAttractionActuelleLabel().setText("Aucune attraction du mois actuellement");
            }

            // On a le choix avec toutes les attractions dans un ComboBox
            vue.getAttractionComboBox().removeAllItems();
            ArrayList<Attraction> attractions = attractionDao.getAll();
            for (Attraction attraction : attractions) {
                vue.getAttractionComboBox().addItem(attraction.getId_attraction() + " - " + attraction.getNom());
            }

            // Puis on valide la nouvelle attraction du mois
            vue.getValiderAttractionButton().addActionListener(event -> {
                String nvAttraction = (String) vue.getAttractionComboBox().getSelectedItem();
                if (nvAttraction != null) {
                    int idAttraction = Integer.parseInt(nvAttraction.split(" - ")[0]);
                    boolean succes = attractionDao.modifAttractionMois(idAttraction);

                    if (succes) {
                        JOptionPane.showMessageDialog(vue, "Attraction du mois mise à jour !");
                        vue.getAttractionActuelleLabel().setText("Attraction actuelle : " + nvAttraction);
                    } else {
                        JOptionPane.showMessageDialog(vue, "Erreur lors de la mise à jour.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        });
    }
}
