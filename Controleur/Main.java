//Sources:
//PreparedStatement -> https://docs.oracle.com/javase/8/docs/api/java/sql/PreparedStatement.html
//ResultSet -> https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html

package Controleur;


import Modele.Admin;
import Modele.Client;
import Vue.VueAccueil;
import Vue.VueLogin;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VueLogin vueLogin = new VueLogin();
            vueLogin.setVisible(true);
        });
    }
}

