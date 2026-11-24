/** Aucun pret n'est enregistre pour ce document : le retour est impossible. */
public class DocumentNonEmprunteException extends BibliothequeException {
    private static final long serialVersionUID = 1L;
    private final int numero;

    public DocumentNonEmprunteException(int numero) {
        super("Aucun pret n'est enregistre pour le document " + numero + ".");
        this.numero = numero;
    }

    public int getNumero() { return numero; }
}
