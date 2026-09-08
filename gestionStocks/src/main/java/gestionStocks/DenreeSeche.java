package gestionStocks;

/**
 * Classe héritière DenreeSeche : étend Article.
 * Pour la catégorie denrée sèche, on gère en plus le poids.
 */
public class DenreeSeche extends Article {
    // Attribut spécifique à la denrée sèche
    protected String poids;     // Poids (ex : 30g, 250g)

    /**
     * Constructeur par défaut.
     */
    public DenreeSeche() {
        super();
        this.poids = "";
    }

    /**
     * Constructeur avec paramètres.
     */
    public DenreeSeche(int idArticle, String libelle, int quantite, String poids) {
        super(idArticle, libelle, quantite, "DS");   // catégorie DS = denrée sèche
        this.poids = poids;
    }

    // ============ ACCESSORS ============
    public String getPoids() { return poids; }
    public void setPoids(String poids) { this.poids = poids; }

    /**
     * Description complète de la denrée sèche.
     */
    @Override
    public String toString() {
        return "[Denrée sèche] " + libelle + " - poids " + poids + " (stock : " + quantite + ")";
    }
}
