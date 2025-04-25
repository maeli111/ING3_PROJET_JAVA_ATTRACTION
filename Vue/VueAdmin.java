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

    public VueAdmin(Admin admin) {
        setTitle("Infos Administrateur");
        setSize(600, 500);
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

        JLabel modificationsLabel = new JLabel("Modifications:", SwingConstants.LEFT);
        modificationsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 10));

        JPanel managementPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        JButton[] btns = {attractionsButton, reductionsButton, dossiersClientsButton, attractionDuMoisButton};
        for (JButton btn : btns) {
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setForeground(Color.BLUE);
            btn.setFont(btn.getFont().deriveFont(Font.ITALIC));
            managementPanel.add(btn);
        }

        JPanel leftPanelWithLabel = new JPanel(new BorderLayout());
        leftPanelWithLabel.add(modificationsLabel, BorderLayout.NORTH);
        leftPanelWithLabel.add(managementPanel, BorderLayout.CENTER);

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
}
