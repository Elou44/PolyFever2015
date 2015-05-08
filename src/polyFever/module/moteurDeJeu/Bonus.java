package polyFever.module.moteurDeJeu;

import polyFever.module.util.math.Vector2;

/* 
 * Classe abstraite définissant les attributs et un prototype de méthode pour les bonus
 */

public abstract class Bonus {

	// A VOIR SI C'EST UTILE OU NON
	protected String nom;				// Nom du bonus
	protected Portee couleur;			// Variable définissant la portée du bonus et donc la couleur lorsque le bonus est sur le plateau de jeu
	protected Vector2 coordonnees;		// Vector2 définissant la position sur le plateau de jeu du bonus
	// A VOIR SI C'EST UTILE OU NON
	protected Joueur joueur;			// Objet joueur prenant le bonus
	protected static int id = 0;			// Entier servant à différencier chaque Bonus, id unique pour chaque Bonus instancié
	
	// Constructeur
	public Bonus()
	{
		this.coordonnees = new Vector2();
		Bonus.id = Bonus.id + 1;
	}
	
	// Méthodes
	public void modifierParametres(){} // Prototype de la méthode modifiant les paramètres des joueurs ou lignes concernées
	
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
