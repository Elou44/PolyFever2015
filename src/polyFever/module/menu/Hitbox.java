package polyFever.module.menu;

public class Hitbox {
	protected float x;
	protected float y;
	protected float largeur;
	protected float hauteur;
	
	/**
	 * Constructeur de la hitbox
	 * @param x : Coordonnée en x du centre de la hitbox
	 * @param y : Coordonnée en y du centre de la hitbox
	 * @param largeur : Largeur de la hitbox
	 * @param hauteur : Hauteur de la hitbox
	 */
	
	public Hitbox(float x, float y, float largeur, float hauteur) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	/**
	 * Methode retournant x
	 * @return Float de la coordonnée x de la hitbox
	 */
	
	public float getX() {
		return x;
	}

	/**
	 * Methode retournant y
	 * @return Float de la coordonnée y de la hitbox
	 */
	
	public float getY() {
		return y;
	}

	/**
	 * Methode retournant la largeur
	 * @return Float de la largeur de la hitbox
	 */
	
	public float getLargeur() {
		return largeur;
	}
	
	/**
	 * Methode retournant la longueur
	 * @return Float de la longueur de la hitbox
	 */
	
	public float getHauteur() {
		return hauteur;
	}
	
	
}
