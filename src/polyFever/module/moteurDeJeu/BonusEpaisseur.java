package polyFever.module.moteurDeJeu;

/* 
 * Classe stockant les informations li�s � un bonus augmentant l'�paisseur des joueurs adverses
 */

public class BonusEpaisseur extends Bonus{

	// Constructeur
	public BonusEpaisseur()
	{
		super();
		this.nom = "Epaisseur";
		this.couleur = Portee.ROUGE;
	}
	
	// M�thodes
	public void modifierParametres()
	{
		/* 
		 * Changer l'�paisseur du joueur affect�
		 */
		joueur.getLigne().setEpaisseur(joueur.getLigne().getEpaisseur()*2);
	}
	
	public void retablirParametres()
	{
		joueur.getLigne().setEpaisseur(joueur.getLigne().getEpaisseur()/2);
	}
	
}
