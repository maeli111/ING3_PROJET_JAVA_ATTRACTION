package Vue;

import javax.swing.*;
import java.awt.*;
import Modele.*;
import DAO.*;
import Modele.Reservation;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class VueClient extends JFrame {
    private Client client;
    private JButton btnAccueil, btnInfo, btnCalendrier, btnDeconnexion, btnLoupe, btnCompte; // Ajout de btnCompte

    public VueClient(Client client) {
        ReservationDao reservationDao = new ReservationDao(new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", ""));
        reservationDao.archiverReservationsPassées();
        this.client = client;
        setTitle("Client");
        setSize(1250, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel PanelHaut = createTopPanel();
        JPanel PanelBas = createBottomPanel();
        JPanel mainPanel = createMainPanel();

        add(PanelHaut, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(PanelBas, BorderLayout.SOUTH);

        // Ajout des effets de survol
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

        JButton[] boutonsRose = {btnAccueil, btnInfo, btnCalendrier, btnLoupe};
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

        // Le bouton Compte reste rose en permanence
        btnCompte.setBackground(Color.PINK);
        btnCompte.setOpaque(true);
        btnCompte.setBorderPainted(false);
    }

    private JPanel createTopPanel() {
        JPanel PanelHaut = new JPanel(new BorderLayout());
        JPanel PanelGauche = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // boutons
        btnAccueil = new JButton("Accueil");
        btnInfo = new JButton("Informations");
        btnCalendrier = new JButton("Calendrier");

        PanelGauche.add(btnAccueil);
        PanelGauche.add(btnInfo);
        PanelGauche.add(btnCalendrier);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLoupe = new JButton("🔍");
        btnCompte = new JButton("Compte");
        rightPanel.add(btnLoupe);
        rightPanel.add(btnCompte);

        PanelHaut.add(PanelGauche, BorderLayout.WEST);
        PanelHaut.add(rightPanel, BorderLayout.EAST);

        return PanelHaut;
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

        JPanel reservationsPanel = new JPanel(new BorderLayout(10, 10));
        JLabel reservationsLabel = new JLabel("Mes réservations:");
        reservationsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel currentReservationsPanel = new JPanel();
        currentReservationsPanel.setBorder(BorderFactory.createTitledBorder("Réservations en cours"));

        ClientDao clientDao = new ClientDao(new DaoFactory("jdbc:mysql://localhost:3306/java_attraction", "root", ""));
        ArrayList<Reservation> reservationsEnCours = clientDao.getReservationsEnCours(client);

        currentReservationsPanel.setLayout(new BoxLayout(currentReservationsPanel, BoxLayout.Y_AXIS));

        // On affiche chaque réservation en cours
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

        JPanel reservationHistoryPanel = new JPanel();
        reservationHistoryPanel.setBorder(BorderFactory.createTitledBorder("Historique des réservations"));

        ArrayList<Reservation> reservationsArchivees = clientDao.getReservationsArchivees(client);
        reservationHistoryPanel.setLayout(new BoxLayout(reservationHistoryPanel, BoxLayout.Y_AXIS));

        // On affiche chaque réservation archivée (historique)
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

    public JButton getBtnLoupe() {
        return btnLoupe;
    }

    public JButton getBtnCompte() {
        return btnCompte;
    }
}