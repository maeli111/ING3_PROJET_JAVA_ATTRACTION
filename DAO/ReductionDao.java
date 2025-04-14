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
                double pourcentage = resultats.getDouble(3);
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
}
