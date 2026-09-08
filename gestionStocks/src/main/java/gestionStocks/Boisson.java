package gestionStocks;

/**
 * Classe héritière Boisson : étend Article.
 * Pour la catégorie boisson, on gère en plus le volume de la bouteille.
 */
public class Boisson extends Article {
    // Attribut spécifique à la boisson
    protected String volume;    // Volume de la bouteille (ex : 50cl)

    /**
     * Constructeur par défaut.
     */
    public Boisson() {
        super();
        this.volume = "";
    }

    /**
     * Constructeur avec paramètres.
     */
    public Boisson(int idArticle, String libelle, int quantite, String volume) {
        super(idArticle, libelle, quantite, "B");   // catégorie B = boisson
        this.volume = volume;
    }

    // ============ ACCESSORS ============
    public String getVolume() { return volume; }
    public void setVolume(String volume) { this.volume = volume; }

    /**
     * Description complète de la boisson.
     */
    @Override
    public String toString() {
        return "[Boisson] " + libelle + " - volume " + volume + " (stock : " + quantite + ")";
    }
}
