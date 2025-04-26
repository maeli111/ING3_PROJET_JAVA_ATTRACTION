package Vue;

import Modele.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VueReservationClient extends JFrame {
    // === NAVIGATION & HEADER ===
    public JButton accueil = new JButton("Accueil");
    public JButton informations = new JButton("Informations");
    public JButton calendrier = new JButton("Calendrier");
    public JButton compte = new JButton("Compte");
    public JTextField parc = new JTextField("Palasi Land");
    public JPanel header = new JPanel(new BorderLayout());
    public JLabel titreResa = new JLabel();

    // === FORMULAIRE PRINCIPAL ===
    public JPanel formPanel = new JPanel();

    // === FORM CLIENTS ===
    public JPanel formClientExistant = new JPanel();

    // === Champs Client Existant ===
    public JTextField nbAdultesField = new JTextField("0", 2);
    public JTextField nbEnfantsField = new JTextField("0", 2);
    public JTextField nbEtudiantsField = new JTextField("0", 2);
    public JTextField nbSeniorsField = new JTextField("0", 2);
    public JTextField nbFamField = new JTextField("0", 2);
    public JTextField nbFamNbField = new JTextField("0", 2);
    public JTextField nbEnfantsFamNbField = new JTextField("3", 2);

    public JButton plusBtnAdultes = new JButton("+");
    public JButton moinsBtnAdultes = new JButton("-");
    public JButton plusBtnEnfants = new JButton("+");
    public JButton moinsBtnEnfants = new JButton("-");
    public JButton plusBtnEtudiants = new JButton("+");
    public JButton moinsBtnEtudiants = new JButton("-");
    public JButton plusBtnSeniors = new JButton("+");
    public JButton moinsBtnSeniors = new JButton("-");
    public JButton plusBtnFam = new JButton("+");
    public JButton moinsBtnFam = new JButton("-");
    public JButton plusBtnFamNb = new JButton("+");
    public JButton moinsBtnFamNb = new JButton("-");
    public JButton plusBtnEnfantsFamNb = new JButton("+");
    public JButton moinsBtnEnfantsFamNb = new JButton("-");

    public JLabel prixLabelExistant = new JLabel();

    // === BOUTONS "?" POUR INFO RÉDUCTION ===
    public JButton infoBtnEnfant = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnEtudiant = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnSenior = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnFam = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnFamNb = createImageButton("images/ptInterrogation.png", 15);

    // === BOUTON DE RÉSERVATION ===
    public JButton reserverButton = new JButton("Valider la réservation");

    public VueReservationClient(Client client) {
        setTitle("Formulaire de Réservation");
        setSize(1250, 680);
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
        Pcompte.add(compte);
        Pbarre.add(Pnavigation, BorderLayout.WEST);
        Pbarre.add(Pcompte, BorderLayout.EAST);

        // Titre "Réserver l'attraction ..."
        titreResa.setFont(new Font("SansSerif", Font.BOLD, 16));
        titreResa.setHorizontalAlignment(SwingConstants.CENTER);

        // Organisation verticale
        JPanel headerCenter = new JPanel();
        headerCenter.setLayout(new BoxLayout(headerCenter, BoxLayout.Y_AXIS));
        headerCenter.setOpaque(false);
        headerCenter.add(parc);
        headerCenter.add(Box.createRigidArea(new Dimension(0, 5)));
        headerCenter.add(titreResa);

        header.add(Pbarre, BorderLayout.NORTH);
        header.add(headerCenter, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // FORMULAIRE
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Panels client existant (contenu complété par le contrôleur)
        formPanel.add(formClientExistant);

        // Bouton réserver
        reserverButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(reserverButton);

        add(formPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public JButton createImageButton(String imagePath, int size) {
        try {
            BufferedImage img = ImageIO.read(new File(imagePath));
            Image scaled = img.getScaledInstance(size, size, Image.SCALE_SMOOTH);
            return new JButton(new ImageIcon(scaled));
        } catch (IOException e) {
            System.out.println("Erreur chargement image : " + imagePath);
            return new JButton("?");
        }
    }
}
