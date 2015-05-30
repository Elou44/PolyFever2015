package polyFever.module.main;
import polyFever.module.menu.*;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;

import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import org.lwjgl.input.Keyboard;
import polyFever.module.affichage.Affichage;
import polyFever.module.moteurDeJeu.*;
import polyFever.module.evenements.Evenements;

public class Prototyp1 extends PolyFever {
	
	public static void main(String[] args) {
		new Prototyp1().run(3, 2, true);
	}
	
	private Affichage affichage; // Classe Affichage
	private StructureMenu structMenu;

	

	public Prototyp1() {
		super("Prototyp1", 900, 600, true); // on interdit le redimensionnement de la fenetre
		
		System.out.println("Création de l'objet Affichage...");
		this.affichage = new Affichage(); // Instanciation de l'affichage
		
		System.out.println("Affichage créé avec succès...");
		
		this.setAffichage(affichage);
		
		this.structMenu = new StructureMenu(this); // Instanciation de la gestion des menus
		
		Evenements ev = new Evenements(this, structMenu); // Instanciation de la gestion des evenements
		this.setEvenements(ev);
	}
	
	@Override
	public void init() {
		
		affichage.initMenu(this);
		affichage.dMenu.updateMenu(structMenu.getCurMenu()); // On appelle la méthode de l'affichage qui va dessiner le nouveau menu
						
	}
	
	@Override
	public void render() {		

		if(!Menu.isMenu()) // Si la partie est défnie (on est en jeu)
		{
			structMenu.getMenuPlay().getPartie().update();
			affichage.dessinerJeu();
		}
		else if(Menu.isMenu())
		{
			affichage.dessinerMenu();
		}
		
		
		
		
		
		
				
	}

}


