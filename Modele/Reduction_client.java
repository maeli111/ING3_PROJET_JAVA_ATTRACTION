package Modele;

public class Reduction_client {
    private int id_client;
    private int id_reduction;

    public Reduction_client(int id_client, int id_reduction){
        this.id_client = id_client;
        this.id_reduction = id_reduction;
    }

    public int getId_attraction(){return id_client;}
    public int getId_reduction(){return id_reduction;}
}
