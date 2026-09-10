public class Periodique extends Document {
    private int numeroParution;

    public Periodique(int numero, String titre, String auteur, int numeroParution) {
        super(numero, titre, auteur);
        this.numeroParution = numeroParution;
    }

    public int getNumeroParution() {
        return numeroParution;
    }

    @Override
    public int dureeMaxPret() {
        return 7; // 7 jours pour un périodique
    }

    @Override
    public String toString() {
        return "Périodique [" + super.toString() + ", N° Parution=" + numeroParution + ", Durée prêt=" + dureeMaxPret() + " jours]";
    }
}