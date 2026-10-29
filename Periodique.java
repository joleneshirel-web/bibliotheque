
public class Periodique extends Document {

    private final int numeroParution;

    public Periodique(int numero, String titre, String auteur, int numeroParution) {
        super(numero, titre, auteur);

        if (numeroParution <= 0) {
            throw new IllegalArgumentException(
                "Le numero de parution doit etre positif."
            );
        }

        this.numeroParution = numeroParution;
    }

    public int getNumeroParution() {
        return numeroParution;
    }

    @Override
    public int dureeMaxPret() {
        return 7;
    }

    @Override
    public String toString() {
        return "Periodique [" + super.toString()
                + ", Numero de parution = " + numeroParution
                + ", Duree du pret = " + dureeMaxPret() + " jours]";
    }
}

