public abstract class Document implements Pretable, Cloneable {

    private final int numero;
    private final String titre;
    private final String auteur;

    private boolean disponible;
    private Utilisateur emprunteur;

    public Document(int numero, String titre, String auteur) {

        if (numero <= 0) {
            throw new IllegalArgumentException(
                "Le numero du document doit etre positif."
            );
        }

        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Le titre ne doit pas etre vide."
            );
        }

        if (auteur == null || auteur.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "L'auteur ne doit pas etre vide."
            );
        }

        this.numero = numero;
        this.titre = titre.trim();
        this.auteur = auteur.trim();
        this.disponible = true;
        this.emprunteur = null;
    }

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
    public boolean retourner() {

        if (disponible) {
            return false;
        }

        disponible = true;
        emprunteur = null;

        return true;
    }

    @Override
    public boolean estEmprunte() {
        return !disponible;
    }

    @Override
    public abstract int dureeMaxPret();

    @Override
    public Document clone() throws CloneNotSupportedException {
        return (Document) super.clone();
    }

    @Override
    public String toString() {

        String informations =
            "Document [N°=" + numero
            + ", Titre=" + titre
            + ", Auteur=" + auteur
            + ", Disponible=" + disponible;

        if (emprunteur != null) {
            informations += ", Emprunte par=" + emprunteur.getNom();
        }

        return informations + "]";
    }
}

