public interface Empruntable {
    boolean emprunter(Utilisateur u);
    void retourner();
    boolean estEmprunte();
    int dureeMaxPret();
    Utilisateur getEmprunteur();
}
