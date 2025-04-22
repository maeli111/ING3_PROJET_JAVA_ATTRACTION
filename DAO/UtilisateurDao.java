//jsp à quoi y sert
package DAO;

import Modele.*;

import java.sql.*;
import java.util.ArrayList;

public class UtilisateurDao {
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public UtilisateurDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Récupérer de la base de données tous les objets des Utilisateurs dans une liste
     * @return : liste retournée des objets des Utilisateurs récupérés
     */
    public ArrayList<Utilisateur> getAll() {
        ArrayList<Utilisateur> listeUtilisateur = new ArrayList<Utilisateur>();

        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            ResultSet resultats = statement.executeQuery("select * from utilisateur");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                int id_utilisateur = resultats.getInt(1);
                String email = resultats.getString(2);
                String nom = resultats.getString(3);
                String prenom = resultats.getString(4);
                String mdp = resultats.getString(5);

            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste de Utilisateur impossible");
        }

        return listeUtilisateur;
    }
}


