```java
public abstract class Document implements Empruntable, Cloneable {

    // Attributs d'identification
    private final int numero;
    private final String titre;
    private final String auteur;

    // État du document
    private boolean disponible;
    private Utilisateur emprunteur;

    public Document(int numero, String titre, String auteur) {

        // Vérification des invariants
        if (numero <= 0) {
            throw new IllegalArgumentException(
                "Le numéro du document doit être positif."
            );
        }

        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Le titre ne doit pas être vide."
            );
        }

        if (auteur == null || auteur.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "L'auteur ne doit pas être vide."
            );
        }

        this.numero = numero;
        this.titre = titre.trim();
        this.auteur = auteur.trim();

        // Un nouveau document est disponible
        this.disponible = true;
        this.emprunteur = null;
    }

    // Accesseurs
    public int getNumero() {
        return numero;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public boolean isDisponible() {
        return disponible;
    }

    @Override
    public Utilisateur getEmprunteur() {
        return emprunteur;
    }

    // Gestion des emprunts
    @Override
    public boolean emprunter(Utilisateur utilisateur) {

        if (!disponible || utilisateur == null) {
            return false;
        }

        this.disponible = false;
        this.emprunteur = utilisateur;

        return true;
    }

    @Override
    public void retourner() {

        if (!disponible) {
            this.disponible = true;
            this.emprunteur = null;
        }
    }

    @Override
    public boolean estEmprunte() {
        return !disponible;
    }

    // Chaque classe fille définit sa durée maximale de prêt
    @Override
    public abstract int dureeMaxPret();

    // Clonage
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // Représentation textuelle
    @Override
    public String toString() {

        String informations =
            "Document [N°=" + numero
            + ", Titre=" + titre
            + ", Auteur=" + auteur
            + ", Disponible=" + disponible;

        if (emprunteur != null) {
            informations +=
                ", Emprunté par=" + emprunteur.getNom();
        }

        informations += "]";

        return informations;
    }
}
```
