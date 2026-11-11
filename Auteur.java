/**
 * Auteur d'un livre.
 * Un objet Auteur est cree et gere par son Livre (composition).
 */
public final class Auteur {
    private final String nom;

    public Auteur(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'auteur ne doit pas etre vide.");
        }
        this.nom = nom.trim();
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}
