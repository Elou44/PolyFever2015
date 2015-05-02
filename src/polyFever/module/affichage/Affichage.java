package polyFever.module.affichage;

import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

public class Affichage {
	
	private DessinJeu dJeu;
	private DessinMenu dMenu;
	
	public Affichage(PolyFever p, Partie partie)
	{

		dJeu = new DessinJeu(this, p, partie);
		dMenu = new DessinMenu();
	}
	
	
	public void dessiner()
	{
		//System.out.println("dessiner Affichage");
		dJeu.dessiner();
		dMenu.dessiner();
	}
	
	public void init()
	{
		dJeu.init();
		dMenu.init();
	}
	
}
