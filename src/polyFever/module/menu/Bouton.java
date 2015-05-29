package polyFever.module.menu;

import java.awt.Rectangle;
 
/**
 * Classe Bouton permettant de naviguer dans l'arborescence de menu
 * @author Ambre
 *
 */

public abstract class Bouton {
	
	Rectangle hitbox;
	
	/**
	 * Constructeur de la classe bouton prennant les param�tres de la hitbox
	 * @param x : Coordonn�e en x pour la hitbox
	 * @param y : Coordonn�e en y pour la hitbox
	 * @param w : Largeur de la hitbox
	 * @param h : Hauteur de la hitbox
	 */
	
	public Bouton(int x, int y, int w, int h){
		this.hitbox = new Rectangle(x, y, w, h);
	}
	
	/**
	 * Constructeur par d�faut sans param�tres
	 */
	
	public Bouton(){
		this.hitbox = new Rectangle();
	}
	
	/**
	 * Methode permettant de r�cup�rer la hitbox associ�e au bouton
	 * @return : Rectangle repr�sentant la hitbox du bouton
	 */
	
	
	public Rectangle getHitbox() {
		return hitbox;
	}


	/**
	 * Methode abstraite qui permettra de d�finir l'action � effectuer en cas de clic sur la hitbox
	 * @return Nouveau menu courant
	 */
	
	public abstract Menu action();
}
