public class utilisateur {

    // Attributs
    private int id;
    private String nom;

    // Constructeur
    public utilisateur(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    // Méthode pour afficher les informations
    public void afficherInformations() {
        System.out.println("ID : " + id);
        System.out.println("Nom : " + nom);
    }

    // Getter pour récupérer le nom
    public String getNom() {
        return nom;
    }
}