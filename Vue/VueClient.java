package Vue;

import javax.swing.*;
import java.awt.*;
import Modele.Client;

public class VueClient extends JFrame {
    private Client client;
    private JButton btnAccueil, btnInfo, btnCalendrier, btnDeconnexion;

    public VueClient(Client client) {
        this.client = client;
        setTitle("Client");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top button panel
        JPanel topPanel = createTopPanel();
        // Bottom button panel (Deconnexion)
        JPanel bottomPanel = createBottomPanel();

        // Main content panel
        JPanel mainPanel = createMainPanel();

        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Buttons
        btnAccueil = new JButton("Accueil");
        btnInfo = new JButton("Informations");
        btnCalendrier = new JButton("Calendrier");

        leftPanel.add(btnAccueil);
        leftPanel.add(btnInfo);
        leftPanel.add(btnCalendrier);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(new JButton("Compte"));

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnDeconnexion = new JButton("Déconnexion");
        bottomPanel.add(btnDeconnexion);
        return bottomPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // Informations personnelles panel
        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informations personnelles"));

        infoPanel.add(new JLabel("Nom:"));
        JTextField nomField = new JTextField(client.getNom());
        nomField.setEditable(false);
        infoPanel.add(nomField);

        infoPanel.add(new JLabel("Prénom:"));
        JTextField prenomField = new JTextField(client.getPrenom());
        prenomField.setEditable(false);
        infoPanel.add(prenomField);

        infoPanel.add(new JLabel("Age:"));
        JTextField ageField = new JTextField(String.valueOf(client.getage()));
        ageField.setEditable(false);
        infoPanel.add(ageField);

        infoPanel.add(new JLabel("E-mail:"));
        JTextField emailField = new JTextField(client.getEmail());
        emailField.setEditable(false);
        infoPanel.add(emailField);

        // Reservations panel
        JPanel reservationsPanel = new JPanel(new BorderLayout(10, 10));
        JLabel reservationsLabel = new JLabel("Mes réservations:");
        reservationsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel currentReservationsPanel = new JPanel();
        currentReservationsPanel.setBorder(BorderFactory.createTitledBorder("Réservations en cours"));

        JPanel reservationHistoryPanel = new JPanel();
        reservationHistoryPanel.setBorder(BorderFactory.createTitledBorder("Historique des réservations"));

        JPanel reservationsInfoPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        reservationsInfoPanel.add(currentReservationsPanel);
        reservationsInfoPanel.add(reservationHistoryPanel);

        reservationsPanel.add(reservationsLabel, BorderLayout.NORTH);
        reservationsPanel.add(reservationsInfoPanel, BorderLayout.CENTER);

        mainPanel.add(infoPanel);
        mainPanel.add(reservationsPanel);

        return mainPanel;
    }

    // Getters for the buttons to be used in the controller
    public JButton getBtnAccueil() {
        return btnAccueil;
    }

    public JButton getBtnInfo() {
        return btnInfo;
    }

    public JButton getBtnCalendrier() {
        return btnCalendrier;
    }

    public JButton getBtnDeconnexion() {
        return btnDeconnexion;
    }
}
