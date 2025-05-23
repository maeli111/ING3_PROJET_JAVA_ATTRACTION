package Modele;

public abstract class Utilisateur {
    //Attributs
    private int id_utilisateur;
    private String email;
    private String nom;
    private String prenom;
    private String mdp;

    //Constructeur
    public Utilisateur(int id_utilisateur, String email, String nom, String prenom, String mdp) {
        this.id_utilisateur = id_utilisateur;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
    }

    //Getter et setter
    public int getId_utilisateur(){return id_utilisateur;}
    public String getEmail(){return email;}
    public String getNom(){return nom;}
    public String getPrenom(){return prenom;}
    public String getMdp(){return mdp;}
    public void setid_utilisateur(int id_utilisateur) {
        this.id_utilisateur= id_utilisateur;
    }
    public void setEmail(String email) { this.email = email; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setMdp(String mdp) { this.mdp = mdp; }

}
