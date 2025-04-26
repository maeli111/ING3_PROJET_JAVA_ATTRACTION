package DAO;

import Modele.Attraction;
import Modele.Reduction;
import Modele.Reservation;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReductionDaoInt {
    public ArrayList<Reduction> getAll();

    public int getPourcentagePremiereVisite(int idClient);

    public int getPourcentageFidelite(int idClient);

    public int getPourcentageAttraction(int idAttraction);

    public void ajouter(Reduction reduction);

    public void modifier(int ancienId, Reduction reduction);

    public void supprimer(int idReduction);

    public ArrayList<Reduction> getReductionsSansAttraction();

    public ArrayList<Reduction> getReductionsAvecAttraction();

    public void supprimerLiaisonsAttractions(int idReduction);

    public ArrayList<Attraction> getAttractionsLiees(int idReduction);

    public ArrayList<Attraction> getAttractionsNonLiees(int idReduction);

    public void lierReductionAttraction(int idReduction, int idAttraction);

    public boolean isEmpty(int id_reduction);

    public double getPourcentageById(int idReduction);

    public int NbResaClient(int idClient) throws SQLException;

    public String getDescriptionById(int id);
}
