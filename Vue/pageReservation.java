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

        JLabel titre = new JLabel("Réserver l'attraction " + attraction.getNom() + " pour le " + reservation.getDate_reservation());
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        titre.setFont(new Font("SansSerif", Font.BOLD, 20));
        formPanel.add(titre);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // === CHOIX CLIENT / INVITÉ ===
        JPanel choixPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton rbClient = new JRadioButton("Client avec un compte");
        JRadioButton rbInvite = new JRadioButton("Nouveau client");
        ButtonGroup group = new ButtonGroup();
        group.add(rbClient);
        group.add(rbInvite);
        choixPanel.add(rbClient);
        choixPanel.add(rbInvite);
        formPanel.add(choixPanel);

        // === PANEL POUR ID CLIENT ===
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("Identifiant client :");
        JTextField idClientField = new JTextField(10);
        idPanel.add(idLabel);
        idPanel.add(idClientField);
        formPanel.add(idPanel);
        idPanel.setVisible(false); // caché par défaut

        // === CHAMP POUR INFOS CLIENT ===
        JPanel champPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        JLabel nomLabel = new JLabel("Nom :");
        JTextField nomField = new JTextField();

        JLabel prenomLabel = new JLabel("Prénom :");
        JTextField prenomField = new JTextField();

        JLabel mailLabel = new JLabel("Adresse mail :");
        JTextField mailField = new JTextField();

        champPanel.add(nomLabel);
        champPanel.add(nomField);
        champPanel.add(prenomLabel);
        champPanel.add(prenomField);
        champPanel.add(mailLabel);
        champPanel.add(mailField);

        champPanel.setVisible(false); // par défaut masqué (sera visible si "invité" sélectionné)

        // Quand on choisit "Client avec un compte"
        rbClient.addActionListener(e -> {
            idPanel.setVisible(true);
            champPanel.setVisible(false);
        });

        // Quand on choisit "Nouveau client"
        rbInvite.addActionListener(e -> {
            idPanel.setVisible(false);
            champPanel.setVisible(true);
        });


        // Nombre de personnes avec boutons + / -
        JLabel nbPersonneLabel = new JLabel("Nombre de personnes :");
        JPanel nbPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField nbPersonneField = new JTextField("1", 2);
        nbPersonneField.setEditable(false);
        nbPersonneField.setHorizontalAlignment(JTextField.CENTER);

        JButton moinsBtn = new JButton("-");
        JButton plusBtn = new JButton("+");

        nbPanel.add(moinsBtn);
        nbPanel.add(nbPersonneField);
        nbPanel.add(plusBtn);

        champPanel.add(nbPersonneLabel);
        champPanel.add(nbPanel);

        formPanel.add(champPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton reserverButton = new JButton("Valider la réservation");
        reserverButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(reserverButton);

        add(formPanel, BorderLayout.CENTER);

        // === BOUTONS + et - ===
        plusBtn.addActionListener(e -> {
            int current = Integer.parseInt(nbPersonneField.getText());
            if (current < 10) {
                nbPersonneField.setText(String.valueOf(current + 1));
            }
        });

        moinsBtn.addActionListener(e -> {
            int current = Integer.parseInt(nbPersonneField.getText());
            if (current > 1) {
                nbPersonneField.setText(String.valueOf(current - 1));
            }
        });

        // === ACTION DE VALIDATION ===
        reserverButton.addActionListener(e -> {
            int idClient = 0;
            String nom = "";
            String prenom = "";
            String mail = "";

            DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
            ClientDao clientDao = new ClientDao(daoFactory);

            if (rbClient.isSelected()) {
                try {
                    idClient = Integer.parseInt(idClientField.getText());
                    Client client = clientDao.getById(idClient);

                    if (client == null) {
                        JOptionPane.showMessageDialog(this, "Client introuvable.");
                        return;
                    }

                    nom = client.getNom();
                    prenom = client.getPrenom();
                    mail = client.getEmail();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID client invalide.");
                    return;
                }
            } else if (rbInvite.isSelected()) {
                nom = nomField.getText().trim();
                prenom = prenomField.getText().trim();
                mail = mailField.getText().trim();

                if (nom.isEmpty() || prenom.isEmpty() || mail.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
                    return;
                }

                idClient = 0;
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un type de client.");
                return;
            }

            int nbPersonnes;
            try {
                nbPersonnes = Integer.parseInt(nbPersonneField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nombre de personnes invalide.");
                return;
            }

            double prixTotal = attraction.getPrix() * nbPersonnes;

            Reservation nouvelleReservation = new Reservation(
                    0,
                    idClient,
                    nom,
                    prenom,
                    mail,
                    date,
                    LocalDate.now(),
                    attraction.getIdAttraction(),
                    prixTotal,
                    nbPersonnes,
                    0
            );

            ReservationDao reservationDao = new ReservationDao(daoFactory);
            reservationDao.ajouter(nouvelleReservation);

            JOptionPane.showMessageDialog(this, "Réservation effectuée avec succès !");
            this.dispose();
        });

        setVisible(true);

    }
}
