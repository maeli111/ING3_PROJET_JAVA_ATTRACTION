package DAO;

import Modele.Reduction_attraction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Reduction_attractionDao implements Reduction_attractionDaoInt {
    private DaoFactory daoFactory;

    public Reduction_attractionDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Cette méthode récupère de la bdd tous les objets des Reduction_attractions dans une liste
     * return : liste retournée des objets des Reduction_attractions récupérés
     */
    @Override
    public ArrayList<Reduction_attraction> getAll() {
        ArrayList<Reduction_attraction> listeReduction_attraction = new ArrayList<Reduction_attraction>();

        try {
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            ResultSet resultats = statement.executeQuery("select * from reduction_attraction");

            while (resultats.next()) {
                int id_RA = resultats.getInt(1);
                int id_attraction = resultats.getInt(2);
                int id_reduction = resultats.getInt(3);

                Reduction_attraction Reduction_attraction = new Reduction_attraction(id_RA, id_attraction,id_reduction);

                listeReduction_attraction.add(Reduction_attraction);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste de Reduction_attraction impossible");
        }

        return listeReduction_attraction;
    }
}
