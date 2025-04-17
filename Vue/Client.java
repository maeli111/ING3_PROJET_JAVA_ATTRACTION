package Vue;

import javax.swing.*;
import java.awt.*;

public class Client extends JFrame {
    public Client() {
        setTitle("Client");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Added border layout with gaps

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
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // Adjusted to 3 rows

        // Personal information panel
        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 5, 5)); // 5 rows, 2 columns
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informations personnelles"));
        infoPanel.add(new JLabel("Nom:"));
        infoPanel.add(new JTextField());
        infoPanel.add(new JLabel("Prénom:"));
        infoPanel.add(new JTextField());
        infoPanel.add(new JLabel("Age:"));
        infoPanel.add(new JTextField());
        infoPanel.add(new JLabel("E-mail:"));
        infoPanel.add(new JTextField());

        // Reservations label and panels
        JPanel reservationsPanel = new JPanel(new BorderLayout(10, 10)); // Border layout with gaps
        JLabel reservationsLabel = new JLabel("Mes réservations:");
        reservationsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Add some space below the label

        // Current reservations panel
        JPanel currentReservationsPanel = new JPanel();
        currentReservationsPanel.setBorder(BorderFactory.createTitledBorder("Réservations en cours"));

        // Reservation history panel
        JPanel reservationHistoryPanel = new JPanel();
        reservationHistoryPanel.setBorder(BorderFactory.createTitledBorder("Historique des réservations"));

        JPanel reservationsInfoPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // Grid layout with gaps
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Client client = new Client();
            client.setVisible(true);
        });
    }
}