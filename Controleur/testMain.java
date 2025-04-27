package Controleur;

import Modele.*;
import Vue.*;
import DAO.*;
import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;

public class testMain {
    public static void main(String[] args) {
        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        AttractionDaoInt attractionDao = new AttractionDao(daoFactory);
        Attraction attraction = attractionDao.chercher(3); // ou un autre ID valide

        if (attraction == null) {
            System.out.println("L'attraction avec l'ID 3 n'a pas été trouvée.");
            return; // Arrêter l'exécution si l'attraction est null
        }

        VueReservationInvite view = new VueReservationInvite();
        LocalDate date = LocalDate.now().plusDays(1);

        new ControleurReservationInvite(view, attraction, date);
    }
}
