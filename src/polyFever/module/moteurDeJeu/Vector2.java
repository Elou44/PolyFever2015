package polyFever.module.moteurDeJeu;

/* 
 * Classe Vector2 accueillant deux variables de type Integer
 * Sert pour définir les positions des différentes entités sur le plateau de jeu
 */

public class Vector2 {
    
	public int x;
	public int y;
	
	// Constructeur
	public Vector2() {
		this.x = 0;
		this.y = 0;
	}
	
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// Comparer deux vecteurs
	public boolean equals(Vector2 other) {
		return (this.x == other.x && this.y == other.y);
	}
}
