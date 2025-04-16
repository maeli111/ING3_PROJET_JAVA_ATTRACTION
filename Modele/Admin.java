package Modele;

public class Admin extends Utilisateur{
    private int id_admin;

    public Admin(int id_admin, int id_utilisateur){
        super(id_utilisateur, "", "", "", "");
        this.id_admin=id_admin;
    }

    public int getId_admin(){return id_admin;}

    public int getid_utilisateur() {
        return super.getId_utilisateur();
    }

    public String getEmail() {
        return super.getEmail();
    }
    public String getNom(){
        return super.getNom();
    }

    public String getPrenom(){
        return super.getPrenom();
    }

    public String getMdp(){
        return super.getMdp();
    }

    // Setter pour l'id admin
    public void setid_admin(int id_admin) {
        this.id_admin = id_admin;
    }
}
