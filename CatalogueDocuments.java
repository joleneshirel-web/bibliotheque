import java.util.List;
import java.util.Optional;

/**
 * Contrat d'acces au catalogue, independant de son implementation.
 */
public interface CatalogueDocuments {
    /** Ajoute un document ; false si son numero est deja present. */
    boolean ajouter(Document document);

    /** Recherche un document par son numero. */
    Optional<Document> rechercherParNumero(int numero);

    /** Parcourt les documents sans exposer la collection interne. */
    List<Document> parcourir();
}
