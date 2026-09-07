public class Main {

    public static void main(String[] args) {

        // Création des objets
        Livre livre1 = new Livre("Maimouna", "Abdoulaye Sadji");
        Livre livre2 = new Livre("l'enfant noir", "Camara Laye");

        utilisateur utilisateur1 = new utilisateur(1, "SIDI");

        // Affichage de l'utilisateur
        System.out.println("=== UTILISATEUR ===");
        utilisateur1.afficherInformations();

        // Affichage des livres
        System.out.println("\n=== LIVRES ===");
        livre1.afficherInformations();
        System.out.println();
        livre2.afficherInformations();

        // Emprunt du premier livre
        System.out.println("\n=== EMPRUNT ===");
        System.out.println(utilisateur1.getNom() + " souhaite emprunter le livre.");
        livre1.emprunter();

        // Vérification
        System.out.println("\n=== ÉTAT DU LIVRE ===");
        livre1.afficherInformations();

        // Retour du livre
        System.out.println("\n=== RETOUR ===");
        System.out.println(utilisateur1.getNom() + " retourne le livre.");
        livre1.retourner();

        // Vérification finale
        System.out.println("\n=== ÉTAT FINAL ===");
        livre1.afficherInformations();
    }
}