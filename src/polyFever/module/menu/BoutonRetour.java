package polyFever.module.menu;

/**
 * Classe BoutonRetour sp�cialisant Bouton pour la navigation "ascendante"
 * @author Ambre
 *
 */

public class BoutonRetour extends Bouton {
	protected Menu menu; //menu courant
	
	/**
	 * Constructeur de la Classe BoutonRetour avec tous les param�tres de hitbox
	 * @param m : Menu courant
	 * @param x : Coordonn�e en x pour la hitbox
	 * @param y : Coordonn�e en y pour la hitbox
	 * @param w : Largeur de la hitbox
	 * @param h : Hauteur de la hitbox
	 */
	public BoutonRetour(Menu m, float x, float y, float l, float h){
		super(x,y,l,h);
		this.menu = m;
	}
	 
	/**
	 * Red�finition de la m�thode abstraite action() pour la navigation ascendante
	 * Appel � la fonction retour() du menu courant
	 * @return : Nouveau menu courant, menu pr�c�dent dans l'arborescence
	 */
	
	@Override
	public Menu action(){
		return menu.retour();
	}
}
