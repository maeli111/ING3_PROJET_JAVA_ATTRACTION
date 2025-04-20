package Controleur;

import DAO.*;
import Modele.*;
import Vue.InfoAttraction;

public class testPageAttraction {
    public static void main(String[] args) {
        // Connexion à la base
        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        AttractionDaoInt attractionDAO = new AttractionDao(daoFactory);

        // Récupérer une attraction existante
        Attraction attraction = attractionDAO.chercher(2);

        // Afficher dans l'interface
        if (attraction != null) {
            new InfoAttraction(attraction);
        }

        else {
            System.out.println("Aucune attraction trouvée avec l'ID 2.");
        }

        daoFactory.disconnect(); // Fermer proprement
    }
}
