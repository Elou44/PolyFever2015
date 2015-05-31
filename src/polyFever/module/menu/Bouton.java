package polyFever.module.menu;

import java.awt.Rectangle;
 
/**
 * Classe Bouton permettant de naviguer dans l'arborescence de menu
 * @author Ambre
 *
 */

public abstract class Bouton {
	
	protected Hitbox hitbox;
	protected boolean isSelected;
	protected int imgBouton; // Indice de l'image du bouton
	
	/**
	 * Constructeur de la classe bouton prennant les param�tres de la hitbox
	 * @param x : Coordonn�e en x pour la hitbox
	 * @param y : Coordonn�e en y pour la hitbox
	 * @param w : Largeur de la hitbox
	 * @param h : Hauteur de la hitbox
	 */
	
	public Bouton(float x, float y, float l, float h,int imgB){
		this.hitbox = new Hitbox(x, y, l, h);
		this.isSelected = false;
		this.imgBouton = imgB;
	}
	
	
	/**
	 * Methode permettant de r�cup�rer la hitbox associ�e au bouton
	 * @return : Rectangle repr�sentant la hitbox du bouton
	 */
	
	
	public Hitbox getHitbox() {
		return hitbox;
	}
	
	public int getImgBouton() {
		return imgBouton;
	}
	
	public float getX() {
		return hitbox.x;
	}
	
	public float getY() {
		return hitbox.y;
	}
	
	public float getL() {
		return hitbox.largeur;
	}
	
	public float getH() {
		return hitbox.hauteur;
	}

	/**
	 * Methode permettant de r�cup�rer l'�tat du bouton 
	 * @return boolean de l'�tat du bouton
	 */

	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * Methode abstraite qui permettra de d�finir l'action � effectuer en cas de clic sur la hitbox
	 * @return Nouveau menu courant
	 */
	
	public abstract void action();
}
