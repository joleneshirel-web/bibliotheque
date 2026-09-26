public class Utilisateur {
    private int id;
    private String nom;

    public Utilisateur(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void afficherInformations() {
        System.out.println("ID : " + id + " | Nom : " + nom);
    }

    @Override
    public String toString() {
        return nom + " (ID: " + id + ")";
    }
}
