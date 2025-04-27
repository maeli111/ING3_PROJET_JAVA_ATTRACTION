package DAO;

import Modele.Reservation;

import java.util.ArrayList;

public interface ReservationDaoInt {
    // C'est une méthode pour récupérer toutes les attractions dans la base de données
    public ArrayList<Reservation> getAll();

    // méthode qui ajoute une réservation à la bdd
    public void ajouter(Reservation reservation);

    // méthode qui récupère le nom de l'attraction en fonction de l'ID de l'attraction
    public String getNomAttraction(int id_attraction);

    // méthode qui archive une réservation déjà passée
    public void archiverReservationsPassées();
}
