package Vue;

import DAO.AttractionDao;
import DAO.ReductionDao;
import DAO.DaoFactory;
import Modele.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;

public class VueAdminRA extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private ReductionDao reductionDao;
    private AttractionDao attractionDao;

    public VueAdminRA(Admin admin) {
        setTitle("Réductions liées aux attractions - Admin");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        reductionDao = new ReductionDao(daoFactory);
        attractionDao = new AttractionDao(daoFactory);

        // --- TOP PANEL ---
        JPanel topPanel = new JPanel(new BorderLayout());

        // --- BARRE DE BOUTONS (ligne du haut) ---
        JPanel buttonBar = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton accueilButton = new JButton("Accueil");
        JButton infoButton = new JButton("Informations");
        JButton calendrierButton = new JButton("Calendrier");
        leftPanel.add(accueilButton);
        leftPanel.add(infoButton);
        leftPanel.add(calendrierButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton compteButton = new JButton("Compte");
        rightPanel.add(compteButton);

        // Action du bouton Compte
        compteButton.addActionListener(e -> {
            dispose();
            VueAdmin vueAdmin = new VueAdmin(admin);
            vueAdmin.setVisible(true);
        });

        buttonBar.add(leftPanel, BorderLayout.WEST);
        buttonBar.add(rightPanel, BorderLayout.EAST);

        // --- TITRE (ligne en dessous, centré sur toute la largeur) ---
        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titreLabel = new JLabel("PalasiLand");
        titreLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titrePanel.add(titreLabel);

        // On met les deux dans topPanel
        topPanel.add(buttonBar, BorderLayout.NORTH);
        topPanel.add(titrePanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Table des réductions liées aux attractions
        String[] columns = {"ID", "Nom", "Pourcentage", "Description", "Attractions Concernées"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        add(scrollPane, BorderLayout.CENTER);

        // Boutons en bas
        JPanel buttonPanel = new JPanel();
        JButton ajouter = new JButton("Ajouter");
        JButton modifier = new JButton("Modifier");
        JButton supprimer = new JButton("Supprimer");

        buttonPanel.add(ajouter);
        buttonPanel.add(modifier);
        buttonPanel.add(supprimer);
        add(buttonPanel, BorderLayout.SOUTH);

        // Charger données
        chargerDonnees();

        ajouter.addActionListener(e -> {
            JTextField nomField = new JTextField();
            JTextField pourcentageField = new JTextField();
            JTextField descriptionField = new JTextField();

            // Récupérer la liste des attractions disponibles
            ArrayList<Attraction> attractions = attractionDao.getAll();
            JPanel attractionCheckboxPanel = new JPanel();
            attractionCheckboxPanel.setLayout(new BoxLayout(attractionCheckboxPanel, BoxLayout.Y_AXIS));

            ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
            for (Attraction attraction : attractions) {
                JCheckBox cb = new JCheckBox(attraction.getNom());
                cb.putClientProperty("attraction", attraction);
                checkBoxes.add(cb);
                attractionCheckboxPanel.add(cb);
            }

            JScrollPane listScrollPane = new JScrollPane(attractionCheckboxPanel);
            listScrollPane.setPreferredSize(new Dimension(300, 150));

            Object[] fields = {
                    "Nom :", nomField,
                    "Pourcentage :", pourcentageField,
                    "Description :", descriptionField,
                    "Sélectionnez les attractions :", listScrollPane
            };

            int res = JOptionPane.showConfirmDialog(null, fields, "Nouvelle réduction qui concerne des attractions", JOptionPane.OK_CANCEL_OPTION);

            if (res == JOptionPane.OK_OPTION) {
                try {
                    String nom = nomField.getText();
                    int pourcentage = Integer.parseInt(pourcentageField.getText());
                    String description = descriptionField.getText();

                    Reduction reduction = new Reduction(nom, pourcentage, description);
                    reductionDao.ajouter(reduction);

                    ArrayList<Attraction> selectedAttractions = new ArrayList<>();
                    for (JCheckBox cb : checkBoxes) {
                        if (cb.isSelected()) {
                            selectedAttractions.add((Attraction) cb.getClientProperty("attraction"));
                        }
                    }

                    for (Attraction selectedAttraction : selectedAttractions) {
                        reductionDao.lierReductionAttraction(reduction.getId_reduction(), selectedAttraction.getId_attraction());
                    }

                    chargerDonnees();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur : données invalides");
                }
            }
        });


        modifier.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Sélectionnez une réduction.");
                return;
            }

            int oldId = (int) model.getValueAt(row, 0);
            String nom = (String) model.getValueAt(row, 1);
            String pourcentage = model.getValueAt(row, 2).toString();
            String description = (String) model.getValueAt(row, 3);

            JTextField nomField = new JTextField(nom);
            JTextField pourcentageField = new JTextField(pourcentage);
            JTextField descriptionField = new JTextField(description);

            // Attractions liées et non liées
            ArrayList<Attraction> linkedAttractions = reductionDao.getAttractionsLiees(oldId); // attractions déjà associées
            ArrayList<Attraction> nonLinkedAttractions = reductionDao.getAttractionsNonLiees(oldId); // attractions non associées

            JPanel attractionCheckboxPanel = new JPanel();
            attractionCheckboxPanel.setLayout(new BoxLayout(attractionCheckboxPanel, BoxLayout.Y_AXIS));

            ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

            // Afficher les attractions déjà liées avec checkbox cochées
            for (Attraction attraction : linkedAttractions) {
                JCheckBox cb = new JCheckBox(attraction.getNom());
                cb.putClientProperty("attraction", attraction);
                cb.setSelected(true); // Marquer comme liée
                checkBoxes.add(cb);
                attractionCheckboxPanel.add(cb);
            }

            // Afficher les attractions non liées avec checkbox décochées
            for (Attraction attraction : nonLinkedAttractions) {
                JCheckBox cb = new JCheckBox(attraction.getNom());
                cb.putClientProperty("attraction", attraction);
                checkBoxes.add(cb);
                attractionCheckboxPanel.add(cb);
            }

            JScrollPane listScrollPane = new JScrollPane(attractionCheckboxPanel);
            listScrollPane.setPreferredSize(new Dimension(300, 150));

            Object[] fields = {
                    "Nom :", nomField,
                    "Pourcentage :", pourcentageField,
                    "Description :", descriptionField,
                    "Modifier les attractions concernées :", listScrollPane
            };

            int res = JOptionPane.showConfirmDialog(null, fields, "Modifier la réduction liée à des attractions", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                try {
                    String newNom = nomField.getText();
                    int newPourcentage = Integer.parseInt(pourcentageField.getText());
                    String newDesc = descriptionField.getText();

                    // Mise à jour de la réduction
                    reductionDao.modifier(oldId, new Reduction(oldId, newNom, newPourcentage, newDesc));

                    // Mise à jour des liaisons
                    reductionDao.supprimerLiaisonsAttractions(oldId); // supprime les anciennes liaisons

                    // Liste des attractions sélectionnées
                    ArrayList<Attraction> selectedAttractions = new ArrayList<>();
                    for (JCheckBox cb : checkBoxes) {
                        if (cb.isSelected()) {
                            Attraction attraction = (Attraction) cb.getClientProperty("attraction");
                            selectedAttractions.add(attraction);
                            reductionDao.lierReductionAttraction(oldId, attraction.getId_attraction()); // lie l'attraction
                        }
                    }

                    // Si aucune attraction n'est sélectionnée, on supprime la réduction
                    if (reductionDao.isEmpty(oldId)) {
                        reductionDao.supprimer(oldId);
                        JOptionPane.showMessageDialog(null, "Réduction supprimée, aucune attraction concernée à la réduction.");
                    }

                    chargerDonnees();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur : données invalides");
                }
            }
        });


        // Action Supprimer
        supprimer.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Sélectionnez une réduction.");
                return;
            }

            int id = (int) model.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(null, "Supprimer cette réduction ?", "Confirmer", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Supprimer les associations avec les attractions dans Reduction_Attraction
                    reductionDao.supprimerLiaisonsAttractions(id);

                    // Supprimer la réduction elle-même
                    reductionDao.supprimer(id);

                    // Recharger les données
                    chargerDonnees();

                    JOptionPane.showMessageDialog(null, "Réduction supprimée avec succès.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la suppression de la réduction.");
                }
            }
        });

    }

    private void chargerDonnees() {
        model.setRowCount(0); // Vider le tableau
        ArrayList<Reduction> reductions = reductionDao.getReductionsAvecAttraction();
        for (Reduction r : reductions) {
            // Récupérer les attractions liées à cette réduction
            ArrayList<Attraction> attractionsLiees = reductionDao.getAttractionsLiees(r.getId_reduction());
            StringBuilder nomsAttractions = new StringBuilder();
            for (Attraction a : attractionsLiees) {
                nomsAttractions.append(a.getNom()).append(", ");
            }
            // Enlever la dernière virgule
            if (nomsAttractions.length() > 0) {
                nomsAttractions.setLength(nomsAttractions.length() - 2);
            }

            // Ajouter les informations dans le tableau
            model.addRow(new Object[]{r.getId_reduction(), r.getNom(), r.getPourcentage(), r.getDescription(), nomsAttractions.toString()});
        }
    }


}
