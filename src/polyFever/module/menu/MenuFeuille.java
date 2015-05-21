package polyFever.module.menu;
/**
 * Classe permettant d'afficher un contenu dans un menu, typiquement le menu Crédits
 * @author Ambre
 *
 */
public class MenuFeuille extends Menu {
	String contenu;
	
	public MenuFeuille(String t, String c){
		super(t);
		this.contenu = c;
	}
}
