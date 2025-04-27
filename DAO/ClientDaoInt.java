package DAO;

import Modele.Client;
import Modele.Reservation;

import java.util.ArrayList;

public interface ClientDaoInt {
    /// Récupérer de la base de données tous les objets des clients dans une liste
    public ArrayList<Client> getAll();

    /// Cette méthode inscrit un nv client en 2 étapes : insertion dans utilisateur, puis dans client
    public void inscrire(Client client);

    /// Supprime un client et son utilisateur associé dans la bdd
    public void supprimer(int id_client);

    /// Cette méthode permet à un client de se connecter en vérifiant l'email et le mdp
    public Client seConnecter(String email, String mdp);

    /// Cette méthode vérifie si un mail est déjà utilisé dans la bdd
    public boolean emailExiste(String email);

    /// Cette méthode récupère le mail d’un client avec son ID
    public String getEmailByIdClient(int idClient);

    /// Cette méthode ajoute un nv client à la bdd
    public void ajouter(Client client);

    /// Cette méthode modifie les infos d’un client dans les tables utilisateur et client
    public void modifier(Client client);

    /// Cette méthode récupère un client spécifique à partir de son ID
    public Client getById(int id_client);

    /// Cette méthode trouve le client grace à son mail qui est unique dans la bdd
    public Client getByEmail(String email);

    /// Cette méthode récupére les réservations en cours du client
    public ArrayList<Reservation> getReservationsEnCours(Client client);

    /// Cette méthode récupère la liste des réservations archivées d'un client
    public ArrayList<Reservation> getReservationsArchivees(Client client);
}
