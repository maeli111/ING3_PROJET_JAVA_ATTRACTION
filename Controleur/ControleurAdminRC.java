package Controleur;

import Vue.*;
import DAO.*;
import Modele.*;

import javax.swing.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;


public class ControleurAdminRC {

    private VueAdminRC vue;
    private Admin admin;
    private ReductionDao reductionDao;

    public ControleurAdminRC(VueAdminRC vue, Admin admin) {
        this.vue = vue;
        this.admin = admin;

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        this.reductionDao = new ReductionDao(daoFactory);

        chargerDonnees();

        // Événements
        vue.getBtnAjouter().addActionListener(e -> ajouterReduction());
        vue.getBtnModifier().addActionListener(e -> modifierReduction());
        vue.getBtnSupprimer().addActionListener(e -> supprimerReduction());

        vue.getBtnCompte().addActionListener(e -> {
            vue.dispose();
            VueAdmin va = new VueAdmin(admin);
            new ControleurAdmin(va, admin);
            va.setVisible(true);
        });
    }

    private void chargerDonnees() {
        DefaultTableModel model = vue.getModel();
        model.setRowCount(0);
        List<Reduction> reductions = reductionDao.getReductionsSansAttraction();
        for (Reduction r : reductions) {
            model.addRow(new Object[]{r.getId_reduction(), r.getNom(), r.getPourcentage(), r.getDescription()});
        }
    }

    private void ajouterReduction() {
        JTextField nomField = new JTextField();
        JTextField pourcentageField = new JTextField();
        JTextField descriptionField = new JTextField();
        Object[] fields = {
                "Nom :", nomField,
                "Pourcentage :", pourcentageField,
                "Description :", descriptionField
        };
        int res = JOptionPane.showConfirmDialog(null, fields, "Nouvelle réduction", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                String nom = nomField.getText();
                int pourcentage = Integer.parseInt(pourcentageField.getText());
                String description = descriptionField.getText();
                reductionDao.ajouter(new Reduction(nom, pourcentage, description));
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

        JTextField idField = new JTextField(String.valueOf(oldId));
        JTextField nomField = new JTextField(nom);
        JTextField pourcentageField = new JTextField(pourcentage);
        JTextField descriptionField = new JTextField(description);

        Object[] fields = {
                "ID :", idField,
                "Nom :", nomField,
                "Pourcentage :", pourcentageField,
                "Description :", descriptionField
        };

        int res = JOptionPane.showConfirmDialog(null, fields, "Modifier réduction", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                int newId = Integer.parseInt(idField.getText());
                String newNom = nomField.getText();
                int newPourcentage = Integer.parseInt(pourcentageField.getText());
                String newDesc = descriptionField.getText();
                reductionDao.modifier(oldId, new Reduction(newId, newNom, newPourcentage, newDesc));
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
            reductionDao.supprimer(id);
            chargerDonnees();
        }
    }
}
