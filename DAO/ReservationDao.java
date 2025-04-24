package DAO;

import Modele.Reservation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import java.sql.*;

public class ReservationDao implements ReservationDaoInt{
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public ReservationDao(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * Récupérer de la base de données tous les objets des Reservations dans une liste
     * @return : liste retournée des objets des Reservations récupérés
     */
    public ArrayList<Reservation> getAll() {
        ArrayList<Reservation> listeReservation = new ArrayList<Reservation>();

        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            ResultSet resultats = statement.executeQuery("select * from reservation");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                int id_reservation = resultats.getInt(1);
                int id_client = resultats.getInt(2);
                String nom = resultats.getString(3);
                String prenom = resultats.getString(4);
                String mail = resultats.getString(5);
                LocalDate date_reservation = resultats.getDate(6).toLocalDate();
                LocalDate date_achat = resultats.getDate(7).toLocalDate();
                int id_attraction = resultats.getInt(8);
                double prix_total = resultats.getDouble(9);
                int nb_personne = resultats.getInt(10);
                int est_archivee = resultats.getInt(11);



                Reservation reservation = new Reservation(id_reservation,id_client,nom,prenom,mail,date_reservation,date_achat,id_attraction,prix_total,nb_personne,est_archivee);

                listeReservation.add(reservation);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste de Reservation impossible");
        }

        return listeReservation;
    }

    public void ajouter(Reservation reservation) {
        try (Connection connexion = daoFactory.getConnection();
             PreparedStatement ps = connexion.prepareStatement(
                     "INSERT INTO reservation (id_client, nom, prenom, mail, date_reservation, date_achat, id_attraction, prix_total, nb_personne, est_archivee) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            ps.setInt(1, reservation.getId_client());
            ps.setString(2, reservation.getNom());
            ps.setString(3, reservation.getPrenom());
            ps.setString(4, reservation.getMail());
            ps.setDate(5, java.sql.Date.valueOf(reservation.getDate_reservation()));
            ps.setDate(6, java.sql.Date.valueOf(reservation.getDate_achat()));
            ps.setInt(7, reservation.getId_attraction());
            ps.setDouble(8, reservation.getPrix_total());
            ps.setInt(9, reservation.getNb_personne());
            ps.setInt(10, reservation.getEst_archivee());

            ps.executeUpdate();

            ps.close();
            connexion.close();

            System.out.println("Réservation ajoutée avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de la réservation.");
        }
    }

    public int genererIdReservationUnique() {
        int id;
        boolean existe = true;

        try (Connection conn = daoFactory.getConnection()) {
            while (existe) {
                id = (int) (Math.random() * 900000) + 100000; // Génère entre 100000 et 999999

                String sql = "SELECT COUNT(*) FROM reservation WHERE id_reservation = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, id);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            existe = rs.getInt(1) > 0; // Si > 0, l'ID existe déjà
                        }
                    }
                }

                if (!existe) return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // erreur
    }
}
