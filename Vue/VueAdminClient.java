package Vue;

import DAO.ClientDao;
import DAO.DaoFactory;
import Modele.Admin;
import Modele.Client;
import Controleur.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

    public class VueAdminClient extends JFrame {
        private ClientDao clientDao;
        private JTable table;
        private DefaultTableModel tableModel;

        public VueAdminClient(Admin admin) {
            setTitle("Clients - Admin");
            setSize(900, 500);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
            clientDao = new ClientDao(daoFactory);

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

            // Action du bouton Compte
            compteButton.addActionListener(e -> {
                VueAdmin vueAdmin = new VueAdmin(admin);
                ControleurAdmin controleurAdmin = new ControleurAdmin(vueAdmin, admin);
                vueAdmin.setVisible(true);
                dispose();
            });

            buttonBar.add(leftPanel, BorderLayout.WEST);
            buttonBar.add(rightPanel, BorderLayout.EAST);

            // --- TITRE ---
            JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel titreLabel = new JLabel("PalasiLand - Gestion des clients");
            titreLabel.setFont(new Font("Serif", Font.BOLD, 24));
            titrePanel.add(titreLabel);

            topPanel.add(buttonBar, BorderLayout.NORTH);
            topPanel.add(titrePanel, BorderLayout.CENTER);
            add(topPanel, BorderLayout.NORTH);

            // --- TABLE DES CLIENTS ---
            tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Prénom", "Email", "Âge", "Mot de passe", "Type de client"}, 0);
            table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);

            // --- BOUTONS EN BAS ---
            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton ajouterButton = new JButton("Ajouter");
            JButton modifierButton = new JButton("Modifier");
            JButton supprimerButton = new JButton("Supprimer");
            bottomPanel.add(ajouterButton);
            bottomPanel.add(modifierButton);
            bottomPanel.add(supprimerButton);
            add(bottomPanel, BorderLayout.SOUTH);

            // Charger les données des clients
            chargerClients();


            ajouterButton.addActionListener(e -> {
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

            int option = JOptionPane.showConfirmDialog(this, message, "Ajouter un client", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String nom = nomField.getText();
                String prenom = prenomField.getText();
                String email = emailField.getText();
                String mdp = new String(mdpField.getPassword());
                int age = Integer.parseInt(ageField.getText());
                String typeClient = typeClientField.getText();

                Client nouveauClient = new Client(nom, prenom, email, mdp, age, typeClient); // constructeur adapté
                clientDao.ajouter(nouveauClient);

                dispose();
                new VueAdminClient(admin).setVisible(true);
            }
        });


        modifierButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int idClient = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
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

                int option = JOptionPane.showConfirmDialog(this, message, "Modifier le client", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    clientExistant.setNom(nomField.getText());
                    clientExistant.setPrenom(prenomField.getText());
                    clientExistant.setEmail(emailField.getText());
                    clientExistant.setMdp(new String(mdpField.getPassword()));
                    clientExistant.setAge(Integer.parseInt(ageField.getText()));
                    clientExistant.setType_client(typeClientField.getText());

                    clientDao.modifier(clientExistant);

                    dispose();
                    new VueAdminClient(admin).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client à modifier.");
            }
        });


        // --- ACTION BOUTON SUPPRIMER ---
        supprimerButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int idClient = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                clientDao.supprimer(idClient);

                dispose();
                new VueAdminClient(admin).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client à supprimer.");
            }
        });
    }

        private void chargerClients() {
            // Vider la table avant de la remplir
            tableModel.setRowCount(0);

            ArrayList<Client> clients = clientDao.getAll();
            for (Client c : clients) {
                // Ajout des nouvelles informations (Âge, Mot de passe, Type de client)
                tableModel.addRow(new Object[]{
                        c.getid_client(),      // ID
                        c.getNom(),            // Nom
                        c.getPrenom(),         // Prénom
                        c.getEmail(),          // Email
                        c.getage(),            // Âge
                        c.getMdp(),            // Mot de passe
                        c.getType_client()     // Type de client
                });
            }
        }

}
