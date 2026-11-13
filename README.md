Projet POO – Bibliothèque de prêt

Présentation du projet

Dans le cadre de notre projet de programmation orientée objet (POO), nous avons réalisé une application de gestion d'une bibliothèque de prêt en Java.

L'objectif est de pouvoir gérer différents types de documents, notamment les livres et les périodiques, ainsi que de consulter les documents disponibles dans notre catalogue.

Le projet est développé en Java et fonctionne en mode console. Les données sont conservées en mémoire pendant l'exécution du programme.

Organisation du projet

Notre projet est composé de plusieurs classes qui permettent de répartir les différentes responsabilités :

- Document : classe abstraite qui représente les informations communes aux documents.
- Livre : représente un livre avec son nombre de pages et ses auteurs.
- Periodique : représente un périodique avec son organisme de rédaction et son numéro de parution.
- Auteur : représente l'auteur d'un livre.
- Utilisateur : représente un adhérent de la bibliothèque.
- Pretable : interface qui définit les opérations liées au prêt.
- CatalogueDocuments : interface qui définit les opérations accessibles sur le catalogue.
- CatalogueDocumentsMemoire : classe qui implémente le catalogue en utilisant une "HashMap".

Travail réalisé – Jalon 3

Pour ce troisième jalon, nous avons travaillé sur les interfaces et les collections Java.

Nous avons créé l'interface "CatalogueDocuments" afin de séparer le fonctionnement du catalogue de son implémentation. Cela permet d'utiliser le catalogue sans dépendre directement de la structure de données choisie.

Nous avons ensuite créé la classe "CatalogueDocumentsMemoire", qui utilise une "HashMap" pour stocker les documents. Chaque document est associé à son numéro, ce qui permet de le retrouver facilement.

Le catalogue permet :

- d'ajouter un document ;
- de refuser l'ajout d'un document dont le numéro existe déjà ;
- de rechercher un document à partir de son numéro ;
- de parcourir les documents sans donner accès directement à la collection interne.

Nous avons également défini les méthodes "equals()" et "hashCode()" dans la classe "Document". Elles permettent de considérer deux documents comme égaux lorsqu'ils possèdent le même numéro.

Choix des collections

Nous avons étudié trois collections Java :

- List : permet de conserver plusieurs éléments dans un ordre précis et d'accepter les doublons.
- Set : permet de stocker des éléments sans doublons.
- Map : permet d'associer une clé à une valeur.

Pour notre catalogue, nous avons choisi une "HashMap<Integer, Document>". La clé correspond au numéro du document et la valeur correspond au document lui-même.

Ce choix facilite la recherche par numéro et permet de vérifier rapidement si un numéro est déjà utilisé.

Complexité des opérations

Avec notre utilisation de "HashMap", les principales opérations ont les complexités moyennes suivantes :

Opération| Complexité moyenne
Recherche par numéro| O(1)
Ajout d'un document| O(1)
Vérification d'un numéro existant| O(1)
Parcours du catalogue| O(n)

Ces complexités sont des moyennes : dans certains cas, les opérations sur une "HashMap" peuvent être plus coûteuses.

Tests

Nous avons réalisé des essais manuels pour vérifier le fonctionnement des différentes parties du projet.

Les tests du Jalon 3 portent notamment sur :

- l'égalité entre deux documents ayant le même numéro ;
- la cohérence entre "equals()" et "hashCode()" ;
- l'ajout d'un document dans le catalogue ;
- le refus des doublons ;
- la recherche d'un document existant ou inexistant ;
- le refus des valeurs invalides ;
- le parcours du catalogue et la protection de sa collection interne.

Exécution du projet

Le projet peut être compilé et exécuté depuis un terminal avec les commandes suivantes :

javac *.java
java Main

Les essais peuvent être lancés avec la classe de tests correspondante.

Conclusion

Ce troisième jalon nous a permis de mieux comprendre l'utilisation des interfaces et des collections en Java. Nous avons appris à séparer le contrat d'une classe de son implémentation et à choisir une structure de données adaptée aux besoins de notre application.

Notre projet évolue progressivement en respectant les objectifs fixés pour chaque jalon.

