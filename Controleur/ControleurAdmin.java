package Controleur;

import Modele.*;
import Vue.*;
import DAO.*;

import javax.swing.*;
import java.util.List;

public class ControleurAdmin {
    private VueAdmin vue;
    private Admin admin;
    private DaoFactory daoFactory;
    private AttractionDao attractionDao;

    public ControleurAdmin(VueAdmin vue, Admin admin) {
        this.admin = admin;
        this.vue = vue;

        daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");
        attractionDao = new AttractionDao(daoFactory);

        ajouterListeners();
        vue.setVisible(true);
    }

    private void ajouterListeners() {
        vue.getAccueilButton().addActionListener(e -> {
            vue.dispose();
            VueAccueil vueAccueil = new VueAccueil(null, admin);
            new ControleurAccueil(vueAccueil, null, admin);
            vueAccueil.setVisible(true);
        });

        vue.getInformationsButton().addActionListener(e -> {
            vue.dispose();
            VuePlusInfos v = new VuePlusInfos(null, admin);
            new ControleurPlusInfos(v, null, admin);
            v.setVisible(true);
        });

        vue.getCalendrierButton().addActionListener(e -> {
            vue.dispose();
            VueCalendrier vueCalendrier = new VueCalendrier(null, admin);
            new ControleurCalendrier(vueCalendrier, null, admin);
            vueCalendrier.setVisible(true);
        });

        vue.getDeconnexionButton().addActionListener(e -> {
            vue.dispose();
            VueLogin vueLogin = new VueLogin();
            new ControleurLogin(vueLogin);
            vueLogin.setVisible(true);
        });

        vue.getAttractionsButton().addActionListener(e -> {
            vue.dispose();
            VueAdminAttraction vueAdminAttraction = new VueAdminAttraction(admin);
            new ControleurAdminAttraction(vueAdminAttraction, admin);
            vueAdminAttraction.setVisible(true);
        });

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

            if (choix == 0) {
                VueAdminRC vueAdminRC = new VueAdminRC(admin);
                new ControleurAdminRC(vueAdminRC, admin);
                vueAdminRC.setVisible(true);
            } else if (choix == 1) {
                VueAdminRA vueAdminRA = new VueAdminRA(admin);
                new ControleurAdminRA(vueAdminRA, admin);
                vueAdminRA.setVisible(true);
            }
        });

        vue.getDossiersClientsButton().addActionListener(e -> {
            vue.dispose();
            VueAdminClient vueAdminClient = new VueAdminClient(admin);
            new ControleurAdminClient(vueAdminClient, admin);
            vueAdminClient.setVisible(true);
        });

        // --------- Attraction du mois -----------
        vue.getAttractionDuMoisButton().addActionListener(e -> {
            vue.getAttractionMoisPanel().setVisible(true);

            // Afficher l'attraction actuelle
            Attraction attractionActuelle = attractionDao.getAttractionMois();
            if (attractionActuelle != null) {
                vue.getAttractionActuelleLabel().setText(
                        "Attraction actuelle : " + attractionActuelle.getId_attraction() + " - " + attractionActuelle.getNom()
                );
            } else {
                vue.getAttractionActuelleLabel().setText("Aucune attraction du mois actuellement");
            }

            // Remplir le ComboBox avec toutes les attractions
            vue.getAttractionComboBox().removeAllItems();
            List<Attraction> attractions = attractionDao.getAll();
            for (Attraction attraction : attractions) {
                vue.getAttractionComboBox().addItem(attraction.getId_attraction() + " - " + attraction.getNom());
            }

            // Listener sur le bouton valider
            vue.getValiderAttractionButton().addActionListener(event -> {
                String selectedItem = (String) vue.getAttractionComboBox().getSelectedItem();
                if (selectedItem != null) {
                    // Récupérer l'id de l'attraction sélectionnée
                    int idSelectionne = Integer.parseInt(selectedItem.split(" - ")[0]);
                    boolean success = attractionDao.modifAttractionMois(idSelectionne);

                    if (success) {
                        JOptionPane.showMessageDialog(vue, "Attraction du mois mise à jour !");
                        vue.getAttractionActuelleLabel().setText("Attraction actuelle : " + selectedItem);
                    } else {
                        JOptionPane.showMessageDialog(vue, "Erreur lors de la mise à jour.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        });
    }
}
