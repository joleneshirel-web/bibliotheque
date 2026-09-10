public abstract class Document {
    private int numero;
    private String titre;
    private String auteur; // Composition / Association
    private boolean disponible;

    public Document(int numero, String titre, String auteur) {
        this.numero = numero;
        this.titre = titre;
        this.auteur = auteur;
        this.disponible = true;
    }

    public int getNumero() {
        return numero;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // Méthode abstraite que les classes filles devront obligatoirement redéfinir (Polymorphisme)
    public abstract int dureeMaxPret();

    // Redéfinition de toString
    @Override
    public String toString() {
        return "Document [N°=" + numero + ", Titre=" + titre + ", Auteur=" + auteur + ", Disponible=" + disponible + "]";
    }
}
