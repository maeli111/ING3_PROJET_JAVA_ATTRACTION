package DAO;

import java.sql.*;
import java.util.ArrayList;
import Modele.Client;

public class ClientDao {
    private DaoFactory daoFactory;

    // Constructeur
    public ClientDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Récupérer de la base de données tous les objets des clients dans une liste
     * @return liste des clients
     */
    public ArrayList<Client> getAll() {
        ArrayList<Client> listeClients = new ArrayList<>();

        try {
            Connection connexion = daoFactory.getConnection();
            Statement statement = connexion.createStatement();

            // Exécuter la requête SELECT
            ResultSet resultats = statement.executeQuery("SELECT * FROM client");

            while (resultats.next()) {
                int id_client = resultats.getInt("id_client");
                int id_utilisateur = resultats.getInt("id_utilisateur");
                int age = resultats.getInt("age");
                String type_client = resultats.getString("type_client");
                String type_membre = resultats.getString("type_membre");

                Client client = new Client(id_client, id_utilisateur, age, type_client, type_membre);

                listeClients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listeClients;
    }

    public void inscrire(Client client) {
        try {
            Connection connexion = daoFactory.getConnection();

            // On ajoute l'utilisateur
            String sqlUtilisateur = "INSERT INTO utilisateur (email, nom, prenom, mdp) VALUES (?, ?, ?, ?)";
            PreparedStatement psUtilisateur = connexion.prepareStatement(sqlUtilisateur, Statement.RETURN_GENERATED_KEYS);
            psUtilisateur.setString(1, client.getEmail());
            psUtilisateur.setString(2, client.getNom());
            psUtilisateur.setString(3, client.getPrenom());
            psUtilisateur.setString(4, client.getMdp());
            int lignesUtilisateur = psUtilisateur.executeUpdate();
            //executeUpdate() retourne le nombre de lignes affectées.
            //Donc s’il retourne > 0 cela signifie que l’ajout de l'utilisateur a fonctionné

            if (lignesUtilisateur > 0) {
                //on utilise ResultSet pour récupérer la clé primaire (ID) que la base a créée automatiquement
                ResultSet rsUtilisateur = psUtilisateur.getGeneratedKeys();
                if (rsUtilisateur.next()) {
                    //getInt(1) = on lit la 1re colonne cad l'id_utilisateur
                    int idUtilisateurGenere = rsUtilisateur.getInt(1);
                    //on met à jour l'objet Client avec setid_utilisateur.
                    client.setid_utilisateur(idUtilisateurGenere);

                    String typeMembre;
                    int age = client.getage();
                    if (age < 18) {
                        typeMembre = "enfant";
                    } else if (age<25){
                        typeMembre = "etudiant";
                    }
                    else if (age >= 60) {
                        typeMembre = "senior";
                    } else {
                        typeMembre = "adulte";
                    }
                    client.setType_membre(typeMembre);

                    //On ajoute le client avec l'ID utilisateur
                    String sqlClient = "INSERT INTO client (id_utilisateur, age, type_client, type_membre) VALUES (?, ?, ?, ?)";
                    PreparedStatement psClient = connexion.prepareStatement(sqlClient, Statement.RETURN_GENERATED_KEYS);
                    psClient.setInt(1, client.getid_utilisateur());
                    psClient.setInt(2, client.getage());
                    psClient.setString(3, "nouveau");
                    psClient.setString(4, client.getType_membre());

                    //on vérifie si l’insertion a marché
                    int lignesClient = psClient.executeUpdate();
                    if (lignesClient > 0) {
                        ResultSet rsClient = psClient.getGeneratedKeys();
                        if (rsClient.next()) {
                            int idClientGenere = rsClient.getInt(1);
                            client.setid_client(idClientGenere);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}