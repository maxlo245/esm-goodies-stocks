package reapproStocks;

/**
 * Classe héritière ArticleDenreeSeche : étend Article.
 * Pour un produit denrée sèche, on gère en plus le poids.
 */
public class ArticleDenreeSeche extends Article {
    // Attribut spécifique à la denrée sèche
    protected String poids;     // Poids (ex : 30g, 250g)

    /**
     * Constructeur par défaut.
     */
    public ArticleDenreeSeche() {
        super();
        this.poids = "";
    }

    /**
     * Constructeur avec paramètres.
     */
    public ArticleDenreeSeche(int idArticle, String libelle, int quantite, String poids) {
        super(idArticle, libelle, "DS", quantite);
        this.poids = poids;
    }

    // ============ ACCESSORS ============
    public String getPoids() { return poids; }
    public void setPoids(String poids) { this.poids = poids; }

    @Override
    public String toString() {
        return "[Denrée sèche] " + libelle + " - poids " + poids + " (stock : " + quantite + ", seuil : " + getSeuilReappro() + ")";
    }
}
