package Vue;

import Modele.Client;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// interface pour réserver une attraction
public class VueReservation extends JFrame {
    // boutons header
    public JButton accueil = new JButton("Accueil");
    public JButton informations = new JButton("Informations");
    public JButton calendrier = new JButton("Calendrier");
    public JButton compte = new JButton("Compte");
    public JTextField parc = new JTextField("Palasi Land");
    public JPanel header = new JPanel(new BorderLayout());
    public JLabel titreResa = new JLabel();

    // formulaire
    public JPanel formPanel = new JPanel();

    // boutons pour le choix du client
    public JRadioButton rbClient = new JRadioButton("Client existant");
    public JRadioButton rbInvite = new JRadioButton("Nouveau client");
    public ButtonGroup groupClientType = new ButtonGroup();

    // formulaires pour les deux types de clients (nouveau ou existant)
    public JPanel formNouveauClient = new JPanel();
    public JPanel formClientExistant = new JPanel();

    // champs à remplir dans le cas d'un nouveau client
    public JTextField nomField = new JTextField(15);
    public JTextField prenomField = new JTextField(15);
    public JTextField emailFieldNouveau = new JTextField(15);
    public JTextField nbPersonneFieldNouveau = new JTextField("0", 2);
    public JButton plusBtnNouveau = new JButton("+");
    public JButton moinsBtnNouveau = new JButton("-");
    public JLabel prixLabelNouveau = new JLabel();


    // champs à remplir dans le cas d'un client existant
    public JTextField emailFieldExistant = new JTextField(10);
    // le nombre de billets par catégorie
    public JTextField nbAdultesField = new JTextField("0", 2);
    public JTextField nbEnfantsField = new JTextField("0", 2);
    public JTextField nbEtudiantsField = new JTextField("0", 2);
    public JTextField nbSeniorsField = new JTextField("0", 2);
    public JTextField nbFamField = new JTextField("0", 2);
    public JTextField nbFamNbField = new JTextField("0", 2);
    public JTextField nbEnfantsFamNbField = new JTextField("3", 2);

    // boutons plus et moins
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

    // boutons "?" pour afficher la description des réductions
    public JButton infoBtnEnfant = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnEtudiant = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnSenior = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnFam = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnFamNb = createImageButton("images/ptInterrogation.png", 15);


    // bouton valider la résrevation
    public JButton reserverButton = new JButton("Valider la réservation");

    public VueReservation(Client client) {
        // paramètres de la fenêtre
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

        // contenu principal
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

        // boutons radio
        JPanel choixPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        groupClientType.add(rbClient);
        groupClientType.add(rbInvite);
        choixPanel.add(rbClient);
        choixPanel.add(rbInvite);
        formPanel.add(choixPanel);

        // panels vides à remplir selon le client choisi
        formPanel.add(formClientExistant);
        formPanel.add(formNouveauClient);

        // bouton réserver
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