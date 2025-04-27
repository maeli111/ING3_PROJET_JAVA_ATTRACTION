package Vue;

import javax.swing.*;
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

        JPanel hautPanel = new JPanel(new BorderLayout());
        JPanel buttonBar = new JPanel(new BorderLayout());

        JPanel gauchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel droitePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        compteButton = new JButton("Compte");
        droitePanel.add(compteButton);

        buttonBar.add(gauchePanel, BorderLayout.WEST);
        buttonBar.add(droitePanel, BorderLayout.EAST);

        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titreLabel = new JLabel("PalasiLand");
        titreLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titrePanel.add(titreLabel);

        hautPanel.add(buttonBar, BorderLayout.NORTH);
        hautPanel.add(titrePanel, BorderLayout.CENTER);

        add(hautPanel, BorderLayout.NORTH);

        //tableau des reductions des attractions
        String[] columns = {"ID", "Nom", "Pourcentage", "Description", "Attractions Concernées"};
        model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");

        buttonPanel.add(ajouterButton);
        buttonPanel.add(modifierButton);
        buttonPanel.add(supprimerButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JButton getAjouterButton() { return ajouterButton; }
    public JButton getModifierButton() { return modifierButton; }
    public JButton getSupprimerButton() { return supprimerButton; }
    public JButton getCompteButton() { return compteButton; }
    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
}
