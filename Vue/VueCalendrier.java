package Vue;

import Modele.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.time.*;

// Fenêtre qui affiche le calendrier des attractions
public class VueCalendrier extends JFrame {
    // composants du header
    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");
    private JButton loupeBtn;
    private JTextField parc = new JTextField("Palasi Land");

    // composants du calendrier
    private JPanel calendarPanel; // calendrier
    private JPanel detailsPanel; // infos sur les attractions
    private JLabel moisLabel; // nom du mois
    private YearMonth moisActuel; // le mois affiché actuellement

    // limitation de la navigation dans le celendrier (ne peux pas aller avant le mois actuel ni plus d'un an après)
    private final YearMonth minMois = YearMonth.now();
    private final YearMonth maxMois = YearMonth.now().plusYears(1);

    private Client client;
    private Admin admin;

    // boutons pour naviguer entre les mois
    private JButton precButton;
    private JButton suivButton;

    // couleurs pour le survol
    private final Color dessusCouleur = new Color(255, 182, 193); // Rose clair
    private final Color defaultCouleur = UIManager.getColor("Button.background"); // Couleur par défaut

    public VueCalendrier(Client client, Admin admin) {
        this.client = client;
        this.admin = admin;

        // info de la fenêtre
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

        // appliquer l'effet de survol à tous les boutons
        applyHoverEffect(accueil, dessusCouleur, defaultCouleur);
        applyHoverEffect(informations, dessusCouleur, defaultCouleur);
        applyHoverEffect(calendrier, dessusCouleur, defaultCouleur);
        applyHoverEffect(compte, dessusCouleur, defaultCouleur);

        calendrier.setBackground(dessusCouleur); // Bouton calendrier actif

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
            // Effet de survol spécial pour la loupe
            loupeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    loupeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    loupeBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });
            Pcompte.add(loupeBtn);
            Pcompte.add(Box.createRigidArea(new Dimension(5, 0)));
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image loupe : " + e.getMessage());
            loupeBtn = new JButton("🔍");
            applyHoverEffect(loupeBtn, dessusCouleur, defaultCouleur);
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
        moisActuel = YearMonth.now();
        JPanel mainPanel = new JPanel(new BorderLayout());

        // dle haut du calendrier
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        precButton = new JButton("←");
        suivButton = new JButton("→");

        // Appliquer l'effet aux boutons de navigation du calendrier
        applyHoverEffect(precButton, dessusCouleur, defaultCouleur);
        applyHoverEffect(suivButton, dessusCouleur, defaultCouleur);

        moisLabel = new JLabel("", SwingConstants.CENTER);
        moisLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

        topPanel.add(precButton, BorderLayout.WEST);
        topPanel.add(moisLabel, BorderLayout.CENTER);
        topPanel.add(suivButton, BorderLayout.EAST);

        wrapper.add(topPanel, BorderLayout.NORTH);

        // grille du calendrier
        calendarPanel = new JPanel(new GridLayout(0, 7, 5, 5));
        wrapper.add(calendarPanel, BorderLayout.CENTER);
        mainPanel.add(wrapper, BorderLayout.CENTER);

        // partie pour afficher les attractions dispo le jour sélectionné
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Attractions disponibles"));
        detailsPanel.setPreferredSize(new Dimension(300, 0));

        mainPanel.add(detailsPanel, BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Méthode pour appliquer l'effet de survol
    private void applyHoverEffect(JButton button, Color hoverColor, Color defaultColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.equals(calendrier)) { // Ne pas changer si c'est le bouton actif
                    button.setBackground(defaultColor);
                }
            }
        });
    }

    // getters pour le controleur
    public JButton getBtnAccueil() { return accueil; }
    public JButton getBtnInfos() { return informations; }
    public JButton getBtnCalendrier() { return calendrier; }
    public JButton getBtnCompte() { return compte; }
    public JButton getBtnPrev() { return precButton; }
    public JButton getBtnNext() { return suivButton; }
    public JButton getLoupeBtn() { return loupeBtn; }

    public JPanel getCalendarPanel() { return calendarPanel; }
    public JPanel getDetailsPanel() { return detailsPanel; }

    public JLabel getMoisLabel() { return moisLabel; }
    public YearMonth getMoisActuel() { return moisActuel; }
    public void setMoisActuel(YearMonth ym) { this.moisActuel = ym; }

    public YearMonth getMinMonth() { return minMois; }
    public YearMonth getMaxMois() { return maxMois; }
}