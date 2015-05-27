package polyFever.module.moteurDeJeu;

/* 
 * Classe stockant les informations liés à un bonus augmentant l'épaisseur des joueurs adverses
 */

public class BonusEpaisseur extends Bonus{

	// Constructeur
	public BonusEpaisseur()
	{
		super();
		this.nom = "Epaisseur";
		this.couleur = Portee.ROUGE;
	}
	
	// Méthodes
	public void modifierParametres()
	{
		/* 
		 * Changer l'épaisseur du joueur affecté
		 */
		joueur.getLigne().setEpaisseur(joueur.getLigne().getEpaisseur()*2);
	}
	
	public void retablirParametres()
	{
		joueur.getLigne().setEpaisseur(joueur.getLigne().getEpaisseur()/2);
	}
	
}
