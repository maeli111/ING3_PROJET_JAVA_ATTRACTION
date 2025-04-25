package Controleur;

import Vue.*;
import DAO.*;
import Modele.*;

import javax.swing.*;

public class ControleurInscription {
    private VueInscription vue;
    private DaoFactory daoFactory;
    private ClientDao clientDao;

    public ControleurInscription(VueInscription vue) {
        this.vue = vue;
        this.daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");
        this.clientDao = new ClientDao(daoFactory);

        initControl();
    }

    private void initControl() {
        vue.btnCompte.addActionListener(e -> {
            vue.dispose();
            VueLogin vueLogin = new VueLogin();
            new ControleurLogin(vueLogin);
            vueLogin.setVisible(true);
        });

        vue.btnRegister.addActionListener(e -> {
            String nom = vue.txtNom.getText();
            String prenom = vue.txtPrenom.getText();
            String email = vue.txtEmail.getText();
            String mdp = new String(vue.txtMdp.getPassword());
            String mdpVerif = new String(vue.txtMdpVerification.getPassword());

            if (!mdp.equals(mdpVerif)) {
                JOptionPane.showMessageDialog(null, "Les mots de passe ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int age;
            try {
                age = Integer.parseInt(vue.txtAge.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "L'âge doit être un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (clientDao.emailExiste(email)) {
                JOptionPane.showMessageDialog(null, "Cet e-mail est déjà utilisé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Client client = new Client();
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setEmail(email);
            client.setMdp(mdp);
            client.setAge(age);

            clientDao.inscrire(client);

            JOptionPane.showMessageDialog(null, "Inscription réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);

            vue.dispose();
            VueClient vueClient = new VueClient(client);
            new ControleurClient(vueClient, client);
            vueClient.setVisible(true);
        });
    }
}
