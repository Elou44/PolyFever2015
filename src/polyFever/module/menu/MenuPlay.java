package polyFever.module.menu;

/**
 * Classe représentant le menu lançant le jeu
 * @author Ambre
 *
 */

public class MenuPlay extends Menu {
	
	/**
	 * Constructeur du Menu lancant le jeu
	 * @param t : Titre du menu qui sera affiché
	 */
	
	public MenuPlay(String t){
		super(t);
	}
	
	/**
	 * Methode s'approchant syntaxiquement de Menu.changementMenu(Menu), permettant un appel sans passer de nouveau menu en paramêtre et lancer la partie
	 * Modification du booléen isMenu pour indiquer au module d'affichage qu'il faut afficher la partie et plus le menu
	 */
	
	public void changementMenu(){
		Menu.isMenu = false;
		//lancer le jeu
	}
	/**
	 * Réécriture de la méthode Menu retour()
	 * Si on est sur la partie (isMenu = False), on modifie le booléen isMenu pour indiquer au module affichage qu'on est de retour sur le menu (et plus sur le jeu)
	 * Si on n'a pas encore lancé le jeu, on revient au menu précédent comme sur la méthode de base
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
