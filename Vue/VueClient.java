package Vue;

import javax.swing.*;
import java.awt.*;
import Modele.*;
import DAO.*;
import Modele.Reservation;

import java.util.ArrayList;

public class VueClient extends JFrame {
    private Client client;
    private JButton btnAccueil, btnInfo, btnCalendrier, btnDeconnexion;

    public VueClient(Client client) {
        ReservationDao reservationDao = new ReservationDao(new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", ""));
        reservationDao.archiverReservationsPassées();  // Appel de la méthode sur l'instance
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

        // Obtenir les réservations en cours via ClientDao
        ClientDao clientDao = new ClientDao(new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", ""));
        ArrayList<Reservation> reservationsEnCours = clientDao.getReservationsEnCours(client);

        currentReservationsPanel.setLayout(new BoxLayout(currentReservationsPanel, BoxLayout.Y_AXIS));

        // Afficher chaque réservation en cours
        for (Reservation reservation : reservationsEnCours) {
            JPanel reservationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            int id_attraction = reservation.getId_attraction();
            ReservationDao reservationDao = new ReservationDao(new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", ""));
            String nomAttraction = reservationDao.getNomAttraction(id_attraction);
            reservationPanel.add(new JLabel("Attraction: " + nomAttraction));
            reservationPanel.add(new JLabel("Date: " + reservation.getDate_reservation().toString()));
            reservationPanel.add(new JLabel("Prix total: " + reservation.getPrix_total()));
            currentReservationsPanel.add(reservationPanel);
        }

        // Historique des réservations panel
        JPanel reservationHistoryPanel = new JPanel();
        reservationHistoryPanel.setBorder(BorderFactory.createTitledBorder("Historique des réservations"));

        // Récupérer les réservations archivées
        ArrayList<Reservation> reservationsArchivees = clientDao.getReservationsArchivees(client);
        reservationHistoryPanel.setLayout(new BoxLayout(reservationHistoryPanel, BoxLayout.Y_AXIS));

        // Afficher chaque réservation archivée
        for (Reservation reservation : reservationsArchivees) {
            JPanel reservationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            int id_attraction = reservation.getId_attraction();
            ReservationDao reservationDao = new ReservationDao(new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", ""));
            String nomAttraction = reservationDao.getNomAttraction(id_attraction);
            reservationPanel.add(new JLabel("Attraction: " + nomAttraction));
            reservationPanel.add(new JLabel("Date: " + reservation.getDate_reservation().toString()));
            reservationPanel.add(new JLabel("Prix total: " + reservation.getPrix_total()));
            reservationHistoryPanel.add(reservationPanel);
        }

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
