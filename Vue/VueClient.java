package Vue;

import javax.swing.*;
import java.awt.*;
import Modele.Client;

public class VueClient extends JFrame {
    public VueClient(Client client) {
        setTitle("Client");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top button panel
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(new JButton("Accueil"));
        leftPanel.add(new JButton("Informations"));
        leftPanel.add(new JButton("Calendrier"));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(new JButton("Compte"));

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // Main content panel
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // Personal information panel
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

        // Reservations label and panels
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

        // Add panels to main panel
        mainPanel.add(infoPanel);
        mainPanel.add(reservationsPanel);

        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
}