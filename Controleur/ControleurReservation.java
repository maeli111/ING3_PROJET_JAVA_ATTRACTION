package Controleur;

import DAO.*;
import Modele.*;
import Vue.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ControleurReservation {
    private final VueReservation view;
    private final Attraction attraction;
    private final LocalDate date;

    private final DaoFactory daoFactory;
    private final AttractionDao attractionDao;
    private final ClientDao clientDao;
    private final ReductionDao reductionDao;
    private final ReservationDao reservationDao;

    public ControleurReservation(VueReservation view, Attraction attraction, LocalDate date) {
        this.view = view;
        this.attraction = attraction;
        this.date = date;

        // DAO
        daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        attractionDao = new AttractionDao(daoFactory);
        clientDao = new ClientDao(daoFactory);
        reductionDao = new ReductionDao(daoFactory);
        reservationDao = new ReservationDao(daoFactory);

        setupView();
        setupListeners();
    }

    private void setupView() {
        // Affichage du titre principal
        view.titreResa.setText("Réserver l'attraction " + attraction.getNom() + " pour le " + date);
        view.formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // === Formulaire Client Existant ===
        JPanel formEx = view.formClientExistant;
        formEx.setLayout(new BoxLayout(formEx, BoxLayout.Y_AXIS));

        addLigne(formEx, "Email client :", view.emailFieldExistant);

        addLigne(formEx, "Nombre d'adultes :", view.nbAdultesField, view.moinsBtnAdultes, view.plusBtnAdultes);
        addLigne(formEx, "Nombre d'enfants :", view.nbEnfantsField, view.moinsBtnEnfants, view.plusBtnEnfants);
        addLigne(formEx, "Nombre d'étudiants :", view.nbEtudiantsField, view.moinsBtnEtudiants, view.plusBtnEtudiants);
        addLigne(formEx, "Nombre de seniors :", view.nbSeniorsField, view.moinsBtnSeniors, view.plusBtnSeniors);
        addLigne(formEx, "Nombre de pack famille :", view.nbFamField, view.moinsBtnFam, view.plusBtnFam);
        addLigne(formEx, "Nombre de pack famille nombreuses :", view.nbFamNbField, view.moinsBtnFamNb, view.plusBtnFamNb);
        addLigne(formEx, "Nombre d'enfants (famille nombreuse):", view.nbEnfantsFamNbField, view.moinsBtnEnfantsFamNb, view.plusBtnEnfantsFamNb);

        formEx.add(Box.createRigidArea(new Dimension(0, 10)));
        view.prixLabelExistant.setFont(new Font("SansSerif", Font.BOLD, 14));
        formEx.add(view.prixLabelExistant);
        formEx.setVisible(false);

        // === Formulaire Nouveau Client ===
        JPanel formNv = view.formNouveauClient;
        formNv.setLayout(new BoxLayout(formNv, BoxLayout.Y_AXIS));
        addLigne(formNv, "Nom :", view.nomField);
        addLigne(formNv, "Prénom :", view.prenomField);
        addLigne(formNv, "Email :", view.emailFieldNouveau);
        addLigne(formNv, "Nombre de personnes :", view.nbPersonneFieldNouveau, view.moinsBtnNouveau, view.plusBtnNouveau);
        view.prixLabelNouveau.setFont(new Font("SansSerif", Font.BOLD, 14));
        view.prixLabelNouveau.setText("Prix total: 0.00 €");
        formNv.add(view.prixLabelNouveau);
        formNv.setVisible(false);
    }

    private void addLigne(JPanel panel, String label, JTextField field) {
        JPanel ligne = new JPanel(new BorderLayout());
        ligne.add(new JLabel(label), BorderLayout.WEST);
        ligne.add(field, BorderLayout.CENTER);
        panel.add(ligne);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addLigne(JPanel panel, String label, JTextField field, JButton moins, JButton plus) {
        JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ligne.add(new JLabel(label));
        ligne.add(moins);
        ligne.add(field);
        ligne.add(plus);
        panel.add(ligne);
    }

    private void setupListeners() {
        // Choix type de client
        view.rbClient.addActionListener(e -> {
            view.formNouveauClient.setVisible(false);
            view.formClientExistant.setVisible(true);
        });

        view.rbInvite.addActionListener(e -> {
            view.formNouveauClient.setVisible(true);
            view.formClientExistant.setVisible(false);
            JOptionPane.showMessageDialog(view,
                    "En tant que nouveau client, aucune réduction ne peut être appliquée.\nCréez un compte pour en bénéficier.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        });

        // === Actions boutons +/- ===
        setupCompteurSansReduction(view.nbPersonneFieldNouveau, view.moinsBtnNouveau, view.plusBtnNouveau, view.prixLabelNouveau);
        setupCompteur(view.nbAdultesField, view.moinsBtnAdultes, view.plusBtnAdultes);
        setupCompteur(view.nbEnfantsField, view.moinsBtnEnfants, view.plusBtnEnfants);
        setupCompteur(view.nbEtudiantsField, view.moinsBtnEtudiants, view.plusBtnEtudiants);
        setupCompteur(view.nbSeniorsField, view.moinsBtnSeniors, view.plusBtnSeniors);
        setupCompteur(view.nbFamField, view.moinsBtnFam, view.plusBtnFam);
        setupCompteur(view.nbFamNbField, view.moinsBtnFamNb, view.plusBtnFamNb);
        setupCompteur(view.nbEnfantsFamNbField, view.moinsBtnEnfantsFamNb, view.plusBtnEnfantsFamNb, true);

        // === Valider réservation ===
        view.reserverButton.addActionListener(e -> reserver());
    }

    private void setupCompteur(JTextField field, JButton moins, JButton plus) {
        setupCompteur(field, moins, plus, view.prixLabelExistant, false);
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
            int max = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());
            if (total < 10 && total < max) {
                if (!estSimple || val < 8) {
                    field.setText(String.valueOf(val + 1));
                    updatePrixTotal(label);
                } else {
                    JOptionPane.showMessageDialog(view, "Maximum 8 enfants par pack famille nombreuse", "Limite atteinte", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(view,
                        "Vous ne pouvez pas réserver plus de 10 places au total.",
                        "Limite atteinte", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private int calculTotal() {
        return Integer.parseInt(view.nbAdultesField.getText()) +
                Integer.parseInt(view.nbEnfantsField.getText()) +
                Integer.parseInt(view.nbEtudiantsField.getText()) +
                Integer.parseInt(view.nbSeniorsField.getText()) +
                4 * Integer.parseInt(view.nbFamField.getText()) +
                Integer.parseInt(view.nbFamNbField.getText()) * (2 + Integer.parseInt(view.nbEnfantsFamNbField.getText()));
    }

    private void updatePrixTotal(JLabel label) {
        try {
            double prix = attraction.getPrix();

            int nbAdultes = Integer.parseInt(view.nbAdultesField.getText());
            int nbEnfants = Integer.parseInt(view.nbEnfantsField.getText());
            int nbEtudiants = Integer.parseInt(view.nbEtudiantsField.getText());
            int nbSeniors = Integer.parseInt(view.nbSeniorsField.getText());
            int nbFam = Integer.parseInt(view.nbFamField.getText());
            int nbFamNb = Integer.parseInt(view.nbFamNbField.getText());
            int nbEnfantsFamNb = Integer.parseInt(view.nbEnfantsFamNbField.getText());

            double reducEnfant = reductionDao.getPourcentageById(4) / 100.0;
            double reducSenior = reductionDao.getPourcentageById(5) / 100.0;
            double reducEtudiant = reductionDao.getPourcentageById(6) / 100.0;
            double reducFam = reductionDao.getPourcentageById(2) / 100.0;
            double reducFamNb = reductionDao.getPourcentageById(7) / 100.0;

            int totalFamNb = nbFamNb * (2 + nbEnfantsFamNb);

            double total = nbAdultes * prix +
                    nbEnfants * prix * (1 - reducEnfant) +
                    nbEtudiants * prix * (1 - reducEtudiant) +
                    nbSeniors * prix * (1 - reducSenior) +
                    4 * nbFam * prix * (1 - reducFam) +
                    totalFamNb * prix * (1 - reducFamNb);

            double reduc = nbEnfants * prix * reducEnfant +
                    nbEtudiants * prix * reducEtudiant +
                    nbSeniors * prix * reducSenior +
                    4 * nbFam * prix * reducFam +
                    totalFamNb * prix * reducFamNb;

            label.setText(String.format("Prix total: %.2f € (Réduction totale: %.2f €)", total, reduc));
        } catch (Exception ignored) {
        }
    }

    private void reserver() {
        try {
            boolean isNouveauClient = view.rbInvite.isSelected();

            if (isNouveauClient) {
                // Nouveau client : on récupère les données du formulaire
                String nom = view.nomField.getText().trim();
                String prenom = view.prenomField.getText().trim();
                String email = view.emailFieldNouveau.getText().trim();
                int nb = Integer.parseInt(view.nbPersonneFieldNouveau.getText());

                int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());
                if (nb > placesRestantes) {
                    throw new IllegalArgumentException("Il ne reste que " + placesRestantes + " places disponibles.");
                }

                double prixTotal = nb * attraction.getPrix();

                Reservation reservation = new Reservation(
                        0, 0, nom, prenom, email,
                        date, LocalDate.now(), attraction.getIdAttraction(), prixTotal, nb, 0
                );

                reservationDao.ajouter(reservation);

                JOptionPane.showMessageDialog(view,
                        String.format("Réservation confirmée pour %s %s\nEmail : %s\nNombre de personnes : %d\nPrix total : %.2f €",
                                prenom, nom, email, nb, prixTotal));
                view.dispose();
                return;
            }

            // === Client existant ===
            String email = view.emailFieldExistant.getText().trim();
            Client client = clientDao.getByEmail(email);
            if (client == null) {
                throw new IllegalArgumentException("Client introuvable avec l'email : " + email);
            }

            int nbAdultes = Integer.parseInt(view.nbAdultesField.getText());
            int nbEnfants = Integer.parseInt(view.nbEnfantsField.getText());
            int nbEtudiants = Integer.parseInt(view.nbEtudiantsField.getText());
            int nbSeniors = Integer.parseInt(view.nbSeniorsField.getText());
            int nbFam = Integer.parseInt(view.nbFamField.getText());
            int nbFamNb = Integer.parseInt(view.nbFamNbField.getText());
            int nbEnfantsFamNb = Integer.parseInt(view.nbEnfantsFamNbField.getText());

            int total = calculTotal();
            int placesDispo = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());
            if (total > placesDispo) {
                throw new IllegalArgumentException("Il ne reste que " + placesDispo + " places disponibles.");
            }

            double prix = attraction.getPrix();
            double reducEnfant = reductionDao.getPourcentageById(4) / 100.0;
            double reducSenior = reductionDao.getPourcentageById(5) / 100.0;
            double reducEtudiant = reductionDao.getPourcentageById(6) / 100.0;
            double reducFam = reductionDao.getPourcentageById(2) / 100.0;
            double reducFamNb = reductionDao.getPourcentageById(7) / 100.0;

            int totalFamNb = nbFamNb * (2 + nbEnfantsFamNb);

            double prixBrut = nbAdultes * prix +
                    nbEnfants * prix * (1 - reducEnfant) +
                    nbEtudiants * prix * (1 - reducEtudiant) +
                    nbSeniors * prix * (1 - reducSenior) +
                    4 * nbFam * prix * (1 - reducFam) +
                    totalFamNb * prix * (1 - reducFamNb);

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
                    date, LocalDate.now(), attraction.getIdAttraction(), prixFinal, total, 0
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

            JOptionPane.showMessageDialog(view, message);
            view.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    e.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupCompteur(JTextField field, JButton moins, JButton plus, boolean estSimple) {
        setupCompteur(field, moins, plus, view.prixLabelExistant, estSimple);
    }

    private void updatePrixSansReduction(JLabel label, JTextField nbField) {
        try {
            int nb = Integer.parseInt(nbField.getText());
            double total = nb * attraction.getPrix();
            label.setText(String.format("Prix total: %.2f €", total));
        } catch (Exception e) {
            label.setText("Prix total: 0.00 €");
        }
    }

    private void setupCompteurSansReduction(JTextField field, JButton moins, JButton plus, JLabel label) {
        moins.addActionListener(e -> {
            int val = Integer.parseInt(field.getText());
            if (val > 1) {
                field.setText(String.valueOf(val - 1));
                updatePrixSansReduction(label, field);
            }
        });

        plus.addActionListener(e -> {
            int val = Integer.parseInt(field.getText());
            int placesRestantes = attractionDao.getPlacesDisponibles(date, attraction.getIdAttraction());
            if (val < 10 && val < placesRestantes) {
                field.setText(String.valueOf(val + 1));
                updatePrixSansReduction(label, field);
            } else {
                JOptionPane.showMessageDialog(view.formPanel,
                        "Attention : il ne reste que " + placesRestantes + " places disponibles !",
                        "Places limitées", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
