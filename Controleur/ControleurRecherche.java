package Controleur;

import Vue.VueRecherche;
import Modele.Admin;
import Modele.Client;

import javax.swing.*;

public class ControleurRecherche {
    private VueRecherche vue;
    private Client client;
    private Admin admin;

    public ControleurRecherche(VueRecherche vue, Client client, Admin admin) {
        this.vue = vue;
        this.client = client;
        this.admin = admin;

        // --- Ajout des actions ---
        vue.getRechercherBtn().addActionListener(e -> lancerRecherche());
    }

    private void lancerRecherche() {
        String texteRecherche = vue.getChampRecherche().getText();
        String filtre = (String) vue.getFiltreCombo().getSelectedItem();

        // Ici tu pourras appeler DAO pour chercher dans la BDD selon les critères
        System.out.println("Texte saisi : " + texteRecherche);
        System.out.println("Filtre choisi : " + filtre);

        // Exemple feedback visuel :
        JOptionPane.showMessageDialog(vue,
                "Recherche : " + texteRecherche + "\nFiltre : " + filtre,
                "Résultat recherche",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
