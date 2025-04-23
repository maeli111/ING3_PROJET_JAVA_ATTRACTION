package Vue;

import DAO.*;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class testReduc extends JFrame {

    public testReduc(Reservation reservation, Attraction attraction, LocalDate date) {
        setTitle("Formulaire de Réservation");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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



        // === FORMULAIRE POUR CLIENT ===
        JPanel formClientExistant = new JPanel();
        formClientExistant.setLayout(new BoxLayout(formClientExistant, BoxLayout.Y_AXIS));

        JPanel idPanel = new JPanel(new BorderLayout());
        JLabel idLabel = new JLabel("ID client :");
        JTextField idField = new JTextField(10);
        idPanel.add(idLabel, BorderLayout.WEST);
        idPanel.add(idField, BorderLayout.CENTER);




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
        formClientExistant.add(idPanel);
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

        formPanel.add(formClientExistant);





        // === BOUTON DE RÉSERVATION ===
        JButton reserverButton = new JButton("Valider la réservation");
        reserverButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(reserverButton);

        // === ACTION DU BOUTON DE RÉSERVATION ===
        reserverButton.addActionListener(e -> {
            try {
                int idClient = Integer.parseInt(idField.getText());
                int nbAdultes = Integer.parseInt(nbAdultesField.getText());
                int nbEnfants = Integer.parseInt(nbEnfantsField.getText());
                int nbEtudiants = Integer.parseInt(nbEtudiantsField.getText());
                int nbSeniors = Integer.parseInt(nbSeniorsField.getText());
                int totalPersonnes = nbAdultes + nbEnfants + nbEtudiants + nbSeniors;

                // Vérification du client
                ClientDao clientDao = new ClientDao(daoFactory);
                Client client = clientDao.getById(idClient);
                if (client == null) {
                    throw new IllegalArgumentException("Client introuvable avec l'ID: " + idClient);
                }

                // Vérification des places disponibles
                int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());
                if (totalPersonnes > placesRestantes) {
                    throw new IllegalArgumentException("Il ne reste que " + placesRestantes + " places disponibles");
                }

                // Calcul du prix (tarif différent pour adultes/enfants)
                double prix = attraction.getPrix();
                double prixTotal = nbAdultes * prix +
                        nbEnfants * prix * 0.7 +
                        nbEtudiants * prix * 0.8 +
                        nbSeniors * prix * 0.6;

                // Création de la réservation
                Reservation nouvelleReservation = new Reservation(
                        0, // id_reservation (auto-généré)
                        idClient,
                        client.getNom(),
                        client.getPrenom(),
                        client.getEmail(),
                        date,
                        LocalDate.now(),
                        attraction.getIdAttraction(),
                        prixTotal,
                        totalPersonnes,
                        0
                );

                // Enregistrement en BDD
                ReservationDao reservationDao = new ReservationDao(daoFactory);
                reservationDao.ajouter(nouvelleReservation);

                // Message de confirmation
                String message = String.format(
                        "Réservation confirmée !\n" +
                                "Client: %s %s\n" +
                                "Adultes: %d | Enfants: %d\n" +
                                "Prix total: %.2f € (dont %.2f € de réduction enfants)",
                        client.getPrenom(),
                        client.getNom(),
                        nbAdultes,
                        nbEnfants,
                        prixTotal,
                        (nbEnfants * attraction.getPrix() * 0.3));

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