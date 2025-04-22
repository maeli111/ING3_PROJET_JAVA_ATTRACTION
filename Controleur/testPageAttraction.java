package Controleur;

import DAO.*;
import Modele.*;
import Vue.InfoAttraction;
import java.time.LocalDate;

public class testPageAttraction {
    public static void main(String[] args) {
        // Connexion à la base
        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        AttractionDaoInt attractionDAO = new AttractionDao(daoFactory);

        // Récupérer une attraction existante
        Attraction attraction = attractionDAO.chercher(2);
        LocalDate date = LocalDate.of(2025, 4, 25);

        // Afficher dans l'interface
        if (attraction != null) {
            new InfoAttraction(attraction, date);
        }

        else {
            System.out.println("Aucune attraction trouvée avec cet ID");
        }

        daoFactory.disconnect(); // Fermer proprement
    }
}
