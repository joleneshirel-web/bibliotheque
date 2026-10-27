
public class Periodique extends Document {

    private final int numeroParution;

    public Periodique(
            int numero,
            String titre,
            String auteur,
            int numeroParution) {

        super(numero, titre, auteur);

        if (numeroParution <= 0) {
            throw new IllegalArgumentException(
                "Le numéro de parution doit être strictement positif."
            );
        }

        this.numeroParution = numeroParution;
    }

    public int getNumeroParution() {
        return numeroParution;
    }

    /**
     * Un périodique peut être emprunté pendant 7 jours maximum.
     */
    @Override
    public int dureeMaxPret() {
        return 7;
    }

    @Override
    public String toString() {
        return "Periodique [" + super.toString()
                + ", N° Parution=" + numeroParution
                + ", Durée prêt=" + dureeMaxPret() + " jours]";
    }
}
```
