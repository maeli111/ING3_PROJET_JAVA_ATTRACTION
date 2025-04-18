package DAO;

import Modele.Admin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.*;
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
        }

        return listeAdmin;
    }


    public Admin connexionAdmin(String email, String mdp) {
        // On initialise un objet Admin
        // il sera rempli si la connexion est réussi
        Admin adminConnecte = null;

        try {
            Connection connexion = daoFactory.getConnection();

            // Il faut vérifier si un utilisateur existe avec cet email et ce mdp
            String sqlUtilisateur = "SELECT * FROM utilisateur WHERE email = ? AND mdp = ?";
            PreparedStatement pUtilisateur = connexion.prepareStatement(sqlUtilisateur);
            pUtilisateur.setString(1, email);
            pUtilisateur.setString(2, mdp);
            
            ResultSet rUtilisateur = pUtilisateur.executeQuery();

            // Si on a trouvé un utilisateur qui correspond avec l'email et le mdp
            if (rUtilisateur.next()) {
                // On récupère l'id_utilisateur grâce au résultat
                int id_utilisateur = rUtilisateur.getInt("id_utilisateur");

                // On vérifie si cet utilisateur est un admin
                String sqlAdmin = "SELECT * FROM admin WHERE id_utilisateur = ?";
                PreparedStatement psAdmin = connexion.prepareStatement(sqlAdmin);
                psAdmin.setInt(1, id_utilisateur);

                ResultSet rAdmin = psAdmin.executeQuery();

                // Si on trouve un admin qui correspond à l'utilisateur trouvé
                if (rAdmin.next()) {
                    // On récupère ainsi l'id de l'admin
                    int id_admin = rAdmin.getInt("id_admin");

                    // On récupère les autres infos de l'utilisateur depuis le premier ResultSet
                    String nom = rUtilisateur.getString("nom");
                    String prenom = rUtilisateur.getString("prenom");

                    adminConnecte = new Admin(id_admin, id_utilisateur, email, nom, prenom, mdp);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // On retourne l'admin si la connexion a réussi sinon null
        return adminConnecte;
    }

}
