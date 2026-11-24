/** Demonstration du Jalon 4 : scenario complet de prets avec erreurs metier. */
public class Main {
    public static void main(String[] args) {
        CatalogueDocuments catalogue = new CatalogueDocumentsMemoire();
        catalogue.ajouter(new Livre(1, "Maimouna", 200, "Abdoulaye Sadji"));
        catalogue.ajouter(new Livre(2, "L'enfant noir", 180, "Camara Laye"));
        catalogue.ajouter(new Periodique(3, "Journal scientifique", "Redaction 2iE", 45));
        catalogue.ajouter(new Livre(4, "Les Soleils des independances", 220, "Ahmadou Kourouma"));

        ServicePret service = new ServicePret(catalogue,
                java.time.Clock.systemDefaultZone(), 2);
        Utilisateur sidi = new Utilisateur(20260153, "KARGOUGOU Sidi", "zarkawi.kargougou@2ie-edu.org");
        Utilisateur jolene = new Utilisateur(20240007, "ZIO Jolene Shirel", "shirel.zio@2ie-edu.org");
        Utilisateur murielle = new Utilisateur(20240209, "OUEDRAOGO Murielle", "muriel.ouedraogo@2ie-edu.org");

        System.out.println("=== Scenario de prets (limite : 2 par utilisateur) ===");
        tenterEmprunt(service, 1, sidi);
        tenterEmprunt(service, 3, sidi);
        tenterEmprunt(service, 2, sidi);       // limite atteinte
        tenterEmprunt(service, 1, jolene);     // deja emprunte
        tenterEmprunt(service, 99, jolene);    // inconnu
        tenterRetour(service, 1);
        tenterRetour(service, 1);              // plus de pret
        tenterEmprunt(service, 2, sidi);       // possible apres le retour
        tenterEmprunt(service, 1, murielle);   // emprunt du livre libere

        System.out.println("\nPrets en cours :");
        for (Pret pret : service.pretsEnCours()) {
            System.out.println("  " + pret);
        }

        System.out.println("\nPrets en retard :");
        if (service.pretsEnRetard().isEmpty()) {
            System.out.println("  Aucun pret en retard.");
        } else {
            for (Pret pret : service.pretsEnRetard()) {
                System.out.println("  " + pret);
            }
        }
    }

    private static void tenterEmprunt(ServicePret service, int numero, Utilisateur u) {
        try {
            System.out.println("OK      : " + service.emprunter(numero, u));
        } catch (BibliothequeException e) {
            System.out.println("REFUSE  : " + e.getMessage());
        }
    }

    private static void tenterRetour(ServicePret service, int numero) {
        try {
            System.out.println("OK      : retour " + service.retourner(numero));
        } catch (BibliothequeException e) {
            System.out.println("REFUSE  : " + e.getMessage());
        }
    }
}
