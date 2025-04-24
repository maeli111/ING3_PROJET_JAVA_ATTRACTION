package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Modele.Admin;

public class VueAdmin extends JFrame {
    private JButton accueil = new JButton("Accueil");
    private JButton informations = new JButton("Informations");
    private JButton calendrier = new JButton("Calendrier");
    private JButton compte = new JButton("Compte");

    public VueAdmin(Admin admin) {
        setTitle("Réduction Administrateur");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ----------- HEADER avec les boutons de navigation -----------

        JPanel header = new JPanel(new BorderLayout());

        JPanel Pnavigation = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        Pnavigation.add(accueil);
        Pnavigation.add(informations);
        Pnavigation.add(calendrier);

        JPanel Pcompte = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        Pcompte.add(compte);

        header.add(Pnavigation, BorderLayout.WEST);
        header.add(Pcompte, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ----------- LISTENERS des boutons de navigation -----------

        accueil.addActionListener(e -> {
            VueAccueil vueAccueil = new VueAccueil();
            vueAccueil.setVisible(true);
            dispose();
        });

        informations.addActionListener(e -> {
            VuePlusInfos vuePlusInfos = new VuePlusInfos();
            vuePlusInfos.setVisible(true);
            dispose();
        });

        calendrier.addActionListener(e -> {
            VueCalendrier vueCalendrier = new VueCalendrier();
            vueCalendrier.setVisible(true);
            dispose();
        });


        // ----------- PANEL PRINCIPAL -----------

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informations personnelles"));

        infoPanel.add(new JLabel("Nom:"));
        JTextField nomField = new JTextField(admin.getNom());
        nomField.setEditable(false);
        infoPanel.add(nomField);

        infoPanel.add(new JLabel("Prénom:"));
        JTextField prenomField = new JTextField(admin.getPrenom());
        prenomField.setEditable(false);
        infoPanel.add(prenomField);

        infoPanel.add(new JLabel("E-mail:"));
        JTextField emailField = new JTextField(admin.getEmail());
        emailField.setEditable(false);
        infoPanel.add(emailField);

        // ----------- BOUTONS DE GESTION -----------

        JLabel modificationsLabel = new JLabel("Modifications:", SwingConstants.LEFT);
        modificationsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 10));

        JPanel managementPanel = new JPanel(new GridLayout(4, 1, 5, 5));

        JButton attractionsButton = new JButton("Attractions");
        JButton reductionsButton = new JButton("Réductions");
        JButton dossiersClientsButton = new JButton("Dossiers clients");
        JButton attractionDuMoisButton = new JButton("Attraction du mois");

        JButton[] btns = {attractionsButton, reductionsButton, dossiersClientsButton, attractionDuMoisButton};
        for (JButton btn : btns) {
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setContentAreaFilled(false);
            btn.setForeground(Color.BLUE);
            btn.setFont(btn.getFont().deriveFont(Font.ITALIC));
            managementPanel.add(btn);
        }

        attractionsButton.addActionListener(e -> {
            dispose();
            VueAdminAttraction vueAdminAttraction = new VueAdminAttraction(admin);
            vueAdminAttraction.setVisible(true);
        });

        reductionsButton.addActionListener(e -> {
            String[] options = {"Réductions clients", "Réductions attractions"};
            int choix = JOptionPane.showOptionDialog(
                    null,
                    "Souhaitez-vous modifier les réductions pour les clients ou les attractions ?",
                    "Choix du type de réductions",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            dispose();

            if (choix == 0) {
                VueAdminRC vueClient = new VueAdminRC(admin);
                vueClient.setVisible(true);
            } else if (choix == 1) {
                VueAdminRA vueAttraction = new VueAdminRA(admin);
                vueAttraction.setVisible(true);
            }
        });

        dossiersClientsButton.addActionListener(e -> {
            dispose();
            VueAdminClient vueAdminClient = new VueAdminClient(admin);
            vueAdminClient.setVisible(true);
        });

        // Pas d’action définie pour "Attraction du mois" ici, tu peux l'ajouter si besoin

        JPanel leftPanelWithLabel = new JPanel(new BorderLayout());
        leftPanelWithLabel.add(modificationsLabel, BorderLayout.NORTH);
        leftPanelWithLabel.add(managementPanel, BorderLayout.CENTER);

        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(leftPanelWithLabel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }
}
