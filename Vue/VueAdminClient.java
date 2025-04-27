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

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel buttonBar = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        compteButton = new JButton("Compte");
        rightPanel.add(compteButton);

        buttonBar.add(leftPanel, BorderLayout.WEST);
        buttonBar.add(rightPanel, BorderLayout.EAST);

        JPanel titrePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titreLabel = new JLabel("PalasiLand - Gestion des clients");
        titreLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titrePanel.add(titreLabel);

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
