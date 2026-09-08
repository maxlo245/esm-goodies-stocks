package gestionStocks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de données de l'application Gestion de Stocks (mode démonstration).
 * Centralise les collections (articles, coureurs, types d'épreuve, réservations)
 * et met à disposition les 4 méthodes génériques :
 *   - CREER
 *   - MODIFIER
 *   - CONSULTER
 *   - SUPPRIMER
 * avec des surcharges selon le type d'objet manipulé.
 *
 * Cette classe simule l'accès à la base de données en mémoire, ce qui permet
 * de tester l'application sans avoir à installer PostgreSQL. Un script SQL
 * (voir dossier sql/) permet de transposer ce modèle vers une vraie BDD.
 */
public class GestionStocksData {
    // Collections principales
    private final List<Article> articles;              // Liste des articles (avec héritiers)
    private final List<Coureur> coureurs;              // Liste des coureurs
    private final List<TypeEpreuve> typeEpreuves;      // Liste des types d'épreuve
    private final List<Reservation> reservations;      // Liste des réservations

    // Compteurs d'identifiants (simulation d'auto-incrément)
    private int seqArticle;
    private int seqCoureur;
    private int seqTypeEpreuve;
    private int seqReservation;

    /**
     * Constructeur : initialise les collections et charge des données.
     */
    public GestionStocksData() {
        this.articles = new ArrayList<>();
        this.coureurs = new ArrayList<>();
        this.typeEpreuves = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.seqArticle = 0;
        this.seqCoureur = 0;
        this.seqTypeEpreuve = 0;
        this.seqReservation = 0;
        initialiserDonnees();
    }

    /**
     * Charge les données de démonstration pour rendre l'application utilisable.
     */
    private void initialiserDonnees() {
        // --- Types d'épreuve ---
        typeEpreuves.add(new TypeEpreuve(++seqTypeEpreuve, "La course folle junior"));
        typeEpreuves.add(new TypeEpreuve(++seqTypeEpreuve, "Le semi-marathon"));
        typeEpreuves.add(new TypeEpreuve(++seqTypeEpreuve, "Le marathon de Paris"));

        // --- Coureurs ---
        coureurs.add(new Coureur(++seqCoureur, "Brillat", "Savarin"));
        coureurs.add(new Coureur(++seqCoureur, "Berthollo", "Pierre"));
        coureurs.add(new Coureur(++seqCoureur, "Durand", "Julie"));
        coureurs.add(new Coureur(++seqCoureur, "Petit", "Jean"));

        // --- Articles (avec classes héritières) ---
        articles.add(new Textile(++seqArticle, "Tee-shirt sport", 5, "L", "vert"));
        articles.add(new Textile(++seqArticle, "Maillot officiel", 150, "S", "blanche"));
        articles.add(new Textile(++seqArticle, "Casquette", 25, "M", "noir"));
        articles.add(new Boisson(++seqArticle, "Bouteille d'eau", 200, "50cl"));
        articles.add(new Boisson(++seqArticle, "Boisson énergisante", 90, "33cl"));
        articles.add(new DenreeSeche(++seqArticle, "Barre énergétique", 120, "30g"));
        articles.add(new DenreeSeche(++seqArticle, "Fruits secs", 400, "250g"));

        // --- Une réservation de démonstration ---
        Reservation r = new Reservation(++seqReservation, LocalDate.now(),
                coureurs.get(1), typeEpreuves.get(0));
        r.ajouterLigne(articles.get(0), 1);   // Tee-shirt
        r.ajouterLigne(articles.get(3), 6);   // Bouteille
        r.ajouterLigne(articles.get(5), 12);  // Barre énergétique
        reservations.add(r);
    }

    // =================================================================
    //                          MÉTHODES GÉNÉRIQUES
    // =================================================================

    /**
     * CREER un article (surcharge Article).
     * @param a l'article à créer
     */
    public void CREER(Article a) {
        a.setIdArticle(++seqArticle);
        articles.add(a);
    }

    /**
     * CREER un coureur (surcharge Coureur).
     */
    public void CREER(Coureur c) {
        c.setIdCoureur(++seqCoureur);
        coureurs.add(c);
    }

    /**
     * CREER un type d'épreuve (surcharge TypeEpreuve).
     */
    public void CREER(TypeEpreuve t) {
        t.setIdTypeEpreuve(++seqTypeEpreuve);
        typeEpreuves.add(t);
    }

    /**
     * CREER une réservation (surcharge Reservation).
     */
    public void CREER(Reservation r) {
        r.setIdReservation(++seqReservation);
        reservations.add(r);
    }

    /**
     * MODIFIER un article existant.
     * @param id identifiant de l'article à modifier
     * @param a nouvel article (remplacement des valeurs)
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
     * MODIFIER un coureur existant.
     */
    public void MODIFIER(int id, Coureur c) {
        for (int i = 0; i < coureurs.size(); i++) {
            if (coureurs.get(i).getIdCoureur() == id) {
                c.setIdCoureur(id);
                coureurs.set(i, c);
                return;
            }
        }
    }

    /**
     * MODIFIER une réservation existante.
     */
    public void MODIFIER(int id, Reservation r) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getIdReservation() == id) {
                r.setIdReservation(id);
                reservations.set(i, r);
                return;
            }
        }
    }

    /**
     * CONSULTER un article par son identifiant.
     * @return l'article trouvé ou null
     */
    public Article CONSULTER_Article(int id) {
        for (Article a : articles) {
            if (a.getIdArticle() == id) return a;
        }
        return null;
    }

    /**
     * CONSULTER un coureur par son identifiant.
     */
    public Coureur CONSULTER_Coureur(int id) {
        for (Coureur c : coureurs) {
            if (c.getIdCoureur() == id) return c;
        }
        return null;
    }

    /**
     * CONSULTER un type d'épreuve par son identifiant.
     */
    public TypeEpreuve CONSULTER_TypeEpreuve(int id) {
        for (TypeEpreuve t : typeEpreuves) {
            if (t.getIdTypeEpreuve() == id) return t;
        }
        return null;
    }

    /**
     * SUPPRIMER logiquement un article (SL = true) : il reste en base mais n'est plus visible.
     * @param id identifiant
     */
    public void SUPPRIMER_Article(int id) {
        Article a = CONSULTER_Article(id);
        if (a != null) a.setSl(true);
    }

    /**
     * SUPPRIMER un coureur.
     */
    public void SUPPRIMER_Coureur(int id) {
        coureurs.removeIf(c -> c.getIdCoureur() == id);
    }

    /**
     * SUPPRIMER une réservation.
     */
    public void SUPPRIMER_Reservation(int id) {
        reservations.removeIf(r -> r.getIdReservation() == id);
    }

    // =================================================================
    //                      ACCESSEURS DE COLLECTIONS
    // =================================================================

    public List<Article> getArticles() { return articles; }
    public List<Coureur> getCoureurs() { return coureurs; }
    public List<TypeEpreuve> getTypeEpreuves() { return typeEpreuves; }
    public List<Reservation> getReservations() { return reservations; }

    /**
     * Retourne les articles non supprimés logiquement.
     */
    public List<Article> getArticlesActifs() {
        return articles.stream().filter(a -> !a.isSl()).collect(Collectors.toList());
    }

    /**
     * Retourne les articles en rupture (quantité = 0).
     */
    public List<Article> getArticlesEnRupture() {
        return getArticlesActifs().stream()
                .filter(a -> a.getQuantite() <= 0)
                .collect(Collectors.toList());
    }

    /**
     * Retourne les réservations en attente de validation.
     */
    public List<Reservation> getReservationsEnAttente() {
        return reservations.stream()
                .filter(Reservation::isEnAttente)
                .collect(Collectors.toList());
    }

    /**
     * Ajuste les stocks lors d'une réservation validée.
     * Vérifie que chaque quantité disponible est suffisante.
     * @return true si la réservation peut être effectuée.
     */
    public boolean verifierEtAjusterStocks(Reservation r) {
        // Vérification préalable des quantités
        for (Reservation.LigneReservation ligne : r.getLignes()) {
            Article a = ligne.getArticle();
            if (a.getQuantite() < ligne.getQuantite()) {
                return false;   // pas assez de stock
            }
        }
        // Réajustement des stocks
        for (Reservation.LigneReservation ligne : r.getLignes()) {
            Article a = ligne.getArticle();
            a.setQuantite(a.getQuantite() - ligne.getQuantite());
        }
        return true;
    }

    /**
     * Valide une réservation en attente et réajuste les stocks.
     */
    public boolean validerReservation(Reservation r) {
        boolean ok = verifierEtAjusterStocks(r);
        if (ok) r.setEnAttente(false);
        return ok;
    }
}
