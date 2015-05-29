package polyFever.module.menu;
import polyFever.module.main.*;

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
	
	public MenuFeuille(PolyFever p, String t, String c){
		super(p, t);
		this.contenu = c;
	}
}
