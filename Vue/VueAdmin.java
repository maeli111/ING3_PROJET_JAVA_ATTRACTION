package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Modele.Admin;

public class VueAdmin extends JFrame {
    private JButton compte = new JButton("Compte");
    private JButton btnDeconnexion = new JButton("Déconnexion");

    private JButton attractionsButton = new JButton("Attractions");
    private JButton reductionsButton = new JButton("Réductions");
    private JButton dossiersClientsButton = new JButton("Dossiers clients");
    private JButton attractionDuMoisButton = new JButton("Attraction du mois");

    private JPanel attractionMoisPanel = new JPanel();
    private JLabel attractionActuelleLabel = new JLabel("Attraction actuelle : ");
    private JComboBox<String> attractionComboBox = new JComboBox<>();
    private JButton validerAttractionButton = new JButton("Valider");

    public VueAdmin(Admin admin) {
        setTitle("Infos Administrateur");
        setSize(1250, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel(new BorderLayout());
        JPanel Pnavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel Pcompte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        Pcompte.add(compte);
        header.add(Pnavigation, BorderLayout.WEST);
        header.add(Pcompte, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informations personnelles"));
        infoPanel.add(new JLabel("Nom:"));
        JTextField nomField = new JTextField(admin.getNom());
        nomField.setEditable(false);
        infoPanel.add(nomField);

        infoPanel.add(new JLabel("Prénom:"));
        JTextField prenomField = new JTextField(admin.getPrenom());
        prenomField.setEditable(false);
        infoPanel.add(prenomField);

        infoPanel.add(new JLabel("E-mail:"));
        JTextField emailField = new JTextField(admin.getEmail());
        emailField.setEditable(false);
        infoPanel.add(emailField);

        // Modification : Sous-titre aligné à gauche avec une marge plus importante
        JLabel modificationsLabel = new JLabel("Modifications:");
        modificationsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Augmentation de la marge gauche à 30

        JPanel gestionPanel = new JPanel();
        gestionPanel.setLayout(new GridBagLayout());
        gestionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JButton[] btns = {attractionsButton, reductionsButton, dossiersClientsButton, attractionDuMoisButton};
        for (JButton btn : btns) {
            btn.setPreferredSize(new Dimension(160, 30));
            btn.setMaximumSize(new Dimension(160, 30));
            gestionPanel.add(btn, gbc);
        }

        // Effets de survol
        btnDeconnexion.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnDeconnexion.setBackground(Color.RED);
                btnDeconnexion.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                btnDeconnexion.setBackground(UIManager.getColor("Button.background"));
                btnDeconnexion.setForeground(UIManager.getColor("Button.foreground"));
            }
        });

        JButton[] boutonsRose = {compte, attractionsButton, reductionsButton,
                dossiersClientsButton, attractionDuMoisButton};
        for (JButton btn : boutonsRose) {
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(Color.PINK);
                }
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(UIManager.getColor("Button.background"));
                }
            });
        }

        attractionMoisPanel.setLayout(new BoxLayout(attractionMoisPanel, BoxLayout.Y_AXIS));
        attractionMoisPanel.setBorder(BorderFactory.createTitledBorder("Définir l'attraction du mois"));
        attractionMoisPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        attractionActuelleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attractionMoisPanel.add(attractionActuelleLabel);
        attractionMoisPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        attractionComboBox.setMaximumSize(new Dimension(200, 25));
        attractionComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        attractionMoisPanel.add(attractionComboBox);
        attractionMoisPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        validerAttractionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        attractionMoisPanel.add(validerAttractionButton);
        attractionMoisPanel.setVisible(false);

        JPanel PanelAvecLabel = new JPanel();
        PanelAvecLabel.setLayout(new BoxLayout(PanelAvecLabel, BoxLayout.Y_AXIS));
        PanelAvecLabel.add(modificationsLabel);
        PanelAvecLabel.add(gestionPanel);
        PanelAvecLabel.add(Box.createRigidArea(new Dimension(0, 20)));
        PanelAvecLabel.add(attractionMoisPanel);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(PanelAvecLabel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        JPanel basPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        basPanel.add(btnDeconnexion);
        add(basPanel, BorderLayout.SOUTH);
    }

    public JButton getDeconnexionButton() { return btnDeconnexion; }
    public JButton getAttractionsButton() { return attractionsButton; }
    public JButton getReductionsButton() { return reductionsButton; }
    public JButton getDossiersClientsButton() { return dossiersClientsButton; }
    public JButton getAttractionDuMoisButton() { return attractionDuMoisButton; }
    public JPanel getAttractionMoisPanel() { return attractionMoisPanel; }
    public JLabel getAttractionActuelleLabel() { return attractionActuelleLabel; }
    public JComboBox<String> getAttractionComboBox() { return attractionComboBox; }
    public JButton getValiderAttractionButton() { return validerAttractionButton; }
}