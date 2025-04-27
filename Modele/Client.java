package Modele;

public class Client extends Utilisateur {
    //Attributs
    private int id_client;
    private int age;
    private String type_client;
    private String type_membre;

    //Constructeur1
    public Client(int id_client, int id_utilisateur, String email, String nom, String prenom, String mdp, int age, String type_client, String type_membre) {
        super(id_utilisateur, email, nom, prenom, mdp);
        this.id_client = id_client;
        this.age = age;
        this.type_client =type_client;
        this.type_membre = type_membre;
    }

    //Constructeur2
    public Client(int id_client, int id_utilisateur, int age, String type_client, String type_membre) {
        super(id_utilisateur, "", "", "", "");
        this.id_client = id_client;
        this.age = age;
        this.type_client = type_client;
        this.type_membre = type_membre;
    }

    //Constructeur3
    public Client() {
        super(0, "", "", "", "");
    }

    //Constructeur4
    public Client(String nom, String prenom, String email, String mdp, int age, String type_client) {
        super(0, email, nom, prenom, mdp);
        this.age = age;
        this.type_client = type_client;
        this.type_membre = "nouveau";
    }




    // Getters
    public int getid_client() { return id_client; }
    public int getage() { return age; }
    public String getType_client() { return type_client; }
    public String getType_membre() { return type_membre; }

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

    // Setter
    public void setid_client(int id_client) {
        this.id_client = id_client;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setType_client (String type_client) {
        this.type_client = type_client;
    }

    public void setType_membre(String typeMembre) {
        this.type_membre = typeMembre;
    }



}
