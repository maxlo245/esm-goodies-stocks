package reapproStocks;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Classe principale (point d'entrée) de l'application de
 * Réapprovisionnement des Goodies pour les Athlètes.
 *
 * Projet : esm.reapproStocksGoodies
 * Package : reapproStocks
 *
 * Lance l'interface graphique Swing.
 */
public class Main {

    /**
     * Point d'entrée du programme.
     * @param args arguments (non utilisés)
     */
    public static void main(String[] args) {
        // Utilisation du look and feel système pour un rendu natif
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // on garde le look and feel par défaut en cas d'erreur
        }

        // Création des données de démonstration
        ReapproStocksData data = new ReapproStocksData();

        // Lancement de l'interface graphique dans le thread dédié (EDT)
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame(data);
            frame.setVisible(true);
        });
    }
}
