/**
 * Racine des erreurs metier de la bibliotheque (exception verifiee).
 * Elle represente un refus legitime d'une operation (document absent,
 * deja emprunte, limite atteinte...) que l'appelant doit traiter.
 * Les erreurs de programmation (argument null, numero negatif) restent
 * des IllegalArgumentException non verifiees.
 */
public class BibliothequeException extends Exception {
    private static final long serialVersionUID = 1L;

    public BibliothequeException(String message) {
        super(message);
    }
}
