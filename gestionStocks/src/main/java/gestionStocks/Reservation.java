package gestionStocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Reservation : représente une réservation de goodies pour un coureur,
 * une date donnée et un type d'épreuve donné.
 */
public class Reservation {
    // Attributs
    protected int idReservation;            // Numéro de réservation
    protected LocalDate date;               // Date de réservation
    protected Coureur coureur;              // Coureur concerné
    protected TypeEpreuve typeEpreuve;      // Type d'épreuve
    protected List<LigneReservation> lignes; // Liste des articles réservés
    protected boolean enAttente;            // true si la réservation est en attente de validation

    /**
     * Constructeur par défaut.
     */
    public Reservation() {
        this.idReservation = 0;
        this.date = LocalDate.now();
        this.coureur = null;
        this.typeEpreuve = null;
        this.lignes = new ArrayList<>();
        this.enAttente = false;
    }

    /**
     * Constructeur avec paramètres.
     */
    public Reservation(int idReservation, LocalDate date, Coureur coureur, TypeEpreuve typeEpreuve) {
        this.idReservation = idReservation;
        this.date = date;
        this.coureur = coureur;
        this.typeEpreuve = typeEpreuve;
        this.lignes = new ArrayList<>();
        this.enAttente = false;
    }

    // ============ ACCESSORS ============
    public int getIdReservation() { return idReservation; }
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Coureur getCoureur() { return coureur; }
    public void setCoureur(Coureur coureur) { this.coureur = coureur; }

    public TypeEpreuve getTypeEpreuve() { return typeEpreuve; }
    public void setTypeEpreuve(TypeEpreuve typeEpreuve) { this.typeEpreuve = typeEpreuve; }

    public List<LigneReservation> getLignes() { return lignes; }
    public void setLignes(List<LigneReservation> lignes) { this.lignes = lignes; }

    public boolean isEnAttente() { return enAttente; }
    public void setEnAttente(boolean enAttente) { this.enAttente = enAttente; }

    /**
     * Ajoute une ligne de réservation à la liste.
     * @param article article concerné
     * @param qte quantité réservée
     */
    public void ajouterLigne(Article article, int qte) {
        this.lignes.add(new LigneReservation(article, qte));
    }

    @Override
    public String toString() {
        return "Réservation n°" + idReservation + " - " + date + " - " +
               (coureur != null ? coureur.getNomComplet() : "?") + " - " +
               (typeEpreuve != null ? typeEpreuve.getLibelle() : "?") +
               (enAttente ? " [EN ATTENTE]" : "");
    }

    /**
     * Classe interne LigneReservation : représente la réservation d'un article
     * avec une quantité.
     */
    public static class LigneReservation {
        protected Article article;  // Article réservé
        protected int quantite;     // Quantité réservée

        public LigneReservation(Article article, int quantite) {
            this.article = article;
            this.quantite = quantite;
        }

        public Article getArticle() { return article; }
        public void setArticle(Article article) { this.article = article; }

        public int getQuantite() { return quantite; }
        public void setQuantite(int quantite) { this.quantite = quantite; }

        @Override
        public String toString() {
            return article.getLibelle() + " x" + quantite;
        }
    }
}
