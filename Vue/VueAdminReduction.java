package Vue;

import DAO.ReductionDao;
import DAO.DaoFactory;
import Modele.Reduction;
import Modele.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class VueAdminReduction extends JFrame {

    public VueAdminReduction(Admin admin) {
        setTitle("Réductions - Admin");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        ReductionDao reductionDao = new ReductionDao(daoFactory);

        // Panneau supérieur avec boutons
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(new JButton("Accueil"));
        leftPanel.add(new JButton("Informations"));
        leftPanel.add(new JButton("Calendrier"));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(new JButton("Compte"));

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Panneau principal (contenu central)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        JLabel titreLabel = new JLabel("PalasiLand", JLabel.CENTER);
        titreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titreLabel.setFont(new Font("Serif", Font.BOLD, 28));
        mainPanel.add(titreLabel);

        // Table
        String[] columns = {"ID", "Nom", "Pourcentage", "Description"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Charger données
        List<Reduction> reductions = reductionDao.getAll();
        for (Reduction r : reductions) {
            model.addRow(new Object[]{r.getId_reduction(), r.getNom(), r.getPourcentage(), r.getDescription()});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        add(scrollPane, BorderLayout.CENTER);

        // Boutons
        JPanel buttonPanel = new JPanel();
        JButton ajouter = new JButton("Ajouter");
        JButton modifier = new JButton("Modifier");
        JButton supprimer = new JButton("Supprimer");

        buttonPanel.add(ajouter);
        buttonPanel.add(modifier);
        buttonPanel.add(supprimer);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Ajouter
        ajouter.addActionListener(e -> {
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
                    dispose();
                    new VueAdminReduction(admin).setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erreur : données invalides");
                }
            }
        });

        // Action Modifier
        modifier.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(null, "Sélectionnez une réduction.");
                return;
            }

            int id = (int) model.getValueAt(row, 0);
            String nom = (String) model.getValueAt(row, 1);
            String pourcentage = model.getValueAt(row, 2).toString();
            String description = (String) model.getValueAt(row, 3);

            JTextField nomField = new JTextField(nom);
            JTextField pourcentageField = new JTextField(pourcentage);
            JTextField descriptionField = new JTextField(description);
            Object[] fields = {
                    "Nom :", nomField,
                    "Pourcentage :", pourcentageField,
                    "Description :", descriptionField
            };
            int res = JOptionPane.showConfirmDialog(null, fields, "Modifier réduction", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                try {
                    String newNom = nomField.getText();
                    int newPourcentage = Integer.parseInt(pourcentageField.getText());
                    String newDesc = descriptionField.getText();
                    reductionDao.modifier(new Reduction(id, newNom, newPourcentage, newDesc));
                    dispose();
                    new VueAdminReduction(admin).setVisible(true);
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
                reductionDao.supprimer(id);
                dispose();
                new VueAdminReduction(admin).setVisible(true);
            }
        });
    }
}
