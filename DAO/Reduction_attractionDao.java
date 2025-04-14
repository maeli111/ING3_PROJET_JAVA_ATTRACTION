package DAO;

import Modele.Reduction_attraction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Reduction_attractionDao {
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public Reduction_attractionDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Récupérer de la base de données tous les objets des Reduction_attractions dans une liste
     * @return : liste retournée des objets des Reduction_attractions récupérés
     */
    public ArrayList<Reduction_attraction> getAll() {
        ArrayList<Reduction_attraction> listeReduction_attraction = new ArrayList<Reduction_attraction>();

        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * from reduction_attraction");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                int id_attraction = resultats.getInt(1);
                int id_reduction = resultats.getInt(2);

                Reduction_attraction Reduction_attraction = new Reduction_attraction(id_attraction,id_reduction);

                listeReduction_attraction.add(Reduction_attraction);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste de Reduction_attraction impossible");
        }

        return listeReduction_attraction;
    }
}
