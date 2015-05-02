package polyFever.module.moteurDeJeu;

import polyFever.module.util.math.Vector2;

/* 
 * Classe stockant les informations li�s � un bonus augmentant l'�paisseur des joueurs adverses
 */

public class BonusEpaisseur extends Bonus{

	// Constructeur
	public BonusEpaisseur(Joueur joueur)
	{
		this.nom = "Epaisseur";
		this.couleur = Portee.ROUGE;
		this.coordonnees = new Vector2();
		this.joueur = joueur;
	}
	
	// M�thodes
	public void modifierParametres()
	{
		/* Changer l'�paisseur du joueur affect�
		 * 
		 */
		float ancienneEpaisseur;
		
		ancienneEpaisseur = joueur.getLigne().getEpaisseur();
		joueur.getLigne().setEpaisseur(ancienneEpaisseur+1);
	}
	
}
