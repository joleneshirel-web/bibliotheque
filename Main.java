public class Main {
    public static void main(String[] args) {
        CatalogueDocuments catalogue = new CatalogueDocumentsMemoire();

        Document livre = new Livre(1, "Maimouna", 200, "Abdoulaye Sadji");
        Document periodique = new Periodique(2, "Journal scientifique", "Redaction 2iE", 45);

        System.out.println("Ajout du livre : " + catalogue.ajouter(livre));
        System.out.println("Ajout du periodique : " + catalogue.ajouter(periodique));

        System.out.println("\nRecherche du document numero 1 :");
        System.out.println(catalogue.rechercherParNumero(1).orElse(null));

        System.out.println("\nParcours du catalogue :");
        for (Document document : catalogue.parcourir()) {
            System.out.println(document);
        }
    }
}
