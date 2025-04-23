package DAO;

import Modele.Reduction;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReductionDaoInt {
    public ArrayList<Reduction> getAll();

    public int getPourcentagePremiereVisite(int idClient);
    public int getPourcentageFidelite(int idClient);
    public void ajouter(Reduction reduction);
    public void modifier(int ancienId, Reduction reduction);
    public void supprimer(int idReduction);
    public double getPourcentageById(int idReduction);
    public int NbResaClient(int idClient) throws SQLException;
}
