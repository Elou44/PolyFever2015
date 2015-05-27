package polyFever.module.menu;
/**
 * Classe représentant le pseudo-menu du plateau de jeu
 * @author Ambre
 *
 */
public class MenuPlay extends Menu {
	
	public MenuPlay(String t){
		super(t);
	}
	
	@Override
	public Menu changementMenu(Menu nouv){
		Menu.isMenu = false;
		return nouv;
	}
	
	@Override
	public Menu retour(){
		Menu.isMenu = true;
		Menu prev = this.pere;
		return prev;
	}
}
