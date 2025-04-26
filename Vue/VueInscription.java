package Vue;

import javax.swing.*;
import java.awt.*;

public class VueInscription extends JFrame {
    public JTextField txtNom, txtPrenom, txtAge, txtEmail;
    public JPasswordField txtMdp, txtMdpVerification;
    public JButton btnRegister, btnCompte;

    public VueInscription() {
        setTitle("Inscription");
        setSize(1250, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ----------- HEADER -----------

        JPanel header = new JPanel(new BorderLayout());

        JPanel Pnavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel Pcompte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnCompte = new JButton("Compte");
        Pcompte.add(btnCompte);

        header.add(Pnavigation, BorderLayout.WEST);
        header.add(Pcompte, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ----------- FORMULAIRE -----------

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

        // ----------- BOUTON INSCRIPTION -----------

        btnRegister = new JButton("S'inscrire");

        JPanel registerButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerButtonPanel.add(btnRegister);
        add(registerButtonPanel, BorderLayout.SOUTH);
    }
}
