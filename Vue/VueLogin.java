package Vue;

import javax.swing.*;
import java.awt.*;

import Controleur.ControleurClient;
import DAO.*;
import Modele.Client;
import Modele.Admin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VueLogin extends JFrame {

    private JTextField txtNom, txtPrenom, txtAge, txtEmail;
    private JPasswordField txtMdp, txtMdpVerification;
    private DaoFactory daoFactory;

    public VueLogin() {
        daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");

        setTitle("Login");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ----------- HEADER avec les boutons de navigation -----------

        JPanel header = new JPanel(new BorderLayout());

        JPanel Pnavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton accueil = new JButton("Accueil");
        JButton informations = new JButton("Informations");
        JButton calendrier = new JButton("Calendrier");
        Pnavigation.add(accueil);
        Pnavigation.add(informations);
        Pnavigation.add(calendrier);

        JPanel Pcompte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton compte = new JButton("Compte");
        Pcompte.add(compte);

        header.add(Pnavigation, BorderLayout.WEST);
        header.add(Pcompte, BorderLayout.EAST);

        // ----------- TOP PANEL "Connexion / Inscription" -----------

        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        JPanel leftTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        leftTitlePanel.add(new JLabel("Connexion", SwingConstants.CENTER));
        JPanel rightTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        rightTitlePanel.add(new JLabel("Inscription", SwingConstants.CENTER));
        topPanel.add(leftTitlePanel);
        topPanel.add(rightTitlePanel);

        // ----------- FUSION HEADER + TOPPANEL -----------

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(header);
        northPanel.add(topPanel);

        add(northPanel, BorderLayout.NORTH);

        // ----------- ACTIONS DES BOUTONS DU HEADER -----------

        accueil.addActionListener(e -> {
            VueAccueil vueAccueil = new VueAccueil(null,null);
            vueAccueil.setVisible(true);
            dispose();
        });

        informations.addActionListener(e -> {
            VuePlusInfos vuePlusInfos = new VuePlusInfos(null, null);
            vuePlusInfos.setVisible(true);
            dispose();
        });

        calendrier.addActionListener(e -> {
            VueCalendrier vueCalendrier = new VueCalendrier(null,null);
            vueCalendrier.setVisible(true);
            dispose();
        });

        // ----------- PANNEAU CENTRAL -----------

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        add(mainPanel, BorderLayout.CENTER);

        // ----------- COLONNE DE GAUCHE : CHOIX MODE DE CONNEXION -----------

        JPanel leftPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnAdmin = new JButton("Administrateur");
        btnAdmin.addActionListener(e -> {
            dispose();
            VueConnexionAdmin vueConnexionAdmin = new VueConnexionAdmin();
            vueConnexionAdmin.setVisible(true);
        });

        JButton btnClient = new JButton("Client");
        btnClient.addActionListener(e -> {
            dispose();
            VueConnexionClient vueConnexionClient = new VueConnexionClient();
            vueConnexionClient.setVisible(true);
        });

        JButton btnGuest = new JButton("Continuer en tant qu'invité");
        btnGuest.setFocusPainted(false);
        btnGuest.setBorderPainted(false);
        btnGuest.setContentAreaFilled(false);
        btnGuest.setForeground(Color.BLUE);
        btnGuest.setFont(btnGuest.getFont().deriveFont(Font.ITALIC));
        btnGuest.addActionListener(e -> {
            dispose();
            VueAccueil accueilGuest = new VueAccueil(null,null);
            accueilGuest.setVisible(true);
        });

        leftPanel.add(btnAdmin);
        leftPanel.add(btnClient);
        leftPanel.add(btnGuest);

        // ----------- COLONNE DE DROITE : FORMULAIRE D'INSCRIPTION -----------

        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtNom = new JTextField();
        txtPrenom = new JTextField();
        txtAge = new JTextField();
        txtEmail = new JTextField();
        txtMdp = new JPasswordField();
        txtMdpVerification = new JPasswordField();

        formPanel.add(new JLabel("Nom :")); formPanel.add(txtNom);
        formPanel.add(new JLabel("Prénom :")); formPanel.add(txtPrenom);
        formPanel.add(new JLabel("Age :")); formPanel.add(txtAge);
        formPanel.add(new JLabel("E-mail :")); formPanel.add(txtEmail);
        formPanel.add(new JLabel("Mot de passe :")); formPanel.add(txtMdp);
        formPanel.add(new JLabel("Vérifier mot de passe :")); formPanel.add(txtMdpVerification);

        rightPanel.add(formPanel, BorderLayout.CENTER);

        JButton btnRegister = new JButton("S'inscrire");
        btnRegister.addActionListener(e -> {
            String nom = txtNom.getText();
            String prenom = txtPrenom.getText();
            String mdp = new String(txtMdp.getPassword());
            String mdpVerification = new String(txtMdpVerification.getPassword());
            String email = txtEmail.getText();

            if (!mdp.equals(mdpVerification)) {
                JOptionPane.showMessageDialog(null, "Les mots de passe ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int age;
            try {
                age = Integer.parseInt(txtAge.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "L'âge doit être un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ClientDao clientDao = new ClientDao(daoFactory);
            if (clientDao.emailExiste(email)) {
                JOptionPane.showMessageDialog(null, "Cet e-mail est déjà utilisé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Client client = new Client();
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setEmail(email);
            client.setMdp(mdp);
            client.setAge(age);

            clientDao.inscrire(client);
            JOptionPane.showMessageDialog(null, "Inscription réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            VueClient vueClient = new VueClient(client);
            new ControleurClient(vueClient, client);
            vueClient.setVisible(true);
            dispose();
        });

        JPanel registerButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerButtonPanel.add(btnRegister);
        rightPanel.add(registerButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
    }
}
