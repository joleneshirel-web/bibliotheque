public abstract class Document implements Empruntable, Cloneable {
    private int numero;
    private String titre;
    private String auteur;
    private boolean disponible;
    private boolean estEmprunte;
    private Utilisateur emprunteur;

    public Document(int numero, String titre, String auteur) {
        this.numero = numero;
        this.titre = titre;
        this.auteur = auteur;
        this.disponible = true;
        this.estEmprunte = false;
        this.emprunteur = null;
    }
    public int getNumero() { return numero; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public Utilisateur getEmprunteur() {
        return emprunteur;
    }

    @Override
    public boolean emprunter(Utilisateur u) {
        if (!estEmprunte && u != null) {
            estEmprunte = true;
            disponible = false;
            emprunteur = u;
            return true;
        }
        return false; 
    }

    @Override
    public void retourner() {
        this.estEmprunte = false;
        this.disponible = true;
        this.emprunteur = null;
    }

    @Override
    public boolean estEmprunte() {
        return estEmprunte;
    }

    @Override
    public abstract int dureeMaxPret();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        String info = "Document [N°=" + numero + ", Titre=" + titre + ", Auteur=" + auteur + ", Disponible=" + disponible + "]";
        if (estEmprunte && emprunteur != null) {
            info += " -> Emprunté par : " + emprunteur.getNom();
        }
        return info;
    }
}
