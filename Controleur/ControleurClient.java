package Controleur;

import Vue.*;
import Modele.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurClient {

    private VueClient vueClient;
    private Client client;

    public ControleurClient(VueClient vueClient, Client client) {
        this.vueClient = vueClient;
        this.client = client;

        // Ajouter des listeners d'événements pour chaque bouton
        addListeners();
    }

    private void addListeners() {
        // Listener pour le bouton "Accueil"
        vueClient.getBtnAccueil().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueClient.dispose(); // Ferme la fenêtre actuelle
                VueAccueil vueAccueil = new VueAccueil(client,null);
                new ControleurAccueil(vueAccueil,client, null);
                vueAccueil.setVisible(true);
            }
        });

        // Listener pour le bouton "Informations"
        vueClient.getBtnInfo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueClient.dispose(); // Ferme la fenêtre actuelle
                VuePlusInfos v = new VuePlusInfos(client, null);
                new ControleurPlusInfos(v, client, null);
                v.setVisible(true);

            }
        });

        // Listener pour le bouton "Calendrier"
        vueClient.getBtnCalendrier().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueClient.dispose(); // Ferme la fenêtre actuelle
                VueCalendrier vueCalendrier = new VueCalendrier(client, null);
                new ControleurCalendrier(vueCalendrier, client, null);
                vueCalendrier.setVisible(true);
            }
        });

        // Listener pour le bouton "Déconnexion"
        vueClient.getBtnDeconnexion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueClient.dispose(); // Ferme la fenêtre actuelle
                VueLogin vueLogin = new VueLogin();
                new ControleurLogin(vueLogin);
                vueLogin.setVisible(true);
            }
        });
    }
}
