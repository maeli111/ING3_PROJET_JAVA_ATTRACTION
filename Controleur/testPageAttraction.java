package Controleur;

import DAO.*;
import Modele.*;
import Vue.InfoAttraction;
import java.time.LocalDate;

public class testPageAttraction {
    public static void main(String[] args) {
        /*
        // Connexion à la base
        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");

        // Récupération des DAOs
        AttractionDao attractionDAO = new AttractionDao(daoFactory);
        ClientDao clientDAO = new ClientDao(daoFactory);
        UtilisateurDao utilisateurDAO = new UtilisateurDao(daoFactory); // Votre DAO pour les utilisateurs

        Client client = clientDAO.getById(1);
        if (client != null) {
            System.out.println("=== CLIENT ID 1 ===");
            System.out.println("ID: " + client.getid_client());
            System.out.println("Nom: " + client.getNom());
            System.out.println("Prénom: " + client.getPrenom());
            System.out.println("Email: " + client.getEmail());
        } else {
            System.out.println("Aucun client trouvé avec l'ID 1");
        }

        // 1. Afficher l'utilisateur avec ID 1
        Utilisateur utilisateur = utilisateurDAO.getById(1);
        if (utilisateur != null) {
            System.out.println("=== UTILISATEUR ID 1 ===");
            System.out.println("ID: " + utilisateur.getId_utilisateur());
            System.out.println("Nom: " + utilisateur.getNom());
            System.out.println("Prénom: " + utilisateur.getPrenom());
            System.out.println("Email: " + utilisateur.getEmail());
        } else {
            System.out.println("Aucun utilisateur trouvé avec l'ID 1");
        }

        daoFactory.disconnect();

         */

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
