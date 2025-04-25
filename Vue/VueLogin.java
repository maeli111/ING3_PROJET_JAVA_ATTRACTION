package Vue;

import javax.swing.*;
import java.awt.*;
import DAO.*;

public class VueLogin extends JFrame {

    private DaoFactory daoFactory;

    public VueLogin() {
        daoFactory = new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", "");

        setTitle("Login");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));


        // ----------- PANNEAU CENTRAL -----------

        JPanel mainPanel = new JPanel(new BorderLayout()); // On garde BorderLayout pour avoir la partie supérieure fixe
        add(mainPanel, BorderLayout.CENTER);

        // ----------- BOUTONS VERTICAUX CENTRÉS EN BAS -----------

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer les boutons dans le panneau

        // On ajuste l'espacement entre les boutons pour qu'ils soient bien espacés
        buttonPanel.add(Box.createVerticalStrut(20)); // Espacement avant le premier bouton

        JButton btnConnect = new JButton("Se connecter");
        btnConnect.addActionListener(e -> {
            dispose();
            VueConnexionClient vueConnexionClient = new VueConnexionClient();
            vueConnexionClient.setVisible(true);
        });
        buttonPanel.add(btnConnect);
        buttonPanel.add(Box.createVerticalStrut(10)); // Espacement entre les boutons

        JButton btnRegister = new JButton("S'inscrire");
        btnRegister.addActionListener(e -> {
            dispose();
            VueInscription vueInscription = new VueInscription();
            vueInscription.setVisible(true);
        });
        buttonPanel.add(btnRegister);
        buttonPanel.add(Box.createVerticalStrut(10)); // Espacement entre les boutons

        JButton btnGuest = new JButton("Continuer en tant qu'invité");
        btnGuest.addActionListener(e -> {
            dispose();
            VueAccueil accueilGuest = new VueAccueil(null,null);
            accueilGuest.setVisible(true);
        });
        buttonPanel.add(btnGuest);
        buttonPanel.add(Box.createVerticalStrut(10)); // Espacement entre les boutons

        JButton btnAdmin = new JButton("Administrateur");
        btnAdmin.addActionListener(e -> {
            dispose();
            VueConnexionAdmin vueConnexionAdmin = new VueConnexionAdmin();
            vueConnexionAdmin.setVisible(true);
        });
        buttonPanel.add(btnAdmin);

        buttonPanel.add(Box.createVerticalStrut(20)); // Espacement après le dernier bouton

        // ----------- AJOUTER LES BOUTONS AU BAS CENTRÉ -----------

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  // Pour centrer les boutons
        bottomPanel.add(buttonPanel);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);  // Ajouter les boutons au bas
    }
}
