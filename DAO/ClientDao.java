package DAO;

import java.sql.*;
import java.util.*;
import Modele.Client;

public class ClientDao implements ClientDaoInt{
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
            PreparedStatement pUtilisateur = connexion.prepareStatement(sqlUtilisateur, Statement.RETURN_GENERATED_KEYS);
            pUtilisateur.setString(1, client.getEmail());
            pUtilisateur.setString(2, client.getNom());
            pUtilisateur.setString(3, client.getPrenom());
            pUtilisateur.setString(4, client.getMdp());
            int lignesUtilisateur = pUtilisateur.executeUpdate();
            //executeUpdate() retourne le nombre de lignes affectées.
            //Donc s’il retourne > 0 cela signifie que l’ajout de l'utilisateur a fonctionné

            if (lignesUtilisateur > 0) {
                //on utilise ResultSet pour récupérer la clé primaire (ID) que la base a créée automatiquement
                ResultSet rUtilisateur = pUtilisateur.getGeneratedKeys();
                if (rUtilisateur.next()) {
                    //getInt(1) = on lit la 1re colonne cad l'id_utilisateur
                    int idUtilisateurGenere = rUtilisateur.getInt(1);
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
                    PreparedStatement pClient = connexion.prepareStatement(sqlClient, Statement.RETURN_GENERATED_KEYS);
                    pClient.setInt(1, client.getid_utilisateur());
                    pClient.setInt(2, client.getage());
                    pClient.setString(3, "nouveau");
                    pClient.setString(4, client.getType_membre());

                    //on vérifie si l’insertion a marché
                    int lignesClient = pClient.executeUpdate();
                    if (lignesClient > 0) {
                        ResultSet rClient = pClient.getGeneratedKeys();
                        if (rClient.next()) {
                            int idClientGenere = rClient.getInt(1);
                            client.setid_client(idClientGenere);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Client seConnecter(String email, String mdp) {
        Client client = null;

        try {
            Connection connexion = daoFactory.getConnection();

            // 1. Récupérer l'utilisateur en fonction de l'email et du mot de passe
            String sqlUtilisateur = "SELECT * FROM utilisateur WHERE email = ? AND mdp = ?";
            PreparedStatement pUtilisateur = connexion.prepareStatement(sqlUtilisateur);
            pUtilisateur.setString(1, email);
            pUtilisateur.setString(2, mdp);
            ResultSet rUtilisateur = pUtilisateur.executeQuery();

            if (rUtilisateur.next()) {
                int idUtilisateur = rUtilisateur.getInt("id_utilisateur");
                String nom = rUtilisateur.getString("nom");
                String prenom = rUtilisateur.getString("prenom");
                String emailUser = rUtilisateur.getString("email"); // ou juste email
                String mdpUser = rUtilisateur.getString("mdp");

                // 2. Récupérer les infos client
                String sqlClient = "SELECT * FROM client WHERE id_utilisateur = ?";
                PreparedStatement pClient = connexion.prepareStatement(sqlClient);
                pClient.setInt(1, idUtilisateur);
                ResultSet rClient = pClient.executeQuery();

                if (rClient.next()) {
                    int idClient = rClient.getInt("id_client");
                    int age = rClient.getInt("age");
                    String typeClient = rClient.getString("type_client");
                    String typeMembre = rClient.getString("type_membre");

                    // Créer le client avec TOUTES les infos
                    client = new Client(idClient, idUtilisateur, nom, prenom, emailUser, mdpUser, age, typeClient, typeMembre);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }



    public void supprimer(int id_client) {
        try (Connection connexion = daoFactory.getConnection()) {
            // Étape 1 : récupérer l'id_utilisateur à partir du client
            String sqlSelect = "SELECT id_utilisateur FROM client WHERE id_client = ?";
            PreparedStatement pSelect = connexion.prepareStatement(sqlSelect);
            pSelect.setInt(1, id_client);
            ResultSet rSet = pSelect.executeQuery();

            if (rSet.next()) {
                int id_utilisateur = rSet.getInt("id_utilisateur");

                // Étape 2 : supprimer le client
                String sqlClient = "DELETE FROM client WHERE id_client = ?";
                PreparedStatement pClient = connexion.prepareStatement(sqlClient);
                pClient.setInt(1, id_client);
                int lignesClient = pClient.executeUpdate();

                // Étape 3 : supprimer l'utilisateur si client supprimé
                if (lignesClient > 0) {
                    String sqlUtilisateur = "DELETE FROM utilisateur WHERE id_utilisateur = ?";
                    PreparedStatement pUtilisateur = connexion.prepareStatement(sqlUtilisateur);
                    pUtilisateur.setInt(1, id_utilisateur);
                    int lignesUtilisateur = pUtilisateur.executeUpdate();

                    if (lignesUtilisateur > 0) {
                        System.out.println("Client et utilisateur supprimés avec succès.");
                    } else {
                        System.out.println("Client supprimé, mais échec lors de la suppression de l'utilisateur.");
                    }
                }
            } else {
                System.out.println("Aucun client trouvé avec l'ID fourni.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getEmailByIdClient(int idClient) {
        String email = null;

        try (Connection connexion = daoFactory.getConnection()) {
            // On récupère d'abord id_utilisateur depuis client
            String sql1 = "SELECT id_utilisateur FROM client WHERE id_client = ?";
            PreparedStatement ps1 = connexion.prepareStatement(sql1);
            ps1.setInt(1, idClient);
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                int idUtilisateur = rs1.getInt("id_utilisateur");

                // Maintenant, on récupère l'email depuis utilisateur
                String sql2 = "SELECT email FROM utilisateur WHERE id_utilisateur = ?";
                PreparedStatement ps2 = connexion.prepareStatement(sql2);
                ps2.setInt(1, idUtilisateur);
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    email = rs2.getString("email");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return email;
    }

    public String afficherEmailClient(int idClient) {
        String email = null;

        try (Connection connexion = daoFactory.getConnection()) {
            // Récupérer l'id_utilisateur du client à partir de l'id_client
            String sqlClient = "SELECT id_utilisateur FROM client WHERE id_client = ?";
            PreparedStatement pClient = connexion.prepareStatement(sqlClient);
            pClient.setInt(1, idClient);
            ResultSet rClient = pClient.executeQuery();

            if (rClient.next()) {
                // Récupérer l'id_utilisateur
                int idUtilisateur = rClient.getInt("id_utilisateur");

                // Maintenant, récupérer l'email depuis la table utilisateur
                String sqlUtilisateur = "SELECT email FROM utilisateur WHERE id_utilisateur = ?";
                PreparedStatement pUtilisateur = connexion.prepareStatement(sqlUtilisateur);
                pUtilisateur.setInt(1, idUtilisateur);
                ResultSet rUtilisateur = pUtilisateur.executeQuery();

                if (rUtilisateur.next()) {
                    email = rUtilisateur.getString("email");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return email; // Retourne l'email, ou null si le client n'a pas été trouvé
    }

    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";
        try (Connection con = daoFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }





}