/**
 * Un Periodique est un Document. Son organisme de redaction est une
 * information editoriale propre au periodique, pas un Auteur de Livre.
 */
public class Periodique extends Document {
    private final String organisme;
    private final int numeroParution;

    public Periodique(int numero, String titre, String organisme, int numeroParution) {
        super(numero, titre);
        if (organisme == null || organisme.trim().isEmpty()) {
            throw new IllegalArgumentException("L'organisme ne doit pas etre vide.");
        }
        if (numeroParution <= 0) {
            throw new IllegalArgumentException("Le numero de parution doit etre positif.");
        }
        this.organisme = organisme.trim();
        this.numeroParution = numeroParution;
    }

    public String getOrganisme() { return organisme; }
    public int getNumeroParution() { return numeroParution; }

    @Override
    public int dureePret() {
        return 7;
    }

    @Override
    public String toString() {
        return "Periodique [" + informationsCommunes()
                + ", organisme='" + organisme + "'"
                + ", numeroParution=" + numeroParution
                + ", dureePret=" + dureePret() + " jours]";
    }
}
