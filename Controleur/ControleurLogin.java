package Controleur;

import Vue.*;

public class ControleurLogin {
    private VueLogin vue;

    public ControleurLogin(VueLogin vueLogin) {
        this.vue = vueLogin;

        //Bouton pour aller sur VueConnexionClient (pour se connecter en tant que client)
        vue.getBtnConnect().addActionListener(e -> {
            vue.dispose();
            VueConnexionClient vueConnexionClient = new VueConnexionClient();
            new ControleurConnexionClient(vueConnexionClient);
            vueConnexionClient.setVisible(true);
        });

        //Bouton pour aller sur VueInscription (pour s'inscrire)
        vue.getBtnRegister().addActionListener(e -> {
            vue.dispose();
            VueInscription vueInscription = new VueInscription();
            new ControleurInscription(vueInscription);
            vueInscription.setVisible(true);
        });

        //Bouton pour aller sur Accueil en tant qu'invité
        vue.getBtnGuest().addActionListener(e -> {
            vue.dispose();
            VueAccueil vueAccueil = new VueAccueil(null,null);
            new ControleurAccueil(vueAccueil,null, null);
            vueAccueil.setVisible(true);
        });

        //Bouton pour aller sur VueConnexionAdmin (pour se connecter en tant qu'admin)
        vue.getBtnAdmin().addActionListener(e -> {
            vue.dispose();
            VueConnexionAdmin vueConnexionAdmin = new VueConnexionAdmin();
            new ControleurConnexionAdmin(vueConnexionAdmin);
            vueConnexionAdmin.setVisible(true);
        });
    }
}
