* SUJET: **Bibliothèque de prêt :** 

•  Fondement de la relation « est un » : L'architecture repose sur la classe mère Document car un livre est un document et un périodique est un document. Cette hiérarchie mutualise les attributs universels (numéro, titre, disponibilité) ainsi que la gestion des emprunts déjà validée, tandis que l'auteur est conservé via une composition rigoureuse. 
•  Justification du choix d'une classe abstraite : La classe Document est déclarée comme abstraite (abstract) car il n'existe pas de « document » générique dans la réalité d'une bibliothèque : on manipule toujours des objets concrets (livre ou périodique). Cette abstraction empêche toute instanciation invalide et formalise un contrat obligatoire pour les classes filles.
•  Éléments communs et variations dynamiques :
•	Ce qui est commun : L'ensemble des métadonnées d'identification, l'état de disponibilité et les règles de transaction (emprunt et retour) s'appliquent uniformément à tous les sous-types. 
•	Ce qui varie : Les attributs spécifiques (le nombre de pages pour le livre, le numéro de parution pour le périodique) et le comportement métier critique qu'est la durée maximale de prêt (dureeMaxPret(), fixée à 21 jours pour le livre et 7 jours pour le périodique).
•  Application du polymorphisme : Le système exploite des références de type Document pour manipuler indifféremment des livres et des périodiques. Lors du parcours des objets ou de l'appel de dureeMaxPret() et de toString(), le programme déclenche automatiquement la version adaptée à l'objet réel, garantissant un code évolutif et propre, entièrement dépourvu de tests de type (instanceof).

## Organisation du travail et traçabilité collaborative
* **Méthodologie :** En raison de contraintes matérielles initiales en présentiel, l'équipe a travaillé sur une machine unique en effectuant une rotation régulière des rôles sur VS Code. 
* **Traçabilité :** Les contributions de chaque membre ont été structurées à travers des commits séquentiels et des branches de travail Git dédiées (`feature/`) avant d'être centralisées proprement sur le dépôt GitHub.

*  **Groupe :** 4

## Répartition des tâches et rôles
* **ZIO Jolène Shirel :** Conception de la hiérarchie des classes (`Document`, `Livre`, `Periodique`) et premier commit.
* **OUEDRAOGO Murielle :** Implémentation des constructeurs (`super()`), de la méthode `dureeMaxPret()` et de `toString()`.
* **KARGOUGOU Sidi:** Réalisation des essais polymorphes dans la classe `Main` et rédaction de la justification technique.

  
