package Vue;

import javax.swing.*;

public class Accueil extends JFrame {
    public Accueil() {
        setTitle("Bienvenue invité");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Bienvenue sur la plateforme !", SwingConstants.CENTER);
        add(label);
    }
}
