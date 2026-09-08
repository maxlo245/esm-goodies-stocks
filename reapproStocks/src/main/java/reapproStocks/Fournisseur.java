package reapproStocks;

/**
 * Classe Fournisseur : représente un fournisseur chez qui l'on commande
 * les goodies à réapprovisionner.
 * Coordonnées : nom, rue, code postal, ville, téléphone, email.
 */
public class Fournisseur {
    // Attributs
    protected int idFournisseur;        // Identifiant unique
    protected String nom;               // Nom du fournisseur
    protected String rue;               // Rue
    protected String cp;                // Code postal
    protected String ville;             // Ville
    protected String tel;               // Téléphone
    protected String email;             // Email

    /**
     * Constructeur par défaut.
     */
    public Fournisseur() {
        this.idFournisseur = 0;
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
    public Fournisseur(int idFournisseur, String nom, String rue, String cp,
                       String ville, String tel, String email) {
        this.idFournisseur = idFournisseur;
        this.nom = nom;
        this.rue = rue;
        this.cp = cp;
        this.ville = ville;
        this.tel = tel;
        this.email = email;
    }

    // ============ ACCESSORS ============
    public int getIdFournisseur() { return idFournisseur; }
    public void setIdFournisseur(int idFournisseur) { this.idFournisseur = idFournisseur; }

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
     * Adresse complète du fournisseur.
     */
    public String getAdresseComplete() {
        return rue + ", " + cp + " " + ville;
    }

    @Override
    public String toString() {
        return nom + " (" + ville + ")";
    }
}
