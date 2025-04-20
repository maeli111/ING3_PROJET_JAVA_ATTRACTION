package Vue;

import DAO.AdminDao;
import DAO.DaoFactory;
import Modele.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VueConnexionAdmin extends JFrame {
    private DaoFactory daoFactory;

    public VueConnexionAdmin() {
        // Connexion à la base de données
        daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");

        // Création du DAO
        AdminDao adminDao = new AdminDao(daoFactory);

        // Paramètres de la fenêtre
        setTitle("Connexion Admin");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panneau supérieur avec boutons
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(new JButton("Accueil"));
        leftPanel.add(new JButton("Informations"));
        leftPanel.add(new JButton("Calendrier"));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(new JButton("Compte"));

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // Panneau principal (contenu central)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20)); // plus de marge en haut

        // Titre PalasiLand
        JLabel titreLabel = new JLabel("PalasiLand", JLabel.CENTER);
        titreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titreLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titreLabel.setForeground(Color.BLACK); // pas en bleu
        mainPanel.add(titreLabel);

        // Texte de bienvenue
        JLabel bienvenuLabel = new JLabel("Se connecter en tant qu'administrateur", JLabel.CENTER);
        bienvenuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bienvenuLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // petit espace entre le titre et le texte
        mainPanel.add(bienvenuLabel);

        // Formulaire
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // espace après le texte
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setMaximumSize(new Dimension(400, 80));

        JLabel emailLabel = new JLabel("Email :");
        JTextField emailField = new JTextField();

        JLabel mdpLabel = new JLabel("Mot de passe :");
        JPasswordField mdpField = new JPasswordField();

        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(mdpLabel);
        formPanel.add(mdpField);
        mainPanel.add(formPanel);

        // Bouton de connexion
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JButton btnConnexion = new JButton("Connexion");
        btnConnexion.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(btnConnexion);

        // Ajout des panels à la fenêtre
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // 🎯 Action sur le bouton Connexion
        btnConnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String mdp = new String(mdpField.getPassword());

                if (email.isEmpty() || mdp.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Admin adminConnecte = adminDao.connexionAdmin(email, mdp);

                if (adminConnecte != null) {
                    JOptionPane.showMessageDialog(null, "Connexion réussie ! Bienvenue, " + adminConnecte.getPrenom() + " " + adminConnecte.getNom(), "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    VueAdmin adminPage = new VueAdmin(adminConnecte);
                    adminPage.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Email ou mot de passe incorrect.", "Échec de connexion", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Affichage
        setVisible(true);
    }
}
