package Controleur;

import Vue.*;
import Modele.*;

import java.awt.event.*;

public class ControleurClient {

    private VueClient vueClient;
    private Client client;

    //Constructeur
    public ControleurClient(VueClient vueClient, Client client) {
        this.vueClient = vueClient;
        this.client = client;
        ajouterListeners();
    }

    private void ajouterListeners() {
        // Bouton qui mène vers l'accueil
        vueClient.getBtnAccueil().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueClient.dispose();
                VueAccueil vueAccueil = new VueAccueil(client, null);
                new ControleurAccueil(vueAccueil, client, null);
                vueAccueil.setVisible(true);
            }
        });

        // Bouton qui mène vers les infos de PalasiLand
        vueClient.getBtnInfo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueClient.dispose();
                VuePlusInfos v = new VuePlusInfos(client, null);
                new ControleurPlusInfos(v, client, null);
                v.setVisible(true);
            }
        });

        // Bouton qui mène vers le Calendrier
        vueClient.getBtnCalendrier().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueClient.dispose();
                VueCalendrier vueCalendrier = new VueCalendrier(client, null);
                new ControleurCalendrier(vueCalendrier, client, null);
                vueCalendrier.setVisible(true);
            }
        });

        // Bouton qui déconnecte le client et mène vers Login
        vueClient.getBtnDeconnexion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueClient.dispose();
                VueLogin vueLogin = new VueLogin();
                new ControleurLogin(vueLogin);
                vueLogin.setVisible(true);
            }
        });

        // Bouton qui mène vers le filtre des attractions
        vueClient.getBtnLoupe().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueClient.dispose();
                VueRecherche vueRecherche = new VueRecherche(client, null);
                new ControleurRecherche(vueRecherche, client, null);
                vueRecherche.setVisible(true);
            }
        });
    }
}
