package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VueReservationInvite extends JFrame {
    // déclaration des composants
    public JTextField nbPersonneFieldNouveau;
    public JButton moinsBtnNouveau, plusBtnNouveau;
    public JLabel prixLabelNouveau;
    public JTextField nomField, prenomField, emailFieldNouveau;
    public JButton reserverButton;
    public JLabel titreResa = new JLabel();  // Utilisez cette variable directement

    // boutons de navigation
    public JButton btnAccueil, btnInformations, btnCalendrier, btnCompte;

    // constructeur
    public VueReservationInvite() {
        // configuration de la fenêtre
        setTitle("Réservation");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // header
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel navGauche = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAccueil = new JButton("Accueil");
        btnInformations = new JButton("Informations");
        btnCalendrier = new JButton("Calendrier");
        navGauche.add(btnAccueil);
        navGauche.add(btnInformations);
        navGauche.add(btnCalendrier);
        titreResa.setFont(new Font("SansSerif", Font.BOLD, 16)); // Vous définissez les propriétés de titreResa ici
        titreResa.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel navDroite = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnCompte = new JButton("Compte");
        navDroite.add(btnCompte);

        topPanel.add(navGauche, BorderLayout.WEST);
        topPanel.add(navDroite, BorderLayout.EAST);

        // formulaire
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // champ nom
        JPanel panelNom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelNom.add(new JLabel("Nom :"));
        nomField = new JTextField(12);
        panelNom.add(nomField);
        formPanel.add(panelNom);

        // champ prénom
        JPanel panelPrenom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelPrenom.add(new JLabel("Prénom :"));
        prenomField = new JTextField(12);
        panelPrenom.add(prenomField);
        formPanel.add(panelPrenom);

        // champ email
        JPanel panelEmail = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelEmail.add(new JLabel("Email :"));
        emailFieldNouveau = new JTextField(12);
        panelEmail.add(emailFieldNouveau);
        formPanel.add(panelEmail);

        // nb de personnes
        JPanel panelNb = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelNb.add(new JLabel("Nombre de personnes :"));
        moinsBtnNouveau = new JButton("-");
        nbPersonneFieldNouveau = new JTextField("0", 3);
        nbPersonneFieldNouveau.setHorizontalAlignment(JTextField.CENTER);
        plusBtnNouveau = new JButton("+");
        panelNb.add(moinsBtnNouveau);
        panelNb.add(nbPersonneFieldNouveau);
        panelNb.add(plusBtnNouveau);
        formPanel.add(panelNb);

        // prix total
        prixLabelNouveau = new JLabel("Prix total: 0.00 €", SwingConstants.CENTER);
        formPanel.add(prixLabelNouveau);

        // panel central
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(titreResa, BorderLayout.NORTH); // Utilisez titreResa ici
        centerPanel.add(formPanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // bouton pour valider la réservation
        reserverButton = new JButton("Réserver");
        add(reserverButton, BorderLayout.SOUTH);
    }

    // ajout de tous les listeners
    public void addListeners(ActionListener listener) {
        btnAccueil.addActionListener(listener);
        btnInformations.addActionListener(listener);
        btnCalendrier.addActionListener(listener);
        btnCompte.addActionListener(listener);
        reserverButton.addActionListener(listener);
        moinsBtnNouveau.addActionListener(listener);
        plusBtnNouveau.addActionListener(listener);
    }
}
