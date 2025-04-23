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
        JLabel prixLabelExistant = new JLabel("Prix total: " + attraction.getPrix() + " €");
        prixLabelExistant.setFont(new Font("SansSerif", Font.BOLD, 14));

        plusBtnExistant.addActionListener(e -> {
            int current = Integer.parseInt(nbPersonneFieldExistant.getText());
            int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());

            if (current < 10 && current < placesRestantes) {
                nbPersonneFieldExistant.setText(String.valueOf(current + 1));
                updatePrix(prixLabelExistant, nbPersonneFieldExistant, attraction);
            } else if (placesRestantes <= 10) {
                JOptionPane.showMessageDialog(formPanel,
                        "Attention : il ne reste que " + placesRestantes + " places disponibles pour l'attraction " + attraction.getNom()+ " à cette date-ci",
                        "Places limitées", JOptionPane.WARNING_MESSAGE);
            }
        });

        moinsBtnExistant.addActionListener(e -> {
            int current = Integer.parseInt(nbPersonneFieldExistant.getText());
            if (current > 1) {
                nbPersonneFieldExistant.setText(String.valueOf(current - 1));
                updatePrix(prixLabelExistant, nbPersonneFieldExistant, attraction);
            }
        });

        // Ajout au panel
        formClientExistant.add(idPanel);
        formClientExistant.add(Box.createRigidArea(new Dimension(0, 10)));
        JPanel ligneNbExistant = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligneNbExistant.add(nbPersonneLabelExistant);
        ligneNbExistant.add(nbPanelExistant);
        formClientExistant.add(ligneNbExistant);
        formClientExistant.add(Box.createRigidArea(new Dimension(0, 10)));
        formClientExistant.add(prixLabelExistant);
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
                ReservationDao reservationDao = new ReservationDao(daoFactory);

                Reservation nouvelleReservation;
                int nbPersonnes;

                if (rbClient.isSelected()) {
                    // CAS CLIENT EXISTANT
                    int idClient = Integer.parseInt(idField.getText());
                    nbPersonnes = Integer.parseInt(nbPersonneFieldExistant.getText());

                    nouvelleReservation = creerReservationClientExistant(
                            idClient, nbPersonnes, attraction, date);

                } else if (rbInvite.isSelected()) {
                    // CAS NOUVEAU CLIENT
                    nbPersonnes = Integer.parseInt(nbPersonneFieldNouveau.getText());

                    // Validation des champs
                    if (nomField.getText().trim().isEmpty() ||
                            prenomField.getText().trim().isEmpty() ||
                            emailField.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                                "Veuillez remplir tous les champs pour les nouveaux clients",
                                "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    nouvelleReservation = creerReservationNouveauClient(
                            nomField.getText(),
                            prenomField.getText(),
                            emailField.getText(),
                            nbPersonnes,
                            attraction,
                            date);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Veuillez sélectionner un type de client",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Enregistrement en BDD
                reservationDao.ajouter(nouvelleReservation);

                // Message de confirmation
                String message = String.format(
                        "Réservation confirmée !\n" +
                                "Attraction: %s\n" +
                                "Date: %s\n" +
                                "Nombre de personnes: %d\n" +
                                "Prix total: %.2f €",
                        attraction.getNom(),
                        date,
                        nouvelleReservation.getNb_personne(),
                        nouvelleReservation.getPrix_total());

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

    private Reservation creerReservationClientExistant(int idClient, int nbPersonnes, Attraction attraction, LocalDate date) {
        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        ClientDao clientDao = new ClientDao(daoFactory);
        ReservationDao reservationDao = new ReservationDao(daoFactory);
        Client client = clientDao.getById(idClient);

        if (client == null) {
            throw new IllegalArgumentException("Client introuvable avec l'ID: " + idClient);
        }

        int idReservationUnique = reservationDao.genererIdReservationUnique(); // appel ici

        return new Reservation(
                idReservationUnique, // id_reservation (auto-généré)
                idClient,
                client.getNom(),
                client.getPrenom(),
                client.getEmail(),
                date, // date de réservation choisie
                LocalDate.now(), // date d'achat = aujourd'hui
                attraction.getIdAttraction(),
                nbPersonnes * attraction.getPrix(), // prix total
                nbPersonnes,
                0 // non archivée
        );
    }

    private Reservation creerReservationNouveauClient(String nom, String prenom, String email,
                                                      int nbPersonnes, Attraction attraction,
                                                      LocalDate date) {

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        ReservationDao reservationDao = new ReservationDao(daoFactory);
        int idReservationUnique = reservationDao.genererIdReservationUnique(); // appel ici

        return new Reservation(
                idReservationUnique, // id_reservation (auto-généré)
                0, // id_client = 0 pour non enregistré
                nom,
                prenom,
                email,
                date, // date de réservation choisie
                LocalDate.now(), // date d'achat = aujourd'hui
                attraction.getIdAttraction(),
                nbPersonnes * attraction.getPrix(), // prix total
                nbPersonnes,
                0 // non archivée
        );
    }
}
