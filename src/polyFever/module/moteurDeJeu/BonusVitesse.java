package polyFever.module.moteurDeJeu;


/* 
 * Classe stockant les informations li�s � un bonus augmentant la vitesse du joueur ayant activ� ce bonus
 */

public class BonusVitesse extends Bonus{

	// Constructeur
	public BonusVitesse()
	{
		super();
		this.nom = "vitesse";
		this.couleur = Portee.VERT;
	}
	
	// M�thode
	public void modifierParametres()
	{
		/* 
		 * Changer la vitesse du joueur affect�
		 */
		Ligne ligne = joueur.getLigne();
		
		ligne.setVitesse(ligne.getVitesse()*1.5f);
	}
	
	public void retablirParametres()
	{
		Ligne ligne = joueur.getLigne();
		
		ligne.setVitesse(ligne.getVitesse()/1.5f);
	}
	
}
