# Projet Bibliothèque de prêt

**Groupe 4 — Projet de Programmation Orientée Objet (Java)**

---

## 👥 Membres du Groupe

* **ZIO Jolène Shirel** (Matricule : `20240007`) — Conception de l'architecture de base, modélisation et intégration du diagramme de classes UML, et implémentation de l'interface `Empruntable` et de la classe abstraite `Document`.
* **OUEDRAOGO Murielle** (Matricule : `20240209`) — Développement des classes concrètes filles (`Livre`, `Periodique`) et de la classe `Utilisateur`.
* **KARGOUGOU Sidi** (Matricule : `20240153`) — Participation active à l'ensemble du travail (conception, logique métier, invariants et tests). *Note : En raison de pannes matérielles récurrentes sur son ordinateur, ses contributions ont été intégrées et déposées sur GitHub depuis les ordinateurs de ses coéquipiers, ce qui explique l'absence de commits à son nom dans l'historique Git.*

## 1. Présentation du projet

Ce projet consiste à développer une application Java permettant de modéliser le fonctionnement d'une bibliothèque de prêt.

L'application permet de représenter différents types de documents, de gérer leur disponibilité, d'effectuer des emprunts et des retours, et d'associer chaque emprunt à un utilisateur.

Le projet met en œuvre plusieurs concepts fondamentaux de la programmation orientée objet : encapsulation, abstraction, héritage, polymorphisme et interfaces.

## 2. Architecture du projet

L'architecture repose sur une classe abstraite `Document`, qui constitue la classe mère des différents types de documents.

### Organisation des classes et interfaces

* **Empruntable** : interface définissant les opérations liées à l'emprunt, au retour et au suivi de l'état d'un document.
* **Pretable** : interface qui étend `Empruntable`.
* **Document** : classe abstraite implémentant `Pretable` et `Cloneable`. Elle centralise les informations communes aux documents ainsi que la gestion des emprunts.
* **Livre** : classe fille de `Document`, qui ajoute le nombre de pages et définit une durée maximale de prêt de 21 jours.
* **Periodique** : classe fille de `Document`, qui ajoute le numéro de parution et définit une durée maximale de prêt de 7 jours.
* **Utilisateur** : classe représentant les personnes pouvant emprunter des documents.
* **Main** : classe permettant de lancer et de présenter le fonctionnement de l'application.
* **Tests** : classe regroupant les vérifications du fonctionnement des différentes fonctionnalités.

## 3. Principes de programmation orientée objet utilisés

### Encapsulation

Les attributs des classes sont privés afin de protéger les données. L'accès aux informations se fait principalement à travers des méthodes dédiées.

### Abstraction

La classe `Document` est abstraite, car elle représente une notion générale qui ne doit pas être instanciée directement. Elle impose notamment aux classes filles de définir leur propre durée maximale de prêt.

### Héritage

Les classes `Livre` et `Periodique` héritent de `Document`. Elles réutilisent ainsi les attributs et les comportements communs tout en ajoutant leurs caractéristiques spécifiques.

### Polymorphisme

Les documents peuvent être manipulés à travers des références de type `Document`. Les méthodes redéfinies, notamment `dureeMaxPret()` et `toString()`, sont exécutées selon le type réel de l'objet.

### Interfaces

L'interface `Empruntable` définit les opérations nécessaires à la gestion des emprunts. L'interface `Pretable` étend ce contrat et est implémentée par `Document`.

## 4. Gestion des emprunts et invariants

Le fonctionnement du système repose sur plusieurs règles :

* Le numéro d'un document doit être strictement positif.
* Le titre et l'auteur d'un document ne doivent être ni nuls ni vides.
* Le nombre de pages d'un livre doit être strictement positif.
* Le numéro de parution d'un périodique doit être strictement positif.
* L'identifiant d'un utilisateur doit être strictement positif.
* Le nom d'un utilisateur ne doit pas être vide.
* L'adresse électronique d'un utilisateur doit respecter le format attendu.
* Un document disponible peut être emprunté par un utilisateur valide.
* Un document déjà emprunté ne peut pas faire l'objet d'un nouvel emprunt.
* Lorsqu'un document est retourné, il redevient disponible et son emprunteur est réinitialisé.

Les opérations d'emprunt et de retour renvoient un booléen permettant de connaître leur résultat.

## 5. Durées maximales de prêt

| Type de document | Durée maximale |
| ---------------- | -------------: |
| Livre            |       21 jours |
| Périodique       |        7 jours |

Ces durées sont définies par redéfinition de la méthode `dureeMaxPret()` dans chaque classe fille.

## 6. Tests et vérifications

La classe `Tests.java` regroupe les vérifications portant notamment sur :

* La structure des classes et des interfaces.
* L'initialisation des objets.
* Les validations des données.
* Les durées maximales de prêt.
* Les opérations d'emprunt et de retour.
* Le refus d'un emprunt lorsque le document est indisponible.
* La gestion de l'emprunteur.
* Le comportement de la méthode `clone()`.

**Résultat obtenu : 41 tests réussis sur 41.**

## 7. Compilation et exécution

Le projet nécessite un JDK Java installé.

Depuis le répertoire contenant les fichiers `.java`, ouvrir un terminal et exécuter :

```bash
javac *.java
```

Pour lancer l'application :

```bash
java Main
```

Pour exécuter les tests :

```bash
java Tests
```

## 8. Diagramme UML

Le diagramme de classes UML présente les classes, les interfaces, les relations d'héritage, d'implémentation et l'association entre les documents et les utilisateurs.

Il est disponible dans le dossier `Diagramme` du projet.

## 9. Organisation du dépôt

```text
bibliotheque/
│
├── Document.java
├── Empruntable.java
├── Pretable.java
├── Livre.java
├── Periodique.java
├── Utilisateur.java
├── Main.java
├── Tests.java
├── README.md
│
└── Diagramme/
    ├── Diagramme_classes_Bibliotheque_Jalon1_final.puml
    └── Diagramme_classes_Bibliotheque_Jalon1_final.png
```

## 10. Informations sur le projet

* **Projet :** Bibliothèque de prêt
* **Groupe :** 4
* **Langage :** Java
* **Version :** Jalon 1out en maintenant pleinement sa participation intellectuelle au projet.
  
