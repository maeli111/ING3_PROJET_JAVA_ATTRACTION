package DAO;

import Modele.Reduction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ReductionDao {
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public ReductionDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Récupérer de la base de données tous les objets des Reductions dans une liste
     * @return : liste retournée des objets des Reductions récupérés
     */
    public ArrayList<Reduction> getAll() {
        ArrayList<Reduction> listeReduction = new ArrayList<Reduction>();

        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            ResultSet resultats = statement.executeQuery("select * from reduction");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                int id_reduction = resultats.getInt(1);
                String nom = resultats.getString(2);
                int pourcentage = resultats.getInt(3);
                String description = resultats.getString(4);

                Reduction Reduction = new Reduction(id_reduction,nom,pourcentage,description);

                listeReduction.add(Reduction);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste de Reductions impossible");
        }

        return listeReduction;
    }

    public int getPourcentagePremiereVisite(int idClient) {
        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            //On vérifie si le client a déjà une réservation
            ResultSet resCheck = statement.executeQuery("SELECT COUNT(*) FROM reservation WHERE id_client = " + idClient);
            if (resCheck.next()) {
                int count = resCheck.getInt(1);
                if (count > 0) {
                    return 0;
                }
            }

            // Si première visite, on récupère le pourcentage de la réduction associée
            ResultSet resReduction = statement.executeQuery("SELECT pourcentage FROM reduction WHERE nom = 'Première visite'");
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
        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            // On compte le nb total de réservations effectuées par le client
            String query = "SELECT COUNT(*) AS nb_reservations " +
                    "FROM reservation " +
                    "WHERE id_client = " + idClient;

            ResultSet res = statement.executeQuery(query);
            if (res.next()) {
                int nbReservations = res.getInt("nb_reservations");

                // Vérifie si c'est un multiple de 5
                if (nbReservations > 0 && nbReservations % 5 == 0) {
                    // Récupère la réduction "Fidélité"
                    ResultSet resReduction = statement.executeQuery(
                            "SELECT pourcentage FROM reduction WHERE nom = 'Fidélité'"
                    );
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


}
