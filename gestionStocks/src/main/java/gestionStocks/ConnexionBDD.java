package gestionStocks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitaire de connexion à la base de données PostgreSQL.
 *
 * En mode démonstration, l'application fonctionne en mémoire via GestionStocksData.
 * Cette classe permet de basculer vers une vraie base de données PostgreSQL
 * en configurant les paramètres de connexion ci-dessous et en créant la
 * base gestionStocks avec le script fourni (voir dossier sql/).
 *
 * Gestion du garbage collector et des exceptions : try/catch/finally.
 */
public class ConnexionBDD {

    // Paramètres de connexion (à adapter selon votre installation PostgreSQL)
    private static final String URL      = "jdbc:postgresql://localhost:5432/gestionStocks";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "postgres";

    // Objet de connexion partagé
    private static Connection connexion = null;

    /**
     * Retourne la connexion à la base de données (ouverture si nécessaire).
     * @return une connexion JDBC
     * @throws SQLException en cas de problème de connexion
     */
    public static Connection getConnexion() throws SQLException {
        try {
            // Ouverture de la connexion si elle n'existe pas ou est fermée
            if (connexion == null || connexion.isClosed()) {
                connexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion à la base gestionStocks établie.");
            }
            return connexion;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base : " + e.getMessage());
            throw e;
        }
    }

    /**
     * Ferme la connexion et libère les ressources (garbage collector manuel).
     */
    public static void fermerConnexion() {
        try {
            if (connexion != null && !connexion.isClosed()) {
                connexion.close();
                System.out.println("Connexion fermée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        } finally {
            // Libération de la référence pour permettre au garbage collector
            // de récupérer la mémoire utilisée.
            connexion = null;
            System.gc();
        }
    }
}
