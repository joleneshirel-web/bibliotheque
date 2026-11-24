/** Le document est deja emprunte : un second emprunt est refuse. */
public class DocumentDejaEmprunteException extends BibliothequeException {
    private static final long serialVersionUID = 1L;
    private final int numero;

    public DocumentDejaEmprunteException(int numero) {
        super("Le document " + numero + " est deja emprunte.");
        this.numero = numero;
    }

    public int getNumero() { return numero; }
}
