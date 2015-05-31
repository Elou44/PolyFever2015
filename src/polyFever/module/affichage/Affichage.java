package polyFever.module.affichage;

import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

/**
 * <p>
 * La classe Affichage contient les instances des classes qui vont afficher tous les �l�ments
 * graphiques sur l'�cran. La classe Affichage est instanci�e par la classe PolyFever
 * d�s le lancement du programme.
 * Les deux �l�ments principaux g�r�s cet classe sont l'affichage du Jeu et l'affichage du Menu.
 * </p>
 *  
 * @author Elouarn Lain� 
 *
 */
public class Affichage {
	
	public DessinJeu dJeu;
	public DessinMenu dMenu;
	
	/**
	 * <p>
	 * Cet M�thode instancie un objet du type {@link DessinMenu}
	 * et appelle la m�thode d'initialisation de cet objet.
	 * </p>
	 * @param p
	 * 		r�f�rence vers un objet de type {@link PolyFever} (classe cr�ant le contexte OpenGL) 
	 * 
	 * @author Elouarn Lain�
	 */
	public void initMenu(PolyFever p)
	{
		dMenu = new DessinMenu(this, p);
		dMenu.init();
	}
	
	/**
	 * <p>
	 * Cet M�thode instancie un objet du type {@link DessinJeu}
	 * et appelle la m�thode d'initialisation de cet objet.
	 * </p>
	 * @param p
	 * 		r�f�rence vers l'objet de type {@link PolyFever} (classe cr�ant le contexte OpenGL) 
	 * @param partie
	 * 		r�f�rence vers l'objet de type {@link Partie} (Classe principale du moteur de jeu)
	 * @author Elouarn Lain�
	 */
	public void initJeu(PolyFever p, Partie partie)
	{
		dJeu = new DessinJeu(this, p, partie);
		dJeu.init();
	}
	
	/**
	 * M�thode appelant la m�thode dessiner de l'objet dMenu.
	 * @author Elouarn Lain�
	 */
	public void dessinerMenu()
	{
		//System.out.println("dessiner Affichage");
		dMenu.dessiner();
	}
	
	/**
	 * M�thode appelant la m�thode dessiner de l'objet dJeu.
	 * @author Elouarn Lain�
	 */
	public void dessinerJeu()
	{
		//System.out.println("dessiner Affichage");
		dJeu.dessiner();
	}
	
}
