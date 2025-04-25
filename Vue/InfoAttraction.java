package Vue;

import Controleur.ControleurReservation;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.time.LocalDate;

public class InfoAttraction extends JFrame {
    // Boutons en haut de la page
    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");

    // Nom du parc
    private JTextField parc = new JTextField("Palasi Land");

    public InfoAttraction(Attraction attraction, LocalDate date) {
        setTitle("Informations de l'attraction");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === HEADER ===
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
        Pcompte.add(compte);

        Pbarre.add(Pnavigation, BorderLayout.WEST);
        Pbarre.add(Pcompte, BorderLayout.EAST);

        JPanel header = new JPanel(new BorderLayout());
        header.add(Pbarre, BorderLayout.NORTH);
        header.add(parc, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        // === CONTENU PRINCIPAL ===
        JPanel mainPanel = new JPanel(new BorderLayout());

        // PHOTO à gauche
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

        // INFOS à droite
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel dateLabel = new JLabel("Date : " + date);
        JLabel nomLabel = new JLabel("Nom : " + attraction.getNom());
        JLabel descLabel = new JLabel("Description : " + attraction.getDescription());
        JLabel prixLabel = new JLabel("Prix : " + attraction.getPrix() + " €");
        JLabel capaciteLabel = new JLabel("Capacité : " + attraction.getCapacite());
        //JLabel reservationLabel = new JLabel("Réservations : " + attraction.getNbReservation());

        Font font = new Font("SansSerif", Font.PLAIN, 16);
        for (JLabel label : new JLabel[]{nomLabel, descLabel, prixLabel, capaciteLabel, dateLabel}) {
            label.setFont(font);
            infoPanel.add(label);
        }

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // === BOUTON RÉSERVER ===
        JButton reserverBtn = new JButton("Réserver");
        reserverBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        reserverBtn.setBackground(new Color(0, 150, 0)); // Vert
        reserverBtn.setForeground(Color.WHITE);
        reserverBtn.setPreferredSize(new Dimension(200, 50));

        reserverBtn.addActionListener(e -> {
            this.dispose(); // Ferme la fenêtre actuelle
            VueReservation view = new VueReservation();
            new ControleurReservation(view, attraction, date);
        });

        // Panel pour le bouton (centré en bas)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(reserverBtn);

        // Ajout au layout principal
        add(buttonPanel, BorderLayout.SOUTH);


        setVisible(true);
    }
}
