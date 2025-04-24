package DAO;

import Modele.Admin;

import java.sql.*;
import java.util.ArrayList;

public class AdminDao implements AdminDaoInt {
    private DaoFactory daoFactory;

    /**
     * Constructeur de la classe AdminDao
     * En paramètre : daoFactory => une instance de DaoFactory pour obtenir la connexion à la base de données
     */
    public AdminDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Cette méthode permet de récupérer tous les administrateurs dans la base de données
     * return : une ArrayList contenant tous les objets Admin récupérés
     */
    @Override
    public ArrayList<Admin> getAll() {
        // On initialise la liste d'administrateurs à retourner
        ArrayList<Admin> listeAdmin = new ArrayList<Admin>();

        try {
            // Connexion à la base de données via la DaoFactory
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            ResultSet resultats = statement.executeQuery("SELECT * FROM admin");

            while (resultats.next()) {
                // On récupère id_admin et id_utilisateur
                int id_admin = resultats.getInt(1);
                int id_utilisateur = resultats.getInt(2);

                // On instancie Admin avec les données récupérées
                Admin admin = new Admin(id_admin, id_utilisateur);
                //On l'ajoute à la liste
                listeAdmin.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listeAdmin;
    }

    /**
     * C'est un méthode qui vérifie les identifiants de connexion d’un admin
     * paramètres email : email et mot de passe entrées par l’utilisateur
     * return : un objet Admin si les identifiants existent et qu’il s’agit bien d’un administrateur, sinon null
     */
    @Override
    public Admin connexionAdmin(String email, String mdp) {
        Admin adminConnecte = null;

        try {
            Connection connexion = daoFactory.getConnection();

            //Requête pour vérifierqu'un utilisateur avec cet email et mdp existe
            String sqlUtilisateur = "SELECT * FROM utilisateur WHERE email = ? AND mdp = ?";
            PreparedStatement pUtilisateur = connexion.prepareStatement(sqlUtilisateur);
            pUtilisateur.setString(1, email);
            pUtilisateur.setString(2, mdp);

            // On exécute la requête
            ResultSet rUtilisateur = pUtilisateur.executeQuery();

            // Si un utilisateur correspondant a été trouvé alors ...
            if (rUtilisateur.next()) {
                // ... on récupère l'identifiant de l'utilisateur
                int id_utilisateur = rUtilisateur.getInt("id_utilisateur");

                // On vérifie que c' est un admin
                String sqlAdmin = "SELECT * FROM admin WHERE id_utilisateur = ?";
                PreparedStatement psAdmin = connexion.prepareStatement(sqlAdmin);
                psAdmin.setInt(1, id_utilisateur);

                ResultSet rAdmin = psAdmin.executeQuery();

                // Si un administrateur est trouvé alors...
                if (rAdmin.next()) {
                    // ... on récupère l'id de l'admin
                    int id_admin = rAdmin.getInt("id_admin");

                    // puis les autres infos de l'utilisateur
                    String nom = rUtilisateur.getString("nom");
                    String prenom = rUtilisateur.getString("prenom");

                    // Et on crée l’objet Admin avec toutes les données
                    adminConnecte = new Admin(id_admin, id_utilisateur, email, nom, prenom, mdp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adminConnecte;
    }
}
