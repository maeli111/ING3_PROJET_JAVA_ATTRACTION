package Modele;

public class Attraction {
    // attributs
    private int id_attraction;
    private String nom;
    private String description;
    private double prix;
    private int capacite;
    private String type_attraction;
    private int mois;

    // constructeurs

    // sans mois
    public Attraction(int id_attraction,String nom, String description, double prix, int capacite, String type_attraction){
        this.id_attraction=id_attraction;
        this.nom=nom;
        this.description=description;
        this.prix=prix;
        this.capacite=capacite;
        this.type_attraction=type_attraction;
    }

    // sans id
    public Attraction(String nom, String description, double prix, int capacite, String type_attraction){
        this.nom=nom;
        this.description=description;
        this.prix=prix;
        this.capacite=capacite;
        this.type_attraction=type_attraction;
    }

    // juste id et nom
    public Attraction(int id_attraction, String nom){
        this.nom=nom;
        this.id_attraction=id_attraction;
    }

    // complet
    public Attraction(int id_attraction,String nom, String description, double prix, int capacite, String type_attraction, int mois){
        this.id_attraction=id_attraction;
        this.nom=nom;
        this.description=description;
        this.prix=prix;
        this.capacite=capacite;
        this.type_attraction=type_attraction;
        this.mois = mois;
    }


    // les getters
    public int getId_attraction(){return id_attraction;}
    public String getNom(){return nom;}
    public String getDescription(){return description;}
    public double getPrix(){return prix;}
    public int getCapacite(){return capacite;}
    public String getType_attraction(){return type_attraction;}
    public int getMois(){return mois;}

    // les setters
    public void setNom(String nom){this.nom = nom;}
    public void setDescription(String description){this.description = description;}
    public void setPrix(Double prix){this.prix = prix;}
    public void setCapacite(int capacite){this.capacite = capacite;}
    public void setType_attraction(String typeAttraction){this.type_attraction = typeAttraction;}

}
