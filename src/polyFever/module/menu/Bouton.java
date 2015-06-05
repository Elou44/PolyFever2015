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
	protected int idColor;
	
	/**
	 * Constructeur de la classe bouton prennant les paramêtres de la hitbox
	 * @param x : Coordonnée en x pour la hitbox
	 * @param y : Coordonnée en y pour la hitbox
	 * @param w : Largeur de la hitbox
	 * @param h : Hauteur de la hitbox
	 */
	
	public Bouton(float x, float y, float l, float h,int imgB){
		this.hitbox = new Hitbox(x, y, l, h);
		this.isSelected = false;
		this.imgBouton = imgB;
		this.idColor = -1;
	}
	
	
	/**
	 * Methode permettant de récupérer la hitbox associée au bouton
	 * @return : Rectangle représentant la hitbox du bouton
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
	
	public int getIdColor() {
		return this.idColor;
	}

	/**
	 * Methode permettant de récupérer l'état du bouton 
	 * @return boolean de l'état du bouton
	 */

	public boolean isSelected() {
		return isSelected;
	}
	
	public void select() {
		if(isSelected)
		{
			isSelected = false;
		}
		else
		{
			isSelected = true;
		}
	}

	/**
	 * Methode abstraite qui permettra de définir l'action à effectuer en cas de clic sur la hitbox
	 * @return Nouveau menu courant
	 */
	
	public abstract void action();
}
