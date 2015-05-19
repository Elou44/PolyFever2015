package polyFever.module.moteurDeJeu;


/* 
 * Classe stockant les informations liés à un bonus augmentant la vitesse du joueur ayant activé ce bonus
 */

public class BonusVitesse extends Bonus{

	// Constructeur
	public BonusVitesse()
	{
		super();
		this.nom = "vitesse";
		this.couleur = Portee.VERT;
	}
	
	// Méthode
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
