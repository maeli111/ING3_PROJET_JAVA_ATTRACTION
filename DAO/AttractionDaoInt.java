package DAO;

import Modele.Attraction;

import java.time.LocalDate;
import java.util.ArrayList;

public interface AttractionDaoInt {
    /// C'est une méthode pour récupérer toutes les attractions dans la base de données
    public ArrayList<Attraction> getAll();

    /// C'est une méthode qui ajoute une attraction à la base de données
    public void ajouter(Attraction attraction);

    /// C'est une méthode qui modifie une attraction existante dans la bdd
    public void modifier(Attraction attraction);

    /// C4est une méthode pour supprimer une attraction de la bdd
    public void supprimer(int idAttraction);

    /// C'est une méthode qui récupére une attraction avec son identifiant
    public Attraction getById(int id);

    public Attraction chercher(int id);

    public boolean estDisponible(LocalDate date, int idAttraction);

    public int getPlacesDisponibles(LocalDate date, int idAttraction);

    public Attraction getAttractionById(int idAttraction);

    public Attraction getAttractionMois();

    // Pour mettre à jour l'attraction du mois
    public boolean modifAttractionMois(int idAttraction);

    public Attraction getByName(String nom);;
}
