package DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Modele.Client;

public class ClientDao {
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public ClientDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Récupérer de la base de données tous les objets des clients dans une liste
     * @return : liste retournée des objets des clients récupérés
     */
    public ArrayList<Client> getAll() {
        ArrayList<Client> listeClients = new ArrayList<Client>();

        /*
            Récupérer la liste des clients de la base de données dans listeClients
        */
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * from client");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table produits dans la base de données
                int id_client = resultats.getInt(1);
                int id_utilisateur = resultats.getInt(2);
                int age = resultats.getInt(3);
                String type_client = resultats.getString(4);
                String type_membre = resultats.getString(5);

                // instancier un objet de Produit avec ces 3 champs en paramètres
                Client client = new Client(id_client,id_utilisateur,age,type_client,type_membre);

                // ajouter ce produit à listeProduits
                listeClients.add(client);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste de clients impossible");
        }

        return listeClients;
    }


}
