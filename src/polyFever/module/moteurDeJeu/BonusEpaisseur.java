package polyFever.module.moteurDeJeu;

/* 
 * Classe stockant les informations li�s � un bonus augmentant l'�paisseur des joueurs adverses
 */

/**
 * Ceci est la classe BonusEpaisseur h�ritant de Bonus @see Bonus
 * Classe stockant les informations li�s � un bonus augmentant l'�paisseur des joueurs adverses
 * 
 * Cette classe h�rite des param�tres et m�thodes de la classe m�re @see Bonus
 * 
 * @author Fr�d�ric Llorca
 *
 */
public class BonusEpaisseur extends Bonus{

	// Constructeur
	/**
	 * Constructeur d'un objet BonusEpaisseur
	 * Appelle le constructeur de la classe m�re Bonus (@see Bonus)
	 * Prend le nom : "Epaisseur"
	 * Prend la port�e : ROUGE
	 */
	public BonusEpaisseur()
	{
		super();
		this.nom = "Epaisseur";
		this.couleur = Portee.ROUGE;
	}
	
	// M�thodes
	/**
	 * M�thode modifiant les param�tres du Joueur
	 * Change l'�paisseur de la ligne du Joueur en la multipliant par deux
	 */
	public void modifierParametres()
	{
		/* 
		 * Changer l'�paisseur du joueur affect�
		 */
		joueur.getLigne().setEpaisseur(joueur.getLigne().getEpaisseur()*2);
	}
	
	/**
	 * M�thode r�tablissant les param�tres du Joueur
	 * Change l'�paisseur de la ligne du Joueur en la divisant par deux
	 */
	public void retablirParametres()
	{
		joueur.getLigne().setEpaisseur(joueur.getLigne().getEpaisseur()/2);
	}
	
}
