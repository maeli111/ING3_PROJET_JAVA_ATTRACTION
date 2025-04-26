package Controleur;

import Vue.*;
import DAO.*;
import Modele.*;

public class ControleurAccueil {
    private VueAccueil vueAccueil;
    private Client client;
    private Admin admin;

    public ControleurAccueil(VueAccueil vue, Client client, Admin admin) {
        this.vueAccueil = vue;
        this.client = client;
        this.admin = admin;

        // Ajout des listeners
        vueAccueil.getLoupeBtn().addActionListener(e -> ouvrirRecherche());

        // ... autres listeners ...
    }

    private void ouvrirRecherche() {
        // Fermer la vue actuelle si nécessaire (optionnel)
        vueAccueil.setVisible(false);

        // Créer et afficher la vue recherche
        VueRecherche vueRecherche = new VueRecherche();
        new ControleurRecherche(vueRecherche, client, admin); // Si vous avez un contrôleur dédié
        vueRecherche.setVisible(true);
    }
}