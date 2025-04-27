package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class VueInscription extends JFrame {
    public JTextField txtNom, txtPrenom, txtAge, txtEmail;
    public JPasswordField txtMdp, txtMdpVerification;
    public JButton btnRegister, btnCompte;

    public VueInscription() {
        setTitle("Inscription");
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
            BufferedImage image2 = ImageIO.read(new File("images/droite.png"));
            Image scaledImg2 = image2.getScaledInstance(170, 170, Image.SCALE_SMOOTH);
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

        JLabel bienvenuLabel = new JLabel("Inscription", JLabel.CENTER);
        bienvenuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bienvenuLabel.setFont(new Font("Bodoni MT", Font.BOLD, 20));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(bienvenuLabel);


        // formulaire
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // police utilisée pour tout le texte
        Font labelPolice = new Font("Bodoni MT", Font.PLAIN, 18);

        txtNom = new JTextField();
        txtPrenom = new JTextField();
        txtAge = new JTextField();
        txtEmail = new JTextField();
        txtMdp = new JPasswordField();
        txtMdpVerification = new JPasswordField();

        // texte avec la police définie
        JLabel lblNom = new JLabel("Nom :");
        lblNom.setFont(labelPolice);
        JLabel lblPrenom = new JLabel("Prénom :");
        lblPrenom.setFont(labelPolice);
        JLabel lblAge = new JLabel("Age :");
        lblAge.setFont(labelPolice);
        JLabel lblEmail = new JLabel("E-mail :");
        lblEmail.setFont(labelPolice);
        JLabel lblMdp = new JLabel("Mot de passe :");
        lblMdp.setFont(labelPolice);
        JLabel lblMdpVerif = new JLabel("Vérifier mot de passe :");
        lblMdpVerif.setFont(labelPolice);

        formPanel.add(lblNom);
        formPanel.add(txtNom);
        formPanel.add(lblPrenom);
        formPanel.add(txtPrenom);
        formPanel.add(lblAge);
        formPanel.add(txtAge);
        formPanel.add(lblEmail);
        formPanel.add(txtEmail);
        formPanel.add(lblMdp);
        formPanel.add(txtMdp);
        formPanel.add(lblMdpVerif);
        formPanel.add(txtMdpVerification);

        mainPanel.add(formPanel);

        // bouton inscription
        btnRegister = new JButton("S'inscrire");
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.setPreferredSize(new Dimension(120, 35));
        mainPanel.add(btnRegister);

        mainContainer.add(mainPanel);
        add(mainContainer, BorderLayout.CENTER);

        // couleurs utilisés pour le survol de boutons
        Color hoverColor = new Color(255, 182, 193);
        Color defaultColor = UIManager.getColor("Button.background");

        // survol pour tous les boutons
        applyHoverEffect(btnCompte, hoverColor, defaultColor);
        applyHoverEffect(btnRegister, hoverColor, defaultColor);
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

}