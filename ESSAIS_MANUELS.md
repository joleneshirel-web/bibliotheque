# Fiche des essais manuels cumulés — Version 1.0 (Jalon 4)

**Exécution :** les 29 scénarios sont rejoués par `java Recette` (code de sortie 1 en cas d'échec). La colonne « Résultat observé » reprend la sortie de l'exécution du 18 novembre 2026 sous Java 21 : **29 scénarios sur 29 conformes**. Le groupe doit rejouer `java Recette` sur sa machine avant la remise et signaler ici tout écart.

Les scénarios 1 à 12 reprennent les jalons 1–2, 13 à 19 le Jalon 3, 20 à 29 le Jalon 4 (`java Main` permet d'en observer plusieurs).

| N° | Manipulation à effectuer | Résultat attendu | Résultat observé / validation |
|---:|---|---|---|
| 1 | Créer un livre avec un numéro, un titre, un nombre de pages et un auteur. | Le livre est créé et ses informations sont affichées. | Conforme — livre cree : Livre [N°=1, titre='Maimouna', disponible=true, pages=200, auteurs=[Abdoulaye Sadji], dureePret=21 jours] |
| 2 | Créer un livre avec deux auteurs. | Les deux auteurs sont conservés et affichés. | Conforme — auteurs conserves : A, B |
| 3 | Créer un périodique avec un organisme et un numéro de parution. | Les informations éditoriales sont affichées. | Conforme — periodique cree : Periodique [N°=3, titre='Revue', disponible=true, organisme='Redaction 2iE', numeroParution=45, dureePret=7 jours] |
| 4 | Afficher `dureePret()` pour un livre puis un périodique. | Les durées propres aux deux sous-types sont retournées. | Conforme — livre=21 j, periodique=7 j |
| 5 | Afficher un livre et un périodique avec `toString()`. | Chaque objet affiche ses informations spécifiques. | Conforme — toString specifiques |
| 6 | Manipuler un livre par une référence `Document` et appeler `dureePret()`. | La méthode redéfinie de `Livre` est appelée. | Conforme — reference Document -> Livre.dureePret()=21 |
| 7 | Manipuler un périodique par une référence `Document` et appeler `dureePret()`. | La méthode redéfinie de `Periodique` est appelée. | Conforme — reference Document -> Periodique.dureePret()=7 |
| 8 | Emprunter un document disponible avec un utilisateur valide. | L'emprunt est accepté et le document devient indisponible. | Conforme — emprunt accepte, disponible=false |
| 9 | Tenter un second emprunt du même document. | L'emprunt est refusé et le premier emprunteur reste enregistré. | Conforme — second emprunt refuse, emprunteur=KARGOUGOU Sidi (ID : 20260153) |
| 10 | Retourner un document emprunté. | Le document redevient disponible et l'emprunteur est effacé. | Conforme — retour accepte, emprunteur efface |
| 11 | Tenter un emprunt avec un utilisateur `null` puis un retour sur un document disponible. | Les opérations sont refusées sans changement d'état. | Conforme — emprunt(null)=false, retour d'un disponible=false |
| 12 | Construire des documents avec des données invalides. | Les invariants existants refusent les constructions invalides. | Conforme — 4 constructions invalides refusees sur 4 |
| 13 | Comparer deux documents ayant le même numéro, puis deux ayant des numéros différents. | Même numéro : égaux ; numéros différents : non égaux. | Conforme — meme numero egaux (meme sous-type different), numeros differents non egaux |
| 14 | Comparer le `hashCode()` de deux documents égaux. | Les deux codes de hachage sont identiques. | Conforme — hashCode=20 et 20 |
| 15 | Ajouter deux documents de numéros différents dans le catalogue. | Les deux ajouts réussissent. | Conforme — deux ajouts reussis |
| 16 | Ajouter un document dont le numéro existe déjà. | L'ajout est refusé et le document initial reste celui qui est enregistré. | Conforme — doublon refuse, document initial conserve |
| 17 | Rechercher un numéro présent puis un numéro absent. | Le document présent est retrouvé ; l'absence est représentée par `Optional.empty()`. | Conforme — present trouve, 99 -> Optional.empty |
| 18 | Parcourir les documents du catalogue. | Les documents enregistrés sont accessibles au parcours. | Conforme — parcours : 2 documents |
| 19 | Tenter de modifier la liste renvoyée par `parcourir()`. | La modification est refusée et la collection interne reste protégée. | Conforme — modification refusee, catalogue intact |
| 20 | Emprunter un livre disponible avec `ServicePret`, puis un périodique. | Deux prêts créés ; échéances à +21 jours (livre) et +7 jours (périodique). | Conforme — echeances 2026-11-22 (livre) et 2026-11-08 (periodique) |
| 21 | Emprunter un numéro absent du catalogue. | `DocumentIntrouvableException` ; aucun prêt créé. | Conforme — DocumentIntrouvableException, 2 prets inchanges |
| 22 | Faire emprunter un document déjà emprunté par un autre utilisateur. | `DocumentDejaEmprunteException` ; le premier emprunteur est conservé. | Conforme — DocumentDejaEmprunteException, emprunteur initial conserve |
| 23 | Avec une limite de 2, faire emprunter un troisième document au même utilisateur. | `LimiteEmpruntsException` ; le troisième document reste disponible. | Conforme — LimiteEmpruntsException, document 2 reste disponible |
| 24 | Retourner un des documents, puis réessayer le troisième emprunt. | Le retour libère la limite ; le nouvel emprunt réussit. | Conforme — apres retour, nouvel emprunt accepte (2 prets) |
| 25 | Retourner un document sans prêt, puis retourner deux fois le même document. | `DocumentNonEmprunteException` à chaque retour non justifié. | Conforme — DocumentNonEmprunteException (jamais emprunte, puis deja rendu) |
| 26 | Appeler `emprunter` avec un utilisateur `null` ou un numéro ≤ 0. | `IllegalArgumentException` ; aucun changement d'état. | Conforme — IllegalArgumentException pour utilisateur null et numero 0 |
| 27 | Utiliser une horloge réglée au-delà de l'échéance puis lister les retards. | Seuls les prêts dont l'échéance est strictement dépassée sont listés. | Conforme — pas de retard le jour de l'echeance (22/11), retard le 23/11 |
| 28 | Tenter de modifier la liste renvoyée par `pretsEnCours()`. | `UnsupportedOperationException` ; les prêts du service restent intacts. | Conforme — liste non modifiable, 2 prets intacts |
| 29 | Emprunter un document directement par `Document.emprunter()` puis passer par le service. | Le service refuse l'emprunt et le retour ; aucun prêt n'est enregistré. | Conforme — contournement detecte : emprunt et retour refuses par le service |

## Vérification automatisée

```bash
./executer_essais.sh    # ou : javac *.java && java Tests
```

Résultats attendus : **58 vérifications réussies** (`Tests`) et **24 essais JUnit réussis** (`ServicePretTest`).

Le nombre indiqué concerne les vérifications automatisées. Les manipulations de cette fiche doivent être exécutées et validées séparément par le groupe.
