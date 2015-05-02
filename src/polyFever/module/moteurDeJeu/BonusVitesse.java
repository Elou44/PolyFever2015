package polyFever.module.moteurDeJeu;

import polyFever.module.util.math.Vector2;

/* 
 * Classe stockant les informations liés à un bonus augmentant la vitesse du joueur ayant activé ce bonus
 */

public class BonusVitesse extends Bonus{

	// Constructeur
	public BonusVitesse(Joueur joueur)
	{
		this.nom = "vitesse";
		this.couleur = Portee.VERT;
		this.coordonnees = new Vector2();
		this.joueur = joueur;
	}
	
	public void modifierParametres()
	{
		/* Changer la vitesse du joueur affecté
		 * 
		 */
		float ancienneVitesse;
		
		ancienneVitesse = joueur.getLigne().getVitesse();
		joueur.getLigne().setEpaisseur(ancienneVitesse+1);
	}
	
}
