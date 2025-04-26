//Sources:
//PreparedStatement -> https://docs.oracle.com/javase/8/docs/api/java/sql/PreparedStatement.html
//ResultSet -> https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html

package Controleur;
import Vue.*;
import Modele.*;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        Client client = new Client(); // ou récupérez le client connecté
        Admin admin = null; // ou récupérez l'admin si besoin

        VueAccueil vue = new VueAccueil(client, admin);
        new ControleurAccueil(vue, client, admin);
        vue.setVisible(true);
    }
}
