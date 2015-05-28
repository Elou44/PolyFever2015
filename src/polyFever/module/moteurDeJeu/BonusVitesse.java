package polyFever.module.moteurDeJeu;


/* 
 * Classe stockant les informations liés à un bonus augmentant la vitesse du joueur ayant activé ce bonus
 */

/**
 * Ceci est la classe BonusVitesse héritant de Bonus @see Bonus
 * Classe stockant les informations liés à un bonus augmentant la vitesse du joueur ayant activé ce bonus
 * 
 * Cette classe hérite des paramètres et méthodes de la classe mère @see Bonus
 * 
 * @author Frédéric Llorca
 *
 */
public class BonusVitesse extends Bonus{

	// Constructeur
	/**
	 * Constructeur d'un objet BonusVitesse
	 * Appelle le constructeur de la classe mère Bonus (@see Bonus)
	 * Prend le nom : "Vitesse"
	 * Prend la portée : VERT
	 */
	public BonusVitesse()
	{
		super();
		this.nom = "vitesse";
		this.couleur = Portee.VERT;
	}
	
	// Méthode
	/**
	 * Méthode modifiant les paramètres du Joueur
	 * Change la vitesse de la ligne du Joueur en la multipliant par 1.5
	 */
	public void modifierParametres()
	{
		/* 
		 * Changer la vitesse du joueur affecté
		 */
		Ligne ligne = joueur.getLigne();
		
		ligne.setVitesse(ligne.getVitesse()*1.5f);
	}
	
	/**
	 * Méthode rétablissant les paramètres du Joueur
	 * Change la vitesse de la ligne du Joueur en la divisant par 1.5
	 */
	public void retablirParametres()
	{
		Ligne ligne = joueur.getLigne();
		
		ligne.setVitesse(ligne.getVitesse()/1.5f);
	}
	
}
