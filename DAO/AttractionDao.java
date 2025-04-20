package DAO;

import Modele.Attraction;
import java.time.*;
import java.sql.*;
import java.util.ArrayList;

public class AttractionDao implements AttractionDaoInt {
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
                int nb_reservation = resultats.getInt(7);

                // instancier un objet de Attraction avec ces 3 champs en paramètres
                Attraction attraction = new Attraction(id_attraction,nom,description,prix,capacite, type_attraction, nb_reservation);

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
                        rs.getString("type_attraction"),
                        rs.getInt("nb_reservation")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la recherche.");
        }
        return a;
    }

    public void ajouter(Attraction attraction) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(
                     "INSERT INTO attraction (nom, description, prix, capacite, type_attraction, nb_reservation) VALUES (?, ?, ?, ?, ?, ?)")) {

            ps.setString(1, attraction.getNom());
            ps.setString(2, attraction.getDescription());
            ps.setDouble(3, attraction.getPrix());
            ps.setInt(4, attraction.getCapacite());
            ps.setString(5, attraction.getTypeAttraction());
            ps.setInt(6, attraction.getNbReservation());

            ps.executeUpdate();

            // Fermeture des ressources
            ps.close();
            connexion.close();

            System.out.println("Attraction ajoutée avec succès.");
        }

        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de l'attraction.");
        }
    }

    public void supprimer(Attraction attraction) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("DELETE FROM attraction WHERE id_attraction = ?")) {

            ps.setInt(1, attraction.getIdAttraction());
            int lignes = ps.executeUpdate();

            if (lignes == 0) {
                System.out.println("Aucune attraction supprimée.");
            } else {
                System.out.println("Attraction supprimée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression.");
        }
    }

    public Attraction modifierNom(Attraction attraction) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(
                     "UPDATE attraction SET nom = ? WHERE id_attraction = ?")) {

            ps.setString(1, attraction.getNom());
            ps.setInt(2, attraction.getIdAttraction());

            int lignes = ps.executeUpdate();

            if (lignes == 0) {
                System.out.println("Aucune attraction mise à jour.");
            }

            else {
                System.out.println("Prix de l'attraction mise à jour.");
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour.");
        }

        return attraction;
    }

    public Attraction modifierDescription(Attraction attraction) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(
                     "UPDATE attraction SET description = ? WHERE id_attraction = ?")) {

            ps.setString(1, attraction.getDescription());
            ps.setInt(2, attraction.getIdAttraction());

            int lignes = ps.executeUpdate();

            if (lignes == 0) {
                System.out.println("Aucune attraction mise à jour.");
            }

            else {
                System.out.println("Description de l'attraction mise à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour.");
        }

        return attraction;
    }

    public Attraction modifierPrix(Attraction attraction) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(
                     "UPDATE attraction SET prix=? WHERE id_attraction=?")) {

            ps.setDouble(1, attraction.getPrix());
            ps.setInt(2, attraction.getIdAttraction());

            int lignes = ps.executeUpdate();

            if (lignes == 0) {
                System.out.println("Aucune attraction mise à jour.");
            }

            else {
                System.out.println("Prix de l'attraction mise à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour.");
        }

        return attraction;
    }

    public Attraction modifierCapacite(Attraction attraction) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(
                     "UPDATE attraction SET capacite=? WHERE id_attraction=?")) {

            ps.setInt(1, attraction.getCapacite());
            ps.setInt(2, attraction.getIdAttraction());

            int lignes = ps.executeUpdate();

            if (lignes == 0) {
                System.out.println("Aucune attraction mise à jour.");
            }

            else {
                System.out.println("Capacite de l'attraction mise à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour.");
        }

        return attraction;
    }

    public Attraction modifierTypeAttraction(Attraction attraction) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(
                     "UPDATE attraction SET type_attraction=?WHERE id_attraction=?")) {

            ps.setString(1, attraction.getTypeAttraction());
            ps.setInt(2, attraction.getIdAttraction());

            int lignes = ps.executeUpdate();

            if (lignes == 0) {
                System.out.println("Aucune attraction mise à jour.");
            }

            else {
                System.out.println("Type de l'attraction mise à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour.");
        }

        return attraction;
    }

    public Attraction modifierNbReservation(Attraction attraction) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(
                     "UPDATE attraction SET nb_reservation=? WHERE id_attraction=?")) {

            ps.setInt(1, attraction.getNbReservation());
            ps.setInt(2, attraction.getIdAttraction());

            int lignes = ps.executeUpdate();

            if (lignes == 0) {
                System.out.println("Aucune attraction mise à jour.");
            }

            else {
                System.out.println("Description de l'attraction mise à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour.");
        }

        return attraction;
    }

    public String afficherNom(int idAttraction) {
        String nom = null;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("SELECT nom FROM attraction WHERE id_attraction = ?")) {

            ps.setInt(1, idAttraction);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nom = rs.getString("nom");
                System.out.println("Nom de l'attraction : " + nom);
            }

            else {
                System.out.println("Aucune attraction trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération du nom.");
        }

        return nom;
    }

    public String afficherDescription(int idAttraction) {
        String description = null;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("SELECT description FROM attraction WHERE id_attraction = ?")) {

            ps.setInt(1, idAttraction);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                description = rs.getString("description");
                System.out.println("description de l'attraction : " + description);
            }

            else {
                System.out.println("Aucune attraction trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération du nom.");
        }

        return description;
    }

    public Double afficherPrix(int idAttraction) {
        double prix = 0.0;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("SELECT prix FROM attraction WHERE id_attraction = ?")) {

            ps.setInt(1, idAttraction);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                prix = rs.getDouble("prix");
                System.out.println("Nom de l'attraction : " + prix);
            }

            else {
                System.out.println("Aucune attraction trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération du nom.");
        }

        return prix;
    }

    public int afficherCapacite(int idAttraction) {
        int capacite = 0;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("SELECT capacite FROM attraction WHERE id_attraction = ?")) {

            ps.setInt(1, idAttraction);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                capacite = rs.getInt("nom");
                System.out.println("Nom de capacite : " + capacite);
            }

            else {
                System.out.println("Aucune attraction trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération du nom.");
        }

        return capacite;
    }

    public String afficherTypeAttraction(int idAttraction) {
        String type = null;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("SELECT type FROM attraction WHERE id_attraction = ?")) {

            ps.setInt(1, idAttraction);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                type = rs.getString("type");
                System.out.println("Nom de l'attraction : " + type);
            }

            else {
                System.out.println("Aucune attraction trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération du nom.");
        }

        return type;
    }

    public int afficherNbReservation(int idAttraction) {
        int nbResa = 0;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("SELECT nbResa FROM attraction WHERE id_attraction = ?")) {

            ps.setInt(1, idAttraction);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nbResa = rs.getInt("nombre de résevation");
                System.out.println("Nom de l'attraction : " + nbResa);
            }

            else {
                System.out.println("Aucune attraction trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération du nom.");
        }

        return nbResa;
    }

    public boolean estDisponible(LocalDate date, int idAttraction) {
        boolean disponible = true;

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement("SELECT SUM(nb_personne) AS total FROM reservation WHERE date_reservation = ? AND id_attraction = ? AND est_archivee = 0")){
            // 1. Récupérer le nombre total de personnes réservées pour l’attraction à cette date
            ps.setDate(1, java.sql.Date.valueOf(date));
            ps.setInt(2, idAttraction);
            ResultSet rs = ps.executeQuery();

            int totalReservations = 0;
            if (rs.next()) {
                totalReservations = rs.getInt("total");
            }

            // 2. Récupérer la capacité maximale de l’attraction
            String sqlCapacite = "SELECT capacite FROM attraction WHERE id_attraction = ?";
            PreparedStatement ps2 = connexion.prepareStatement(sqlCapacite);
            ps2.setInt(1, idAttraction);
            ResultSet rs2 = ps2.executeQuery();

            int capacite = 0;
            if (rs2.next()) {
                capacite = rs2.getInt("capacite");
            }

            // 3. Vérifier si la capacité est atteinte
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

}
