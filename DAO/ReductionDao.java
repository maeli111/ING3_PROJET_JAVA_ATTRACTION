package DAO;

import Modele.Reduction;

import java.sql.*;
import java.util.ArrayList;

public class ReductionDao {
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public ReductionDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Récupérer de la base de données tous les objets des Reductions dans une liste
     *
     * @return : liste retournée des objets des Reductions récupérés
     */
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

    public int getPourcentagePremiereVisite(int idClient) {
        String queryCheck = "SELECT COUNT(*) FROM reservation WHERE id_client = ?";
        String queryReduction = "SELECT pourcentage FROM reduction WHERE nom = 'Première visite'";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmtCheck = connexion.prepareStatement(queryCheck);
             PreparedStatement stmtReduction = connexion.prepareStatement(queryReduction)) {

            // Vérification si le client a déjà une réservation
            stmtCheck.setInt(1, idClient);
            ResultSet resCheck = stmtCheck.executeQuery();
            if (resCheck.next() && resCheck.getInt(1) > 0) {
                return 0; // Pas de réduction si le client a déjà réservé
            }

            // Si c'est une première visite, on récupère la réduction
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

    public int getPourcentageFidelite(int idClient) {
        String queryCountReservations = "SELECT COUNT(*) AS nb_reservations FROM reservation WHERE id_client = ?";
        String queryFidelite = "SELECT pourcentage FROM reduction WHERE nom = 'Fidélité'";

        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement stmtCountReservations = connexion.prepareStatement(queryCountReservations);
             PreparedStatement stmtFidelite = connexion.prepareStatement(queryFidelite)) {

            // Vérification du nombre de réservations
            stmtCountReservations.setInt(1, idClient);
            ResultSet res = stmtCountReservations.executeQuery();
            if (res.next()) {
                int nbReservations = res.getInt("nb_reservations");

                // Vérification si c'est un multiple de 5
                if (nbReservations > 0 && nbReservations % 5 == 0) {
                    // On récupère la réduction de fidélité
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

    public void ajouter(Reduction reduction) {
        String sql = "INSERT INTO reduction (nom, pourcentage, description) VALUES (?, ?, ?)";
        try (Connection conn = daoFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, reduction.getNom());
            stmt.setDouble(2, reduction.getPourcentage());
            stmt.setString(3, reduction.getDescription());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
}