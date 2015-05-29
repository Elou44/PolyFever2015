package polyFever.module.affichage;
import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

/**
 * <p>
 * La classe DessinJeu contient les instances des classes qui vont afficher tous les éléments
 * graphiques liés à une partie (lignes, joueurs, scores, etc...). La classe DessinJeu est instanciée par la classe Affichage.
 * Les deux éléments principaux gérés cet classe sont l'affichage du Plateau et l'affichage des Scores.
 * </p>
 *  
 * @author Elouarn Lainé 
 *
 */
public class DessinJeu {
	
	
	/**
	 * <p>
	 * La classe DessinJeu contient deux attributs :
	 * - un objet de type DessinPlateau {@link DessinPlateau} gérant l'affichage du plateau de jeu (Zone dans laquelle se déplacent les joueurs).
	 * - un objet de type DessinScores {@link DessinScores} gérant l'affichage des Scores.
	 * </p>
	 * 
	 * @author Elouarn Lainé
	 */
	public DessinPlateau dPlateau;
	public DessinScores dScores;
	
	/**
	 * Constructeur de la classe DessinJeu
	 * Instanciation des objets DessinPlateau et DessinScores
	 * @param a
	 * 		référence vers l'objet Affichage.
	 * @param p
	 * 		référence vers l'objet polyFever.
	 * @param partie
	 * 		référence vers l'objet Partie.
	 * 
	 * @author Elouarn Lainé
	 */
	public DessinJeu(Affichage a, PolyFever p, Partie partie)
	{
		dPlateau = new DessinPlateau(a, p, partie);
		dScores = new DessinScores();
	}
	
	/**
	 * Méthode appelant les méthodes d'initialisation des objets dPlateau et dScores.
	 * 
	 * @author Elouarn Lainé
	 */
	public void init()
	{
		dPlateau.init();
		dScores.init();
	}
	
	
	/**
	 * Méthode appelant les méthodes dessiner des objets dPlateau et dScores.
	 * 
	 * @author Elouarn Lainé
	 */
	public void dessiner()
	{
		//System.out.println("	dessiner dJeu");
		dPlateau.dessiner();
		dScores.dessiner();
	}
	

	
}
