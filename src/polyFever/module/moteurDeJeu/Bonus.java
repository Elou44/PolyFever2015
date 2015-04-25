package polyFever.module.moteurDeJeu;

/* 
 * Classe abstraite d�finissant les attributs et un prototype de m�thode pour les bonus
 */

public abstract class Bonus {

	// A VOIR SI C'EST UTILE OU NON
	protected String nom;				// Nom du bonus
	public enum Portee					// Enum�ration des diff�rents types de port�e d'un bonus, soit ROUGE = affecte les autres joueurs, soit VERT = affecte le joueur ayant activ� ce bonus
	{
		ROUGE,
		VERT,
	}
	public Portee couleur;				// Variable d�finissant la port�e du bonus et donc la couleur lorsque le bonus est sur le plateau de jeu
	public Vector2 coordonnees;			// Vector2 d�finissant la position sur le plateau de jeu du bonus
	// A VOIR SI C'EST UTILE OU NON
	protected Joueur joueur;			// Objet joueur prenant le bonus
	
	// M�thodes
	public void modifierParametres(){};	// Prototype de la m�thode modifiant les param�tres des joueurs ou lignes concern�es
	
}
