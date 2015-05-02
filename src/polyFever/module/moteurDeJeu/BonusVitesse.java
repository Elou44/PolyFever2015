package polyFever.module.moteurDeJeu;

import polyFever.module.util.math.Vector2;

/* 
 * Classe stockant les informations li�s � un bonus augmentant la vitesse du joueur ayant activ� ce bonus
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
		/* Changer la vitesse du joueur affect�
		 * 
		 */
		float ancienneVitesse;
		
		ancienneVitesse = joueur.getLigne().getVitesse();
		joueur.getLigne().setEpaisseur(ancienneVitesse+1);
	}
	
}
