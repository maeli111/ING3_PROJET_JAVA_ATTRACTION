package Modele;

public class Admin {
    private int id_admin;
    private int id_utilisateur;

    public Admin(int id_admin,int id_utilisateur){
        this.id_admin=id_admin;
        this.id_utilisateur=id_utilisateur;
    }

    public int getId_admin(){return id_admin;}
    public int getId_utilisateur(){return id_utilisateur;}
}
