package Vue;

import javax.swing.*;
import java.awt.*;

public class VueLogin extends JFrame {
    private JButton btnConnect;
    private JButton btnRegister;
    private JButton btnGuest;
    private JButton btnAdmin;

    public VueLogin() {
        setTitle("Login");
        setSize(1250, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createVerticalStrut(20));

        btnConnect = new JButton("Se connecter");
        buttonPanel.add(btnConnect);
        buttonPanel.add(Box.createVerticalStrut(10));

        btnRegister = new JButton("S'inscrire");
        buttonPanel.add(btnRegister);
        buttonPanel.add(Box.createVerticalStrut(10));

        btnGuest = new JButton("Continuer en tant qu'invité");
        buttonPanel.add(btnGuest);
        buttonPanel.add(Box.createVerticalStrut(10));

        btnAdmin = new JButton("Administrateur");
        buttonPanel.add(btnAdmin);

        buttonPanel.add(Box.createVerticalStrut(20));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(buttonPanel);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    // Getters pour le contrôleur
    public JButton getBtnConnect() {
        return btnConnect;
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public JButton getBtnGuest() {
        return btnGuest;
    }

    public JButton getBtnAdmin() {
        return btnAdmin;
    }
}
