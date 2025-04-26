package Controleur;

import Modele.*;
import Vue.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ControleurInfoAttraction {
    private VueInfoAttraction vue;
    private Client client;
    private Admin admin;
    private Attraction attraction;
    private LocalDate date;

    public ControleurInfoAttraction(VueInfoAttraction vue, Client client, Admin admin, Attraction attraction, LocalDate date) {
        this.vue = vue;
        this.client = client;
        this.admin = admin;
        this.attraction = attraction;
        this.date = date;

        // Ajout des listeners
        ajouterListeners();
    }

    private void ajouterListeners() {
        vue.reserverBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if (client == null) {
                        // Si le client est null, ouvrir VueReservationInvite
                        vue.dispose();
                        VueReservationInvite view = new VueReservationInvite();
                        new ControleurReservationInvite(view, attraction, date);
                        view.setVisible(true);
                    } else {
                        // Si le client existe, ouvrir VueReservationClient
                        vue.dispose();
                        VueReservationClient rc = new VueReservationClient(client);
                        new ControleurReservationClient(rc, attraction, date, client);
                        rc.setVisible(true);
                    }
            }
        });


        vue.accueil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vue.dispose();
                VueAccueil a = new VueAccueil(client,admin);
                new ControleurAccueil(a,client,admin);
                a.setVisible(true);
            }
        });

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


        vue.getCalendrier().addActionListener(e -> {
            VueCalendrier vueCalendrier = new VueCalendrier(client, admin);
            new ControleurCalendrier(vueCalendrier, client, admin);
            vueCalendrier.setVisible(true);
            vue.dispose();
        });
    }
}
