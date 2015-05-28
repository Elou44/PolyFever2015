package polyFever.module.moteurDeJeu;


/* 
 * Classe stockant les informations li�s � un bonus augmentant la vitesse du joueur ayant activ� ce bonus
 */

/**
 * Ceci est la classe BonusVitesse h�ritant de Bonus @see Bonus
 * Classe stockant les informations li�s � un bonus augmentant la vitesse du joueur ayant activ� ce bonus
 * 
 * Cette classe h�rite des param�tres et m�thodes de la classe m�re @see Bonus
 * 
 * @author Fr�d�ric Llorca
 *
 */
public class BonusVitesse extends Bonus{

	// Constructeur
	/**
	 * Constructeur d'un objet BonusVitesse
	 * Appelle le constructeur de la classe m�re Bonus (@see Bonus)
	 * Prend le nom : "Vitesse"
	 * Prend la port�e : VERT
	 */
	public BonusVitesse()
	{
		super();
		this.nom = "vitesse";
		this.couleur = Portee.VERT;
	}
	
	// M�thode
	/**
	 * M�thode modifiant les param�tres du Joueur
	 * Change la vitesse de la ligne du Joueur en la multipliant par 1.5
	 */
	public void modifierParametres()
	{
		/* 
		 * Changer la vitesse du joueur affect�
		 */
		Ligne ligne = joueur.getLigne();
		
		ligne.setVitesse(ligne.getVitesse()*1.5f);
	}
	
	/**
	 * M�thode r�tablissant les param�tres du Joueur
	 * Change la vitesse de la ligne du Joueur en la divisant par 1.5
	 */
	public void retablirParametres()
	{
		Ligne ligne = joueur.getLigne();
		
		ligne.setVitesse(ligne.getVitesse()/1.5f);
	}
	
}
