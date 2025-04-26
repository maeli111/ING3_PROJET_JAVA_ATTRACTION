package Controleur;

import Vue.*;
import Modele.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurPlusInfos {
    public ControleurPlusInfos(VuePlusInfos vue, Client client, Admin admin) {

        vue.accueil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VueAccueil v = new VueAccueil(client, admin);
                new ControleurAccueil(v, client, admin);
                v.setVisible(true);
                vue.dispose();
            }
        });

        vue.calendrier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VueCalendrier v = new VueCalendrier(client, admin);
                new ControleurCalendrier(v, client, admin);
                v.setVisible(true);
                vue.dispose();
            }
        });

        vue.informations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VuePlusInfos v = new VuePlusInfos(client, admin);
                new ControleurPlusInfos(v, client, admin);
                v.setVisible(true);
                vue.dispose();
            }
        });

        vue.compte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (client == null && admin == null) {
                    VueLogin login = new VueLogin();
                    new ControleurLogin(login);
                    login.setVisible(true);
                } else if (client != null) {
                    VueClient v = new VueClient(client);
                    new ControleurClient(v, client);
                    v.setVisible(true);
                } else {
                    VueAdmin v = new VueAdmin(admin);
                    new ControleurAdmin(v, admin);
                    v.setVisible(true);
                }
                vue.dispose();
            }
        });

        // --- Action du bouton Loupe ---
        vue.loupe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VueRecherche v = new VueRecherche(client, admin);  // VueRecherche ouverte
                new ControleurRecherche(v, client, admin);
                v.setVisible(true);
                vue.dispose(); // Ferme la vue actuelle
            }
        });
    }
}
