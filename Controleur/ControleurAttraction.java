package Controleur;

import Vue.*;
import Modele.Attraction;
import Modele.Client;
import Modele.Admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurAttraction {
    private VueAttraction vueAttraction;
    private Attraction attraction;
    private Client client;
    private Admin admin;

    public ControleurAttraction(VueAttraction vueAttraction, Attraction attraction, Client client, Admin admin) {
        this.vueAttraction = vueAttraction;
        this.attraction = attraction;
        this.client = client;
        this.admin = admin;

        // Ajouter les listeners d'événements pour les boutons
        addListeners();
    }

    private void addListeners() {
        // Listener pour le bouton "Accueil"
        vueAttraction.getAccueilButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigation vers la vue d'accueil
                vueAttraction.dispose();
                VueAccueil vueAccueil = new VueAccueil(client, admin);
                new ControleurAccueil(vueAccueil, client, admin);
                vueAccueil.setVisible(true);
            }
        });

        // Listener pour le bouton "Informations"
        vueAttraction.getInformationsButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VuePlusInfos v = new VuePlusInfos(client, admin);
                new ControleurPlusInfos(v, client, admin);
                v.setVisible(true);
                vueAttraction.dispose();
            }
        });

        // Listener pour le bouton "Calendrier"
        vueAttraction.getCalendrierButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VueCalendrier v = new VueCalendrier(client, admin);
                new ControleurCalendrier(v, client, admin);
                v.setVisible(true);
                vueAttraction.dispose();
            }
        });

        // Listener pour le bouton "Loupe" (Recherche)
        vueAttraction.getLoupeButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VueRecherche v = new VueRecherche(client, admin);
                new ControleurRecherche(v, client, admin);
                v.setVisible(true);
                vueAttraction.dispose();
            }
        });

        // Listener pour le bouton "Réserver"
        vueAttraction.getReserverButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lancer la vue Calendrier pour la réservation
                VueCalendrier v = new VueCalendrier(client, admin);
                new ControleurCalendrier(v, client, admin);
                v.setVisible(true);
                vueAttraction.dispose();
            }
        });

        // Listener pour le bouton "Compte"
        vueAttraction.getCompteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                vueAttraction.dispose();
            }
        });
    }
}
