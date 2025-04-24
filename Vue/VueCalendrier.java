package Vue;

import DAO.AttractionDao;
import DAO.AttractionDaoInt;
import DAO.DaoFactory;
import Modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.Locale;
import java.sql.*;

public class VueCalendrier extends JFrame {
    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");
    private JTextField parc = new JTextField("Palasi Land");

    private JPanel calendarPanel;
    private JPanel detailsPanel;

    private JLabel moisLabel;
    private YearMonth currentMonth;

    private final YearMonth minMonth = YearMonth.now();
    private final YearMonth maxMonth = YearMonth.of(2026, 4); // Avril 2026

    // ➕ Ajout : Attributs pour client et admin
    private Client client;
    private Admin admin;

    public VueCalendrier(Client client, Admin admin) {
        // ➕ Initialisation des attributs
        this.client = client;
        this.admin = admin;

        setTitle("Calendrier des Attractions");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // HEADER
        parc.setHorizontalAlignment(JTextField.CENTER);
        parc.setEditable(false);
        parc.setFont(new Font("Bodoni MT", Font.BOLD, 32));
        parc.setBorder(null);
        parc.setOpaque(false);
        calendrier.setBackground(new Color(255, 182, 193));

        JPanel Pbarre = new JPanel(new BorderLayout());
        JPanel Pnavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        Pnavigation.add(accueil);
        Pnavigation.add(informations);
        Pnavigation.add(calendrier);

        accueil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VueAccueil vueAccueil = new VueAccueil(client, admin);
                vueAccueil.setVisible(true);
                dispose();
            }
        });

        informations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VuePlusInfos vuePlusInfos = new VuePlusInfos(client, admin);
                vuePlusInfos.setVisible(true);
                dispose(); // pour fermer la fenêtre actuelle si tu veux
            }
        });

        calendrier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VueCalendrier vueCalendrier = new VueCalendrier(client, admin);
                vueCalendrier.setVisible(true);
                dispose();
            }
        });

        JPanel Pcompte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        Pcompte.add(compte);

        compte.addActionListener(e -> {
            if (client == null && admin == null) {
                new VueLogin().setVisible(true);
                dispose();
            } else if (client != null && admin == null) {
                new VueClient(client).setVisible(true);
                dispose();
            } else if (client == null && admin != null) {
                new VueAdmin(admin).setVisible(true);
                dispose();
            }
        });

        Pbarre.add(Pnavigation, BorderLayout.WEST);
        Pbarre.add(Pcompte, BorderLayout.EAST);

        JPanel header = new JPanel(new BorderLayout());
        header.add(Pbarre, BorderLayout.NORTH);
        header.add(parc, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // CONTENU
        currentMonth = YearMonth.now();
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Calendrier
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        JButton prevButton = new JButton("←");
        JButton nextButton = new JButton("→");

        moisLabel = new JLabel("", SwingConstants.CENTER);
        moisLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        prevButton.addActionListener(e -> {
            if (currentMonth.isAfter(minMonth)) {
                currentMonth = currentMonth.minusMonths(1);
                updateCalendar();
            }
        });

        nextButton.addActionListener(e -> {
            if (currentMonth.isBefore(maxMonth)) {
                currentMonth = currentMonth.plusMonths(1);
                updateCalendar();
            }
        });

        topPanel.add(prevButton, BorderLayout.WEST);
        topPanel.add(moisLabel, BorderLayout.CENTER);
        topPanel.add(nextButton, BorderLayout.EAST);

        wrapper.add(topPanel, BorderLayout.NORTH);

        calendarPanel = new JPanel(new GridLayout(0, 7, 5, 5));
        wrapper.add(calendarPanel, BorderLayout.CENTER);
        mainPanel.add(wrapper, BorderLayout.CENTER);

        // ➕ Panel de droite pour les détails
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Attractions disponibles"));
        detailsPanel.setPreferredSize(new Dimension(300, 0));

        mainPanel.add(detailsPanel, BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);

        updateCalendar();
        setVisible(true);
    }

    private void updateCalendar() {
        calendarPanel.removeAll();

        for (DayOfWeek day : DayOfWeek.values()) {
            JLabel label = new JLabel(day.getDisplayName(java.time.format.TextStyle.SHORT, Locale.FRENCH), SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            calendarPanel.add(label);
        }

        moisLabel.setText(currentMonth.getMonth().getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH).toUpperCase() + " " + currentMonth.getYear());

        LocalDate firstDay = currentMonth.atDay(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue();

        for (int i = 1; i < dayOfWeek; i++) {
            calendarPanel.add(new JLabel(""));
        }

        int daysInMonth = currentMonth.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            LocalDate dateClicked = currentMonth.atDay(day);
            dayButton.addActionListener(new DayClickListener(dateClicked));
            calendarPanel.add(dayButton);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private class DayClickListener implements ActionListener {
        private final LocalDate date;

        public DayClickListener(LocalDate date) {
            this.date = date;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            detailsPanel.removeAll();

            JLabel titre = new JLabel("Attractions disponibles le " + date);
            titre.setAlignmentX(Component.CENTER_ALIGNMENT);
            titre.setFont(new Font("SansSerif", Font.BOLD, 16));
            detailsPanel.add(titre);
            detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

            DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
            AttractionDaoInt attractionDAO = new AttractionDao(daoFactory);
            boolean found = false;

            try {
                Connection connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_attraction", "root", "");
                PreparedStatement ps = connexion.prepareStatement("SELECT id_attraction, nom FROM attraction");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int idAttraction = rs.getInt("id_attraction");
                    String nomAttraction = rs.getString("nom");

                    if (attractionDAO.estDisponible(date, idAttraction)) {
                        found = true;

                        JButton btn = new JButton(nomAttraction);
                        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

                        btn.addActionListener(ev -> {
                            Attraction attraction = attractionDAO.chercher(idAttraction);
                            if (attraction != null) {
                                dispose();
                                new VueInfoAttraction(attraction, date, client, admin).setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Aucune attraction trouvée avec l'ID : " + idAttraction);
                            }
                        });

                        detailsPanel.add(btn);
                        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                    }
                }

                rs.close();
                ps.close();
                connexion.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                detailsPanel.add(new JLabel("(Erreur lors du chargement)"));
            }

            if (!found) {
                JLabel noAttractions = new JLabel("Aucune attraction disponible à cette date.");
                noAttractions.setAlignmentX(Component.CENTER_ALIGNMENT);
                detailsPanel.add(noAttractions);
            }

            detailsPanel.revalidate();
            detailsPanel.repaint();
        }
    }
}