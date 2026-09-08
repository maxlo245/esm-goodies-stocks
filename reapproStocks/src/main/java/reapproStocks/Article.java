package reapproStocks;

/**
 * Classe mère Article : représente un article du stock à réapprovisionner.
 * Chaque article appartient à une catégorie :
 *   - T  : produit textile
 *   - B  : produit boisson
 *   - DS : produit denrée sèche
 * Le seuil de réapprovisionnement varie selon la catégorie.
 */
public class Article {
    // Attributs communs
    protected int idArticle;          // Code article / identifiant
    protected String libelle;         // Libellé de l'article
    protected String categorie;       // Catégorie : T, B, DS
    protected int quantite;           // Quantité en stock

    /**
     * Constructeur par défaut.
     */
    public Article() {
        this.idArticle = 0;
        this.libelle = "";
        this.categorie = "";
        this.quantite = 0;
    }

    /**
     * Constructeur avec paramètres.
     */
    public Article(int idArticle, String libelle, String categorie, int quantite) {
        this.idArticle = idArticle;
        this.libelle = libelle;
        this.categorie = categorie;
        this.quantite = quantite;
    }

    // ============ ACCESSORS ============
    public int getIdArticle() { return idArticle; }
    public void setIdArticle(int idArticle) { this.idArticle = idArticle; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    /**
     * Retourne le seuil de réapprovisionnement selon la catégorie :
     *   - 10  pour un produit textile
     *   - 100 pour un produit boisson
     *   - 500 pour un produit denrée sèche
     * @return le seuil
     */
    public int getSeuilReappro() {
        return switch (categorie) {
            case "T"  -> 10;
            case "B"  -> 100;
            case "DS" -> 500;
            default   -> 0;
        };
    }

    /**
     * Indique si l'article doit être réapprovisionné
     * (quantité en stock inférieure ou égale au seuil).
     * @return true si un réapprovisionnement est nécessaire
     */
    public boolean doitEtreReapprovisionne() {
        return quantite <= getSeuilReappro();
    }

    @Override
    public String toString() {
        return libelle + " (stock : " + quantite + ")";
    }
}
