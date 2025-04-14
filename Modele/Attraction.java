package Modele;

public class Attraction {
    private int id_attraction;
    private String nom;
    private String description;
    private double prix;
    private int capacite;
    private String type_attraction;
    private int nb_reservation;

    public Attraction(int id_attraction,String nom, String description, double prix, int capacite, String type_attraction, int nb_reservation){
        this.id_attraction=id_attraction;
        this.nom=nom;
        this.description=description;
        this.prix=prix;
        this.capacite=capacite;
        this.type_attraction=type_attraction;
        this.nb_reservation=nb_reservation;
    }

    public int getId_attraction(){return id_attraction;}
    public String getNom(){return nom;}
    public String getDescription(){return description;}
    public double getPrix(){return prix;}
    public int getCapacite(){return capacite;}
    public String getType_attraction(){return type_attraction;}
    public int getNb_reservation(){return nb_reservation;}
}
