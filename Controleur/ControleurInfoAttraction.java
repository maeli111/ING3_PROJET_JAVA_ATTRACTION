package Controleur;

import DAO.DaoFactory;
import DAO.ReductionDao;
import Modele.*;
import Vue.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;


public class ControleurInfoAttraction {
    private VueInfoAttraction vue;
    private Client client;
    private Admin admin;
    private Attraction attraction;
    private LocalDate date;

    // constructeur
    public ControleurInfoAttraction(VueInfoAttraction vue, Client client, Admin admin, Attraction attraction, LocalDate date) {
        this.vue = vue;
        this.client = client;
        this.admin = admin;
        this.attraction = attraction;
        this.date = date;

        // Ajout des listeners
        ajouterListeners();
    }

    // Méthode pour vérifier s'il y a une réduction sur l'attraction
    private double hasReduction() {
        // on récupère l'id de l'attraction
        int idAttraction = attraction.getId_attraction();

        // Création du DAO pour les réductions + connexion à la bdd
        DaoFactory daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");
        ReductionDao reductionDao = new ReductionDao(daoFactory);

        // Appel de la méthode pour obtenir le pourcentage de réduction pour cette attraction
        double reduction = reductionDao.getPourcentageAttraction(idAttraction);
        if (reduction >0){
            return reduction;
        }
        else {
            return 0;
        }

    }

    // configuration des listeners
    private void ajouterListeners() {
        vue.reserverBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Vérification de la réduction
                double reduc = hasReduction();
                if (reduc !=0) {
                    // Si une réduction est disponible, afficher un message
                    String message = String.format("Une réduction de %.2f%% est disponible pour cette attraction!", reduc);
                    // Affichage du message dans une boîte de dialogue
                    JOptionPane.showMessageDialog(vue, message);
                }

                // Rediriger selon le type de client (client connecté ou invité)
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
                VueAccueil a = new VueAccueil(client, admin);
                new ControleurAccueil(a, client, admin);
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
