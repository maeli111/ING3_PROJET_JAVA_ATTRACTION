package Vue;

import Modele.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;

public class VueAccueil extends JFrame{
    //boutons en haut de la page
    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte=new JButton("Compte");

    //nom du parc
    private JTextField parc = new JTextField("Palasi Land");


    public VueAccueil(Client client, Admin admin){
        setTitle("Accueil");
        setSize(1250,680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //couleur pour savoir la page selectionnee
        accueil.setBackground(new Color(255,182,193));
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

        compte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        //scroll
        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));


        //images
        //image de gauche
        JPanel images = new JPanel(new GridLayout(1, 3, 20, 0));

        try {
            BufferedImage image1 = ImageIO.read(new File("C:\\wamp64\\www\\TP8_ING3\\ING3_PROJET_JAVA_ATTRACTION\\Vue\\carroussel1.jpg"));
            Image scaledImg1 = image1.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel img1 = new JLabel(new ImageIcon(scaledImg1));
            img1.setHorizontalAlignment(SwingConstants.CENTER);
            images.add(img1);
        } catch (Exception e) {
            System.out.println("Erreur image 1 : " + e.getMessage());
        }

        try {
            BufferedImage image2 = ImageIO.read(new File("C:\\wamp64\\www\\TP8_ING3\\ING3_PROJET_JAVA_ATTRACTION\\Vue\\carroussel2.jpg"));
            Image scaledImg2 = image2.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel img2 = new JLabel(new ImageIcon(scaledImg2));
            img2.setHorizontalAlignment(SwingConstants.CENTER);
            images.add(img2);
        } catch (Exception e) {
            System.out.println("Erreur image 2 : " + e.getMessage());
        }

        try {
            BufferedImage image3 = ImageIO.read(new File("C:\\wamp64\\www\\TP8_ING3\\ING3_PROJET_JAVA_ATTRACTION\\Vue\\carroussel3.jpg"));
            Image scaledImg3 = image3.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel img3 = new JLabel(new ImageIcon(scaledImg3));
            img3.setHorizontalAlignment(SwingConstants.CENTER);
            images.add(img3);
        } catch (Exception e) {
            System.out.println("Erreur image 3 : " + e.getMessage());
        }

        contenu.add(images);


        //texte d'informations sur le parc
        JLabel description = new JLabel(
                "<html><div style='text-align: center;'>"
                        + "Entrez dans l'univers magique de <b>Palasi Land</b> <br>"
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


        description.setFont(new Font("Bodoni MT", Font.PLAIN, 18));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contenu.add(description);

        //bouton pour + d'informations
        JButton infos = new JButton("Plus d'informations");
        infos.setAlignmentX(Component.CENTER_ALIGNMENT);
        infos.setPreferredSize(new Dimension(150, 40));
        contenu.add(Box.createRigidArea(new Dimension(0, 20))); // espace
        contenu.add(infos);
        contenu.add(Box.createRigidArea(new Dimension(0, 20))); // espace bas

        // action pour le bouton "Plus d'informations"
        infos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VuePlusInfos(client, admin);
                dispose(); // pour fermer la fenêtre actuelle si tu veux
            }
        });


        // action pour le bouton "Informations" dans la barre de navigation
        informations.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VuePlusInfos(client, admin);
                dispose();
            }
        });


        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(contenu);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

    }

}


//SOURCES :
//images : https://docs.oracle.com/javase/8/docs/api/index.html?javax/imageio/ImageIO.html

//sauter des lignes dans un JLabel : https://openclassrooms.com/forum/sujet/saut-de-ligne-dans-un-jlabel-89457