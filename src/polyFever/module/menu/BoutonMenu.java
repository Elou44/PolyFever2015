package polyFever.module.menu;

import java.awt.Rectangle;
/**
 * Classe BoutonMenu sp�cialisant la classe Bouton pour la navigation "descendante"
 * @author Ambre
 *
 */

public class BoutonMenu extends Bouton {
	Menu pere; //menu affich�
	Menu fils; //menu qui est la cible du bouton
	
	/**
	 * Constructeur de la classe BoutonMenu prennant tous les param�tres
	 * @param p : Menu courant, celui qui est affich�
	 * @param f : Menu fils, celui que le bouton a pour cible
	 * @param x : Coordonn�e en x pour la hitbox
	 * @param y : Coordonn�e en y pour la hitbox
	 * @param w : Largeur de la hitbox
	 * @param h : Hauteur de la hitbox
	 */
	
	public BoutonMenu(Menu p, Menu f, int x, int y, int w, int h){
		super(x, y, w, h);
		this.pere = p;
		this.fils = f;
		
	}
	
	/**
	 * Constructeur BoutonMenu avec les param�tres de la hitbox par d�faut
	 * @param p : Menu courant, celui qui est affich�
	 * @param f : Menu fils, celui que le bouton a pour cible
	 */
	
	public BoutonMenu(Menu p, Menu f){
		super();
		this.pere = p;
		this.fils = f;
		
	}
	 
	/**
	 * Red�finition de la m�thode abstraite action() pour la navigation descendante
	 * Appel � la fonction changementMenu() du menu courant (pere)
	 * @return : Nouveau menu courant
	 */
	@Override
	public Menu action(){ //on click sur la hitbox, on appelle cette fonction
		return pere.changementMenu(fils);
	}
}
