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

    public VueInfoAttraction(Attraction attraction, LocalDate date, Client client, Admin admin) {
        setTitle("Informations de l'attraction");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // === HEADER ===
        JTextField parc = new JTextField("Palasi Land");
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

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel dateLabel = new JLabel("Date : " + date);
        JLabel nomLabel = new JLabel("Nom : " + attraction.getNom());
        JLabel descLabel = new JLabel("Description : " + attraction.getDescription());
        JLabel prixLabel = new JLabel("Prix : " + attraction.getPrix() + " €");
        JLabel capaciteLabel = new JLabel("Capacité : " + attraction.getCapacite());

        Font font = new Font("SansSerif", Font.PLAIN, 16);
        for (JLabel label : new JLabel[]{nomLabel, descLabel, prixLabel, capaciteLabel, dateLabel}) {
            label.setFont(font);
            infoPanel.add(label);
        }

        mainPanel.add(infoPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        reserverBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        reserverBtn.setBackground(new Color(0, 150, 0));
        reserverBtn.setForeground(Color.WHITE);
        reserverBtn.setPreferredSize(new Dimension(200, 50));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(reserverBtn);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    public JButton getAccueil() { return accueil; }
    public JButton getInformations() { return informations; }
    public JButton getCalendrier() { return calendrier; }
    public JButton getCompte() { return compte; }
}
