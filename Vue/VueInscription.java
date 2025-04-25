package Vue;

import javax.swing.*;
import java.awt.*;
import DAO.*;
import Modele.*;
import Controleur.*;

public class VueInscription extends JFrame {

    private JTextField txtNom, txtPrenom, txtAge, txtEmail;
    private JPasswordField txtMdp, txtMdpVerification;
    private DaoFactory daoFactory;

    public VueInscription() {
        daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");

        setTitle("Inscription");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ----------- HEADER avec les boutons de navigation -----------

        JPanel header = new JPanel(new BorderLayout());

        JPanel Pnavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JPanel Pcompte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton compte = new JButton("Compte");
        Pcompte.add(compte);

        header.add(Pnavigation, BorderLayout.WEST);
        header.add(Pcompte, BorderLayout.EAST);

        add(header, BorderLayout.NORTH); // Ajout du header avec les boutons de navigation

        compte.addActionListener(e -> {
            VueLogin vueLogin = new VueLogin();
            vueLogin.setVisible(true);
            dispose();
        });

        // ----------- FORMULAIRE D'INSCRIPTION -----------

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtNom = new JTextField();
        txtPrenom = new JTextField();
        txtAge = new JTextField();
        txtEmail = new JTextField();
        txtMdp = new JPasswordField();
        txtMdpVerification = new JPasswordField();

        formPanel.add(new JLabel("Nom :"));
        formPanel.add(txtNom);
        formPanel.add(new JLabel("Prénom :"));
        formPanel.add(txtPrenom);
        formPanel.add(new JLabel("Age :"));
        formPanel.add(txtAge);
        formPanel.add(new JLabel("E-mail :"));
        formPanel.add(txtEmail);
        formPanel.add(new JLabel("Mot de passe :"));
        formPanel.add(txtMdp);
        formPanel.add(new JLabel("Vérifier mot de passe :"));
        formPanel.add(txtMdpVerification);

        add(formPanel, BorderLayout.CENTER);

        // ----------- BOUTON D'INSCRIPTION -----------

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
        add(registerButtonPanel, BorderLayout.SOUTH); // Ajout du bouton d'inscription en bas
    }
}
