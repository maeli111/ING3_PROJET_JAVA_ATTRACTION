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
        //Bouton pour retourner sur le compte de l'admin
        vue.getCompteButton().addActionListener(e -> {
            vue.dispose();
            VueAdmin vueAdmin = new VueAdmin(admin);
            new ControleurAdmin(vueAdmin, admin);
            vueAdmin.setVisible(true);
        });

        vue.getAjouterButton().addActionListener(e -> ajouterReduction());
        vue.getModifierButton().addActionListener(e -> modifierReduction());
        vue.getSupprimerButton().addActionListener(e -> supprimerReduction());
    }

    //Méthode qui charge toutes les réductions liées à une ou des attrcations dans une table
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

    //Méthode pour ajouter une réduction liées à une ou des attractions
    private void ajouterReduction() {
        JTextField nomField = new JTextField();
        JTextField pourcentageField = new JTextField();
        JTextField descriptionField = new JTextField();

        ArrayList<Attraction> attractions = attractionDao.getAll();
        JPanel attractionPanel = new JPanel();
        attractionPanel.setLayout(new BoxLayout(attractionPanel, BoxLayout.Y_AXIS));

        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        for (Attraction attraction : attractions) {
            JCheckBox cb = new JCheckBox(attraction.getNom());
            cb.putClientProperty("attraction", attraction);
            checkBoxes.add(cb);
            attractionPanel.add(cb);
        }

        JScrollPane ScrollPane = new JScrollPane(attractionPanel);
        ScrollPane.setPreferredSize(new Dimension(300, 150));

        Object[] fields = {
                "Nom :", nomField,
                "Pourcentage :", pourcentageField,
                "Description :", descriptionField,
                "Sélectionnez les attractions :", ScrollPane
        };

        int nvReduc = JOptionPane.showConfirmDialog(null, fields, "Nouvelle réduction", JOptionPane.OK_CANCEL_OPTION);

        if (nvReduc == JOptionPane.OK_OPTION) {
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

    //Méthode pour modifier une réduction liées à une ou plusieurs attractions
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

        JTextField nomField = new JTextField(nom);
        JTextField pourcentageField = new JTextField(pourcentage);
        JTextField descriptionField = new JTextField(description);

        ArrayList<Attraction> AttractionsLies = reductionDao.getAttractionsLiees(ancienneId);
        ArrayList<Attraction> AttractionPasLies = reductionDao.getAttractionsNonLiees(ancienneId);

        JPanel attractionPanel = new JPanel();
        attractionPanel.setLayout(new BoxLayout(attractionPanel, BoxLayout.Y_AXIS));

        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

        for (Attraction attraction : AttractionsLies) {
            JCheckBox cb = new JCheckBox(attraction.getNom());
            cb.putClientProperty("attraction", attraction);
            cb.setSelected(true);
            checkBoxes.add(cb);
            attractionPanel.add(cb);
        }

        for (Attraction attraction : AttractionPasLies) {
            JCheckBox cb = new JCheckBox(attraction.getNom());
            cb.putClientProperty("attraction", attraction);
            checkBoxes.add(cb);
            attractionPanel.add(cb);
        }

        JScrollPane listScrollPane = new JScrollPane(attractionPanel);
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
                String nvNom = nomField.getText();
                int nvPourcentage = Integer.parseInt(pourcentageField.getText());
                String nvDesc = descriptionField.getText();

                reductionDao.modifier(ancienneId, new Reduction(ancienneId, nvNom, nvPourcentage, nvDesc));
                reductionDao.supprimerLiaisonsAttractions(ancienneId);

                for (JCheckBox cb : checkBoxes) {
                    if (cb.isSelected()) {
                        Attraction attraction = (Attraction) cb.getClientProperty("attraction");
                        reductionDao.lierReductionAttraction(ancienneId, attraction.getId_attraction());
                    }
                }

                if (reductionDao.isEmpty(ancienneId)) {
                    reductionDao.supprimer(ancienneId);
                    JOptionPane.showMessageDialog(null, "Réduction supprimée, aucune attraction concernée.");
                }

                chargerDonnees();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur : données invalides");
            }
        }
    }

    //Méthode qui supprime une attraction
    private void supprimerReduction() {
        int ligne = vue.getTable().getSelectedRow();
        if (ligne == -1) {
            JOptionPane.showMessageDialog(null, "Sélectionnez une réduction.");
            return;
        }

        int id = (int) vue.getModel().getValueAt(ligne, 0);
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
