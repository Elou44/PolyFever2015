package polyFever.module.menu;

/**
 * Classe correspondant au bouton du menu qui lancera la partie
 * @author Ambre
 *
 */

public class BoutonQuitter extends Bouton {
	protected Menu pere;
	//protected MenuPlay fils;
	
	/**
	 * Constructeur du bouton asoci� au menu qui lancera le jeu
	 * @param p : Menu affich�
	 * @param f : Menu qui lance le jeu
	 * @param x : Coordonn�e x du centre de la hitbox
	 * @param y : Coordonn�e y du centre de la hitbox
	 * @param l : Longueur de la hitbox
	 * @param h : Largeur de la hitbox
	 */
	
	public BoutonQuitter(Menu p, float x, float y, float l, float h, int imgB){
		super(x, y, l, h, imgB);
		this.pere = p;
		
	}
	/**
	 * Methode appell�e lors du clik sur le bouton
	 * @return Menu lancant le jeu
	 */
	
	
	@Override
	public void action() {
		pere.p.closeGame();
		System.out.println("Fermeture du programme, Merci d'avoir jou� !");
	}
}
