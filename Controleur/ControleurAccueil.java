package Controleur;

import Vue.*;
import Modele.*;
import DAO.*;

public class ControleurAccueil {

    public ControleurAccueil(VueAccueil vue, Client client, Admin admin) {

        DaoFactory daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");
        AttractionDao attractionDao = new AttractionDao(daoFactory);

        Attraction attractionDuMois = attractionDao.getAttractionMois(); // Utilise ta méthode pour obtenir l'attraction du mois
        if (attractionDuMois != null) {
            vue.afficherAttractionDuMois(attractionDuMois); // Méthode pour afficher l'attraction dans ta VueAccueil
        }


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

    }
}
