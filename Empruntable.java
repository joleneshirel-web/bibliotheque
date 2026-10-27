
public interface Empruntable {
    boolean emprunter(Utilisateur utilisateur);
    void retourner();
    boolean estEmprunte();
    int dureeMaxPret();
    Utilisateur getEmprunteur();
}
