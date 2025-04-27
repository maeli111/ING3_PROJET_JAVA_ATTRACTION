package Controleur;

import DAO.*;
import Modele.*;
import Vue.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ControleurReservationClient {
    private final VueReservationClient vue;
    private final Attraction attraction;
    private final LocalDate date;

    // dao
    private final DaoFactory daoFactory;
    private final AttractionDao attractionDao;
    private final ClientDao clientDao;
    private final ReductionDao reductionDao;
    private final ReservationDao reservationDao;

    // constructeur
    public ControleurReservationClient(VueReservationClient vue, Attraction attraction, LocalDate date, Client client) {
        this.vue = vue;
        this.attraction = attraction;
        this.date = date;

        daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        attractionDao = new AttractionDao(daoFactory);
        clientDao = new ClientDao(daoFactory);
        reductionDao = new ReductionDao(daoFactory);
        reservationDao = new ReservationDao(daoFactory);

        setupvue();
        setupListeners(client);
    }

    // configuration de la vue
    private void setupvue() {
        vue.titreResa.setText("Réserver l'attraction " + attraction.getNom() + " pour le " + date);
        vue.titreResa.setForeground(new Color(255, 105, 180));
        vue.titreResa.setFont(new Font("Arial", Font.BOLD, 22));

        vue.formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel formEx = vue.formClientExistant;
        formEx.setLayout(new BoxLayout(formEx, BoxLayout.Y_AXIS));
        formEx.setBackground(Color.WHITE);
        formEx.setOpaque(true);

        // champs pour les différents type de places
        addLigne(formEx, "Nombre d'adultes :", vue.nbAdultesField, vue.moinsBtnAdultes, vue.plusBtnAdultes);
        addLigne(formEx, "Nombre d'enfants :", vue.nbEnfantsField, vue.moinsBtnEnfants, vue.plusBtnEnfants, vue.infoBtnEnfant);
        addLigne(formEx, "Nombre d'étudiants :", vue.nbEtudiantsField, vue.moinsBtnEtudiants, vue.plusBtnEtudiants, vue.infoBtnEtudiant);
        addLigne(formEx, "Nombre de seniors :", vue.nbSeniorsField, vue.moinsBtnSeniors, vue.plusBtnSeniors, vue.infoBtnSenior);
        addLigne(formEx, "Nombre de pack famille :", vue.nbFamField, vue.moinsBtnFam, vue.plusBtnFam, vue.infoBtnFam);
        addLigne(formEx, "Nombre de pack famille nombreuses :", vue.nbFamNbField, vue.moinsBtnFamNb, vue.plusBtnFamNb, vue.infoBtnFamNb);
        addLigne(formEx, "Nombre d'enfants (famille nombreuse):", vue.nbEnfantsFamNbField, vue.moinsBtnEnfantsFamNb, vue.plusBtnEnfantsFamNb);

        formEx.add(Box.createRigidArea(new Dimension(0, 10)));
        vue.prixLabelExistant.setFont(new Font("Arial", Font.BOLD, 22));
        vue.prixLabelExistant.setForeground(new Color(255, 20, 147));
        vue.prixLabelExistant.setBackground(Color.WHITE);
        vue.prixLabelExistant.setOpaque(true);
        vue.prixLabelExistant.setHorizontalAlignment(SwingConstants.CENTER);
        vue.prixLabelExistant.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel prixPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prixPanel.setOpaque(false);
        prixPanel.add(vue.prixLabelExistant);
        formEx.add(prixPanel);

        formEx.setVisible(true);
    }

    // ajoute ligne dans le form avec un champ/label
    private void addLigne(JPanel panel, String label, JTextField field) {
        JPanel ligne = new JPanel(new BorderLayout());
        ligne.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(label);
        lbl.setForeground(new Color(255, 105, 180));
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        ligne.add(lbl, BorderLayout.WEST);
        ligne.add(field, BorderLayout.CENTER);
        panel.add(ligne);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    // ajoute ligne dans le form avec des boutons + et - pour ajuster le nombre de places
    private void addLigne(JPanel panel, String label, JTextField field, JButton moins, JButton plus) {
        JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligne.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(label);
        lbl.setForeground(new Color(255, 105, 180));
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        ligne.add(lbl);
        ligne.add(moins);
        ligne.add(field);
        ligne.add(plus);
        panel.add(ligne);
    }

    // ajoute ligne dans le form avec des boutons + et - pour ajuster le nombre de places
    // avec un bouton "?" pour afficher la description
    private void addLigne(JPanel panel, String label, JTextField field, JButton moins, JButton plus, JButton info) {
        JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligne.setBackground(Color.WHITE);
        JLabel lbl = new JLabel(label);
        lbl.setForeground(new Color(255, 105, 180));
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        ligne.add(lbl);
        ligne.add(moins);
        ligne.add(field);
        ligne.add(plus);
        ligne.add(info);
        panel.add(ligne);
    }

    // configuration des listeners
    private void setupListeners(Client client) {
        setupCompteur(vue.nbAdultesField, vue.moinsBtnAdultes, vue.plusBtnAdultes);
        setupCompteur(vue.nbEnfantsField, vue.moinsBtnEnfants, vue.plusBtnEnfants);
        setupCompteur(vue.nbEtudiantsField, vue.moinsBtnEtudiants, vue.plusBtnEtudiants);
        setupCompteur(vue.nbSeniorsField, vue.moinsBtnSeniors, vue.plusBtnSeniors);
        setupCompteur(vue.nbFamField, vue.moinsBtnFam, vue.plusBtnFam);
        setupCompteur(vue.nbFamNbField, vue.moinsBtnFamNb, vue.plusBtnFamNb);
        setupCompteur(vue.nbEnfantsFamNbField, vue.moinsBtnEnfantsFamNb, vue.plusBtnEnfantsFamNb);

        setupInfoReduction(vue.infoBtnEnfant, 4);
        setupInfoReduction(vue.infoBtnEtudiant, 6);
        setupInfoReduction(vue.infoBtnSenior, 5);
        setupInfoReduction(vue.infoBtnFam, 2);
        setupInfoReduction(vue.infoBtnFamNb, 7);

        vue.reserverButton.addActionListener(e -> {
            reserver(client);
            VueClient vueClient = new VueClient(client);
            new ControleurClient(vueClient, client);
            vueClient.setVisible(true);
            vue.dispose();
        });

        vue.compte.addActionListener(e -> {
            VueClient vueClient = new VueClient(client);
            new ControleurClient(vueClient, client);
            vueClient.setVisible(true);
            vue.dispose();
        });

        vue.informations.addActionListener(e -> {
            VuePlusInfos v = new VuePlusInfos(client, null);
            new ControleurPlusInfos(v, client, null);
            v.setVisible(true);
            vue.dispose();
        });

        vue.calendrier.addActionListener(e -> {
            VueCalendrier vueCalendrier = new VueCalendrier(client, null);
            new ControleurCalendrier(vueCalendrier, client, null);
            vueCalendrier.setVisible(true);
            vue.dispose();
        });
    }

    private void setupCompteur(JTextField field, JButton moins, JButton plus) {
        setupCompteur(field, moins, plus, vue.prixLabelExistant, false);
    }

    private void setupCompteur(JTextField field, JButton moins, JButton plus, JLabel label, boolean estSimple) {
        moins.addActionListener(e -> {
            int val = Integer.parseInt(field.getText());
            if ((estSimple && val > 1) || (!estSimple && val > 0)) {
                field.setText(String.valueOf(val - 1));
                updatePrixTotal(label);
            }
        });

        plus.addActionListener(e -> {
            int val = Integer.parseInt(field.getText());
            int total = calculTotal();
            int max = attractionDao.getPlacesDisponibles(date, attraction.getId_attraction());
            if (total < 10 && total < max) {
                if (!estSimple || val < 8) {
                    field.setText(String.valueOf(val + 1));
                    updatePrixTotal(label);
                } else {
                    JOptionPane.showMessageDialog(vue, "Maximum 8 enfants par pack famille nombreuse", "Limite atteinte", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(vue,
                        "Vous ne pouvez pas réserver plus de 10 places au total.",
                        "Limite atteinte", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private int calculTotal() {
        return Integer.parseInt(vue.nbAdultesField.getText()) +
                Integer.parseInt(vue.nbEnfantsField.getText()) +
                Integer.parseInt(vue.nbEtudiantsField.getText()) +
                Integer.parseInt(vue.nbSeniorsField.getText()) +
                4 * Integer.parseInt(vue.nbFamField.getText()) +
                Integer.parseInt(vue.nbFamNbField.getText()) * (2 + Integer.parseInt(vue.nbEnfantsFamNbField.getText()));
    }

    private void updatePrixTotal(JLabel label) {
        try {
            double prix = attraction.getPrix();

            // Récupérer la réduction sur l'attraction
            double reducAttraction = reductionDao.getPourcentageAttraction(attraction.getId_attraction()) / 100.0;

            // Récupération des quantités de personnes
            int nbAdultes = Integer.parseInt(vue.nbAdultesField.getText());
            int nbEnfants = Integer.parseInt(vue.nbEnfantsField.getText());
            int nbEtudiants = Integer.parseInt(vue.nbEtudiantsField.getText());
            int nbSeniors = Integer.parseInt(vue.nbSeniorsField.getText());
            int nbFam = Integer.parseInt(vue.nbFamField.getText());
            int nbFamNb = Integer.parseInt(vue.nbFamNbField.getText());
            int nbEnfantsFamNb = Integer.parseInt(vue.nbEnfantsFamNbField.getText());

            // Récupérer les réductions spécifiques
            double reducEnfant = reductionDao.getPourcentageById(4) / 100.0;
            double reducSenior = reductionDao.getPourcentageById(5) / 100.0;
            double reducEtudiant = reductionDao.getPourcentageById(6) / 100.0;
            double reducFam = reductionDao.getPourcentageById(2) / 100.0;
            double reducFamNb = reductionDao.getPourcentageById(7) / 100.0;

            // Calcul du nombre total de personnes dans la catégorie "famille nombreuse"
            int totalFamNb = nbFamNb * (2 + nbEnfantsFamNb);

            // Calcul du prix sans réduction
            double totalSansReduction = nbAdultes * prix +
                    nbEnfants * prix +
                    nbEtudiants * prix +
                    nbSeniors * prix +
                    4 * nbFam * prix +
                    totalFamNb * prix;

            // Calcul du prix avec réduction
            double prixApresAttraction = prix * (1 - reducAttraction);

            double totalAvecReduction = nbAdultes * prixApresAttraction +
                    nbEnfants * prixApresAttraction * (1 - reducEnfant) +
                    nbEtudiants * prixApresAttraction * (1 - reducEtudiant) +
                    nbSeniors * prixApresAttraction * (1 - reducSenior) +
                    4 * nbFam * prixApresAttraction * (1 - reducFam) +
                    totalFamNb * prixApresAttraction * (1 - reducFamNb);

            // Affichage des informations
            if (totalAvecReduction < totalSansReduction) {
                double difference = totalSansReduction - totalAvecReduction;
                label.setText(String.format("Prix total avec réductions: %.2f € (Réduction totale: %.2f €)\n",totalAvecReduction, difference));
            } else {
                label.setText(String.format("Prix total sans réductions: %.2f €", totalSansReduction));
            }

        } catch (Exception ignored) {
            // En cas d'erreur de conversion ou autre
        }
    }

    private void reserver(Client client) {
        try {
            int nbAdultes = Integer.parseInt(vue.nbAdultesField.getText());
            int nbEnfants = Integer.parseInt(vue.nbEnfantsField.getText());
            int nbEtudiants = Integer.parseInt(vue.nbEtudiantsField.getText());
            int nbSeniors = Integer.parseInt(vue.nbSeniorsField.getText());
            int nbFam = Integer.parseInt(vue.nbFamField.getText());
            int nbFamNb = Integer.parseInt(vue.nbFamNbField.getText());
            int nbEnfantsFamNb = Integer.parseInt(vue.nbEnfantsFamNbField.getText());

            int total = calculTotal();
            int placesDispo = attractionDao.getPlacesDisponibles(date, attraction.getId_attraction());
            if (total > placesDispo) {
                throw new IllegalArgumentException("Il ne reste que " + placesDispo + " places disponibles.");
            }

            double prix = attraction.getPrix();
            double reducAttraction = reductionDao.getPourcentageAttraction(attraction.getId_attraction()) / 100.0;
            double prixApresAttraction = prix * (1 - reducAttraction);

            double reducEnfant = reductionDao.getPourcentageById(4) / 100.0;
            double reducSenior = reductionDao.getPourcentageById(5) / 100.0;
            double reducEtudiant = reductionDao.getPourcentageById(6) / 100.0;
            double reducFam = reductionDao.getPourcentageById(2) / 100.0;
            double reducFamNb = reductionDao.getPourcentageById(7) / 100.0;

            int totalFamNb = nbFamNb * (2 + nbEnfantsFamNb);

            double prixBrut = nbAdultes * prixApresAttraction +
                    nbEnfants * prixApresAttraction * (1 - reducEnfant) +
                    nbEtudiants * prixApresAttraction * (1 - reducEtudiant) +
                    nbSeniors * prixApresAttraction * (1 - reducSenior) +
                    4 * nbFam * prixApresAttraction * (1 - reducFam) +
                    totalFamNb * prixApresAttraction * (1 - reducFamNb);

            int nbResa = reductionDao.NbResaClient(client.getid_client());
            int pourcentageReduction = 0;
            String typeReduction = "";

            if (nbResa == 0) {
                pourcentageReduction = reductionDao.getPourcentagePremiereVisite(client.getid_client());
                typeReduction = "Première visite";
            } else if (nbResa >= 10) {
                pourcentageReduction = reductionDao.getPourcentageFidelite(client.getid_client());
                typeReduction = "Fidélité";
            }

            double montantReduction = (prixBrut * pourcentageReduction) / 100;
            double prixFinal = prixBrut - montantReduction;

            Reservation nouvelleReservation = new Reservation(
                    0, client.getid_client(), client.getNom(), client.getPrenom(), client.getEmail(),
                    date, LocalDate.now(), attraction.getId_attraction(), prixFinal, total, 0
            );

            reservationDao.ajouter(nouvelleReservation);

            String message = String.format(
                    "Réservation confirmée !\n\n" +
                            "Client: %s %s (ID: %d)\n" +
                            "Total: %d personnes\n" +
                            "Prix brut: %.2f €\n%sPrix final: %.2f €",
                    client.getPrenom(), client.getNom(), client.getid_client(),
                    total, prixBrut,
                    pourcentageReduction > 0 ?
                            String.format("Réduction \"%s\" : -%d%% (%.2f €)\n", typeReduction, pourcentageReduction, montantReduction)
                            : "Aucune réduction supplémentaire\n",
                    prixFinal
            );

            JOptionPane.showMessageDialog(vue, message);
            vue.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(vue, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupInfoReduction(JButton bouton, int idReduction) {
        bouton.setPreferredSize(new Dimension(20, 20));
        bouton.setMargin(new Insets(2, 2, 2, 2));
        bouton.addActionListener(e -> {
            try {
                String description = reductionDao.getDescriptionById(idReduction);
                JOptionPane.showMessageDialog(vue, description, "Détail de la réduction", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vue, "Impossible de charger la description.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
