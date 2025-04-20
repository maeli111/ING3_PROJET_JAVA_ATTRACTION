package Vue;

import javax.swing.*;
import java.awt.*;
import DAO.*;
import Modele.Client;
import Modele.Admin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JTextField txtNom, txtPrenom, txtAge, txtEmail;
    private JPasswordField txtMdp, txtMdpVerification;
    private DaoFactory daoFactory;

    public Login() {
        daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");

        setTitle("Login");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //Partie en haut avec les titres "Connexion" et "Inscription"
        JPanel topPanel = new JPanel(new GridLayout(1, 2)); // One row, two columns

        JPanel leftTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        leftTitlePanel.add(new JLabel("Connexion", SwingConstants.CENTER));

        JPanel rightTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        rightTitlePanel.add(new JLabel("Inscription", SwingConstants.CENTER));

        topPanel.add(leftTitlePanel);
        topPanel.add(rightTitlePanel);

        //panneau principal au centre
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // One row, two columns

        //colonne de gauche pour le choix du mode de connexion (client/admin/invité)
        JPanel leftPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // Four sections vertically
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add some padding

        JButton btnAdmin = new JButton("Administrateur");
        leftPanel.add(btnAdmin);

        btnAdmin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Admin admin = new Admin();

                dispose(); // Ferme la fenêtre Login
                VueConnexionAdmin vueConnexionAdmin = new VueConnexionAdmin();
                vueConnexionAdmin.setVisible(true);
            }
        });

        JButton btnClient = new JButton("Client");
        leftPanel.add(btnClient);

        btnClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client = new Client();

                dispose(); // Ferme la fenêtre Login
                VueConnexionClient vueConnexionClient = new VueConnexionClient();
                vueConnexionClient.setVisible(true);
            }
        });


        JButton btnGuest = new JButton("Continuer en tant qu'invité");
        btnGuest.setFocusPainted(false);
        btnGuest.setBorderPainted(false);
        btnGuest.setContentAreaFilled(false);
        btnGuest.setForeground(Color.BLUE);
        btnGuest.setFont(btnGuest.getFont().deriveFont(Font.ITALIC));
        leftPanel.add(btnGuest);

        btnGuest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dispose(); // Ferme la fenêtre Login
                Accueil accueil = new Accueil();
                accueil.setVisible(true);
            }
        });

        //colonne de droite pour s'inscrire
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Nom :"));
        txtNom = new JTextField();
        formPanel.add(txtNom);

        formPanel.add(new JLabel("Prénom :"));
        txtPrenom = new JTextField();
        formPanel.add(txtPrenom);

        formPanel.add(new JLabel("Age :"));
        txtAge = new JTextField();
        formPanel.add(txtAge);

        formPanel.add(new JLabel("E-mail :"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Mot de passe :"));
        txtMdp = new JPasswordField();
        formPanel.add(txtMdp);

        formPanel.add(new JLabel("Vérifier mot de passe :"));
        txtMdpVerification = new JPasswordField();
        formPanel.add(txtMdpVerification);

        rightPanel.add(formPanel, BorderLayout.CENTER);

        JPanel registerButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnRegister = new JButton("S'inscrire");

        // action quand on clique sur "s'inscrire"
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //on récupère ce que l'utilisateur a écrit
                String nom = txtNom.getText();
                String prenom = txtPrenom.getText();
                String mdp = new String(txtMdp.getPassword());
                String mdpVerification = new String(txtMdpVerification.getPassword());

                //On vérifie que les mdp soient identiques
                if (!mdp.equals(mdpVerification)) {
                    JOptionPane.showMessageDialog(null, "Les mots de passe ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //On vérifie que l'age est un entier
                int age;
                try {
                    age = Integer.parseInt(txtAge.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "L'âge doit être un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //On vérifie que le mail n'est pas déjà dans la bdd
                String email = txtEmail.getText();
                ClientDao clientDao = new ClientDao(daoFactory);
                if (clientDao.emailExiste(email)) {
                    JOptionPane.showMessageDialog(null, "Cet e-mail est déjà utilisé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Si tout est validé on crée le client et on l'inscrit da,s la bdd
                Client client = new Client();
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setEmail(email);
                client.setMdp(mdp);
                client.setAge(age);

                clientDao.inscrire(client);
                JOptionPane.showMessageDialog(null, "Inscription réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                VueClient clientPage = new VueClient(client);
                clientPage.setVisible(true);
            }
        });


        registerButtonPanel.add(btnRegister);
        rightPanel.add(registerButtonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}
