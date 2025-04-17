package Vue;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    public Login() {
        setTitle("Login");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Use BorderLayout

        // Top panel for titles - now split into two panels
        JPanel topPanel = new JPanel(new GridLayout(1, 2)); // One row, two columns

        // Left title panel for "Connexion"
        JPanel leftTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        leftTitlePanel.add(new JLabel("Connexion", SwingConstants.CENTER));

        // Right title panel for "Inscription"
        JPanel rightTitlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        rightTitlePanel.add(new JLabel("Inscription", SwingConstants.CENTER));

        topPanel.add(leftTitlePanel);
        topPanel.add(rightTitlePanel);

        // Main content panel divided into two halves
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // One row, two columns

        // Left panel for login options
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

        // Right panel for registration information
        JPanel rightPanel = new JPanel(new BorderLayout()); // Changed to BorderLayout
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        formPanel.add(new JLabel("Nom :"));
        formPanel.add(new JTextField());

        formPanel.add(new JLabel("Prénom :"));
        formPanel.add(new JTextField());

        formPanel.add(new JLabel("Age :"));
        formPanel.add(new JTextField());

        formPanel.add(new JLabel("E-mail :"));
        formPanel.add(new JTextField());

        formPanel.add(new JLabel("Mot de passe :"));
        formPanel.add(new JPasswordField());

        formPanel.add(new JLabel("Vérifier mot de passe :"));
        formPanel.add(new JPasswordField());

        rightPanel.add(formPanel, BorderLayout.CENTER);

        // Panel for the register button
        JPanel registerButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnRegister = new JButton("S'inscrire");
        registerButtonPanel.add(btnRegister);
        rightPanel.add(registerButtonPanel, BorderLayout.SOUTH);

        // Add panels to the main frame
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