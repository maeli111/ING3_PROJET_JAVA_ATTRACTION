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
        DaoFactory daoF = DaoFactory.getInstance("java_attraction", "root", "");
    }
}