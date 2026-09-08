package reapproStocks;

/**
 * Classe héritière ArticleBoisson : étend Article.
 * Pour un produit boisson, on gère en plus le volume (contenance).
 */
public class ArticleBoisson extends Article {
    // Attribut spécifique à la boisson
    protected String contenance;    // Contenance (ex : 50cl)

    /**
     * Constructeur par défaut.
     */
    public ArticleBoisson() {
        super();
        this.contenance = "";
    }

    /**
     * Constructeur avec paramètres.
     */
    public ArticleBoisson(int idArticle, String libelle, int quantite, String contenance) {
        super(idArticle, libelle, "B", quantite);
        this.contenance = contenance;
    }

    // ============ ACCESSORS ============
    public String getContenance() { return contenance; }
    public void setContenance(String contenance) { this.contenance = contenance; }

    @Override
    public String toString() {
        return "[Boisson] " + libelle + " - contenance " + contenance + " (stock : " + quantite + ", seuil : " + getSeuilReappro() + ")";
    }
}
