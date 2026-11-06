# Fiche des essais manuels cumules — Jalon 2

**Important :** cette fiche est un protocole d'essais. Le groupe doit executer les manipulations et renseigner la colonne « Resultat observe » avant de declarer les essais realises. Ne pas cocher un essai qui n'a pas ete execute.

Les scenarios reprennent les comportements de base verifies au Jalon 1 et ajoutent les essais demandes au Jalon 2. Les essais 6 et 7 sont les deux essais de substitution explicites via une reference `Document`.

| N° | Manipulation a effectuer | Resultat attendu | Resultat observe / validation |
|---:|---|---|---|
| 1 | Creer un `Livre` avec un numero, un titre, un nombre de pages et un auteur. Afficher l'objet. | Le livre est cree ; ses informations et son auteur apparaissent. | A renseigner par le groupe |
| 2 | Creer un `Livre` avec deux noms d'auteurs. | Les deux auteurs sont conserves et affiches dans l'ordre. | A renseigner par le groupe |
| 3 | Creer un `Periodique` avec un organisme et un numero de parution. | Le periodique affiche ses informations editoriales propres. | A renseigner par le groupe |
| 4 | Afficher `dureePret()` pour un livre puis pour un periodique. | Le livre retourne 21 jours ; le periodique retourne 7 jours. | A renseigner par le groupe |
| 5 | Afficher les deux sous-types avec `toString()`. | Chaque affichage contient les informations specifiques de son type concret. | A renseigner par le groupe |
| 6 | **Substitution 1 :** declarer `Document d = new Livre(...)`, puis appeler `d.dureePret()` et `d.toString()`. | Les implementations de `Livre` sont executees sans `instanceof` ni test de type dans le client. | A renseigner par le groupe |
| 7 | **Substitution 2 :** declarer `Document d = new Periodique(...)`, puis appeler `d.dureePret()` et `d.toString()`. | Les implementations de `Periodique` sont executees sans `instanceof` ni test de type dans le client. | A renseigner par le groupe |
| 8 | Emprunter un document disponible avec un utilisateur valide. | L'emprunt reussit ; le document devient indisponible et l'utilisateur est enregistre. | A renseigner par le groupe |
| 9 | Tenter un second emprunt sur le meme document. | L'emprunt est refuse ; le premier emprunteur reste enregistre. | A renseigner par le groupe |
| 10 | Retourner un document emprunte, puis consulter son etat. | Le retour reussit ; le document redevient disponible et l'emprunteur est efface. | A renseigner par le groupe |
| 11 | Tenter un emprunt avec un utilisateur `null`, puis un retour sur un document deja disponible. | Les operations sont refusees et l'etat du document ne change pas. | A renseigner par le groupe |
| 12 | Tenter de creer un livre sans auteur ou avec un nom d'auteur vide ; tenter un periodique avec un numero de parution invalide. | Les constructions invalides declenchent `IllegalArgumentException`. | A renseigner par le groupe |

## Execution des verifications automatisees

Dans le dossier contenant les fichiers Java :

```bash
javac *.java
java Tests
```

La suite automatisee actuelle comporte 43 assertions. Elle complete les essais manuels ; elle ne remplace pas la manipulation et la validation des scenarios ci-dessus.

## Contrats observes pour la substitution

- Un emprunt n'est accepte que si le document est disponible et si l'utilisateur n'est pas `null`.
- En cas de refus, l'etat du document et l'emprunteur deja enregistre restent inchanges.
- Apres un emprunt accepte, le document est indisponible et l'utilisateur est son emprunteur.
- Apres un retour accepte, le document est disponible et l'emprunteur vaut `null`.
- `Livre` et `Periodique` ne redefinissent pas `emprunter()` ni `retourner()` : ils conservent donc le meme contrat que `Document`.
