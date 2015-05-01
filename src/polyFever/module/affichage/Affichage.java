package polyFever.module.affichage;

import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

public class Affichage {
	
	private DessinJeu dJeu;
	private DessinMenu dMenu;
	
	
	private final int WIDTH; // Largeur de la fenetre en pixel
	private final int HEIGHT; // Hauteur de la fenetre en pixel
	private int RATIOPIXWIDTH; // Largeur d'un pixel en float
	private int RATIOPIXHEIGHT; // Hauteur d'un pixel en float
	


	public Affichage(int width, int height, PolyFever p, Partie partie)
	{
		this.WIDTH = width;
		this.HEIGHT = height;
		RATIOPIXWIDTH = 2/width;
		RATIOPIXHEIGHT = 2/height;
		dJeu = new DessinJeu(width, height, this, p, partie);
		dMenu = new DessinMenu();
	}
	
	
	public int getWIDTH() {
		return WIDTH;
	}


	public int getHEIGHT() {
		return HEIGHT;
	}
	
	public int getRATIOPIXWIDTH() {
		return RATIOPIXWIDTH;
	}


	public int getRATIOPIXHEIGHT() {
		return RATIOPIXHEIGHT;
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
