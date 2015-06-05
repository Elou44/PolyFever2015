package polyFever.module.menu;
import java.util.Iterator;

import polyFever.module.main.*;

import org.lwjgl.input.Keyboard;

import polyFever.module.moteurDeJeu.*;

/**
 * Classe représentant le menu lançant le jeu
 * @author Ambre et Elouarn Lainé
 *
 */

public class MenuPlay extends Menu {
	
	private Partie partie;
	private StructureMenu structMenu;
	
	/**
	 * Constructeur du Menu lancant le jeu
	 * @param t : Titre du menu qui sera affiché
	 */
	
	public MenuPlay(StructureMenu sm, PolyFever p, String t, int imgT, int imgF){
		super(sm, p, t, imgT, imgF);
		this.structMenu = sm;
	}
	
	/**
	 * Methode s'approchant syntaxiquement de Menu.changementMenu(Menu), permettant un appel sans passer de nouveau menu en paramêtre et lancer la partie
	 * Modification du booléen isMenu pour indiquer au module d'affichage qu'il faut afficher la partie et plus le menu
	 */
	
	public Menu changementMenu(){
		
		Menu.isMenu = false;
		partie = new Partie(p, structMenu);
		
	    Bouton b;
	    int i = 0;
	    System.out.println("NBBOUTONS" + this.getBoutons().size());
	    Iterator<Bouton> ib =this.getBoutons().iterator();
	    while(ib.hasNext()) //On parcours les Boutons
	    { 
			b = ib.next();
			//System.out.println("Bouton (idColor:"+b.getIdColor()+") isSelected:"+b.isSelected);
			if(b.isSelected) {
				
				
					if(b.getIdColor()==0) { Joueur j1 = new Joueur(partie, "Joueur 1", Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT);partie.ajouterJoueur(j1, p, 0); }

					else if(b.getIdColor()==1) { Joueur j2 = new Joueur(partie, "Joueur 2", Keyboard.KEY_C, Keyboard.KEY_V); partie.ajouterJoueur(j2, p, 1); }

					else if(b.getIdColor()==2) { Joueur j3 = new Joueur(partie, "Joueur 3", Keyboard.KEY_A, Keyboard.KEY_Z); partie.ajouterJoueur(j3, p, 2); }

					else if(b.getIdColor()==3) { Joueur j4 = new Joueur(partie, "Joueur 4", Keyboard.KEY_J, Keyboard.KEY_K); partie.ajouterJoueur(j4, p, 3);  }

					else if(b.getIdColor()==4) { Joueur j5 = new Joueur(partie, "Joueur 5", Keyboard.KEY_E, Keyboard.KEY_R); partie.ajouterJoueur(j5, p, 4); }

					else if(b.getIdColor()==5) { Joueur j6 = new Joueur(partie, "Joueur 6", Keyboard.KEY_T, Keyboard.KEY_Y); partie.ajouterJoueur(j6, p, 5); }

					else if(b.getIdColor()==6) { Joueur j7 = new Joueur(partie, "Joueur 7", Keyboard.KEY_U, Keyboard.KEY_I); partie.ajouterJoueur(j7, p, 6); }

					else if(b.getIdColor()==7) { Joueur j8 = new Joueur(partie, "Joueur 8", Keyboard.KEY_O, Keyboard.KEY_P); partie.ajouterJoueur(j8, p, 7); }

					else if(b.getIdColor()==8) { Joueur j9 = new Joueur(partie, "Joueur 9", Keyboard.KEY_Q, Keyboard.KEY_S); partie.ajouterJoueur(j9, p, 8); }

					else if(b.getIdColor()==9) { Joueur j10 = new Joueur(partie, "Joueur 10", Keyboard.KEY_D, Keyboard.KEY_F); partie.ajouterJoueur(j10, p, 9); }

					else if(b.getIdColor()==10) { Joueur j11 = new Joueur(partie, "Joueur 11", Keyboard.KEY_G, Keyboard.KEY_H); partie.ajouterJoueur(j11, p, 10); }

					else if(b.getIdColor()==11) { Joueur j12 = new Joueur(partie, "Joueur 12", Keyboard.KEY_L, Keyboard.KEY_M); partie.ajouterJoueur(j12, p, 11); }

					else if(b.getIdColor()==12) { Joueur j13 = new Joueur(partie, "Joueur 13", Keyboard.KEY_W, Keyboard.KEY_X); partie.ajouterJoueur(j13, p, 12); }

					else if(b.getIdColor()==13) { Joueur j14 = new Joueur(partie, "Joueur 14", Keyboard.KEY_B, Keyboard.KEY_N); partie.ajouterJoueur(j14, p, 13); }

					else if(b.getIdColor()==14) { Joueur j15 = new Joueur(partie, "Joueur 15", Keyboard.KEY_6, Keyboard.KEY_9); partie.ajouterJoueur(j15, p, 14); }

					else if(b.getIdColor()==15) { Joueur j16 = new Joueur(partie, "Joueur 16", Keyboard.KEY_1, Keyboard.KEY_0); partie.ajouterJoueur(j16, p, 15); }

				
			}
			
			if(b.getIdColor() != -1)
			{
				i++;
			}
			
			
		}
		
		partie.initialiserPartie();
		
		p.getAffichage().initJeu(p, partie);
		
		p.getEvenements().initControles(partie);
		
		return this;
	}
	
	public Partie getPartie(){
		return this.partie;
	}
	
	/**
	 * Réécriture de la méthode Menu retour()
	 * Si on est sur la partie (isMenu = False), on modifie le booléen isMenu pour indiquer au module affichage qu'on est de retour sur le menu (et plus sur le jeu)
	 * Si on n'a pas encore lancé le jeu, on revient au menu précédent comme sur la méthode de base
	 */
	@Override
	public Menu retour(){
		if (Menu.isMenu == false){  // Inutile ?
			Menu.isMenu = true;
			return this;
		} else {
			this.structMenu.setCurMenu(this.pere); // On met à jour le menu courant 
			p.getAffichage().dMenu.updateMenu(structMenu.getCurMenu()); // On appelle la méthode de l'affichage qui va dessiner le nouveau menu
			return this.pere;
		}
		
	}
}
