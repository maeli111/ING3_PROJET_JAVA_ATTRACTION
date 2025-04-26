package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class VueRecherche extends JFrame {

    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");
    private JButton loupeBtn;
    private JButton rechercherBtn = new JButton("Rechercher");

    private JTextField parc = new JTextField("Palasi Land");

    private JComboBox<String> filtreCombo;
    private JTextField champRecherche;

    public VueRecherche() {
        setTitle("Recherche");
        setSize(1250, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        parc.setHorizontalAlignment(JTextField.CENTER);
        parc.setEditable(false);
        parc.setFont(new Font("Bodoni MT", Font.BOLD, 32));
        parc.setBorder(null);
        parc.setOpaque(false);

        JPanel Pbarre = new JPanel(new BorderLayout());
        JPanel Pnavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        Pnavigation.add(accueil);
        Pnavigation.add(informations);
        Pnavigation.add(calendrier);
        JPanel Pcompte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        try {
            BufferedImage loupeImage = ImageIO.read(new File("images/loupe.png"));
            Image scaledLoupe = loupeImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            this.loupeBtn = new JButton(new ImageIcon(scaledLoupe));
            loupeBtn.setBorder(BorderFactory.createEmptyBorder());
            loupeBtn.setContentAreaFilled(false);
            Pcompte.add(loupeBtn);
            Pcompte.add(Box.createRigidArea(new Dimension(5, 0)));
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image loupe : " + e.getMessage());
            this.loupeBtn = new JButton("🔍");
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

        // ---- Zone principale de recherche ----
        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel question = new JLabel("Que voulez-vous rechercher ?");
        question.setFont(new Font("Bodoni MT", Font.BOLD, 24));
        question.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenu.add(question);
        contenu.add(Box.createRigidArea(new Dimension(0, 20)));

        // Champs de recherche + filtre
        JPanel panelRecherche = new JPanel();
        panelRecherche.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        champRecherche = new JTextField(20);
        champRecherche.setFont(new Font("Bodoni MT", Font.PLAIN, 18));

        String[] options = {
                "Prix croissant",
                "Prix décroissant",
                "Type",
                "Capacité croissante",
                "Capacité décroissante"
        };
        filtreCombo = new JComboBox<>(options);
        filtreCombo.setFont(new Font("Bodoni MT", Font.PLAIN, 16));

        panelRecherche.add(champRecherche);
        panelRecherche.add(filtreCombo);
        panelRecherche.add(rechercherBtn);

        contenu.add(panelRecherche);

        add(contenu, BorderLayout.CENTER);
    }

    // --- Getters pour le Contrôleur ---
    public JButton getAccueil() { return accueil; }
    public JButton getInformations() { return informations; }
    public JButton getCalendrier() { return calendrier; }
    public JButton getCompte() { return compte; }
    public JButton getLoupeBtn() { return loupeBtn; }
    public JButton getRechercherBtn() { return rechercherBtn; }
    public JTextField getChampRecherche() { return champRecherche; }
    public JComboBox<String> getFiltreCombo() { return filtreCombo; }
}
