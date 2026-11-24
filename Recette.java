import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Recette : execute les 29 scenarios de ESSAIS_MANUELS.md et affiche pour
 * chacun le resultat observe. Code de sortie 1 si un scenario echoue.
 */
public class Recette {
    private static int echecs = 0;

    public static void main(String[] args) throws Exception {
        Utilisateur u1 = new Utilisateur(20260153, "KARGOUGOU Sidi", "zarkawi.kargougou@2ie-edu.org");
        Utilisateur u2 = new Utilisateur(20240007, "ZIO Jolene Shirel", "shirel.jio@2ie-edu.org");

        Livre l = new Livre(1, "Maimouna", 200, "Abdoulaye Sadji");
        cas(1, l.getNumero() == 1 && l.getNombrePages() == 200 && l.getAuteurs().length == 1,
                "livre cree : " + l);
        Livre l2 = new Livre(2, "Collectif", 100, "A", "B");
        cas(2, l2.getAuteurs().length == 2, "auteurs conserves : " + l2.getAuteurs()[0] + ", " + l2.getAuteurs()[1]);
        Periodique p = new Periodique(3, "Revue", "Redaction 2iE", 45);
        cas(3, p.getOrganisme().equals("Redaction 2iE") && p.getNumeroParution() == 45, "periodique cree : " + p);
        cas(4, l.dureePret() == 21 && p.dureePret() == 7, "livre=" + l.dureePret() + " j, periodique=" + p.dureePret() + " j");
        cas(5, l.toString().startsWith("Livre [") && p.toString().startsWith("Periodique ["), "toString specifiques");
        Document dl = l; Document dp = p;
        cas(6, dl.dureePret() == 21, "reference Document -> Livre.dureePret()=" + dl.dureePret());
        cas(7, dp.dureePret() == 7, "reference Document -> Periodique.dureePret()=" + dp.dureePret());
        Document d = new Livre(10, "Test", 50, "X");
        boolean a = d.emprunter(u1);
        cas(8, a && !d.isDisponible(), "emprunt accepte, disponible=" + d.isDisponible());
        boolean b = d.emprunter(u2);
        cas(9, !b && d.getEmprunteur() == u1, "second emprunt refuse, emprunteur=" + d.getEmprunteur());
        boolean c = d.retourner();
        cas(10, c && d.isDisponible() && d.getEmprunteur() == null, "retour accepte, emprunteur efface");
        boolean e1 = d.emprunter(null), e2 = d.retourner();
        cas(11, !e1 && !e2 && d.isDisponible(), "emprunt(null)=" + e1 + ", retour d'un disponible=" + e2);
        int refus = 0;
        try { new Livre(0, "T", 1, "A"); } catch (IllegalArgumentException x) { refus++; }
        try { new Livre(5, " ", 1, "A"); } catch (IllegalArgumentException x) { refus++; }
        try { new Livre(5, "T", 1); } catch (IllegalArgumentException x) { refus++; }
        try { new Utilisateur(1, "A", "mauvais"); } catch (IllegalArgumentException x) { refus++; }
        cas(12, refus == 4, refus + " constructions invalides refusees sur 4");
        Document da = new Livre(20, "A", 10, "X"), db = new Periodique(20, "B", "R", 1), dc = new Livre(21, "C", 10, "X");
        cas(13, da.equals(db) && !da.equals(dc), "meme numero egaux (meme sous-type different), numeros differents non egaux");
        cas(14, da.hashCode() == db.hashCode(), "hashCode=" + da.hashCode() + " et " + db.hashCode());
        CatalogueDocuments cat = new CatalogueDocumentsMemoire();
        cas(15, cat.ajouter(da) && cat.ajouter(dc), "deux ajouts reussis");
        boolean dup = cat.ajouter(new Livre(20, "Doublon", 10, "X"));
        cas(16, !dup && cat.rechercherParNumero(20).get() == da, "doublon refuse, document initial conserve");
        cas(17, cat.rechercherParNumero(21).isPresent() && !cat.rechercherParNumero(99).isPresent(), "present trouve, 99 -> Optional.empty");
        cas(18, cat.parcourir().size() == 2, "parcours : " + cat.parcourir().size() + " documents");
        boolean immuable = false;
        try { cat.parcourir().clear(); } catch (UnsupportedOperationException x) { immuable = true; }
        cas(19, immuable && cat.parcourir().size() == 2, "modification refusee, catalogue intact");

        CatalogueDocuments c2 = new CatalogueDocumentsMemoire();
        c2.ajouter(new Livre(1, "L1", 10, "X")); c2.ajouter(new Livre(2, "L2", 10, "X"));
        c2.ajouter(new Livre(3, "L3", 10, "X")); c2.ajouter(new Periodique(4, "P4", "R", 1));
        ServicePret s = new ServicePret(c2, Clock.fixed(Instant.parse("2026-11-01T10:00:00Z"), ZoneOffset.UTC), 2);
        Pret p1 = s.emprunter(1, u1), p4 = s.emprunter(4, u1);
        cas(20, p1.getDateEcheance().toString().equals("2026-11-22") && p4.getDateEcheance().toString().equals("2026-11-08"),
                "echeances " + p1.getDateEcheance() + " (livre) et " + p4.getDateEcheance() + " (periodique)");
        cas(21, leve(() -> s.emprunter(99, u2), DocumentIntrouvableException.class) && s.pretsEnCours().size() == 2, "DocumentIntrouvableException, 2 prets inchanges");
        cas(22, leve(() -> s.emprunter(1, u2), DocumentDejaEmprunteException.class) && s.rechercherPret(1).get().getUtilisateur() == u1, "DocumentDejaEmprunteException, emprunteur initial conserve");
        cas(23, leve(() -> s.emprunter(2, u1), LimiteEmpruntsException.class) && c2.rechercherParNumero(2).get().isDisponible(), "LimiteEmpruntsException, document 2 reste disponible");
        s.retourner(1); s.emprunter(2, u1);
        cas(24, s.pretsDe(u1).size() == 2, "apres retour, nouvel emprunt accepte (" + s.pretsDe(u1).size() + " prets)");
        cas(25, leve(() -> s.retourner(3), DocumentNonEmprunteException.class) && leve(() -> s.retourner(1), DocumentNonEmprunteException.class), "DocumentNonEmprunteException (jamais emprunte, puis deja rendu)");
        cas(26, leveIae(() -> s.emprunter(3, null)) && leveIae(() -> s.emprunter(0, u2)), "IllegalArgumentException pour utilisateur null et numero 0");
        Horloge h = new Horloge(Instant.parse("2026-11-01T10:00:00Z"));
        ServicePret tard = new ServicePret(c2, h, 5);
        tard.emprunter(3, u2);                       // livre : 21 jours -> echeance 22 novembre
        boolean avantEcheance = tard.pretsEnRetard().isEmpty();
        h.instant = Instant.parse("2026-11-22T10:00:00Z");
        boolean jourEcheance = tard.pretsEnRetard().isEmpty();
        h.instant = Instant.parse("2026-11-23T10:00:00Z");
        boolean lendemain = tard.pretsEnRetard().size() == 1;
        cas(27, avantEcheance && jourEcheance && lendemain, "pas de retard le jour de l'echeance (22/11), retard le 23/11");
        boolean ro = false;
        try { s.pretsEnCours().clear(); } catch (UnsupportedOperationException x) { ro = true; }
        cas(28, ro && s.pretsEnCours().size() == 2, "liste non modifiable, 2 prets intacts");
        Document direct = new Livre(30, "Direct", 10, "X");
        c2.ajouter(direct); direct.emprunter(u2);
        cas(29, leve(() -> s.emprunter(30, u1), DocumentDejaEmprunteException.class) && leve(() -> s.retourner(30), DocumentNonEmprunteException.class) && s.rechercherPret(30).isEmpty(),
                "contournement detecte : emprunt et retour refuses par le service");

        System.out.println(echecs == 0 ? "\nRecette : 29 scenarios sur 29 conformes." : "\nRecette : " + echecs + " ECHEC(S).");
        if (echecs > 0) System.exit(1);
    }

    private static final class Horloge extends Clock {
        Instant instant;
        Horloge(Instant i) { instant = i; }
        @Override public java.time.ZoneId getZone() { return ZoneOffset.UTC; }
        @Override public Clock withZone(java.time.ZoneId z) { return this; }
        @Override public Instant instant() { return instant; }
    }

    private interface Appel { void faire() throws Exception; }

    private static boolean leve(Appel a, Class<? extends Exception> type) {
        try { a.faire(); return false; } catch (Exception e) { return type.isInstance(e); }
    }
    private static boolean leveIae(Appel a) { return leve(a, IllegalArgumentException.class); }

    private static void cas(int n, boolean ok, String observe) {
        if (!ok) echecs++;
        System.out.printf("%2d  %s  %s%n", n, ok ? "CONFORME" : "ECHEC   ", observe);
    }
}
