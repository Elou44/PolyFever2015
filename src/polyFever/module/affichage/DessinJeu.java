package polyFever.module.affichage;
import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

/**
 * <p>
 * La classe DessinJeu contient les instances des classes qui vont afficher tous les �l�ments
 * graphiques li�s � une partie (lignes, joueurs, scores, etc...). La classe DessinJeu est instanci�e par la classe Affichage.
 * Les deux �l�ments principaux g�r�s cet classe sont l'affichage du Plateau et l'affichage des Scores.
 * </p>
 *  
 * @author Elouarn Lain� 
 *
 */
public class DessinJeu {
	
	
	/**
	 * <p>
	 * La classe DessinJeu contient deux attributs :
	 * - un objet de type DessinPlateau {@link DessinPlateau} g�rant l'affichage du plateau de jeu (Zone dans laquelle se d�placent les joueurs).
	 * - un objet de type DessinScores {@link DessinScores} g�rant l'affichage des Scores.
	 * </p>
	 * 
	 * @author Elouarn Lain�
	 */
	public DessinPlateau dPlateau;
	public DessinScores dScores;
	
	/**
	 * Constructeur de la classe DessinJeu
	 * Instanciation des objets DessinPlateau et DessinScores
	 * @param a
	 * 		r�f�rence vers l'objet Affichage.
	 * @param p
	 * 		r�f�rence vers l'objet polyFever.
	 * @param partie
	 * 		r�f�rence vers l'objet Partie.
	 * 
	 * @author Elouarn Lain�
	 */
	public DessinJeu(Affichage a, PolyFever p, Partie partie)
	{
		dPlateau = new DessinPlateau(a, p, partie);
		dScores = new DessinScores();
	}
	
	/**
	 * M�thode appelant les m�thodes d'initialisation des objets dPlateau et dScores.
	 * 
	 * @author Elouarn Lain�
	 */
	public void init()
	{
		dPlateau.init();
		dScores.init();
	}
	
	
	/**
	 * M�thode appelant les m�thodes dessiner des objets dPlateau et dScores.
	 * 
	 * @author Elouarn Lain�
	 */
	public void dessiner()
	{
		//System.out.println("	dessiner dJeu");
		dPlateau.dessiner();
		dScores.dessiner();
	}
	

	
}
