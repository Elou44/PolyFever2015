package polyFever.module.menu;

/**
 * Classe correspondant au bouton du menu qui permettra de s�lectionner un nombre pr�cis de joueurs.
 * @author Elouarn Lain�
 *
 */

public class BoutonPlayer extends Bouton {
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
	
	public BoutonPlayer(Menu p, float x, float y, float l, float h, int imgB, int c){
		super(x, y, l, h, imgB);
		this.pere = p;
		super.idColor = c;
		
	}
	/**
	 * Methode appell�e lors du clik sur le bouton
	 * @return Menu lancant le jeu
	 */
	
	
	@Override
	public void action() {
		super.select();
		System.out.println("IdColorClickedButton : " + super.getIdColor());
		pere.p.getAffichage().dMenu.updateMenu(pere.structMenu.getCurMenu()); // On appelle la m�thode de l'affichage qui va dessiner le nouveau menu
	}
}
