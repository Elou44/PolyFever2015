package polyFever.module.affichage;
import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

/**
 * <p>
 * La classe DessinPlateau contient les instances des classes qui vont afficher tous les éléments
 * graphiques liés à une plateau de jeu (lignes, joueurs, et bonus). La classe DessinPlateau est instanciée par la classe DessinJeu.
 * Les deux éléments principaux gérés cet classe sont l'affichage des Lignes et l'affichage des bonus.
 * </p>
 *  
 * @author Elouarn Lainé 
 *
 */
public class DessinPlateau {
	
	public DessinBonus dBonus;
	public DessinLignes dLignes;
	
	/**
	 * Constructeur de la classe DessinPlateau
	 * Instanciation des objets {@link DessinLignes} et {@link DessinBonus}
	 * @param a
	 * 		référence vers l'objet Affichage.
	 * @param p
	 * 		référence vers l'objet polyFever.
	 * @param partie
	 * 		référence vers l'objet Partie.
	 * 
	 * @author Elouarn Lainé
	 */
	public DessinPlateau(Affichage a, PolyFever p, Partie partie)
	{
		dBonus = new DessinBonus();
		dLignes = new DessinLignes(a, p, partie);
	}
	
	
	/**
	 * Méthode appelant les méthodes d'initialisation des objets dLignes et dBonus.
	 * 
	 * @author Elouarn Lainé
	 */
	public void init()
	{
		dBonus.init();
		dLignes.init();
	}

	
	/**
	 * Méthode appelant les méthodes dessiner des objets dLignes et dBonus.
	 * 
	 * @author Elouarn Lainé
	 */
	public void dessiner()
	{
		dBonus.dessiner();
		dLignes.dessiner();
	}

}
