package polyFever.module.affichage;

import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

public class Affichage {
	
	private DessinJeu dJeu;
	private DessinMenu dMenu;
	
	
	private final int WIDTH; // Largeur de la fenetre en pixel
	private final int HEIGHT; // Hauteur de la fenetre en pixel

	


	public Affichage(int width, int height, PolyFever p, Partie partie)
	{
		this.WIDTH = width;
		this.HEIGHT = height;

		
		dJeu = new DessinJeu(width, height, this, p, partie);
		dMenu = new DessinMenu();
	}
	
	
	public int getWIDTH() {
		return WIDTH;
	}


	public int getHEIGHT() {
		return HEIGHT;
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
