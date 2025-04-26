package Controleur;

import DAO.AttractionDao;
import DAO.ReductionDao;
import DAO.DaoFactory;
import Modele.Admin;
import Modele.Attraction;
import Modele.Reduction;
import Vue.VueAdmin;
import Vue.VueAdminRA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ControleurAdminRA {

    private VueAdminRA vue;
    private Admin admin;
    private ReductionDao reductionDao;
    private AttractionDao attractionDao;

    public ControleurAdminRA(VueAdminRA vue, Admin admin) {
        this.vue = vue;
        this.admin = admin;

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        reductionDao = new ReductionDao(daoFactory);
        attractionDao = new AttractionDao(daoFactory);

        initListeners();
        chargerDonnees();
    }

    private void initListeners() {
        vue.getCompteButton().addActionListener(e -> {
            vue.dispose();
            VueAdmin vueAdmin = new VueAdmin(admin);
            vueAdmin.setVisible(true);
        });

        vue.getAjouterButton().addActionListener(e -> ajouterReduction());
        vue.getModifierButton().addActionListener(e -> modifierReduction());
        vue.getSupprimerButton().addActionListener(e -> supprimerReduction());
    }

    private void chargerDonnees() {
        DefaultTableModel model = vue.getModel();
        model.setRowCount(0);

        ArrayList<Reduction> reductions = reductionDao.getReductionsAvecAttraction();
        for (Reduction r : reductions) {
            ArrayList<Attraction> attractionsLiees = reductionDao.getAttractionsLiees(r.getId_reduction());
            StringBuilder nomsAttractions = new StringBuilder();
            for (Attraction a : attractionsLiees) {
                nomsAttractions.append(a.getNom()).append(", ");
            }
            if (nomsAttractions.length() > 0) {
                nomsAttractions.setLength(nomsAttractions.length() - 2);
            }
            model.addRow(new Object[]{r.getId_reduction(), r.getNom(), r.getPourcentage(), r.getDescription(), nomsAttractions.toString()});
        }
    }

    private void ajouterReduction() {
        JTextField nomField = new JTextField();
        JTextField pourcentageField = new JTextField();
        JTextField descriptionField = new JTextField();

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

        int res = JOptionPane.showConfirmDialog(null, fields, "Nouvelle réduction", JOptionPane.OK_CANCEL_OPTION);

        if (res == JOptionPane.OK_OPTION) {
            try {
                String nom = nomField.getText();
                int pourcentage = Integer.parseInt(pourcentageField.getText());
                String description = descriptionField.getText();

                Reduction reduction = new Reduction(nom, pourcentage, description);
                reductionDao.ajouter(reduction);

                for (JCheckBox cb : checkBoxes) {
                    if (cb.isSelected()) {
                        Attraction attraction = (Attraction) cb.getClientProperty("attraction");
                        reductionDao.lierReductionAttraction(reduction.getId_reduction(), attraction.getId_attraction());
                    }
                }

                chargerDonnees();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur : données invalides");
            }
        }
    }

    private void modifierReduction() {
        int row = vue.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Sélectionnez une réduction.");
            return;
        }

        DefaultTableModel model = vue.getModel();
        int oldId = (int) model.getValueAt(row, 0);
        String nom = (String) model.getValueAt(row, 1);
        String pourcentage = model.getValueAt(row, 2).toString();
        String description = (String) model.getValueAt(row, 3);

        JTextField nomField = new JTextField(nom);
        JTextField pourcentageField = new JTextField(pourcentage);
        JTextField descriptionField = new JTextField(description);

        ArrayList<Attraction> linkedAttractions = reductionDao.getAttractionsLiees(oldId);
        ArrayList<Attraction> nonLinkedAttractions = reductionDao.getAttractionsNonLiees(oldId);

        JPanel attractionCheckboxPanel = new JPanel();
        attractionCheckboxPanel.setLayout(new BoxLayout(attractionCheckboxPanel, BoxLayout.Y_AXIS));

        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

        for (Attraction attraction : linkedAttractions) {
            JCheckBox cb = new JCheckBox(attraction.getNom());
            cb.putClientProperty("attraction", attraction);
            cb.setSelected(true);
            checkBoxes.add(cb);
            attractionCheckboxPanel.add(cb);
        }

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
                "Modifier les attractions :", listScrollPane
        };

        int res = JOptionPane.showConfirmDialog(null, fields, "Modifier la réduction", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                String newNom = nomField.getText();
                int newPourcentage = Integer.parseInt(pourcentageField.getText());
                String newDesc = descriptionField.getText();

                reductionDao.modifier(oldId, new Reduction(oldId, newNom, newPourcentage, newDesc));
                reductionDao.supprimerLiaisonsAttractions(oldId);

                for (JCheckBox cb : checkBoxes) {
                    if (cb.isSelected()) {
                        Attraction attraction = (Attraction) cb.getClientProperty("attraction");
                        reductionDao.lierReductionAttraction(oldId, attraction.getId_attraction());
                    }
                }

                if (reductionDao.isEmpty(oldId)) {
                    reductionDao.supprimer(oldId);
                    JOptionPane.showMessageDialog(null, "Réduction supprimée, aucune attraction concernée.");
                }

                chargerDonnees();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur : données invalides");
            }
        }
    }

    private void supprimerReduction() {
        int row = vue.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Sélectionnez une réduction.");
            return;
        }

        int id = (int) vue.getModel().getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(null, "Supprimer cette réduction ?", "Confirmer", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                reductionDao.supprimerLiaisonsAttractions(id);
                reductionDao.supprimer(id);
                chargerDonnees();
                JOptionPane.showMessageDialog(null, "Réduction supprimée.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression.");
            }
        }
    }
}
