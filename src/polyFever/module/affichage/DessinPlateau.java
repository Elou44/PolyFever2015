package polyFever.module.affichage;
import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

public class DessinPlateau {
	
	private DessinBonus dBonus;
	private DessinJoueur dJoueur;
	private DessinLigne[] dLigneTab;
	
	public DessinPlateau(Affichage a, PolyFever p, Partie partie)
	{
		dBonus = new DessinBonus();
		dJoueur = new DessinJoueur();
		dLigneTab = new DessinLigne[1];
		for(int i=0; i < 1; i++)
		{
			dLigneTab[i] = new DessinLigne(a, p, partie,i*0.03f);
		}
	}
	
	public void dessiner()
	{
		//System.out.println("		dessiner dPlateau");
		dBonus.dessiner();
		dJoueur.dessiner();
		
		for(int i=0; i < 1; i++)
		{
			dLigneTab[i].dessiner();
		}


		
	}
	
	public void init()
	{
		dBonus.init();
		dJoueur.init();
		for(int i=0; i < 1; i++)
		{
			dLigneTab[i].init();
		}
	}

}
