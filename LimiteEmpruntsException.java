/** L'utilisateur a deja atteint le nombre maximal de prets simultanes. */
public class LimiteEmpruntsException extends BibliothequeException {
    private static final long serialVersionUID = 1L;
    private final int idUtilisateur;
    private final int limite;

    public LimiteEmpruntsException(int idUtilisateur, int limite) {
        super("L'utilisateur " + idUtilisateur + " a atteint la limite de "
                + limite + " prets simultanes.");
        this.idUtilisateur = idUtilisateur;
        this.limite = limite;
    }

    public int getIdUtilisateur() { return idUtilisateur; }
    public int getLimite() { return limite; }
}
