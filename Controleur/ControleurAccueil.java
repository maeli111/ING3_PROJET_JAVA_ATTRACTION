package Controleur;

import Vue.*;
import Modele.*;

public class ControleurAccueil {

    public ControleurAccueil(VueAccueil vue, Client client, Admin admin) {

        vue.getCompte().addActionListener(e -> {
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
        });

        vue.getInformations().addActionListener(e -> {
            VuePlusInfos v = new VuePlusInfos(client, admin);
            new ControleurPlusInfos(v, client, admin);
            v.setVisible(true);

            vue.dispose();
        });

        vue.getInfos().addActionListener(e -> {
            VuePlusInfos v = new VuePlusInfos(client, admin);
            new ControleurPlusInfos(v, client, admin);
            v.setVisible(true);
            vue.dispose();
        });

        vue.getCalendrier().addActionListener(e -> {
            VueCalendrier vueCalendrier = new VueCalendrier(client, admin);
            new ControleurCalendrier(vueCalendrier, client, admin);
            vueCalendrier.setVisible(true);
            vue.dispose();
        });

        vue.getAccueil().addActionListener(e -> {
            // Redémarre ou recharge la page actuelle si besoin
        });
    }
}
