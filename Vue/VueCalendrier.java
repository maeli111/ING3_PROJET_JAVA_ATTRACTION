package Vue;

import Modele.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.time.*;

public class VueCalendrier extends JFrame {

    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");
    private JButton loupeBtn; // AJOUT du bouton loupe
    private JTextField parc = new JTextField("Palasi Land");

    private JPanel calendarPanel;
    private JPanel detailsPanel;

    private JLabel moisLabel;
    private YearMonth currentMonth;

    private final YearMonth minMonth = YearMonth.now();
    private final YearMonth maxMonth = YearMonth.of(2026, 4);

    private Client client;
    private Admin admin;

    private JButton prevButton;
    private JButton nextButton;

    public VueCalendrier(Client client, Admin admin) {
        this.client = client;
        this.admin = admin;

        setTitle("Calendrier des Attractions");
        setSize(1250, 680);
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

        JPanel Pcompte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        // AJOUT de la LOUPE
        try {
            BufferedImage loupeImage = ImageIO.read(new File("images/loupe.png"));
            Image scaledLoupe = loupeImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            loupeBtn = new JButton(new ImageIcon(scaledLoupe));
            loupeBtn.setBorder(BorderFactory.createEmptyBorder());
            loupeBtn.setContentAreaFilled(false);
            Pcompte.add(loupeBtn);
            Pcompte.add(Box.createRigidArea(new Dimension(5, 0)));
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image loupe : " + e.getMessage());
            loupeBtn = new JButton("🔍");
            Pcompte.add(loupeBtn);
            Pcompte.add(Box.createRigidArea(new Dimension(5, 0)));
        }

        Pcompte.add(compte);

        Pbarre.add(Pnavigation, BorderLayout.WEST);
        Pbarre.add(Pcompte, BorderLayout.EAST);

        JPanel header = new JPanel(new BorderLayout());
        header.add(Pbarre, BorderLayout.NORTH);
        header.add(parc, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // CONTENU
        currentMonth = YearMonth.now();
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        prevButton = new JButton("←");
        nextButton = new JButton("→");

        moisLabel = new JLabel("", SwingConstants.CENTER);
        moisLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        topPanel.add(prevButton, BorderLayout.WEST);
        topPanel.add(moisLabel, BorderLayout.CENTER);
        topPanel.add(nextButton, BorderLayout.EAST);

        wrapper.add(topPanel, BorderLayout.NORTH);

        calendarPanel = new JPanel(new GridLayout(0, 7, 5, 5));
        wrapper.add(calendarPanel, BorderLayout.CENTER);
        mainPanel.add(wrapper, BorderLayout.CENTER);

        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Attractions disponibles"));
        detailsPanel.setPreferredSize(new Dimension(300, 0));

        mainPanel.add(detailsPanel, BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // --- Méthodes accessibles au contrôleur ---
    public JButton getBtnAccueil() { return accueil; }
    public JButton getBtnInfos() { return informations; }
    public JButton getBtnCalendrier() { return calendrier; }
    public JButton getBtnCompte() { return compte; }
    public JButton getBtnPrev() { return prevButton; }
    public JButton getBtnNext() { return nextButton; }
    public JButton getLoupeBtn() { return loupeBtn; } // GETTER pour la loupe

    public JPanel getCalendarPanel() { return calendarPanel; }
    public JPanel getDetailsPanel() { return detailsPanel; }

    public JLabel getMoisLabel() { return moisLabel; }
    public YearMonth getCurrentMonth() { return currentMonth; }
    public void setCurrentMonth(YearMonth ym) { this.currentMonth = ym; }

    public YearMonth getMinMonth() { return minMonth; }
    public YearMonth getMaxMonth() { return maxMonth; }
}
