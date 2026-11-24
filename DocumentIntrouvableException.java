/** Aucun document ne porte ce numero dans le catalogue. */
public class DocumentIntrouvableException extends BibliothequeException {
    private static final long serialVersionUID = 1L;
    private final int numero;

    public DocumentIntrouvableException(int numero) {
        super("Aucun document ne porte le numero " + numero + ".");
        this.numero = numero;
    }

    public int getNumero() { return numero; }
}
