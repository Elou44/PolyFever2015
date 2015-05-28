package polyFever.module.affichage;
import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

public class DessinJeu {
	
	public DessinPlateau dPlateau;
	public DessinScores dScores;
	
	
	public DessinJeu(Affichage a, PolyFever p, Partie partie)
	{
		dPlateau = new DessinPlateau(a, p, partie);
		dScores = new DessinScores();
	}
	
	public void dessiner()
	{
		//System.out.println("	dessiner dJeu");
		dPlateau.dessiner();
		dScores.dessiner();
	}
	
	public void init()
	{
		dPlateau.init();
		dScores.init();
	}
	
}
