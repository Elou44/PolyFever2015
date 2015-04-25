package polyFever.module.moteurDeJeu;

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
		int ancienneEpaisseur;
		
		ancienneEpaisseur = joueur.ligne.getEpaisseur();
		joueur.ligne.setEpaisseur(ancienneEpaisseur+1);
	}
	
}
