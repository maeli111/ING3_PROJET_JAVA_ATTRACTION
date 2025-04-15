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

                // Utilise le constructeur secondaire simplifié
                Client client = new Client(id_client, id_utilisateur, age, type_client, type_membre);

                listeClients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste de clients impossible");
        }

        return listeClients;
    }

    /**
     * Ajouter un nouveau client dans la base de données
     */
    public void ajouter(Client client) {
        try {
            Connection connexion = daoFactory.getConnection();

            // 1. Ajouter l'utilisateur
            String sqlUtilisateur = "INSERT INTO utilisateur (email, nom, prenom, mdp) VALUES (?, ?, ?, ?)";
            PreparedStatement psUtilisateur = connexion.prepareStatement(sqlUtilisateur, Statement.RETURN_GENERATED_KEYS);
            psUtilisateur.setString(1, client.getEmail());
            psUtilisateur.setString(2, client.getNom());
            psUtilisateur.setString(3, client.getPrenom());
            psUtilisateur.setString(4, client.getMdp());
            int lignesUtilisateur = psUtilisateur.executeUpdate();

            if (lignesUtilisateur > 0) {
                ResultSet rsUtilisateur = psUtilisateur.getGeneratedKeys();
                if (rsUtilisateur.next()) {
                    int idUtilisateurGenere = rsUtilisateur.getInt(1);
                    client.setid_utilisateur(idUtilisateurGenere);

                    // 2. Ajouter le client avec l'ID utilisateur
                    String sqlClient = "INSERT INTO client (id_utilisateur, age, type_client, type_membre) VALUES (?, ?, ?, ?)";
                    PreparedStatement psClient = connexion.prepareStatement(sqlClient, Statement.RETURN_GENERATED_KEYS);
                    psClient.setInt(1, client.getid_utilisateur());
                    psClient.setInt(2, client.getage());
                    psClient.setString(3, client.getType_client());
                    psClient.setString(4, client.getType_membre());

                    int lignesClient = psClient.executeUpdate();
                    if (lignesClient > 0) {
                        ResultSet rsClient = psClient.getGeneratedKeys();
                        if (rsClient.next()) {
                            int idClientGenere = rsClient.getInt(1);
                            client.setid_client(idClientGenere);
                            System.out.println("Client ajouté avec ID : " + idClientGenere);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout du client et de l'utilisateur");
        }
    }
}