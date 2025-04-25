package Vue;

import Controleur.*;
import DAO.AttractionDao;
import DAO.DaoFactory;
import Modele.Admin;
import Modele.Attraction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VueAdminAttraction extends JFrame {
    private AttractionDao attractionDao;
    private JTable table;
    private DefaultTableModel tableModel;

    public VueAdminAttraction(Admin admin) {
        setTitle("Attractions - Admin");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        attractionDao = new AttractionDao(daoFactory);

        // --- TOP PANEL ---
        JPanel topPanel = new JPanel(new BorderLayout());
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
        compteButton.addActionListener(e -> {
            VueAdmin vueAdmin = new VueAdmin(admin);
            ControleurAdmin controleurAdmin = new ControleurAdmin(vueAdmin, admin);
            vueAdmin.setVisible(true);
            dispose();
        });

        buttonBar.add(leftPanel, BorderLayout.WEST);
        buttonBar.add(rightPanel, BorderLayout.EAST);

        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titreLabel = new JLabel("PalasiLand - Gestion des attractions");
        titreLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titrePanel.add(titreLabel);

        topPanel.add(buttonBar, BorderLayout.NORTH);
        topPanel.add(titrePanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // --- TABLE ---
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Description", "Prix", "Capacité", "Type"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // --- BOTTOM PANEL ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton ajouterButton = new JButton("Ajouter");
        JButton modifierButton = new JButton("Modifier");
        JButton supprimerButton = new JButton("Supprimer");
        bottomPanel.add(ajouterButton);
        bottomPanel.add(modifierButton);
        bottomPanel.add(supprimerButton);
        add(bottomPanel, BorderLayout.SOUTH);

        chargerAttractions();

        ajouterButton.addActionListener(e -> {
            JTextField nomField = new JTextField();
            JTextField descriptionField = new JTextField();
            JTextField prixField = new JTextField();
            JTextField capaciteField = new JTextField();
            JTextField typeField = new JTextField();

            Object[] message = {
                    "Nom:", nomField,
                    "Description:", descriptionField,
                    "Prix:", prixField,
                    "Capacité:", capaciteField,
                    "Type:", typeField
            };

            int option = JOptionPane.showConfirmDialog(this, message, "Ajouter une attraction", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String nom = nomField.getText();
                    String desc = descriptionField.getText();
                    double prix = Double.parseDouble(prixField.getText());
                    int capacite = Integer.parseInt(capaciteField.getText());
                    String type = typeField.getText();

                    Attraction nouvelle = new Attraction(0, nom, desc, prix, capacite, type);
                    attractionDao.ajouter(nouvelle);

                    dispose();
                    new VueAdminAttraction(admin).setVisible(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs numériques valides pour le prix et la capacité.");
                }
            }
        });

        modifierButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                Attraction existante = attractionDao.getById(id);

                JTextField nomField = new JTextField(existante.getNom());
                JTextField descField = new JTextField(existante.getDescription());
                JTextField prixField = new JTextField(String.valueOf(existante.getPrix()));
                JTextField capaciteField = new JTextField(String.valueOf(existante.getCapacite()));
                JTextField typeField = new JTextField(existante.getType_attraction());

                Object[] message = {
                        "Nom:", nomField,
                        "Description:", descField,
                        "Prix:", prixField,
                        "Capacité:", capaciteField,
                        "Type:", typeField
                };

                int option = JOptionPane.showConfirmDialog(this, message, "Modifier l'attraction", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        existante.setNom(nomField.getText());
                        existante.setDescription(descField.getText());
                        existante.setPrix(Double.parseDouble(prixField.getText()));
                        existante.setCapacite(Integer.parseInt(capaciteField.getText()));
                        existante.setType_attraction(typeField.getText());

                        attractionDao.modifier(existante);

                        dispose();
                        new VueAdminAttraction(admin).setVisible(true);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs numériques valides pour le prix et la capacité.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une attraction à modifier.");
            }
        });

        supprimerButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                attractionDao.supprimer(id);
                dispose();
                new VueAdminAttraction(admin).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une attraction à supprimer.");
            }
        });
    }

    private void chargerAttractions() {
        tableModel.setRowCount(0);
        ArrayList<Attraction> attractions = attractionDao.getAll();
        for (Attraction a : attractions) {
            tableModel.addRow(new Object[]{
                    a.getId_attraction(),
                    a.getNom(),
                    a.getDescription(),
                    a.getPrix(),
                    a.getCapacite(),
                    a.getType_attraction()
            });
        }
    }
}
