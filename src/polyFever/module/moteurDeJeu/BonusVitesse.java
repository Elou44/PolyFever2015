package polyFever.module.moteurDeJeu;


/* 
 * Classe stockant les informations li�s � un bonus augmentant la vitesse du joueur ayant activ� ce bonus
 */

public class BonusVitesse extends Bonus{

	// Constructeur
	public BonusVitesse()
	{
		super();
		this.nom = "vitesse";
		this.couleur = Portee.VERT;
	}
	
	// M�thode
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
