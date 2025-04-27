package Vue;

import Modele.*;

import javax.swing.*;
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

        JPanel HautPanel = new JPanel(new BorderLayout());

        JPanel buttonBar = new JPanel(new BorderLayout());
        JPanel gauchePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel droitePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        compte = new JButton("Compte");
        droitePanel.add(compte);

        buttonBar.add(gauchePanel, BorderLayout.WEST);
        buttonBar.add(droitePanel, BorderLayout.EAST);

        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titreLabel = new JLabel("PalasiLand");
        titreLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titrePanel.add(titreLabel);

        HautPanel.add(buttonBar, BorderLayout.NORTH);
        HautPanel.add(titrePanel, BorderLayout.CENTER);
        add(HautPanel, BorderLayout.NORTH);

        //tableau réducs liés aux clients
        String[] columns = {"ID", "Nom", "Pourcentage", "Description"};
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
        ajouter = new JButton("Ajouter");
        modifier = new JButton("Modifier");
        supprimer = new JButton("Supprimer");
        buttonPanel.add(ajouter);
        buttonPanel.add(modifier);
        buttonPanel.add(supprimer);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JButton getBtnAjouter() { return ajouter; }
    public JButton getBtnModifier() { return modifier; }
    public JButton getBtnSupprimer() { return supprimer; }
    public JButton getBtnCompte() { return compte; }

    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }

}
