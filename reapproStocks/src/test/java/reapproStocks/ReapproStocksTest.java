package reapproStocks;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires de l'application de Réapprovisionnement des Goodies.
 * Valide les seuils par catégorie et les cas extrêmes.
 */
public class ReapproStocksTest {

    /**
     * Test que le seuil de réapprovisionnement dépend de la catégorie.
     */
    @Test
    public void testSeuilsParCategorie() {
        ArticleTextile textile = new ArticleTextile(0, "Maillot", 10, "M", "bleu");
        ArticleBoisson boisson = new ArticleBoisson(0, "Bouteille", 200, "50cl");
        ArticleDenreeSeche ds = new ArticleDenreeSeche(0, "Barre", 400, "30g");

        assertEquals(10, textile.getSeuilReappro(), "Seuil textile = 10");
        assertEquals(100, boisson.getSeuilReappro(), "Seuil boisson = 100");
        assertEquals(500, ds.getSeuilReappro(), "Seuil denrée sèche = 500");
    }

    /**
     * Test que le réappro est déclenché quand la quantité est <= au seuil.
     */
    @Test
    public void testDoitEtreReapprovisionne() {
        ArticleTextile textile = new ArticleTextile(0, "Maillot", 5, "M", "bleu"); // seuil 10, stock 5
        assertTrue(textile.doitEtreReapprovisionne(), "Stock 5 <= seuil 10 => réappro nécessaire");

        ArticleBoisson boisson = new ArticleBoisson(0, "Bouteille", 200, "50cl"); // seuil 100, stock 200
        assertFalse(boisson.doitEtreReapprovisionne(), "Stock 200 > seuil 100 => pas de réappro");
    }

    /**
     * Test de création d'un fournisseur.
     */
    @Test
    public void testCreerFournisseur() {
        ReapproStocksData data = new ReapproStocksData();
        Fournisseur f = new Fournisseur(0, "Fourn Test", "1 rue", "75000", "Paris", "01", "mail");
        data.CREER(f);
        assertTrue(f.getIdFournisseur() > 0, "Le fournisseur doit avoir un ID");
    }

    /**
     * Test de recherche des réappro du jour pour un fournisseur.
     */
    @Test
    public void testReapproDuJourPourFournisseur() {
        ReapproStocksData data = new ReapproStocksData();
        List<Reapprovisionnement> liste = data.getReapproDuJourPour("Sté du textile");
        assertTrue(liste.size() >= 1, "Il doit y avoir au moins un réappro du jour pour Sté du textile");
    }

    /**
     * Test de création d'une demande de réapprovisionnement.
     */
    @Test
    public void testCreerReappro() {
        ReapproStocksData data = new ReapproStocksData();
        Reapprovisionnement r = new Reapprovisionnement(0, LocalDate.now(),
                data.getFournisseurs().get(0), data.getPointsLivraison().get(0), data.getMotifs().get(0));
        r.ajouterLigne(data.getArticles().get(0), 10);
        data.CREER(r);
        assertTrue(r.getNumeroReappro() > 0, "La demande doit avoir un numéro");
        assertEquals(1, r.getLignes().size(), "La demande doit contenir 1 ligne");
    }
}
