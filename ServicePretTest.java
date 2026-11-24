import static org.junit.Assert.*;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

/** Essais JUnit 4 du Jalon 4 : ServicePret, exceptions metier, atomicite. */
public class ServicePretTest {
    private static final Instant DEBUT = Instant.parse("2026-11-01T10:00:00Z");

    private HorlogeMobile horloge;
    private CatalogueDocuments catalogue;
    private ServicePret service;
    private Document livre1, livre2, periodique3, livre4;
    private Utilisateur sidi, jolene;

    @Before
    public void preparer() {
        horloge = new HorlogeMobile(DEBUT);
        catalogue = new CatalogueDocumentsMemoire();
        livre1 = new Livre(1, "Maimouna", 200, "Abdoulaye Sadji");
        livre2 = new Livre(2, "L'enfant noir", 180, "Camara Laye");
        periodique3 = new Periodique(3, "Journal scientifique", "Redaction 2iE", 45);
        livre4 = new Livre(4, "Les Soleils des independances", 220, "Ahmadou Kourouma");
        for (Document d : new Document[] {livre1, livre2, periodique3, livre4}) {
            catalogue.ajouter(d);
        }
        service = new ServicePret(catalogue, horloge, 2);
        sidi = new Utilisateur(20260153, "KARGOUGOU Sidi", "zarkawi.kargougou@2ie-edu.org");
        jolene = new Utilisateur(20240007, "ZIO Jolene Shirel", "shirel.jio@2ie-edu.org");
    }

    // --- Emprunt nominal -------------------------------------------------

    @Test
    public void empruntValideModifieLeDocumentEtCreeLePret() throws Exception {
        Pret pret = service.emprunter(1, sidi);
        assertTrue(livre1.estEmprunte());
        assertSame(sidi, livre1.getEmprunteur());
        assertEquals(1, pret.getNumeroDocument());
        assertSame(sidi, pret.getUtilisateur());
        assertEquals(LocalDate.of(2026, 11, 1), pret.getDateEmprunt());
        assertTrue(service.rechercherPret(1).isPresent());
    }

    @Test
    public void echeanceDependDuSousTypeDuDocument() throws Exception {
        Pret pretLivre = service.emprunter(1, sidi);
        Pret pretPeriodique = service.emprunter(3, sidi);
        assertEquals(LocalDate.of(2026, 11, 22), pretLivre.getDateEcheance());       // 21 jours
        assertEquals(LocalDate.of(2026, 11, 8), pretPeriodique.getDateEcheance());   // 7 jours
    }

    // --- Refus metier : etat inchange -----------------------------------

    @Test
    public void documentInconnuLeveUneExceptionMetier() {
        DocumentIntrouvableException e = assertThrows(DocumentIntrouvableException.class,
                () -> service.emprunter(99, sidi));
        assertEquals(99, e.getNumero());
        assertTrue(service.pretsEnCours().isEmpty());
    }

    @Test
    public void secondEmpruntRefuseEtPremierEmprunteurConserve() throws Exception {
        Pret premier = service.emprunter(1, sidi);
        assertThrows(DocumentDejaEmprunteException.class, () -> service.emprunter(1, jolene));
        assertSame(sidi, livre1.getEmprunteur());
        assertSame(premier, service.rechercherPret(1).get());
        assertEquals(1, service.pretsEnCours().size());
    }

    @Test
    public void limiteDEmpruntsRefuseSansToucherAuDocument() throws Exception {
        service.emprunter(1, sidi);
        service.emprunter(2, sidi);
        LimiteEmpruntsException e = assertThrows(LimiteEmpruntsException.class,
                () -> service.emprunter(3, sidi));
        assertEquals(2, e.getLimite());
        assertEquals(20260153, e.getIdUtilisateur());
        assertFalse(periodique3.estEmprunte());
        assertFalse(service.rechercherPret(3).isPresent());
    }

    @Test
    public void limiteLibereeApresUnRetour() throws Exception {
        service.emprunter(1, sidi);
        service.emprunter(2, sidi);
        service.retourner(1);
        service.emprunter(3, sidi);
        assertEquals(2, service.pretsDe(sidi).size());
    }

    @Test
    public void limiteEstAppliqueeParUtilisateur() throws Exception {
        service.emprunter(1, sidi);
        service.emprunter(2, sidi);
        service.emprunter(3, jolene);   // une autre personne n'est pas bloquee
        assertEquals(1, service.pretsDe(jolene).size());
    }

    // --- Retour ----------------------------------------------------------

    @Test
    public void retourRestitueLeDocumentEtSupprimeLePret() throws Exception {
        Pret emprunte = service.emprunter(1, sidi);
        Pret termine = service.retourner(1);
        assertSame(emprunte, termine);
        assertFalse(livre1.estEmprunte());
        assertNull(livre1.getEmprunteur());
        assertFalse(service.rechercherPret(1).isPresent());
    }

    @Test
    public void retourSansPretEstRefuse() {
        assertThrows(DocumentNonEmprunteException.class, () -> service.retourner(1));
    }

    @Test
    public void secondRetourEstRefuse() throws Exception {
        service.emprunter(1, sidi);
        service.retourner(1);
        assertThrows(DocumentNonEmprunteException.class, () -> service.retourner(1));
    }

    @Test
    public void retourDUnDocumentInconnuEstRefuse() {
        assertThrows(DocumentIntrouvableException.class, () -> service.retourner(99));
    }

    @Test
    public void documentRetourneRedevientEmpruntableParUnAutre() throws Exception {
        service.emprunter(1, sidi);
        service.retourner(1);
        Pret pret = service.emprunter(1, jolene);
        assertSame(jolene, pret.getUtilisateur());
    }

    // --- Erreurs de programmation (non verifiees) ------------------------

    @Test
    public void utilisateurNullEstUneErreurDeProgrammation() {
        assertThrows(IllegalArgumentException.class, () -> service.emprunter(1, null));
        assertFalse(livre1.estEmprunte());
    }

    @Test
    public void numeroNonPositifEstUneErreurDeProgrammation() {
        assertThrows(IllegalArgumentException.class, () -> service.emprunter(0, sidi));
        assertThrows(IllegalArgumentException.class, () -> service.retourner(-5));
    }

    @Test
    public void constructeurRefuseLesParametresInvalides() {
        assertThrows(IllegalArgumentException.class, () -> new ServicePret(null));
        assertThrows(IllegalArgumentException.class,
                () -> new ServicePret(catalogue, null, 2));
        assertThrows(IllegalArgumentException.class,
                () -> new ServicePret(catalogue, horloge, 0));
    }

    @Test
    public void pretRefuseLesDonneesIncoherentes() {
        LocalDate jour = LocalDate.of(2026, 11, 1);
        assertThrows(IllegalArgumentException.class, () -> new Pret(0, sidi, jour, jour));
        assertThrows(IllegalArgumentException.class, () -> new Pret(1, null, jour, jour));
        assertThrows(IllegalArgumentException.class,
                () -> new Pret(1, sidi, jour, jour.minusDays(1)));
    }

    // --- Retards ---------------------------------------------------------

    @Test
    public void retardDetecteStrictementApresLEcheance() throws Exception {
        service.emprunter(3, sidi);                          // echeance le 8 novembre
        horloge.avancerDe(7);                               // 8 novembre : pas en retard
        assertTrue(service.pretsEnRetard().isEmpty());
        horloge.avancerDe(1);                               // 9 novembre : en retard
        assertEquals(1, service.pretsEnRetard().size());
    }

    @Test
    public void seulsLesPretsDepassesSontEnRetard() throws Exception {
        service.emprunter(3, sidi);                          // 7 jours
        service.emprunter(1, jolene);                       // 21 jours
        horloge.avancerDe(10);
        List<Pret> retards = service.pretsEnRetard();
        assertEquals(1, retards.size());
        assertEquals(3, retards.get(0).getNumeroDocument());
    }

    // --- Encapsulation des resultats ------------------------------------

    @Test
    public void listesRenvoyeesSontNonModifiables() throws Exception {
        service.emprunter(1, sidi);
        List<Pret> liste = service.pretsEnCours();
        assertThrows(UnsupportedOperationException.class, liste::clear);
        assertEquals(1, service.pretsEnCours().size());
    }

    @Test
    public void listesSontTrieesParNumeroDeDocument() throws Exception {
        service.emprunter(3, sidi);
        service.emprunter(1, sidi);
        List<Pret> liste = service.pretsEnCours();
        assertEquals(1, liste.get(0).getNumeroDocument());
        assertEquals(3, liste.get(1).getNumeroDocument());
    }

    // --- Atomicite --------------------------------------------------------

    @Test
    public void panneApresLeChangementDEtatAnnuleTout() throws Exception {
        horloge.declencherPanne();
        assertThrows(IllegalStateException.class, () -> service.emprunter(1, sidi));
        // Retour a l'etat initial : ni document modifie, ni pret enregistre.
        assertFalse(livre1.estEmprunte());
        assertNull(livre1.getEmprunteur());
        assertTrue(service.pretsEnCours().isEmpty());
        // Le systeme reste utilisable apres l'incident.
        horloge.reparer();
        assertNotNull(service.emprunter(1, sidi));
    }

    @Test
    public void refusMetierNeLaisseAucuneTrace() throws Exception {
        service.emprunter(1, sidi);
        service.emprunter(2, sidi);
        List<Pret> avant = service.pretsEnCours();
        assertThrows(LimiteEmpruntsException.class, () -> service.emprunter(3, sidi));
        assertThrows(DocumentDejaEmprunteException.class, () -> service.emprunter(1, jolene));
        assertEquals(avant, service.pretsEnCours());
    }

    // --- Contournement du service et substitution du catalogue ----------

    @Test
    public void emprunteDirectementSurLeDocumentEstDetecte() throws Exception {
        assertTrue(livre1.emprunter(jolene));               // contourne le service
        assertThrows(DocumentDejaEmprunteException.class, () -> service.emprunter(1, sidi));
        assertThrows(DocumentNonEmprunteException.class, () -> service.retourner(1));
        assertTrue(service.pretsEnCours().isEmpty());
    }

    @Test
    public void serviceFonctionneAvecNimporteQuelleImplementationDuCatalogue() throws Exception {
        CatalogueDocuments liste = new CatalogueEnListe();
        Document doc = new Livre(7, "Autre catalogue", 100, "Auteur");
        liste.ajouter(doc);
        ServicePret autre = new ServicePret(liste, horloge, 1);
        autre.emprunter(7, sidi);
        assertTrue(doc.estEmprunte());
        assertThrows(DocumentIntrouvableException.class, () -> autre.emprunter(8, sidi));
    }

    // --- Doublures de test ------------------------------------------------

    /** Horloge reglable, avec possibilite de simuler une panne. */
    private static final class HorlogeMobile extends Clock {
        private Instant instant;
        private boolean enPanne;

        HorlogeMobile(Instant depart) { this.instant = depart; }

        void avancerDe(int jours) { instant = instant.plusSeconds(jours * 86_400L); }
        void declencherPanne() { enPanne = true; }
        void reparer() { enPanne = false; }

        @Override public ZoneId getZone() { return ZoneOffset.UTC; }
        @Override public Clock withZone(ZoneId zone) { return this; }
        @Override public Instant instant() {
            if (enPanne) {
                throw new IllegalStateException("Panne simulee de l'horloge");
            }
            return instant;
        }
    }

    /** Seconde implementation du contrat, basee sur une liste. */
    private static final class CatalogueEnListe implements CatalogueDocuments {
        private final List<Document> documents = new ArrayList<>();

        @Override public boolean ajouter(Document document) {
            if (documents.contains(document)) {
                return false;
            }
            return documents.add(document);
        }

        @Override public Optional<Document> rechercherParNumero(int numero) {
            for (Document d : documents) {
                if (d.getNumero() == numero) {
                    return Optional.of(d);
                }
            }
            return Optional.empty();
        }

        @Override public List<Document> parcourir() { return new ArrayList<>(documents); }
    }
}
