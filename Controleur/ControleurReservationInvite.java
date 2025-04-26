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

    public ControleurReservationInvite(VueReservationInvite vue, Attraction attraction, LocalDate date) {
        this.vue = vue;
        this.attraction = attraction;
        this.date = date;

        // DAO
        daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        attractionDao = new AttractionDao(daoFactory);
        reservationDao = new ReservationDao(daoFactory);

        // Initialisation du nombre de personnes à 0
        vue.nbPersonneFieldNouveau.setText("0");

        // Setup des vues et des listeners
        setupVue();
        setupListeners();
        JOptionPane.showMessageDialog(vue,
                "En tant que nouveau client, aucune réduction ne peut être appliquée.\nCréez un compte pour en bénéficier.",
                "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setupVue() {
        // Si tu veux continuer d'afficher le titre dans la fenêtre (non obligatoire si c'est déjà fait)
        vue.titreResa.setText("Réserver l'attraction " + attraction.getNom() + " pour le " + date);
    }

    private void setupListeners() {
        // Ajouter des listeners aux boutons de navigation
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

    private void updatePrixTotal() {
        try {
            int nbPersonnes = Integer.parseInt(vue.nbPersonneFieldNouveau.getText());
            double prixTotal = nbPersonnes * attraction.getPrix();
            vue.prixLabelNouveau.setText(String.format("Prix total: %.2f €", prixTotal));
        } catch (Exception e) {
            vue.prixLabelNouveau.setText("Prix total: 0.00 €");
        }
    }

    private boolean verifierInformations() {
        // Vérifier si les champs nom, prénom, email et nombre de personnes sont remplis
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

    private void reserver() {
        try {
            String nom = vue.nomField.getText().trim();
            String prenom = vue.prenomField.getText().trim();
            String email = vue.emailFieldNouveau.getText().trim();
            int nbPersonnes = Integer.parseInt(vue.nbPersonneFieldNouveau.getText());

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
