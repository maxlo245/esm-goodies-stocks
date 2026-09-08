package reapproStocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Reapprovisionnement : représente une demande de réapprovisionnement
 * de goodies auprès d'un fournisseur, pour un point de livraison donné,
 * avec un motif et une date.
 *
 * Chaque demande contient une liste de lignes (article + quantité).
 */
public class Reapprovisionnement {
    // Attributs
    protected int numeroReappro;                    // Numéro de réapprovisionnement
    protected LocalDate date;                       // Date de la demande
    protected Fournisseur fournisseur;              // Fournisseur concerné
    protected PointLivraison pointLivraison;        // Point de livraison
    protected Motif motif;                          // Motif de la demande
    protected List<LigneReappro> lignes;            // Liste des lignes

    /**
     * Constructeur par défaut.
     */
    public Reapprovisionnement() {
        this.numeroReappro = 0;
        this.date = LocalDate.now();
        this.fournisseur = null;
        this.pointLivraison = null;
        this.motif = null;
        this.lignes = new ArrayList<>();
    }

    /**
     * Constructeur avec paramètres.
     */
    public Reapprovisionnement(int numeroReappro, LocalDate date, Fournisseur fournisseur,
                               PointLivraison pointLivraison, Motif motif) {
        this.numeroReappro = numeroReappro;
        this.date = date;
        this.fournisseur = fournisseur;
        this.pointLivraison = pointLivraison;
        this.motif = motif;
        this.lignes = new ArrayList<>();
    }

    // ============ ACCESSORS ============
    public int getNumeroReappro() { return numeroReappro; }
    public void setNumeroReappro(int numeroReappro) { this.numeroReappro = numeroReappro; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Fournisseur getFournisseur() { return fournisseur; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }

    public PointLivraison getPointLivraison() { return pointLivraison; }
    public void setPointLivraison(PointLivraison pointLivraison) { this.pointLivraison = pointLivraison; }

    public Motif getMotif() { return motif; }
    public void setMotif(Motif motif) { this.motif = motif; }

    public List<LigneReappro> getLignes() { return lignes; }
    public void setLignes(List<LigneReappro> lignes) { this.lignes = lignes; }

    /**
     * Ajoute une ligne de réapprovisionnement.
     * @param article article
     * @param quantite quantité à commander
     */
    public void ajouterLigne(Article article, int quantite) {
        this.lignes.add(new LigneReappro(article, quantite));
    }

    @Override
    public String toString() {
        return "Réappro n°" + numeroReappro + " - " + date + " - " +
               (fournisseur != null ? fournisseur.getNom() : "?") + " - " +
               (motif != null ? motif.getCode() : "?");
    }

    /**
     * Classe interne LigneReappro : représente une ligne d'une demande
     * de réapprovisionnement (article + quantité à commander).
     */
    public static class LigneReappro {
        protected Article article;      // Article concerné
        protected int quantite;         // Quantité à commander

        public LigneReappro(Article article, int quantite) {
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
