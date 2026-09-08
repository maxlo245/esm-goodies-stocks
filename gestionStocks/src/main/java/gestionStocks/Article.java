package gestionStocks;

/**
 * Classe mère Article : représente un article du stock (goodie pour athlètes).
 * Chaque article appartient à UNE catégorie (Textile, Boisson, Denrée sèche).
 * Possède un libellé et une quantité (stock disponible).
 * Un indicateur logique SL permet la suppression logique de l'article.
 */
public class Article {
    // Attributs communs
    protected int idArticle;          // Identifiant unique de l'article
    protected String libelle;         // Libellé de l'article
    protected int quantite;           // Quantité en stock disponible
    protected String categorie;       // Catégorie : T (textile), B (boisson), DS (denrée sèche)
    protected boolean sl;             // Suppression logique (true = supprimé)

    /**
     * Constructeur par défaut.
     */
    public Article() {
        this.idArticle = 0;
        this.libelle = "";
        this.quantite = 0;
        this.categorie = "";
        this.sl = false;
    }

    /**
     * Constructeur avec paramètres.
     * @param idArticle identifiant
     * @param libelle libellé
     * @param quantite quantité en stock
     * @param categorie catégorie
     */
    public Article(int idArticle, String libelle, int quantite, String categorie) {
        this.idArticle = idArticle;
        this.libelle = libelle;
        this.quantite = quantite;
        this.categorie = categorie;
        this.sl = false;
    }

    // ============ ACCESSORS (getters / setters) ============
    public int getIdArticle() { return idArticle; }
    public void setIdArticle(int idArticle) { this.idArticle = idArticle; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public boolean isSl() { return sl; }
    public void setSl(boolean sl) { this.sl = sl; }

    /**
     * Redéfinition de toString pour un affichage lisible.
     */
    @Override
    public String toString() {
        return libelle + " (stock : " + quantite + ")";
    }
}
