package Vue;

import Modele.Attraction;
import Modele.Client;
import Modele.Admin;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

// VueAttraction.java

public class VueAttraction extends JFrame {
    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");
    private JButton reserverBtn; // Déclaration du bouton "Réserver"
    private JButton loupeBtn;
    private JTextField parc = new JTextField("Palasi Land");

    public VueAttraction(Attraction attraction, Client client, Admin admin) {
        setTitle("Informations de l'attraction");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // HEADER
        parc.setHorizontalAlignment(JTextField.CENTER);
        parc.setEditable(false);
        parc.setFont(new Font("Bodoni MT", Font.BOLD, 32));
        parc.setBorder(null);
        parc.setOpaque(false);

        JPanel Pbarre = new JPanel(new BorderLayout());

        JPanel Pnavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        Pnavigation.add(accueil);
        Pnavigation.add(informations);
        Pnavigation.add(calendrier);

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
        Pcompte.add(compte);

        Pbarre.add(Pnavigation, BorderLayout.WEST);
        Pbarre.add(Pcompte, BorderLayout.EAST);

        JPanel header = new JPanel(new BorderLayout());
        header.add(Pbarre, BorderLayout.NORTH);
        header.add(parc, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        // CONTENU PRINCIPAL
        JPanel mainPanel = new JPanel(new BorderLayout());

        // PHOTO
        JLabel photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(300, 300));

        try {
            BufferedImage img = ImageIO.read(new File("images/" + attraction.getNom() + ".jpg"));
            Image scaled = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            photoLabel.setText("Image non trouvée");
            photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        mainPanel.add(photoLabel, BorderLayout.WEST);

        // INFOS
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel nomLabel = new JLabel("Nom : " + attraction.getNom());
        JLabel descLabel = new JLabel("Description : " + attraction.getDescription());
        JLabel prixLabel = new JLabel("Prix : " + attraction.getPrix() + " €");
        JLabel capaciteLabel = new JLabel("Capacité : " + attraction.getCapacite());

        Font font = new Font("SansSerif", Font.PLAIN, 16);
        for (JLabel label : new JLabel[]{nomLabel, descLabel, prixLabel, capaciteLabel}) {
            label.setFont(font);
            infoPanel.add(label);
        }

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // BOUTON FAIRE UNE RÉSERVATION
        reserverBtn = new JButton("Faire une réservation");
        reserverBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        reserverBtn.setBackground(new Color(0, 150, 0)); // Vert
        reserverBtn.setForeground(Color.WHITE);
        reserverBtn.setPreferredSize(new Dimension(250, 50));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(reserverBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Getters pour les boutons à utiliser dans le contrôleur
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
