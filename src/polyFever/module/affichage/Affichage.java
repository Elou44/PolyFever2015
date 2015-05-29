package polyFever.module.affichage;

import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

/**
 * <p>
 * La classe Affichage contient les instances des classes qui vont afficher tous les éléments
 * graphiques sur l'écran. La classe Affichage est instanciée par la classe PolyFever
 * dès le lancement du programme.
 * Les deux éléments principaux gérés cet classe sont l'affichage du Jeu et l'affichage du Menu.
 * </p>
 *  
 * @author Elouarn Lainé 
 *
 */
public class Affichage {
	
	/**
	 * <p>
	 * La classe Affichage contient deux attributs :
	 * - un objet de type DessinJeu {@link DessinJeu} gérant l'affichage d'une partie.
	 * - un objet de type DessinMenu {@link DessinMenu} gérant l'affichage du menu.
	 *</p>
	 *
	 *@author Elouarn Lainé
	 */
	public DessinJeu dJeu;
	public DessinMenu dMenu;
	
	public Affichage()
	{
		
	}
	
	/**
	 * <p>
	 * Cet Méthode instancie un objet du type DessinMenu {@link DessinMenu}
	 * et appelle la méthode d'initialisation de cet objet.
	 * </p>
	 * @param p
	 * 		référence vers un objet de type PolyFever {@link PolyFever} (classe créant le contexte OpenGL) 
	 * @param partie
	 * 		ne sera pas utilisé (A ENLEVER)
	 * @author Elouarn Lainé
	 */
	public void initMenu(PolyFever p, Partie partie)
	{
		dMenu = new DessinMenu(this, p, partie);
		dMenu.init();
	}
	
	/**
	 * <p>
	 * Cet Méthode instancie un objet du type DessinJeu {@link DessinJeu}
	 * et appelle la méthode d'initialisation de cet objet.
	 * </p>
	 * @param p
	 * 		référence vers l'objet de type PolyFever {@link PolyFever} (classe créant le contexte OpenGL) 
	 * @param partie
	 * 		référence vers l'objet de type Partie {@link Partie} (Classe principale du moteur de jeu)
	 * @author Elouarn Lainé
	 */
	public void initJeu(PolyFever p, Partie partie)
	{
		dJeu = new DessinJeu(this, p, partie);
		dJeu.init();
	}
	
	/**
	 * Méthode appelant la méthode dessiner de l'objet dMenu.
	 * @author Elouarn Lainé
	 */
	public void dessinerMenu()
	{
		//System.out.println("dessiner Affichage");
		dMenu.dessiner();
	}
	
	/**
	 * Méthode appelant la méthode dessiner de l'objet dJeu.
	 * @author Elouarn Lainé
	 */
	public void dessinerJeu()
	{
		//System.out.println("dessiner Affichage");
		dJeu.dessiner();
	}
	
}
