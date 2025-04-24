package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;

public class VuePlusInfos extends JFrame {
    //boutons en haut de la page
    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");

    //nom du parc
    private JTextField parc = new JTextField("Palasi Land");

    public VuePlusInfos() {
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

        /*JLabel infos = new JLabel(
                "<html><div style='text-align: center;'>"
                        + "<b>Palasi Land</b> est un parc d'attraction destiné aux plus jeunes comme aux plus grands<br>"
                        + "Palasi Land vous offre un choix varié d'attractions, <br>"
                        + "en passant par quatre attractions à sensations fortes comme Titan's Fury, Colère de Zeus, Hydra Coaster, Labyrinthe du minotaure" +
                        "trois attractions aquatiques comme Odysée des mers, Cascade d'Atlantide, Sirènes en furie" +
                        "deux manèges familiaux comme Carrousel d'Olympe, Petite aventure de Pégase" +
                        "deux manèges intéractifs comme Arène d'Arès, Mission Oracles" +
                        "ou encore deux attractions plus mystérieuses comme La Malédiction de Méduse, Les portes d'Hadès"+
                        "Les tarifs de nos attractions ....<b> A COMPLETER </b>"
                        + "Le système de réduction ....<b> A COMPLETER </b>"
                        + "</div></html>",
                SwingConstants.CENTER
        );*/

        JLabel infos = new JLabel(
                "<html><div style='text-align: center; font-family: Arial; font-size: 12px;'>"
                        + "<b>Palasi Land</b> est un parc d'attractions destiné aux plus jeunes comme aux plus grands !<br>"
                        + "Venez vivre des moments inoubliables en famille ou entre amis dans un univers mythologique et fantastique! <br><br>"

                        + "🎢 <b>Palasi Land</b> vous offre un choix varié d’attractions, pour tous les goûts. <br><br>"
                        + " En passant par quatre attractions à sensations fortes, trois attractions aquatiques, <br>"
                        +" deux manèges familiaux, deux manèges intéractifs et deux attractions plus mystérieuses <br><br>"

                        + "<b>🌪️ Sensations fortes :</b><br>"
                        + "• Titan's Fury - 8€<br>"
                        + "• Colère de Zeus - 6€<br>"
                        + "• Hydra Coaster - 7€<br>"
                        + "• Labyrinthe du Minotaure - 8€<br><br>"

                        + "<b>🌊 Attractions aquatiques :</b><br>"
                        + "• Odyssée des Mers - 5€<br>"
                        + "• Cascade d'Atlantide - 6€<br>"
                        + "• Sirènes en Furie - 6€<br><br>"

                        + "<b>🎠 Manèges familiaux :</b><br>"
                        + "• Carrousel d’Olympe - 4€<br>"
                        + "• Petite Aventure de Pégase - 4€<br><br>"

                        + "<b>🕹️ Manèges interactifs :</b><br>"
                        + "• Arène d’Arès - 6€<br>"
                        + "• Mission Oracles - 7€<br><br>"

                        + "<b>🌀 Attractions mystérieuses :</b><br>"
                        + "• La Malédiction de Méduse - 5€<br>"
                        + "• Les Portes d’Hadès - 6€<br><br>"

                        + "<b>💸 Tarifs réduits :</b><br>"
                        + "Profitez de réductions spéciales selon votre profil de visiteur !<br>"
                        + "Découvrez-les dans le tableau ci-dessous ⬇️<br><br>"

                        + "</div></html>",
                SwingConstants.CENTER
        );



        infos.setFont(new Font("Bodoni MT", Font.PLAIN, 18));
        infos.setAlignmentX(Component.CENTER_ALIGNMENT);
        infos.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contenu.add(infos);

        // tableau de reductions
        String[] colonnes = {"Réductions","Pourcentage", "Description"};
        Object[][] infosReduc={
                {"Première visite", "15%", "Pour les nouveaux utilisateurs à la réservation de leur 1ère attraction"},
                {"Pack famille", "25%", "Pour les familles avec 2 adultes et 2 enfants"},
                {"Fidélité", "15%", "Pour les clients ayant réservé 5 attractions ou plus"},
                {"Client enfant", "25%", "Pour les enfants de moins de 18 ans"},
                {"Client sénior", "15%", "Pour les adultes de plus de 60 ans"},
                {"Client étudiant", "20%", "Pour les étudiants entre 18 et 25 ans"},
                {"Famille nombreuse", "30%", "Pour les familles de 2 adultes et plus de 3 enfants"},
                {"Pâques","10%","Pour les fêtes, profitez de -10% sur certaines attractions"}
        };
        JTable reductions = new JTable(infosReduc, colonnes);
        reductions.setFillsViewportHeight(true);
        //reductions.setPreferredScrollableViewportSize(new Dimension(1000, 160)); // optionnel

        JScrollPane tableauScroll = new JScrollPane(reductions);
        tableauScroll.setAlignmentX(Component.CENTER_ALIGNMENT); // centrer dans le contenu
        tableauScroll.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // ajout du tableau dans le contenu
        contenu.add(tableauScroll);


        // actions sur les boutons
        accueil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VueAccueil accueil1 = new VueAccueil();
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