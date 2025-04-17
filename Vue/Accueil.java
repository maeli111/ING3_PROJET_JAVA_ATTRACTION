package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.BufferedReader;
import java.io.File;
import javax.imageio.ImageIO;

public class Accueil extends JFrame{
    //boutons en haut de la page
    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte=new JButton("Compte");

    //nom du parc
    private JTextField parc = new JTextField("Palasi Land");


    public Accueil(){
        setTitle("Accueil");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //STYLES UTILISES POUR LA PAGE
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

        //ajout des 2 panels creer pour les boutons du haut dans le layout
        Pbarre.add(Pnavigation, BorderLayout.WEST);
        Pbarre.add(Pcompte, BorderLayout.EAST);
        //add(Pbarre, BorderLayout.NORTH);

        //header avec la barre de navigation et le nom du parc
        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());
        header.add(Pbarre, BorderLayout.NORTH);
        header.add(parc, BorderLayout.CENTER);

        //ajout de notre header au layout
        add(header, BorderLayout.NORTH);


        //images
        //image de gauche
        JPanel images = new JPanel(new BorderLayout());
        try{
            BufferedImage image1 =ImageIO.read(new File("C:\\wamp64\\www\\ING3_PROJET_JAVA_ATTRACTION\\Vue\\carroussel1.jpg"));
            Image scaledImg1 = image1.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel img1 = new JLabel(new ImageIcon(scaledImg1));
            images.add(img1, BorderLayout.WEST);
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image de gauche : " + e.getMessage());
        }
        //image de droite
        try {
            BufferedImage image2 = ImageIO.read(new File("C:\\wamp64\\www\\ING3_PROJET_JAVA_ATTRACTION\\Vue\\carroussel2.jpg"));
            Image scaledImg2 = image2.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel img2 = new JLabel(new ImageIcon(scaledImg2));
            images.add(img2, BorderLayout.EAST);
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image de droite : " + e.getMessage());
        }

        add(images, BorderLayout.CENTER);


    }
}

//SOURCES :
//images : https://docs.oracle.com/javase/8/docs/api/index.html?javax/imageio/ImageIO.html