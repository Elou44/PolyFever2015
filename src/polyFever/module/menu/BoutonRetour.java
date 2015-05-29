package polyFever.module.menu;

/**
 * Classe BoutonRetour spécialisant Bouton pour la navigation "ascendante"
 * @author Ambre
 *
 */

public class BoutonRetour extends Bouton {
	protected Menu menu; //menu courant
	
	/**
	 * Constructeur de la Classe BoutonRetour avec tous les paramêtres de hitbox
	 * @param m : Menu courant
	 * @param x : Coordonnée en x pour la hitbox
	 * @param y : Coordonnée en y pour la hitbox
	 * @param w : Largeur de la hitbox
	 * @param h : Hauteur de la hitbox
	 */
	public BoutonRetour(Menu m, float x, float y, float l, float h){
		super(x,y,l,h);
		this.menu = m;
	}
	 
	/**
	 * Redéfinition de la méthode abstraite action() pour la navigation ascendante
	 * Appel à la fonction retour() du menu courant
	 * @return : Nouveau menu courant, menu précédent dans l'arborescence
	 */
	
	@Override
	public Menu action(){
		return menu.retour();
	}
}
