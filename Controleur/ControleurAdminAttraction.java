package Controleur;

import Vue.*;
import Modele.Admin;
import Modele.Attraction;
import DAO.AttractionDao;
import DAO.DaoFactory;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ControleurAdminAttraction {
    private VueAdminAttraction vue;
    private Admin admin;
    private AttractionDao attractionDao;

    // Constructeur
    public ControleurAdminAttraction(VueAdminAttraction vue, Admin admin) {
        this.vue = vue;
        this.admin = admin;
        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        attractionDao = new AttractionDao(daoFactory);

        ajouterListeners();
        vue.setVisible(true);
        chargerAttractions();
    }

    private void ajouterListeners() {

        // Bouton pour revenir au compte Admin
        vue.getCompteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vue.dispose();
                VueAdmin vueAdmin = new VueAdmin(admin);
                new ControleurAdmin(vueAdmin, admin);
                vueAdmin.setVisible(true);
            }
        });

        // Bouton pour ajouter une nv attraction
        vue.getAjouterButton().addActionListener(e -> {
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

            int option = JOptionPane.showConfirmDialog(vue, message, "Ajouter une attraction", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    // On récupère le nom,description,prix, capacité et type de l'attraction saisie par l'admin
                    String nom = nomField.getText();
                    String desc = descriptionField.getText();
                    double prix = Double.parseDouble(prixField.getText());
                    int capacite = Integer.parseInt(capaciteField.getText());
                    String type = typeField.getText();

                    // Puis on crée et ajoute cette nv attraction
                    Attraction nouvelle = new Attraction(0, nom, desc, prix, capacite, type);
                    attractionDao.ajouter(nouvelle);
                    chargerAttractions();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(vue, "Veuillez entrer des valeurs numériques valides pour le prix et la capacité.");
                }
            }
        });

        // Bouton pour modifier une des attractions
        vue.getModifierButton().addActionListener(e -> {
            int ligne = vue.getTable().getSelectedRow(); // on prend la ligne choisie
            if (ligne >= 0) {
                int id = Integer.parseInt(vue.getTableModel().getValueAt(ligne, 0).toString());
                Attraction existante = attractionDao.getById(id); // et on récupère l'attraction qui correspond à cette ligne choisie

                // On remplit les lignes avec les données de l'attraction
                JTextField nomField = new JTextField(existante.getNom());
                JTextField descriptionField = new JTextField(existante.getDescription());
                JTextField prixField = new JTextField(String.valueOf(existante.getPrix()));
                JTextField capaciteField = new JTextField(String.valueOf(existante.getCapacite()));
                JTextField typeField = new JTextField(existante.getType_attraction());

                Object[] message = {
                        "Nom:", nomField,
                        "Description:", descriptionField,
                        "Prix:", prixField,
                        "Capacité:", capaciteField,
                        "Type:", typeField
                };

                int option = JOptionPane.showConfirmDialog(vue, message, "Modifier l'attraction", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        // On met à jour l'attraction
                        existante.setNom(nomField.getText());
                        existante.setDescription(descriptionField.getText());
                        existante.setPrix(Double.parseDouble(prixField.getText()));
                        existante.setCapacite(Integer.parseInt(capaciteField.getText()));
                        existante.setType_attraction(typeField.getText());

                        attractionDao.modifier(existante);
                        chargerAttractions();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(vue, "Veuillez entrer des valeurs numériques valides pour le prix et la capacité.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(vue, "Veuillez sélectionner une attraction à modifier.");
            }
        });

        // Bouton pour supprimer une attraction
        vue.getSupprimerButton().addActionListener(e -> {
            int ligne = vue.getTable().getSelectedRow();
            if (ligne >= 0) {
                int id = Integer.parseInt(vue.getTableModel().getValueAt(ligne, 0).toString());
                attractionDao.supprimer(id);
                chargerAttractions();
            } else {
                JOptionPane.showMessageDialog(vue, "Veuillez sélectionner une attraction à supprimer.");
            }
        });
    }

    // Méthode qui charge les attractions dans la table
    private void chargerAttractions() {
        vue.getTableModel().setRowCount(0);
        ArrayList<Attraction> attractions = attractionDao.getAll();
        for (Attraction attraction : attractions) {
            vue.getTableModel().addRow(new Object[] {
                    attraction.getId_attraction(),
                    attraction.getNom(),
                    attraction.getDescription(),
                    attraction.getPrix(),
                    attraction.getCapacite(),
                    attraction.getType_attraction()
            });
        }
    }
}
