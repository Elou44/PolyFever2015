package polyFever.module.affichage;

import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

public class Affichage {
	
	private DessinJeu dJeu;
	private DessinMenu dMenu;
	
	public Affichage()
	{
		
	}
	
	
	public void initMenu()
	{
		dMenu = new DessinMenu();
		dMenu.init();
	}
	
	public void initJeu(PolyFever p, Partie partie)
	{
		dJeu = new DessinJeu(this, p, partie);
		dJeu.init();
	}
	
	public void dessinerMenu()
	{
		//System.out.println("dessiner Affichage");
		dMenu.dessiner();
	}
	
	public void dessinerJeu()
	{
		//System.out.println("dessiner Affichage");
		dJeu.dessiner();
	}
	
}
