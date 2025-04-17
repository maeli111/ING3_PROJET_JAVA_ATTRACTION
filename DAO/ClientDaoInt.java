package DAO;

import Modele.Client;

import java.util.ArrayList;

public interface ClientDaoInt {
    /**
     * Récupérer de la base de données tous les objets des clients dans une liste
     * @return : liste retournée des objets des clients récupérés
     */
    public ArrayList<Client> getAll();
    public void inscrire(Client client);
    public void supprimer(int id_client);
}
