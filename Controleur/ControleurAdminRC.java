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

    //Constructeur
    public ControleurAdminRC(VueAdminRC vue, Admin admin) {
        this.vue = vue;
        this.admin = admin;

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        this.reductionDao = new ReductionDao(daoFactory);

        chargerDonnees();

        vue.getBtnAjouter().addActionListener(e -> ajouterReduction());
        vue.getBtnModifier().addActionListener(e -> modifierReduction());
        vue.getBtnSupprimer().addActionListener(e -> supprimerReduction());

        //Bouton qui retourne sur le compte de l'admin
        vue.getBtnCompte().addActionListener(e -> {
            vue.dispose();
            VueAdmin va = new VueAdmin(admin);
            new ControleurAdmin(va, admin);
            va.setVisible(true);
        });
    }

    //Méthode qui charge les réductions liées aux clients dans une table
    private void chargerDonnees() {
        DefaultTableModel model = vue.getModel();
        model.setRowCount(0);
        ArrayList<Reduction> reductions = reductionDao.getReductionsSansAttraction();
        for (Reduction r : reductions) {
            model.addRow(new Object[]{r.getId_reduction(), r.getNom(), r.getPourcentage(), r.getDescription()});
        }
    }

    //Méthode qui ajoute une réduction liées aux clients
    private void ajouterReduction() {
        JTextField nomField = new JTextField();
        JTextField pourcentageField = new JTextField();
        JTextField descriptionField = new JTextField();
        Object[] fields = {
                "Nom :", nomField,
                "Pourcentage :", pourcentageField,
                "Description :", descriptionField
        };
        int ok = JOptionPane.showConfirmDialog(null, fields, "Nouvelle réduction", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
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

    //Méthode qui modifie une réduction liées aux clients
    private void modifierReduction() {
        int ligne = vue.getTable().getSelectedRow();
        if (ligne == -1) {
            JOptionPane.showMessageDialog(null, "Sélectionnez une réduction.");
            return;
        }

        DefaultTableModel model = vue.getModel();
        int ancienneId = (int) model.getValueAt(ligne, 0);
        String nom = (String) model.getValueAt(ligne, 1);
        String pourcentage = model.getValueAt(ligne, 2).toString();
        String description = (String) model.getValueAt(ligne, 3);

        JTextField idField = new JTextField(String.valueOf(ancienneId));
        JTextField nomField = new JTextField(nom);
        JTextField pourcentageField = new JTextField(pourcentage);
        JTextField descriptionField = new JTextField(description);

        Object[] fields = {
                "ID :", idField,
                "Nom :", nomField,
                "Pourcentage :", pourcentageField,
                "Description :", descriptionField
        };

        int ok = JOptionPane.showConfirmDialog(null, fields, "Modifier réduction", JOptionPane.OK_CANCEL_OPTION);
        if (ok == JOptionPane.OK_OPTION) {
            try {
                int nvId = Integer.parseInt(idField.getText());
                String nvNom = nomField.getText();
                int nvPourcentage = Integer.parseInt(pourcentageField.getText());
                String nvDesc = descriptionField.getText();
                reductionDao.modifier(ancienneId, new Reduction(nvId, nvNom, nvPourcentage, nvDesc));
                chargerDonnees();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur : données invalides");
            }
        }
    }

    //Méthode qui supprime une réduction liées aux clients
    private void supprimerReduction() {
        int ligne = vue.getTable().getSelectedRow();
        if (ligne == -1) {
            JOptionPane.showMessageDialog(null, "Sélectionnez une réduction.");
            return;
        }

        int id = (int) vue.getModel().getValueAt(ligne, 0);
        int confirm = JOptionPane.showConfirmDialog(null, "Supprimer cette réduction ?", "Confirmer", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            reductionDao.supprimer(id);
            chargerDonnees();
        }
    }
}
