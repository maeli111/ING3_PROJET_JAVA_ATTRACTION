package Controleur;

import Vue.*;
import Modele.Admin;
import Modele.Client;
import DAO.ClientDao;
import DAO.DaoFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControleurAdminClient {
    private VueAdminClient vue;
    private Admin admin;
    private ClientDao clientDao;

    //Constructeur
    public ControleurAdminClient(VueAdminClient vue, Admin admin) {
        this.vue = vue;
        this.admin = admin;

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        clientDao = new ClientDao(daoFactory);

        ajouterListeners();
        vue.setVisible(true);
        chargerClients();
    }

    private void ajouterListeners() {

        //retour sur le compte de l'admin
        vue.getCompteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vue.dispose();
                VueAdmin vueAdmin = new VueAdmin(admin);
                new ControleurAdmin(vueAdmin, admin);
                vueAdmin.setVisible(true);
            }
        });

        //Bouton qui ajoute un client
        vue.getAjouterButton().addActionListener(e -> {
            JTextField nomField = new JTextField();
            JTextField prenomField = new JTextField();
            JTextField emailField = new JTextField();
            JPasswordField mdpField = new JPasswordField();
            JTextField ageField = new JTextField();
            JTextField typeClientField = new JTextField();

            Object[] message = {
                    "Nom:", nomField,
                    "Prénom:", prenomField,
                    "Email:", emailField,
                    "Mot de passe:", mdpField,
                    "Âge:", ageField,
                    "Type de client:", typeClientField
            };

            int option = JOptionPane.showConfirmDialog(vue, message, "Ajouter un client", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String email = emailField.getText();
                String mdp = new String(mdpField.getPassword());
                int age = Integer.parseInt(ageField.getText());
                String typeClient = typeClientField.getText();

                Client nouveauClient = new Client(nom, prenom, email, mdp, age, typeClient);
                clientDao.ajouter(nouveauClient);

                chargerClients();
            }
        });

        //Bouton qui modifie un client
        vue.getModifierButton().addActionListener(e -> {
            int ligne = vue.getTable().getSelectedRow();
            if (ligne >= 0) {
                int idClient = Integer.parseInt(vue.getTableModel().getValueAt(ligne, 0).toString());
                Client clientExistant = clientDao.getById(idClient);

                JTextField nomField = new JTextField(clientExistant.getNom());
                JTextField prenomField = new JTextField(clientExistant.getPrenom());
                JTextField emailField = new JTextField(clientExistant.getEmail());
                JPasswordField mdpField = new JPasswordField(clientExistant.getMdp());
                JTextField ageField = new JTextField(String.valueOf(clientExistant.getage()));
                JTextField typeClientField = new JTextField(clientExistant.getType_client());

                Object[] message = {
                        "Nom:", nomField,
                        "Prénom:", prenomField,
                        "Email:", emailField,
                        "Mot de passe:", mdpField,
                        "Âge:", ageField,
                        "Type de client:", typeClientField
                };

                int option = JOptionPane.showConfirmDialog(vue, message, "Modifier le client", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    clientExistant.setNom(nomField.getText());
                    clientExistant.setPrenom(prenomField.getText());
                    clientExistant.setEmail(emailField.getText());
                    clientExistant.setMdp(new String(mdpField.getPassword()));
                    clientExistant.setAge(Integer.parseInt(ageField.getText()));
                    clientExistant.setType_client(typeClientField.getText());

                    clientDao.modifier(clientExistant);
                    chargerClients();
                }
            } else {
                JOptionPane.showMessageDialog(vue, "Veuillez sélectionner un client à modifier.");
            }
        });

        //Bouton qui supprime un client
        vue.getSupprimerButton().addActionListener(e -> {
            int ligne = vue.getTable().getSelectedRow();
            if (ligne >= 0) {
                int idClient = Integer.parseInt(vue.getTableModel().getValueAt(ligne, 0).toString());
                clientDao.supprimer(idClient);
                chargerClients();
            } else {
                JOptionPane.showMessageDialog(vue, "Veuillez sélectionner un client à supprimer.");
            }
        });
    }

    // Méthode qui charge la liste de tous les clients dans vue
    private void chargerClients() {
        ArrayList<Client> clients = clientDao.getAll();
        vue.chargerClients(clients);
    }
}
