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
	
	public BoutonMenu(Menu p, Menu f, float x, float y, float l, float h, int imgB){
		super(x, y, l, h, imgB);
		this.pere = p;
		this.fils = f;
		
	}
	

	/**
	 * Redéfinition de la méthode abstraite action() pour la navigation descendante
	 * Appel à la fonction changementMenu() du menu courant (pere)
	 * @return : Nouveau menu courant
	 */
	@Override
	public void action(){ //on click sur la hitbox, on appelle cette fonction
		pere.changementMenu(fils);
	}
}
