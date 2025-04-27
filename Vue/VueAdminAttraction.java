package Vue;

import Modele.Admin;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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
        Color defaultColor = UIManager.getColor("Button.background"); // Couleur bouton par défaut

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
        titreLabel.setFont(new Font("Bodoni MT", Font.BOLD, 32)); // Même style de titre
        titrePanel.add(titreLabel);

        hautPanel.add(buttonBar, BorderLayout.NORTH);
        hautPanel.add(titrePanel, BorderLayout.CENTER);
        add(hautPanel, BorderLayout.NORTH);

        // Colonnes du tableau
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Description", "Prix", "Capacité", "Type"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre le tableau non éditable
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Style du tableau
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(230, 230, 250)); // Fond header lavande clair
        table.getTableHeader().setForeground(new Color(60, 60, 60)); // Texte header gris foncé
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28); // Hauteur des lignes
        table.setGridColor(new Color(220, 220, 220)); // Couleur de la grille
        table.setBackground(Color.WHITE); // Fond du tableau
        table.setSelectionBackground(new Color(255, 192, 203)); // Rose clair sélection
        table.setSelectionForeground(Color.BLACK); // Texte sélectionné noir

        // Centrer le texte dans les cellules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table); // Scrollable
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Un peu de padding
        add(scrollPane, BorderLayout.CENTER);

        // Bas de page : Boutons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        bottomPanel.add(ajouterButton);
        bottomPanel.add(modifierButton);
        bottomPanel.add(supprimerButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Hover effet sur boutons
        applyHoverEffect(ajouterButton, hoverColor, defaultColor);
        applyHoverEffect(modifierButton, hoverColor, defaultColor);
        applyHoverEffect(supprimerButton, hoverColor, defaultColor);
        applyHoverEffect(compteButton, hoverColor, defaultColor);
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
