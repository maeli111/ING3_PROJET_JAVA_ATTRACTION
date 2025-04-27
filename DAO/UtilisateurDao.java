//jsp à quoi y sert
package DAO;

import Modele.Utilisateur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UtilisateurDao implements UtilisateurDaoInt{
    private DaoFactory daoFactory;

    public UtilisateurDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Cette méthode récupère de la bdd tous les objets des Utilisateurs dans une liste
     * return : liste retournée des objets des Utilisateurs récupérés
     */
    @Override
    public ArrayList<Utilisateur> getAll() {
        ArrayList<Utilisateur> listeUtilisateur = new ArrayList<Utilisateur>();

        try {
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            ResultSet resultats = statement.executeQuery("select * from utilisateur");

            while (resultats.next()) {
                int id_utilisateur = resultats.getInt(1);
                String email = resultats.getString(2);
                String nom = resultats.getString(3);
                String prenom = resultats.getString(4);
                String mdp = resultats.getString(5);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste de Utilisateur impossible");
        }

        return listeUtilisateur;
    }
}
