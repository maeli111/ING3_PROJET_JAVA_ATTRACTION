package DAO;

import Modele.*;

import java.sql.*;
import java.util.ArrayList;

public class ReductionDao  implements ReductionDaoInt{
    private DaoFactory daoFactory;

    public ReductionDao(DaoFactory daoFactory){
        this.daoFactory = daoFactory;
    }

    /*
     * Récupérer de la base de données tous les objets des Reductions dans une liste
     *
     * @return : liste retournée des objets des Reductions récupérés
     */
    @Override
    public ArrayList<Reduction> getAll() {
        ArrayList<Reduction> listeReduction = new ArrayList<Reduction>();

        try (Connection connexion = daoFactory.getConnection();
             Statement statement = connexion.createStatement()) {

            ResultSet resultats = statement.executeQuery("SELECT * FROM reduction");

            while (resultats.next()) {
                int id_reduction = resultats.getInt(1);
                String nom = resultats.getString(2);
                int pourcentage = resultats.getInt(3);
                String description = resultats.getString(4);

                Reduction reduction = new Reduction(id_reduction, nom, pourcentage, description);
                listeReduction.add(reduction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste de Reductions impossible");
        }

        return listeReduction;
    }

    // méthode qui vérifie si un client a déjà effectué une réservation à l'aide de son ID
    // + retourne le pourcentage de réduction si c'est sa première visite
    @Override
    public int getPourcentagePremiereVisite(int idClient) {
        // on récupère le client correspondant à cet ID dans la bdd
        String queryCheck = "SELECT COUNT(*) FROM reservation WHERE id_client = ?";
        String queryReduction = "SELECT pourcentage FROM reduction WHERE nom = 'Première visite'";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmtCheck = connexion.prepareStatement(queryCheck);
             PreparedStatement stmtReduction = connexion.prepareStatement(queryReduction)) {

            // on regarde si le client a déjà fait une réservation
            stmtCheck.setInt(1, idClient);
            ResultSet resCheck = stmtCheck.executeQuery();

            // pas de réduction si le client a déjà réservé
            if (resCheck.next() && resCheck.getInt(1) > 0) {
                return 0;
            }

            // si c'est sa première visite on récupère le pourcentage de réduction
            ResultSet resReduction = stmtReduction.executeQuery();
            if (resReduction.next()) {
                return resReduction.getInt("pourcentage");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la vérification de la première visite.");
        }

        return 0;
    }

    // méthode qui vérifie si le client a effectué 5 réservations à l'aide de son ID
    // + retourne la réduction de fidélité
    @Override
    public int getPourcentageFidelite(int idClient) {
        // on récupère le client correspondant à cet ID dans la bdd
        String queryCountReservations = "SELECT COUNT(*) AS nb_reservations FROM reservation WHERE id_client = ?";
        String queryFidelite = "SELECT pourcentage FROM reduction WHERE nom = 'Fidélité'";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmtCountReservations = connexion.prepareStatement(queryCountReservations);
             PreparedStatement stmtFidelite = connexion.prepareStatement(queryFidelite)) {

            // on vérifie le nombre de réservations faite par le client
            stmtCountReservations.setInt(1, idClient);
            ResultSet res = stmtCountReservations.executeQuery();
            if (res.next()) {
                int nbReservations = res.getInt("nb_reservations");

                // on vérifie si c'est un multiple de 5
                if (nbReservations > 0 && nbReservations % 5 == 0) {
                    // on récupère la réduction de fidélité
                    ResultSet resReduction = stmtFidelite.executeQuery();
                    if (resReduction.next()) {
                        return resReduction.getInt("pourcentage");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la vérification de la fidélité.");
        }

        return 0;
    }

    // méthode qui récupère le pourcentage de réduction d'une attraction
    @Override
    public int getPourcentageAttraction(int idAttraction) {
        // on récupère l'attraction et le pourcentage
        String queryCheckAttraction = "SELECT id_reduction FROM reduction_attraction WHERE id_attraction = ?";
        String queryReduction = "SELECT pourcentage FROM reduction WHERE id_reduction = ?";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmtCheckAttraction = connexion.prepareStatement(queryCheckAttraction);
             PreparedStatement stmtReduction = connexion.prepareStatement(queryReduction)) {

            // on regarde si l'attraction a une réduction
            stmtCheckAttraction.setInt(1, idAttraction);
            ResultSet resCheckAttraction = stmtCheckAttraction.executeQuery();

            if (resCheckAttraction.next()) {
                int idReduction = resCheckAttraction.getInt("id_reduction");

                // on récupère le pourcentage associé à cette réduction
                stmtReduction.setInt(1, idReduction);
                ResultSet resReduction = stmtReduction.executeQuery();
                if (resReduction.next()) {
                    return resReduction.getInt("pourcentage");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération de la réduction pour l'attraction.");
        }

        return 0; // s'il n'y a pas de réduction trouvée
    }

    // méthode qui ajoute une réduction à la base de données
    @Override
    public void ajouter(Reduction reduction) {
        String query = "INSERT INTO Reduction (nom, pourcentage, description) VALUES (?, ?, ?)";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, reduction.getNom());
            stmt.setInt(2, reduction.getPourcentage());
            stmt.setString(3, reduction.getDescription());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idGenere = rs.getInt(1);
                reduction.setId_reduction(idGenere);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // méthode qui modifie une réduction existante dans la bdd
    @Override
    public void modifier(int ancienId, Reduction reduction) {
        String sql = "UPDATE reduction SET id_reduction = ?,nom = ?, pourcentage = ?, description = ? WHERE id_reduction = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(2, reduction.getNom());
            stmt.setDouble(3, reduction.getPourcentage());
            stmt.setString(4, reduction.getDescription());
            stmt.setInt(1, reduction.getId_reduction());
            stmt.setInt(5, ancienId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // méthode pour supprimer une réduction de la bdd
    @Override
    public void supprimer(int idReduction) {
        String sql = "DELETE FROM reduction WHERE id_reduction = ?";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReduction);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // méthode qui retourne toutes les réductions (sans attraction associée)
    @Override
    public ArrayList<Reduction> getReductionsSansAttraction() {
        ArrayList<Reduction> liste = new ArrayList<>();

        String sql = "SELECT * FROM Reduction WHERE id_reduction NOT IN (SELECT id_reduction FROM Reduction_Attraction)";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_reduction");
                String nom = rs.getString("nom");
                int pourcentage = rs.getInt("pourcentage");
                String description = rs.getString("description");

                liste.add(new Reduction(id, nom, pourcentage, description));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des réductions sans attraction.");
        }

        return liste;
    }

    // méthode qui retourne toutes les réductions liées à une attraction
    @Override
    public ArrayList<Reduction> getReductionsAvecAttraction() {
        ArrayList<Reduction> liste = new ArrayList<>();

        String sql = "SELECT DISTINCT r.* " +
                "FROM Reduction r " +
                "JOIN Reduction_Attraction ra ON r.id_reduction = ra.id_reduction";

        try (Connection connection = daoFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_reduction");
                String nom = rs.getString("nom");
                int pourcentage = rs.getInt("pourcentage");
                String description = rs.getString("description");

                liste.add(new Reduction(id, nom, pourcentage, description));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération des réductions liées aux attractions.");
        }

        return liste;
    }

    // méthode qui supprime toutes les liaisons entre une réduction et ses attractions
    @Override
    public void supprimerLiaisonsAttractions(int idReduction) {
        String sql = "DELETE FROM Reduction_Attraction WHERE id_reduction = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReduction);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // méthode qui retourne les attractions liées à une réduction donnée
    @Override
    public ArrayList<Attraction> getAttractionsLiees(int idReduction) {
        ArrayList<Attraction> attractions = new ArrayList<>();
        String sql = "SELECT a.* FROM Attraction a " +
                "JOIN Reduction_Attraction ra ON a.id_attraction = ra.id_attraction " +
                "WHERE ra.id_reduction = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReduction);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                attractions.add(new Attraction(
                        rs.getInt("id_attraction"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getInt("capacite"),
                        rs.getString("type_attraction")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attractions;
    }

    // méthode qui retourne toutes les attractions qui ne sont pas liées à une réduction spécifique
    @Override
    public ArrayList<Attraction> getAttractionsNonLiees(int idReduction) {
        ArrayList<Attraction> attractions = new ArrayList<>();
        String sql = "SELECT * FROM Attraction WHERE id_attraction NOT IN (" +
                "SELECT id_attraction FROM Reduction_Attraction WHERE id_reduction = ?)";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReduction);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                attractions.add(new Attraction(
                        rs.getInt("id_attraction"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDouble("prix"),
                        rs.getInt("capacite"),
                        rs.getString("type_attraction")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return attractions;
    }

    // méthode qui  lie une réduction à une attraction
    @Override
    public void lierReductionAttraction(int idReduction, int idAttraction) {
        String sql = "INSERT INTO Reduction_Attraction (id_reduction, id_attraction) VALUES (?, ?)";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReduction);
            stmt.setInt(2, idAttraction);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // méthode qui vérifie si une réduction n'est liée à aucune attraction
    @Override
    public boolean isEmpty(int id_reduction) {
        String query = "SELECT COUNT(*) FROM Reduction_Attraction WHERE id_reduction = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id_reduction);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;  // retourne true si aucune attraction n'est liée
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;  // par défaut retourne true si une erreur se produit
    }

    // méthode qui récupère le pourcentage de réduction en fonction de son ID
    @Override
    public double getPourcentageById(int idReduction) {
        double pourcentage = 0.0;

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT pourcentage FROM reduction WHERE id_reduction = ?")) {
            ps.setInt(1, idReduction);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pourcentage = rs.getDouble("pourcentage");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pourcentage;
    }

    // méthode qui retourne le nombre de réservations faites par un client
    @Override
    public int NbResaClient(int idClient) throws SQLException {
        String query = "SELECT COUNT(DISTINCT id_reservation) FROM reservation WHERE id_client = ?";

        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

            return 0;
        }
    }

    // méthode qui récupère la description d'une réduction avec son ID
    @Override
    public String getDescriptionById(int id) {
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT description FROM reduction WHERE id_reduction = ?")) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("description");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Description non trouvée.";
    }

}