package polyFever.module.moteurDeJeu;

import polyFever.module.util.math.Vector2;

/* 
 * Classe stockant les informations liés à un bonus augmentant l'épaisseur des joueurs adverses
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
	
	// Méthodes
	public void modifierParametres()
	{
		/* Changer l'épaisseur du joueur affecté
		 * 
		 */
		float ancienneEpaisseur;
		
		ancienneEpaisseur = joueur.getLigne().getEpaisseur();
		joueur.getLigne().setEpaisseur(ancienneEpaisseur+1);
	}
	
}
