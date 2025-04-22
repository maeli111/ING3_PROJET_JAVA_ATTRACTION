package Controleur;

import DAO.*;
import Vue.*;
import Modele.Reservation;

import java.time.LocalDate;

public class testReservation {
    public static void main(String[] args) {
        // Exemple de réservation avec des données factices
        Reservation reservationTest = new Reservation(
                0,                      // id_reservation (pas encore insérée)
                1,                      // id_client (à adapter selon ton contexte)
                "",                     // nom (sera rempli via le formulaire)
                "",                     // prénom
                "",                     // mail
                LocalDate.of(2025, 5, 1), // date_reservation
                null,                   // date_achat (remplie auto dans la vue)
                2,                      // id_attraction (à adapter selon ta BDD)
                0.0,                    // prix_total (calculé automatiquement)
                0,                      // nb_personne (à remplir)
                0                       // est_archivee
        );

        // Lancer la fenêtre
        //new pageReservation(reservationTest);
    }
}


