package Modele;

public class Reduction_attraction extends Reduction{
    private int id_attraction;

    public Reduction_attraction(int id_attraction, int id_reduction){
        super(id_reduction,"",0,"");
        this.id_attraction = id_attraction;
    }

    public int getId_attraction(){return id_attraction;}

    public String getNom() {
        return super.getNom();
    }

    public int getPourcentage() {
        return super.getPourcentage();
    }

    public String getDescription() {
        return super.getDescription();
    }
}
