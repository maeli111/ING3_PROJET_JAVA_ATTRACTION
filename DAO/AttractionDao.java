package DAO;

import Modele.Attraction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.time.*;
import java.sql.*;

public class AttractionDao implements AttractionDaoInt{
    private DaoFactory daoFactory;

    public AttractionDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /*
     * C'est une méthode pour récupérer toutes les attractions dans la base de données
     * return : ArrayList<Attraction> => liste qui contient tous les objets Attraction récupérés
     */
    @Override
    public ArrayList<Attraction> getAll() {
        ArrayList<Attraction> listeAttraction = new ArrayList<>();

        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            ResultSet resultats = statement.executeQuery("SELECT * FROM attraction");

            while (resultats.next()) {
                int id_attraction = resultats.getInt(1);
                String nom = resultats.getString(2);
                String description = resultats.getString(3);
                double prix = resultats.getDouble(4);
                int capacite = resultats.getInt(5);
                String type_attraction = resultats.getString(6);
                int mois = resultats.getInt(7);

                Attraction attraction = new Attraction(id_attraction, nom, description, prix, capacite, type_attraction,mois);

                listeAttraction.add(attraction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste d'attractions impossible");
        }

        return listeAttraction;
    }

    /*
     * C'est une méthode qui ajoute une attraction à la base de données
     * paramètre attraction : c'est l'attraction à ajouter dans la bdd
     */
    @Override
    public void ajouter(Attraction attraction) {
        String requete = "INSERT INTO attraction (nom, description, prix, capacite, type_attraction, mois) VALUES ('" +
                attraction.getNom() + "', '" +
                attraction.getDescription() + "', " +
                attraction.getPrix() + ", " +
                attraction.getCapacite() + ", '" +
                attraction.getType_attraction() + "', '" +
                attraction.getMois() + "')";

        try (Connection connexion = daoFactory.getConnection();
             Statement statement = connexion.createStatement()) {
            statement.executeUpdate(requete);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de l'attraction impossible");
        }
    }


    /*
     * C'est une méthode qui modifie une attraction existante dans la bdd
     * paramètre attraction : objet Attraction qui va être modifé
     */
    @Override
    public void modifier(Attraction attraction) {
        String requete = "UPDATE attraction SET nom = ?, description = ?, prix = ?, capacite = ?, type_attraction = ?, mois = ? WHERE id_attraction = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement preparedStatement = connexion.prepareStatement(requete)) {

            preparedStatement.setString(1, attraction.getNom());
            preparedStatement.setString(2, attraction.getDescription());
            preparedStatement.setDouble(3, attraction.getPrix());
            preparedStatement.setInt(4, attraction.getCapacite());
            preparedStatement.setString(5, attraction.getType_attraction());
            preparedStatement.setInt(6, attraction.getMois());
            preparedStatement.setInt(7, attraction.getId_attraction());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Modification de l'attraction impossible");
        }
    }

    /*
     * C'est une méthode pour supprimer une attraction de la bdd
     * paramètre idAttraction : identifiant de l'attraction que l'on veut supprimer
     */
    @Override
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

    /*
     * C'est une méthode qui récupére une attraction avec son identifiant
     * paramètre id : identifiant de l'attraction que l'on cherche
     * return Attraction : objet Attraction si on la trouve, ou null si on ne la trouve pas
     */
    @Override
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

    // méthode qui cherche une attraction à l'aide de son ID dans la bdd
    @Override
    public Attraction chercher(int id) {
        Attraction a = null;
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("SELECT * FROM attraction WHERE id_attraction = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                a = new Attraction(
                        rs.getInt("id_attraction"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getInt("capacite"),
                        rs.getString("type_attraction")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la recherche.");
        }
        return a;
    }

    // méthode qui va regarder si une attraction est disponible pour une date donnée à l'aide de son ID
    @Override
    public boolean estDisponible(LocalDate date, int idAttraction) {
        boolean disponible = true;

        try (Connection connexion = daoFactory.getConnection();
            PreparedStatement ps = connexion.prepareStatement("SELECT SUM(nb_personne) AS total FROM reservation WHERE date_reservation = ? AND id_attraction = ? AND est_archivee = 0")){

            // on récupère le nombre total de places réservées pour l’attraction à cette date
            ps.setDate(1, java.sql.Date.valueOf(date));
            ps.setInt(2, idAttraction);
            ResultSet rs = ps.executeQuery();

            int totalReservations = 0;
            if (rs.next()) {
                totalReservations = rs.getInt("total");
            }

            // on récupère la capacité maximale de l’attraction
            String sqlCapacite = "SELECT capacite FROM attraction WHERE id_attraction = ?";
            PreparedStatement ps2 = connexion.prepareStatement(sqlCapacite);
            ps2.setInt(1, idAttraction);
            ResultSet rs2 = ps2.executeQuery();

            int capacite = 0;
            if (rs2.next()) {
                capacite = rs2.getInt("capacite");
            }

            // on vérifie si la capacité est atteinte
            if (totalReservations >= capacite) {
                disponible = false;
            }

            rs.close();
            rs2.close();
            ps.close();
            ps2.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return disponible;
    }

    // méthode qui va regarder le nombre de places diponibles pour une attraction pour une date donnée et l'ID de l'attraction
    @Override
    public int getPlacesDisponibles(LocalDate date, int idAttraction) {
        int placesDisponibles = 0;

        try (Connection connexion = daoFactory.getConnection()) {
            //on regarde le nombre total de places déjà réservées pour cette attraction à cett date
            PreparedStatement ps = connexion.prepareStatement(
                    "SELECT SUM(nb_personne) AS total FROM reservation WHERE date_reservation = ? AND id_attraction = ? AND est_archivee = 0"
            );
            ps.setDate(1, java.sql.Date.valueOf(date));
            ps.setInt(2, idAttraction);
            ResultSet rs = ps.executeQuery();

            int totalReservations = 0;
            if (rs.next()) {
                totalReservations = rs.getInt("total");
            }

            // on regarde la capcité maximal
            PreparedStatement ps2 = connexion.prepareStatement(
                    "SELECT capacite FROM attraction WHERE id_attraction = ?"
            );
            ps2.setInt(1, idAttraction);
            ResultSet rs2 = ps2.executeQuery();

            int capacite = 0;
            if (rs2.next()) {
                capacite = rs2.getInt("capacite");
            }

            // on calcule le nombre de places restantes
            placesDisponibles = capacite - totalReservations;

            rs.close();
            rs2.close();
            ps.close();
            ps2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return placesDisponibles;
    }

    // méthode qui cherche une attraction à l'aide de son ID dans la bdd
    @Override
    public Attraction getAttractionById(int idAttraction) {
        Attraction attraction = null;
        String query = "SELECT * FROM Attraction WHERE id_attraction = ?";

        DaoFactory daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");

        try (Connection conn = daoFactory.getConnection();  // Appel à getConnection() sur l'instance de DaoFactory
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idAttraction);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_attraction");
                    String nom = rs.getString("nom");
                    attraction = new Attraction(id, nom);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attraction;
    }

    // méthode qui retourne l'attraction du mois
    public Attraction getAttractionMois() {
        Attraction attraction = null;
        String query = "SELECT * FROM attraction WHERE mois = 1 ";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int id = rs.getInt("id_attraction");
                String nom = rs.getString("nom");
                String description = rs.getString("description");
                double prix = rs.getDouble("prix");
                int capacite = rs.getInt("capacite");
                String type = rs.getString("type_attraction");
                int mois = rs.getInt("mois");

                attraction = new Attraction(id, nom, description, prix, capacite, type, mois);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération de l'attraction du mois 1.");
        }

        return attraction;
    }

    // méthode pour mettre à jour l'attraction du mois
    public boolean modifAttractionMois(int idAttraction) {
        try {
            Connection conn = daoFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE attraction SET mois = 0");
            stmt.executeUpdate();

            stmt = conn.prepareStatement("UPDATE attraction SET mois = 1 WHERE id_attraction = ?");
            stmt.setInt(1, idAttraction);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // méthode qui cherche une attraction à l'aide de son nom dans la bdd
    @Override
    public Attraction getByName(String nom) {
        Attraction attraction = null;

        // requête sql pour récupérer l'attraction par son nom
        String sql = "SELECT * FROM attraction WHERE nom = ?";

        try (Connection connection = daoFactory.getConnection(); // Utiliser getConnection() de DaoFactory
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, nom);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id_attraction");
                String name = resultSet.getString("nom");
                String description = resultSet.getString("description");
                double prix = resultSet.getDouble("prix");
                int capacite = resultSet.getInt("capacite");
                String typeAttraction = resultSet.getString("type_attraction");

                attraction = new Attraction(id, name, description, prix, capacite, typeAttraction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération de l'attraction par nom.");
        }

        return attraction;
    }

}
