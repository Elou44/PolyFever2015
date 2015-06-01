package polyFever.module.main;
import polyFever.module.menu.*;

import polyFever.module.affichage.Affichage;

import polyFever.module.evenements.Evenements;

/**
 * Classe qui h�rite de {@link PolyFever} et qui contient le point d'entr� du programme.
 * @author Elouarn Lain�
 *
 */
public class PolyFeverGame extends PolyFever {
	
	/**
	 * Point d'entr� du programme. Instanciation d'un objet 
	 * de type {@link PolyFeverGame} qui h�rite de {@link PolyFever} et appelle de la m�thode
	 * run pour cr�er le contexte openGL et lancer la gameLoop().
	 * @param args
	 */
	public static void main(String[] args) {
		new PolyFeverGame().run(3, 2, true);
	}
	
	private Affichage affichage; // Classe Affichage
	private StructureMenu structMenu; // Classe qui instancie les �l�ments du menu

	
	/**
	 * Contructeur de la classe PolyFeverGame.
	 * Cr�ation d'une fen�tre de 900x600 pixels. Instanciation de l'objet {@link Affichage}
	 * , de l'objet {@link StructureMenu} ainsi que de l'objet {@link Evenements}.
	 */
	public PolyFeverGame() {
		super("PolyFever", 900, 600, true); // on autorise le redimensionnement de la fen�tre
		
		System.out.println("Cr�ation de l'objet Affichage...");
		this.affichage = new Affichage(); // Instanciation de l'affichage
		
		System.out.println("Affichage cr�� avec succ�s...");
		
		this.setAffichage(affichage);
		
		this.structMenu = new StructureMenu(this); // Instanciation de la gestion des menus
		
		Evenements ev = new Evenements(this, structMenu); // Instanciation de la gestion des evenements
		this.setEvenements(ev);
	}
	
	/**
	 * Appel des m�thodes d'initialisation de l'affichage du menu.
	 */
	@Override
	public void init() {
		
		affichage.initMenu(this);
		affichage.dMenu.updateMenu(structMenu.getCurMenu()); // On appelle la m�thode de l'affichage qui va dessiner le nouveau menu
						
	}
	
	/**
	 * Appel des m�thodes qui dessinent le menu ou le jeu selon que l'on soit dans le menu ou en partie.
	 */
	@Override
	public void render() {		

		if(!Menu.isMenu()) // Si la partie est d�fnie (on est en jeu)
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


