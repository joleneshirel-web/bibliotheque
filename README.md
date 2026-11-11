# Bibliothèque de prêt — Jalon 3 : Interfaces et collections

Groupe 4 — Projet Java de programmation orientée objet.

## 1. Objectif du jalon

Le Jalon 3 ajoute uniquement les éléments demandés dans le sujet :
- le contrat `CatalogueDocuments` ;
- une implémentation du catalogue en mémoire avec une collection adaptée ;
- l'ajout, la recherche par numéro et le parcours des documents ;
- l'encapsulation de la collection interne ;
- `equals()` et `hashCode()` dans `Document` ;
- les essais cumulés des jalons précédents et du Jalon 3.

`ServicePret` n'est pas introduit : il est prévu au Jalon 4.

## 2. Comparaison des collections

| Structure | Unicité | Ordre | Recherche par numéro |
|---|---|---|---|
| `List` | N'accepte pas automatiquement les identifiants uniques | Conserve l'ordre | Parcours généralement nécessaire : O(n) |
| `Set` | Unicité selon `equals()` et `hashCode()` | Dépend de l'implémentation | Pas d'accès direct par numéro |
| `Map` | Les clés sont uniques | Dépend de l'implémentation | Accès direct à partir de la clé |

Le catalogue utilise une `HashMap<Integer, Document>`. Le numéro immuable du document sert de clé. Ce choix répond directement au besoin d'identification unique et de recherche par numéro. L'ordre de parcours n'est pas garanti par `HashMap` et le projet ne dépend pas d'un ordre particulier.

## 3. Contrat `CatalogueDocuments`

L'interface expose seulement les opérations nécessaires au jalon :
- `ajouter(Document document)` : ajoute le document et renvoie `false` si le numéro existe déjà ;
- `rechercherParNumero(int numero)` : renvoie un `Optional<Document>`, vide si le numéro est absent ;
- `parcourir()` : renvoie les documents pour permettre leur parcours.

Le client utilise le type `CatalogueDocuments`, et non la classe concrète `CatalogueDocumentsMemoire`. L'interface isole donc le code client du choix de l'implémentation.

## 4. Encapsulation et invariants du catalogue

La collection est stockée dans l'attribut privé `documents`. Elle n'est jamais retournée directement.

`parcourir()` construit une copie des valeurs puis renvoie une liste non modifiable. Modifier cette liste ne permet donc pas de modifier la collection interne.

- Un document `null` ne peut pas être ajouté.
- Un numéro déjà présent ne peut pas être ajouté une deuxième fois.
- Un numéro de recherche doit être strictement positif.
- Une recherche sans résultat renvoie `Optional.empty()`.

Aucune opération de suppression ou de gestion des prêts n'est ajoutée à ce catalogue : elles ne font pas partie des opérations demandées dans le périmètre du Jalon 3.

## 5. `equals()` et `hashCode()`

Dans `Document`, deux documents sont égaux lorsqu'ils possèdent le même numéro. Le numéro est un identifiant positif et immuable ; l'égalité ne dépend donc ni du titre, ni du type concret, ni de la disponibilité.

`hashCode()` utilise ce même numéro. Deux documents égaux ont ainsi le même code de hachage, et celui-ci reste stable pendant la vie de l'objet. Les deux méthodes sont `final` afin que les sous-types ne puissent pas modifier cette règle.

Ces méthodes sont définies et vérifiées avant l'utilisation de la collection de hachage du catalogue.

## 6. Complexité des opérations principales

Pour `HashMap`, les coûts usuels moyens sont :
- ajout par numéro : O(1) en moyenne ;
- recherche par numéro : O(1) en moyenne ;
- parcours de tous les documents : O(n).

Ces complexités sont moyennes ; en cas de collisions défavorables, une opération individuelle peut coûter davantage.

## 7. Diagramme de classes

Les deux fichiers du diagramme sont fournis :
- `Diagramme_Jalon3.puml` : source PlantUML ;
- `Diagramme_Jalon3.png` : image du diagramme de classes.

Le diagramme représente l'interface du catalogue, son implémentation mémoire et les relations avec le modèle issu des jalons précédents.

## 8. Essais

`Tests.java` conserve les vérifications automatisées du Jalon 2 et ajoute les vérifications du Jalon 3. Les essais ajoutés vérifient :
- l'égalité de deux documents portant le même numéro ;
- la cohérence et la stabilité de `hashCode()` ;
- l'ajout et le refus des doublons ;
- la recherche d'un document présent et d'un numéro absent ;
- le parcours sans exposition de la collection interne ;
- le refus des données invalides.

La suite contient **58 vérifications automatisées cumulées**. Le sujet demande au minimum douze essais cumulés ; ce seuil est donc dépassé.

`ESSAIS_MANUELS.md` fournit également les manipulations à exécuter et à valider par le groupe. Les résultats manuels doivent être renseignés après exécution réelle.

## 9. Compilation et exécution

Depuis le dossier contenant les fichiers Java :

```bash
javac *.java
java Tests
java Main
```

Aucune bibliothèque externe n'est nécessaire.

## 10. Limites volontaires du jalon

Ce projet n'introduit pas :
- `ServicePret` ;
- les exceptions métier dédiées ;
- les tests JUnit ;
- une base de données, un réseau ou une interface graphique.

Ces éléments ne sont pas demandés au Jalon 3. Les exceptions métier, l'atomicité des opérations de prêt et JUnit seront traités au Jalon 4.
