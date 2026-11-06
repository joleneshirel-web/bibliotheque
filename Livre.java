/**
 * Un livre est un document.
 * Ses auteurs sont des objets possedes par le livre (composition).
 * Un tableau simple est utilise a ce jalon ; les collections Java ne sont
 * pas introduites ici car elles relèvent du jalon suivant.
 */
public class Livre extends Document {
    private final int nombrePages;
    private Auteur[] auteurs;

    /**
     * Les noms sont fournis au constructeur ; le Livre cree lui-meme
     * les objets Auteur qui composent son etat interne.
     */
    public Livre(int numero, String titre, int nombrePages, String... nomsAuteurs) {
        super(numero, titre);

        if (nombrePages <= 0) {
            throw new IllegalArgumentException("Le nombre de pages doit etre strictement positif.");
        }
        if (nomsAuteurs == null || nomsAuteurs.length == 0) {
            throw new IllegalArgumentException("Un livre doit avoir au moins un auteur.");
        }

        this.nombrePages = nombrePages;
        this.auteurs = new Auteur[nomsAuteurs.length];
        for (int i = 0; i < nomsAuteurs.length; i++) {
            this.auteurs[i] = new Auteur(nomsAuteurs[i]);
        }
    }

    public int getNombrePages() {
        return nombrePages;
    }

    /** Renvoie une copie du tableau pour proteger le tableau interne. */
    public Auteur[] getAuteurs() {
        return auteurs.clone();
    }

    @Override
    public int dureePret() {
        return 21;
    }

    @Override
    public String toString() {
        StringBuilder noms = new StringBuilder();
        for (int i = 0; i < auteurs.length; i++) {
            if (i > 0) {
                noms.append(", ");
            }
            noms.append(auteurs[i].getNom());
        }
        return "Livre [" + informationsCommunes()
                + ", pages=" + nombrePages
                + ", auteurs=[" + noms + "]"
                + ", dureePret=" + dureePret() + " jours]";
    }

    /**
     * Le clonage conserve le comportement du Jalon 1 et copie aussi le
     * tableau et les objets Auteur pour que les deux livres aient leur
     * propre composition.
     */
    @Override
    public Livre clone() throws CloneNotSupportedException {
        Livre copie = (Livre) super.clone();
        Auteur[] auteursCopies = new Auteur[auteurs.length];
        for (int i = 0; i < auteurs.length; i++) {
            auteursCopies[i] = new Auteur(auteurs[i].getNom());
        }
        copie.auteurs = auteursCopies;
        return copie;
    }
}
