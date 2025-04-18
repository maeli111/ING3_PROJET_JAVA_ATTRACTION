//Sources:
//PreparedStatement -> https://docs.oracle.com/javase/8/docs/api/java/sql/PreparedStatement.html
//ResultSet -> https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html

package Controleur;

import Modele.Client;
import DAO.ClientDao;
import DAO.DaoFactory;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        ClientDao dao = new ClientDao(daoFactory);

        Scanner scanner = new Scanner(System.in);

        System.out.print("ID du client : ");
        int idClient = scanner.nextInt();

        // Appel de la méthode pour afficher l'email du client
        String email = dao.afficherEmailClient(idClient);

        if (email != null) {
            System.out.println("L'email du client est : " + email);
        } else {
            System.out.println("Client non trouvé ou problème d'accès à l'email.");
        }

        scanner.close();

    }
}