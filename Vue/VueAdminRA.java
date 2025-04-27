package Vue;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import Modele.*;

public class VueAdminRA extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;
    private JButton compteButton;

    public VueAdminRA(Admin admin) {
        setTitle("Réductions liées aux attractions - Admin");
        setSize(1250, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color hoverColor = new Color(255, 182, 193);
        Color defaultColor = UIManager.getColor("Button.background");

        // Haut Panel
        JPanel hautPanel = new JPanel(new BorderLayout());
        JPanel buttonBar = new JPanel(new BorderLayout());

        JPanel gauchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel droitePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        compteButton = new JButton("Compte");
        droitePanel.add(compteButton);

        buttonBar.add(gauchePanel, BorderLayout.WEST);
        buttonBar.add(droitePanel, BorderLayout.EAST);

        // Titre principal
        JPanel titrePanel = new JPanel(new GridLayout(2, 1));
        JLabel parc = new JLabel("Palasi Land");
        parc.setHorizontalAlignment(JLabel.CENTER);
        parc.setFont(new Font("Bodoni MT", Font.BOLD, 32));
        parc.setBorder(null);
        parc.setOpaque(false);

        JLabel gestionLabel = new JLabel("Gestion Réduction Attractions");
        gestionLabel.setHorizontalAlignment(JLabel.CENTER);
        gestionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        gestionLabel.setForeground(new Color(100, 100, 100));

        titrePanel.add(parc);
        titrePanel.add(gestionLabel);

        hautPanel.add(buttonBar, BorderLayout.NORTH);
        hautPanel.add(titrePanel, BorderLayout.CENTER);

        add(hautPanel, BorderLayout.NORTH);

        // Tableau
        String[] columns = {"ID", "Nom", "Pourcentage", "Description", "Attractions Concernées"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(230, 230, 250));
        table.getTableHeader().setForeground(new Color(60, 60, 60));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setGridColor(new Color(220, 220, 220));
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(255, 192, 203));
        table.setSelectionForeground(Color.BLACK);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Boutons en bas
        JPanel buttonPanel = new JPanel();
        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);
        ajouterButton.setFont(buttonFont);
        modifierButton.setFont(buttonFont);
        supprimerButton.setFont(buttonFont);

        buttonPanel.add(ajouterButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(supprimerButton);

        add(buttonPanel, BorderLayout.SOUTH);

        applyHoverEffect(compteButton, hoverColor, defaultColor);
        applyHoverEffect(ajouterButton, hoverColor, defaultColor);
        applyHoverEffect(modifierButton, hoverColor, defaultColor);
        applyHoverEffect(supprimerButton, hoverColor, defaultColor);
    }

    // Méthode pour effet hover
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

    // Getters
    public JButton getAjouterButton() { return ajouterButton; }
    public JButton getModifierButton() { return modifierButton; }
    public JButton getSupprimerButton() { return supprimerButton; }
    public JButton getCompteButton() { return compteButton; }
    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
}
