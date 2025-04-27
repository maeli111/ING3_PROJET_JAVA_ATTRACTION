package Vue;

import Modele.Client;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VueReservationClient extends JFrame {
    private final Color ROSE_PRINCIPAL = new Color(255, 105, 180);
    private final Color ROSE_FONCE = new Color(255, 20, 147);
    private final Color ROSE_SURVOL = new Color(255, 182, 193);
    private final Color ROSE_FOND = new Color(255, 240, 245);

    public JButton accueil = new JButton("Accueil");
    public JButton informations = new JButton("Informations");
    public JButton calendrier = new JButton("Calendrier");
    public JButton compte = new JButton("Compte");
    public JTextField parc = new JTextField("Palasi Land");
    public JPanel header = new JPanel(new BorderLayout());
    public JLabel titreResa = new JLabel();

    public JPanel formPanel = new JPanel();
    public JPanel formClientExistant = new JPanel();

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

    public JButton infoBtnEnfant = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnEtudiant = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnSenior = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnFam = createImageButton("images/ptInterrogation.png", 15);
    public JButton infoBtnFamNb = createImageButton("images/ptInterrogation.png", 15);

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
        parc.setFont(new Font("Bodoni MT", Font.BOLD, 40));
        parc.setBorder(null);
        parc.setOpaque(false);

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

        titreResa.setFont(new Font("SansSerif", Font.BOLD, 16));
        titreResa.setHorizontalAlignment(SwingConstants.CENTER);

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
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        formPanel.setBackground(Color.WHITE);
        formPanel.setOpaque(true);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setPreferredSize(new Dimension(1000, 500));

        formPanel.add(formClientExistant);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(formPanel);
        add(centerPanel, BorderLayout.CENTER);

        // BOUTON VALIDER
        reserverButton.setFont(new Font("Arial", Font.BOLD, 24));
        reserverButton.setBackground(ROSE_PRINCIPAL);
        reserverButton.setForeground(Color.WHITE);
        reserverButton.setFocusPainted(false);
        reserverButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        reserverButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(reserverButton, BorderLayout.SOUTH);
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
