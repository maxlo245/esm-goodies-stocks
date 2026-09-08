package reapproStocks;

/**
 * Classe Motif : représente le motif d'une demande de réapprovisionnement.
 *
 * Liste des motifs :
 *   - R  : réapprovisionnement
 *   - NP : nouveaux produits
 *   - UR : urgence réapprovisionnement
 */
public class Motif {
    // Attributs
    protected String code;      // Code du motif (R, NP, UR)
    protected String libelle;   // Libellé du motif

    /**
     * Constructeur par défaut.
     */
    public Motif() {
        this.code = "";
        this.libelle = "";
    }

    /**
     * Constructeur avec paramètres.
     */
    public Motif(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    // ============ ACCESSORS ============
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    @Override
    public String toString() {
        return code + " - " + libelle;
    }
}
