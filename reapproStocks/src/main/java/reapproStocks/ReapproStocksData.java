package reapproStocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de données de l'application de Réapprovisionnement des Goodies
 * (mode démonstration, données en mémoire).
 *
 * Centralise les collections (fournisseurs, points de livraison, motifs,
 * articles, réapprovisionnements) et met à disposition les 4 méthodes
 * génériques CRÉER / MODIFIER / CONSULTER / SUPPRIMER avec des surcharges.
 *
 * Un script SQL (voir dossier sql/) permet de transposer ce modèle vers
 * une vraie base PostgreSQL nommée reapproStocks.
 */
public class ReapproStocksData {

    // Collections principales
    private final List<Fournisseur> fournisseurs;            // Liste des fournisseurs
    private final List<PointLivraison> pointsLivraison;      // Liste des points de livraison
    private final List<Motif> motifs;                        // Liste des motifs
    private final List<Article> articles;                    // Liste des articles (avec héritiers)
    private final List<Reapprovisionnement> reapprovisionnements; // Liste des demandes

    // Compteurs d'identifiants (simulation d'auto-incrément)
    private int seqFournisseur;
    private int seqPointLivraison;
    private int seqArticle;
    private int seqReappro;
    /**
     * Constructeur : initialise les collections et charge les données.
     */
    public ReapproStocksData() {
        this.fournisseurs = new ArrayList<>();
        this.pointsLivraison = new ArrayList<>();
        this.motifs = new ArrayList<>();
        this.articles = new ArrayList<>();
        this.reapprovisionnements = new ArrayList<>();
        this.seqFournisseur = 0;
        this.seqPointLivraison = 0;
        this.seqArticle = 0;
        this.seqReappro = 0;
        initialiserDonnees();
    }

    /**
     * Charge les données de démonstration conformément à l'exemple du cahier des charges.
     */
    private void initialiserDonnees() {
        // --- Fournisseurs ---
        fournisseurs.add(new Fournisseur(++seqFournisseur, "Sté du textile", "15 rue Emile Zola", "77000", "Meaux", "01-12-88-89-11", "stedutextile@yahoo.fr"));
        fournisseurs.add(new Fournisseur(++seqFournisseur, "BoissoDistrib", "8 avenue de la Gare", "75011", "Paris", "01-45-67-89-22", "contact@boissodistrib.fr"));
        fournisseurs.add(new Fournisseur(++seqFournisseur, "NutriSèche", "42 rue des Champs", "69003", "Lyon", "04-72-33-44-55", "vente@nutriseche.fr"));

        // --- Points de livraison ---
        pointsLivraison.add(new PointLivraison(++seqPointLivraison, "Entrepôt Bergson", "ZI de la Marre", "94000", "Créteil", "06-12-78-89-11", "entrepot.bergson@outlook.fr"));
        pointsLivraison.add(new PointLivraison(++seqPointLivraison, "Local Saint-Maur", "2 rue des Acacias", "94100", "Saint-Maur-des-Fossés", "06-98-76-54-32", "local.saintmaur@outlook.fr"));

        // --- Motifs ---
        motifs.add(new Motif("R", "Réapprovisionnement"));
        motifs.add(new Motif("NP", "Nouveaux produits"));
        motifs.add(new Motif("UR", "Urgence réapprovisionnement"));

        // --- Articles (avec classes héritières) ---
        articles.add(new ArticleTextile(++seqArticle, "Maillot officiel", 150, "S", "blanche"));
        articles.add(new ArticleTextile(++seqArticle, "Tee-shirt sport", 5, "L", "vert"));
        articles.add(new ArticleBoisson(++seqArticle, "Bouteille d'eau", 200, "50cl"));
        articles.add(new ArticleBoisson(++seqArticle, "Boisson énergisante", 90, "33cl"));
        articles.add(new ArticleDenreeSeche(++seqArticle, "Barre énergétique", 400, "30g"));
        articles.add(new ArticleDenreeSeche(++seqArticle, "Fruits secs", 50, "250g"));

        // --- Un réapprovisionnement de démonstration (exemple du document) ---
        Reapprovisionnement r = new Reapprovisionnement(++seqReappro, LocalDate.now(),
                fournisseurs.get(0), pointsLivraison.get(0), motifs.get(0));
        r.ajouterLigne(articles.get(0), 150);   // Maillot
        r.ajouterLigne(articles.get(2), 200);   // Bouteille
        r.ajouterLigne(articles.get(4), 400);   // Barre énergétique
        reapprovisionnements.add(r);
    }

    // =================================================================
    //                          MÉTHODES GÉNÉRIQUES
    // =================================================================

    /**
     * CREER un fournisseur.
     */
    public void CREER(Fournisseur f) {
        f.setIdFournisseur(++seqFournisseur);
        fournisseurs.add(f);
    }

    /**
     * CREER un point de livraison.
     */
    public void CREER(PointLivraison p) {
        p.setIdPointLivraison(++seqPointLivraison);
        pointsLivraison.add(p);
    }

    /**
     * CREER un article (surcharge Article).
     */
    public void CREER(Article a) {
        a.setIdArticle(++seqArticle);
        articles.add(a);
    }

    /**
     * CREER une demande de réapprovisionnement.
     */
    public void CREER(Reapprovisionnement r) {
        r.setNumeroReappro(++seqReappro);
        reapprovisionnements.add(r);
    }

    /**
     * MODIFIER un fournisseur.
     */
    public void MODIFIER(int id, Fournisseur f) {
        for (int i = 0; i < fournisseurs.size(); i++) {
            if (fournisseurs.get(i).getIdFournisseur() == id) {
                f.setIdFournisseur(id);
                fournisseurs.set(i, f);
                return;
            }
        }
    }

    /**
     * MODIFIER un point de livraison.
     */
    public void MODIFIER(int id, PointLivraison p) {
        for (int i = 0; i < pointsLivraison.size(); i++) {
            if (pointsLivraison.get(i).getIdPointLivraison() == id) {
                p.setIdPointLivraison(id);
                pointsLivraison.set(i, p);
                return;
            }
        }
    }

    /**
     * MODIFIER un article.
     */
    public void MODIFIER(int id, Article a) {
        for (int i = 0; i < articles.size(); i++) {
            if (articles.get(i).getIdArticle() == id) {
                a.setIdArticle(id);
                articles.set(i, a);
                return;
            }
        }
    }

    /**
     * CONSULTER un fournisseur par son identifiant.
     */
    public Fournisseur CONSULTER_Fournisseur(int id) {
        for (Fournisseur f : fournisseurs) {
            if (f.getIdFournisseur() == id) return f;
        }
        return null;
    }

    /**
     * CONSULTER un point de livraison par son identifiant.
     */
    public PointLivraison CONSULTER_PointLivraison(int id) {
        for (PointLivraison p : pointsLivraison) {
            if (p.getIdPointLivraison() == id) return p;
        }
        return null;
    }

    /**
     * CONSULTER un article par son identifiant.
     */
    public Article CONSULTER_Article(int id) {
        for (Article a : articles) {
            if (a.getIdArticle() == id) return a;
        }
        return null;
    }

    /**
     * SUPPRIMER un fournisseur.
     */
    public void SUPPRIMER_Fournisseur(int id) {
        fournisseurs.removeIf(f -> f.getIdFournisseur() == id);
    }

    /**
     * SUPPRIMER un point de livraison.
     */
    public void SUPPRIMER_PointLivraison(int id) {
        pointsLivraison.removeIf(p -> p.getIdPointLivraison() == id);
    }

    /**
     * SUPPRIMER une demande de réapprovisionnement.
     */
    public void SUPPRIMER_Reappro(int numero) {
        reapprovisionnements.removeIf(r -> r.getNumeroReappro() == numero);
    }

    // =================================================================
    //                      ACCESSEURS DE COLLECTIONS
    // =================================================================

    public List<Fournisseur> getFournisseurs() { return fournisseurs; }
    public List<PointLivraison> getPointsLivraison() { return pointsLivraison; }
    public List<Motif> getMotifs() { return motifs; }
    public List<Article> getArticles() { return articles; }
    public List<Reapprovisionnement> getReapprovisionnements() { return reapprovisionnements; }

    /**
     * Retourne les articles devant être réapprovisionnés
     * (quantité stock <= seuil de la catégorie).
     */
    public List<Article> getArticlesAFournir() {
        return articles.stream()
                .filter(Article::doitEtreReapprovisionne)
                .collect(Collectors.toList());
    }

    /**
     * Retourne les réapprovisionnements en date du jour courant pour un fournisseur donné.
     * @param nomFournisseur nom exact du fournisseur
     * @return liste des demandes correspondantes
     */
    public List<Reapprovisionnement> getReapproDuJourPour(String nomFournisseur) {
        LocalDate aujourdhui = LocalDate.now();
        return reapprovisionnements.stream()
                .filter(r -> r.getDate().equals(aujourdhui))
                .filter(r -> r.getFournisseur() != null && nomFournisseur.equals(r.getFournisseur().getNom()))
                .collect(Collectors.toList());
    }
}
