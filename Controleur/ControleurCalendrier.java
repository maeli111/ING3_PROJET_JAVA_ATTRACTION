package Controleur;

import Vue.*;
import Modele.*;
import DAO.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.util.Locale;
import java.sql.*;

public class ControleurCalendrier {

    private final VueCalendrier vue;
    private final Client client;
    private final Admin admin;
    private final AttractionDaoInt attractionDAO;

    public ControleurCalendrier(VueCalendrier vue, Client client, Admin admin) {
        this.vue = vue;
        this.client = client;
        this.admin = admin;

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        this.attractionDAO = new AttractionDao(daoFactory);

        setupListeners();
        updateCalendar(); // initialisation
    }

    private void setupListeners() {
        vue.getBtnAccueil().addActionListener(e -> {
            VueAccueil vueAccueil = new VueAccueil(client,admin);
            new ControleurAccueil(vueAccueil,client, admin);
            vueAccueil.setVisible(true);
            vue.dispose();
        });

        vue.getBtnInfos().addActionListener(e -> {
            VuePlusInfos v = new VuePlusInfos(client, admin);
            new ControleurPlusInfos(v, client, admin);
            v.setVisible(true);

            vue.dispose();
        });

        vue.getBtnCalendrier().addActionListener(e -> {
            VueCalendrier v = new VueCalendrier(client, admin);
            new ControleurCalendrier(v, client, admin);
            vue.dispose();
        });

        vue.getBtnCompte().addActionListener(e -> {
            if (client == null && admin == null) {
                VueLogin vueLogin = new VueLogin();
                new ControleurLogin(vueLogin);
                vueLogin.setVisible(true);
            } else if (client != null) {
                VueClient v = new VueClient(client);
                new ControleurClient(v, client);
                v.setVisible(true);
            } else if (admin != null) {
                VueAdmin v = new VueAdmin(admin);
                new ControleurAdmin(v, admin);
                v.setVisible(true);
            }
            vue.dispose();
        });

        vue.getBtnPrev().addActionListener(e -> {
            YearMonth curr = vue.getCurrentMonth();
            if (curr.isAfter(vue.getMinMonth())) {
                vue.setCurrentMonth(curr.minusMonths(1));
                updateCalendar();
            }
        });

        vue.getBtnNext().addActionListener(e -> {
            YearMonth curr = vue.getCurrentMonth();
            if (curr.isBefore(vue.getMaxMonth())) {
                vue.setCurrentMonth(curr.plusMonths(1));
                updateCalendar();
            }
        });
    }

    private void updateCalendar() {
        JPanel calendar = vue.getCalendarPanel();
        calendar.removeAll();

        for (DayOfWeek day : DayOfWeek.values()) {
            JLabel label = new JLabel(day.getDisplayName(java.time.format.TextStyle.SHORT, Locale.FRENCH), SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            calendar.add(label);
        }

        YearMonth month = vue.getCurrentMonth();
        vue.getMoisLabel().setText(month.getMonth().getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH).toUpperCase() + " " + month.getYear());

        LocalDate firstDay = month.atDay(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue();

        for (int i = 1; i < dayOfWeek; i++) {
            calendar.add(new JLabel(""));
        }

        for (int day = 1; day <= month.lengthOfMonth(); day++) {
            LocalDate date = month.atDay(day);
            JButton btn = new JButton(String.valueOf(day));
            btn.addActionListener(e -> afficherAttractions(date));
            calendar.add(btn);
        }

        calendar.revalidate();
        calendar.repaint();
    }

    private void afficherAttractions(LocalDate date) {
        JPanel details = vue.getDetailsPanel();
        details.removeAll();

        JLabel titre = new JLabel("Attractions disponibles le " + date);
        titre.setFont(new Font("SansSerif", Font.BOLD, 16));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        details.add(titre);
        details.add(Box.createRigidArea(new Dimension(0, 10)));

        boolean found = false;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_attraction", "root", "");
             PreparedStatement ps = conn.prepareStatement("SELECT id_attraction, nom FROM attraction");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_attraction");
                String nom = rs.getString("nom");

                if (attractionDAO.estDisponible(date, id)) {
                    found = true;
                    JButton btn = new JButton(nom);
                    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                    btn.addActionListener(ev -> {
                        Attraction attraction = attractionDAO.chercher(id);
                        if (attraction != null) {
                            new VueInfoAttraction(attraction, date, client, admin).setVisible(true);
                            vue.dispose();
                        }
                    });
                    details.add(btn);
                    details.add(Box.createRigidArea(new Dimension(0, 5)));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            details.add(new JLabel("(Erreur lors du chargement)"));
        }

        if (!found) {
            JLabel lbl = new JLabel("Aucune attraction disponible à cette date.");
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            details.add(lbl);
        }

        details.revalidate();
        details.repaint();
    }
}
