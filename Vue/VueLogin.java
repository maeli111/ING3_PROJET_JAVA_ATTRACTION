package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        // Ajout du titre "Welcome to Palasi Land"
        JLabel lblTitle = new JLabel("Welcome to Palasi Land", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Andalus", Font.BOLD, 34));
        lblTitle.setForeground(new Color(0x2D7C0D));
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
        titlePanel.add(lblTitle, BorderLayout.CENTER);
        mainPanel.add(titlePanel, BorderLayout.PAGE_START);

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Ajoute un peu d'espace (20px)

        JLabel imageLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon("images/home.jpg");

        imageLabel.setIcon(originalIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        imagePanel.add(imageLabel, BorderLayout.CENTER);
        mainPanel.add(imagePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createVerticalStrut(20));

        btnConnect = createUniformButton("Se connecter");
        buttonPanel.add(btnConnect);
        buttonPanel.add(Box.createVerticalStrut(10));

        btnRegister = createUniformButton("S'inscrire");
        buttonPanel.add(btnRegister);
        buttonPanel.add(Box.createVerticalStrut(10));

        btnGuest = createUniformButton("Continuer en tant qu'invité");
        buttonPanel.add(btnGuest);
        buttonPanel.add(Box.createVerticalStrut(10));

        btnAdmin = createUniformButton("Administrateur");
        buttonPanel.add(btnAdmin);

        buttonPanel.add(Box.createVerticalStrut(20));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(buttonPanel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    // Méthode pour créer des boutons uniformes
    private JButton createUniformButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 40));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.PINK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(UIManager.getColor("Button.background"));
            }
        });
        return button;
    }

    // Getters pour les boutons
    public JButton getBtnConnect() { return btnConnect; }
    public JButton getBtnRegister() { return btnRegister; }
    public JButton getBtnGuest() { return btnGuest; }
    public JButton getBtnAdmin() { return btnAdmin; }
}
