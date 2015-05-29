package polyFever.module.menu;

/**
 * Classe repr�sentant le menu lan�ant le jeu
 * @author Ambre
 *
 */

public class MenuPlay extends Menu {
	
	/**
	 * Constructeur du Menu lancant le jeu
	 * @param t : Titre du menu qui sera affich�
	 */
	
	public MenuPlay(String t){
		super(t);
	}
	
	/**
	 * Methode s'approchant syntaxiquement de Menu.changementMenu(Menu), permettant un appel sans passer de nouveau menu en param�tre et lancer la partie
	 * Modification du bool�en isMenu pour indiquer au module d'affichage qu'il faut afficher la partie et plus le menu
	 */
	
	public void changementMenu(){
		Menu.isMenu = false;
		//lancer le jeu
	}
	/**
	 * R��criture de la m�thode Menu retour()
	 * Si on est sur la partie (isMenu = False), on modifie le bool�en isMenu pour indiquer au module affichage qu'on est de retour sur le menu (et plus sur le jeu)
	 * Si on n'a pas encore lanc� le jeu, on revient au menu pr�c�dent comme sur la m�thode de base
	 */
	@Override
	public Menu retour(){
		if (Menu.isMenu == false){
			Menu.isMenu = true;
			return this;
		} else {
			return this.pere;
		}
		
	}
}
