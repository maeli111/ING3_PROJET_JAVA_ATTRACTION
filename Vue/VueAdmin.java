package Vue;

import javax.swing.*;
import java.awt.*;
import Modele.Admin;

public class VueAdmin extends JFrame {
    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");
    private JButton btnDeconnexion = new JButton("Déconnexion");

    private JButton attractionsButton = new JButton("Attractions");
    private JButton reductionsButton = new JButton("Réductions");
    private JButton dossiersClientsButton = new JButton("Dossiers clients");
    private JButton attractionDuMoisButton = new JButton("Attraction du mois");

    // Nouveaux éléments pour Attraction du Mois
    private JPanel attractionMoisPanel = new JPanel();
    private JLabel attractionActuelleLabel = new JLabel("Attraction actuelle : ");
    private JComboBox<String> attractionComboBox = new JComboBox<>();
    private JButton validerAttractionButton = new JButton("Valider");

    public VueAdmin(Admin admin) {
        setTitle("Infos Administrateur");
        setSize(1250, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ----------- HEADER -----------
        JPanel header = new JPanel(new BorderLayout());
        JPanel Pnavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        Pnavigation.add(accueil);
        Pnavigation.add(informations);
        Pnavigation.add(calendrier);

        JPanel Pcompte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        Pcompte.add(compte);

        header.add(Pnavigation, BorderLayout.WEST);
        header.add(Pcompte, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // ----------- MAIN -----------
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

        JLabel modificationsLabel = new JLabel("Modifications:", SwingConstants.CENTER);
        modificationsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        modificationsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel managementPanel = new JPanel();
        managementPanel.setLayout(new BoxLayout(managementPanel, BoxLayout.Y_AXIS));
        managementPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centre le panneau

        JButton[] btns = {attractionsButton, reductionsButton, dossiersClientsButton, attractionDuMoisButton};
        for (JButton btn : btns) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT); // Centre chaque bouton
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setForeground(Color.BLUE);
            btn.setFont(btn.getFont().deriveFont(Font.ITALIC, 14f)); // Taille de texte un peu plus grande
            managementPanel.add(btn);
            managementPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Petit espace entre les boutons
        }

        // ----------- ATTRACTION DU MOIS PANEL -----------
        attractionMoisPanel.setLayout(new BoxLayout(attractionMoisPanel, BoxLayout.Y_AXIS));
        attractionMoisPanel.setBorder(BorderFactory.createTitledBorder("Définir l'attraction du mois"));
        attractionMoisPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centre aussi ce panneau
        attractionMoisPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        attractionActuelleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attractionMoisPanel.add(attractionActuelleLabel);
        attractionMoisPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        attractionComboBox.setMaximumSize(new Dimension(200, 25)); // Taille fixée pour ne pas être trop large
        attractionComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        attractionMoisPanel.add(attractionComboBox);
        attractionMoisPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        validerAttractionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        attractionMoisPanel.add(validerAttractionButton);
        attractionMoisPanel.setVisible(false); // Masqué par défaut

        // Panel qui contient les boutons + le panneau AttractionMois
        JPanel leftPanelWithLabel = new JPanel();
        leftPanelWithLabel.setLayout(new BoxLayout(leftPanelWithLabel, BoxLayout.Y_AXIS));
        leftPanelWithLabel.add(modificationsLabel);
        leftPanelWithLabel.add(managementPanel);
        leftPanelWithLabel.add(Box.createRigidArea(new Dimension(0, 20))); // espace
        leftPanelWithLabel.add(attractionMoisPanel); // <= ajouté ici

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanelWithLabel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // ----------- FOOTER -----------
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnDeconnexion);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // ===== GETTERS =====
    public JButton getAccueilButton() { return accueil; }
    public JButton getInformationsButton() { return informations; }
    public JButton getCalendrierButton() { return calendrier; }
    public JButton getCompteButton() { return compte; }
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
