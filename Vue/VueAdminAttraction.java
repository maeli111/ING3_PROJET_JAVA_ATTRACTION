package Vue;

import Modele.Admin;
import Modele.Attraction;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VueAdminAttraction extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton accueilButton;
    private JButton informationsButton;
    private JButton calendrierButton;
    private JButton compteButton;
    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;

    public VueAdminAttraction(Admin admin) {
        setTitle("Attractions - Admin");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- TOP PANEL ---
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonBar = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        accueilButton = new JButton("Accueil");
        informationsButton = new JButton("Informations");
        calendrierButton = new JButton("Calendrier");
        leftPanel.add(accueilButton);
        leftPanel.add(informationsButton);
        leftPanel.add(calendrierButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        compteButton = new JButton("Compte");
        rightPanel.add(compteButton);

        buttonBar.add(leftPanel, BorderLayout.WEST);
        buttonBar.add(rightPanel, BorderLayout.EAST);

        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titreLabel = new JLabel("PalasiLand - Gestion des attractions");
        titreLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titrePanel.add(titreLabel);

        topPanel.add(buttonBar, BorderLayout.NORTH);
        topPanel.add(titrePanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // --- TABLE ---
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Description", "Prix", "Capacité", "Type"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // --- BOTTOM PANEL ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");
        bottomPanel.add(ajouterButton);
        bottomPanel.add(modifierButton);
        bottomPanel.add(supprimerButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Getters pour les boutons et la table
    public JButton getAccueilButton() {
        return accueilButton;
    }

    public JButton getInformationsButton() {
        return informationsButton;
    }

    public JButton getCalendrierButton() {
        return calendrierButton;
    }

    public JButton getCompteButton() {
        return compteButton;
    }

    public JButton getAjouterButton() {
        return ajouterButton;
    }

    public JButton getModifierButton() {
        return modifierButton;
    }

    public JButton getSupprimerButton() {
        return supprimerButton;
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
