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
	
	public BoutonMenu(Menu p, Menu f, float x, float y, float l, float h, int imgB){
		super(x, y, l, h, imgB);
		this.pere = p;
		this.fils = f;
		
	}
	

	/**
	 * Red�finition de la m�thode abstraite action() pour la navigation descendante
	 * Appel � la fonction changementMenu() du menu courant (pere)
	 * @return : Nouveau menu courant
	 */
	@Override
	public void action(){ //on click sur la hitbox, on appelle cette fonction
		pere.changementMenu(fils);
	}
}
