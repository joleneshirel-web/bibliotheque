
public class Utilisateur {

    private final int id;
    private final String nom;
    
    public Utilisateur(int id, String nom) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "L'identifiant doit être strictement positif."
            );
        }

        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Le nom ne doit pas être vide."
            );
        }

        this.id = id;
        this.nom = nom.trim();
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    /**
     * Affiche les informations de l'utilisateur.
     */
    public void afficherInformations() {
        System.out.println("ID : " + id + " | Nom : " + nom);
    }

    @Override
    public String toString() {
        return nom + " (ID : " + id + ")";
    }
}

