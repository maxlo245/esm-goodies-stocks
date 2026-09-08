# Gestion des Goodies pour les Athlètes

Ce dépôt contient **deux applications Java Swing** développées dans le cadre des
projets de gestion de stocks et de réapprovisionnement des goodies pour les athlètes
de l'association **Webcourses**.

## Applications

| Application | Projet Maven | Base de données | Rôle |
|-------------|--------------|-----------------|------|
| **Gestion de Stocks des Athlètes** | `esm.gestionStocksAthletes` | `gestionStocks` | Gestion des articles, coureurs, types d'épreuves et réservations de goodies |
| **Réapprovisionnement des Goodies** | `esm.reapproStocksGoodies` | `reapproStocks` | Gestion des fournisseurs, points de livraison, motifs et demandes de réapprovisionnement |

## Technologies

- **Langage** : Java 17
- **Interface** : Java Swing (JFrame)
- **Base de données** : PostgreSQL (scripts SQL fournis dans `sql/`)
- **Build** : Maven
- **Tests** : JUnit 5

## Mode démonstration (par défaut)

Les deux applications fonctionnent **sans base de données installée** : les données
sont stockées en mémoire et pré-chargées avec des exemples conformes au cahier des
charges. Cela permet de tester immédiatement l'ensemble des fonctionnalités.

Pour basculer sur une **vraie base PostgreSQL**, reportez-vous à la section
« Utilisation avec PostgreSQL » ci-dessous.

## Lancement

Prérequis : JDK 17+ et Maven installés.

### Application 1 : Gestion de Stocks des Athlètes

```bash
cd gestionStocks
mvn clean package
java -jar target/esm.gestionStocksAthletes-1.0.0.jar
```

### Application 2 : Réapprovisionnement des Goodies

```bash
cd reapproStocks
mvn clean package
java -jar target/esm.reapproStocksGoodies-1.0.0.jar
```

## Structure des menus

### Gestion de Stocks des Athlètes
- Gestion des articles (Textile, Boisson, Denrée sèche)
- Gestion des coureurs
- Gestion des types d'épreuve
- Gestion des réservations (création, modification, consultation, annulation)
- Articles en rupture / réservations en attente
- Consultation de l'historique

### Réapprovisionnement des Goodies
- Gestion des fournisseurs
- Gestion des points de livraison
- Gestion des articles (avec seuil par catégorie)
- Création de demandes de réapprovisionnement
- Articles à réapprovisionner
- Consultation de l'historique

## Utilisation avec PostgreSQL (optionnel)

1. Créez vos bases avec les scripts fournis :
   ```bash
   psql -U postgres -f sql/gestionStocks.sql
   psql -U postgres -f sql/reapproStocks.sql
   ```
2. Adaptez les identifiants dans les classes `ConnexionBDD` de chaque module.
3. Utilisez le code JDBC des `ConnexionBDD` pour accéder aux données.

## Documentation

- [Documentation technique](docs/documentation-technique.md)
- [Documentation d'utilisation](docs/documentation-utilisation.md)

## Tests

```bash
mvn test
```
