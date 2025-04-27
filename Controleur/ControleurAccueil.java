package Controleur;

import Vue.*;
import Modele.*;
import DAO.*;

// gère les actions de VueAccueil
public class ControleurAccueil {

    public ControleurAccueil(VueAccueil vue, Client client, Admin admin) {
        // connexion avec la base de données
        DaoFactory daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");
        AttractionDao attractionDao = new AttractionDao(daoFactory);

        // récupération attraction du mois
        Attraction attractionDuMois = attractionDao.getAttractionMois(); // Utilise ta méthode pour obtenir l'attraction du mois
        if (attractionDuMois != null) {
            vue.afficherAttractionDuMois(attractionDuMois); // Méthode pour afficher l'attraction dans ta VueAccueil
        }

        // gestion des boutons et des actions

        // bouton compte mène à la page de connexion d'un client ou d'un admin
        vue.getCompte().addActionListener(e -> {
            if (client == null && admin == null) { // si pas connecté
                VueLogin login = new VueLogin();
                new ControleurLogin(login);
                login.setVisible(true);
            } else if (client != null) { // si un client est connecter
                VueClient vueClient = new VueClient(client);
                new ControleurClient(vueClient, client);
                vueClient.setVisible(true);
            } else { // si un admin est connecté
                VueAdmin vueAdmin = new VueAdmin(admin);
                new ControleurAdmin(vueAdmin, admin);
                vueAdmin.setVisible(true);
            }
            vue.dispose();
        });

        // bouton infos qui permet de voir les infos supplémentaires
        vue.getInformations().addActionListener(e -> {
            VuePlusInfos v = new VuePlusInfos(client, admin);
            new ControleurPlusInfos(v, client, admin);
            v.setVisible(true);

            vue.dispose();
        });

        // bouton loupe mène à la page recherche
        vue.getLoupeBtn().addActionListener(e ->{
            VueRecherche v = new VueRecherche(client, admin);
            new ControleurRecherche(v, client, admin);
            v.setVisible(true);
            vue.dispose();
        });

        // autre bouton infos mde voir les infos supplémentaires
        vue.getInfos().addActionListener(e -> {
            VuePlusInfos v = new VuePlusInfos(client, admin);
            new ControleurPlusInfos(v, client, admin);
            v.setVisible(true);
            vue.dispose();
        });

        // bouton calendrier mène à la page calendrier
        vue.getCalendrier().addActionListener(e -> {
            VueCalendrier vueCalendrier = new VueCalendrier(client, admin);
            new ControleurCalendrier(vueCalendrier, client, admin);
            vueCalendrier.setVisible(true);
            vue.dispose();
        });

    }
}
