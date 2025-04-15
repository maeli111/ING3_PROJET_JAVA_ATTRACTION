package Modele;

public abstract class Utilisateur {
    private int id_utilisateur;
    private String email;
    private String nom;
    private String prenom;
    private String mdp;

    public Utilisateur(int id_utilisateur, String email, String nom, String prenom, String mdp) {
        this.id_utilisateur = id_utilisateur;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
    }

    public int getId_utilisateur(){return id_utilisateur;}
    public String getEmail(){return email;}
    public String getNom(){return nom;}
    public String getPrenom(){return prenom;}
    public String getMdp(){return mdp;}
    public void setid_utilisateur(int id_utilisateur) {
        this.id_utilisateur= id_utilisateur;
    }
}
