package Controleur;

import Vue.*;
import DAO.*;
import Modele.*;

public class ControleurAccueil {
    private VueAccueil vueAccueil;

    public ControleurAccueil(VueAccueil vue) {
        this.vueAccueil = vue;

        vueAccueil.getLoupeBtn().addActionListener(e -> ouvrirRecherche());

    }

    private void ouvrirRecherche() {
        // Fermer la vue actuelle si nécessaire (optionnel)
        vueAccueil.setVisible(false);

        // Créer et afficher la vue recherche
        VueRecherche vueRecherche = new VueRecherche();
        new ControleurRecherche(vueRecherche); // Si vous avez un contrôleur dédié
        vueRecherche.setVisible(true);
    }
}