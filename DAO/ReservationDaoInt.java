package DAO;

import Modele.Reservation;

import java.util.ArrayList;

public interface ReservationDaoInt {
    public ArrayList<Reservation> getAll();

    public void ajouter(Reservation reservation);

    public String getNomAttraction(int id_attraction);

    public void archiverReservationsPassées();
}
