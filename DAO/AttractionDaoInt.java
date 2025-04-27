package DAO;

import Modele.Attraction;

import java.time.LocalDate;
import java.util.ArrayList;

public interface AttractionDaoInt {
    // C'est une méthode pour récupérer toutes les attractions dans la base de données
    public ArrayList<Attraction> getAll();

    // C'est une méthode qui ajoute une attraction à la base de données
    public void ajouter(Attraction attraction);

    // C'est une méthode qui modifie une attraction existante dans la bdd
    public void modifier(Attraction attraction);

    // C'est une méthode pour supprimer une attraction de la bdd
    public void supprimer(int idAttraction);

    // C'est une méthode qui récupére une attraction avec son identifiant
    public Attraction getById(int id);

    // méthode qui cherche une attraction à l'aide de son ID dans la bdd
    public Attraction chercher(int id);

    // méthode qui va regarder si une attraction est disponible pour une date donnée à l'aide de son ID
    public boolean estDisponible(LocalDate date, int idAttraction);

    // méthode qui va regarder le nombre de places diponibles pour une attraction pour une date donnée et l'ID de l'attraction
    public int getPlacesDisponibles(LocalDate date, int idAttraction);

    // méthode qui cherche une attraction à l'aide de son ID dans la bdd
    public Attraction getAttractionById(int idAttraction);

    // méthode qui retourne l'attraction du mois
    public Attraction getAttractionMois();

    // méthode pour mettre à jour l'attraction du mois
    public boolean modifAttractionMois(int idAttraction);

    // méthode qui cherche une attraction à l'aide de son nom dans la bdd
    public Attraction getByName(String nom);;
}
