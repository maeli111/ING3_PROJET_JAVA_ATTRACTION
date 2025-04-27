package Vue;

import Modele.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VueReservationClient extends JFrame {
    // header
    public JButton accueil = new JButton("Accueil");
    public JButton informations = new JButton("Informations");
    public JButton calendrier = new JButton("Calendrier");
    public JButton compte = new JButton("Compte");
    public JTextField parc = new JTextField("Palasi Land");
    public JPanel header = new JPanel(new BorderLayout());
    public JLabel titreResa = new JLabel();

    // formulaire principal
    public JPanel formPanel = new JPanel();

    // formulaire client
    public JPanel formClientExistant = new JPanel();

    // champs pour un client existant
    public JTextField nbAdultesField = new JTextField("0", 2);
    public JTextField nbEnfantsField = new JTextField("0", 2);
    public JTextField nbEtudiantsField = new JTextField("0", 2);
    public JTextField nbSeniorsField = new JTextField("0", 2);
    public JTextField nbFamField = new JTextField("0", 2);
    public JTextField nbFamNbField = new JTextField("0", 2);
    public JTextField nbEnfantsFamNbField = new JTextField("3", 2);

    // Boutons + et - pour ajuster le nombre de places
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

    // boutons "?" avec les description des réductions
    public JButton infoBtnEnfant = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnEtudiant = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnSenior = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnFam = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnFamNb = createImageButton("images/ptInterrogation.png", 15);

    // bouton de réservation
    public JButton reserverButton = new JButton("Valider la réservation");

    // constructeur
    public VueReservationClient(Client client) {
        // config de la fenêtre
        setTitle("Formulaire de Réservation");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // header
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

        // titre
        titreResa.setFont(new Font("SansSerif", Font.BOLD, 16));
        titreResa.setHorizontalAlignment(SwingConstants.CENTER);

        // organisation de la page
        JPanel headerCenter = new JPanel();
        headerCenter.setLayout(new BoxLayout(headerCenter, BoxLayout.Y_AXIS));
        headerCenter.setOpaque(false);
        headerCenter.add(parc);
        headerCenter.add(Box.createRigidArea(new Dimension(0, 5)));
        headerCenter.add(titreResa);

        header.add(Pbarre, BorderLayout.NORTH);
        header.add(headerCenter, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // formulaire
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Panels client existant
        formPanel.add(formClientExistant);

        // bouton valider la réservation
        reserverButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(reserverButton);

        add(formPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // création d'un bouton avec une image
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