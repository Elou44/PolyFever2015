package polyFever.module.moteurDeJeu;

import polyFever.module.util.math.Vector2;

/* 
 * Classe abstraite d�finissant les attributs et un prototype de m�thode pour les bonus
 */

public abstract class Bonus {

	// A VOIR SI C'EST UTILE OU NON
	protected String nom;				// Nom du bonus
	protected Portee couleur;			// Variable d�finissant la port�e du bonus et donc la couleur lorsque le bonus est sur le plateau de jeu
	protected Vector2 coordonnees;		// Vector2 d�finissant la position sur le plateau de jeu du bonus
	// A VOIR SI C'EST UTILE OU NON
	protected Joueur joueur;			// Objet joueur prenant le bonus
	protected static int id = 0;			// Entier servant � diff�rencier chaque Bonus, id unique pour chaque Bonus instanci�
	
	// Constructeur
	public Bonus()
	{
		this.coordonnees = new Vector2();
		Bonus.id = Bonus.id + 1;
	}
	
	// M�thodes
	public void modifierParametres(){} // Prototype de la m�thode modifiant les param�tres des joueurs ou lignes concern�es
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Portee getCouleur() {
		return couleur;
	}

	public Vector2 getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Vector2 coordonnees) {
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
	
}
