package polyFever.module.menu;
/**
 * Classe permettant d'afficher un contenu dans un menu, typiquement le menu Crédits
 * @author Ambre
 *
 */
public class MenuFeuille extends Menu {
	String contenu;
	
	/**
	 * Constructeur d'un menu feuille informatif
	 * @param t : String, titre du menu
	 * @param c : String, contenu informatif à afficher
	 */
	
	public MenuFeuille(String t, String c){
		super(t);
		this.contenu = c;
	}
}
