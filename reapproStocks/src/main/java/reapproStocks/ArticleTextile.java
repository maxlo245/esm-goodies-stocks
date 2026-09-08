package reapproStocks;

/**
 * Classe héritière ArticleTextile : étend Article.
 * Pour un produit textile, on gère en plus la taille et la couleur.
 */
public class ArticleTextile extends Article {
    // Attributs spécifiques au textile
    protected String taille;    // Taille (S, M, L, XL, ...)
    protected String couleur;   // Couleur

    /**
     * Constructeur par défaut.
     */
    public ArticleTextile() {
        super();
        this.taille = "";
        this.couleur = "";
    }

    /**
     * Constructeur avec paramètres.
     */
    public ArticleTextile(int idArticle, String libelle, int quantite, String taille, String couleur) {
        super(idArticle, libelle, "T", quantite);
        this.taille = taille;
        this.couleur = couleur;
    }

    // ============ ACCESSORS ============
    public String getTaille() { return taille; }
    public void setTaille(String taille) { this.taille = taille; }

    public String getCouleur() { return couleur; }
    public void setCouleur(String couleur) { this.couleur = couleur; }

    @Override
    public String toString() {
        return "[Textile] " + libelle + " - taille " + taille + " - couleur " + couleur + " (stock : " + quantite + ", seuil : " + getSeuilReappro() + ")";
    }
}
