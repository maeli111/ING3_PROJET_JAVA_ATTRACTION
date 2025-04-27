package Vue;

import Modele.Admin;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VueAdminAttraction extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton compteButton;
    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;

    public VueAdminAttraction(Admin admin) {
        setTitle("Attractions - Admin");
        setSize(1250, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color hoverColor = new Color(255, 182, 193); // Rose clair au survol
        Color defaultColor = new Color(230, 230, 250); // Fond lavande clair pour les boutons
        Color textColor = new Color(60, 60, 60); // Texte gris foncé

        JPanel hautPanel = new JPanel(new BorderLayout());
        JPanel buttonBar = new JPanel(new BorderLayout());
        JPanel gauchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel droitePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        compteButton = new JButton("Compte");
        droitePanel.add(compteButton);

        buttonBar.add(gauchePanel, BorderLayout.WEST);
        buttonBar.add(droitePanel, BorderLayout.EAST);

        // Titre centré
        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titreLabel = new JLabel("PalasiLand - Gestion des attractions");
        titreLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titrePanel.add(titreLabel);

        hautPanel.add(buttonBar, BorderLayout.NORTH);
        hautPanel.add(titrePanel, BorderLayout.CENTER);
        add(hautPanel, BorderLayout.NORTH);

        //colonnes du tableau
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Description", "Prix", "Capacité", "Type"}, 0);
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table); //scrollable
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        //Boutons dans le panneau du bas
        bottomPanel.add(ajouterButton);
        bottomPanel.add(modifierButton);
        bottomPanel.add(supprimerButton);

        add(bottomPanel, BorderLayout.SOUTH);

        applyHoverEffect(ajouterButton, hoverColor, defaultColor);
        applyHoverEffect(modifierButton, hoverColor, defaultColor);
        applyHoverEffect(supprimerButton, hoverColor, defaultColor);
        applyHoverEffect(compteButton, hoverColor, UIManager.getColor("Button.background")); // Compte reste normal
    }

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

    public JButton getCompteButton() { return compteButton; }

    public JButton getAjouterButton() { return ajouterButton; }

    public JButton getModifierButton() { return modifierButton; }

    public JButton getSupprimerButton() { return supprimerButton; }

    public JTable getTable() { return table; }

    public DefaultTableModel getTableModel() { return tableModel; }
}
