package Vue;

import Modele.Attraction;
import Modele.Client;
import Modele.Admin;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

// interface pour afficher les informations d'une attraction
public class VueAttraction extends JFrame {
    // boutons du header
    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");
    private JButton reserverBtn; // Déclaration du bouton "Réserver"
    private JButton loupeBtn;
    private JTextField parc = new JTextField("Palasi Land");

    // définition des couleurs
    private final Color ROSE_PRINCIPAL = new Color(255, 105, 180);
    private final Color ROSE_FONCE = new Color(255, 20, 147);
    private final Color ROSE_SURVOL = new Color(255, 182, 193); // Rose clair pour le survol des boutons de navigation


    public VueAttraction(Attraction attraction, Client client, Admin admin) {
        // paramètres de la fenêtre
        setTitle("Informations de l'attraction");
        setSize(1250, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240)); // Fond gris très clair


        // HEADER
        parc.setHorizontalAlignment(JTextField.CENTER);
        parc.setEditable(false);
        parc.setFont(new Font("Bodoni MT", Font.BOLD, 32));
        parc.setBorder(null);
        parc.setOpaque(false);

        // appliquer l'effet de survol aux boutons de navigation
        applyHoverEffect(accueil, ROSE_SURVOL);
        applyHoverEffect(informations, ROSE_SURVOL);
        applyHoverEffect(calendrier, ROSE_SURVOL);
        applyHoverEffect(compte, ROSE_SURVOL);

        // surbrillance  pour le bouton Informations
        informations.setBackground(ROSE_SURVOL);

        JPanel Pbarre = new JPanel(new BorderLayout());
        Pbarre.setOpaque(false);

        JPanel Pnavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        Pnavigation.add(accueil);
        Pnavigation.add(informations);
        Pnavigation.add(calendrier);
        Pnavigation.setOpaque(false);

        JPanel Pcompte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        try {
            BufferedImage loupeImage = ImageIO.read(new File("images/loupe.png"));
            Image scaledLoupe = loupeImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            loupeBtn = new JButton(new ImageIcon(scaledLoupe));
            loupeBtn.setBorder(BorderFactory.createEmptyBorder());
            loupeBtn.setContentAreaFilled(false);
            Pcompte.add(loupeBtn);
        } catch (Exception e) {
            loupeBtn = new JButton("🔍");
            Pcompte.add(loupeBtn);
        }
        Pcompte.setOpaque(false);
        Pcompte.add(compte);

        Pbarre.add(Pnavigation, BorderLayout.WEST);
        Pbarre.add(Pcompte, BorderLayout.EAST);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(Pbarre, BorderLayout.NORTH);
        header.add(parc, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // CONTENU PRINCIPAL
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setOpaque(false);

        // Panel image
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setOpaque(false);
        imagePanel.setPreferredSize(new Dimension(getWidth() / 2, getHeight()));

        JLabel photoLabel = new JLabel();
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            BufferedImage img = ImageIO.read(new File("images/" + attraction.getId_attraction() + ".jpg"));
            Image scaled = img.getScaledInstance(500, 450, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            photoLabel.setText("Image non disponible");
            photoLabel.setFont(new Font("Bodoni MT", Font.ITALIC, 16));
            photoLabel.setForeground(Color.GRAY);
        }

        imagePanel.add(photoLabel, BorderLayout.CENTER);
        mainPanel.add(imagePanel, BorderLayout.WEST);


        // Panel informations
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Titre attraction
        JLabel titreAttraction = new JLabel(attraction.getNom());
        titreAttraction.setFont(new Font("Bodoni MT", Font.BOLD, 28));
        titreAttraction.setForeground(ROSE_PRINCIPAL);
        infoPanel.add(titreAttraction, gbc);

        // Informations sur l'attraction
        Font infoFont = new Font("Bodoni MT", Font.PLAIN, 16);
        Color infoColor = new Color(70, 70, 70);

        gbc.gridy++;
        addInfoLabel(infoPanel, "Description :", attraction.getDescription(), infoFont, infoColor, gbc);

        gbc.gridy++;
        addInfoLabel(infoPanel, "Prix :", attraction.getPrix() + " €", infoFont, infoColor, gbc);

        gbc.gridy++;
        addInfoLabel(infoPanel, "Capacité :", String.valueOf(attraction.getCapacite()), infoFont, infoColor, gbc);

        mainPanel.add(infoPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // BOUTON FAIRE UNE RÉSERVATION
        reserverBtn = new JButton("Réserver");
        reserverBtn.setFont(new Font("Bodoni MT", Font.BOLD, 18));
        reserverBtn.setBackground(ROSE_PRINCIPAL);
        reserverBtn.setForeground(Color.WHITE);
        reserverBtn.setPreferredSize(new Dimension(150, 50));
        reserverBtn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        reserverBtn.setFocusPainted(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        buttonPanel.setOpaque(false);

        // Effet de survol pour le bouton Réserver
        reserverBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                reserverBtn.setBackground(ROSE_FONCE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                reserverBtn.setBackground(ROSE_PRINCIPAL);
            }
        });

        buttonPanel.add(reserverBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Méthode pour appliquer l'effet de survol aux boutons de navigation
    private void applyHoverEffect(JButton button, Color hoverColor) {
        Color defaultColor = button.getBackground();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.equals(informations)) { // Ne pas changer si c'est le bouton actif
                    button.setBackground(defaultColor);
                }
            }
        });
    }

    private void addInfoLabel(JPanel panel, String label, String value, Font font, Color color, GridBagConstraints gbc) {
        JLabel lbl = new JLabel("<html><b>" + label + "</b> " + value + "</html>");
        lbl.setFont(font);
        lbl.setForeground(color);
        panel.add(lbl, gbc);
    }

    // getters pour le contrôleur
    public JButton getAccueilButton() {
        return accueil;
    }

    public JButton getInformationsButton() {
        return informations;
    }

    public JButton getCalendrierButton() {
        return calendrier;
    }

    public JButton getCompteButton() {
        return compte;
    }

    public JButton getLoupeButton() {
        return loupeBtn;
    }

    // Ajouter un getter pour le bouton "Réserver"
    public JButton getReserverButton() {
        return reserverBtn;
    }
}
