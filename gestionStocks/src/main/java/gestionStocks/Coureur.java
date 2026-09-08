package gestionStocks;

/**
 * Classe Coureur : représente un coureur participant aux épreuves.
 * Le coureur dispose d'un nom et d'un prénom, ainsi que d'un identifiant.
 */
public class Coureur {
    // Attributs
    protected int idCoureur;        // Identifiant unique du coureur
    protected String nom;           // Nom du coureur
    protected String prenom;        // Prénom du coureur

    /**
     * Constructeur par défaut.
     */
    public Coureur() {
        this.idCoureur = 0;
        this.nom = "";
        this.prenom = "";
    }

    /**
     * Constructeur avec paramètres.
     * @param idCoureur identifiant
     * @param nom nom
     * @param prenom prénom
     */
    public Coureur(int idCoureur, String nom, String prenom) {
        this.idCoureur = idCoureur;
        this.nom = nom;
        this.prenom = prenom;
    }

    // ============ ACCESSORS ============
    public int getIdCoureur() { return idCoureur; }
    public void setIdCoureur(int idCoureur) { this.idCoureur = idCoureur; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    /**
     * Nom complet.
     */
    public String getNomComplet() {
        return prenom + " " + nom;
    }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }
}
