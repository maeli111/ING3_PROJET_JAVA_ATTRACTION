package Vue;

import Controleur.ControleurConnexionAdmin;

import javax.swing.*;
import java.awt.*;

public class VueConnexionClient extends JFrame {
    private JTextField emailField;
    private JPasswordField mdpField;
    private JButton btnConnexion;
    private JButton btnCompte;

    public VueConnexionClient() {
        setTitle("Connexion Client");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // -------- Panneau haut --------
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCompte = new JButton("Compte");
        rightPanel.add(btnCompte);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // -------- Panneau principal --------
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        JLabel titreLabel = new JLabel("PalasiLand", JLabel.CENTER);
        titreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titreLabel.setFont(new Font("Serif", Font.BOLD, 28));
        mainPanel.add(titreLabel);

        JLabel bienvenuLabel = new JLabel("Se connecter en tant que client", JLabel.CENTER);
        bienvenuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bienvenuLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(bienvenuLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formPanel.setMaximumSize(new Dimension(400, 80));

        JLabel emailLabel = new JLabel("Email :");
        emailField = new JTextField();

        JLabel mdpLabel = new JLabel("Mot de passe :");
        mdpField = new JPasswordField();

        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(mdpLabel);
        formPanel.add(mdpField);
        mainPanel.add(formPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        btnConnexion = new JButton("Connexion");
        btnConnexion.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(btnConnexion);

        // -------- Ajout aux panels --------
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

    }

    // Accesseurs pour le contrôleur
    public JButton getBtnConnexion() {
        return btnConnexion;
    }

    public JButton getBtnCompte() {
        return btnCompte;
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getMotDePasse() {
        return new String(mdpField.getPassword());
    }
}
