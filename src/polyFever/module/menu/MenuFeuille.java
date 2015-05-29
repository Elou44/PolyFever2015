package polyFever.module.menu;
/**
 * Classe permettant d'afficher un contenu dans un menu, typiquement le menu Cr�dits
 * @author Ambre
 *
 */
public class MenuFeuille extends Menu {
	String contenu;
	
	/**
	 * Constructeur d'un menu feuille informatif
	 * @param t : String, titre du menu
	 * @param c : String, contenu informatif � afficher
	 */
	
	public MenuFeuille(String t, String c){
		super(t);
		this.contenu = c;
	}
}
