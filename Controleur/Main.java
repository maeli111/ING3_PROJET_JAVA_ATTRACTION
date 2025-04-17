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
        System.out.print("Entrez l'ID du client à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consomme le retour à la ligne

        dao.supprimer(id);
        scanner.close();
    }
}