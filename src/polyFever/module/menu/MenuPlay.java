package polyFever.module.menu;
import polyFever.module.main.*;

import org.lwjgl.input.Keyboard;

import polyFever.module.moteurDeJeu.*;

/**
 * Classe repr�sentant le menu lan�ant le jeu
 * @author Ambre
 *
 */

public class MenuPlay extends Menu {
	
	private Partie partie;
	
	/**
	 * Constructeur du Menu lancant le jeu
	 * @param t : Titre du menu qui sera affich�
	 */
	
	public MenuPlay(PolyFever p, String t){
		super(p, t);
	}
	
	/**
	 * Methode s'approchant syntaxiquement de Menu.changementMenu(Menu), permettant un appel sans passer de nouveau menu en param�tre et lancer la partie
	 * Modification du bool�en isMenu pour indiquer au module d'affichage qu'il faut afficher la partie et plus le menu
	 */
	
	public Menu changementMenu(){
		
		Menu.isMenu = false;
		partie = new Partie(p);
		Joueur j1 = new Joueur(partie, "Joueur 1", Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT);
		Joueur j2 = new Joueur(partie, "Joueur 2", Keyboard.KEY_V, Keyboard.KEY_B);
		Joueur j3 = new Joueur(partie, "Joueur 3", Keyboard.KEY_A, Keyboard.KEY_Z);
		Joueur j4 = new Joueur(partie, "Joueur 4", Keyboard.KEY_K, Keyboard.KEY_L);
		partie.ajouterJoueur(j1, p);
		partie.ajouterJoueur(j2, p);
		partie.ajouterJoueur(j3, p);
		partie.ajouterJoueur(j4, p);
		partie.initialiserPartie();
		
		return this;
	}
	
	public Partie getPartie(){
		return this.partie;
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
