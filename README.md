BIBLIOTHÈQUE DE PRÊT

JALON 3 : INTERFACES ET COLLECTIONS

Groupe 4 — Projet Java de programmation orientée objet

1. Objectif du jalon

Dans ce troisième jalon de notre projet de bibliothèque de prêt, nous avons travaillé sur les interfaces et les collections en Java.

Notre objectif était de mettre en place un catalogue permettant de stocker les documents, de les ajouter, de les rechercher à partir de leur numéro et de parcourir l'ensemble des documents enregistrés.

Pour réaliser ce travail, nous avons ajouté les éléments suivants :

- L'interface "CatalogueDocuments" ;
- La classe "CatalogueDocumentsMemoire", qui représente notre implémentation du catalogue en mémoire ;
- Les méthodes permettant d'ajouter, de rechercher et de parcourir les documents ;
- Les méthodes "equals()" et "hashCode()" dans la classe "Document" ;
- Les tests permettant de vérifier le bon fonctionnement de l'ensemble.

Nous avons également conservé les fonctionnalités et les tests réalisés pendant les jalons précédents.

À noter que nous n'avons pas encore introduit la classe "ServicePret", car celle-ci est prévue pour le Jalon 4.

2. Choix de la collection utilisée

Avant de choisir la collection à utiliser pour notre catalogue, nous avons étudié les principales collections disponibles en Java : "List", "Set" et "Map".

Voici une comparaison de leurs caractéristiques :

Collection| Fonctionnement
"List"| Permet de stocker plusieurs éléments et conserve leur ordre d'insertion. Les doublons sont autorisés.
"Set"| Évite les doublons en se basant sur "equals()" et "hashCode()".
"Map"| Stocke les éléments sous forme de paires clé-valeur. Chaque clé doit être unique.

Après cette comparaison, nous avons choisi d'utiliser une "HashMap<Integer, Document>" pour notre catalogue.

Ce choix s'explique par le fait que chaque document possède un numéro unique. Nous avons donc utilisé ce numéro comme clé et le document correspondant comme valeur.

Cela nous permet de retrouver directement un document à partir de son numéro, sans devoir parcourir toute la collection.

Nous avons également retenu cette solution parce qu'elle permet d'ajouter des documents tout en évitant les doublons de numéros.

La "HashMap" ne garantit pas un ordre particulier lors du parcours, mais cela ne pose pas de problème pour notre projet, puisque l'ordre des documents n'est pas une exigence du jalon.

3. Mise en place de l'interface "CatalogueDocuments"

Nous avons créé une interface appelée "CatalogueDocuments" afin de définir les opérations que notre catalogue doit obligatoirement proposer.

Cette interface contient trois méthodes principales :

- "ajouter(Document document)" : permet d'ajouter un document au catalogue. La méthode renvoie "false" si un document portant déjà le même numéro existe.
- "rechercherParNumero(int numero)" : permet de rechercher un document à partir de son numéro. Elle renvoie un "Optional<Document>" pour représenter aussi bien un résultat trouvé qu'une absence de résultat.
- "parcourir()" : permet de récupérer les documents du catalogue afin de pouvoir les parcourir.

Nous avons choisi de faire passer les opérations par une interface plutôt que de dépendre directement de notre classe d'implémentation.

Ainsi, le reste du programme peut utiliser le type "CatalogueDocuments" sans avoir besoin de connaître les détails de fonctionnement de "CatalogueDocumentsMemoire".

4. Implémentation et encapsulation du catalogue

Nous avons ensuite créé la classe "CatalogueDocumentsMemoire", qui implémente l'interface "CatalogueDocuments".

Dans cette classe, nous avons utilisé une "HashMap<Integer, Document>" pour stocker les documents.

Nous avons déclaré cette collection avec l'attribut "private", afin qu'elle ne puisse pas être modifiée directement depuis les autres classes.

Nous avons également pris plusieurs précautions pour protéger les données du catalogue :

- Nous refusons l'ajout d'un document dont la valeur est "null".
- Nous empêchons l'ajout de deux documents possédant le même numéro.
- Nous vérifions que le numéro fourni lors d'une recherche est strictement positif.
- Lorsqu'un document n'est pas trouvé, nous renvoyons "Optional.empty()".

Pour la méthode "parcourir()", nous avons choisi de ne pas retourner directement notre collection interne.

À la place, nous créons une copie des documents sous forme de liste, puis nous rendons cette liste non modifiable.

Cette solution permet de parcourir les documents sans donner au code extérieur la possibilité de modifier directement le contenu du catalogue.

Enfin, nous n'avons pas ajouté de méthode de suppression ou de gestion des prêts, car ces fonctionnalités ne sont pas demandées dans le cadre du Jalon 3.

5. Redéfinition de "equals()" et "hashCode()"

Dans ce jalon, nous avons également travaillé sur les méthodes "equals()" et "hashCode()" de la classe "Document".

Nous avons décidé que deux documents sont considérés comme égaux lorsqu'ils possèdent le même numéro, même si leurs titres ou leurs types sont différents.

Ce choix est logique, car le numéro représente l'identifiant unique de chaque document dans notre bibliothèque.

Nous avons donc redéfini les deux méthodes en utilisant uniquement ce numéro.

La méthode "equals()" permet de vérifier si deux documents possèdent le même identifiant, tandis que "hashCode()" calcule leur code de hachage à partir de ce même identifiant.

Nous avons également déclaré ces méthodes "final" afin que les classes qui héritent de "Document" ne puissent pas modifier cette règle d'égalité.

Cela permet de garantir la cohérence du fonctionnement de notre catalogue, notamment avec l'utilisation des collections basées sur le hachage.

6. Complexité des opérations

Nous avons aussi étudié la complexité des principales opérations de notre catalogue.

Avec l'utilisation de "HashMap", nous obtenons les performances moyennes suivantes :

Opération| Complexité moyenne
Ajout d'un document| O(1)
Recherche par numéro| O(1)
Parcours des documents| O(n)

Cela signifie que l'ajout et la recherche sont généralement rapides, même lorsque le nombre de documents augmente.

Le parcours nécessite quant à lui de visiter les différents documents enregistrés, ce qui explique sa complexité en O(n).

Ces performances sont des moyennes et peuvent varier notamment en cas de collisions dans la table de hachage.

7. Diagramme de classes

Pour représenter l'organisation de notre code et les relations entre les différentes classes, nous avons réalisé un diagramme de classes avec PlantUML.

Nous avons fourni deux fichiers :

- "Diagramme_Jalon3.puml" : le fichier source permettant de modifier et de générer le diagramme ;
- "Diagramme_Jalon3.png" : l'image du diagramme de classes.

Ce diagramme présente notamment l'interface "CatalogueDocuments", son implémentation "CatalogueDocumentsMemoire" ainsi que les relations avec les classes du modèle développées pendant les jalons précédents.

Il nous permet d'avoir une meilleure vue d'ensemble de l'organisation de notre projet.

8. Tests et vérifications

Pour vérifier que notre travail fonctionne correctement, nous avons complété le fichier "Tests.java".

Nous avons conservé les tests des jalons précédents et ajouté de nouveaux tests pour vérifier les fonctionnalités du Jalon 3.

Nos nouveaux tests portent notamment sur :

- L'égalité entre deux documents ayant le même numéro ;
- La cohérence de "hashCode()" ;
- La stabilité du code de hachage ;
- L'ajout correct des documents ;
- Le refus des documents ayant un numéro déjà utilisé ;
- La recherche d'un document existant ;
- La recherche d'un numéro inexistant ;
- Le parcours des documents ;
- La protection de la collection interne ;
- Le refus des données invalides.

Au total, notre fichier "Tests.java" contient 58 vérifications automatisées cumulées, en prenant en compte les jalons précédents et le Jalon 3.

Ce nombre dépasse le minimum de douze tests cumulés demandé dans le sujet.

Nous avons également préparé un fichier "ESSAIS_MANUELS.md", qui présente les manipulations à effectuer pour compléter les vérifications automatisées.

Les résultats de ces essais manuels devront être renseignés après leur exécution effective par les membres du groupe.

9. Compilation et exécution du projet

Pour compiler et exécuter notre projet, nous avons utilisé les commandes suivantes dans le terminal, depuis le dossier contenant les fichiers Java.

Premièrement, nous compilons les classes avec :

javac *.java

Ensuite, nous exécutons les tests avec :

java Tests

Enfin, pour lancer le programme principal, nous utilisons :

java Main

Notre projet ne nécessite aucune bibliothèque externe. Il peut donc être compilé et exécuté directement avec le JDK.

10. Conclusion

À travers ce troisième jalon, nous avons pu approfondir notre compréhension des interfaces et des collections en Java.

Nous avons appris à utiliser une "HashMap" pour organiser les documents de notre bibliothèque et faciliter leur recherche à partir d'un identifiant unique.

Nous avons également travaillé sur l'encapsulation afin de protéger les données internes de notre catalogue, ainsi que sur les méthodes "equals()" et "hashCode()" pour définir correctement l'égalité entre les documents.

Enfin, nous avons renforcé nos vérifications avec de nouveaux tests tout en conservant ceux des jalons précédents.

Ce travail nous permet de disposer d'une base mieux organisée et plus fiable pour poursuivre le développement de notre projet lors du prochain jalon.
