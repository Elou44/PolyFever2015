package polyFever.module.moteurDeJeu;

import polyFever.module.util.math.Vector4;

/* 
 * Classe abstraite définissant les attributs et un prototype de méthode pour les bonus
 */

/**
 * Ceci est la classe Abstract Bonus
 * Classe abstraite définissant les attributs et les prototypes des méthodes pour les bonus
 * 
 * @param nom
 * 			Chaine de caractères accueillant le nom du Bonus
 * @param couleur
 * 			Variable définissant la portée du bonus et donc la couleur lorsque le bonus est sur le plateau de jeu, valeur de l'énumération Portee
 * @param coordonnees
 * 			Vector4 définissant la position sur le plateau de jeu du bonus
 * @param joueur
 * 			Objet joueur affecté par le bonus
 * @param id
 * 			Entier servant à différencier chaque Bonus, id unique pour chaque Bonus instancié
 * @param tpsDepart
 * 			Long donnant le temps (en millisecondes) ou le bonus a commencé à prendre effet
 * @param duree
 * 			Long donnant la durée d'effet du bonus
 * @param indexTab
 * 			Entier index du bonus dans le tableau des bonusPresents de l'objet Partie
 * 
 * @author Frédéric Llorca
 *
 */
public abstract class Bonus {

	// A VOIR SI C'EST UTILE OU NON
	protected String nom;				// Nom du bonus
	protected Portee couleur;			// Variable définissant la portée du bonus et donc la couleur lorsque le bonus est sur le plateau de jeu
	protected Vector4 coordonnees;		// Vector4 définissant la position sur le plateau de jeu du bonus
	// A VOIR SI C'EST UTILE OU NON
	protected Joueur joueur;			// Objet joueur prenant le bonus
	protected static int id = 0;		// Entier servant à différencier chaque Bonus, id unique pour chaque Bonus instancié
	protected long tpsDepart;			// Variable donnant le temps ou le bonus a commencé à prendre effet
	protected long duree;				// Variable donnant la durée d'effet du bonus
	protected int indexTab;				// Index du bonus dans le tableau des bonusPresents de l'objet Partie
	
	// Constructeur
	/**
	 * Constructeur d'un objet Bonus
	 * Constructeur par défaut ne prenant aucun paramètres
	 * Ce constructeur initalise les variables nécessaires à l'utilisation d'un objet Bonus
	 * Ce constructeur est appelé lors de l'instanciation d'un objet Bonus fils
	 */
	public Bonus()
	{
		this.coordonnees = new Vector4();
		Bonus.id = Bonus.id + 1;
		this.tpsDepart = 0;
		this.duree = 0;
		this.indexTab = 0;
	}
	
	// Méthodes
	/**
	 * Méthode modifiant les paramètres du Joueur affecté selon l'effet du Bonus
	 */
	public void modifierParametres(){} // Prototype de la méthode modifiant les paramètres des joueurs ou lignes concernées
	
	/**
	 * Méthode rétablissant les paramètres originaux du Joueur affecté
	 */
	public void retablirParametres(){}	// Prototype de la méthode rétablissant les paramètres du joueur après effet du bonus
	
	/**
	 * Retourne le nom du Bonus
	 * @return un String contenant le nom du Bonus
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Retourne l'entier donnant la couleur du Bonus
	 * @return l'entier couleur
	 */
	public Portee getCouleur() {
		return couleur;
	}

	public Vector4 getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Vector4 coordonnees) {
		this.coordonnees = coordonnees;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public static int getId() {
		return id;
	}

	public long getTpsDepart() {
		return tpsDepart;
	}

	public void setTpsDepart(long tpsDepart) {
		this.tpsDepart = tpsDepart;
	}

	public long getDuree() {
		return duree;
	}

	public void setDuree(long duree) {
		this.duree = duree;
	}
	
	public int getIndexTab() {
		return indexTab;
	}

	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}

	/**
	 * Méthode renvoyant une chaine de caractères décrivant un objet Bonus
	 */
	@Override
	public String toString() {
		return "Bonus [nom=" + nom + ", couleur=" + couleur + ", coordonnees="
				+ coordonnees + ", joueur=" + joueur + ", tpsDepart="
				+ tpsDepart + ", duree=" + duree + ", indexTab=" + indexTab
				+ "]";
	}
	
}
