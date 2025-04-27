package Controleur;

import Vue.*;
import Modele.*;
import DAO.*;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.util.Locale;
import java.sql.*;

public class ControleurCalendrier {
    private final VueCalendrier vue;
    private final Client client;
    private final Admin admin;
    private final AttractionDaoInt attractionDAO;

    // constructeur
    public ControleurCalendrier(VueCalendrier vue, Client client, Admin admin) {
        this.vue = vue;
        this.client = client;
        this.admin = admin;

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        this.attractionDAO = new AttractionDao(daoFactory);

        setupListeners();
        updateCalendar();
    }

    // configuration des listeners
    private void setupListeners() {
        vue.getBtnAccueil().addActionListener(e -> {
            VueAccueil vueAccueil = new VueAccueil(client, admin);
            new ControleurAccueil(vueAccueil, client, admin);
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
            v.setVisible(true);
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

        vue.getLoupeBtn().addActionListener(e -> {
            VueRecherche v = new VueRecherche(client, admin);
            new ControleurRecherche(v, client, admin);
            v.setVisible(true);
            vue.dispose();
        });

        vue.getBtnPrev().addActionListener(e -> {
            YearMonth curr = vue.getMoisActuel();
            if (curr.isAfter(vue.getMinMonth())) {
                vue.setMoisActuel(curr.minusMonths(1));
                updateCalendar();
            }
        });

        vue.getBtnNext().addActionListener(e -> {
            YearMonth curr = vue.getMoisActuel();
            if (curr.isBefore(vue.getMaxMois())) {
                vue.setMoisActuel(curr.plusMonths(1));
                updateCalendar();
            }
        });
    }

    // mise à jour calendrier
    private void updateCalendar() {
        JPanel calendar = vue.getCalendarPanel();
        calendar.removeAll();

        // affichage jour de la semaine
        for (DayOfWeek day : DayOfWeek.values()) {
            JLabel label = new JLabel(day.getDisplayName(java.time.format.TextStyle.SHORT, Locale.FRENCH), SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            calendar.add(label);
        }

        // affichage jour et mois actuels
        YearMonth month = vue.getMoisActuel();
        vue.getMoisLabel().setText(month.getMonth().getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH).toUpperCase() + " " + month.getYear());

        LocalDate firstDay = month.atDay(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue();

        // cases vides au début du mois
        for (int i = 1; i < dayOfWeek; i++) {
            calendar.add(new JLabel(""));
        }

        // boutons pour chaque jours du mois
        for (int day = 1; day <= month.lengthOfMonth(); day++) {
            LocalDate date = month.atDay(day);
            JButton btn = new JButton(String.valueOf(day));
            btn.addActionListener(e -> afficherAttractions(date));
            calendar.add(btn);
        }

        calendar.revalidate();
        calendar.repaint();
    }

    // affichage des attractions en fonction de la date sélectionnée
    private void afficherAttractions(LocalDate date) {
        JPanel details = vue.getDetailsPanel();
        details.removeAll();

        // mise en page
        JLabel titre = new JLabel("Attractions disponibles le " + date);
        titre.setFont(new Font("SansSerif", Font.BOLD, 16));
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        details.add(titre);
        details.add(Box.createRigidArea(new Dimension(0, 10)));

        boolean found = false;

        // connexion à la bdd
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_attraction", "root", "");
             PreparedStatement ps = conn.prepareStatement("SELECT id_attraction, nom FROM attraction");
             ResultSet rs = ps.executeQuery()) {

            // on parcours les attractions et on affiche celles disponibles
            while (rs.next()) {
                int id = rs.getInt("id_attraction");
                String nom = rs.getString("nom");

                // vérification de la disponibilité
                if (attractionDAO.estDisponible(date, id)) {
                    // création d'un bouton qui mène à la page avec les informations de l'attraction
                    found = true;
                    JButton btn = new JButton(nom);

                    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                    btn.addActionListener(ev -> {
                        Attraction attraction = attractionDAO.chercher(id);

                        if (attraction != null) {
                            VueInfoAttraction vueInfoAttraction = new VueInfoAttraction(attraction, date, client, admin);
                            new ControleurInfoAttraction(vueInfoAttraction, client, admin, attraction, date);
                            vueInfoAttraction.setVisible(true);
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
