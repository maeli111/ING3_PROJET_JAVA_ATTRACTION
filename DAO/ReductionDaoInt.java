package DAO;

import Modele.Reduction;

import java.util.ArrayList;

public interface ReductionDaoInt {
    public ArrayList<Reduction> getAll();

    public int getPourcentagePremiereVisite(int idClient);
    public int getPourcentageFidelite(int idClient);
    public void ajouter(Reduction reduction);
    public void modifier(int ancienId, Reduction reduction);
    public void supprimer(int idReduction);
}
