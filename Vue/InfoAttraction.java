package Vue;

import Modele.Attraction;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class InfoAttraction extends JFrame {

    public InfoAttraction(Attraction attraction) {
        setTitle("Informations de l'attraction");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal avec BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // === PHOTO (à gauche) ===
        JLabel photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(250, 250));
        try {
            File imageFile = new File("images/" + attraction.getNom() + ".jpg");

            if (!imageFile.exists()) {
                imageFile = new File("images/attraction.jpg"); // image fallback
            }

            BufferedImage img = ImageIO.read(imageFile);
            Image scaled = img.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            photoLabel.setText("Image non trouvée");
            photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        mainPanel.add(photoLabel, BorderLayout.WEST);

        // === INFOS (à droite) ===
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel nomLabel = new JLabel("Nom : " + attraction.getNom());
        JLabel descLabel = new JLabel("Description : " + attraction.getDescription());
        JLabel prixLabel = new JLabel("Prix : " + attraction.getPrix() + " €");
        JLabel capaciteLabel = new JLabel("Capacité : " + attraction.getCapacite());
        JLabel reservationLabel = new JLabel("Réservations : " + attraction.getNbReservation());

        Font font = new Font("SansSerif", Font.PLAIN, 16);
        for (JLabel label : new JLabel[]{nomLabel, descLabel, prixLabel, capaciteLabel, reservationLabel}) {
            label.setFont(font);
            infoPanel.add(label);
        }

        mainPanel.add(infoPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }
}
