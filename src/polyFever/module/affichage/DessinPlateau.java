package polyFever.module.affichage;
import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

public class DessinPlateau {
	
	public DessinBonus dBonus;
	public DessinLigne dLigne;
	
	public DessinPlateau(Affichage a, PolyFever p, Partie partie)
	{
		dBonus = new DessinBonus();
		dLigne = new DessinLigne(a, p, partie);
		
	}
	
	public void dessiner()
	{
		//System.out.println("		dessiner dPlateau");
		dBonus.dessiner();
		dLigne.dessiner();

		
	}
	
	public void init()
	{
		dBonus.init();
		dLigne.init();
	}

}
