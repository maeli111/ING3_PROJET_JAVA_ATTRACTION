//Sources:
//PreparedStatement -> https://docs.oracle.com/javase/8/docs/api/java/sql/PreparedStatement.html
//ResultSet -> https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html

package Controleur;
import Vue.VueLogin;
import Vue.VueInscription;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VueInscription VueConnexionClient = new VueInscription();
            VueConnexionClient.setVisible(true);
        });
    }
}

