package Modele;

public class Reduction_client extends Reduction{
    private int id_client;

    public Reduction_client(int id_client, int id_reduction){
        super(id_reduction,"",0.0,"");
        this.id_client = id_client;
    }

    public int getId_attraction(){return id_client;}

    public String getNom() {
        return super.getNom();
    }

    public double getPourcentage() {
        return super.getPourcentage();
    }

    public String getDescription() {
        return super.getDescription();
    }
}
