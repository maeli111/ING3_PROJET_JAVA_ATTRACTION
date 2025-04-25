package Controleur;

import Modele.Admin;
import Vue.*;

import javax.swing.*;

public class ControleurAdmin {
    private VueAdmin vue;
    private Admin admin;

    public ControleurAdmin(VueAdmin vue,Admin admin) {
        this.admin = admin;
        this.vue = vue;
        ajouterListeners();
        vue.setVisible(true);
    }

    private void ajouterListeners() {
        vue.getAccueilButton().addActionListener(e -> {
            vue.dispose();
            new VueAccueil(null, admin).setVisible(true);
        });

        vue.getInformationsButton().addActionListener(e -> {
            vue.dispose();
            new VuePlusInfos(null, admin).setVisible(true);
        });

        vue.getCalendrierButton().addActionListener(e -> {
            vue.dispose();
            new VueCalendrier(null, admin).setVisible(true);
        });

        vue.getDeconnexionButton().addActionListener(e -> {
            vue.dispose();
            new VueAccueil(null, null).setVisible(true);
        });

        vue.getAttractionsButton().addActionListener(e -> {
            vue.dispose();
            new VueAdminAttraction(admin).setVisible(true);
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
                new VueAdminRC(admin).setVisible(true);
            } else if (choix == 1) {
                new VueAdminRA(admin).setVisible(true);
            }
        });

        vue.getDossiersClientsButton().addActionListener(e -> {
            vue.dispose();
            new VueAdminClient(admin).setVisible(true);
        });

        // Tu peux encore gérer Attraction du mois ici si tu veux
    }
}
