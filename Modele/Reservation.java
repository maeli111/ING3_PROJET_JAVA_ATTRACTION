package Modele;
import java.time.LocalDate;

public class Reservation {
    private int id_reservation;
    private int id_client;
    private String nom;
    private String prenom;
    private String mail;
    private LocalDate date_reservation;
    private LocalDate date_achat;
    private int id_attraction;
    private double prix_total;
    private int nb_personne;
    private int est_archivee;

    public Reservation(int id_reservation,int id_client, String nom, String prenom,String mail, LocalDate date_reservation, LocalDate date_achat, int id_attraction, double prix_total, int nb_personne, int est_archivee){
        this.id_reservation=id_reservation;
        this.id_client=id_client;
        this.nom=nom;
        this.prenom=prenom;
        this.mail=mail;
        this.date_achat=date_achat;
        this.date_reservation=date_reservation;
        this.id_attraction=id_attraction;
        this.prix_total=prix_total;
        this.nb_personne=nb_personne;
        this.est_archivee=est_archivee;
    }

    public Reservation(LocalDate date_reservation, int id_attraction){
        this.date_reservation=date_reservation;
        this.id_attraction=id_attraction;
    }

    public int getId_reservation(){return id_reservation;}
    public int getId_client(){return id_client;}
    public String getNom(){return nom;}
    public String getPrenom(){return prenom;}
    public String getMail(){return mail;}
    public LocalDate getDate_reservation(){return date_reservation;}
    public LocalDate getDate_achat(){return date_achat;}
    public int getId_attraction(){return id_attraction;}
    public double getPrix_total(){return prix_total;}
    public int getNb_personne(){return nb_personne;}
    public int getEst_archivee(){return est_archivee;}


}
