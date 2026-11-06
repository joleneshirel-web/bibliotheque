public class Main {
    public static void main(String[] args) {
        Utilisateur utilisateur = new Utilisateur(1, "Jolene", "jolene@example.com");

        Document[] documents = {
            new Livre(1, "Maimouna", 200, "Abdoulaye Sadji"),
            new Livre(2, "Une si longue lettre", 131, "Mariama Ba"),
            new Periodique(3, "Journal scientifique", "Redaction 2iE", 45)
        };

        // Aucun test de type : le polymorphisme choisit dureePret() et toString().
        for (Document document : documents) {
            System.out.println(document);
            System.out.println("Duree de pret : " + document.dureePret() + " jours");
        }

        Document document = documents[0];
        if (document.emprunter(utilisateur)) {
            System.out.println("Emprunt accepte : " + document.getEmprunteur());
        }
        document.retourner();
        System.out.println("Disponible apres retour : " + document.isDisponible());
    }
}
