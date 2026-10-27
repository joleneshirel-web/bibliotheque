
public class Livre extends Document {

    private final int nombrePages;

    public Livre(int numero, String titre, String auteur, int nombrePages) {

        super(numero, titre, auteur);

        if (nombrePages <= 0) {
            throw new IllegalArgumentException(
                "Le nombre de pages doit être strictement positif."
            );
        }

        this.nombrePages = nombrePages;
    }

    public int getNombrePages() {
        return nombrePages;
    }

    /**
     * Un livre peut être emprunté pendant 21 jours maximum.
     */
    @Override
    public int dureeMaxPret() {
        return 21;
    }

    @Override
    public String toString() {
        return "Livre [" + super.toString()
                + ", Pages=" + nombrePages
                + ", Durée prêt=" + dureeMaxPret() + " jours]";
    }
}
