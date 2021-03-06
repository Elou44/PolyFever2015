package polyFever.module.menu;
import polyFever.module.main.*;

import java.util.*;

/**
 * Classe d�finissant les menus du jeu PolyFever
 * Un menu poss�de un titre, un p�re et des fils
 * @author Ambre
 *
 */

public class Menu {
	protected PolyFever p;
	protected StructureMenu structMenu;
	protected String titre;
	protected Menu pere;
	protected Set<Menu> fils;
	protected Set<Bouton> boutons;
	protected int imgTitre; // Indice de l'image du titre du menu
	protected int imgFond; // Indice de l'image du fond du menu
	
	public static boolean isMenu = true;
	
	
	/**
	 * Constructeur de menu gr�ce au titre
	 * @param titre : Titre du menu
	 */
	
	public Menu(StructureMenu sm, PolyFever p, String titre, int imgT, int imgF) {
		this.structMenu = sm;
		this.p = p;
		this.titre = titre;
		this.pere = null;
		this.fils = new HashSet<Menu>();
		this.boutons = new HashSet<Bouton>();
		this.imgTitre = imgT;
		this.imgFond = imgF;
	}
	
	/**
	 * Methode ajoutant un bouton � la liste des boutons du menu
	 * @param b : Bouton � ajouter
	 */
	
	public void addBouton(Bouton b){
		this.boutons.add(b);
	}
	
	/**
	 * Methode permettant de r�cup�rer les boutons du menu
	 * @return : Ensemble des boutons du menu courant
	 */
	
	public Set<Bouton> getBoutons() {
		return boutons;
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
				structMenu.setCurMenu(nouv); // On met � jour le menu courant (curMenu)
				p.getAffichage().dMenu.updateMenu(structMenu.getCurMenu()); // On appelle la m�thode de l'affichage qui va dessiner le nouveau menu
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
		this.structMenu.setCurMenu(prev); // On met � jour le menu courant 
		p.getAffichage().dMenu.updateMenu(structMenu.getCurMenu()); // On appelle la m�thode de l'affichage qui va dessiner le nouveau menu
		return prev;
	}
	
	/**
	 * Methode permettant de renvoyer l'�tat du menu (menu classique ou jeu)
	 * @return Boolean isMenu
	 */
	
	public static boolean isMenu() {
		return isMenu;
	}
	
	public String getTitre(){
		return titre;
	}
	
	public int getImgTitre() {
		return imgTitre;
	}
	
	public int getImgFond() {
		return imgFond;
	}
	
	@Override
	public String toString() {
		return "Le menu courant est : "+this.titre;
	}

}
