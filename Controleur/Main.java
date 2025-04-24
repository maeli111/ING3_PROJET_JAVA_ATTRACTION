//Sources:
//PreparedStatement -> https://docs.oracle.com/javase/8/docs/api/java/sql/PreparedStatement.html
//ResultSet -> https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html

package Controleur;


import Modele.Admin;
import Modele.Client;
import Vue.VueAccueil;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Client client = new Client();
            Admin admin = new Admin();
            admin = null;
            client = null;
            VueAccueil VueAccueil = new VueAccueil(client, admin);
            VueAccueil.setVisible(true);
        });
    }
}

