public class Livre extends Document {
    private int nombrePages;

    public Livre(int numero, String titre, String auteur, int nombrePages) {
        super(numero, titre, auteur);
        this.nombrePages = nombrePages;
    }

    public int getNombrePages() {
        return nombrePages;
    }

    @Override
    public int dureeMaxPret() {
        return 21; // 21 jours pour un livre
    }

    @Override
    public String toString() {
        return "Livre [" + super.toString() + ", Pages=" + nombrePages + ", Durée prêt=" + dureeMaxPret() + " jours]";
    }
}
