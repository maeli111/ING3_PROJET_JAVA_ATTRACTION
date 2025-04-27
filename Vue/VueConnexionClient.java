package Vue;

import Controleur.ControleurConnexionAdmin;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class VueConnexionClient extends JFrame {
    private JTextField emailField;
    private JPasswordField mdpField;
    private JButton btnConnexion;
    private JButton btnCompte;

    public VueConnexionClient() {
        setTitle("Connexion Client");
        setSize(1250, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));


        // header de la page avec le bouton d'acces au compte
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCompte = new JButton("Compte");
        rightPanel.add(btnCompte);
        topPanel.add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);


        // contenu de la page
        JPanel mainContainer = new JPanel(new GridBagLayout());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // image
        JPanel images = new JPanel(new GridLayout(1, 3, 20, 0));
        try {
            BufferedImage image2 = ImageIO.read(new File("images/gauche.png"));
            Image scaledImg2 = image2.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel img2 = new JLabel(new ImageIcon(scaledImg2));
            images.add(img2);
        } catch (Exception e) { System.out.println("Erreur image 2 : " + e.getMessage()); }

        // ajout de l'image + centrage
        images.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(images);

        // titre de notre parc + texte
        JLabel titreLabel = new JLabel("PalasiLand", JLabel.CENTER);
        titreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titreLabel.setFont(new Font("Bodoni MT", Font.BOLD, 28));
        mainPanel.add(titreLabel);

        JLabel bienvenuLabel = new JLabel("Se connecter en tant que client", JLabel.CENTER);
        bienvenuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bienvenuLabel.setFont(new Font("Bodoni MT", Font.BOLD, 20));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(bienvenuLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        formPanel.setMaximumSize(new Dimension(400, 100));

        // police utilisée pour tout le texte
        Font labelPolice = new Font("Bodoni MT", Font.PLAIN, 18);

        // case a remplir pour l'email
        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setFont(labelPolice);
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(250, 30));

        // case a remplir pour le mdp
        JLabel mdpLabel = new JLabel("Mot de passe :");
        mdpLabel.setFont(labelPolice);
        mdpField = new JPasswordField();
        mdpField.setPreferredSize(new Dimension(250, 30));

        // ajoute a la page tous les composants
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(mdpLabel);
        formPanel.add(mdpField);
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // bouton pour se connecter en tant que client
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        btnConnexion = new JButton("Connexion");
        btnConnexion.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConnexion.setPreferredSize(new Dimension(120, 35));
        mainPanel.add(btnConnexion);

        mainContainer.add(mainPanel);
        add(mainContainer, BorderLayout.CENTER);

        // couleurs utilisés pour le survol de boutons
        Color hoverColor = new Color(255, 182, 193);
        Color defaultColor = UIManager.getColor("Button.background");

        // survol pour tous les boutons
        applyHoverEffect(btnCompte, hoverColor, defaultColor);
        applyHoverEffect(btnConnexion, hoverColor, defaultColor);
    }
    private void applyHoverEffect(JButton button, Color hoverColor, Color defaultColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(defaultColor);
            }
        });
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