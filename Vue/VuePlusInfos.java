package Vue;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import DAO.*;
import Modele.*;

public class VuePlusInfos extends JFrame {
    public JButton accueil = new JButton("Accueil");
    public JButton informations = new JButton("Informations");
    public JButton calendrier = new JButton("Calendrier");
    public JButton compte = new JButton("Compte");

    private JTextField parc = new JTextField("Palasi Land");

    public VuePlusInfos(Client client, Admin admin) {
        setTitle("Plus d'informations");
        setSize(1250, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Couleurs
        Color hoverColor = new Color(255, 182, 193); // Rose clair
        Color defaultColor = UIManager.getColor("Button.background"); // Couleur par défaut des boutons

        // Appliquer le style de survol à tous les boutons
        applyHoverEffect(accueil, hoverColor, defaultColor);
        applyHoverEffect(informations, hoverColor, defaultColor);
        applyHoverEffect(calendrier, hoverColor, defaultColor);
        applyHoverEffect(compte, hoverColor, defaultColor);

        informations.setBackground(new Color(255, 182, 193));
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
        Pcompte.add(compte);
        Pbarre.add(Pnavigation, BorderLayout.WEST);
        Pbarre.add(Pcompte, BorderLayout.EAST);

        JPanel header = new JPanel(new BorderLayout());
        header.add(Pbarre, BorderLayout.NORTH);
        header.add(parc, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel contenu = new JPanel();
        contenu.setLayout(new BoxLayout(contenu, BoxLayout.Y_AXIS));
        contenu.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        ArrayList<Attraction> attractions = new AttractionDao(daoFactory).getAll();
        ArrayList<Reduction> reductions = new ReductionDao(daoFactory).getAll();

        Map<String, ArrayList<Attraction>> attractionsParType = new HashMap<>();
        for (Attraction a : attractions) {
            attractionsParType.computeIfAbsent(a.getType_attraction(), k -> new ArrayList<>()).add(a);
        }

        Map<String, String> typeIcones = Map.of(
                "Sensations fortes", "🌪️",
                "Aquatique", "🌊",
                "Famille", "🎠",
                "Interactif", "🕹️",
                "Horreur", "🌀"
        );

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><div style='text-align: center; font-family: Arial; font-size: 12px;'>")
                .append("<b>Palasi Land</b> est un parc d'attractions destiné aux plus jeunes comme aux plus grands !<br>")
                .append("Venez vivre des moments inoubliables en famille ou entre amis dans un univers mythologique et fantastique! <br><br>")
                .append("🎢 <b>Palasi Land</b> vous offre un choix varié d'attractions, pour tous les goûts. <br><br>");

        for (String type : typeIcones.keySet()) {
            if (attractionsParType.containsKey(type)) {
                htmlBuilder.append("<b>").append(typeIcones.get(type)).append(" ")
                        .append(Character.toUpperCase(type.charAt(0))).append(type.substring(1)).append(" :</b><br>");
                for (Attraction a : attractionsParType.get(type)) {
                    htmlBuilder.append("• ").append(a.getNom()).append(" - ").append(a.getPrix()).append("€<br>");

                    // Ajouter l'image correspondant à l'attraction
                    int attractionId = a.getId_attraction();
                    String imagePath = "C:\\wamp64\\www\\ING3_PROJET_JAVA_ATTRACTION\\Images\\" + attractionId + ".jpg";
                    htmlBuilder.append("<img src='file:///" + imagePath + "' width='200' height='150'/> <br>");
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

        String[] colonnes = {"Réductions", "Pourcentage", "Description"};
        Object[][] data = new Object[reductions.size()][3];
        for (int i = 0; i < reductions.size(); i++) {
            Reduction r = reductions.get(i);
            data[i][0] = r.getNom();
            data[i][1] = (int) r.getPourcentage() + "%";
            data[i][2] = r.getDescription();
        }

        JTable tableau = new JTable(data, colonnes);
        JScrollPane tableauScroll = new JScrollPane(tableau);
        tableauScroll.setAlignmentX(Component.CENTER_ALIGNMENT);
        tableauScroll.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        contenu.add(tableauScroll);

        JScrollPane scrollPane = new JScrollPane(contenu);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Méthode pour appliquer l'effet de survol à un bouton
    private void applyHoverEffect(JButton button, Color hoverColor, Color defaultColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(defaultColor);
            }
        });
    }
}