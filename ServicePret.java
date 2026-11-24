import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service metier des prets. Il s'appuie sur le contrat CatalogueDocuments
 * (jamais sur l'implementation concrete) et sur une horloge injectee, ce
 * qui rend les dates testables.
 *
 * Invariants maintenus par le service :
 *  - un document a au plus un pret en cours ;
 *  - un utilisateur a au plus 'limiteEmprunts' prets en cours ;
 *  - tout pret en cours correspond a un document emprunte, et inversement
 *    pour les documents empruntes via le service.
 *
 * Atomicite : chaque operation est « tout ou rien ». Les controles sont
 * faits avant toute modification ; la seule etape qui modifie deux etats
 * (le document puis le registre des prets) est compensee en cas d'erreur.
 */
public class ServicePret {
    public static final int LIMITE_PAR_DEFAUT = 3;

    private final CatalogueDocuments catalogue;
    private final Clock horloge;
    private final int limiteEmprunts;
    private final Map<Integer, Pret> pretsEnCours = new HashMap<>();

    public ServicePret(CatalogueDocuments catalogue) {
        this(catalogue, Clock.systemDefaultZone(), LIMITE_PAR_DEFAUT);
    }

    public ServicePret(CatalogueDocuments catalogue, Clock horloge, int limiteEmprunts) {
        if (catalogue == null || horloge == null) {
            throw new IllegalArgumentException("Catalogue et horloge sont obligatoires.");
        }
        if (limiteEmprunts <= 0) {
            throw new IllegalArgumentException("La limite d'emprunts doit etre strictement positive.");
        }
        this.catalogue = catalogue;
        this.horloge = horloge;
        this.limiteEmprunts = limiteEmprunts;
    }

    public int getLimiteEmprunts() { return limiteEmprunts; }

    /**
     * Enregistre l'emprunt du document par l'utilisateur.
     * Echec (exception) : aucun etat n'est modifie.
     */
    public Pret emprunter(int numeroDocument, Utilisateur utilisateur)
            throws BibliothequeException {
        if (utilisateur == null) {
            throw new IllegalArgumentException("L'utilisateur ne doit pas etre null.");
        }
        Document document = trouver(numeroDocument);

        // 1. Tous les controles, sans rien modifier.
        if (document.estEmprunte() || pretsEnCours.containsKey(numeroDocument)) {
            throw new DocumentDejaEmprunteException(numeroDocument);
        }
        if (nombrePrets(utilisateur) >= limiteEmprunts) {
            throw new LimiteEmpruntsException(utilisateur.getId(), limiteEmprunts);
        }

        // 2. Modification en deux temps, avec compensation.
        if (!document.emprunter(utilisateur)) {
            throw new DocumentDejaEmprunteException(numeroDocument);
        }
        try {
            LocalDate debut = LocalDate.now(horloge);
            Pret pret = new Pret(numeroDocument, utilisateur, debut,
                    debut.plusDays(document.dureePret()));
            pretsEnCours.put(numeroDocument, pret);
            return pret;
        } catch (RuntimeException erreur) {
            document.retourner();
            pretsEnCours.remove(numeroDocument);
            throw erreur;
        }
    }

    /** Enregistre le retour et renvoie le pret qui vient de se terminer. */
    public Pret retourner(int numeroDocument) throws BibliothequeException {
        Document document = trouver(numeroDocument);
        Pret pret = pretsEnCours.get(numeroDocument);
        if (pret == null) {
            throw new DocumentNonEmprunteException(numeroDocument);
        }
        document.retourner();
        pretsEnCours.remove(numeroDocument);
        return pret;
    }

    public Optional<Pret> rechercherPret(int numeroDocument) {
        return Optional.ofNullable(pretsEnCours.get(numeroDocument));
    }

    /** Copie non modifiable, triee par numero de document. */
    public List<Pret> pretsEnCours() {
        return copieTriee(new ArrayList<>(pretsEnCours.values()));
    }

    public List<Pret> pretsDe(Utilisateur utilisateur) {
        if (utilisateur == null) {
            throw new IllegalArgumentException("L'utilisateur ne doit pas etre null.");
        }
        List<Pret> resultat = new ArrayList<>();
        for (Pret pret : pretsEnCours.values()) {
            if (pret.getUtilisateur().getId() == utilisateur.getId()) {
                resultat.add(pret);
            }
        }
        return copieTriee(resultat);
    }

    public List<Pret> pretsEnRetard() {
        LocalDate aujourdhui = LocalDate.now(horloge);
        List<Pret> resultat = new ArrayList<>();
        for (Pret pret : pretsEnCours.values()) {
            if (pret.estEnRetard(aujourdhui)) {
                resultat.add(pret);
            }
        }
        return copieTriee(resultat);
    }

    private Document trouver(int numeroDocument) throws DocumentIntrouvableException {
        // Un numero <= 0 est une erreur de programmation : le catalogue
        // leve IllegalArgumentException, que l'on laisse remonter.
        return catalogue.rechercherParNumero(numeroDocument)
                .orElseThrow(() -> new DocumentIntrouvableException(numeroDocument));
    }

    private int nombrePrets(Utilisateur utilisateur) {
        int total = 0;
        for (Pret pret : pretsEnCours.values()) {
            if (pret.getUtilisateur().getId() == utilisateur.getId()) {
                total++;
            }
        }
        return total;
    }

    private static List<Pret> copieTriee(List<Pret> prets) {
        prets.sort(Comparator.comparingInt(Pret::getNumeroDocument));
        return Collections.unmodifiableList(prets);
    }
}
