package Controleur;

import DAO.AdminDao;
import DAO.DaoFactory;
import Modele.Admin;
import Vue.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControleurConnexionAdmin {
    private VueConnexionAdmin vue;
    private DaoFactory daoFactory;
    private AdminDao adminDao;

    public ControleurConnexionAdmin(VueConnexionAdmin vue) {
        this.vue = vue;
        this.daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");
        this.adminDao = new AdminDao(daoFactory);

        initControl();
    }

    private void initControl() {
        vue.btnCompte.addActionListener(e -> {
            vue.dispose();
            new VueLogin().setVisible(true);
        });

        vue.btnConnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = vue.emailField.getText().trim();
                String mdp = new String(vue.mdpField.getPassword());

                if (email.isEmpty() || mdp.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Admin adminConnecte = adminDao.connexionAdmin(email, mdp);

                if (adminConnecte != null) {
                    JOptionPane.showMessageDialog(null, "Connexion réussie ! Bienvenue, " + adminConnecte.getPrenom() + " " + adminConnecte.getNom(), "Succès", JOptionPane.INFORMATION_MESSAGE);
                    vue.dispose();
                    VueAdmin vueAdmin = new VueAdmin(adminConnecte);
                    new ControleurAdmin(vueAdmin, adminConnecte);
                    vueAdmin.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Email ou mot de passe incorrect.", "Échec de connexion", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
