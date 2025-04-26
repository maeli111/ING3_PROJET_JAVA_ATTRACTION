package Vue;

import Modele.Attraction;
import Modele.Client;
import Modele.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDate;
import javax.imageio.ImageIO;

public class VueInfoAttraction extends JFrame {
    // Boutons accessibles par le contrôleur
    public JButton accueil = new JButton("Accueil");
    public JButton informations = new JButton("Informations");
    public JButton calendrier = new JButton("Calendrier");
    public JButton compte = new JButton("Compte");
    public JButton reserverBtn = new JButton("Réserver");

    // Couleurs harmonisées
    private final Color ROSE_PRINCIPAL = new Color(255, 105, 180);
    private final Color ROSE_FONCE = new Color(255, 20, 147);
    private final Color ROSE_SURVOL = new Color(255, 182, 193); // Rose clair pour le survol des boutons de navigation

    public VueInfoAttraction(Attraction attraction, LocalDate date, Client client, Admin admin) {
        setTitle("Informations de l'attraction");
        setSize(1250, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 240)); // Fond gris très clair

        // === HEADER ===
        JTextField parc = new JTextField("Palasi Land");
        parc.setHorizontalAlignment(JTextField.CENTER);
        parc.setEditable(false);
        parc.setFont(new Font("Bodoni MT", Font.BOLD, 32));
        parc.setBorder(null);
        parc.setOpaque(false);

        // Appliquer l'effet de survol aux boutons de navigation
        applyHoverEffect(accueil, ROSE_SURVOL);
        applyHoverEffect(informations, ROSE_SURVOL);
        applyHoverEffect(calendrier, ROSE_SURVOL);
        applyHoverEffect(compte, ROSE_SURVOL);

        // Mettre en surbrillance le bouton Informations
        informations.setBackground(ROSE_SURVOL);

        JPanel Pbarre = new JPanel(new BorderLayout());
        Pbarre.setOpaque(false);

        JPanel Pnavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        Pnavigation.setOpaque(false);
        Pnavigation.add(accueil);
        Pnavigation.add(informations);
        Pnavigation.add(calendrier);

        JPanel Pcompte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        Pcompte.setOpaque(false);
        Pcompte.add(compte);

        Pbarre.add(Pnavigation, BorderLayout.WEST);
        Pbarre.add(Pcompte, BorderLayout.EAST);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(Pbarre, BorderLayout.NORTH);
        header.add(parc, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // === CONTENU PRINCIPAL ===
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

        // Informations
        Font infoFont = new Font("Bodoni MT", Font.PLAIN, 16);
        Color infoColor = new Color(70, 70, 70);

        gbc.gridy++;
        addInfoLabel(infoPanel, "Date :", date.toString(), infoFont, infoColor, gbc);

        gbc.gridy++;
        addInfoLabel(infoPanel, "Description :", attraction.getDescription(), infoFont, infoColor, gbc);

        gbc.gridy++;
        addInfoLabel(infoPanel, "Prix :", attraction.getPrix() + " €", infoFont, infoColor, gbc);

        gbc.gridy++;
        addInfoLabel(infoPanel, "Capacité :", String.valueOf(attraction.getCapacite()), infoFont, infoColor, gbc);

        mainPanel.add(infoPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // === BOUTON RÉSERVER ===
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        buttonPanel.setOpaque(false);

        reserverBtn.setFont(new Font("Bodoni MT", Font.BOLD, 18));
        reserverBtn.setBackground(ROSE_PRINCIPAL);
        reserverBtn.setForeground(Color.WHITE);
        reserverBtn.setPreferredSize(new Dimension(150, 50));
        reserverBtn.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        reserverBtn.setFocusPainted(false);

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

    public JButton getAccueil() { return accueil; }
    public JButton getInformations() { return informations; }
    public JButton getCalendrier() { return calendrier; }
    public JButton getCompte() { return compte; }
    public JButton getReserverBtn() { return reserverBtn; }
}