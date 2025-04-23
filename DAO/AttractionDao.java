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

    /**
     * Ajouter une attraction dans la base de données
     */
    public void ajouter(Attraction attraction) {
        String requete = "INSERT INTO attraction (nom, description, prix, capacite, type_attraction) VALUES ('" +
                attraction.getNom() + "', '" +
                attraction.getDescription() + "', " +
                attraction.getPrix() + ", " +
                attraction.getCapacite() + ", '" +
                attraction.getType_attraction() + "')";

        try (Connection connexion = daoFactory.getConnection();
             Statement statement = connexion.createStatement()) {
            statement.executeUpdate(requete);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de l'attraction impossible");
        }
    }

    /**
     * Modifier une attraction existante
     */
    public void modifier(Attraction attraction) {
        String requete = "UPDATE attraction SET " +
                "nom = '" + attraction.getNom() + "', " +
                "description = '" + attraction.getDescription() + "', " +
                "prix = " + attraction.getPrix() + ", " +
                "capacite = " + attraction.getCapacite() + ", " +
                "type_attraction = '" + attraction.getType_attraction() + "' " +
                "WHERE id_attraction = " + attraction.getId_attraction();

        try (Connection connexion = daoFactory.getConnection();
             Statement statement = connexion.createStatement()) {
            statement.executeUpdate(requete);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Modification de l'attraction impossible");
        }
    }

    /**
     * Supprimer une attraction
     */
    public void supprimer(int idAttraction) {
        String requete = "DELETE FROM attraction WHERE id_attraction = " + idAttraction;

        try (Connection connexion = daoFactory.getConnection();
             Statement statement = connexion.createStatement()) {
            statement.executeUpdate(requete);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression de l'attraction impossible");
        }
    }

    /**
     * Obtenir une attraction par son ID
     */
    public Attraction getById(int id) {
        String requete = "SELECT * FROM attraction WHERE id_attraction = " + id;
        try (Connection connexion = daoFactory.getConnection();
             Statement statement = connexion.createStatement();
             ResultSet resultats = statement.executeQuery(requete)) {

            if (resultats.next()) {
                int id_attraction = resultats.getInt("id_attraction");
                String nom = resultats.getString("nom");
                String description = resultats.getString("description");
                double prix = resultats.getDouble("prix");
                int capacite = resultats.getInt("capacite");
                String type = resultats.getString("type_attraction");

                return new Attraction(id_attraction, nom, description, prix, capacite, type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Récupération de l'attraction impossible");
        }
        return null;
    }

}
