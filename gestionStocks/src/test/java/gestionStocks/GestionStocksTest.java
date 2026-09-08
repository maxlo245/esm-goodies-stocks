package gestionStocks;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests unitaires de l'application Gestion de Stocks.
 * Valide les cas extrêmes : règle « NE JAMAIS FAIRE CONFIANCE A UN UTILISATEUR ».
 */
public class GestionStocksTest {

    /**
     * Test de création d'un article textile.
     */
    @Test
    public void testCreerArticleTextile() {
        GestionStocksData data = new GestionStocksData();
        Textile t = new Textile(0, "Maillot test", 10, "M", "bleu");
        data.CREER(t);
        assertTrue(t.getIdArticle() > 0, "L'article doit recevoir un identifiant");
        assertEquals("T", t.getCategorie());
        assertEquals("M", t.getTaille());
        assertEquals("bleu", t.getCouleur());
    }

    /**
     * Test de création d'un coureur avec des valeurs vides (cas extrême).
     */
    @Test
    public void testCreerCoureurVide() {
        GestionStocksData data = new GestionStocksData();
        Coureur c = new Coureur(0, "", "");
        data.CREER(c);
        assertTrue(c.getIdCoureur() > 0, "Même un coureur vide doit avoir un ID");
    }

    /**
     * Test que le stock insuffisant place la réservation en attente.
     */
    @Test
    public void testReservationStockInsuffisantEnAttente() {
        GestionStocksData data = new GestionStocksData();
        Article maillot = data.getArticles().get(0); // Tee-shirt stock 5
        Reservation r = new Reservation(0, LocalDate.now(), data.getCoureurs().get(0), data.getTypeEpreuves().get(0));
        r.ajouterLigne(maillot, 100);   // demande 100 alors que stock = 5
        boolean ok = data.verifierEtAjusterStocks(r);
        assertFalse(ok, "La réservation doit échouer si stock insuffisant");
        assertEquals(5, maillot.getQuantite(), "Le stock ne doit pas être débité si la réservation échoue");
    }

    /**
     * Test qu'une réservation valide débite correctement le stock.
     */
    @Test
    public void testReservationValideDebiteStock() {
        GestionStocksData data = new GestionStocksData();
        Article a = data.getArticles().get(0);  // stock 5
        int avant = a.getQuantite();
        Reservation r = new Reservation(0, LocalDate.now(), data.getCoureurs().get(0), data.getTypeEpreuves().get(0));
        r.ajouterLigne(a, 2);
        boolean ok = data.verifierEtAjusterStocks(r);
        assertTrue(ok, "Réservation de 2 sur un stock de 5 doit fonctionner");
        assertEquals(avant - 2, a.getQuantite(), "Le stock doit être débité");
    }

    /**
     * Test de suppression logique d'un article.
     */
    @Test
    public void testSuppressionLogique() {
        GestionStocksData data = new GestionStocksData();
        Article a = data.getArticles().get(0);
        data.SUPPRIMER_Article(a.getIdArticle());
        assertTrue(a.isSl(), "L'article doit être marqué supprimé logiquement");
        assertFalse(data.getArticlesActifs().contains(a), "L'article supprimé ne doit plus être actif");
    }

    /**
     * Test de liste des articles en rupture.
     */
    @Test
    public void testListeArticlesEnRupture() {
        GestionStocksData data = new GestionStocksData();
        // Créer un article avec quantite 0
        Article a = new Textile(0, "Article épuisé", 0, "S", "noir");
        data.CREER(a);
        List<Article> rupture = data.getArticlesEnRupture();
        assertTrue(rupture.contains(a), "Un article à 0 doit figurer dans les articles en rupture");
    }
}
