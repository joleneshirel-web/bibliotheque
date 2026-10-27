
public class Main {

    public static void main(String[] args) {

        Utilisateur u1 = new Utilisateur(1, "Jolene");
        Utilisateur u2 = new Utilisateur(2, "Sidi");

        System.out.println("=== Test des documents ===");

        // Creation des documents
        Document livre = new Livre(1, "Maimouna", "Abdoulaye Sadji", 200);
        Document periodique = new Periodique(2, "Revue scientifique", "Redaction", 5);

        System.out.println(livre);
        System.out.println(periodique);

        // Test des constructeurs
        try {
            new Livre(0, "Java", "Dupont", 100);
            System.out.println("Erreur : numero invalide accepte");
        } catch (IllegalArgumentException e) {
            System.out.println("Test numero invalide : OK");
        }

        try {
            new Livre(3, "", "Dupont", 100);
            System.out.println("Erreur : titre vide accepte");
        } catch (IllegalArgumentException e) {
            System.out.println("Test titre vide : OK");
        }

        try {
            new Livre(4, "Java", "Dupont", 0);
            System.out.println("Erreur : nombre de pages invalide accepte");
        } catch (IllegalArgumentException e) {
            System.out.println("Test nombre de pages : OK");
        }

        try {
            new Utilisateur(0, "Awa");
            System.out.println("Erreur : identifiant invalide accepte");
        } catch (IllegalArgumentException e) {
            System.out.println("Test identifiant utilisateur : OK");
        }

        // Test des durees de pret
        System.out.println("\n=== Test des durees ===");
        System.out.println("Duree du pret d'un livre : " + livre.dureeMaxPret() + " jours");
        System.out.println("Duree du pret d'un periodique : " + periodique.dureeMaxPret() + " jours");

        // Test de l'emprunt
        System.out.println("\n=== Test des emprunts ===");

        if (livre.emprunter(u1)) {
            System.out.println("Le livre a ete emprunte par " + u1.getNom());
        }

        if (!livre.emprunter(u2)) {
            System.out.println("Le livre est deja emprunte.");
        }

        System.out.println("Emprunteur actuel : " + livre.getEmprunteur());

        // Test du retour
        livre.retourner();

        if (livre.isDisponible()) {
            System.out.println("Le livre a ete retourne.");
        }

        if (livre.emprunter(u2)) {
            System.out.println("Le livre peut maintenant etre emprunte par " + u2.getNom());
        }
    }
}
