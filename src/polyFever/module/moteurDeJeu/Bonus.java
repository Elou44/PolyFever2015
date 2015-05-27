package polyFever.module.moteurDeJeu;

import polyFever.module.util.math.Vector4;

/* 
 * Classe abstraite d�finissant les attributs et un prototype de m�thode pour les bonus
 */

public abstract class Bonus {

	// A VOIR SI C'EST UTILE OU NON
	protected String nom;				// Nom du bonus
	protected Portee couleur;			// Variable d�finissant la port�e du bonus et donc la couleur lorsque le bonus est sur le plateau de jeu
	protected Vector4 coordonnees;		// Vector2 d�finissant la position sur le plateau de jeu du bonus
	// A VOIR SI C'EST UTILE OU NON
	protected Joueur joueur;			// Objet joueur prenant le bonus
	protected static int id = 0;		// Entier servant � diff�rencier chaque Bonus, id unique pour chaque Bonus instanci�
	protected long tpsDepart;			// Variable donnant le temps ou le bonus a commenc� ) prendre effet
	protected long duree;				// Variable donnant la dur�e d'effet du bonus
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
	
	// M�thodes
	public void modifierParametres(){} // Prototype de la m�thode modifiant les param�tres des joueurs ou lignes concern�es
	
	public void retablirParametres(){}	// Prototype de la m�thode r�tablissant les param�tres du joueur apr�s effet du bonus
	
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
