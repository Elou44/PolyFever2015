package menu;

import java.util.*;

/**
 * Classe d�finissant les menus du jeu PolyFever
 * Un menu poss�de un titre, un p�re et des fils
 * @author Ambre
 *
 */

public class Menu {
	protected String titre;
	protected Menu pere;
	protected Set<Menu> fils;
	
	/**
	 * Constructeur de menu gr�ce au titre
	 * @param titre : Titre du menu
	 */
	public Menu(String titre) {
		this.titre = titre;
		this.pere = null;
		this.fils = new HashSet<Menu>();
	}
	
	/**
	 * Methode permettant de d�finir le p�re du menu en question
	 * @param pere : Objet Menu qui sera le p�re du menu concern� dans l'arborescence
	 */
	public void setPere(Menu pere) {
		this.pere = pere;
		System.out.println("Le p�re de "+this.titre+" est "+this.pere.titre);
	}
	
	/**
	 * Methode permettant d'ajouter un fils � sa liste de fils
	 * @param fils : Objet Menu � ajouter � la liste de fils
	 */
	
	public void addFils(Menu fils){
		this.fils.add(fils);
		System.out.println("Un fils de "+this.titre+" est "+fils.titre);
	}
	
	/**
	 * Methode permettant de changer de menu courant, � utiliser avec un pseudo menu permettant de parcourir l'arborescence
	 * @param nouv : Objet Menu souhait� comme nouveau menu courant
	 * @return : Objet Menu � affecter au pseudo menu de parcours
	 */
	
	public Menu changementMenu(Menu nouv){
			if (this.fils.contains(nouv)){
				System.out.println("Le nouveau menu est "+nouv.titre);
				return nouv;
			} else {
				System.out.println("Le menu n'a pas pu �tre chang�. Le menu est "+this.titre);
				return this;
			}
		
	}
	
	/**
	 * Methode permettant de revenir en arri�re d'un �tage dans l'arborescence, � utiliser sur le pseudo menu de parcours
	 * @return : Objet Menu qui est le p�re du menu courant
	 */
	public Menu retour(){
		Menu prev = this.pere;
		System.out.println("Retour au menu pr�c�dent : "+prev.titre);
		return prev;
	}
	
	
	@Override
	public String toString() {
		return "Le menu courant est : "+this.titre;
	}



	public static class Test{
		public static void main(String[] args) {
			
			Menu home = new Menu("Home");
			home.setPere(home);
			
			Menu play = new Menu("Play");
			home.addFils(play);
			play.setPere(home);
			
			Menu settings = new Menu("Settings");
			home.addFils(settings);
			settings.setPere(home);
			
			Menu credit = new Menu("Credit");
			home.addFils(credit);
			credit.setPere(home);
			
			Menu quit = new Menu("Quit");
			home.addFils(quit);
			quit.setPere(home);
			
			Menu local = new Menu("Local Multi");
			play.addFils(local);
			local.setPere(play);
			
			Menu lan = new Menu("LAN Multi");
			play.addFils(lan);
			lan.setPere(play);		
			
			Menu host = new Menu("H�berger");
			lan.addFils(host);
			host.setPere(lan);
			
			Menu join = new Menu("Rejoindre");
			lan.addFils(join);
			join.setPere(lan);
			
			
			
			Menu curMenu = home;
			System.out.println(curMenu.toString());
			curMenu = curMenu.changementMenu(play);
			curMenu = curMenu.retour();
			curMenu = curMenu.changementMenu(play);
			curMenu = curMenu.changementMenu(local);
			curMenu = curMenu.retour();
			curMenu = curMenu.changementMenu(lan);
			curMenu = curMenu.changementMenu(join);
			curMenu = curMenu.retour();


		}
	}
}