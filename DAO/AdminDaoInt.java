package DAO;

import Modele.Admin;

import java.util.ArrayList;

public interface AdminDaoInt {
    ///Cette méthode permet de récupérer tous les administrateurs dans la base de données
    public ArrayList<Admin> getAll();

    ///Pour se connecter en tant qu'admin
    public Admin connexionAdmin(String email, String mdp);
}
