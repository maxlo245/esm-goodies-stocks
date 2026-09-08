package gestionStocks;

/**
 * Classe héritière Textile : étend Article.
 * Pour la catégorie textile, on gère en plus la taille et la couleur.
 */
public class Textile extends Article {
    // Attributs spécifiques au textile
    protected String taille;    // Taille (S, M, L, XL, ...)
    protected String couleur;   // Couleur du textile

    /**
     * Constructeur par défaut.
     */
    public Textile() {
        super();
        this.taille = "";
        this.couleur = "";
    }

    /**
     * Constructeur avec paramètres.
     */
    public Textile(int idArticle, String libelle, int quantite, String taille, String couleur) {
        super(idArticle, libelle, quantite, "T");   // catégorie T = textile
        this.taille = taille;
        this.couleur = couleur;
    }

    // ============ ACCESSORS ============
    public String getTaille() { return taille; }
    public void setTaille(String taille) { this.taille = taille; }

    public String getCouleur() { return couleur; }
    public void setCouleur(String couleur) { this.couleur = couleur; }

    /**
     * Description complète du textile.
     */
    @Override
    public String toString() {
        return "[Textile] " + libelle + " - taille " + taille + " - couleur " + couleur + " (stock : " + quantite + ")";
    }
}
