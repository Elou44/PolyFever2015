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
	 * Constructeur de la classe bouton prennant les paramêtres de la hitbox
	 * @param x : Coordonnée en x pour la hitbox
	 * @param y : Coordonnée en y pour la hitbox
	 * @param w : Largeur de la hitbox
	 * @param h : Hauteur de la hitbox
	 */
	
	public Bouton(int x, int y, int w, int h){
		this.hitbox = new Rectangle(x, y, w, h);
	}
	
	/**
	 * Constructeur par défaut sans paramêtres
	 */
	
	public Bouton(){
		this.hitbox = new Rectangle();
	}
	
	/**
	 * Methode permettant de récupérer la hitbox associée au bouton
	 * @return : Rectangle représentant la hitbox du bouton
	 */
	
	
	public Rectangle getHitbox() {
		return hitbox;
	}


	/**
	 * Methode abstraite qui permettra de définir l'action à effectuer en cas de clic sur la hitbox
	 * @return Nouveau menu courant
	 */
	
	public abstract Menu action();
}
