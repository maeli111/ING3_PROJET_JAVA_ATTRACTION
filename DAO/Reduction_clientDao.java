package DAO;

import Modele.Reduction_client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Reduction_clientDao {
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public Reduction_clientDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Récupérer de la base de données tous les objets des Reduction_clients dans une liste
     * @return : liste retournée des objets des Reduction_clients récupérés
     */
    public ArrayList<Reduction_client> getAll() {
        ArrayList<Reduction_client> listeReduction_client = new ArrayList<Reduction_client>();

        try {
            // connexion
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * from reduction_client");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                int id_client = resultats.getInt(1);
                int id_reduction = resultats.getInt(2);

                Reduction_client Reduction_client = new Reduction_client(id_client,id_reduction);

                listeReduction_client.add(Reduction_client);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste de Reduction_client impossible");
        }

        return listeReduction_client;
    }
}
