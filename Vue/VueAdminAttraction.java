package Vue;

import Modele.Admin;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VueAdminAttraction extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton compteButton, ajouterButton, modifierButton, supprimerButton;

    public VueAdminAttraction(Admin admin) {
        setTitle("Attractions - Admin");
        setSize(1250, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color hoverColor = new Color(255, 182, 193); // Rose clair hover
        Color defaultColor = new Color(230, 230, 250); // Lavande clair
        Color textColor = new Color(60, 60, 60); // Gris foncé pour texte

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonBar = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        compteButton = new JButton("Compte");
        rightPanel.add(compteButton);

        buttonBar.add(leftPanel, BorderLayout.WEST);
        buttonBar.add(rightPanel, BorderLayout.EAST);

        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel parc = new JLabel("Palasi Land");
        parc.setHorizontalAlignment(JLabel.CENTER);
        parc.setFont(new Font("Bodoni MT", Font.BOLD, 32));
        parc.setBorder(null);
        parc.setOpaque(false);
        titrePanel.add(parc);

        JPanel gestionPanel = new JPanel();
        JLabel gestionLabel = new JLabel("Gestion des Attractions");
        gestionLabel.setHorizontalAlignment(JLabel.CENTER);
        gestionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gestionLabel.setForeground(new Color(0, 0, 0));
        gestionPanel.add(gestionLabel);

        topPanel.add(buttonBar, BorderLayout.NORTH);
        topPanel.add(titrePanel, BorderLayout.CENTER);
        topPanel.add(gestionPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Table des attractions
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Description", "Prix", "Capacité", "Type"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Style de la table
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(defaultColor);
        table.getTableHeader().setForeground(textColor);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setGridColor(new Color(220, 220, 220));
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(255, 192, 203));
        table.setSelectionForeground(Color.BLACK);

        // Pour centrer le texte dans toutes les colonnes
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Boutons du bas
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        Font boutonFont = new Font("Segoe UI", Font.BOLD, 16);
        JButton[] boutons = {ajouterButton, modifierButton, supprimerButton};
        for (JButton btn : boutons) {
            btn.setFont(boutonFont);
            btn.setBackground(defaultColor);
            btn.setForeground(textColor);
            bottomPanel.add(btn);
        }

        add(bottomPanel, BorderLayout.SOUTH);

        applyHoverEffect(ajouterButton, hoverColor, defaultColor);
        applyHoverEffect(modifierButton, hoverColor, defaultColor);
        applyHoverEffect(supprimerButton, hoverColor, defaultColor);
        applyHoverEffect(compteButton, hoverColor, UIManager.getColor("Button.background")); // Compte = normal
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
