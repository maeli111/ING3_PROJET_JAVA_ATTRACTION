package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class VueReservationInvite extends JFrame {
    private final Color ROSE_PRINCIPAL = new Color(255, 105, 180);
    private final Color ROSE_FONCE = new Color(255, 20, 147);
    private final Color ROSE_SURVOL = new Color(255, 182, 193);
    private final Color ROSE_FOND = new Color(255, 240, 245);

    public JTextField nbPersonneFieldNouveau;
    public JButton moinsBtnNouveau, plusBtnNouveau;
    public JLabel prixLabelNouveau;
    public JTextField nomField, prenomField, emailFieldNouveau;
    public JButton reserverButton;
    public JLabel titreResa = new JLabel();

    public JButton btnAccueil, btnInformations, btnCalendrier, btnCompte, loupeBtn;

    public VueReservationInvite() {
        setTitle("Réservation");
        setSize(1250, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color hoverColor = ROSE_SURVOL;
        Color defaultColor = UIManager.getColor("Button.background");

        JPanel Pbarre = new JPanel(new BorderLayout());
        Pbarre.setOpaque(false);

        JPanel Pnavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        Pnavigation.setOpaque(false);
        btnAccueil = createStyledButton("Accueil");
        btnInformations = createStyledButton("Informations");
        btnCalendrier = createStyledButton("Calendrier");
        applyHoverEffect(btnAccueil, hoverColor, defaultColor);
        applyHoverEffect(btnInformations, hoverColor, defaultColor);
        applyHoverEffect(btnCalendrier, hoverColor, defaultColor);

        Pnavigation.add(btnAccueil);
        Pnavigation.add(btnInformations);
        Pnavigation.add(btnCalendrier);

        JPanel Pdroite = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        Pdroite.setOpaque(false);
        btnCompte = createStyledButton("Compte");
        applyHoverEffect(btnCompte, hoverColor, defaultColor);

        try {
            BufferedImage loupeImage = ImageIO.read(new File("images/loupe.png"));
            Image scaledLoupe = loupeImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            loupeBtn = new JButton(new ImageIcon(scaledLoupe));
            loupeBtn.setBorder(BorderFactory.createEmptyBorder());
            loupeBtn.setContentAreaFilled(false);
            loupeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                private Color originalBg = loupeBtn.getBackground();
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    loupeBtn.setBackground(hoverColor);
                    loupeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    loupeBtn.setBackground(originalBg);
                    loupeBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });
            Pdroite.add(loupeBtn);
            Pdroite.add(Box.createRigidArea(new Dimension(5, 0)));
        } catch (IOException e) {
            loupeBtn = createStyledButton("🔍");
            applyHoverEffect(loupeBtn, hoverColor, defaultColor);
            Pdroite.add(loupeBtn);
            Pdroite.add(Box.createRigidArea(new Dimension(5, 0)));
        }

        Pdroite.add(btnCompte);
        Pbarre.add(Pnavigation, BorderLayout.WEST);
        Pbarre.add(Pdroite, BorderLayout.EAST);

        JTextField parc = new JTextField("Palasi Land");
        parc.setHorizontalAlignment(JTextField.CENTER);
        parc.setEditable(false);
        parc.setFont(new Font("Bodoni MT", Font.BOLD, 40));
        parc.setBorder(null);
        parc.setOpaque(false);

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.add(Pbarre, BorderLayout.NORTH);
        header.add(parc, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);



        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        formPanel.setBackground(Color.WHITE);
        formPanel.setOpaque(true);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setPreferredSize(new Dimension(600, 400));

        Font fieldFont = new Font("Arial", Font.PLAIN, 18);

        formPanel.add(createFormField("Nom :", fieldFont));
        nomField = new JTextField(20);
        styleTextField(nomField, fieldFont);
        formPanel.add(nomField);

        formPanel.add(createFormField("Prénom :", fieldFont));
        prenomField = new JTextField(20);
        styleTextField(prenomField, fieldFont);
        formPanel.add(prenomField);

        formPanel.add(createFormField("Email :", fieldFont));
        emailFieldNouveau = new JTextField(20);
        styleTextField(emailFieldNouveau, fieldFont);
        formPanel.add(emailFieldNouveau);

        formPanel.add(createFormField("Nombre de personnes :", fieldFont));
        JPanel panelNb = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelNb.setOpaque(false);
        moinsBtnNouveau = createStyledButton("-");
        moinsBtnNouveau.setFont(fieldFont);
        nbPersonneFieldNouveau = new JTextField("0", 5);
        styleTextField(nbPersonneFieldNouveau, fieldFont);
        nbPersonneFieldNouveau.setHorizontalAlignment(JTextField.CENTER);
        plusBtnNouveau = createStyledButton("+");
        plusBtnNouveau.setFont(fieldFont);
        panelNb.add(moinsBtnNouveau);
        panelNb.add(nbPersonneFieldNouveau);
        panelNb.add(plusBtnNouveau);
        formPanel.add(panelNb);

        prixLabelNouveau = new JLabel("Prix total: 0.00 €", SwingConstants.CENTER);
        prixLabelNouveau.setFont(new Font("Arial", Font.BOLD, 22));
        prixLabelNouveau.setForeground(ROSE_FONCE);
        prixLabelNouveau.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        JPanel prixPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prixPanel.setOpaque(false);
        prixPanel.add(prixLabelNouveau);
        formPanel.add(prixPanel);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(formPanel);

        add(centerPanel, BorderLayout.CENTER);

        reserverButton = new JButton("Réserver");
        reserverButton.setFont(new Font("Arial", Font.BOLD, 24));
        reserverButton.setBackground(ROSE_PRINCIPAL);
        reserverButton.setForeground(Color.WHITE);
        reserverButton.setFocusPainted(false);
        reserverButton.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        reserverButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(reserverButton, BorderLayout.SOUTH);
    }

    private void applyHoverEffect(JButton button, Color hoverColor, Color defaultColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(defaultColor);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setBackground(UIManager.getColor("Button.background"));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        return button;
    }

    private JLabel createFormField(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(ROSE_PRINCIPAL);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private void styleTextField(JTextField textField, Font font) {
        textField.setMaximumSize(new Dimension(300, 35));
        textField.setFont(font);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROSE_PRINCIPAL, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
    }

    public void addListeners(ActionListener listener) {
        btnAccueil.addActionListener(listener);
        btnInformations.addActionListener(listener);
        btnCalendrier.addActionListener(listener);
        btnCompte.addActionListener(listener);
        reserverButton.addActionListener(listener);
        moinsBtnNouveau.addActionListener(listener);
        plusBtnNouveau.addActionListener(listener);
    }
}
