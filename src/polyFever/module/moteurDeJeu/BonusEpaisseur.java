package polyFever.module.moteurDeJeu;

/* 
 * Classe stockant les informations liés à un bonus augmentant l'épaisseur des joueurs adverses
 */

/**
 * Ceci est la classe BonusEpaisseur héritant de Bonus @see Bonus
 * Classe stockant les informations liés à un bonus augmentant l'épaisseur des joueurs adverses
 * 
 * Cette classe hérite des paramètres et méthodes de la classe mère @see Bonus
 * 
 * @author Frédéric Llorca
 *
 */
public class BonusEpaisseur extends Bonus{

	// Constructeur
	/**
	 * Constructeur d'un objet BonusEpaisseur
	 * Appelle le constructeur de la classe mère Bonus (@see Bonus)
	 * Prend le nom : "Epaisseur"
	 * Prend la portée : ROUGE
	 */
	public BonusEpaisseur()
	{
		super();
		this.nom = "Epaisseur";
		this.couleur = Portee.ROUGE;
	}
	
	// Méthodes
	/**
	 * Méthode modifiant les paramètres du Joueur
	 * Change l'épaisseur de la ligne du Joueur en la multipliant par deux
	 */
	public void modifierParametres()
	{
		/* 
		 * Changer l'épaisseur du joueur affecté
		 */
		joueur.getLigne().setEpaisseur(joueur.getLigne().getEpaisseur()*2);
	}
	
	/**
	 * Méthode rétablissant les paramètres du Joueur
	 * Change l'épaisseur de la ligne du Joueur en la divisant par deux
	 */
	public void retablirParametres()
	{
		joueur.getLigne().setEpaisseur(joueur.getLigne().getEpaisseur()/2);
	}
	
}
