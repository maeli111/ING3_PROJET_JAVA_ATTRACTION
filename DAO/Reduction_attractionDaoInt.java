package DAO;

import Modele.Reduction_attraction;
import java.util.ArrayList;

public interface Reduction_attractionDaoInt {
    /// Cette méthode récupère de la bdd tous les objets des Reduction_attractions dans une liste
    public ArrayList<Reduction_attraction> getAll();
}
