package Vue;

import javax.swing.*;
import java.awt.*;

public class Admin extends JFrame {
    public Admin() {
        setTitle("Administrator");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Added border layout with gaps

        // Top button panel
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(new JButton("Accueil"));
        leftPanel.add(new JButton("Informations"));
        leftPanel.add(new JButton("Calendrier"));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(new JButton("Compte"));

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // Main content panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // Border layout with gaps

        // Personal information panel
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // 3 rows, 2 columns
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informations personnelles"));
        infoPanel.add(new JLabel("Nom:"));
        infoPanel.add(new JTextField());
        infoPanel.add(new JLabel("Prénom:"));
        infoPanel.add(new JTextField());
        infoPanel.add(new JLabel("E-mail:"));
        infoPanel.add(new JTextField());

        // Modifications label
        JLabel modificationsLabel = new JLabel("Modifications:", SwingConstants.LEFT);
        modificationsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 10)); // Add some space to the right

        // Management panels
        JPanel managementPanel = new JPanel(new GridLayout(4, 1, 5, 5)); // 4 rows with reduced gap

        JButton attractionsButton = new JButton("Attractions");
        attractionsButton.setFocusPainted(false);
        attractionsButton.setBorderPainted(false);
        attractionsButton.setContentAreaFilled(false);
        attractionsButton.setForeground(Color.BLUE);
        attractionsButton.setFont(attractionsButton.getFont().deriveFont(Font.ITALIC));

        JButton reductionsButton = new JButton("Réductions");
        reductionsButton.setFocusPainted(false);
        reductionsButton.setBorderPainted(false);
        reductionsButton.setContentAreaFilled(false);
        reductionsButton.setForeground(Color.BLUE);
        reductionsButton.setFont(reductionsButton.getFont().deriveFont(Font.ITALIC));

        JButton dossiersClientsButton = new JButton("Dossiers clients");
        dossiersClientsButton.setFocusPainted(false);
        dossiersClientsButton.setBorderPainted(false);
        dossiersClientsButton.setContentAreaFilled(false);
        dossiersClientsButton.setForeground(Color.BLUE);
        dossiersClientsButton.setFont(dossiersClientsButton.getFont().deriveFont(Font.ITALIC));

        JButton attractionDuMoisButton = new JButton("Attraction du mois");
        attractionDuMoisButton.setFocusPainted(false);
        attractionDuMoisButton.setBorderPainted(false);
        attractionDuMoisButton.setContentAreaFilled(false);
        attractionDuMoisButton.setForeground(Color.BLUE);
        attractionDuMoisButton.setFont(attractionDuMoisButton.getFont().deriveFont(Font.ITALIC));

        managementPanel.add(attractionsButton);
        managementPanel.add(reductionsButton);
        managementPanel.add(dossiersClientsButton);
        managementPanel.add(attractionDuMoisButton);

        // Add panels to main panel
        JPanel leftPanelWithLabel = new JPanel(new BorderLayout());
        leftPanelWithLabel.add(modificationsLabel, BorderLayout.NORTH);
        leftPanelWithLabel.add(managementPanel, BorderLayout.CENTER);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanelWithLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Admin admin = new Admin();
            admin.setVisible(true);
        });
    }
}