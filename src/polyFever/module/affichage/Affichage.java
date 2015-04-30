package polyFever.module.affichage;

public class Affichage {
	
	private DessinJeu dJeu;
	private DessinMenu dMenu;
	
	private final int WIDTH; // Largeur de la fenetre en pixel
	private final int HEIGHT; // Hauteur de la fenetre en pixel
	private int RATIOPIXWIDTH; // Largeur d'un pixel en float
	private int RATIOPIXHEIGHT; // Hauteur d'un pixel en float
	


	public Affichage(int width, int height)
	{
		this.WIDTH = width;
		this.HEIGHT = height;
		RATIOPIXWIDTH = 2/width;
		RATIOPIXHEIGHT = 2/height;
		dJeu = new DessinJeu(width, height);
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


	public void dessiner(int vbo)
	{
		//System.out.println("dessiner Affichage");
		dJeu.dessiner(vbo);
		dMenu.dessiner();
	}

}
