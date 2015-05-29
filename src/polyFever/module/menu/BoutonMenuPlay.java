package polyFever.module.menu;

/**
 * Classe correspondant au bouton du menu qui lancera la partie
 * @author Ambre
 *
 */

public class BoutonMenuPlay extends Bouton {
	protected Menu pere;
	protected MenuPlay fils;
	
	/**
	 * Constructeur du bouton asocié au menu qui lancera le jeu
	 * @param p : Menu affiché
	 * @param f : Menu qui lance le jeu
	 * @param x : Coordonnée x du centre de la hitbox
	 * @param y : Coordonnée y du centre de la hitbox
	 * @param l : Longueur de la hitbox
	 * @param h : Largeur de la hitbox
	 */
	
	public BoutonMenuPlay(Menu p, MenuPlay f, float x, float y, float l, float h){
		super(x, y, l, h);
		this.pere = p;
		this.fils = f;
		
	}
	/**
	 * Methode appellée lors du clik sur le bouton
	 * @return Menu lancant le jeu
	 */
	
	
	@Override
	public Menu action() {
		return fils.changementMenu();
	}
}
