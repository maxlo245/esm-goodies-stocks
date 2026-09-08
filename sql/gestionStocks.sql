-- =========================================================================
-- Script de création de la base de données gestionStocks (PostgreSQL)
-- Projet : esm.gestionStocksAthletes
--
-- Tables : Coureur, Type_epreuve, Articles, Réserver
-- =========================================================================

-- Création de la base de données (à exécuter en tant qu'utilisateur ayant les droits)
CREATE DATABASE gestionStocks;

-- Connexion à la base
\c gestionStocks

-- =========================================================================
-- TABLE Coureur : représente un coureur participant aux épreuves
-- =========================================================================
CREATE TABLE Coureur (
    id_coureur  SERIAL PRIMARY KEY,
    nom         VARCHAR(80)  NOT NULL,
    prenom      VARCHAR(80)  NOT NULL
);

-- =========================================================================
-- TABLE Type_epreuve : représente un type d'épreuve
-- =========================================================================
CREATE TABLE Type_epreuve (
    id_type_epreuve  SERIAL PRIMARY KEY,
    libelle          VARCHAR(120) NOT NULL
);

-- =========================================================================
-- TABLE Articles : représente un article (goodie) du stock
--    catégorie : T (textile), B (boisson), DS (denrée sèche)
--    sl : indicateur de suppression logique
-- =========================================================================
CREATE TABLE Articles (
    id_article SERIAL PRIMARY KEY,
    libelle    VARCHAR(80) NOT NULL,
    categorie  VARCHAR(2)  NOT NULL,
    quantite   INTEGER     NOT NULL DEFAULT 0,
    taille     VARCHAR(5),          -- textile
    couleur    VARCHAR(30),         -- textile
    volume     VARCHAR(10),         -- boisson
    poids      VARCHAR(10),         -- denrée sèche
    sl         BOOLEAN     NOT NULL DEFAULT FALSE   -- suppression logique
);

-- =========================================================================
-- TABLE Réserver : association entre un coureur, un type d'épreuve
-- et une liste d'articles réservés à une date donnée.
-- Une table de liaison Article_Reservation permet de lier chaque réservation
-- à ses articles avec la quantité réservée.
-- =========================================================================
CREATE TABLE Re_server (
    id_reservation    SERIAL PRIMARY KEY,
    date_reservation  DATE NOT NULL,
    id_coureur        INTEGER NOT NULL REFERENCES Coureur(id_coureur),
    id_type_epreuve   INTEGER NOT NULL REFERENCES Type_epreuve(id_type_epreuve),
    en_attente        BOOLEAN NOT NULL DEFAULT FALSE
);

-- Table de liaison : articles réservés pour une réservation
CREATE TABLE Article_Reservation (
    id_reservation  INTEGER NOT NULL REFERENCES Re_server(id_reservation),
    id_article      INTEGER NOT NULL REFERENCES Articles(id_article),
    quantite        INTEGER NOT NULL,
    PRIMARY KEY (id_reservation, id_article)
);

-- =========================================================================
-- CHARGEMENT DES DONNÉES DE DÉMONSTRATION
-- =========================================================================

-- Types d'épreuve
INSERT INTO Type_epreuve (libelle) VALUES ('La course folle junior');
INSERT INTO Type_epreuve (libelle) VALUES ('Le semi-marathon');
INSERT INTO Type_epreuve (libelle) VALUES ('Le marathon de Paris');

-- Coureurs
INSERT INTO Coureur (nom, prenom) VALUES ('Brillat', 'Savarin');
INSERT INTO Coureur (nom, prenom) VALUES ('Berthollo', 'Pierre');
INSERT INTO Coureur (nom, prenom) VALUES ('Durand', 'Julie');
INSERT INTO Coureur (nom, prenom) VALUES ('Petit', 'Jean');

-- Articles
INSERT INTO Articles (libelle, categorie, quantite, taille, couleur, volume, poids)
VALUES ('Tee-shirt sport', 'T', 5, 'L', 'vert', NULL, NULL);
INSERT INTO Articles (libelle, categorie, quantite, taille, couleur, volume, poids)
VALUES ('Maillot officiel', 'T', 150, 'S', 'blanche', NULL, NULL);
INSERT INTO Articles (libelle, categorie, quantite, taille, couleur, volume, poids)
VALUES ('Casquette', 'T', 25, 'M', 'noir', NULL, NULL);
INSERT INTO Articles (libelle, categorie, quantite, taille, couleur, volume, poids)
VALUES ('Bouteille d''eau', 'B', 200, NULL, NULL, '50cl', NULL);
INSERT INTO Articles (libelle, categorie, quantite, taille, couleur, volume, poids)
VALUES ('Boisson énergisante', 'B', 90, NULL, NULL, '33cl', NULL);
INSERT INTO Articles (libelle, categorie, quantite, taille, couleur, volume, poids)
VALUES ('Barre énergétique', 'DS', 120, NULL, NULL, NULL, '30g');
INSERT INTO Articles (libelle, categorie, quantite, taille, couleur, volume, poids)
VALUES ('Fruits secs', 'DS', 400, NULL, NULL, NULL, '250g');

-- Une réservation de démonstration
INSERT INTO Re_server (date_reservation, id_coureur, id_type_epreuve, en_attente)
VALUES (CURRENT_DATE, 2, 1, FALSE);

INSERT INTO Article_Reservation (id_reservation, id_article, quantite)
VALUES (1, 1, 1);
INSERT INTO Article_Reservation (id_reservation, id_article, quantite)
VALUES (1, 4, 6);
INSERT INTO Article_Reservation (id_reservation, id_article, quantite)
VALUES (1, 6, 12);

-- =========================================================================
-- LISTE DES REQUÊTES À RÉALISER (conforme au cahier des charges)
-- =========================================================================

-- 1. Afficher la liste des produits
SELECT id_article, libelle, categorie, quantite, taille, couleur, volume, poids
FROM Articles
WHERE sl = FALSE;

-- 2. Afficher la liste des coureurs
SELECT id_coureur, nom, prenom FROM Coureur;

-- 3. Afficher la liste des types d'épreuves
SELECT id_type_epreuve, libelle FROM Type_epreuve;

-- 4. Afficher la liste des réservations
SELECT r.id_reservation, r.date_reservation,
       c.nom, c.prenom, t.libelle AS type_epreuve, r.en_attente
FROM Re_server r
JOIN Coureur c ON c.id_coureur = r.id_coureur
JOIN Type_epreuve t ON t.id_type_epreuve = r.id_type_epreuve;

-- 5. Afficher la liste des réservations en date du jour courant pour le coureur Brillat Savarin
SELECT r.id_reservation, r.date_reservation, c.nom, c.prenom, t.libelle AS type_epreuve
FROM Re_server r
JOIN Coureur c ON c.id_coureur = r.id_coureur
JOIN Type_epreuve t ON t.id_type_epreuve = r.id_type_epreuve
WHERE r.date_reservation = CURRENT_DATE
  AND c.nom = 'Brillat' AND c.prenom = 'Savarin';
