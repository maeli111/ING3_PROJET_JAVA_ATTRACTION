package DAO;

import Modele.Attraction;
import java.time.*;
import java.util.ArrayList;

public interface AttractionDaoInt {
    public ArrayList<Attraction> getAll();

    Attraction chercher(int id);

    void ajouter(Attraction attraction);
    void supprimer(Attraction attraction);

    Attraction modifierNom(Attraction attraction);
    Attraction modifierDescription(Attraction attraction);
    Attraction modifierPrix(Attraction attraction);
    Attraction modifierCapacite(Attraction attraction);
    Attraction modifierTypeAttraction(Attraction attraction);
    Attraction modifierNbReservation(Attraction attraction);

    String afficherNom(int id);
    String afficherDescription(int id);
    Double afficherPrix(int id);
    int afficherCapacite(int id);
    String afficherTypeAttraction(int id);
    int afficherNbReservation(int id);

    public boolean estDisponible(LocalDate date, int idAttraction);
    public int getPlacesDisponibles(LocalDate date, int idAttraction);
}
