public class Main {
    public static void main(String[] args) {
        Document doc1 = new Livre(1, "Maimouna", "Abdoulaye Sadji", 200);
        Document doc2 = new Periodique(2, "Journal Scientifique", "Rédaction 2iE", 45);

        Utilisateur etudiant1 = new Utilisateur(20240007, "Jolène ZIO");
        Utilisateur etudiant2 = new Utilisateur(20240008, "Sidi KARGOUGOU");

        System.out.println("1. TEST DES DURÉES ET DU POLYMORPHISME ");
        Document[] documents = { doc1, doc2 };
        for (Document d : documents) {
            System.out.println("Titre : " + d.getTitre() + " | Durée max de prêt : " + d.dureeMaxPret() + " jours");
        }

        System.out.println("\n 2. TEST DES SCÉNARIOS D'EMPRUNT ET DE RETOUR ");
        boolean premierEmprunt = doc1.emprunter(etudiant1);
        System.out.println(etudiant1.getNom() + " tente d'emprunter : " + (premierEmprunt ? "ACCEPTÉ" : "REFUSÉ"));

        boolean secondEmprunt = doc1.emprunter(etudiant2);
        System.out.println(etudiant2.getNom() + " tente d'emprunter le même livre : " + (secondEmprunt ? "ACCEPTÉ" : "REFUSÉ (Déjà emprunté)"));

        doc1.retourner();
        System.out.println("Retour du livre effectué.");

        boolean empruntApresRetour = doc1.emprunter(etudiant2);
        System.out.println(etudiant2.getNom() + " tente d'emprunter après retour : " + (empruntApresRetour ? "ACCEPTÉ" : "REFUSÉ"));

        System.out.println("\n3. TEST APRÈS CLONAGE ");
        try {
            Document docClone = (Document) doc1.clone();
            System.out.println("Original emprunté ? " + doc1.estEmprunte());
            System.out.println("Clone emprunté ? " + docClone.estEmprunte());
        } catch (CloneNotSupportedException e) {
            System.out.println("Erreur de clonage");
        }
    }
}
