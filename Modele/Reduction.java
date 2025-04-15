package Modele;

public class Reduction {
    private int id_reduction;
    private String nom;
    private double pourcentage;
    private String description;

    public Reduction(int id_reduction, String nom, double pourcentage, String description){
        this.id_reduction=id_reduction;
        this.nom = nom;
        this.pourcentage=pourcentage;
        this.description=description;
    }

    public int getId_reduction(){return id_reduction;}
    public String getNom(){return nom;}
    public double getPourcentage(){return pourcentage;}
    public String getDescription(){return description;}

}
