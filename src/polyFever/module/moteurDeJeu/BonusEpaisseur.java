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
		/* Changer l'�paisseur du joueur affect�
		 * 
		 */
		float ancienneEpaisseur;
		
		ancienneEpaisseur = joueur.getLigne().getEpaisseur();
		joueur.getLigne().setEpaisseur(ancienneEpaisseur+1);
	}
	
}
