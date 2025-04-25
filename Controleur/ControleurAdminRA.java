package Controleur;

import Vue.*;
import Modele.*;
import DAO.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ControleurAdminRA {
    private VueAdminRA vueAdmin;
    private Admin admin;

    public ControleurAdminRA(VueAdminRA vueAdmin, Admin admin) {
        this.vueAdmin = vueAdmin;
        this.admin = admin;

        // Connexion à la base de données via DaoFactory
        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");

        // Créer une instance d'AttractionDao pour interagir avec la base de données
        AttractionDao attractionDao = new AttractionDao(daoFactory);

        // Récupérer toutes les attractions en appelant getAll() sur l'instance d'AttractionDao
        ArrayList<Attraction> attractions = attractionDao.getAll();

        // Passer cette liste d'attractions à la vue
        vueAdmin.chargerAttractions(attractions);

        // Initialiser les actions des boutons (optionnel)
        ajouterListeners();
    }

    // Ajouter vos listeners pour les événements des boutons
    private void ajouterListeners() {

        vueAdmin.getCompteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vueAdmin.dispose();
                VueAdmin vueAdmin = new VueAdmin(admin);
                new ControleurAdmin(vueAdmin, admin);
                vueAdmin.setVisible(true);
            }
        });

        // Exemple de listener pour le bouton "Ajouter Attraction"
        vueAdmin.getAjouterButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField nomField = new JTextField();
                JTextField descriptionField = new JTextField();
                JTextField prixField = new JTextField();
                JTextField capaciteField = new JTextField();
                JTextField typeAttractionField = new JTextField();

                Object[] message = {
                        "Nom:", nomField,
                        "Description:", descriptionField,
                        "Prix:", prixField,
                        "Capacité:", capaciteField,
                        "Type d'attraction:", typeAttractionField
                };

                int option = JOptionPane.showConfirmDialog(vueAdmin, message, "Ajouter une attraction", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String nom = nomField.getText();
                    String description = descriptionField.getText();
                    double prix = Double.parseDouble(prixField.getText());
                    int capacite = Integer.parseInt(capaciteField.getText());
                    String typeAttraction = typeAttractionField.getText();

                    Attraction nouvelleAttraction = new Attraction(nom, description, prix, capacite, typeAttraction);
                    AttractionDao attractionDao = new AttractionDao(DaoFactory.getInstance("java_attraction", "root", ""));
                    attractionDao.ajouter(nouvelleAttraction);

                    // Recharger les attractions après ajout
                    ArrayList<Attraction> attractions = attractionDao.getAll();
                    vueAdmin.chargerAttractions(attractions);
                }
            }
        });

        // Exemple de listener pour le bouton "Modifier Attraction"
        vueAdmin.getModifierButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = vueAdmin.getTable().getSelectedRow();
                if (selectedRow >= 0) {
                    int idAttraction = Integer.parseInt(vueAdmin.getTableModel().getValueAt(selectedRow, 0).toString());
                    AttractionDao attractionDao = new AttractionDao(DaoFactory.getInstance("java_attraction", "root", ""));
                    Attraction attractionExistant = attractionDao.getById(idAttraction);

                    // Afficher un formulaire pour modifier les informations
                    JTextField nomField = new JTextField(attractionExistant.getNom());
                    JTextField descriptionField = new JTextField(attractionExistant.getDescription());
                    JTextField prixField = new JTextField(String.valueOf(attractionExistant.getPrix()));
                    JTextField capaciteField = new JTextField(String.valueOf(attractionExistant.getCapacite()));
                    JTextField typeAttractionField = new JTextField(attractionExistant.getType_attraction());

                    Object[] message = {
                            "Nom:", nomField,
                            "Description:", descriptionField,
                            "Prix:", prixField,
                            "Capacité:", capaciteField,
                            "Type d'attraction:", typeAttractionField
                    };

                    int option = JOptionPane.showConfirmDialog(vueAdmin, message, "Modifier l'attraction", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        attractionExistant.setNom(nomField.getText());
                        attractionExistant.setDescription(descriptionField.getText());
                        attractionExistant.setPrix(Double.parseDouble(prixField.getText()));
                        attractionExistant.setCapacite(Integer.parseInt(capaciteField.getText()));
                        attractionExistant.setType_attraction(typeAttractionField.getText());

                        attractionDao.modifier(attractionExistant);

                        // Recharger les attractions après modification
                        ArrayList<Attraction> attractions = attractionDao.getAll();
                        vueAdmin.chargerAttractions(attractions);
                    }
                } else {
                    JOptionPane.showMessageDialog(vueAdmin, "Veuillez sélectionner une attraction à modifier.");
                }
            }
        });

        // Exemple de listener pour le bouton "Supprimer Attraction"
        vueAdmin.getSupprimerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = vueAdmin.getTable().getSelectedRow();
                if (selectedRow >= 0) {
                    int idAttraction = Integer.parseInt(vueAdmin.getTableModel().getValueAt(selectedRow, 0).toString());
                    AttractionDao attractionDao = new AttractionDao(DaoFactory.getInstance("java_attraction", "root", ""));
                    attractionDao.supprimer(idAttraction);

                    // Recharger les attractions après suppression
                    ArrayList<Attraction> attractions = attractionDao.getAll();
                    vueAdmin.chargerAttractions(attractions);
                } else {
                    JOptionPane.showMessageDialog(vueAdmin, "Veuillez sélectionner une attraction à supprimer.");
                }
            }
        });
    }
}
