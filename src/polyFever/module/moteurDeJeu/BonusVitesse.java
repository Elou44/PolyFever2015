package polyFever.module.moteurDeJeu;


/* 
 * Classe stockant les informations liés à un bonus augmentant la vitesse du joueur ayant activé ce bonus
 */

public class BonusVitesse extends Bonus{

	// Constructeur
	public BonusVitesse()
	{
		super();
		this.nom = "vitesse";
		this.couleur = Portee.VERT;
	}
	
	// Méthode
	public void modifierParametres()
	{
		/* 
		 * Changer la vitesse du joueur affecté
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
