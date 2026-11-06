/**
 * Type abstrait commun aux documents de la bibliotheque.
 * Les sous-types definissent leur propre dureePret() et toString().
 */
public abstract class Document implements Pretable, Cloneable {
    private final int numero;
    private final String titre;
    private boolean disponible;
    private Utilisateur emprunteur;

    protected Document(int numero, String titre) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Le numero du document doit etre positif.");
        }
        if (titre == null || titre.trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne doit pas etre vide.");
        }
        this.numero = numero;
        this.titre = titre.trim();
        this.disponible = true;
        this.emprunteur = null;
    }

    public int getNumero() { return numero; }
    public String getTitre() { return titre; }
    public boolean isDisponible() { return disponible; }

    @Override
    public Utilisateur getEmprunteur() { return emprunteur; }

    /**
     * Si l'emprunt reussit, le document devient indisponible et
     * l'utilisateur devient son emprunteur.
     * Un utilisateur null ou un document deja emprunte est refuse.
     */
    @Override
    public boolean emprunter(Utilisateur utilisateur) {
        if (!disponible || utilisateur == null) {
            return false;
        }
        disponible = false;
        emprunteur = utilisateur;
        return true;
    }

    /**
     * Si le retour reussit, le document redevient disponible
     * et son emprunteur est efface.
     */
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

    /** Duree maximale du pret, exprimee en jours. */
    @Override
    public abstract int dureePret();

    @Override
    public Document clone() throws CloneNotSupportedException {
        return (Document) super.clone();
    }

    protected String informationsCommunes() {
        String texte = "N°=" + numero + ", titre='" + titre
                + "', disponible=" + disponible;
        if (emprunteur != null) {
            texte += ", emprunteur='" + emprunteur.getNom() + "'";
        }
        return texte;
    }
}
