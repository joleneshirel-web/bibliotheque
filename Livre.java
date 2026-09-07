public class Livre {

    // Attributs
    private String titre;
    private String auteur;
    private boolean disponible;

    // Constructeur
    public Livre(String titre, String auteur) {
        this.titre = titre;
        this.auteur = auteur; 
        this.disponible = true;
    }

    // Méthode pour emprunter le livre
    public void emprunter() {
        if (disponible) {
            disponible = false;
            System.out.println("Le livre \"" + titre + "\" a été emprunté.");
        } else {
            System.out.println("Le livre \"" + titre + "\" est déjà emprunté.");
        }
    }

    // Méthode pour retourner le livre
    public void retourner() {
        if (!disponible) {
            disponible = true;
            System.out.println("Le livre \"" + titre + "\" a été retourné.");
        } else {
            System.out.println("Le livre \"" + titre + "\" est déjà disponible.");
        }
    }

    // Méthode pour afficher les informations du livre
    public void afficherInformations() {
        System.out.println("Titre : " + titre);
        System.out.println("Auteur : " + auteur);
        System.out.println("Disponible : " + disponible);
    }
}