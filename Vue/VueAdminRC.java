package Vue;

import Modele.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VueAdminRC extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private JButton ajouter, modifier, supprimer, compte;

    public VueAdminRC(Admin admin) {
        setTitle("Réductions - Admin");
        setSize(1250, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        Color hoverColor = new Color(255, 182, 193);
        Color defaultColor = UIManager.getColor("Button.background");

        JPanel HautPanel = new JPanel(new BorderLayout());

        JPanel buttonBar = new JPanel(new BorderLayout());
        JPanel gauchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel droitePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        compte = new JButton("Compte");
        droitePanel.add(compte);

        buttonBar.add(gauchePanel, BorderLayout.WEST);
        buttonBar.add(droitePanel, BorderLayout.EAST);

        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel parc = new JLabel("Palasi Land");
        parc.setHorizontalAlignment(JTextField.CENTER);
        parc.setFont(new Font("Bodoni MT", Font.BOLD, 32));
        parc.setBorder(null);
        parc.setOpaque(false);
        titrePanel.add(parc);

        HautPanel.add(buttonBar, BorderLayout.NORTH);
        HautPanel.add(titrePanel, BorderLayout.CENTER);

        // On ajoute le titre "Gestion Réduction Client" sous Palasi Land
        JPanel gestionPanel = new JPanel();
        JLabel gestionLabel = new JLabel("Gestion Réduction Client");
        gestionLabel.setHorizontalAlignment(JTextField.CENTER);
        gestionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gestionLabel.setForeground(new Color(100, 100, 100));  // Gris foncé pour le texte
        gestionPanel.add(gestionLabel);
        HautPanel.add(gestionPanel, BorderLayout.SOUTH);

        add(HautPanel, BorderLayout.NORTH);

        // Tableau réductions liés aux clients
        String[] columns = {"ID", "Nom", "Pourcentage", "Description"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(230, 230, 250)); // Fond header
        table.getTableHeader().setForeground(new Color(60, 60, 60)); // Couleur texte header
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28); // Hauteur des lignes
        table.setGridColor(new Color(220, 220, 220)); // Couleur lignes grille
        table.setBackground(Color.WHITE); // Fond du tableau
        table.setSelectionBackground(new Color(255, 192, 203)); // Fond sélection
        table.setSelectionForeground(Color.BLACK); // Texte sélectionné

        // Pour centrer le texte dans les cellules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        ajouter = new JButton("Ajouter");
        modifier = new JButton("Modifier");
        supprimer = new JButton("Supprimer");
        buttonPanel.add(ajouter);
        buttonPanel.add(modifier);
        buttonPanel.add(supprimer);
        add(buttonPanel, BorderLayout.SOUTH);

        Font boutonFont = new Font("Segoe UI", Font.BOLD, 16);
        ajouter.setFont(boutonFont);
        modifier.setFont(boutonFont);
        supprimer.setFont(boutonFont);

        applyHoverEffect(ajouter, hoverColor, defaultColor);
        applyHoverEffect(modifier, hoverColor, defaultColor);
        applyHoverEffect(supprimer, hoverColor, defaultColor);
        applyHoverEffect(compte, hoverColor, defaultColor);
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

    public JButton getBtnAjouter() { return ajouter; }
    public JButton getBtnModifier() { return modifier; }
    public JButton getBtnSupprimer() { return supprimer; }
    public JButton getBtnCompte() { return compte; }

    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
}
