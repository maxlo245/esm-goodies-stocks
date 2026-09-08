-- =========================================================================
-- Script de création de la base de données reapproStocks (PostgreSQL)
-- Projet : esm.reapproStocksGoodies
--
-- Tables : Fournisseur, Point_de_livraison, Motif, Article, Réapprovisionnement
-- =========================================================================

-- Création de la base de données
CREATE DATABASE reapproStocks;

-- Connexion à la base
\c reapproStocks

-- =========================================================================
-- TABLE Fournisseur : fournisseur des goodies
-- =========================================================================
CREATE TABLE Fournisseur (
    id_fournisseur  SERIAL PRIMARY KEY,
    nom             VARCHAR(80) NOT NULL,
    rue             VARCHAR(120),
    cp              VARCHAR(10),
    ville           VARCHAR(80),
    tel             VARCHAR(20),
    email           VARCHAR(120)
);

-- =========================================================================
-- TABLE Point_de_livraison : point (lieu) de livraison
-- =========================================================================
CREATE TABLE Point_de_livraison (
    id_point_livraison  SERIAL PRIMARY KEY,
    nom                 VARCHAR(80) NOT NULL,
    rue                 VARCHAR(120),
    cp                  VARCHAR(10),
    ville               VARCHAR(80),
    tel                 VARCHAR(20),
    email               VARCHAR(120)
);

-- =========================================================================
-- TABLE Motif : motif d'une demande de réapprovisionnement
--    code : R (réapprovisionnement), NP (nouveaux produits), UR (urgence)
-- =========================================================================
CREATE TABLE Motif (
    code    VARCHAR(5) PRIMARY KEY,
    libelle VARCHAR(80) NOT NULL
);

-- =========================================================================
-- TABLE Article : article à réapprovisionner
--    categorie : T (textile, seuil 10), B (boisson, seuil 100), DS (denrée sèche, seuil 500)
-- =========================================================================
CREATE TABLE Article (
    id_article  SERIAL PRIMARY KEY,
    libelle     VARCHAR(80) NOT NULL,
    categorie   VARCHAR(2)  NOT NULL,
    quantite    INTEGER     NOT NULL DEFAULT 0,
    taille      VARCHAR(5),          -- textile
    couleur     VARCHAR(30),         -- textile
    contenance  VARCHAR(10),         -- boisson
    poids       VARCHAR(10)          -- denrée sèche
);

-- =========================================================================
-- TABLE Réapprovisionnement : demande de réapprovisionnement
-- =========================================================================
CREATE TABLE Reapprovisionnement (
    numero_reappro      SERIAL PRIMARY KEY,
    date_reappro        DATE NOT NULL,
    id_fournisseur      INTEGER NOT NULL REFERENCES Fournisseur(id_fournisseur),
    id_point_livraison  INTEGER NOT NULL REFERENCES Point_de_livraison(id_point_livraison),
    code_motif          VARCHAR(5) NOT NULL REFERENCES Motif(code)
);

-- Table de liaison : lignes de la demande (article + quantité)
CREATE TABLE Ligne_Reappro (
    numero_reappro  INTEGER NOT NULL REFERENCES Reapprovisionnement(numero_reappro),
    id_article      INTEGER NOT NULL REFERENCES Article(id_article),
    quantite        INTEGER NOT NULL,
    PRIMARY KEY (numero_reappro, id_article)
);

-- =========================================================================
-- CHARGEMENT DES DONNÉES DE DÉMONSTRATION
-- =========================================================================

-- Fournisseurs
INSERT INTO Fournisseur (nom, rue, cp, ville, tel, email)
VALUES ('Sté du textile', '15 rue Emile Zola', '77000', 'Meaux', '01-12-88-89-11', 'stedutextile@yahoo.fr');
INSERT INTO Fournisseur (nom, rue, cp, ville, tel, email)
VALUES ('BoissoDistrib', '8 avenue de la Gare', '75011', 'Paris', '01-45-67-89-22', 'contact@boissodistrib.fr');
INSERT INTO Fournisseur (nom, rue, cp, ville, tel, email)
VALUES ('NutriSèche', '42 rue des Champs', '69003', 'Lyon', '04-72-33-44-55', 'vente@nutriseche.fr');

-- Points de livraison
INSERT INTO Point_de_livraison (nom, rue, cp, ville, tel, email)
VALUES ('Entrepôt Bergson', 'ZI de la Marre', '94000', 'Créteil', '06-12-78-89-11', 'entrepot.bergson@outlook.fr');
INSERT INTO Point_de_livraison (nom, rue, cp, ville, tel, email)
VALUES ('Local Saint-Maur', '2 rue des Acacias', '94100', 'Saint-Maur-des-Fossés', '06-98-76-54-32', 'local.saintmaur@outlook.fr');

-- Motifs
INSERT INTO Motif (code, libelle) VALUES ('R', 'Réapprovisionnement');
INSERT INTO Motif (code, libelle) VALUES ('NP', 'Nouveaux produits');
INSERT INTO Motif (code, libelle) VALUES ('UR', 'Urgence réapprovisionnement');

-- Articles
INSERT INTO Article (libelle, categorie, quantite, taille, couleur, contenance, poids)
VALUES ('Maillot officiel', 'T', 150, 'S', 'blanche', NULL, NULL);
INSERT INTO Article (libelle, categorie, quantite, taille, couleur, contenance, poids)
VALUES ('Tee-shirt sport', 'T', 5, 'L', 'vert', NULL, NULL);
INSERT INTO Article (libelle, categorie, quantite, taille, couleur, contenance, poids)
VALUES ('Bouteille d''eau', 'B', 200, NULL, NULL, '50cl', NULL);
INSERT INTO Article (libelle, categorie, quantite, taille, couleur, contenance, poids)
VALUES ('Boisson énergisante', 'B', 90, NULL, NULL, '33cl', NULL);
INSERT INTO Article (libelle, categorie, quantite, taille, couleur, contenance, poids)
VALUES ('Barre énergétique', 'DS', 400, NULL, NULL, NULL, '30g');
INSERT INTO Article (libelle, categorie, quantite, taille, couleur, contenance, poids)
VALUES ('Fruits secs', 'DS', 50, NULL, NULL, NULL, '250g');

-- Une demande de réapprovisionnement de démonstration
INSERT INTO Reapprovisionnement (date_reappro, id_fournisseur, id_point_livraison, code_motif)
VALUES (CURRENT_DATE, 1, 1, 'R');

INSERT INTO Ligne_Reappro (numero_reappro, id_article, quantite)
VALUES (1, 1, 150);
INSERT INTO Ligne_Reappro (numero_reappro, id_article, quantite)
VALUES (1, 3, 200);
INSERT INTO Ligne_Reappro (numero_reappro, id_article, quantite)
VALUES (1, 5, 400);

-- =========================================================================
-- LISTE DES REQUÊTES À RÉALISER (conforme au cahier des charges)
-- =========================================================================

-- 1. Afficher la liste des fournisseurs
SELECT id_fournisseur, nom, rue, cp, ville, tel, email FROM Fournisseur;

-- 2. Afficher la liste des points de livraison
SELECT id_point_livraison, nom, rue, cp, ville, tel, email FROM Point_de_livraison;

-- 3. Afficher la liste des articles et leurs stocks associés
SELECT id_article, libelle, categorie, quantite, taille, couleur, contenance, poids
FROM Article;

-- 4. Afficher la liste des réapprovisionnements en date du jour courant,
--    pour le fournisseur Sté du textile
SELECT r.numero_reappro, r.date_reappro, f.nom AS fournisseur, m.code AS motif
FROM Reapprovisionnement r
JOIN Fournisseur f ON f.id_fournisseur = r.id_fournisseur
JOIN Motif m ON m.code = r.code_motif
WHERE r.date_reappro = CURRENT_DATE
  AND f.nom = 'Sté du textile';
