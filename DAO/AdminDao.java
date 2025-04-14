package DAO;

import Modele.Admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdminDao {
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public AdminDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Récupérer de la base de données tous les objets des clients dans une liste
     * @return : liste retournée des objets des clients récupérés
     */
    public ArrayList<Admin> getAll() {
        ArrayList<Admin> listeAdmin = new ArrayList<Admin>();

        /*
            Récupérer la liste des clients de la base de données dans listeClients
        */
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * from admin");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table produits dans la base de données
                int id_admin = resultats.getInt(1);
                int id_utilisateur = resultats.getInt(2);

                // instancier un objet de Produit avec ces 3 champs en paramètres
                Admin admin = new Admin(id_admin,id_utilisateur);

                // ajouter ce produit à listeProduits
                listeAdmin.add(admin);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste d'administrateurs impossible");
        }

        return listeAdmin;
    }
}
