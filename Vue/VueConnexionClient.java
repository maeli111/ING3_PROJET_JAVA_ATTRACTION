package Vue;

import DAO.ClientDao;
import DAO.DaoFactory;
import Modele.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VueConnexionClient extends JFrame {
    private DaoFactory daoFactory;

    public VueConnexionClient() {
        // Connexion à la base de données
        daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");

        // Création du DAO
        ClientDao clientDao = new ClientDao(daoFactory);

        // Paramètres de la fenêtre
        setTitle("Connexion Client");
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
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel bienvenuLabel = new JLabel("Se connecter en tant que client", JLabel.CENTER);
        bienvenuLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(bienvenuLabel);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

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
        JButton btnConnexion = new JButton("Connexion");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnConnexion);
        mainPanel.add(buttonPanel);

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

                Client clientConnecte = clientDao.seConnecter(email, mdp);

                if (clientConnecte != null) {
                    JOptionPane.showMessageDialog(null, "Connexion réussie ! Bienvenue, " + email, "Succès", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    VueClient clientPage = new VueClient(clientConnecte);
                    clientPage.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Email ou mot de passe incorrect.", "Échec de connexion", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Affichage
        setVisible(true);
    }
}
