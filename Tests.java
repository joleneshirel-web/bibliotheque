/** Tests fonctionnels du modele du Jalon 2, sans bibliotheque externe. */
public class Tests {
    private static int total = 0;
    private static int reussis = 0;

    public static void main(String[] args) throws Exception {
        Utilisateur u1 = new Utilisateur(1, "Jolene", "jolene@example.com");
        Utilisateur u2 = new Utilisateur(2, "Sidi", "sidi@example.com");

        Document livre = new Livre(1, "Maimouna", 200, "Abdoulaye Sadji");
        Document periodique = new Periodique(2, "Journal scientifique", "Redaction 2iE", 5);

        System.out.println("=== 1. Structure et heritage ===");
        verifier("Document est abstraite", java.lang.reflect.Modifier.isAbstract(Document.class.getModifiers()));
        verifier("Livre herite de Document", Document.class.isAssignableFrom(Livre.class));
        verifier("Periodique herite de Document", Document.class.isAssignableFrom(Periodique.class));
        verifier("Livre respecte le contrat Pretable conserve du Jalon 1", Pretable.class.isAssignableFrom(Livre.class));
        verifier("Periodique respecte le contrat Pretable conserve du Jalon 1", Pretable.class.isAssignableFrom(Periodique.class));

        System.out.println("\n=== 2. Composition Livre-Auteur ===");
        Livre livreDeuxAuteurs = new Livre(3, "Livre collectif", 250, "Auteur A", "Auteur B");
        verifier("Un livre accepte plusieurs auteurs", livreDeuxAuteurs.getAuteurs().length == 2);
        verifier("Le premier auteur est conserve", livreDeuxAuteurs.getAuteurs()[0].getNom().equals("Auteur A"));
        verifier("Le second auteur est conserve", livreDeuxAuteurs.getAuteurs()[1].getNom().equals("Auteur B"));
        verifier("Les auteurs sont affiches par toString", livreDeuxAuteurs.toString().contains("Auteur B"));
        Auteur[] tableauExpose = livreDeuxAuteurs.getAuteurs();
        tableauExpose[0] = new Auteur("Modification externe");
        verifier("Modifier le tableau retourne ne modifie pas le livre", livreDeuxAuteurs.getAuteurs()[0].getNom().equals("Auteur A"));

        System.out.println("\n=== 3. Polymorphisme et substitution via Document ===");
        verifier("Livre : dureePret vaut 21 jours", livre.dureePret() == 21);
        verifier("Periodique : dureePret vaut 7 jours", periodique.dureePret() == 7);
        Document referenceLivre = new Livre(4, "L'enfant noir", 180, "Camara Laye");
        Document referencePeriodique = new Periodique(5, "Revue", "Redaction", 8);
        verifier("Substitution 1 : Document reference vers Livre", referenceLivre.dureePret() == 21);
        verifier("Substitution 2 : Document reference vers Periodique", referencePeriodique.dureePret() == 7);
        verifier("toString dynamique du Livre", referenceLivre.toString().startsWith("Livre ["));
        verifier("toString dynamique du Periodique", referencePeriodique.toString().startsWith("Periodique ["));

        System.out.println("\n=== 4. Contrats emprunt et retour ===");
        verifier("Un document neuf est disponible", livre.isDisponible());
        verifier("Un document neuf n'a pas d'emprunteur", livre.getEmprunteur() == null);
        verifier("Emprunt accepte via une reference Document", referenceLivre.emprunter(u1));
        verifier("Postcondition emprunt : document indisponible", !referenceLivre.isDisponible());
        verifier("Postcondition emprunt : utilisateur enregistre", referenceLivre.getEmprunteur() == u1);
        verifier("Un second emprunt est refuse", !referenceLivre.emprunter(u2));
        verifier("Echec sans changement : emprunteur initial conserve", referenceLivre.getEmprunteur() == u1);
        verifier("Retour accepte via une reference Document", referenceLivre.retourner());
        verifier("Postcondition retour : document disponible", referenceLivre.isDisponible());
        verifier("Postcondition retour : emprunteur efface", referenceLivre.getEmprunteur() == null);
        verifier("Un second retour est refuse", !referenceLivre.retourner());
        verifier("Un emprunt avec utilisateur null est refuse", !periodique.emprunter(null));
        verifier("Echec sans changement : periodique toujours disponible", periodique.isDisponible());

        System.out.println("\n=== 5. Invariants et validations ===");
        verifier("Numero nul refuse", refuse(() -> new Livre(0, "Titre", 10, "Auteur")));
        verifier("Titre vide refuse", refuse(() -> new Livre(6, " ", 10, "Auteur")));
        verifier("Nombre de pages nul refuse", refuse(() -> new Livre(6, "Titre", 0, "Auteur")));
        verifier("Livre sans auteur refuse", refuse(() -> new Livre(6, "Titre", 10)));
        verifier("Nom d'auteur vide refuse", refuse(() -> new Livre(6, "Titre", 10, " ")));
        verifier("Numero de parution negatif refuse", refuse(() -> new Periodique(7, "Revue", "Redaction", -1)));
        verifier("Organisme vide refuse", refuse(() -> new Periodique(7, "Revue", " ", 1)));
        verifier("Identifiant utilisateur nul refuse", refuse(() -> new Utilisateur(0, "Awa", "awa@example.com")));
        verifier("Nom utilisateur vide refuse", refuse(() -> new Utilisateur(8, " ", "awa@example.com")));
        verifier("Courriel invalide refuse", refuse(() -> new Utilisateur(8, "Awa", "awa.example.com")));

        System.out.println("\n=== 6. Conservation du clonage du Jalon 1 ===");
        Livre original = new Livre(9, "Original", 100, "Auteur Original");
        Livre copie = original.clone();
        verifier("Le clone est un objet distinct", copie != original);
        verifier("Le clone conserve les informations du livre", copie.getTitre().equals(original.getTitre()));
        verifier("Le clone conserve son auteur", copie.getAuteurs()[0].getNom().equals("Auteur Original"));
        verifier("Le tableau d'auteurs du clone est distinct", copie.getAuteurs() != original.getAuteurs());

        System.out.println("\nResultat : " + reussis + " tests reussis sur " + total);
        if (reussis != total) {
            System.exit(1);
        }
    }

    private static boolean refuse(Action action) {
        try {
            action.executer();
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    private interface Action {
        void executer();
    }

    private static void verifier(String description, boolean condition) {
        total++;
        if (condition) {
            reussis++;
        }
        System.out.println((condition ? "REUSSI : " : "ECHEC : ") + description);
    }
}
