package DAO;

import Modele.Attraction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AttractionDao {
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public AttractionDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Récupérer de la base de données tous les objets des attractions dans une liste
     * @return : liste retournée des objets des attractions récupérés
     */
    public ArrayList<Attraction> getAll() {
        ArrayList<Attraction> listeAttraction = new ArrayList<Attraction>();

        /*
            Récupérer la liste des attractions de la base de données dans listeattractions
        */
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // récupération des attractions de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * from attraction");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table attractions dans la base de données
                int id_attraction = resultats.getInt(1);
                String nom = resultats.getString(2);
                String description = resultats.getString(3);
                double prix = resultats.getDouble(4);
                int capacite = resultats.getInt(5);
                String type_attraction = resultats.getString(6);

                // instancier un objet de Attraction avec ces 3 champs en paramètres
                Attraction attraction = new Attraction(id_attraction,nom,description,prix,capacite, type_attraction);

                // ajouter ce Attraction à listeattractions
                listeAttraction.add(attraction);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste d'attractions impossible");
        }

        return listeAttraction;
    }
}
