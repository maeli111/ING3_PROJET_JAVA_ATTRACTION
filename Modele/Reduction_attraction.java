package Modele;

public class Reduction_attraction {
    private int id_attraction;
    private int id_reduction;

    public Reduction_attraction(int id_attraction, int id_reduction){
        this.id_attraction = id_attraction;
        this.id_reduction = id_reduction;
    }

    public int getId_attraction(){return id_attraction;}
    public int getId_reduction(){return id_reduction;}
}
