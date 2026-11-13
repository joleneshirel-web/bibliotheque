Bibliothèque de prêt — Jalon 3 : Interfaces et collections

Groupe 4 – Projet Java de programmation orientée objet.

---

1. Objectif du jalon

Le Jalon 3 ajoute les éléments demandés dans le sujet :

- le contrat "CatalogueDocuments" ;
- une implémentation du catalogue en mémoire avec une collection adaptée ;
- l'ajout, la recherche par numéro et le parcours des documents ;
- l'encapsulation de la collection interne ;
- les méthodes "equals()" et "hashCode()" dans "Document" ;
- les essais cumulés des jalons précédents et du Jalon 3.

"ServicePret" n'est pas introduit : il est prévu au Jalon 4.

---

2. Comparaison des collections

Dans ce jalon, nous avons étudié trois structures principales du framework Java : "List", "Set" et "Map".

Structure| Unicité| Ordre| Recherche par numéro
"List"| N'accepte pas automatiquement les identifiants uniques| Conserve l'ordre d'insertion| Parcours généralement nécessaire : O(n)
"Set"| Unicité selon "equals()" et "hashCode()"| Dépend de l'implémentation| Pas d'accès direct par numéro
"Map"| Les clés sont uniques| Dépend de l'implémentation| Accès direct à partir de la clé

Le catalogue utilise une "HashMap<Integer, Document>". Le numéro immuable du document sert de clé. Ce choix répond directement au besoin d'identification unique et de recherche par numéro.

L'ordre de parcours n'est pas garanti par "HashMap" et notre projet ne dépend pas d'un ordre particulier.

---

3. Contrat "CatalogueDocuments"

Nous avons créé l'interface "CatalogueDocuments" afin de définir les opérations nécessaires au fonctionnement du catalogue, sans imposer la structure de données utilisée pour les réaliser.

L'interface expose trois méthodes :

- "ajouter(Document document)" : ajoute un document et renvoie "false" si son numéro existe déjà.
- "rechercherParNumero(int numero)" : renvoie un "Optional<Document>", vide si aucun document ne correspond au numéro recherché.
- "parcourir()" : renvoie une liste des documents du catalogue sans donner directement accès à la collection interne.

Ce choix permet de séparer le contrat de son implémentation. Le programme peut ainsi utiliser le catalogue à travers l'interface "CatalogueDocuments", sans dépendre directement de la classe "CatalogueDocumentsMemoire".

---

4. Implémentation "CatalogueDocumentsMemoire"

La classe "CatalogueDocumentsMemoire" implémente l'interface "CatalogueDocuments".

Nous avons choisi une "HashMap<Integer, Document>" pour stocker les documents en mémoire.

La clé correspond au numéro du document et la valeur correspond à l'objet "Document" associé.

4.1. Ajout d'un document

La méthode "ajouter()" vérifie d'abord que le document reçu n'est pas "null".

Elle récupère ensuite son numéro et vérifie si celui-ci est déjà présent dans la "HashMap".

- Si le numéro existe déjà, la méthode renvoie "false".
- Sinon, le document est ajouté et la méthode renvoie "true".

Cette vérification permet d'éviter d'enregistrer deux documents portant le même numéro.

4.2. Recherche par numéro

La méthode "rechercherParNumero()" vérifie que le numéro fourni est strictement positif.

Elle utilise ensuite la méthode "get()" de la "HashMap" pour retrouver le document correspondant.

Le résultat est retourné sous forme d'un "Optional<Document>". Cela permet de représenter clairement le cas où aucun document n'est trouvé, sans retourner directement une valeur "null".

4.3. Parcours des documents

La méthode "parcourir()" permet de consulter les documents enregistrés dans le catalogue.

Elle construit une nouvelle "ArrayList" à partir des valeurs de la "HashMap", puis applique "Collections.unmodifiableList()".

La liste retournée ne peut donc pas être modifiée par l'appelant. La collection interne du catalogue reste privée et protégée.

---

5. Gestion de l'égalité : "equals()" et "hashCode()"

Dans la classe abstraite "Document", nous avons redéfini les méthodes "equals()" et "hashCode()".

Nous avons choisi de considérer que deux documents sont égaux lorsqu'ils possèdent le même numéro.

La méthode "equals()" compare donc les numéros des deux documents. La méthode "hashCode()" calcule le code de hachage à partir de ce même numéro.

Le numéro étant déclaré "final", il ne peut pas être modifié après la création du document.

Les deux méthodes respectent ainsi la règle suivante : deux objets considérés comme égaux doivent posséder le même code de hachage.

Nous avons également déclaré ces méthodes "final" afin que les sous-classes "Livre" et "Periodique" conservent la même règle d'égalité.

Cette règle permet d'identifier les documents indépendamment de leur type concret.

---

6. Encapsulation de la collection interne

Nous avons déclaré la "HashMap" comme attribut "private final" dans "CatalogueDocumentsMemoire".

Ce choix empêche les autres classes d'accéder directement à la collection utilisée pour stocker les documents.

La méthode "parcourir()" ne retourne pas la "HashMap" elle-même. Elle retourne une copie de ses valeurs sous forme de liste non modifiable.

Cela permet aux autres parties du programme de consulter les documents sans pouvoir modifier directement la structure interne du catalogue.

L'encapsulation garantit ainsi que les opérations d'ajout et de recherche restent contrôlées par les méthodes prévues dans l'interface.

---

7. Complexité des principales opérations

Avec notre choix d'une "HashMap", les principales opérations possèdent les complexités moyennes suivantes :

Opération| Complexité moyenne
Vérification d'un numéro avec "containsKey()"| O(1)
Ajout avec "put()"| O(1)
Recherche avec "get()"| O(1)
Parcours de tous les documents| O(n)

Les opérations de recherche, de vérification et d'ajout ont une complexité moyenne en O(1), car la "HashMap" utilise le hachage pour accéder aux éléments à partir de leur clé.

Le parcours possède une complexité en O(n), car il faut examiner l'ensemble des documents enregistrés.

Ces complexités sont des moyennes et peuvent varier selon les collisions et le fonctionnement interne de la "HashMap".

---

8. Essais et vérifications

Nous avons conservé les essais réalisés lors des jalons précédents et ajouté les vérifications nécessaires pour le Jalon 3.

Les vérifications portent notamment sur :

- l'égalité de deux documents ayant le même numéro ;
- la cohérence entre "equals()" et "hashCode()" ;
- l'ajout d'un document au catalogue ;
- le refus d'un document dont le numéro existe déjà ;
- la recherche d'un document existant ;
- la recherche d'un document inexistant ;
- le refus d'un numéro invalide ;
- le refus de l'ajout d'un document "null" ;
- le parcours des documents ;
- la protection de la liste retournée.

Ces essais permettent de vérifier que les nouvelles fonctionnalités fonctionnent conformément aux règles définies dans notre projet.

Les résultats des essais manuels sont présentés dans le fichier "ESSAIS_MANUELS.md" et dans la classe de tests du projet.

---

9. Diagramme de classes

Le diagramme de classes du Jalon 3 présente l'organisation du projet après l'ajout du catalogue.

Il représente notamment :

- l'interface "CatalogueDocuments" ;
- la classe "CatalogueDocumentsMemoire" qui implémente cette interface ;
- l'utilisation de "Document" dans le catalogue ;
- la hiérarchie entre "Document", "Livre" et "Periodique" ;
- les autres relations déjà présentes dans le projet.

Le diagramme est fourni sous deux formats :

- "Diagramme_Jalon3.puml" : fichier source PlantUML ;
- "Diagramme_Jalon3.png" : représentation du diagramme sous forme d'image.

---

10. Exécution du projet

Le projet peut être compilé et exécuté depuis un terminal à partir du dossier contenant les fichiers Java.

Compilation :

javac *.java

Exécution du programme principal :

java Main

Pour lancer les essais, il faut exécuter la classe de tests correspondante.

---

Conclusion

Ce troisième jalon nous a permis de mieux comprendre l'utilisation des interfaces et des collections en Java.

Nous avons appris à séparer le contrat d'une fonctionnalité de son implémentation et à choisir une structure de données adaptée aux besoins de notre application.

Grâce à la mise en place du catalogue, nous pouvons maintenant ajouter des documents, les rechercher à partir de leur numéro et parcourir les éléments enregistrés tout en protégeant la collection interne.

Nous avons également défini une règle d'égalité commune à tous les documents avec les méthodes "equals()" et "hashCode()".

Ce travail nous permet de faire évoluer progressivement notre bibliothèque de prêt tout en respectant les objectifs fixés pour chaque jalon.

---
