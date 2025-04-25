package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;

import DAO.*;
import Modele.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



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

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root","");
        List<Attraction> attractions = new AttractionDao(daoFactory).getAll();
        List<Reduction> reductions = new ReductionDao(daoFactory).getAll();


        Map<String, List<Attraction>> attractionsParType = new HashMap<>();
        for (Attraction a : attractions) {
            attractionsParType.computeIfAbsent(a.getType_attraction(), k -> new ArrayList<>()).add(a);
        }

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><div style='text-align: center; font-family: Arial; font-size: 12px;'>")
                .append("<b>Palasi Land</b> est un parc d'attractions destiné aux plus jeunes comme aux plus grands !<br>")
                .append("Venez vivre des moments inoubliables en famille ou entre amis dans un univers mythologique et fantastique! <br><br>")
                .append("🎢 <b>Palasi Land</b> vous offre un choix varié d’attractions, pour tous les goûts. <br><br>")
                .append(" En passant par quatre attractions à sensations fortes, trois attractions aquatiques, <br>")
                .append(" deux manèges familiaux, deux manèges intéractifs et deux attractions plus mystérieuses <br><br>");

        // fonction pour ajouter dynamiquement les sections
        Map<String, String> typeIcones = Map.of(
                "Sensations fortes", "🌪️",
                "Aquatique", "🌊",
                "Famille", "🎠",
                "Interactif", "🕹️",
                "Horreur", "🌀"
        );

        for (String type : typeIcones.keySet()) {
            if (attractionsParType.containsKey(type)) {
                htmlBuilder.append("<b>").append(typeIcones.get(type)).append(" ")
                        .append(Character.toUpperCase(type.charAt(0))).append(type.substring(1)).append(" :</b><br>");
                for (Attraction a : attractionsParType.get(type)) {
                    htmlBuilder.append("• ").append(a.getNom()).append(" - ").append(a.getPrix()).append("€<br>");
                }
                htmlBuilder.append("<br>");
            }
        }


        htmlBuilder.append("<b>💸 Tarifs réduits :</b><br>")
                .append("Profitez de réductions spéciales selon votre profil de visiteur !<br>")
                .append("Découvrez-les dans le tableau ci-dessous ⬇️<br><br>")
                .append("</div></html>");

        JLabel infos = new JLabel(htmlBuilder.toString(), SwingConstants.CENTER);
        infos.setFont(new Font("Bodoni MT", Font.PLAIN, 18));
        infos.setAlignmentX(Component.CENTER_ALIGNMENT);
        infos.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contenu.add(infos);

        // tableau dynamique depuis les réductions
        String[] colonnes = {"Réductions", "Pourcentage", "Description"};
        Object[][] data = new Object[reductions.size()][3];
        for (int i = 0; i < reductions.size(); i++) {
            Reduction r = reductions.get(i);
            data[i][0] = r.getNom();
            data[i][1] = (int) r.getPourcentage() + "%";
            data[i][2] = r.getDescription();
        }

        JTable tableau = new JTable(data, colonnes);
        tableau.setFillsViewportHeight(true);



        JScrollPane tableauScroll = new JScrollPane(tableau);
        tableauScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        tableauScroll.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // ajout du tableau dans le contenu
        contenu.add(tableauScroll);


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