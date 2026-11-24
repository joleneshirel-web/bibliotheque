import java.time.LocalDate;

/**
 * Trace immuable d'un pret : quel document, quel utilisateur, quand,
 * et jusqu'a quand. La date d'echeance est fixee a la creation a partir
 * de la duree propre au sous-type du document (polymorphisme).
 */
public final class Pret {
    private final int numeroDocument;
    private final Utilisateur utilisateur;
    private final LocalDate dateEmprunt;
    private final LocalDate dateEcheance;

    public Pret(int numeroDocument, Utilisateur utilisateur,
                LocalDate dateEmprunt, LocalDate dateEcheance) {
        if (numeroDocument <= 0) {
            throw new IllegalArgumentException("Le numero du document doit etre positif.");
        }
        if (utilisateur == null || dateEmprunt == null || dateEcheance == null) {
            throw new IllegalArgumentException("Utilisateur et dates sont obligatoires.");
        }
        if (dateEcheance.isBefore(dateEmprunt)) {
            throw new IllegalArgumentException("L'echeance ne peut pas preceder l'emprunt.");
        }
        this.numeroDocument = numeroDocument;
        this.utilisateur = utilisateur;
        this.dateEmprunt = dateEmprunt;
        this.dateEcheance = dateEcheance;
    }

    public int getNumeroDocument() { return numeroDocument; }
    public Utilisateur getUtilisateur() { return utilisateur; }
    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public LocalDate getDateEcheance() { return dateEcheance; }

    /** Un pret est en retard strictement apres sa date d'echeance. */
    public boolean estEnRetard(LocalDate jour) {
        if (jour == null) {
            throw new IllegalArgumentException("La date de reference est obligatoire.");
        }
        return jour.isAfter(dateEcheance);
    }

    @Override
    public String toString() {
        return "Pret [document=" + numeroDocument
                + ", utilisateur=" + utilisateur
                + ", emprunt=" + dateEmprunt
                + ", echeance=" + dateEcheance + "]";
    }
}
