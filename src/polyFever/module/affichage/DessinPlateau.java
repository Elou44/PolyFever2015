package polyFever.module.affichage;
import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

public class DessinPlateau {
	
	private DessinBonus dBonus;
	private DessinJoueur dJoueur;
	private DessinLigne dLigne;
	
	public DessinPlateau(Affichage a, PolyFever p, Partie partie)
	{
		dBonus = new DessinBonus();
		dJoueur = new DessinJoueur();
		dLigne = new DessinLigne(a, p, partie);
		
	}
	
	public void dessiner()
	{
		//System.out.println("		dessiner dPlateau");
		dBonus.dessiner();
		dJoueur.dessiner();
		dLigne.dessiner();

		
	}
	
	public void init()
	{
		dBonus.init();
		dJoueur.init();
		dLigne.init();
	}

}
