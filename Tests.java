public class Tests {
    private static int total = 0;
    private static int reussis = 0;

    public static void main(String[] args) {
        Utilisateur jolene = new Utilisateur(20240007, "Jolène Shirel ZIO", "jolene.zio@example.com");
        Utilisateur sidi = new Utilisateur(20240008, "Sidi KARGOUGOU", "sidi.kargougou@example.com");
        Utilisateur murielle = new Utilisateur(20240009, "Murielle OUEDRAOGO", "murielle.ouedraogo@example.com");

        Document livre = new Livre(1, "Maimouna", "Abdoulaye Sadji", 200);
        Document periodique = new Periodique(3, "Journal Scientifique", "Rédaction 2iE", 45);

        System.out.println("Structure");
        verifier("Document est abstraite", java.lang.reflect.Modifier.isAbstract(Document.class.getModifiers()));
        verifier("Livre est un Pretable", Pretable.class.isAssignableFrom(Livre.class));
        verifier("Periodique est un Pretable", Pretable.class.isAssignableFrom(Periodique.class));
        verifier("Une référence Document désigne un Livre", livre instanceof Livre);

        System.out.println("Durées de prêt");
        verifier("Un livre se prête 21 jours", livre.dureeMaxPret() == 21);
        verifier("Un périodique se prête 7 jours", periodique.dureeMaxPret() == 7);
        Pretable e = livre;
        verifier("La durée est correcte via Pretable", e.dureeMaxPret() == 21);

        System.out.println("État initial");
        verifier("Un document neuf est disponible", livre.isDisponible());
        verifier("Un document neuf n'est pas emprunté", !livre.estEmprunte());
        verifier("Un document neuf n'a pas d'emprunteur", livre.getEmprunteur() == null);

        System.out.println("Emprunt accepté");
        verifier("Jolène ZIO emprunte Maimouna", livre.emprunter(jolene));
        verifier("Le livre n'est plus disponible", !livre.isDisponible());
        verifier("Le livre est marqué emprunté", livre.estEmprunte());
        verifier("L'emprunteur est Jolène ZIO", livre.getEmprunteur() == jolene);

        System.out.println("Emprunt refusé");
        verifier("Sidi KARGOUGOU ne peut pas emprunter le même livre", !livre.emprunter(sidi));
        verifier("L'emprunteur reste Jolène ZIO", livre.getEmprunteur() == jolene);
        verifier("Un emprunt sans adhérent est refusé", !periodique.emprunter(null));
        verifier("Le périodique reste disponible", periodique.isDisponible());

        System.out.println("Retour");
        verifier("Le retour du livre emprunté est accepté", livre.retourner());
        verifier("Le livre est de nouveau disponible", livre.isDisponible());
        verifier("Le livre n'a plus d'emprunteur", livre.getEmprunteur() == null);
        verifier("Un second retour est refusé", !livre.retourner());
        verifier("Le retour d'un document jamais emprunté est refusé", !periodique.retourner());
        verifier("Sidi KARGOUGOU emprunte après le retour", livre.emprunter(sidi));
        verifier("Le nouvel emprunteur est Sidi KARGOUGOU", livre.getEmprunteur() == sidi);
        livre.retourner();

        System.out.println("Clonage");
        try {
            Document original = new Livre(2, "Une si longue lettre", "Mariama Bâ", 131);
            original.emprunter(murielle);
            Document copie = original.clone();
            verifier("La copie est un objet distinct", copie != original);
            verifier("La copie garde le même type réel", copie instanceof Livre);
            verifier("La copie reprend le numéro et le titre", copie.getNumero() == 2 && copie.getTitre().equals("Une si longue lettre"));
            verifier("La copie reprend l'état emprunté", copie.estEmprunte());
            verifier("La copie reprend l'emprunteur", copie.getEmprunteur() == murielle);
            original.retourner();
            verifier("Le retour de l'original ne touche pas la copie", copie.estEmprunte() && original.isDisponible());
            Document copieLibre = periodique.clone();
            verifier("Emprunter la copie réussit", copieLibre.emprunter(jolene));
            verifier("L'original du périodique reste disponible", periodique.isDisponible());
        } catch (CloneNotSupportedException ex) {
            verifier("Le clonage doit être possible", false);
        }
        System.out.println("Validations des constructeurs");
        verifier("Numéro nul refusé", constructionRefusee(1));
        verifier("Titre vide refusé", constructionRefusee(2));
        verifier("Auteur absent refusé", constructionRefusee(3));
        verifier("Nombre de pages nul refusé", constructionRefusee(4));
        verifier("Numéro de parution négatif refusé", constructionRefusee(5));
        verifier("Identifiant d'adhérent nul refusé", constructionRefusee(6));
        verifier("Nom d'adhérent vide refusé", constructionRefusee(7));
        verifier("Courriel sans arobase refusé", constructionRefusee(8));

        System.out.println();
        System.out.println("Résultat : " + reussis + " tests réussis sur " + total);
        if (reussis != total) {
            System.exit(1);
        }
    }

    private static boolean constructionRefusee(int cas) {
        try {
            switch (cas) {
                case 1:
                    new Livre(0, "Maimouna", "Abdoulaye Sadji", 200);
                    break;
                case 2:
                    new Livre(1, "   ", "Abdoulaye Sadji", 200);
                    break;
                case 3:
                    new Periodique(3, "Journal Scientifique", null, 45);
                    break;
                case 4:
                    new Livre(1, "Maimouna", "Abdoulaye Sadji", 0);
                    break;
                case 5:
                    new Periodique(3, "Journal Scientifique", "Rédaction 2iE", -1);
                    break;
                case 6:
                    new Utilisateur(0, "Murielle OUEDRAOGO", "murielle.ouedraogo@example.com");
                    break;
                case 7:
                    new Utilisateur(20240009, "", "murielle.ouedraogo@example.com");
                    break;
                default:
                    new Utilisateur(20240009, "Murielle OUEDRAOGO", "murielle.ouedraogo");
                    break;
            }
        } catch (IllegalArgumentException ex) {
            return true;
        }
        return false;
    }

    private static void verifier(String description, boolean condition) {
        total++;
        if (condition) {
            reussis++;
        }
        System.out.println((condition ? "  REUSSI  " : "  ECHEC   ") + description);
    }
}
