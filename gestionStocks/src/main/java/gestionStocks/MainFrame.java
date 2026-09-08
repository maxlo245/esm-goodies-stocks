package gestionStocks;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;

/**
 * Fenêtre principale de l'application Gestion de Stocks des Athlètes.
 * Interface graphique Java Swing conforme au cahier des charges.
 *
 * Menu principal :
 *  1- Gestion des articles
 *  2- Gestion des coureurs
 *  3- Gestion des types d'épreuve
 *  4- Gestion des réservations
 *  5- Article en rupture / réservation en attente
 *  6- Consulter l'historique
 *  7- Quitter
 */
public class MainFrame extends JFrame {

    // Service de données partagé
    private final GestionStocksData data;

    // Cartes graphiques
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);

    // Composants réutilisables interdépendants
    private JComboBox<Article> comboArticles;
    private JComboBox<Coureur> comboCoureurs;
    private JComboBox<TypeEpreuve> comboTypos;
    private JTable tableReservations;
    private DefaultListModel<Reservation> modelAttente;

    /**
     * Constructeur de la fenêtre principale.
     */
    public MainFrame(GestionStocksData data) {
        this.data = data;
        initUI();
    }

    /**
     * Initialise l'interface graphique.
     */
    private void initUI() {
        setTitle("Gestion de Stocks des Athlètes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Barre de menu en haut
        setJMenuBar(creerBarreMenu());

        // Panneau central avec cartes
        cards.setBorder(new EmptyBorder(10, 10, 10, 10));

        cards.add(creerPanelDashboard(), "accueil");
        cards.add(creerPanelArticles(), "articles");
        cards.add(creerPanelCoureurs(), "coureurs");
        cards.add(creerPanelTypesEpreuve(), "types");
        cards.add(creerPanelReservations(), "reservations");
        cards.add(creerPanelRuptureAttente(), "rupture");
        cards.add(creerPanelHistorique(), "historique");

        add(cards, BorderLayout.CENTER);

        // Barre de statut
        JLabel lblStatut = new JLabel("Prêt");
        lblStatut.setBorder(new EmptyBorder(5, 10, 5, 10));
        add(lblStatut, BorderLayout.SOUTH);
    }

    /**
     * Crée la barre de menu.
     */
    private JMenuBar creerBarreMenu() {
        JMenuBar barre = new JMenuBar();

        JMenu menuAccueil = new JMenu("Accueil");
        JMenuItem itemAccueil = new JMenuItem("Tableau de bord");
        itemAccueil.addActionListener(e -> cardLayout.show(cards, "accueil"));
        menuAccueil.add(itemAccueil);
        barre.add(menuAccueil);

        JMenu menuArticles = new JMenu("Articles");
        JMenuItem itemArticles = new JMenuItem("Gestion des articles");
        itemArticles.addActionListener(e -> { rafraichirArticles(); cardLayout.show(cards, "articles"); });
        menuArticles.add(itemArticles);
        barre.add(menuArticles);

        JMenu menuCoureurs = new JMenu("Coureurs");
        JMenuItem itemCoureurs = new JMenuItem("Gestion des coureurs");
        itemCoureurs.addActionListener(e -> { rafraichirCoureurs(); cardLayout.show(cards, "coureurs"); });
        menuCoureurs.add(itemCoureurs);
        barre.add(menuCoureurs);

        JMenu menuTypes = new JMenu("Types d'épreuve");
        JMenuItem itemTypes = new JMenuItem("Gestion des types d'épreuve");
        itemTypes.addActionListener(e -> { rafraichirTypes(); cardLayout.show(cards, "types"); });
        menuTypes.add(itemTypes);
        barre.add(menuTypes);

        JMenu menuReservations = new JMenu("Réservations");
        JMenuItem itemReservations = new JMenuItem("Gestion des réservations");
        itemReservations.addActionListener(e -> { rafraichirReservations(); cardLayout.show(cards, "reservations"); });
        menuReservations.add(itemReservations);
        barre.add(menuReservations);

        JMenu menuRupture = new JMenu("Rupture / Attente");
        JMenuItem itemRupture = new JMenuItem("Rupture & réservations en attente");
        itemRupture.addActionListener(e -> { rafraichirRupture(); cardLayout.show(cards, "rupture"); });
        menuRupture.add(itemRupture);
        barre.add(menuRupture);

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
    private JPanel creerPanelDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titre = new JLabel("Bienvenue dans l'application de Gestion de Stocks des Athlètes", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titre, BorderLayout.NORTH);

        JTextArea zone = new JTextArea("""
            Cette application gère les stocks de goodies destinés aux athlètes.

            Fonctionnalités :
              • Gestion des articles (Textile, Boisson, Denrée sèche)
              • Gestion des coureurs
              • Gestion des types d'épreuve
              • Création de réservations avec contrôle des stocks
              • Gestion des articles en rupture et réservations en attente
              • Consultation de l'historique

            Utilisez le menu ci-dessus pour naviguer."""
        );
        zone.setEditable(false);
        zone.setFont(new Font("Arial", Font.PLAIN, 14));
        zone.setBackground(getContentPane().getBackground());
        zone.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(zone, BorderLayout.CENTER);
        return panel;
    }

    // =================================================================
    //                        PANEL ARTICLES
    // =================================================================
    private JPanel creerPanelArticles() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // ===== Formulaire de saisie =====
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Article"));

        JTextField txtLibelle = new JTextField();
        JComboBox<String> comboCategorie = new JComboBox<>(new String[]{"T", "B", "DS"});
        JTextField txtTaille = new JTextField();
        JTextField txtCouleur = new JTextField();
        JTextField txtVolume = new JTextField();
        JTextField txtPoids = new JTextField();
        JTextField txtQuantite = new JTextField("10");

        form.add(new JLabel("Libellé :")); form.add(txtLibelle);
        form.add(new JLabel("Catégorie (T/B/DS) :")); form.add(comboCategorie);
        form.add(new JLabel("Taille (textile) :")); form.add(txtTaille);
        form.add(new JLabel("Couleur (textile) :")); form.add(txtCouleur);
        form.add(new JLabel("Volume (boisson) :")); form.add(txtVolume);
        form.add(new JLabel("Poids (denrée sèche) :")); form.add(txtPoids);
        form.add(new JLabel("Quantité stock :")); form.add(txtQuantite);

        // ===== Liste des articles =====
        comboArticles = new JComboBox<>();
        JButton btnAfficher = new JButton("Afficher l'article sélectionné");
        btnAfficher.addActionListener(e -> {
            Article a = (Article) comboArticles.getSelectedItem();
            if (a != null) {
                txtLibelle.setText(a.getLibelle());
                comboCategorie.setSelectedItem(a.getCategorie());
                txtQuantite.setText(String.valueOf(a.getQuantite()));
                if (a instanceof Textile t) {
                    txtTaille.setText(t.getTaille());
                    txtCouleur.setText(t.getCouleur());
                    txtVolume.setText(""); txtPoids.setText("");
                } else if (a instanceof Boisson b) {
                    txtVolume.setText(b.getVolume());
                    txtTaille.setText(""); txtCouleur.setText(""); txtPoids.setText("");
                } else if (a instanceof DenreeSeche d) {
                    txtPoids.setText(d.getPoids());
                    txtTaille.setText(""); txtCouleur.setText(""); txtVolume.setText("");
                }
            }
        });

        // ===== Boutons d'action =====
        JPanel boutons = new JPanel(new FlowLayout());
        JButton btnAjouter = new JButton("AJOUTER");
        btnAjouter.addActionListener(e -> {
            try {
                String libelle = txtLibelle.getText().trim();
                String cat = (String) comboCategorie.getSelectedItem();
                int qte = Integer.parseInt(txtQuantite.getText().trim());
                Article article = construireArticle(0, libelle, qte, cat, txtTaille.getText(), txtCouleur.getText(), txtVolume.getText(), txtPoids.getText());
                data.CREER(article);
                JOptionPane.showMessageDialog(this, "Article créé avec succès. ID : " + article.getIdArticle(), "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                rafraichirArticles();
                viderChamps(txtLibelle, txtTaille, txtCouleur, txtVolume, txtPoids);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantité invalide. Veuillez saisir un entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnModifier = new JButton("MODIFIER");
        btnModifier.addActionListener(e -> {
            Article a = (Article) comboArticles.getSelectedItem();
            if (a == null) return;
            String libelle = txtLibelle.getText().trim();
            String cat = (String) comboCategorie.getSelectedItem();
            int qte = Integer.parseInt(txtQuantite.getText().trim());
            Article nouveau = construireArticle(a.getIdArticle(), libelle, qte, cat, txtTaille.getText(), txtCouleur.getText(), txtVolume.getText(), txtPoids.getText());
            data.MODIFIER(a.getIdArticle(), nouveau);
            JOptionPane.showMessageDialog(this, "Article modifié.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            rafraichirArticles();
        });

        JButton btnSupprimer = new JButton("SUPPRESSION LOGIQUE");
        btnSupprimer.addActionListener(e -> {
            Article a = (Article) comboArticles.getSelectedItem();
            if (a == null) return;
            if (JOptionPane.showConfirmDialog(this, "Confirmer la suppression logique de l'article ?", "Attention", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                data.SUPPRIMER_Article(a.getIdArticle());
                rafraichirArticles();
            }
        });

        boutons.add(btnAjouter);
        boutons.add(btnModifier);
        boutons.add(btnSupprimer);

        JPanel centre = new JPanel(new BorderLayout(10, 10));
        centre.add(comboArticles, BorderLayout.NORTH);
        centre.add(btnAfficher, BorderLayout.SOUTH);

        JPanel est = new JPanel(new BorderLayout());
        est.add(form, BorderLayout.NORTH);
        est.add(boutons, BorderLayout.SOUTH);

        panel.add(creerZoneTitres("Gestion des articles"), BorderLayout.NORTH);
        panel.add(centre, BorderLayout.CENTER);
        panel.add(est, BorderLayout.EAST);
        return panel;
    }

    /**
     * Construit un article selon sa catégorie (Textile / Boisson / Denrée sèche).
     */
    private Article construireArticle(int id, String libelle, int qte, String cat,
                                      String taille, String couleur, String volume, String poids) {
        if ("T".equals(cat)) return new Textile(id, libelle, qte, taille, couleur);
        if ("B".equals(cat)) return new Boisson(id, libelle, qte, volume);
        return new DenreeSeche(id, libelle, qte, poids);
    }

    private void viderChamps(JTextField... champs) {
        for (JTextField c : champs) c.setText("");
    }

    // =================================================================
    //                        PANEL COUREURS
    // =================================================================
    private JPanel creerPanelCoureurs() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Nouveau coureur"));
        JTextField txtNom = new JTextField();
        JTextField txtPrenom = new JTextField();
        form.add(new JLabel("Nom :")); form.add(txtNom);
        form.add(new JLabel("Prénom :")); form.add(txtPrenom);

        comboCoureurs = new JComboBox<>();

        JButton btnAjouter = new JButton("AJOUTER");
        btnAjouter.addActionListener(e -> {
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            if (nom.isEmpty() || prenom.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nom et prénom sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Coureur c = new Coureur(0, nom, prenom);
            data.CREER(c);
            JOptionPane.showMessageDialog(this, "Coureur créé avec l'ID " + c.getIdCoureur(), "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            rafraichirCoureurs();
            txtNom.setText(""); txtPrenom.setText("");
        });

        panel.add(creerZoneTitres("Gestion des coureurs"), BorderLayout.NORTH);
        panel.add(comboCoureurs, BorderLayout.CENTER);
        JPanel est = new JPanel(new BorderLayout());
        est.add(form, BorderLayout.NORTH);
        est.add(btnAjouter, BorderLayout.SOUTH);
        panel.add(est, BorderLayout.EAST);
        return panel;
    }

    // =================================================================
    //                    PANEL TYPES D'ÉPREUVE
    // =================================================================
    private JPanel creerPanelTypesEpreuve() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel form = new JPanel(new BorderLayout());
        form.setBorder(BorderFactory.createTitledBorder("Nouveau type d'épreuve"));
        JTextField txtLibelle = new JTextField();
        form.add(new JLabel("Libellé :"), BorderLayout.NORTH);
        form.add(txtLibelle, BorderLayout.CENTER);

        comboTypos = new JComboBox<>();

        JButton btnAjouter = new JButton("AJOUTER");
        btnAjouter.addActionListener(e -> {
            String libelle = txtLibelle.getText().trim();
            if (libelle.isEmpty()) return;
            TypeEpreuve t = new TypeEpreuve(0, libelle);
            data.CREER(t);
            JOptionPane.showMessageDialog(this, "Type d'épreuve créé avec l'ID " + t.getIdTypeEpreuve(), "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            rafraichirTypes();
            txtLibelle.setText("");
        });

        panel.add(creerZoneTitres("Gestion des types d'épreuve"), BorderLayout.NORTH);
        panel.add(comboTypos, BorderLayout.CENTER);
        JPanel est = new JPanel(new BorderLayout());
        est.add(form, BorderLayout.CENTER);
        est.add(btnAjouter, BorderLayout.SOUTH);
        panel.add(est, BorderLayout.EAST);
        return panel;
    }

    // =================================================================
    //                      PANEL RÉSERVATIONS
    // =================================================================
    private JPanel creerPanelReservations() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Sélection du contexte
        JPanel choix = new JPanel(new GridLayout(0, 2, 8, 8));
        choix.setBorder(BorderFactory.createTitledBorder("Réservation"));

        JComboBox<Coureur> comboCoureur = new JComboBox<>();
        JComboBox<TypeEpreuve> comboType = new JComboBox<>();
        rafraichirComboCoureurs(comboCoureur);
        rafraichirComboTypes(comboType);
        JComboBox<Article> comboArticle = new JComboBox<>();
        for (Article a : data.getArticlesActifs()) comboArticle.addItem(a);
        JTextField txtQte = new JTextField("1");

        choix.add(new JLabel("Coureur :")); choix.add(comboCoureur);
        choix.add(new JLabel("Type d'épreuve :")); choix.add(comboType);

        // Zone de composition de la liste d'articles
        JPanel ligneAjout = new JPanel(new FlowLayout());
        ligneAjout.add(new JLabel("Article :"));
        ligneAjout.add(comboArticle);
        ligneAjout.add(new JLabel("Qté :"));
        ligneAjout.add(txtQte);
        JButton btnAjouterLigne = new JButton("Ajouter à la réservation");
        ligneAjout.add(btnAjouterLigne);

        DefaultListModel<String> modelLignes = new DefaultListModel<>();
        JList<String> listLignes = new JList<>(modelLignes);
        listLignes.setBorder(BorderFactory.createTitledBorder("Articles réservés"));

        // Mise à jour du combo articles
        JButton btnCreer = new JButton("CRÉER LA RÉSERVATION");

        btnAjouterLigne.addActionListener(e -> {
            Article a = (Article) comboArticle.getSelectedItem();
            try {
                int qte = Integer.parseInt(txtQte.getText().trim());
                modelLignes.addElement(a.getLibelle() + " x" + qte + "  [" + a.getIdArticle() + "]");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantité invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCreer.addActionListener(e -> {
            Coureur coureur = (Coureur) comboCoureur.getSelectedItem();
            TypeEpreuve type = (TypeEpreuve) comboType.getSelectedItem();
            if (coureur == null || type == null) return;
            Reservation r = new Reservation(0, LocalDate.now(), coureur, type);
            // Reconstituer les lignes depuis le modèle
            for (int i = 0; i < modelLignes.size(); i++) {
                String ligne = modelLignes.get(i);
                // format "libelle xqte  [id]"
                String idStr = ligne.substring(ligne.lastIndexOf('[') + 1, ligne.lastIndexOf(']'));
                String qteStr = ligne.substring(ligne.indexOf('x') + 1, ligne.lastIndexOf("  [")).trim();
                int id = Integer.parseInt(idStr);
                int qte = Integer.parseInt(qteStr);
                Article a = data.CONSULTER_Article(id);
                r.ajouterLigne(a, qte);
            }
            if (r.getLignes().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez ajouter au moins un article.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean ok = data.verifierEtAjusterStocks(r);
            if (ok) {
                data.CREER(r);
                JOptionPane.showMessageDialog(this, "Réservation n°" + r.getIdReservation() + " créée. Stocks réajustés.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } else {
                r.setEnAttente(true);
                data.CREER(r);
                JOptionPane.showMessageDialog(this, "Stock insuffisant. La réservation a été placée en attente de validation.", "Alerte", JOptionPane.WARNING_MESSAGE);
            }
            modelLignes.clear();
            rafraichirReservations();
            rafraichirRupture();
        });

        comboCoureur.addActionListener(e -> { rafraichirComboCoureurs(comboCoureur); });
        comboType.addActionListener(e -> { rafraichirComboTypes(comboType); });

        // Table des réservations
        tableReservations = new JTable();
        tableReservations.setModel(creerModeleReservations());

        panel.add(creerZoneTitres("Gestion des réservations"), BorderLayout.NORTH);
        JPanel haut = new JPanel(new BorderLayout());
        haut.add(choix, BorderLayout.NORTH);
        haut.add(ligneAjout, BorderLayout.CENTER);
        haut.add(btnCreer, BorderLayout.SOUTH);
        panel.add(haut, BorderLayout.CENTER);
        panel.add(new JScrollPane(tableReservations), BorderLayout.SOUTH);
        panel.add(listLignes, BorderLayout.EAST);
        return panel;
    }

    // =================================================================
    //              PANEL RUPTURE / RÉSERVATION EN ATTENTE
    // =================================================================
    private JPanel creerPanelRuptureAttente() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel gauche = new JPanel(new BorderLayout());
        gauche.setBorder(BorderFactory.createTitledBorder("Produits en rupture"));
        JTextArea txtRupture = new JTextArea();
        txtRupture.setEditable(false);
        gauche.add(new JScrollPane(txtRupture), BorderLayout.CENTER);
        JButton btnActualiserRupture = new JButton("Actualiser");
        btnActualiserRupture.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            for (Article a : data.getArticlesEnRupture()) {
                sb.append("• ").append(a).append("\n");
            }
            if (sb.length() == 0) sb.append("Aucun article en rupture.");
            txtRupture.setText(sb.toString());
        });
        gauche.add(btnActualiserRupture, BorderLayout.SOUTH);

        JPanel droite = new JPanel(new BorderLayout());
        droite.setBorder(BorderFactory.createTitledBorder("Réservations en attente de validation"));
        modelAttente = new DefaultListModel<>();
        JList<Reservation> listAttente = new JList<>(modelAttente);
        droite.add(new JScrollPane(listAttente), BorderLayout.CENTER);
        JButton btnValider = new JButton("Valider la réservation sélectionnée");
        btnValider.addActionListener(e -> {
            Reservation r = listAttente.getSelectedValue();
            if (r == null) return;
            if (data.validerReservation(r)) {
                JOptionPane.showMessageDialog(this, "Réservation n°" + r.getIdReservation() + " validée et stocks réajustés.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Réservation impossible : stock toujours insuffisant.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            rafraichirRupture();
            rafraichirReservations();
        });
        droite.add(btnValider, BorderLayout.SOUTH);

        panel.add(creerZoneTitres("Articles en rupture / réservations en attente"), BorderLayout.NORTH);
        panel.add(gauche, BorderLayout.CENTER);
        panel.add(droite, BorderLayout.EAST);
        return panel;
    }

    // =================================================================
    //                       PANEL HISTORIQUE
    // =================================================================
    private JPanel creerPanelHistorique() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel params = new JPanel(new GridLayout(0, 2, 8, 8));
        params.setBorder(BorderFactory.createTitledBorder("Filtres"));

        JTextField txtDate = new JTextField();
        JComboBox<Coureur> comboCoureur = new JComboBox<>();
        JComboBox<TypeEpreuve> comboType = new JComboBox<>();

        params.add(new JLabel("Date (AAAA-MM-JJ) :")); params.add(txtDate);
        params.add(new JLabel("Coureur :")); params.add(comboCoureur);
        params.add(new JLabel("Type d'épreuve :")); params.add(comboType);

        JTextArea txtResult = new JTextArea();
        txtResult.setEditable(false);

        JButton btnConsulter = new JButton("CONSULTER");

        btnConsulter.addActionListener(e -> {
            long total = data.getReservations().size();
            txtResult.setText("Historique des réservations\n------------------------------\n");
            for (Reservation r : data.getReservations()) {
                txtResult.append(r + "\n");
                for (Reservation.LigneReservation ligne : r.getLignes()) {
                    txtResult.append("    - " + ligne + "\n");
                }
            }
            txtResult.append("\nTotal : " + total + " réservation(s).");
        });

        panel.add(creerZoneTitres("Consulter l'historique"), BorderLayout.NORTH);
        panel.add(params, BorderLayout.NORTH);
        panel.add(btnConsulter, BorderLayout.CENTER);
        panel.add(new JScrollPane(txtResult), BorderLayout.SOUTH);
        return panel;
    }

    // =================================================================
    //                       OUTILS COMMUNS
    // =================================================================
    private JLabel creerZoneTitres(String texte) {
        JLabel lbl = new JLabel(texte);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setBorder(new EmptyBorder(0, 0, 10, 0));
        return lbl;
    }

    private void rafraichirArticles() {
        if (comboArticles != null) {
            comboArticles.removeAllItems();
            for (Article a : data.getArticlesActifs()) comboArticles.addItem(a);
        }
    }

    private void rafraichirCoureurs() {
        if (comboCoureurs != null) {
            comboCoureurs.removeAllItems();
            for (Coureur c : data.getCoureurs()) comboCoureurs.addItem(c);
        }
    }

    private void rafraichirTypes() {
        if (comboTypos != null) {
            comboTypos.removeAllItems();
            for (TypeEpreuve t : data.getTypeEpreuves()) comboTypos.addItem(t);
        }
    }

    private void rafraichirReservations() {
        if (tableReservations != null) {
            tableReservations.setModel(creerModeleReservations());
        }
    }

    private void rafraichirRupture() {
        if (modelAttente != null) {
            modelAttente.clear();
            for (Reservation r : data.getReservationsEnAttente()) modelAttente.addElement(r);
        }
    }

    private void rafraichirComboCoureurs(JComboBox<Coureur> combo) {
        combo.removeAllItems();
        for (Coureur c : data.getCoureurs()) combo.addItem(c);
    }

    private void rafraichirComboTypes(JComboBox<TypeEpreuve> combo) {
        combo.removeAllItems();
        for (TypeEpreuve t : data.getTypeEpreuves()) combo.addItem(t);
    }

    /**
     * Crée le modèle de table pour les réservations.
     */
    private javax.swing.table.TableModel creerModeleReservations() {
        String[] colonnes = {"N°", "Date", "Coureur", "Type épreuve", "Articles", "Statut"};
        Object[][] lignes = new Object[data.getReservations().size()][6];
        int i = 0;
        for (Reservation r : data.getReservations()) {
            lignes[i][0] = r.getIdReservation();
            lignes[i][1] = r.getDate().toString();
            lignes[i][2] = r.getCoureur() != null ? r.getCoureur().getNomComplet() : "";
            lignes[i][3] = r.getTypeEpreuve() != null ? r.getTypeEpreuve().getLibelle() : "";
            StringBuilder sb = new StringBuilder();
            for (Reservation.LigneReservation l : r.getLignes()) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(l);
            }
            lignes[i][4] = sb.toString();
            lignes[i][5] = r.isEnAttente() ? "En attente" : "Validée";
            i++;
        }
        return new javax.swing.table.DefaultTableModel(lignes, colonnes);
    }
}
