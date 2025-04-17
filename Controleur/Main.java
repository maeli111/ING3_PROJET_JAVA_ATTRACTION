//Sources:
//PreparedStatement -> https://docs.oracle.com/javase/8/docs/api/java/sql/PreparedStatement.html
//ResultSet -> https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html

package Controleur;

import Modele.Client;
import DAO.ClientDao;
import DAO.DaoFactory;

public class Main {
    public static void main(String[] args) {
        DaoFactory daoF = DaoFactory.getInstance("java_attraction", "root", "");
        ClientDao dao = new ClientDao(daoF);

        // Création d’un nouveau client (inscription)
        Client nouveauClient = new Client();
        nouveauClient.setid_utilisateur(0); // L'ID sera généré automatiquement
        nouveauClient.setid_client(0);      // L'ID client aussi
        nouveauClient.setAge(22);           // Âge pour déterminer le type_membre automatiquement
        nouveauClient.setType_client("nouveau"); // On peut le mettre par défaut, ou laisser le DAO le faire
        nouveauClient.setType_membre("");        // Sera défini dans le DAO
        // Ajoute les infos utilisateur :
        // (doit être dans Client même si héritées)
        nouveauClient.setid_utilisateur(0); // généré par DAO
        nouveauClient.setid_client(0);      // généré par DAO

        // Email et identifiants
        // (Ajoute des setters dans Utilisateur si besoin)
        // Exemple : ajouter les méthodes suivantes dans Utilisateur :
        // public void setEmail(String email) { this.email = email; } etc.
        nouveauClient.setEmail("nouveau@mail.com");
        nouveauClient.setNom("Dupont");
        nouveauClient.setPrenom("Alice");
        nouveauClient.setMdp("mdp123");

        // Inscription du client
        dao.inscrire(nouveauClient);

        System.out.println("Inscription réussie pour : " + nouveauClient.getPrenom() + " " + nouveauClient.getNom());
    }
}