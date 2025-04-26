package Controleur;

import Modele.*;
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
            VueAccueil vueAccueil = new VueAccueil(null,admin);
            new ControleurAccueil(vueAccueil,null, admin);
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
                new ControleurAdminRC(vueAdminRC,admin);
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

        // Tu peux encore gérer Attraction du mois ici si tu veux
    }
}
