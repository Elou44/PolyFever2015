package polyFever.module.menu;

import java.awt.Rectangle;
/**
 * Classe BoutonMenu spécialisant la classe Bouton pour la navigation "descendante"
 * @author Ambre
 *
 */

public class BoutonMenu extends Bouton {
	Menu pere; //menu affiché
	Menu fils; //menu qui est la cible du bouton
	
	/**
	 * Constructeur de la classe BoutonMenu prennant tous les paramêtres
	 * @param p : Menu courant, celui qui est affiché
	 * @param f : Menu fils, celui que le bouton a pour cible
	 * @param x : Coordonnée en x pour la hitbox
	 * @param y : Coordonnée en y pour la hitbox
	 * @param w : Largeur de la hitbox
	 * @param h : Hauteur de la hitbox
	 */
	
	public BoutonMenu(Menu p, Menu f, int x, int y, int w, int h){
		super(x, y, w, h);
		this.pere = p;
		this.fils = f;
		
	}
	
	/**
	 * Constructeur BoutonMenu avec les paramêtres de la hitbox par défaut
	 * @param p : Menu courant, celui qui est affiché
	 * @param f : Menu fils, celui que le bouton a pour cible
	 */
	
	public BoutonMenu(Menu p, Menu f){
		super();
		this.pere = p;
		this.fils = f;
		
	}
	 
	/**
	 * Redéfinition de la méthode abstraite action() pour la navigation descendante
	 * Appel à la fonction changementMenu() du menu courant (pere)
	 * @return : Nouveau menu courant
	 */
	@Override
	public Menu action(){ //on click sur la hitbox, on appelle cette fonction
		return pere.changementMenu(fils);
	}
}
