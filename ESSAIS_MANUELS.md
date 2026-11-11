# Fiche des essais manuels cumulés — Jalon 3

**Consigne :** exécuter chaque manipulation et renseigner la colonne « Résultat observé / validation ». Ne pas déclarer un essai réalisé avant de l'avoir exécuté.

Les douze premiers scénarios reprennent les vérifications fonctionnelles introduites aux jalons précédents. Les scénarios suivants portent sur le contrat `equals/hashCode` et le catalogue du Jalon 3.

| N° | Manipulation à effectuer | Résultat attendu | Résultat observé / validation |
|---:|---|---|---|
| 1 | Créer un livre avec un numéro, un titre, un nombre de pages et un auteur. | Le livre est créé et ses informations sont affichées. | À renseigner par le groupe |
| 2 | Créer un livre avec deux auteurs. | Les deux auteurs sont conservés et affichés. | À renseigner par le groupe |
| 3 | Créer un périodique avec un organisme et un numéro de parution. | Les informations éditoriales sont affichées. | À renseigner par le groupe |
| 4 | Afficher `dureePret()` pour un livre puis un périodique. | Les durées propres aux deux sous-types sont retournées. | À renseigner par le groupe |
| 5 | Afficher un livre et un périodique avec `toString()`. | Chaque objet affiche ses informations spécifiques. | À renseigner par le groupe |
| 6 | Manipuler un livre par une référence `Document` et appeler `dureePret()`. | La méthode redéfinie de `Livre` est appelée. | À renseigner par le groupe |
| 7 | Manipuler un périodique par une référence `Document` et appeler `dureePret()`. | La méthode redéfinie de `Periodique` est appelée. | À renseigner par le groupe |
| 8 | Emprunter un document disponible avec un utilisateur valide. | L'emprunt est accepté et le document devient indisponible. | À renseigner par le groupe |
| 9 | Tenter un second emprunt du même document. | L'emprunt est refusé et le premier emprunteur reste enregistré. | À renseigner par le groupe |
| 10 | Retourner un document emprunté. | Le document redevient disponible et l'emprunteur est effacé. | À renseigner par le groupe |
| 11 | Tenter un emprunt avec un utilisateur `null` puis un retour sur un document disponible. | Les opérations sont refusées sans changement d'état. | À renseigner par le groupe |
| 12 | Construire des documents avec des données invalides. | Les invariants existants refusent les constructions invalides. | À renseigner par le groupe |
| 13 | Comparer deux documents ayant le même numéro, puis deux ayant des numéros différents. | Même numéro : égaux ; numéros différents : non égaux. | À renseigner par le groupe |
| 14 | Comparer le `hashCode()` de deux documents égaux. | Les deux codes de hachage sont identiques. | À renseigner par le groupe |
| 15 | Ajouter deux documents de numéros différents dans le catalogue. | Les deux ajouts réussissent. | À renseigner par le groupe |
| 16 | Ajouter un document dont le numéro existe déjà. | L'ajout est refusé et le document initial reste celui qui est enregistré. | À renseigner par le groupe |
| 17 | Rechercher un numéro présent puis un numéro absent. | Le document présent est retrouvé ; l'absence est représentée par `Optional.empty()`. | À renseigner par le groupe |
| 18 | Parcourir les documents du catalogue. | Les documents enregistrés sont accessibles au parcours. | À renseigner par le groupe |
| 19 | Tenter de modifier la liste renvoyée par `parcourir()`. | La modification est refusée et la collection interne reste protégée. | À renseigner par le groupe |

## Vérification automatisée

```bash
javac *.java
java Tests
```

Résultat attendu : **58 vérifications réussies**.

Le nombre indiqué concerne les vérifications automatisées. Les manipulations de cette fiche doivent être exécutées et validées séparément par le groupe.
