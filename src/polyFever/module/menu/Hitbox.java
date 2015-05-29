package polyFever.module.menu;

public class Hitbox {
	protected float x;
	protected float y;
	protected float largeur;
	protected float hauteur;
	
	
	
	public Hitbox(float x, float y, float largeur, float hauteur) {
		this.x = x;
		this.y = y;
		this.largeur = largeur;
		this.hauteur = hauteur;
	}

	
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getLargeur() {
		return largeur;
	}
	
	public float getHauteur() {
		return hauteur;
	}
	
	
}
