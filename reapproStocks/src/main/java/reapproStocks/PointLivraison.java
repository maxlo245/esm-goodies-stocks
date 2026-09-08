package reapproStocks;

/**
 * Classe PointLivraison : représente un point (lieu) de livraison
 * des goodies réapprovisionnés.
 * Coordonnées : nom, rue, code postal, ville, téléphone, email.
 */
public class PointLivraison {
    // Attributs
    protected int idPointLivraison;     // Identifiant unique
    protected String nom;               // Nom du point de livraison
    protected String rue;               // Rue
    protected String cp;                // Code postal
    protected String ville;             // Ville
    protected String tel;               // Téléphone
    protected String email;             // Email

    /**
     * Constructeur par défaut.
     */
    public PointLivraison() {
        this.idPointLivraison = 0;
        this.nom = "";
        this.rue = "";
        this.cp = "";
        this.ville = "";
        this.tel = "";
        this.email = "";
    }

    /**
     * Constructeur avec paramètres.
     */
    public PointLivraison(int idPointLivraison, String nom, String rue, String cp,
                          String ville, String tel, String email) {
        this.idPointLivraison = idPointLivraison;
        this.nom = nom;
        this.rue = rue;
        this.cp = cp;
        this.ville = ville;
        this.tel = tel;
        this.email = email;
    }

    // ============ ACCESSORS ============
    public int getIdPointLivraison() { return idPointLivraison; }
    public void setIdPointLivraison(int idPointLivraison) { this.idPointLivraison = idPointLivraison; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getRue() { return rue; }
    public void setRue(String rue) { this.rue = rue; }

    public String getCp() { return cp; }
    public void setCp(String cp) { this.cp = cp; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    /**
     * Adresse complète du point de livraison.
     */
    public String getAdresseComplete() {
        return rue + ", " + cp + " " + ville;
    }

    @Override
    public String toString() {
        return nom + " (" + ville + ")";
    }
}
