package reapproStocks;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Fenêtre principale de l'application de Réapprovisionnement des Goodies.
 * Interface graphique Java Swing.
 *
 * Menu principal :
 *  1- Gestion des fournisseurs
 *  2- Gestion des points de livraison
 *  3- Gestion des articles
 *  4- Gestion des réapprovisionnements
 *  5- Articles à réapprovisionner
 *  6- Consulter l'historique
 *  7- Quitter
 */
public class MainFrame extends JFrame {

    // Service de données partagé
    private final ReapproStocksData data;

    // Cartes graphiques
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);

    // Composants réutilisables
    private JComboBox<Article> comboArticles;
    private JComboBox<Fournisseur> comboFournisseurs;
    private JComboBox<PointLivraison> comboPoints;
    private JTable tableReappro;

    /**
     * Constructeur de la fenêtre principale.
     */
    public MainFrame(ReapproStocksData data) {
        this.data = data;
        initUI();
    }

    /**
     * Initialise l'interface graphique.
     */
    private void initUI() {
        setTitle("Réapprovisionnement des Goodies pour les Athlètes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        setJMenuBar(creerBarreMenu());

        cards.setBorder(new EmptyBorder(10, 10, 10, 10));
        cards.add(creerPanelAccueil(), "accueil");
        cards.add(creerPanelFournisseurs(), "fournisseurs");
        cards.add(creerPanelPointsLivraison(), "points");
        cards.add(creerPanelArticles(), "articles");
        cards.add(creerPanelReappro(), "reappro");
        cards.add(creerPanelArticlesFournir(), "afournir");
        cards.add(creerPanelHistorique(), "historique");

        add(cards, BorderLayout.CENTER);

        JLabel lblStatut = new JLabel("Prêt");
        lblStatut.setBorder(new EmptyBorder(5, 10, 5, 10));
        add(lblStatut, BorderLayout.SOUTH);
    }

    /**
     * Crée la barre de menu.
     */
    private JMenuBar creerBarreMenu() {
        JMenuBar barre = new JMenuBar();

        JMenu menuFourn = new JMenu("Fournisseurs");
        JMenuItem itemFourn = new JMenuItem("Gestion des fournisseurs");
        itemFourn.addActionListener(e -> { rafraichirFournisseurs(); cardLayout.show(cards, "fournisseurs"); });
        menuFourn.add(itemFourn);
        barre.add(menuFourn);

        JMenu menuPoints = new JMenu("Points de livraison");
        JMenuItem itemPoints = new JMenuItem("Gestion des points de livraison");
        itemPoints.addActionListener(e -> { rafraichirPoints(); cardLayout.show(cards, "points"); });
        menuPoints.add(itemPoints);
        barre.add(menuPoints);

        JMenu menuArticles = new JMenu("Articles");
        JMenuItem itemArticles = new JMenuItem("Gestion des articles");
        itemArticles.addActionListener(e -> { rafraichirArticles(); cardLayout.show(cards, "articles"); });
        menuArticles.add(itemArticles);
        barre.add(menuArticles);

        JMenu menuReappro = new JMenu("Réapprovisionnement");
        JMenuItem itemReappro = new JMenuItem("Créer une demande de réapprovisionnement");
        itemReappro.addActionListener(e -> { rafraichirReappro(); cardLayout.show(cards, "reappro"); });
        menuReappro.add(itemReappro);
        barre.add(menuReappro);

        JMenu menuFournir = new JMenu("À réapprovisionner");
        JMenuItem itemFournir = new JMenuItem("Articles à réapprovisionner");
        itemFournir.addActionListener(e -> cardLayout.show(cards, "afournir"));
        menuFournir.add(itemFournir);
        barre.add(menuFournir);

        JMenu menuHistorique = new JMenu("Historique");
        JMenuItem itemHistorique = new JMenuItem("Consulter l'historique");
        itemHistorique.addActionListener(e -> cardLayout.show(cards, "historique"));
        menuHistorique.add(itemHistorique);
        barre.add(menuHistorique);

        return barre;
    }

    // =================================================================
    //                        PANEL ACCUEIL
    // =================================================================
    private JPanel creerPanelAccueil() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titre = new JLabel("Application de Réapprovisionnement des Goodies", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titre, BorderLayout.NORTH);

        JTextArea zone = new JTextArea("""
            Cette application permet de gérer le réapprovisionnement des goodies
            pour les athlètes de l'association Webcourses.

            Fonctionnalités :
              • Gestion des fournisseurs
              • Gestion des points de livraison
              • Gestion des articles (Textile, Boisson, Denrée sèche)
              • Création de demandes de réapprovisionnement
              • Liste des articles à réapprovisionner (selon le seuil)
              • Consultation de l'historique

            Le seuil de réapprovisionnement varie selon la catégorie :
              • Textile : 10
              • Boisson : 100
              • Denrée sèche : 500

            Motifs : R (réapprovisionnement), NP (nouveaux produits), UR (urgence)."""
        );
        zone.setEditable(false);
        zone.setFont(new Font("Arial", Font.PLAIN, 14));
        zone.setBackground(getContentPane().getBackground());
        zone.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(zone, BorderLayout.CENTER);
        return panel;
    }

    // =================================================================
    //                     PANEL FOURNISSEURS
    // =================================================================
    private JPanel creerPanelFournisseurs() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Fournisseur"));

        JTextField txtNom = new JTextField();
        JTextField txtRue = new JTextField();
        JTextField txtCp = new JTextField();
        JTextField txtVille = new JTextField();
        JTextField txtTel = new JTextField();
        JTextField txtEmail = new JTextField();

        form.add(new JLabel("Nom :")); form.add(txtNom);
        form.add(new JLabel("Rue :")); form.add(txtRue);
        form.add(new JLabel("CP :")); form.add(txtCp);
        form.add(new JLabel("Ville :")); form.add(txtVille);
        form.add(new JLabel("Tél :")); form.add(txtTel);
        form.add(new JLabel("Email :")); form.add(txtEmail);

        comboFournisseurs = new JComboBox<>();

        JButton btnAjouter = new JButton("AJOUTER");
        btnAjouter.addActionListener(e -> {
            Fournisseur f = new Fournisseur(0, txtNom.getText().trim(), txtRue.getText().trim(),
                    txtCp.getText().trim(), txtVille.getText().trim(), txtTel.getText().trim(), txtEmail.getText().trim());
            data.CREER(f);
            JOptionPane.showMessageDialog(this, "Fournisseur créé avec l'ID " + f.getIdFournisseur(), "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            rafraichirFournisseurs();
            txtNom.setText(""); txtRue.setText(""); txtCp.setText(""); txtVille.setText(""); txtTel.setText(""); txtEmail.setText("");
        });

        panel.add(titre("Gestion des fournisseurs"), BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        JPanel east = new JPanel(new BorderLayout());
        east.add(new JScrollPane(comboFournisseurs), BorderLayout.CENTER);
        east.add(btnAjouter, BorderLayout.SOUTH);
        panel.add(east, BorderLayout.EAST);
        return panel;
    }

    // =================================================================
    //                  PANEL POINTS DE LIVRAISON
    // =================================================================
    private JPanel creerPanelPointsLivraison() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Point de livraison"));

        JTextField txtNom = new JTextField();
        JTextField txtRue = new JTextField();
        JTextField txtCp = new JTextField();
        JTextField txtVille = new JTextField();
        JTextField txtTel = new JTextField();
        JTextField txtEmail = new JTextField();

        form.add(new JLabel("Nom :")); form.add(txtNom);
        form.add(new JLabel("Rue :")); form.add(txtRue);
        form.add(new JLabel("CP :")); form.add(txtCp);
        form.add(new JLabel("Ville :")); form.add(txtVille);
        form.add(new JLabel("Tél :")); form.add(txtTel);
        form.add(new JLabel("Email :")); form.add(txtEmail);

        comboPoints = new JComboBox<>();

        JButton btnAjouter = new JButton("AJOUTER");
        btnAjouter.addActionListener(e -> {
            PointLivraison p = new PointLivraison(0, txtNom.getText().trim(), txtRue.getText().trim(),
                    txtCp.getText().trim(), txtVille.getText().trim(), txtTel.getText().trim(), txtEmail.getText().trim());
            data.CREER(p);
            JOptionPane.showMessageDialog(this, "Point de livraison créé avec l'ID " + p.getIdPointLivraison(), "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            rafraichirPoints();
            txtNom.setText(""); txtRue.setText(""); txtCp.setText(""); txtVille.setText(""); txtTel.setText(""); txtEmail.setText("");
        });

        panel.add(titre("Gestion des points de livraison"), BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        JPanel east = new JPanel(new BorderLayout());
        east.add(new JScrollPane(comboPoints), BorderLayout.CENTER);
        east.add(btnAjouter, BorderLayout.SOUTH);
        panel.add(east, BorderLayout.EAST);
        return panel;
    }

    // =================================================================
    //                        PANEL ARTICLES
    // =================================================================
    private JPanel creerPanelArticles() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Article"));

        JTextField txtLibelle = new JTextField();
        JComboBox<String> comboCategorie = new JComboBox<>(new String[]{"T", "B", "DS"});
        JTextField txtTaille = new JTextField();
        JTextField txtCouleur = new JTextField();
        JTextField txtContenance = new JTextField();
        JTextField txtPoids = new JTextField();
        JTextField txtQuantite = new JTextField("10");

        form.add(new JLabel("Libellé :")); form.add(txtLibelle);
        form.add(new JLabel("Catégorie (T/B/DS) :")); form.add(comboCategorie);
        form.add(new JLabel("Taille (textile) :")); form.add(txtTaille);
        form.add(new JLabel("Couleur (textile) :")); form.add(txtCouleur);
        form.add(new JLabel("Contenance (boisson) :")); form.add(txtContenance);
        form.add(new JLabel("Poids (denrée sèche) :")); form.add(txtPoids);
        form.add(new JLabel("Quantité stock :")); form.add(txtQuantite);

        comboArticles = new JComboBox<>();

        JButton btnAjouter = new JButton("AJOUTER");
        btnAjouter.addActionListener(e -> {
            String cat = (String) comboCategorie.getSelectedItem();
            int qte = Integer.parseInt(txtQuantite.getText().trim());
            Article a = construireArticle(0, txtLibelle.getText().trim(), qte, cat,
                    txtTaille.getText(), txtCouleur.getText(), txtContenance.getText(), txtPoids.getText());
            data.CREER(a);
            JOptionPane.showMessageDialog(this, "Article créé avec l'ID " + a.getIdArticle() + " (seuil : " + a.getSeuilReappro() + ")", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            rafraichirArticles();
        });

        panel.add(titre("Gestion des articles"), BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        JPanel east = new JPanel(new BorderLayout());
        east.add(new JScrollPane(comboArticles), BorderLayout.CENTER);
        east.add(btnAjouter, BorderLayout.SOUTH);
        panel.add(east, BorderLayout.EAST);
        return panel;
    }

    /**
     * Construit un article selon sa catégorie.
     */
    private Article construireArticle(int id, String libelle, int qte, String cat,
                                      String taille, String couleur, String contenance, String poids) {
        if ("T".equals(cat)) return new ArticleTextile(id, libelle, qte, taille, couleur);
        if ("B".equals(cat)) return new ArticleBoisson(id, libelle, qte, contenance);
        return new ArticleDenreeSeche(id, libelle, qte, poids);
    }

    // =================================================================
    //                    PANEL RÉAPPROVISIONNEMENT
    // =================================================================
    private JPanel creerPanelReappro() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel choix = new JPanel(new GridLayout(0, 2, 8, 8));
        choix.setBorder(BorderFactory.createTitledBorder("Nouvelle demande de réapprovisionnement"));

        JComboBox<Fournisseur> comboFourn = new JComboBox<>();
        JComboBox<PointLivraison> comboPoint = new JComboBox<>();
        JComboBox<Motif> comboMotif = new JComboBox<>();
        JComboBox<Article> comboArticle = new JComboBox<>();
        JTextField txtQte = new JTextField("1");

        for (Fournisseur f : data.getFournisseurs()) comboFourn.addItem(f);
        for (PointLivraison p : data.getPointsLivraison()) comboPoint.addItem(p);
        for (Motif m : data.getMotifs()) comboMotif.addItem(m);
        for (Article a : data.getArticles()) comboArticle.addItem(a);

        choix.add(new JLabel("Fournisseur :")); choix.add(comboFourn);
        choix.add(new JLabel("Point de livraison :")); choix.add(comboPoint);
        choix.add(new JLabel("Motif :")); choix.add(comboMotif);
        choix.add(new JLabel("Article :")); choix.add(comboArticle);
        choix.add(new JLabel("Quantité :")); choix.add(txtQte);

        DefaultListModel<String> modelLignes = new DefaultListModel<>();
        JList<String> listLignes = new JList<>(modelLignes);
        listLignes.setBorder(BorderFactory.createTitledBorder("Articles de la demande"));

        JButton btnAjouterLigne = new JButton("Ajouter la ligne");
        btnAjouterLigne.addActionListener(e -> {
            Article a = (Article) comboArticle.getSelectedItem();
            try {
                int qte = Integer.parseInt(txtQte.getText().trim());
                modelLignes.addElement(a.getLibelle() + " x" + qte + "  [" + a.getIdArticle() + "]");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantité invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnCreer = new JButton("CRÉER LA DEMANDE");
        btnCreer.addActionListener(e -> {
            Fournisseur f = (Fournisseur) comboFourn.getSelectedItem();
            PointLivraison p = (PointLivraison) comboPoint.getSelectedItem();
            Motif m = (Motif) comboMotif.getSelectedItem();
            if (f == null || p == null || m == null) return;
            Reapprovisionnement r = new Reapprovisionnement(0, LocalDate.now(), f, p, m);
            for (int i = 0; i < modelLignes.size(); i++) {
                String ligne = modelLignes.get(i);
                String idStr = ligne.substring(ligne.lastIndexOf('[') + 1, ligne.lastIndexOf(']'));
                String qteStr = ligne.substring(ligne.indexOf('x') + 1, ligne.lastIndexOf("  [")).trim();
                int id = Integer.parseInt(idStr);
                int qte = Integer.parseInt(qteStr);
                Article a = data.CONSULTER_Article(id);
                r.ajouterLigne(a, qte);
            }
            if (r.getLignes().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez ajouter au moins une ligne.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            data.CREER(r);
            JOptionPane.showMessageDialog(this, "Demande de réapprovisionnement n°" + r.getNumeroReappro() + " créée.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            modelLignes.clear();
            rafraichirReappro();
        });

        tableReappro = new JTable();

        panel.add(titre("Gestion des réapprovisionnements"), BorderLayout.NORTH);
        JPanel haut = new JPanel(new BorderLayout());
        haut.add(choix, BorderLayout.NORTH);
        JPanel boutons = new JPanel(new FlowLayout());
        boutons.add(btnAjouterLigne);
        boutons.add(btnCreer);
        haut.add(boutons, BorderLayout.CENTER);
        panel.add(haut, BorderLayout.CENTER);
        panel.add(listLignes, BorderLayout.EAST);
        panel.add(new JScrollPane(tableReappro), BorderLayout.SOUTH);
        return panel;
    }

    // =================================================================
    //               PANEL ARTICLES À RÉAPPROVISIONNER
    // =================================================================
    private JPanel creerPanelArticlesFournir() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JTextArea txt = new JTextArea();
        txt.setEditable(false);
        JButton btnActualiser = new JButton("Actualiser la liste");
        btnActualiser.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Articles à réapprovisionner (stock <= seuil)\n");
            for (Article a : data.getArticlesAFournir()) {
                sb.append("  • ").append(a).append("\n");
            }
            if (data.getArticlesAFournir().isEmpty()) sb.append("Aucun article à réapprovisionner.");
            txt.setText(sb.toString());
        });
        panel.add(titre("Articles à réapprovisionner"), BorderLayout.NORTH);
        panel.add(new JScrollPane(txt), BorderLayout.CENTER);
        panel.add(btnActualiser, BorderLayout.SOUTH);
        return panel;
    }

    // =================================================================
    //                       PANEL HISTORIQUE
    // =================================================================
    private JPanel creerPanelHistorique() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel params = new JPanel(new GridLayout(0, 2, 8, 8));
        params.setBorder(BorderFactory.createTitledBorder("Consultation"));

        JButton btnFournisseurJour = new JButton("Réappro du jour pour « Sté du textile »");
        JButton btnTout = new JButton("Afficher toutes les demandes");

        JTextArea txtResult = new JTextArea();
        txtResult.setEditable(false);

        btnFournisseurJour.addActionListener(e -> {
            txtResult.setText("Réapprovisionnements du jour pour Sté du textile\n");
            int n = 0;
            for (Reapprovisionnement r : data.getReapproDuJourPour("Sté du textile")) {
                txtResult.append("  - " + r + "\n");
                n++;
            }
            if (n == 0) txtResult.append("Aucune demande.");
        });

        btnTout.addActionListener(e -> {
            txtResult.setText("Historique des réapprovisionnements\n");
            for (Reapprovisionnement r : data.getReapprovisionnements()) {
                txtResult.append("  - " + r + "\n");
                for (Reapprovisionnement.LigneReappro l : r.getLignes()) {
                    txtResult.append("        • " + l + "\n");
                }
            }
        });

        params.add(btnFournisseurJour);
        params.add(btnTout);

        panel.add(titre("Consulter l'historique"), BorderLayout.NORTH);
        panel.add(params, BorderLayout.NORTH);
        panel.add(new JScrollPane(txtResult), BorderLayout.CENTER);
        return panel;
    }

    // =================================================================
    //                       OUTILS COMMUNS
    // =================================================================
    private JLabel titre(String texte) {
        JLabel lbl = new JLabel(texte);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setBorder(new EmptyBorder(0, 0, 10, 0));
        return lbl;
    }

    private void rafraichirFournisseurs() {
        if (comboFournisseurs != null) {
            comboFournisseurs.removeAllItems();
            for (Fournisseur f : data.getFournisseurs()) comboFournisseurs.addItem(f);
        }
    }

    private void rafraichirPoints() {
        if (comboPoints != null) {
            comboPoints.removeAllItems();
            for (PointLivraison p : data.getPointsLivraison()) comboPoints.addItem(p);
        }
    }

    private void rafraichirArticles() {
        if (comboArticles != null) {
            comboArticles.removeAllItems();
            for (Article a : data.getArticles()) comboArticles.addItem(a);
        }
    }

    private void rafraichirReappro() {
        if (tableReappro != null) {
            String[] colonnes = {"N°", "Date", "Fournisseur", "Point de livraison", "Motif", "Articles"};
            Object[][] lignes = new Object[data.getReapprovisionnements().size()][6];
            int i = 0;
            for (Reapprovisionnement r : data.getReapprovisionnements()) {
                lignes[i][0] = r.getNumeroReappro();
                lignes[i][1] = r.getDate().toString();
                lignes[i][2] = r.getFournisseur() != null ? r.getFournisseur().getNom() : "";
                lignes[i][3] = r.getPointLivraison() != null ? r.getPointLivraison().getNom() : "";
                lignes[i][4] = r.getMotif() != null ? r.getMotif().getCode() : "";
                StringBuilder sb = new StringBuilder();
                for (Reapprovisionnement.LigneReappro l : r.getLignes()) {
                    if (sb.length() > 0) sb.append(", ");
                    sb.append(l);
                }
                lignes[i][5] = sb.toString();
                i++;
            }
            tableReappro.setModel(new javax.swing.table.DefaultTableModel(lignes, colonnes));
        }
    }
}
