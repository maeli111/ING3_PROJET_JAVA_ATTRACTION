package Modele;

public class Client {
    private int id_client;
    private int id_utilisateur;
    private int age;
    private String type_client;
    private String type_membre;

    // constructeur
    public Client (int id_client, int id_utilisateur, int age,String type_client,String type_membre) {
        this.id_client = id_client;
        this.id_utilisateur = id_utilisateur;
        this.age = age;
        this.type_client =type_client;
        this.type_membre = type_membre;
    }

    // getters
    public int getid_client() { return id_client; }
    public int getid_utilisateur() { return id_utilisateur; }
    public int getage() { return age; }
    public String getType_client() {return type_client;}
    public String getType_membre(){return type_membre;}

}

