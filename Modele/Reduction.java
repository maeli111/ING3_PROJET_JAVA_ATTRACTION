package Modele;

public class Reduction {
    // attributs
    private int id_reduction;
    private String nom;
    private int pourcentage;
    private String description;

    // constructeurs

    // complet
    public Reduction(int id_reduction, String nom, int pourcentage, String description){
        this.id_reduction=id_reduction;
        this.nom = nom;
        this.pourcentage=pourcentage;
        this.description=description;
    }

    // sans id
    public Reduction(String nom, int pourcentage, String description) {
        this.nom = nom;
        this.pourcentage = pourcentage;
        this.description = description;
    }

    // les getters
    public int getId_reduction(){return id_reduction;}
    public String getNom(){return nom;}
    public int getPourcentage(){return pourcentage;}
    public String getDescription(){return description;}

    // setter
    public void setId_reduction(int id_reduction){this.id_reduction = id_reduction;}

}
