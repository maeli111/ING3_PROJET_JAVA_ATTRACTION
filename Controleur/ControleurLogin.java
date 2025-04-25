package Controleur;

import Vue.*;

public class ControleurLogin {
    private VueLogin vue;

    public ControleurLogin(VueLogin vueLogin) {
        this.vue = vueLogin;

        vue.getBtnConnect().addActionListener(e -> {
            vue.dispose();
            VueConnexionClient vueConnexionClient = new VueConnexionClient();
            new ControleurConnexionClient(vueConnexionClient);
            vueConnexionClient.setVisible(true);
        });

        vue.getBtnRegister().addActionListener(e -> {
            vue.dispose();
            VueInscription vueInscription = new VueInscription();
            new ControleurInscription(vueInscription);
            vueInscription.setVisible(true);
        });

        vue.getBtnGuest().addActionListener(e -> {
            vue.dispose();
            VueAccueil vueAccueil = new VueAccueil(null,null);
            new ControleurAccueil(vueAccueil,null, null);
            vueAccueil.setVisible(true);
        });

        vue.getBtnAdmin().addActionListener(e -> {
            vue.dispose();
            VueConnexionAdmin vueConnexionAdmin = new VueConnexionAdmin();
            new ControleurConnexionAdmin(vueConnexionAdmin);
            vueConnexionAdmin.setVisible(true);
        });
    }
}
