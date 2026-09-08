package reapproStocks;

import javax.swing.JOptionPane;
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
        SwingUtilities.invokeLater(() -> creerEtAfficherFenetre(data));
    }

    /**
     * Crée la fenêtre principale, l'affiche et la place au premier plan.
     */
    private static void creerEtAfficherFenetre(ReapproStocksData data) {
        try {
            MainFrame frame = new MainFrame(data);
            frame.setVisible(true);
            frame.setLocation(100, 100);          // position fixe sur l'écran
            frame.toFront();                      // met la fenêtre au premier plan
            frame.requestFocus();                 // donne le focus
            frame.setAlwaysOnTop(true);           // s'assure qu'elle passe au-dessus
            java.awt.EventQueue.invokeLater(() -> {
                frame.setAlwaysOnTop(false);      // après affichage, on retire le "toujours au-dessus"
                frame.toFront();
            });
        } catch (Throwable t) {
            // En cas d'erreur au démarrage, on l'affiche clairement
            System.err.println("Erreur au lancement de l'application :");
            t.printStackTrace();
            try {
                JOptionPane.showMessageDialog(null,
                    "Impossible de démarrer l'application :\n" + t.getMessage() +
                    "\n\nPlus de détails dans la console.",
                    "Erreur de démarrage", JOptionPane.ERROR_MESSAGE);
            } catch (Throwable t2) {
                System.err.println("Erreur supplémentaire lors de l'affichage du message : " + t2.getMessage());
            }
        }
    }
}
