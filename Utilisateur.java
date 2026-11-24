public class Utilisateur {
    private final int id;
    private final String nom;
    private final String email;

    public Utilisateur(int id, String nom, String email) {
        if (id <= 0) {
            throw new IllegalArgumentException("L'identifiant doit etre positif.");
        }
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom ne doit pas etre vide.");
        }
        if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("L'adresse email est invalide.");
        }
        this.id = id;
        this.nom = nom.trim();
        this.email = email.trim();
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getEmail() { return email; }

    public void afficherInformations() {
        System.out.println("ID : " + id + " | Nom : " + nom + " | Email : " + email);
    }

    @Override
    public boolean equals(Object objet) {
        if (this == objet) {
            return true;
        }
        if (!(objet instanceof Utilisateur)) {
            return false;
        }
        Utilisateur autre = (Utilisateur) objet;
        return id == autre.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return nom + " (ID : " + id + ")";
    }
}
