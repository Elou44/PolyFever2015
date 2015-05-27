package polyFever.module.moteurDeJeu;

import polyFever.module.util.math.Vector4;

/* 
 * Classe abstraite définissant les attributs et un prototype de méthode pour les bonus
 */

public abstract class Bonus {

	// A VOIR SI C'EST UTILE OU NON
	protected String nom;				// Nom du bonus
	protected Portee couleur;			// Variable définissant la portée du bonus et donc la couleur lorsque le bonus est sur le plateau de jeu
	protected Vector4 coordonnees;		// Vector2 définissant la position sur le plateau de jeu du bonus
	// A VOIR SI C'EST UTILE OU NON
	protected Joueur joueur;			// Objet joueur prenant le bonus
	protected static int id = 0;		// Entier servant à différencier chaque Bonus, id unique pour chaque Bonus instancié
	protected long tpsDepart;			// Variable donnant le temps ou le bonus a commencé ) prendre effet
	protected long duree;				// Variable donnant la durée d'effet du bonus
	protected int indexTab;				// Index du bonus dans le tableau des bonusPresents de l'objet Partie
	
	// Constructeur
	public Bonus()
	{
		this.coordonnees = new Vector4();
		Bonus.id = Bonus.id + 1;
		this.tpsDepart = 0;
		this.duree = 0;
		this.indexTab = 0;
	}
	
	// Méthodes
	public void modifierParametres(){} // Prototype de la méthode modifiant les paramètres des joueurs ou lignes concernées
	
	public void retablirParametres(){}	// Prototype de la méthode rétablissant les paramètres du joueur après effet du bonus
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

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

	@Override
	public String toString() {
		return "Bonus [nom=" + nom + ", couleur=" + couleur + ", coordonnees="
				+ coordonnees + ", joueur=" + joueur + ", tpsDepart="
				+ tpsDepart + ", duree=" + duree + ", indexTab=" + indexTab
				+ "]";
	}
	
}
