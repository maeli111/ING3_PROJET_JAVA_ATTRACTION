package Vue;

import Modele.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import Modele.Attraction;

public class VueAdminRA extends JFrame {
    private JButton ajouterButton;
    private JButton modifierButton;
    private JButton supprimerButton;
    private JButton compteButton;
    private JTable table;
    private DefaultTableModel tableModel;

    public VueAdminRA(Admin admin) {
        // Titre de la fenêtre
        setTitle("Gestion des Attractions");

        // Initialisation des composants
        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");
        compteButton = new JButton("Compte");

        // Initialisation du tableau et du modèle de données
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        // Ajouter les colonnes au modèle du tableau
        tableModel.addColumn("ID");
        tableModel.addColumn("Nom");
        tableModel.addColumn("Description");
        tableModel.addColumn("Prix");
        tableModel.addColumn("Capacité");
        tableModel.addColumn("Type");

        // Initialisation de la disposition (layout) de la fenêtre
        setLayout(new BorderLayout());

        // Panel principal pour contenir le titre et le bouton "Compte"
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Vertical layout pour empiler les composants

        // Panel pour le bouton "Compte" (en haut)
        JPanel panelCompte = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Utilisation de FlowLayout pour le bouton
        panelCompte.add(compteButton);

        // Panel pour le titre centré sous le bouton "Compte"
        JPanel panelTitle = new JPanel();
        JLabel titleLabel = new JLabel("PalasiLand", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Met le titre en gras et de taille 24
        panelTitle.add(titleLabel);

        // Ajouter le bouton "Compte" et le titre au topPanel
        topPanel.add(panelCompte); // Bouton "Compte" en haut à droite
        topPanel.add(panelTitle);  // Titre centré en dessous du bouton "Compte"

        // Panel pour les autres boutons (en bas)
        JPanel panelButtons = new JPanel();
        panelButtons.add(ajouterButton);
        panelButtons.add(modifierButton);
        panelButtons.add(supprimerButton);

        // Ajouter les composants à la fenêtre
        add(topPanel, BorderLayout.NORTH); // Le panel avec titre et bouton "Compte"
        add(new JScrollPane(table), BorderLayout.CENTER); // Tableau des attractions
        add(panelButtons, BorderLayout.SOUTH); // Les autres boutons en bas

        // Taille de la fenêtre
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre
    }

    /**
     * Cette méthode est utilisée pour charger les attractions dans le tableau.
     * @param attractions : la liste des attractions à afficher dans la vue.
     */
    public void chargerAttractions(ArrayList<Attraction> attractions) {
        // Vider le tableau existant
        tableModel.setRowCount(0);

        // Ajouter chaque attraction à la table
        for (Attraction attraction : attractions) {
            Object[] rowData = {
                    attraction.getId_attraction(),
                    attraction.getNom(),
                    attraction.getDescription(),
                    attraction.getPrix(),
                    attraction.getCapacite(),
                    attraction.getType_attraction()
            };
            tableModel.addRow(rowData);
        }
    }

    // Getters pour les boutons afin que le contrôleur puisse y accéder
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

    public JButton getCompteButton() {
        return compteButton;
    }
}
