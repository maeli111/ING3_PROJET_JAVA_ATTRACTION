package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Locale;

public class Calendrier extends JFrame {
    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");
    private JTextField parc = new JTextField("Palasi Land");

    private JPanel calendarPanel;
    private JLabel moisLabel;
    private YearMonth currentMonth;

    private final YearMonth minMonth = YearMonth.now();
    private final YearMonth maxMonth = YearMonth.of(2026, 4); // Avril 2026

    public Calendrier() {
        setTitle("Calendrier des Attractions");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // === HEADER ===
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

        JPanel Pcompte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        Pcompte.add(compte);

        Pbarre.add(Pnavigation, BorderLayout.WEST);
        Pbarre.add(Pcompte, BorderLayout.EAST);

        JPanel header = new JPanel(new BorderLayout());
        header.add(Pbarre, BorderLayout.NORTH);
        header.add(parc, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // === CONTENU CALENDRIER ===
        currentMonth = YearMonth.now();

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Navigation mois
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

        add(wrapper, BorderLayout.CENTER);

        updateCalendar(); // Initial load

        setVisible(true);
    }

    private void updateCalendar() {
        calendarPanel.removeAll();

        // Label jours de la semaine
        for (DayOfWeek day : DayOfWeek.values()) {
            JLabel label = new JLabel(day.getDisplayName(java.time.format.TextStyle.SHORT, Locale.FRENCH), SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            calendarPanel.add(label);
        }

        moisLabel.setText(currentMonth.getMonth().getDisplayName(java.time.format.TextStyle.FULL, Locale.FRENCH).toUpperCase() + " " + currentMonth.getYear());

        LocalDate firstDay = currentMonth.atDay(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue(); // 1=Lundi, 7=Dimanche

        // Décalage avant le 1er
        for (int i = 1; i < dayOfWeek; i++) {
            calendarPanel.add(new JLabel(""));
        }

        int daysInMonth = currentMonth.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.addActionListener(new DayClickListener(day));
            calendarPanel.add(dayButton);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private static class DayClickListener implements ActionListener {
        private final int day;

        public DayClickListener(int day) {
            this.day = day;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Simulation des attractions dispos
            String[] attractions = {
                    "Grand Huit - 8 places restantes",
                    "Chaises Volantes - 3 places restantes",
                    "Train Fantôme - 0 places (complet)"
            };

            StringBuilder message = new StringBuilder("Attractions le " + day + " :\n");
            for (String att : attractions) {
                if (!att.contains("0 places")) {
                    message.append("• ").append(att).append("\n");
                }
            }

            JOptionPane.showMessageDialog(null, message.toString(), "Disponibilités", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
