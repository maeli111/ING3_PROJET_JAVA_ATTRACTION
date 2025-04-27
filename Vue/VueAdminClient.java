package Vue;

import DAO.ClientDao;
import DAO.DaoFactory;
import Modele.Admin;
import Modele.Client;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VueAdminClient extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton compteButton, ajouterButton, modifierButton, supprimerButton;

    public VueAdminClient(Admin admin) {
        setTitle("Clients - Admin");
        setSize(1250, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        DaoFactory daoFactory = DaoFactory.getInstance("java_attraction", "root", "");
        ClientDao clientDao = new ClientDao(daoFactory);

        Color hoverColor = new Color(255, 182, 193);
        Color defaultColor = UIManager.getColor("Button.background");

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
        parc.setHorizontalAlignment(JTextField.CENTER);
        parc.setFont(new Font("Bodoni MT", Font.BOLD, 32));
        parc.setBorder(null);
        parc.setOpaque(false);
        titrePanel.add(parc);

        JPanel gestionPanel = new JPanel();
        JLabel gestionLabel = new JLabel("Gestion Réductions Clients");
        gestionLabel.setHorizontalAlignment(JTextField.CENTER);
        gestionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gestionLabel.setForeground(new Color(100, 100, 100));  // Gris foncé pour le texte
        gestionPanel.add(gestionLabel);
        topPanel.add(gestionPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        topPanel.add(buttonBar, BorderLayout.NORTH);
        topPanel.add(titrePanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        //tableau des clients
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Prénom", "Email", "Âge", "Mot de passe", "Type de client"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ajouterButton = new JButton("Ajouter");
        modifierButton = new JButton("Modifier");
        supprimerButton = new JButton("Supprimer");
        bottomPanel.add(ajouterButton);
        bottomPanel.add(modifierButton);
        bottomPanel.add(supprimerButton);
        add(bottomPanel, BorderLayout.SOUTH);

        Font boutonFont = new Font("Segoe UI", Font.BOLD, 16);
        ajouterButton.setFont(boutonFont);
        modifierButton.setFont(boutonFont);
        supprimerButton.setFont(boutonFont);

        applyHoverEffect(ajouterButton, hoverColor, defaultColor);
        applyHoverEffect(modifierButton, hoverColor, defaultColor);
        applyHoverEffect(supprimerButton, hoverColor, defaultColor);
        applyHoverEffect(compteButton, hoverColor, defaultColor);

        chargerClients(new ArrayList<>());
    }

    // Méthode pour charger les clients dans la table
    public void chargerClients(ArrayList<Client> clients) {
        tableModel.setRowCount(0); // Vider la table avant de la remplir
        for (Client c : clients) {
            tableModel.addRow(new Object[]{
                    c.getid_client(),
                    c.getNom(),
                    c.getPrenom(),
                    c.getEmail(),
                    c.getage(),
                    c.getMdp(),
                    c.getType_client()
            });
        }
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
