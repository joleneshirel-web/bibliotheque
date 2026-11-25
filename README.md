# Bibliothèque de prêt — Version 1.0 (Jalon 4)

**Groupe 4** — Projet Java de Programmation Orientée Objet (2iE)  
**Tag GitHub :** `jalon-4` / `version-1.0`

## Membres du groupe (Groupe 4)

| Nom et Prénoms | Identifiant étudiant | Adresse courriel |
|---|---|---|
| **KARGOUGOU Sidi** | `20260153` | `zarkawi.kargougou@2ie-edu.org` |
| **ZIO Jolene Shirel** | `20240007` | `shirel.zio@2ie-edu.org` |
| **OUEDRAOGO Murielle** | `20240209` | `murielle.ouedraogo@2ie-edu.org` |

---

Application Java en mode console pour gérer un catalogue de documents (livres, périodiques) et leurs prêts. Aucune bibliothèque externe n'est nécessaire pour l'application ; JUnit 4 n'est requis que pour l'exécution de la suite de tests `ServicePretTest`.

## 1. Contenu du dépôt

| Fichier / Dossier | Rôle |
|---|---|
| `Document`, `Livre`, `Periodique`, `Auteur`, `Utilisateur` | Modèle objet et invariants (Jalons 1–2) |
| `Empruntable`, `Pretable` | Contrats de prêt d'un document |
| `CatalogueDocuments`, `CatalogueDocumentsMemoire` | Catalogue : contrat et implémentation `HashMap` (Jalon 3) |
| `ServicePret`, `Pret` | Règles de prêt, échéances, retards et atomicité (Jalon 4) |
| `BibliothequeException` et 4 sous-classes | Hiérarchie d'exceptions métier vérifiées (Jalon 4) |
| `Main` | Démonstration d'un scénario complet en ligne de commande |
| `Tests` | 58 vérifications automatisées héritées des Jalons 1 à 3 |
| `ServicePretTest` | 24 essais automatisés JUnit 4 du Jalon 4 |
| `Recette.java`, `ESSAIS_MANUELS.md` | Recette : 29 scénarios exécutables et fiche des résultats observés |
| `docs/` | Diagramme de classes du Jalon 4 (`Diagramme_Jalon4.puml` et `Diagramme_Jalon4.png`) |
| `executer_essais.sh` | Script compilant et exécutant toutes les suites d'essais sur poste propre |

## 2. Compilation et exécution (sur poste propre)

### Sans JUnit (application, recette et tests des jalons 1–3)
```bash
javac $(ls *.java | grep -v ServicePretTest)
java Main           # démonstration en console
java Tests          # 58 vérifications des jalons 1 à 3
java Recette        # recette : 29 scénarios de bout en bout
```

### Avec JUnit 4 (toutes les suites, y compris les 24 tests JUnit)
Placez `junit-4.13.2.jar` et `hamcrest-core-1.3.jar` dans un dossier `lib/` (ou passez leurs chemins en arguments) :

```bash
chmod +x executer_essais.sh
./executer_essais.sh
# Ou avec des chemins personnalisés :
./executer_essais.sh /chemin/vers/junit-4.13.2.jar /chemin/vers/hamcrest-core-1.3.jar
```

## 3. Fonctionnalités

- Ajouter un document au catalogue, le retrouver par numéro unique, parcourir le catalogue sans exposer la collection interne.
- Emprunter un document : la date d'échéance dépend dynamiquement du sous-type (21 jours pour un `Livre`, 7 jours pour un `Periodique`).
- Retourner un document emprunté et libérer le quota de l'utilisateur.
- Limiter le nombre de prêts simultanés par utilisateur (3 par défaut, paramétrable à la construction).
- Lister les prêts en cours, les prêts d'un utilisateur donné et les prêts strictement en retard.

## 4. Choix de conception du Jalon 4

**Séparer le document et le prêt.** `Document` conserve son état propre (disponibilité, emprunteur). `ServicePret` orchestre le processus métier : quota par utilisateur, calcul des échéances, détection des retards. `Pret` est un objet valeur immuable.

**Deux familles d'erreurs distinctes.**
- Un refus métier légitime (`DocumentIntrouvableException`, `DocumentDejaEmprunteException`, `DocumentNonEmprunteException`, `LimiteEmpruntsException`) hérite de `BibliothequeException` (exception *vérifiée*) : l'appelant est contraint de traiter le refus.
- Une erreur de programmation ou précondition violée (`utilisateur == null`, `numero <= 0`, paramètres de construction invalides) lève `IllegalArgumentException` (exception *non vérifiée*).

**Atomicité (« tout ou rien »).**
1. Tous les contrôles d'invariants et de règles métier sont effectués avant toute mutation d'état.
2. La seule étape modifiant deux états (marquage du document comme emprunté, puis création/enregistrement du `Pret`) est encadrée par un bloc `try/catch` compensatoire : en cas d'exception, le document est remis à l'état disponible.
3. Aucun échec métier ou technique ne laisse le système dans un état partiellement modifié.

**Faible couplage au catalogue.** `ServicePret` dépend uniquement de l'interface `CatalogueDocuments`. Un test JUnit dédié valide son fonctionnement avec une implémentation alternative (`CatalogueEnListe`).

**Horloge injectable.** `ServicePret` reçoit un `java.time.Clock`, permettant de tester déterministement les échéances et le franchissement des dates de retard.

## 5. Synthèse des essais et validation

- `Tests` : **58 vérifications réussies** (Jalons 1 à 3).
- `ServicePretTest` : **24 essais JUnit 4 réussis** (cas nominaux, cas limites, erreurs de programmation, atomicité après panne, contournement du service, substitution du catalogue).
- **Total automatisé : 82 tests**, complétés par `Recette` (**29 scénarios conformes** documentés dans `ESSAIS_MANUELS.md`).

## 6. Limites connues

- Le service n'est pas synchronisé pour des accès concurrents (`HashMap`).
- Les données sont conservées uniquement en mémoire (pas de persistance fichier/base de données).
- `Document.emprunter()` reste publique pour la compatibilité avec les jalons 1–3 : un emprunt direct contournant `ServicePret` est détecté (nouvel emprunt et retour via le service refusés) mais non bloqué au niveau de `Document`.
- Le retour d'un document ne vérifie pas l'identité de l'utilisateur qui restitue l'ouvrage.

## 7. Diagramme de classes

Les sources et le rendu graphique du diagramme de classes du Jalon 4 sont disponibles dans `docs/` :
- `docs/Diagramme_Jalon4.puml`
- `docs/Diagramme_Jalon4.png`
