package DAO;

import Modele.Utilisateur;

import java.util.ArrayList;

public interface UtilisateurDaoInt {
    //Cette méthode récupère de la bdd tous les objets des Utilisateurs dans une liste
    public ArrayList<Utilisateur> getAll();
}
