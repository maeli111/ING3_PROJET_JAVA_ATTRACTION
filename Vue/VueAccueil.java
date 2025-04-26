package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;
import Modele.*;

public class VueAccueil extends JFrame {

    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");
    private JButton infos = new JButton("Plus d'informations");

    private JTextField parc = new JTextField("Palasi Land");

    // Labels pour l'attraction du mois
    private JLabel attractionTitre = new JLabel("Attraction du mois");
    private JLabel attractionNom = new JLabel("Nom : ");
    private JLabel attractionDescription = new JLabel("Description : ");
    private JLabel attractionPrix = new JLabel("Prix : ");
    private JLabel attractionCapacite = new JLabel("Capacité : ");
    private JLabel attractionImageLabel = new JLabel(); // Pour l'image de l'attraction

    public VueAccueil(Client client, Admin admin) {
        setTitle("Accueil");
        setSize(1250, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Couleurs
        Color hoverColor = new Color(255, 182, 193); // Rose clair
        Color defaultColor = UIManager.getColor("Button.background"); // Couleur par défaut des boutons

        // Appliquer le style de survol à tous les boutons
        applyHoverEffect(accueil, hoverColor, defaultColor);
        applyHoverEffect(informations, hoverColor, defaultColor);
        applyHoverEffect(calendrier, hoverColor, defaultColor);
        applyHoverEffect(compte, hoverColor, defaultColor);
        applyHoverEffect(infos, hoverColor, defaultColor);

        accueil.setBackground(new Color(255, 182, 193));
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

        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel images = new JPanel(new GridLayout(1, 3, 20, 0));

        try {
            BufferedImage image1 = ImageIO.read(new File("C:\\wamp64\\www\\ING3_PROJET_JAVA_ATTRACTION\\Images\\gauche.png"));
            Image scaledImg1 = image1.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel img1 = new JLabel(new ImageIcon(scaledImg1));
            images.add(img1);
        } catch (Exception e) { System.out.println("Erreur image 1 : " + e.getMessage()); }

        try {
            BufferedImage image2 = ImageIO.read(new File("C:\\wamp64\\www\\ING3_PROJET_JAVA_ATTRACTION\\Images\\milieu.png"));
            Image scaledImg2 = image2.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel img2 = new JLabel(new ImageIcon(scaledImg2));
            images.add(img2);
        } catch (Exception e) { System.out.println("Erreur image 2 : " + e.getMessage()); }

        try {
            BufferedImage image3 = ImageIO.read(new File("C:\\wamp64\\www\\ING3_PROJET_JAVA_ATTRACTION\\Images\\droite.png"));
            Image scaledImg3 = image3.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel img3 = new JLabel(new ImageIcon(scaledImg3));
            images.add(img3);
        } catch (Exception e) { System.out.println("Erreur image 3 : " + e.getMessage()); }

        contenu.add(images);

        JLabel description = new JLabel(
                "<html><div style='text-align: center;'>"
                        + "Entrez dans l'univers magique de <b>Palasi Land</b> <br>"
                        + "là où les légendes anciennes prennent vie et où les dieux vous entraînent dans des aventures extraordinaires !<br><br>"
                        + "Que vous soyez prêt à défier les titans dans des montagnes russes vertigineuses,<br>"
                        + "plonger dans les profondeurs des océans mystiques ou voyager à travers des terres enchantées,<br><br>"
                        + "<b>Palasi Land</b> vous offrira des expériences mythologiques<br>"
                        + "pour tous les courageux aventuriers et aventurières, petits et grands !<br><br>"
                        + "Venez découvrir et percer les secrets de vos héros préférés,<br>"
                        + "rencontrer des créatures légendaires, passer un moment inoubliable avec des dieux antiques<br>"
                        + "dans un parc où chaque attraction vous plonge au cœur d'un conte épique !"
                        + "</div></html>",
                SwingConstants.CENTER
        );
        description.setFont(new Font("Bodoni MT", Font.PLAIN, 18));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contenu.add(description);

        // Ajouter un JPanel avec FlowLayout pour centrer le bouton
        JPanel panelInfos = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInfos.add(infos);
        contenu.add(Box.createRigidArea(new Dimension(0, 20)));
        contenu.add(panelInfos);
        contenu.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Section "Attraction du mois" ---
        JPanel attractionMois = new JPanel();
        attractionMois.setLayout(new BoxLayout(attractionMois, BoxLayout.Y_AXIS));
        attractionMois.setBorder(BorderFactory.createTitledBorder("Attraction du mois"));

        attractionTitre.setFont(new Font("Bodoni MT", Font.BOLD, 24));
        attractionTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
        attractionMois.add(attractionTitre);
        attractionMois.add(Box.createRigidArea(new Dimension(0, 10)));

        // Panel pour l'image de l'attraction
        JPanel imagePanel = new JPanel();
        attractionImageLabel.setPreferredSize(new Dimension(200, 200));
        imagePanel.add(attractionImageLabel);
        attractionMois.add(imagePanel);
        attractionMois.add(Box.createRigidArea(new Dimension(0, 10)));

        attractionNom.setFont(new Font("Bodoni MT", Font.PLAIN, 18));
        attractionDescription.setFont(new Font("Bodoni MT", Font.PLAIN, 18));
        attractionPrix.setFont(new Font("Bodoni MT", Font.PLAIN, 18));
        attractionCapacite.setFont(new Font("Bodoni MT", Font.PLAIN, 18));

        attractionMois.add(attractionNom);
        attractionMois.add(attractionDescription);
        attractionMois.add(attractionPrix);
        attractionMois.add(attractionCapacite);

        contenu.add(attractionMois);
        // -------------------------------------

        JScrollPane scrollPane = new JScrollPane(contenu);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Méthode pour appliquer l'effet de survol à un bouton
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

    // Getters pour le contrôleur
    public JButton getAccueil() { return accueil; }
    public JButton getInformations() { return informations; }
    public JButton getCalendrier() { return calendrier; }
    public JButton getCompte() { return compte; }
    public JButton getInfos() { return infos; }

    public void afficherAttractionDuMois(Attraction attraction) {
        attractionNom.setText("Nom : " + attraction.getNom());
        attractionDescription.setText("<html>Description :<br>" + attraction.getDescription() + "</html>");
        attractionPrix.setText("Prix : " + attraction.getPrix() + "€");
        attractionCapacite.setText("Capacité : " + attraction.getCapacite() + " personnes");

        // Charger et afficher l'image de l'attraction
        try {
            String imagePath = "C:\\wamp64\\www\\ING3_PROJET_JAVA_ATTRACTION\\Images\\" + attraction.getId_attraction() + ".jpg";
            BufferedImage image = ImageIO.read(new File(imagePath));
            Image scaledImg = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            attractionImageLabel.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image de l'attraction: " + e.getMessage());
            attractionImageLabel.setIcon(null); // Pas d'image si erreur
        }
    }
}