package Controleur;

import Vue.*;
import Modele.*;
import DAO.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ControleurReservationInvite {
    private final VueReservationInvite vue;
    private final Attraction attraction;
    private final LocalDate date;

    private final DaoFactory daoFactory;
    private final AttractionDao attractionDao;
    private final ReservationDao reservationDao;

    // constructeur
    public ControleurReservationInvite(VueReservationInvite vue, Attraction attraction, LocalDate date) {
        this.vue = vue;
        this.attraction = attraction;
        this.date = date;

        // DAO + connexion à la bdd
        daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        attractionDao = new AttractionDao(daoFactory);
        reservationDao = new ReservationDao(daoFactory);

        // initialisation du nombre de personnes à 0
        vue.nbPersonneFieldNouveau.setText("0");

        // Setup des vues et des listeners
        setupVue();
        setupListeners();

        // affichage pour nouveau client
        JOptionPane.showMessageDialog(vue,
                "En tant que nouveau client, aucune réduction ne peut être appliquée.\nCréez un compte pour en bénéficier.",
                "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    // titre de la vue
    private void setupVue() {
        vue.titreResa.setText("Réserver l'attraction " + attraction.getNom() + " pour le " + date);
    }

    // configuration des listeners
    private void setupListeners() {
        vue.addListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();

                // Action pour le bouton "Compte"
                if (source == vue.btnCompte) {
                    VueLogin login = new VueLogin();
                    new ControleurLogin(login);
                    login.setVisible(true);
                    vue.dispose();
                }

                // Action pour le bouton "Informations"
                if (source == vue.btnInformations) {
                    VuePlusInfos v = new VuePlusInfos(null, null);
                    new ControleurPlusInfos(v, null, null);
                    v.setVisible(true);
                    vue.dispose();
                }

                // Action pour le bouton "Calendrier"
                if (source == vue.btnCalendrier) {
                    VueCalendrier vueCalendrier = new VueCalendrier(null, null);
                    new ControleurCalendrier(vueCalendrier, null, null);
                    vueCalendrier.setVisible(true);
                    vue.dispose();
                }

                // Action pour le bouton "Réserver"
                if (source == vue.reserverButton) {
                    if (verifierInformations()) {
                        reserver();
                        VueAccueil vueAccueil = new VueAccueil(null,null);
                        new ControleurAccueil(vueAccueil,null,null);
                        vueAccueil.setVisible(true);
                        vue.dispose();
                    } else {
                        JOptionPane.showMessageDialog(vue, "Veuillez remplir tous les champs avant de réserver.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Gestion des boutons + et -
        vue.moinsBtnNouveau.addActionListener(e -> {
            int val = Integer.parseInt(vue.nbPersonneFieldNouveau.getText());
            if (val > 0) {
                vue.nbPersonneFieldNouveau.setText(String.valueOf(val - 1));
                updatePrixTotal();
            }
        });

        vue.plusBtnNouveau.addActionListener(e -> {
            int val = Integer.parseInt(vue.nbPersonneFieldNouveau.getText());
            int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getId_attraction());
            if (val < 10 && val < placesRestantes) {
                vue.nbPersonneFieldNouveau.setText(String.valueOf(val + 1));
                updatePrixTotal();
            } else {
                JOptionPane.showMessageDialog(vue, "Il ne reste que " + placesRestantes + " places disponibles", "Limite atteinte", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // méthode qui met à jour le pix total en fonction du nombre de personne choisi par le client
    private void updatePrixTotal() {
        try {
            int nbPersonnes = Integer.parseInt(vue.nbPersonneFieldNouveau.getText());
            double prixTotal = nbPersonnes * attraction.getPrix();
            vue.prixLabelNouveau.setText(String.format("Prix total: %.2f €", prixTotal));
        } catch (Exception e) {
            vue.prixLabelNouveau.setText("Prix total: 0.00 €");
        }
    }

    // méthode pour vérifier que les champs nom, prénom, email et nombre de personnes sont remplis
    private boolean verifierInformations() {
        // on récupère la valeur des champs
        String nom = vue.nomField.getText().trim();
        String prenom = vue.prenomField.getText().trim();
        String email = vue.emailFieldNouveau.getText().trim();
        String nbPersonnesText = vue.nbPersonneFieldNouveau.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || nbPersonnesText.isEmpty()) {
            return false;  // Un ou plusieurs champs ne sont pas remplis
        }

        try {
            int nbPersonnes = Integer.parseInt(nbPersonnesText);
            if (nbPersonnes <= 0) {
                return false;  // Le nombre de personnes doit être supérieur à 0
            }
        } catch (NumberFormatException e) {
            return false;  // Si le nombre de personnes n'est pas un entier valide
        }

        return true;
    }

    // méthode pour effectuer la réservation
    private void reserver() {
        try {
            String nom = vue.nomField.getText().trim();
            String prenom = vue.prenomField.getText().trim();
            String email = vue.emailFieldNouveau.getText().trim();
            int nbPersonnes = Integer.parseInt(vue.nbPersonneFieldNouveau.getText());

            // le client ne peut pas prendre plus de place qu'il n'en reste
            int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getId_attraction());
            if (nbPersonnes > placesRestantes) {
                throw new IllegalArgumentException("Il ne reste que " + placesRestantes + " places disponibles.");
            }

            double prixTotal = nbPersonnes * attraction.getPrix();

            // Créer la réservation
            Reservation reservation = new Reservation(
                    0, 0, nom, prenom, email,
                    date, LocalDate.now(), attraction.getId_attraction(), prixTotal, nbPersonnes, 0
            );

            // enregistrement de la résevation dans la bdd
            reservationDao.ajouter(reservation);

            // Afficher un message de confirmation
            JOptionPane.showMessageDialog(vue,
                    String.format("Réservation confirmée pour %s %s\nEmail : %s\nNombre de personnes : %d\nPrix total : %.2f €",
                            prenom, nom, email, nbPersonnes, prixTotal));


        } catch (Exception e) {
            JOptionPane.showMessageDialog(vue, "Erreur : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
