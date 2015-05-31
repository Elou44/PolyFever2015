package polyFever.module.affichage;
import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

/**
 * <p>
 * La classe DessinPlateau contient les instances des classes qui vont afficher tous les éléments
 * graphiques liés à une plateau de jeu (lignes, joueurs, et bonus). La classe DessinPlateau est instanciée par la classe DessinJeu.
 * Les deux éléments principaux gérés cet classe sont l'affichage des Lignes et l'affichage des Scores.
 * </p>
 *  
 * @author Elouarn Lainé 
 *
 */

public class DessinPlateau {
	
	public DessinBonus dBonus;
	public DessinLignes dLignes;
	
	public DessinPlateau(Affichage a, PolyFever p, Partie partie)
	{
		dBonus = new DessinBonus();
		dLignes = new DessinLignes(a, p, partie);
		
	}
	
	public void dessiner()
	{
		//System.out.println("		dessiner dPlateau");
		dBonus.dessiner();
		dLignes.dessiner();

		
	}
	
	public void init()
	{
		dBonus.init();
		dLignes.init();
	}

}
