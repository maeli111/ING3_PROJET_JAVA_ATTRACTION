package Controleur;

import DAO.*;
import Modele.*;

import java.util.Scanner;

public class TestMain {
    public static void main(String[] args) {
        // Initialisation de la DAO
        DaoFactory dao = DaoFactory.getInstance("java_attraction", "root", "");
        AttractionDao attractionDAO = new AttractionDao(dao);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Affichage du menu
            System.out.println("\nMenu ");
            System.out.println("1. Ajouter attraction");
            System.out.println("2. Modifier attraction");
            System.out.println("3. Supprimer attraction");
            System.out.println("4. Lister toutes les attractions");
            System.out.println("5. Quitter");

            System.out.print("Choisissez une option : ");
            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    // Ajouter une attraction
                    System.out.print("\nNom de l'attraction : ");
                    String nomAttraction = scanner.next();

                    System.out.print("Description de l'attraction : ");
                    String descriptionAttraction = scanner.next();

                    System.out.print("Prix de l'attraction : ");
                    double prixAttraction = scanner.nextDouble();

                    System.out.print("Capacité de l'attraction : ");
                    int capaciteAttraction = scanner.nextInt();

                    System.out.print("Type de l'attraction : ");
                    String typeAttraction = scanner.next();

                    System.out.print("Nombre de réservations : ");
                    int nbReservationAttraction = scanner.nextInt();

                    // Création d'une nouvelle attraction
                    Attraction nouvelleAttraction = new Attraction(
                            0,  // L'ID sera auto-incrémenté
                            nomAttraction,
                            descriptionAttraction,
                            prixAttraction,
                            capaciteAttraction,
                            typeAttraction,
                            nbReservationAttraction
                    );

                    // Appel de la méthode pour ajouter l'attraction
                    attractionDAO.ajouter(nouvelleAttraction);
                    System.out.println("Attraction ajoutée.");

                    break;

                case 2:
                    // Modifier une attraction existante
                    System.out.print("\nID de l'attraction à modifier : ");
                    int idAttractionModif = scanner.nextInt();

                    Attraction attractionModif = attractionDAO.chercher(idAttractionModif);

                    if (attractionModif != null) {
                        System.out.println("\nQue souhaitez-vous modifier ?");
                        System.out.println("1. Nom");
                        System.out.println("2. Description");
                        System.out.println("3. Prix");
                        System.out.println("4. Capacité");
                        System.out.println("5. Type d'attraction");
                        System.out.println("6. Nombre de réservations");

                        System.out.print("Choix : ");
                        int choixModif = scanner.nextInt();
                        scanner.nextLine(); // consommer le saut de ligne

                        switch (choixModif) {
                            case 1:
                                System.out.print("Nouveau nom de l'attraction : ");
                                String nouvNom = scanner.nextLine();

                                // Mise à jour de l'attraction
                                Attraction attractionMaj =  new Attraction (attractionModif.getIdAttraction(), nouvNom, attractionModif.getDescription(), attractionModif.getPrix(), attractionModif.getCapacite(), attractionModif.getTypeAttraction(), attractionModif.getNbReservation());

                                // Appel de la méthode pour modifier l'attraction
                                attractionDAO.modifierNom(attractionMaj);
                                System.out.println("Attraction modifiée.");

                                break;

                            case 2:
                                System.out.print("Nouvelle description de l'attraction : ");
                                String nouvDescription = scanner.nextLine();

                                // Mise à jour de l'attraction
                                Attraction attractionMaj1 =  new Attraction (attractionModif.getIdAttraction(), attractionModif.getNom(), nouvDescription, attractionModif.getPrix(), attractionModif.getCapacite(), attractionModif.getTypeAttraction(), attractionModif.getNbReservation());

                                // Appel de la méthode pour modifier l'attraction
                                attractionDAO.modifierDescription(attractionMaj1);
                                System.out.println("Attraction modifiée.");

                                break;

                            case 3:
                                System.out.print("Nouveau prix de l'attraction : ");
                                double nouveauPrix = scanner.nextDouble();

                                // Mise à jour de l'attraction
                                Attraction attractionMaj2 =  new Attraction (attractionModif.getIdAttraction(), attractionModif.getNom(), attractionModif.getDescription(), nouveauPrix, attractionModif.getCapacite(), attractionModif.getTypeAttraction(), attractionModif.getNbReservation());

                                // Appel de la méthode pour modifier l'attraction
                                attractionDAO.modifierPrix(attractionMaj2);
                                System.out.println("Attraction modifiée.");

                                break;

                            case 4:
                                System.out.print("Nouvelle capacite de l'attraction : ");
                                int nouvCapacite = scanner.nextInt();

                                // Mise à jour de l'attraction
                                Attraction attractionMaj3 =  new Attraction (attractionModif.getIdAttraction(), attractionModif.getNom(), attractionModif.getDescription(), attractionModif.getPrix(), nouvCapacite, attractionModif.getTypeAttraction(), attractionModif.getNbReservation());

                                // Appel de la méthode pour modifier l'attraction
                                attractionDAO.modifierCapacite(attractionMaj3);
                                System.out.println("Attraction modifiée.");

                                break;

                            case 5:
                                System.out.print("Nouveau type de l'attraction : ");
                                String nouvType = scanner.nextLine();

                                // Mise à jour de l'attraction
                                Attraction attractionMaj4 =  new Attraction (attractionModif.getIdAttraction(), attractionModif.getNom(), attractionModif.getDescription(), attractionModif.getPrix(), attractionModif.getCapacite(), nouvType, attractionModif.getNbReservation());

                                // Appel de la méthode pour modifier l'attraction
                                attractionDAO.modifierTypeAttraction(attractionMaj4);
                                System.out.println("Attraction modifiée.");

                                break;

                            case 6:
                                System.out.print("Nouveau nb reservation de l'attraction : ");
                                int nouvNbResa = scanner.nextInt();

                                // Mise à jour de l'attraction
                                Attraction attractionMaj6 =  new Attraction (attractionModif.getIdAttraction(), attractionModif.getNom(), attractionModif.getDescription(), attractionModif.getPrix(), attractionModif.getCapacite(), attractionModif.getTypeAttraction(), nouvNbResa);

                                // Appel de la méthode pour modifier l'attraction
                                attractionDAO.modifierNbReservation(attractionMaj6);
                                System.out.println("Attraction modifiée.");

                                break;

                            default:
                                System.out.println("Option invalide.");
                        }

                        System.out.println("Attraction modifiée.");
                    } else {
                        System.out.println("Aucune attraction trouvée avec cet ID.");
                    }
                    break;


                case 3:
                    // Supprimer une attraction
                    System.out.print("\nID de l'attraction à supprimer : ");
                    int idAttractionSupp = scanner.nextInt();

                    Attraction attractionSupp = attractionDAO.chercher(idAttractionSupp);

                    if (attractionSupp != null) {
                        // Appel de la méthode pour supprimer l'attraction
                        attractionDAO.supprimer(attractionSupp);
                        System.out.println("Attraction supprimée.");
                    } else {
                        System.out.println("Aucune attraction trouvée avec cet ID.");
                    }
                    break;

                case 4:
                    // Lister toutes les attractions
                    System.out.println("\nListe des attractions disponibles :");
                    for (Attraction a : attractionDAO.getAll()) {
                        System.out.println(a.getIdAttraction() + ": " + a.getNom() + " - " + a.getDescription() + " | Prix : " + a.getPrix() + "€");
                    }
                    break;

                case 5:
                    // Quitter l'application
                    dao.disconnect();
                    System.out.println("Au revoir !");
                    return;

                default:
                    System.out.println("Option invalide. Essayez encore.");
            }
        }
    }
}
