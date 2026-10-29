
public class Utilisateur {

    private final int id;
    private final String nom;
    private final String email;

    public Utilisateur(int id, String nom, String email) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "L'identifiant doit etre positif."
            );
        }

        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Le nom ne doit pas etre vide."
            );
        }

        if (email == null
                || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException(
                "L'adresse email est invalide."
            );
        }

        this.id = id;
        this.nom = nom.trim();
        this.email = email.trim();
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public void afficherInformations() {
        System.out.println(
            "ID : " + id
            + " | Nom : " + nom
            + " | Email : " + email
        );
    }

    @Override
    public String toString() {
        return nom + " (ID : " + id + ")";
    }
}

