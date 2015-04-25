package polyFever.module.moteurDeJeu;

/* 
 * Classe abstraite définissant les attributs et un prototype de méthode pour les bonus
 */

public abstract class Bonus {

	// A VOIR SI C'EST UTILE OU NON
	protected String nom;				// Nom du bonus
	public enum Portee					// Enumération des différents types de portée d'un bonus, soit ROUGE = affecte les autres joueurs, soit VERT = affecte le joueur ayant activé ce bonus
	{
		ROUGE,
		VERT,
	}
	public Portee couleur;				// Variable définissant la portée du bonus et donc la couleur lorsque le bonus est sur le plateau de jeu
	public Vector2 coordonnees;			// Vector2 définissant la position sur le plateau de jeu du bonus
	// A VOIR SI C'EST UTILE OU NON
	protected Joueur joueur;			// Objet joueur prenant le bonus
	
	// Méthodes
	public void modifierParametres(){};	// Prototype de la méthode modifiant les paramètres des joueurs ou lignes concernées
	
}
