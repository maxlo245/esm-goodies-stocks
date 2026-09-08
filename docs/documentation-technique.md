# Documentation technique

## Gestion de Stocks des Athlètes

### Architecture du projet

```
gestionStocks/
├── pom.xml
├── src/main/java/gestionStocks/
│   ├── Main.java               ← Point d'entrée
│   ├── MainFrame.java          ← Interface Swing (JFrame)
│   ├── GestionStocksData.java  ← Service de données (mode démo)
│   ├── ConnexionBDD.java       ← Connexion JDBC PostgreSQL
│   ├── Article.java            ← Classe mère (3 catégories)
│   ├── Textile.java            ← Héritière : taille + couleur
│   ├── Boisson.java            ← Héritière : volume
│   ├── DenreeSeche.java        ← Héritière : poids
│   ├── Coureur.java            ← Coureur
│   ├── TypeEpreuve.java        ← Type d'épreuve
│   └── Reservation.java        ← Réservation + LigneReservation
└── src/test/java/gestionStocks/
    └── GestionStocksTest.java  ← Tests JUnit
```

### Modèle de données (classes)

| Classe | Rôle | Hérite de |
|--------|------|-----------|
| `Article` | Classe mère – id, libellé, quantité, catégorie, sl | — |
| `Textile` | + taille, couleur | `Article` |
| `Boisson` | + volume | `Article` |
| `DenreeSeche` | + poids | `Article` |
| `Coureur` | id, nom, prénom | — |
| `TypeEpreuve` | id, libellé | — |
| `Reservation` | id, date, coureur, typeEpreuve, lignes, enAttente | — |

### Méthodes génériques (4 opérations CRUD avec surcharges)

| Méthode | Surcharge Article | Surcharge Coureur | Surcharge Reservation |
|---------|-------------------|-------------------|-----------------------|
| `CREER` | `CREER(Article)` | `CREER(Coureur)` | `CREER(Reservation)` |
| `MODIFIER` | `MODIFIER(int id, Article)` | `MODIFIER(int id, Coureur)` | `MODIFIER(int id, Reservation)` |
| `CONSULTER` | `CONSULTER_Article(int id)` | `CONSULTER_Coureur(int id)` | — |
| `SUPPRIMER` | `SUPPRIMER_Article(int id)` (logique SL) | `SUPPRIMER_Coureur(int id)` | `SUPPRIMER_Reservation(int id)` |

### Logique métier

- **Rupture de stock** : quand un article atteint 0, il apparaît dans « Articles en rupture ».
- **Réservation** : chaque ligne vérifie que la quantité demandée ≤ quantité disponible.
  - Si suffisant → la réservation est validée et les stocks réajustés.
  - Si insuffisant → la réservation passe en « attente de validation ».
- **Validation en attente** : une fois réapprovisionné, le gestionnaire valide la réservation et les stocks sont réajustés.
- **Suppression logique** : un article marqué `SL=true` n'apparaît plus dans les listes actives.

---

## Réapprovisionnement des Goodies

### Architecture du projet

```
reapproStocks/
├── pom.xml
├── src/main/java/reapproStocks/
│   ├── Main.java                    ← Point d'entrée
│   ├── MainFrame.java               ← Interface Swing (JFrame)
│   ├── ReapproStocksData.java       ← Service de données (mode démo)
│   ├── ConnexionBDD.java            ← Connexion JDBC PostgreSQL
│   ├── Fournisseur.java             ← Fournisseur
│   ├── PointLivraison.java          ← Point de livraison
│   ├── Motif.java                   ← Motif (R, NP, UR)
│   ├── Article.java                 ← Classe mère
│   ├── ArticleTextile.java          ← Héritière : taille + couleur
│   ├── ArticleBoisson.java          ← Héritière : contenance
│   ├── ArticleDenreeSeche.java      ← Héritière : poids
│   └── Reapprovisionnement.java     ← Réapprovisionnement + LigneReappro
└── src/test/java/reapproStocks/
    └── ReapproStocksTest.java       ← Tests JUnit
```

### Seuils de réapprovisionnement

| Catégorie | Seuil |
|-----------|-------|
| T (Textile) | 10 |
| B (Boisson) | 100 |
| DS (Denrée sèche) | 500 |

Un article doit être réapprovisionné quand `quantite ≤ seuil`.

### Base de données (PostgreSQL)

La base `reapproStocks` contient les tables :
- `Fournisseur`, `Point_de_livraison`, `Motif`, `Article`, `Reapprovisionnement`, `Ligne_Reappro`
