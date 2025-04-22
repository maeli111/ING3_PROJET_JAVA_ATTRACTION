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




        // === FORMULAIRE DE RÉSERVATION ===
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

        // === AFFICHAGE DU STATUT ===
        JLabel statusLabel = new JLabel("Sélectionnez un type de client");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(statusLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // === FORMULAIRE POUR NOUVEAU CLIENT ===
        JPanel formNouveauClient = new JPanel();
        formNouveauClient.setLayout(new BoxLayout(formNouveauClient, BoxLayout.Y_AXIS));

        // Ligne NOM
        JPanel ligneNom = new JPanel(new BorderLayout());
        JLabel nomLabel = new JLabel("Nom :");
        JTextField nomField = new JTextField(15);
        ligneNom.add(nomLabel, BorderLayout.WEST);
        ligneNom.add(nomField, BorderLayout.CENTER);

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
        plusBtnNouveau.addActionListener(e -> {
            int current = Integer.parseInt(nbPersonneFieldNouveau.getText());
            if (current < 10) nbPersonneFieldNouveau.setText(String.valueOf(current + 1));
        });
        moinsBtnNouveau.addActionListener(e -> {
            int current = Integer.parseInt(nbPersonneFieldNouveau.getText());
            if (current > 1) nbPersonneFieldNouveau.setText(String.valueOf(current - 1));
        });

        // Ajout au panel
        formNouveauClient.add(ligneNom);
        formNouveauClient.add(Box.createRigidArea(new Dimension(0, 10)));
        formNouveauClient.add(ligneEmail);
        formNouveauClient.add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel ligneNbNouveau = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligneNbNouveau.add(nbPersonneLabelNouveau);
        ligneNbNouveau.add(nbPanelNouveau);
        formNouveauClient.add(ligneNbNouveau);
        formNouveauClient.setVisible(false); // caché par défaut


        // === FORMULAIRE POUR CLIENT EXISTANT ===
        JPanel formClientExistant = new JPanel();
        formClientExistant.setLayout(new BoxLayout(formClientExistant, BoxLayout.Y_AXIS));

        JPanel idPanel = new JPanel(new BorderLayout());
        JLabel idLabel = new JLabel("ID client :");
        JTextField idField = new JTextField(10);
        idPanel.add(idLabel, BorderLayout.WEST);
        idPanel.add(idField, BorderLayout.CENTER);

        // Nombre de personnes (client existant)
        JPanel nbPanelExistant = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nbPersonneLabelExistant = new JLabel("Nombre de personnes :");
        JTextField nbPersonneFieldExistant = new JTextField("1", 2);
        nbPersonneFieldExistant.setEditable(false);
        nbPersonneFieldExistant.setHorizontalAlignment(JTextField.CENTER);
        JButton moinsBtnExistant = new JButton("-");
        JButton plusBtnExistant = new JButton("+");
        nbPanelExistant.add(moinsBtnExistant);
        nbPanelExistant.add(nbPersonneFieldExistant);
        nbPanelExistant.add(plusBtnExistant);

        // Actions +/-
        plusBtnExistant.addActionListener(e -> {
            int current = Integer.parseInt(nbPersonneFieldExistant.getText());
            if (current < 10) nbPersonneFieldExistant.setText(String.valueOf(current + 1));
        });
        moinsBtnExistant.addActionListener(e -> {
            int current = Integer.parseInt(nbPersonneFieldExistant.getText());
            if (current > 1) nbPersonneFieldExistant.setText(String.valueOf(current - 1));
        });

        // Ajout au panel
        formClientExistant.add(idPanel);
        formClientExistant.add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel ligneNbExistant = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligneNbExistant.add(nbPersonneLabelExistant);
        ligneNbExistant.add(nbPanelExistant);
        formClientExistant.add(ligneNbExistant);

        formClientExistant.setVisible(false); // caché par défaut

        formPanel.add(formClientExistant);
        formPanel.add(formNouveauClient);

        // === GESTION DES BOUTONS RADIO ===
        rbClient.addActionListener(e -> {
            statusLabel.setText("ancien");
            formNouveauClient.setVisible(false);
            formClientExistant.setVisible(true);
        });

        rbInvite.addActionListener(e -> {
            statusLabel.setText("nouveau");
            formNouveauClient.setVisible(true);
            formClientExistant.setVisible(false);
        });


        // === BOUTON DE RÉSERVATION ===
        JButton reserverButton = new JButton("Valider la réservation");
        reserverButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(reserverButton);

        // === ACTION DU BOUTON DE RÉSERVATION ===
        reserverButton.addActionListener(e -> {
            String typeClient = rbClient.isSelected() ? "ancien" :
                    rbInvite.isSelected() ? "nouveau" : "non sélectionné";

            JOptionPane.showMessageDialog(this,
                    "Réservation pour client " + typeClient + "!\n" +
                            "Attraction: " + attraction.getNom() + "\n" +
                            "Date: " + date);
        });

        add(formPanel, BorderLayout.CENTER);
        setVisible(true);
    }

}
