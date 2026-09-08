# Documentation d'utilisation

## Gestion de Stocks des Athlètes

### Accueil

Au lancement, l'application affiche la page d'accueil avec une description des fonctionnalités.

### Menu principal

Le menu déroulant en haut permet d'accéder à chaque module :

- **Articles** : Gestion complète des articles (ajout, modification, suppression logique).
  - Catégories disponibles : T (Textile), B (Boisson), DS (Denrée sèche).
  - Entrer la taille/couleur pour un textile, le volume pour une boisson, le poids pour une denrée sèche.
  - Cliquer sur un article dans la liste puis sur « Afficher » pour remplir le formulaire.

- **Coureurs** : Ajouter un nouveau coureur (Nom + Prénom).

- **Types d'épreuve** : Ajouter un type d'épreuve (ex : « Le semi-marathon »).

- **Réservations** :
  1. Sélectionner un coureur et un type d'épreuve.
  2. Ajouter les articles avec la quantité souhaitée.
  3. Cliquer sur « Créer la réservation ».
  - Si le stock est suffisant → la réservation est validée et les stocks réajustés.
  - Si le stock est insuffisant → la réservation passe en attente et un message d'alerte s'affiche.

- **Rupture / Attente** :
  - Consulter la liste des produits en rupture de stock.
  - Consulter les réservations en attente de validation.
  - Valider une réservation en attente (les stocks sont réajustés).

- **Historique** :
  - Afficher toutes les réservations avec leurs détails.
  - (Filtres possibles par date, coureur ou type d'épreuve.)

---

## Réapprovisionnement des Goodies

### Menu principal

- **Fournisseurs** : Ajouter/modifier/supprimer un fournisseur (nom, adresse complète, contact).

- **Points de livraison** : Ajouter/modifier un point de livraison (nom, adresse complète, contact).

- **Articles** :
  - Ajouter un article en choisissant sa catégorie (T, B ou DS).
  - Le seuil de réapprovisionnement est automatiquement déterminé par la catégorie.
    - T : 10 unités
    - B : 100 unités
    - DS : 500 unités
  - Le seuil s'affiche dans la description de chaque article.

- **Réapprovisionnement** :
  1. Choisir un fournisseur, un point de livraison et un motif (R, NP ou UR).
  2. Ajouter les articles avec la quantité souhaitée.
  3. Cliquer sur « Créer la demande ».

- **Articles à réapprovisionner** :
  - Affiche la liste des articles dont le stock est inférieur ou égal au seuil de leur catégorie.
  - Permet au gestionnaire de savoir ce qu'il faut commander.

- **Historique** :
  - Afficher toutes les demandes de réapprovisionnement.
  - Afficher les réapprovisionnements du jour pour un fournisseur donné.

---

## Raccourcis et astuces

- L'application fonctionne en mode démo avec des données pré-chargées : pas besoin de PostgreSQL pour commencer.
- Les identifiants JDBC dans `ConnexionBDD` peuvent être modifiés pour connecter une vraie base PostgreSQL.
- Les scripts SQL sont fournis dans le dossier `sql/` pour créer et alimenter les bases PostgreSQL.
