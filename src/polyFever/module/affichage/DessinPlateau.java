package polyFever.module.affichage;
import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

/**
 * <p>
 * La classe DessinPlateau contient les instances des classes qui vont afficher tous les �l�ments
 * graphiques li�s � une plateau de jeu (lignes, joueurs, et bonus). La classe DessinPlateau est instanci�e par la classe DessinJeu.
 * Les deux �l�ments principaux g�r�s cet classe sont l'affichage des Lignes et l'affichage des bonus.
 * </p>
 *  
 * @author Elouarn Lain� 
 *
 */
public class DessinPlateau {
	
	public DessinBonus dBonus;
	public DessinLignes dLignes;
	
	/**
	 * Constructeur de la classe DessinPlateau
	 * Instanciation des objets {@link DessinLignes} et {@link DessinBonus}
	 * @param a
	 * 		r�f�rence vers l'objet Affichage.
	 * @param p
	 * 		r�f�rence vers l'objet polyFever.
	 * @param partie
	 * 		r�f�rence vers l'objet Partie.
	 * 
	 * @author Elouarn Lain�
	 */
	public DessinPlateau(Affichage a, PolyFever p, Partie partie)
	{
		dBonus = new DessinBonus();
		dLignes = new DessinLignes(a, p, partie);
	}
	
	
	/**
	 * M�thode appelant les m�thodes d'initialisation des objets dLignes et dBonus.
	 * 
	 * @author Elouarn Lain�
	 */
	public void init()
	{
		dBonus.init();
		dLignes.init();
	}

	
	/**
	 * M�thode appelant les m�thodes dessiner des objets dLignes et dBonus.
	 * 
	 * @author Elouarn Lain�
	 */
	public void dessiner()
	{
		dBonus.dessiner();
		dLignes.dessiner();
	}

}
