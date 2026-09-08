package gestionStocks;

/**
 * Classe TypeEpreuve : représente un type d'épreuve.
 * Un libellé (ex : "La courses folle junior") précise le type d'épreuve.
 */
public class TypeEpreuve {
    // Attributs
    protected int idTypeEpreuve;    // Identifiant unique du type d'épreuve
    protected String libelle;       // Libellé du type d'épreuve

    /**
     * Constructeur par défaut.
     */
    public TypeEpreuve() {
        this.idTypeEpreuve = 0;
        this.libelle = "";
    }

    /**
     * Constructeur avec paramètres.
     */
    public TypeEpreuve(int idTypeEpreuve, String libelle) {
        this.idTypeEpreuve = idTypeEpreuve;
        this.libelle = libelle;
    }

    // ============ ACCESSORS ============
    public int getIdTypeEpreuve() { return idTypeEpreuve; }
    public void setIdTypeEpreuve(int idTypeEpreuve) { this.idTypeEpreuve = idTypeEpreuve; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    @Override
    public String toString() {
        return libelle;
    }
}
