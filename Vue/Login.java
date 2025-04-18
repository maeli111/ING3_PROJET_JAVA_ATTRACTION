package Vue;

import javax.swing.*;
import java.awt.*;
import DAO.*;
import Modele.Client;
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

        JPanel topPanel = new JPanel(new GridLayout(1, 2)); // One row, two columns

        JPanel leftTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        leftTitlePanel.add(new JLabel("Connexion", SwingConstants.CENTER));

        JPanel rightTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        rightTitlePanel.add(new JLabel("Inscription", SwingConstants.CENTER));

        topPanel.add(leftTitlePanel);
        topPanel.add(rightTitlePanel);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // One row, two columns

        JPanel leftPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // Four sections vertically
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add some padding

        JButton btnAdmin = new JButton("Administrateur");
        leftPanel.add(btnAdmin);

        JButton btnClient = new JButton("Client");
        leftPanel.add(btnClient);

        JButton btnGuest = new JButton("Continuer en tant qu'invité");
        btnGuest.setFocusPainted(false);
        btnGuest.setBorderPainted(false);
        btnGuest.setContentAreaFilled(false);
        btnGuest.setForeground(Color.BLUE);
        btnGuest.setFont(btnGuest.getFont().deriveFont(Font.ITALIC));
        leftPanel.add(btnGuest);

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

        // ActionListener pour l'inscription
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = txtNom.getText();
                String prenom = txtPrenom.getText();
                String email = txtEmail.getText();
                String mdp = new String(txtMdp.getPassword());
                String mdpVerification = new String(txtMdpVerification.getPassword());

                // Vérifier que les mots de passe correspondent
                if (!mdp.equals(mdpVerification)) {
                    JOptionPane.showMessageDialog(null, "Les mots de passe ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Récupérer l'âge et vérifier s'il est valide
                int age = 0;
                try {
                    age = Integer.parseInt(txtAge.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "L'âge doit être un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Créer un nouvel objet Client avec les données
                Client client = new Client(0, 0, age, "nouveau", "adulte"); // L'ID et l'ID utilisateur seront générés par la base
                client.setNom(nom);
                client.setPrenom(prenom);
                client.setEmail(email);
                client.setMdp(mdp);

                // Appel de la méthode inscrire de ClientDao
                ClientDao clientDao = new ClientDao(daoFactory);  // Vous devez initialiser daoFactory
                clientDao.inscrire(client);

                // Inscription réussie, rediriger vers la page Client
                JOptionPane.showMessageDialog(null, "Inscription réussie !", "Succès", JOptionPane.INFORMATION_MESSAGE);

                // Fermer la fenêtre de connexion
                dispose();

                Vue.Client clientPage = new Vue.Client(); // ta classe `Client` actuelle
                clientPage.setVisible(true); // Ouvre la fenêtre
                dispose(); // Ferme la fenêtre actuelle (Login)

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
