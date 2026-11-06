# Bibliothèque de prêt — Jalon 2 : Composition et polymorphisme

Groupe 4 — Projet Java de programmation orientée objet.

## 1. Objectif du jalon

Ce jalon porte uniquement sur les choix entre héritage et composition, le type abstrait `Document`, ses sous-types `Livre` et `Periodique`, la composition entre `Livre` et ses auteurs, la redéfinition de `dureePret()` et `toString()`, ainsi que la vérification du principe de substitution. Les interfaces déjà présentes au Jalon 1 sont conservées sans développement supplémentaire. Les collections du framework Java ne sont pas introduites ici.

## 2. Choix d'héritage : `Document`, `Livre` et `Periodique`

`Livre` et `Periodique` héritent de `Document` parce que chacun est un document. Cette relation « est-un » est justifiée : les deux sous-types possèdent un numéro, un titre, un état de disponibilité et peuvent être empruntés puis retournés.

`Document` est abstraite : le modèle ne crée pas de document générique concret. Elle centralise les informations et les opérations communes. Elle déclare également `dureePret()` comme méthode abstraite, car chaque sous-type détermine sa propre durée.

Les règles générales d'emprunt et de retour restent dans `Document`. Nous ne créons pas de sous-classes artificielles pour représenter les règles de prêt : les conditions et les changements d'état sont communs aux deux types de documents. Seule la durée varie et relève du polymorphisme.

- `Livre.dureePret()` retourne 21 jours.
- `Periodique.dureePret()` retourne 7 jours.

## 3. Choix de composition : `Livre` et `Auteur`

Un livre possède un ou plusieurs auteurs. Cette relation est modélisée par composition : le constructeur de `Livre` reçoit les noms des auteurs et crée les objets `Auteur` qui constituent ses informations propres. Le modèle ne reçoit pas un tableau d'objets `Auteur` créé ailleurs.

Le tableau `Auteur[]` est utilisé volontairement pour représenter les auteurs à ce jalon. `getAuteurs()` renvoie une copie du tableau afin d'éviter qu'un appelant puisse remplacer directement les éléments du tableau interne. La classe `Auteur` est immuable : son nom est `final` et aucun mutateur n'est fourni.

Cette solution n'introduit pas `ArrayList`, `HashSet`, `Map` ou d'autres collections du framework Java ; leur étude est laissée au jalon consacré aux interfaces et collections.

Un périodique n'est pas composé d'auteurs de livre dans ce modèle : il possède un `organisme` de rédaction et un `numeroParution`, qui sont ses informations éditoriales propres.

## 4. Polymorphisme sans test de type dans le client

`Livre` et `Periodique` redéfinissent toutes les deux `dureePret()` et `toString()`. Le client peut manipuler les objets au moyen de références `Document` : la méthode correspondant au type réel de l'objet est appelée automatiquement.

Exemple présent dans `Main.java` :

```java
Document[] documents = {
    new Livre(1, "Maimouna", 200, "Abdoulaye Sadji"),
    new Periodique(2, "Journal scientifique", "Redaction 2iE", 5)
};

for (Document document : documents) {
    System.out.println(document);
    System.out.println(document.dureePret());
}
```

Le client ne contient aucun `instanceof` ni branchement `if` pour distinguer les sous-types.

## 5. Principe de substitution et contrats

Une référence `Document` peut désigner un `Livre` ou un `Periodique`. Les sous-types conservent le contrat d'emprunt et de retour défini par `Document` ; ils ne renforcent pas les préconditions et ne diminuent pas les garanties.

**Préconditions de l'emprunt :** le document doit être disponible et l'utilisateur doit être non nul. Si ces conditions ne sont pas satisfaites, la méthode renvoie `false` et ne modifie pas l'état.

**Postconditions d'un emprunt réussi :** la méthode renvoie `true`, le document devient indisponible et l'utilisateur est enregistré comme emprunteur.

**Postconditions d'un retour réussi :** la méthode renvoie `true`, le document redevient disponible et l'emprunteur est remis à `null`.

Les deux essais obligatoires de substitution sont dans `Tests.java` : un `Livre` puis un `Periodique` sont manipulés au moyen d'une référence de type `Document`.

## 6. Invariants du modèle

- Le numéro d'un document est strictement positif.
- Le titre d'un document n'est ni `null` ni vide.
- Le nombre de pages d'un livre est strictement positif.
- Un livre possède au moins un auteur ; le nom de chaque auteur n'est ni `null` ni vide.
- L'organisme d'un périodique n'est ni `null` ni vide.
- Le numéro de parution d'un périodique est strictement positif.
- Un document disponible n'a pas d'emprunteur.
- Un emprunt accepté rend le document indisponible et renseigne son emprunteur.
- Un retour accepté rend le document disponible et efface son emprunteur.

## 7. Classes du projet

- `Document.java` : classe abstraite, état commun, emprunt/retour et méthode abstraite `dureePret()`.
- `Livre.java` : sous-type de `Document`, nombre de pages, composition avec `Auteur`, redéfinitions.
- `Periodique.java` : sous-type de `Document`, organisme, numéro de parution et redéfinitions.
- `Auteur.java` : valeur représentant le nom d'un auteur.
- `Utilisateur.java` : informations et validations de l'emprunteur.
- `Empruntable.java` et `Pretable.java` : interfaces conservées du Jalon 1 ; leur présence n'est pas une nouvelle fonctionnalité du Jalon 2.
- `Main.java` : exemple client illustrant le polymorphisme sans test de type.
- `Tests.java` : 43 vérifications automatisées.
- `ESSAIS_MANUELS.md` : 12 scénarios manuels cumulés à exécuter et à valider par le groupe.
- `Diagramme_Jalon2.puml` : diagramme de classes PlantUML.

## 8. Compilation et exécution

Avec un JDK installé, ouvrir un terminal dans le dossier du projet puis exécuter :

```bash
javac *.java
java Tests
java Main
```

La suite de tests actuelle doit afficher : `43 tests reussis sur 43`.

Les essais manuels ne doivent être déclarés réalisés qu'après exécution par les membres du groupe et renseignement de la fiche `ESSAIS_MANUELS.md`.

## 9. Périmètre respecté

Le projet se limite aux objectifs du Jalon 2 : composition, héritage, polymorphisme et substitution. Il ne met pas en place de gestionnaire de bibliothèque ni de collections Java du framework, afin de réserver ces notions au jalon ultérieur.
