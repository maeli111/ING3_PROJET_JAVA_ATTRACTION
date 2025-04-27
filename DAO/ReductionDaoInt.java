package DAO;

import Modele.Attraction;
import Modele.Reduction;
import Modele.Reservation;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReductionDaoInt {
    // C'est une méthode pour récupérer toutes les attractions dans la base de données
    public ArrayList<Reduction> getAll();

    // méthode qui vérifie si un client a déjà effectué une réservation à l'aide de son ID
    // + retourne le pourcentage de réduction si c'est sa première visite
    public int getPourcentagePremiereVisite(int idClient);

    // méthode qui vérifie si le client a effectué 5 réservations à l'aide de son ID
    // + retourne la réduction de fidélité
    public int getPourcentageFidelite(int idClient);

    // méthode qui récupère le pourcentage de réduction d'une attraction
    public int getPourcentageAttraction(int idAttraction);

    // C'est une méthode qui ajoute une réduction à la base de données
    public void ajouter(Reduction reduction);

    // méthode qui modifie une réduction existante dans la bdd
    public void modifier(int ancienId, Reduction reduction);

    // méthode pour supprimer une réduction de la bdd
    public void supprimer(int idReduction);

    // méthode qui retourne toutes les réductions (sans attraction associée)
    public ArrayList<Reduction> getReductionsSansAttraction();

    // méthode qui retourne toutes les réductions liées à une attraction
    public ArrayList<Reduction> getReductionsAvecAttraction();

    // méthode qui supprime toutes les liaisons entre une réduction et ses attractions
    public void supprimerLiaisonsAttractions(int idReduction);

    // méthode qui retourne les attractions liées à une réduction donnée
    public ArrayList<Attraction> getAttractionsLiees(int idReduction);

    // méthode qui retourne toutes les attractions qui ne sont pas liées à une réduction spécifique
    public ArrayList<Attraction> getAttractionsNonLiees(int idReduction);

    // méthode qui  lie une réduction à une attraction
    public void lierReductionAttraction(int idReduction, int idAttraction);

    // méthode qui vérifie si une réduction n'est liée à aucune attraction
    public boolean isEmpty(int id_reduction);

    // méthode qui récupère le pourcentage de réduction en fonction de son ID
    public double getPourcentageById(int idReduction);

    // méthode qui retourne le nombre de réservations faites par un client
    public int NbResaClient(int idClient) throws SQLException;

    // méthode qui récupère la description d'une réduction avec son ID
    public String getDescriptionById(int id);
}
