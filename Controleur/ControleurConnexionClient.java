package Controleur;

import DAO.ClientDao;
import DAO.DaoFactory;
import Modele.Client;
import Vue.*;

import javax.swing.*;

public class ControleurConnexionClient {
    private VueConnexionClient vue;

    //Constructeur
    public ControleurConnexionClient(VueConnexionClient vueConnexionClient) {
        this.vue = vueConnexionClient;
        DaoFactory daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");
        ClientDao clientDao = new ClientDao(daoFactory);

        // Bouton qui permet de se connecter en tant que client
        vue.getBtnConnexion().addActionListener(e -> {
            String email = vue.getEmail();
            String mdp = vue.getMotDePasse();

            if (email.isEmpty() || mdp.isEmpty()) {
                JOptionPane.showMessageDialog(vue, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Client clientConnecte = clientDao.seConnecter(email, mdp);
            if (clientConnecte != null) {
                JOptionPane.showMessageDialog(vue, "Connexion réussie ! Bienvenue, " + clientConnecte.getPrenom() + " " + clientConnecte.getNom(), "Succès", JOptionPane.INFORMATION_MESSAGE);
                VueClient vueClient = new VueClient(clientConnecte);
                new ControleurClient(vueClient, clientConnecte);
                vueClient.setVisible(true);
                vue.dispose();
            } else {
                JOptionPane.showMessageDialog(vue, "Email ou mot de passe incorrect.", "Échec de connexion", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Bouton qui fait un retour à login
        vue.getBtnCompte().addActionListener(e -> {
            vue.dispose();
            VueLogin vueLogin = new VueLogin();
            new ControleurLogin(vueLogin);
            vueLogin.setVisible(true);
        });
    }
}
