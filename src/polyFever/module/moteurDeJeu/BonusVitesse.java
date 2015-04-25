package polyFever.module.moteurDeJeu;

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
		int ancienneVitesse;
		
		ancienneVitesse = joueur.ligne.getVitesse();
		joueur.ligne.setEpaisseur(ancienneVitesse+1);
	}
	
}
