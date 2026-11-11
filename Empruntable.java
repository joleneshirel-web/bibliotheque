public interface Empruntable {
    boolean emprunter(Utilisateur utilisateur);
    boolean retourner();
    boolean estEmprunte();
    int dureePret();
    Utilisateur getEmprunteur();
}
