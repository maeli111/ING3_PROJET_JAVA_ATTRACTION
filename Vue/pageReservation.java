package Vue;

import DAO.*;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class pageReservation extends JFrame {
    // Boutons en haut de la page
    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");

    // Nom du parc
    private JTextField parc = new JTextField("Palasi Land");

    public pageReservation(Reservation reservation, Attraction attraction, LocalDate date) {
        setTitle("Formulaire de Réservation");
        setSize(900, 900);
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




        // === FORMULAIRE DE RÉSERVATION ===
        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        AttractionDao attractionDao = new AttractionDao(daoFactory);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        JLabel titre = new JLabel("Réserver l'attraction " + attraction.getNom() + " pour le " + date);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setFont(new Font("SansSerif", Font.BOLD, 20));
        formPanel.add(titre);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));



        // === CHOIX CLIENT SIMPLIFIÉ ===
        JPanel choixPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton rbClient = new JRadioButton("Client existant");
        JRadioButton rbInvite = new JRadioButton("Nouveau client");
        ButtonGroup group = new ButtonGroup();
        group.add(rbClient);
        group.add(rbInvite);
        choixPanel.add(rbClient);
        choixPanel.add(rbInvite);
        formPanel.add(choixPanel);


        // === FORMULAIRE POUR NOUVEAU CLIENT ===
        JPanel formNouveauClient = new JPanel();
        formNouveauClient.setLayout(new BoxLayout(formNouveauClient, BoxLayout.Y_AXIS));

        // Ligne NOM
        JPanel ligneNom = new JPanel(new BorderLayout());
        JLabel nomLabel = new JLabel("Nom :");
        JTextField nomField = new JTextField(15);
        ligneNom.add(nomLabel, BorderLayout.WEST);
        ligneNom.add(nomField, BorderLayout.CENTER);

        // Ligne PRENOM
        JPanel lignePrenom = new JPanel(new BorderLayout());
        JLabel prenomLabel = new JLabel("Prénom :");
        JTextField prenomField = new JTextField(15);
        lignePrenom.add(prenomLabel, BorderLayout.WEST);
        lignePrenom.add(prenomField, BorderLayout.CENTER);

        // Ligne EMAIL
        JPanel ligneEmail = new JPanel(new BorderLayout());
        JLabel emailLabel = new JLabel("Email :");
        JTextField emailField = new JTextField(15);
        ligneEmail.add(emailLabel, BorderLayout.WEST);
        ligneEmail.add(emailField, BorderLayout.CENTER);

        // Nombre de personnes (nouveau client)
        JPanel nbPanelNouveau = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nbPersonneLabelNouveau = new JLabel("Nombre de personnes :");
        JTextField nbPersonneFieldNouveau = new JTextField("1", 2);
        nbPersonneFieldNouveau.setEditable(false);
        nbPersonneFieldNouveau.setHorizontalAlignment(JTextField.CENTER);
        JButton moinsBtnNouveau = new JButton("-");
        JButton plusBtnNouveau = new JButton("+");
        nbPanelNouveau.add(moinsBtnNouveau);
        nbPanelNouveau.add(nbPersonneFieldNouveau);
        nbPanelNouveau.add(plusBtnNouveau);

        // Actions +/-
        JLabel prixLabelNouveau = new JLabel("Prix total: " + attraction.getPrix() + " €");
        prixLabelNouveau.setFont(new Font("SansSerif", Font.BOLD, 14));

        plusBtnNouveau.addActionListener(e -> {
            int current = Integer.parseInt(nbPersonneFieldNouveau.getText());
            int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());

            if (current < 10 && current < placesRestantes) {
                nbPersonneFieldNouveau.setText(String.valueOf(current + 1));
                updatePrix(prixLabelNouveau, nbPersonneFieldNouveau, attraction);
            } else if (placesRestantes <= 10) {
                JOptionPane.showMessageDialog(formPanel,
                        "Attention : il ne reste que " + placesRestantes + " places disponibles !",
                        "Places limitées", JOptionPane.WARNING_MESSAGE);
            }
        });


        moinsBtnNouveau.addActionListener(e -> {
            int current = Integer.parseInt(nbPersonneFieldNouveau.getText());
            if (current > 1) {
                nbPersonneFieldNouveau.setText(String.valueOf(current - 1));
                updatePrix(prixLabelNouveau, nbPersonneFieldNouveau, attraction);
            }
        });

        // Ajout au panel
        formNouveauClient.add(ligneNom);
        formNouveauClient.add(Box.createRigidArea(new Dimension(0, 10)));
        formNouveauClient.add(lignePrenom);
        formNouveauClient.add(Box.createRigidArea(new Dimension(0, 10)));
        formNouveauClient.add(ligneEmail);
        formNouveauClient.add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel ligneNbNouveau = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligneNbNouveau.add(nbPersonneLabelNouveau);
        ligneNbNouveau.add(nbPanelNouveau);
        formNouveauClient.add(ligneNbNouveau);
        formNouveauClient.add(Box.createRigidArea(new Dimension(0, 10)));
        formNouveauClient.add(prixLabelNouveau);
        formNouveauClient.setVisible(false); // caché par défaut







        // === FORMULAIRE POUR CLIENT EXISTANT ===
        JPanel formClientExistant = new JPanel();
        formClientExistant.setLayout(new BoxLayout(formClientExistant, BoxLayout.Y_AXIS));

        JPanel emailPanel = new JPanel(new BorderLayout());
        JLabel emailLabel2 = new JLabel("Email client :");
        JTextField emailField2 = new JTextField(10);
        emailPanel.add(emailLabel2, BorderLayout.WEST);
        emailPanel.add(emailField2, BorderLayout.CENTER);


        // Nombre d'adultes
        JPanel nbAdultesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nbAdultesLabel = new JLabel("Nombre d'adultes :");
        JTextField nbAdultesField = new JTextField("0", 2);
        nbAdultesField.setEditable(false);
        nbAdultesField.setHorizontalAlignment(JTextField.CENTER);
        JButton moinsBtnAdultes = new JButton("-");
        JButton plusBtnAdultes = new JButton("+");
        nbAdultesPanel.add(moinsBtnAdultes);
        nbAdultesPanel.add(nbAdultesField);
        nbAdultesPanel.add(plusBtnAdultes);

        // Nombre d'enfants
        JPanel nbEnfantsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nbEnfantsLabel = new JLabel("Nombre d'enfants :");
        JTextField nbEnfantsField = new JTextField("0", 2);
        nbEnfantsField.setEditable(false);
        nbEnfantsField.setHorizontalAlignment(JTextField.CENTER);
        JButton moinsBtnEnfants = new JButton("-");
        JButton plusBtnEnfants = new JButton("+");
        nbEnfantsPanel.add(moinsBtnEnfants);
        nbEnfantsPanel.add(nbEnfantsField);
        nbEnfantsPanel.add(plusBtnEnfants);

        // Nombre d'étudiants
        JPanel nbEtudiantsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nbEtudiantsLabel = new JLabel("Nombre d'étudiants :");
        JTextField nbEtudiantsField = new JTextField("0", 2);
        nbEtudiantsField.setEditable(false);
        nbEtudiantsField.setHorizontalAlignment(JTextField.CENTER);
        JButton moinsBtnEtudiants = new JButton("-");
        JButton plusBtnEtudiants = new JButton("+");
        nbEtudiantsPanel.add(moinsBtnEtudiants);
        nbEtudiantsPanel.add(nbEtudiantsField);
        nbEtudiantsPanel.add(plusBtnEtudiants);

        // Nombre de senior
        JPanel nbSeniorsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nbSeniorsLabel = new JLabel("Nombre de seniors :");
        JTextField nbSeniorsField = new JTextField("0", 2);
        nbSeniorsField.setEditable(false);
        nbSeniorsField.setHorizontalAlignment(JTextField.CENTER);
        JButton moinsBtnSenior = new JButton("-");
        JButton plusBtnSenior = new JButton("+");
        nbSeniorsPanel.add(moinsBtnSenior);
        nbSeniorsPanel.add(nbSeniorsField);
        nbSeniorsPanel.add(plusBtnSenior);

        // Nombre de pack famille
        JPanel nbFamPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nbFamLabel = new JLabel("Nombre de pack famille :");
        JTextField nbFamField = new JTextField("0", 2);
        nbFamField.setEditable(false);
        nbFamField.setHorizontalAlignment(JTextField.CENTER);
        JButton moinsBtnFam = new JButton("-");
        JButton plusBtnFam = new JButton("+");
        nbFamPanel.add(moinsBtnFam);
        nbFamPanel.add(nbFamField);
        nbFamPanel.add(plusBtnFam);

        // Nombre de pack famille nombreuses
        JPanel nbFamNbPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nbFamNbLabel = new JLabel("Nombre de pack famille nombreuses:");
        JTextField nbFamNbField = new JTextField("0", 2);
        nbFamNbField.setEditable(false);
        nbFamNbField.setHorizontalAlignment(JTextField.CENTER);
        JButton moinsBtnFamNb = new JButton("-");
        JButton plusBtnFamNb = new JButton("+");
        nbFamNbPanel.add(moinsBtnFamNb);
        nbFamNbPanel.add(nbFamNbField);
        nbFamNbPanel.add(plusBtnFamNb);

        // Nommbre d'enfants
        JPanel nbEnfantsFamNbPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nbEnfantsFamNbLabel = new JLabel("Nombre d'enfants (famille nombreuse):");
        JTextField nbEnfantsFamNbField = new JTextField("3", 2);
        nbEnfantsFamNbField.setEditable(false);
        nbEnfantsFamNbField.setHorizontalAlignment(JTextField.CENTER);
        JButton moinsBtnEnfantsFamNb = new JButton("-");
        JButton plusBtnEnfantsFamNb = new JButton("+");
        nbEnfantsFamNbPanel.add(moinsBtnEnfantsFamNb);
        nbEnfantsFamNbPanel.add(nbEnfantsFamNbField);
        nbEnfantsFamNbPanel.add(plusBtnEnfantsFamNb);





        // Nombre de personnes (client existant)
        JPanel nbPanelExistant = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JTextField nbPersonneFieldExistant = new JTextField("1", 2);
        nbPersonneFieldExistant.setEditable(false);
        nbPersonneFieldExistant.setHorizontalAlignment(JTextField.CENTER);
        JButton moinsBtnExistant = new JButton("-");
        JButton plusBtnExistant = new JButton("+");
        nbPanelExistant.add(moinsBtnExistant);
        nbPanelExistant.add(nbPersonneFieldExistant);
        nbPanelExistant.add(plusBtnExistant);


        // Actions +/- client existant
        // Actions +/- adultes
        JLabel prixLabel = new JLabel("Prix total: 0 €");
        prixLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        plusBtnAdultes.addActionListener(e -> {
            int nbAdultes = Integer.parseInt(nbAdultesField.getText());
            int nbEnfants = Integer.parseInt(nbEnfantsField.getText());
            int nbEtudiants = Integer.parseInt(nbEtudiantsField.getText());
            int nbSeniors = Integer.parseInt(nbSeniorsField.getText());
            int nbFam = Integer.parseInt(nbFamField.getText());
            int nbFamNb = Integer.parseInt(nbFamNbField.getText());
            int nbEnfantsFamNb = Integer.parseInt(nbEnfantsFamNbField.getText());
            int totalFamNb = nbFamNb * (2 + nbEnfantsFamNb);

            int total = nbAdultes + nbEnfants + nbEtudiants + nbSeniors + 4 * nbFam + totalFamNb;

            int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());

            if (total < 10 && total < placesRestantes) {
                nbAdultesField.setText(String.valueOf(nbAdultes + 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            } else {
                JOptionPane.showMessageDialog(formPanel,
                        "Vous ne pouvez pas réserver plus de 10 places au total.",
                        "Limite atteinte", JOptionPane.WARNING_MESSAGE);
            }
        });


        moinsBtnAdultes.addActionListener(e -> {
            int current = Integer.parseInt(nbAdultesField.getText());
            if (current > 0) {
                nbAdultesField.setText(String.valueOf(current - 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            }
        });

        // Actions +/- enfants
        plusBtnEnfants.addActionListener(e -> {
            int nbAdultes = Integer.parseInt(nbAdultesField.getText());
            int nbEnfants = Integer.parseInt(nbEnfantsField.getText());
            int nbEtudiants = Integer.parseInt(nbEtudiantsField.getText());
            int nbSeniors = Integer.parseInt(nbSeniorsField.getText());
            int nbFam = Integer.parseInt(nbFamField.getText());
            int nbFamNb = Integer.parseInt(nbFamNbField.getText());
            int nbEnfantsFamNb = Integer.parseInt(nbEnfantsFamNbField.getText());
            int totalFamNb = nbFamNb * (2 + nbEnfantsFamNb);

            int total = nbAdultes + nbEnfants + nbEtudiants + nbSeniors + 4 * nbFam + totalFamNb;

            int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());

            if (total < 10 && total < placesRestantes) {
                nbEnfantsField.setText(String.valueOf(nbEnfants + 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            } else {
                JOptionPane.showMessageDialog(formPanel,
                        "Vous ne pouvez pas réserver plus de 10 places au total.",
                        "Limite atteinte", JOptionPane.WARNING_MESSAGE);
            }
        });

        moinsBtnEnfants.addActionListener(e -> {
            int current = Integer.parseInt(nbEnfantsField.getText());
            if (current > 0) {
                nbEnfantsField.setText(String.valueOf(current - 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            }
        });

        // Actions +/- étudiants
        plusBtnEtudiants.addActionListener(e -> {
            int nbAdultes = Integer.parseInt(nbAdultesField.getText());
            int nbEnfants = Integer.parseInt(nbEnfantsField.getText());
            int nbEtudiants = Integer.parseInt(nbEtudiantsField.getText());
            int nbSeniors = Integer.parseInt(nbSeniorsField.getText());
            int nbFam = Integer.parseInt(nbFamField.getText());
            int nbFamNb = Integer.parseInt(nbFamNbField.getText());
            int nbEnfantsFamNb = Integer.parseInt(nbEnfantsFamNbField.getText());
            int totalFamNb = nbFamNb * (2 + nbEnfantsFamNb);

            int total = nbAdultes + nbEnfants + nbEtudiants + nbSeniors + 4 * nbFam + totalFamNb;

            int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());

            if (total < 10 && total < placesRestantes) {
                nbEtudiantsField.setText(String.valueOf(nbEtudiants + 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            } else {
                JOptionPane.showMessageDialog(formPanel,
                        "Vous ne pouvez pas réserver plus de 10 places au total.",
                        "Limite atteinte", JOptionPane.WARNING_MESSAGE);
            }
        });

        moinsBtnEtudiants.addActionListener(e -> {
            int current = Integer.parseInt(nbEtudiantsField.getText());
            if (current > 0) {
                nbEtudiantsField.setText(String.valueOf(current - 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            }
        });

        // Actions +/- seniors
        plusBtnSenior.addActionListener(e -> {
            int nbAdultes = Integer.parseInt(nbAdultesField.getText());
            int nbEnfants = Integer.parseInt(nbEnfantsField.getText());
            int nbEtudiants = Integer.parseInt(nbEtudiantsField.getText());
            int nbSeniors = Integer.parseInt(nbSeniorsField.getText());
            int nbFam = Integer.parseInt(nbFamField.getText());
            int nbFamNb = Integer.parseInt(nbFamNbField.getText());
            int nbEnfantsFamNb = Integer.parseInt(nbEnfantsFamNbField.getText());
            int totalFamNb = nbFamNb * (2 + nbEnfantsFamNb);

            int total = nbAdultes + nbEnfants + nbEtudiants + nbSeniors + 4 * nbFam + totalFamNb;

            int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());

            if (total < 10 && total < placesRestantes) {
                nbSeniorsField.setText(String.valueOf(nbSeniors + 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            } else {
                JOptionPane.showMessageDialog(formPanel,
                        "Vous ne pouvez pas réserver plus de 10 places au total.",
                        "Limite atteinte", JOptionPane.WARNING_MESSAGE);
            }
        });

        moinsBtnSenior.addActionListener(e -> {
            int current = Integer.parseInt(nbSeniorsField.getText());
            if (current > 0) {
                nbSeniorsField.setText(String.valueOf(current - 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            }
        });

        // Actions +/- pack famille
        plusBtnFam.addActionListener(e -> {
            int nbAdultes = Integer.parseInt(nbAdultesField.getText());
            int nbEnfants = Integer.parseInt(nbEnfantsField.getText());
            int nbEtudiants = Integer.parseInt(nbEtudiantsField.getText());
            int nbSeniors = Integer.parseInt(nbSeniorsField.getText());
            int nbFam = Integer.parseInt(nbFamField.getText());
            int nbFamNb = Integer.parseInt(nbFamNbField.getText());
            int nbEnfantsFamNb = Integer.parseInt(nbEnfantsFamNbField.getText());
            int totalFamNb = nbFamNb * (2 + nbEnfantsFamNb);

            int total = nbAdultes + nbEnfants + nbEtudiants + nbSeniors + 4 * nbFam + totalFamNb;

            int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());

            if (total < 10 && total < placesRestantes) {
                nbFamField.setText(String.valueOf(nbFam + 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            } else {
                JOptionPane.showMessageDialog(formPanel,
                        "Vous ne pouvez pas réserver plus de 10 places au total.",
                        "Limite atteinte", JOptionPane.WARNING_MESSAGE);
            }
        });

        moinsBtnFam.addActionListener(e -> {
            int current = Integer.parseInt(nbFamField.getText());

            if (current > 0) {
                nbFamField.setText(String.valueOf(current - 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            }
        });

        // Actions +/- pack famille nombreuse
        plusBtnFamNb.addActionListener(e -> {
            int nbAdultes = Integer.parseInt(nbAdultesField.getText());
            int nbEnfants = Integer.parseInt(nbEnfantsField.getText());
            int nbEtudiants = Integer.parseInt(nbEtudiantsField.getText());
            int nbSeniors = Integer.parseInt(nbSeniorsField.getText());
            int nbFam = Integer.parseInt(nbFamField.getText());
            int nbFamNb = Integer.parseInt(nbFamNbField.getText());
            int nbEnfantsFamNb = Integer.parseInt(nbEnfantsFamNbField.getText());
            int totalFamNb = nbFamNb * (2 + nbEnfantsFamNb);

            int total = nbAdultes + nbEnfants + nbEtudiants + nbSeniors + 4 * nbFam + totalFamNb;

            int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());

            if (total < 10 && total < placesRestantes) {
                nbFamNbField.setText(String.valueOf(nbFamNb + 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            } else {
                JOptionPane.showMessageDialog(formPanel,
                        "Vous ne pouvez pas réserver plus de 10 places au total.",
                        "Limite atteinte", JOptionPane.WARNING_MESSAGE);
            }
        });

        moinsBtnFamNb.addActionListener(e -> {
            int current = Integer.parseInt(nbFamNbField.getText());

            if (current > 0) {
                nbFamNbField.setText(String.valueOf(current - 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            }
        });

        // Actions +/- nombre enfants pack famille nombreuse
        plusBtnEnfantsFamNb.addActionListener(e -> {
            int current = Integer.parseInt(nbEnfantsFamNbField.getText());
            if (current < 8) {
                nbEnfantsFamNbField.setText(String.valueOf(current + 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            } else {
                JOptionPane.showMessageDialog(formPanel, "Maximum 8 enfants par pack famille nombreuse", "Limite atteinte", JOptionPane.WARNING_MESSAGE);
            }
        });

        moinsBtnEnfantsFamNb.addActionListener(e -> {
            int current = Integer.parseInt(nbEnfantsFamNbField.getText());
            if (current > 3) {
                nbEnfantsFamNbField.setText(String.valueOf(current - 1));
                updatePrixTotal(prixLabel, nbAdultesField, nbEnfantsField, nbEtudiantsField, nbSeniorsField, nbFamField, nbFamNbField, nbEnfantsFamNbField, attraction);
            }
        });





        // Ajout au panel
        formClientExistant.add(emailPanel);
        formClientExistant.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel ligneAdultes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligneAdultes.add(nbAdultesLabel);
        ligneAdultes.add(nbAdultesPanel);
        formClientExistant.add(ligneAdultes);

        JPanel ligneEnfants = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligneEnfants.add(nbEnfantsLabel);
        ligneEnfants.add(nbEnfantsPanel);
        formClientExistant.add(ligneEnfants);

        JPanel ligneEtudiants = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligneEtudiants.add(nbEtudiantsLabel);
        ligneEtudiants.add(nbEtudiantsPanel);
        formClientExistant.add(ligneEtudiants);

        JPanel ligneSeniors = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligneSeniors.add(nbSeniorsLabel);
        ligneSeniors.add(nbSeniorsPanel);
        formClientExistant.add(ligneSeniors);

        JPanel ligneFam = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligneFam.add(nbFamLabel);
        ligneFam.add(nbFamPanel);
        formClientExistant.add(ligneFam);

        JPanel ligneFamNb = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligneFamNb.add(nbFamNbLabel);
        ligneFamNb.add(nbFamNbPanel);
        formClientExistant.add(ligneFamNb);

        JPanel ligneEnfantsFamNb = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligneEnfantsFamNb.add(nbEnfantsFamNbLabel);
        ligneEnfantsFamNb.add(nbEnfantsFamNbPanel);
        formClientExistant.add(ligneEnfantsFamNb);

        formClientExistant.add(Box.createRigidArea(new Dimension(0, 10)));
        formClientExistant.add(prixLabel);
        formClientExistant.setVisible(false); // caché par défaut

        formPanel.add(formClientExistant);
        formPanel.add(formNouveauClient);




        // === GESTION DES BOUTONS RADIO ===
        rbClient.addActionListener(e -> {
            formNouveauClient.setVisible(false);
            formClientExistant.setVisible(true);
        });

        rbInvite.addActionListener(e -> {
            formNouveauClient.setVisible(true);
            formClientExistant.setVisible(false);

            JOptionPane.showMessageDialog(this,
                    "En tant que nouveau client, aucune réduction ne peut être appliquée.\nCréez un compte pour en bénéficier.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        });





        // === BOUTON DE RÉSERVATION ===
        JButton reserverButton = new JButton("Valider la réservation");
        reserverButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(reserverButton);

        // === ACTION DU BOUTON DE RÉSERVATION ===
        reserverButton.addActionListener(e -> {
            try {
                // Récupération des valeurs
                String emailClient = emailField2.getText();
                int nbAdultes = Integer.parseInt(nbAdultesField.getText());
                int nbEnfants = Integer.parseInt(nbEnfantsField.getText());
                int nbEtudiants = Integer.parseInt(nbEtudiantsField.getText());
                int nbSeniors = Integer.parseInt(nbSeniorsField.getText());
                int nbFam = Integer.parseInt(nbFamField.getText());
                int nbFamNb = Integer.parseInt(nbFamNbField.getText());
                int nbEnfantsFamNb = Integer.parseInt(nbEnfantsFamNbField.getText());

                int totalPersonnes = nbAdultes + nbEnfants + nbEtudiants + nbSeniors + (4 * nbFam) + (4 * nbFamNb);

                // Vérification du client par email
                ClientDao clientDao = new ClientDao(daoFactory);
                Client client = clientDao.getByEmail(emailClient);
                if (client == null) {
                    throw new IllegalArgumentException("Client introuvable avec l'email: " + emailClient);
                }

                int idClient = client.getid_client();

                // Vérification des places disponibles
                int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());
                if (totalPersonnes > placesRestantes) {
                    throw new IllegalArgumentException("Il ne reste que " + placesRestantes + " places disponibles");
                }

                // Calcul du prix brut
                double prix = attraction.getPrix();
                ReductionDao reductionDao = new ReductionDao(daoFactory);

                double reducEnfant = reductionDao.getPourcentageById(4) / 100.0;
                double reducSenior = reductionDao.getPourcentageById(5) / 100.0;
                double reducEtudiant = reductionDao.getPourcentageById(6) / 100.0;
                double reducFam = reductionDao.getPourcentageById(2) / 100.0;
                double reducFamNb = reductionDao.getPourcentageById(7) / 100.0;

                int totalAdultesFamNb = nbFamNb * 2;
                int totalEnfantsFamNb = nbFamNb * nbEnfantsFamNb;
                int totalPersFamNb = totalAdultesFamNb + totalEnfantsFamNb;

                double prixBrut = nbAdultes * prix +
                        nbEnfants * prix * (1 - reducEnfant) +
                        nbEtudiants * prix * (1 - reducEtudiant) +
                        nbSeniors * prix * (1 - reducSenior) +
                        4 * nbFam * prix * (1 - reducFam) +
                        totalPersFamNb * prix * (1 - reducFamNb);

                // Application des réductions supplémentaires
                int nbResa = reductionDao.NbResaClient(idClient);

                int pourcentageReduction = 0;
                String typeReduction = "";

                if (nbResa == 0) {
                    pourcentageReduction = reductionDao.getPourcentagePremiereVisite(idClient);
                    typeReduction = "Première visite";
                } else if (nbResa >= 10) {
                    pourcentageReduction = reductionDao.getPourcentageFidelite(idClient);
                    typeReduction = "Fidélité";
                }

                double montantReduction = (prixBrut * pourcentageReduction) / 100;
                double prixFinal = prixBrut - montantReduction;

                // Création de la réservation
                Reservation nouvelleReservation = new Reservation(
                        0,
                        idClient,
                        client.getNom(),
                        client.getPrenom(),
                        client.getEmail(),
                        date,
                        LocalDate.now(),
                        attraction.getIdAttraction(),
                        prixFinal,
                        totalPersonnes,
                        0
                );

                // Enregistrement en BDD
                ReservationDao reservationDao = new ReservationDao(daoFactory);
                reservationDao.ajouter(nouvelleReservation);

                // Message de confirmation
                String message = String.format(
                        "Réservation confirmée !\n\n" +
                                "Client: %s %s (ID: %d)\n" +
                                "Détail des participants:\n" +
                                "- Adultes: %d\n" +
                                "- Enfants: %d (30%% réduction)\n" +
                                "- Étudiants: %d (20%% réduction)\n" +
                                "- Seniors: %d (40%% réduction)\n" +
                                "- Packs famille: %d (50%% réduction)\n" +
                                "- Packs famille nombreuse: %d (60%% réduction)\n\n" +
                                "Prix brut: %.2f €\n" +
                                "%s\n" +
                                "Prix final: %.2f €",
                        client.getPrenom(),
                        client.getNom(),
                        idClient,
                        nbAdultes,
                        nbEnfants,
                        nbEtudiants,
                        nbSeniors,
                        nbFam,
                        nbFamNb,
                        prixBrut,
                        pourcentageReduction > 0 ?
                                String.format("Réduction \"%s\" appliquée: -%d%% (%.2f €)\n",
                                        typeReduction, pourcentageReduction, montantReduction)
                                : "Aucune réduction supplémentaire appliquée.\n",
                        prixFinal
                );

                JOptionPane.showMessageDialog(this, message);
                this.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Veuillez entrer des valeurs numériques valides",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de la réservation: " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        add(formPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void updatePrix(JLabel prixLabel, JTextField nbField, Attraction attraction) {
        int nb = Integer.parseInt(nbField.getText());
        double total = nb * attraction.getPrix();
        prixLabel.setText(String.format("Prix total: %.2f €", total));
    }

    private void updatePrixTotal(JLabel prixLabel, JTextField nbAdultesField, JTextField nbEnfantsField,
                                 JTextField nbEtudiantsField, JTextField nbSeniorsField, JTextField nbFamField,
                                 JTextField nbFamNbField, JTextField nbEnfantsFamNbField, Attraction attraction) {
        int nbAdultes = Integer.parseInt(nbAdultesField.getText());
        int nbEnfants = Integer.parseInt(nbEnfantsField.getText());
        int nbEtudiants = Integer.parseInt(nbEtudiantsField.getText());
        int nbSeniors = Integer.parseInt(nbSeniorsField.getText());
        int nbFam = Integer.parseInt(nbFamField.getText());
        int nbFamNb = Integer.parseInt(nbFamNbField.getText());
        int nbEnfantsFamNb = Integer.parseInt(nbEnfantsFamNbField.getText());

        double prix = attraction.getPrix();

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        ReductionDao reductionDao = new ReductionDao(daoFactory);

        double reducEnfant = reductionDao.getPourcentageById(4) / 100.0;
        double reducSenior = reductionDao.getPourcentageById(5) / 100.0;
        double reducEtudiant = reductionDao.getPourcentageById(6) / 100.0;
        double reducFam = reductionDao.getPourcentageById(2) / 100.0;
        double reducFamNb = reductionDao.getPourcentageById(7) / 100.0;

        // 2 adultes + n enfants pour chaque pack famille nombreuse
        int totalAdultesFamNb = nbFamNb * 2;
        int totalEnfantsFamNb = nbFamNb * nbEnfantsFamNb;
        int totalPersFamNb = totalAdultesFamNb + totalEnfantsFamNb;

        double prixTotal = nbAdultes * prix +
                nbEnfants * prix * (1 - reducEnfant) +
                nbEtudiants * prix * (1 - reducEtudiant) +
                nbSeniors * prix * (1 - reducSenior) +
                4 * nbFam * prix * (1 - reducFam) +
                totalPersFamNb * nbFamNb * prix * (1 - reducFamNb);

        double totalReduc = nbEnfants  * prix * reducEnfant +
                nbEtudiants * prix * reducEtudiant +
                nbSeniors * prix * reducSenior +
                4 * nbFam * prix * reducFam +
                totalPersFamNb * nbFamNb * prix * reducFamNb;

        prixLabel.setText(String.format("Prix total: %.2f € (Réduction totale: %.2f €)", prixTotal, totalReduc));
    }

}
