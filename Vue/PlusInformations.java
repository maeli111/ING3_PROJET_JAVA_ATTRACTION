package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;

public class PlusInformations extends JFrame {
    //boutons en haut de la page
    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");

    //nom du parc
    private JTextField parc = new JTextField("Palasi Land");

    public PlusInformations() {
        setTitle("Plus d'informations");
        setSize(1250, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //couleur pour savoir la page selectionnee
        informations.setBackground(new Color(255, 182, 193));

        //style du nom du parc
        parc.setHorizontalAlignment(JTextField.CENTER);
        parc.setEditable(false);
        parc.setFont(new Font("Bodoni MT", Font.BOLD, 32));
        //supprimer les bordures des composants + rendre le fond transparent
        parc.setBorder(null);
        parc.setOpaque(false);

        //barre de navigation
        JPanel Pbarre = new JPanel(new BorderLayout());

        //boutons de navigation qu'on place a gauche
        JPanel Pnavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        Pnavigation.add(accueil);
        Pnavigation.add(informations);
        Pnavigation.add(calendrier);

        //bouton d'acces au compte qu'on place a droite
        JPanel Pcompte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        Pcompte.add(compte);

        //ajout des 2 panels creer pour les boutons du haut dans le layout
        Pbarre.add(Pnavigation, BorderLayout.WEST);
        Pbarre.add(Pcompte, BorderLayout.EAST);

        //header avec la barre de navigation et le nom du parc
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.add(Pbarre, BorderLayout.NORTH);
        header.add(parc, BorderLayout.CENTER);

        //ajout de notre header au layout
        add(header, BorderLayout.NORTH);

        //contenu avec scroll
        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel infos = new JLabel(
                "<html><div style='text-align: center;'>"
                        + "<b>Palasi Land</b> est un parc d'attraction destiné aux plus jeunes comme aux plus grands,<br>"
                        + "là où les légendes anciennes prennent vie et où les dieux vous entraînent dans des aventures extraordinaires !<br><br>"
                        + "Que vous soyez prêt à défier les titans dans des montagnes russes vertigineuses,<br>"
                        + "plonger dans les profondeurs des océans mystiques ou voyager à travers des terres enchantées,<br><br>"
                        + "<b>Palasi Land</b> vous offrira des expériences mythologiques<br>"
                        + "pour tous les courageux aventuriers et aventurières, petits et grands !<br><br>"
                        + "Venez découvrir et percer les secrets de vos héros préférés,<br>"
                        + "rencontrer des créatures légendaires, passer un moment inoubliable avec des dieux antiques<br>"
                        + "dans un parc où chaque attraction vous plonge au cœur d’un conte épique !"
                        + "</div></html>",
                SwingConstants.CENTER
        );

        infos.setFont(new Font("Bodoni MT", Font.PLAIN, 18));
        infos.setAlignmentX(Component.CENTER_ALIGNMENT);
        infos.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contenu.add(infos);

        // actions sur les boutons
        accueil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Accueil accueil1 = new Accueil();
                accueil1.setVisible(true);
                dispose();
            }
        });

        // contenu avec scroll
        JScrollPane scrollPane = new JScrollPane(contenu);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // Pour tester
        setVisible(true);
    }
}