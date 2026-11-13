import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Catalogue en memoire. La Map associe le numero unique au document.
 */
public class CatalogueDocumentsMemoire implements CatalogueDocuments {
    private final Map<Integer, Document> documents = new HashMap<>();

    @Override
    public boolean ajouter(Document document) {
        if (document == null) {
            throw new IllegalArgumentException("Le document ne doit pas etre null.");
        }

        int numero = document.getNumero();
        if (documents.containsKey(numero)) {
            return false;
        }

        documents.put(numero, document);
        return true;
    }

    @Override
    public Optional<Document> rechercherParNumero(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("Le numero doit etre strictement positif.");
        }
        return Optional.ofNullable(documents.get(numero));
    }

    @Override
    public List<Document> parcourir() {
        // Copie defensive non modifiable : la Map privee reste inaccessible.
        return Collections.unmodifiableList(new ArrayList<>(documents.values()));
    }
}
