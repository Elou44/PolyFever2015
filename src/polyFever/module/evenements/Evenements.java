package polyFever.module.evenements;

import java.util.*;

import org.lwjgl.input.Keyboard;

import polyFever.module.moteurDeJeu.*;
import polyFever.module.menu.*;


//implementation : http://pastebin.com/i7TYjrbM

/**
 * Classe g�rant les contr�les des joueurs et les entr�es utilisateurs dans les menus.
 * N'est modifi� par aucune autre classe : ne contient aucun getter/setter.
 * @author Valentin
 *
 */
public class Evenements {
	private Partie partie;			//Partie
	private Hashtable controles;	//Liste des touches associes aux joueurs
	private StringBuilder entree;	//Entree utilisateur
	
	//Rappel : vous m'appelez pour les menus, donc vous n'initialiserez jamais directement avec la liste des joueurs
	/**
	 * Constructeur par d�faut, aucun param�tre.
	 * Sera appel� d�s le d�but de l'�x�cution pour la navugation dans les menus, lorsque les contr�les et le nombre de joueurs seront encore inconnus.
	 */
	public Evenements() {
		System.out.println("Instanciation d'un objet Evenements...");
		this.partie = new Partie();				//partie geree
		this.controles = new Hashtable();		//table associant les joueurs a leurs controles
		this.entree = new StringBuilder();		//buffer pour les entrees utilisateurs
	}
	/*
	//Retourne les coordonn�es du clic au menu, donc c'est un couple
	public void gestionMenu() {
		//Renvoie les coordonn�es de la souris quand l'utilisateur clic
	}
	*/
	/**
	 * Gestion des entr�es au clavier des utilisateurs : saisie de pseudo, adresse IP, port.
	 * @param param	L'objet Parametres dans lequel modifier la chaine correspondante.
	 */
	public void entreeUtilisateur(Parametres param) {
		//Renvoie la chaine saisie par l'utilisateur (pour les pseudos et les adresses)
		//Choix : ESCAPE pour arreter la saisie, RETURN pour la valider, BACK pour effacer
		while(Keyboard.next()) {
			
			if(Keyboard.getEventKey() == Key.KEY_ESCAPE) {
				param.setPseudo(new String());		//On renvoie une chaine vide quand l'utilisateur annule la saisie
			
			} else if(Keyboard.getEventKey() == Key.KEY_RETURN) {
				this.entree.deleteCharAt(this.entree.length()-1);	//On efface le dernier caractere
			
			} else if(Keyboard.getEventKey() == Key.BACK) {
				param.setPseudo(this.entree.toString());	//On envoie la chaine courante (en String)
				
			} else this.entree.append(getEventCharacter());	//On ajoute le caractere associe a la touche
			
		}
		//getEventCharacter envoie le caractere (en char) associe (fonctionne avec Keyboard.next, les evenements)
		//getKeyName envoie le nom de la Key saisie en parametre (en String) (fonctionne avec l'int KEY_xxx)
	}
	
	//Retourne les deux touches pour tourner entr�es par un joueur, donc c'est un couple
	//Retourne les entiers KEY_xxx : utiliser Keyboard.getKeyName()
	/**
	 * Fonction qui permet aux joueurs de choisir leurs touches.
	 * @param param	Objet Parametres o� modifier les touches.
	 */
	public void entreeControles(Parametres param) {
		//Renvoie les deux premiers caract�res valides entr�s par l'utilisateur
		while(Keyboard.next()) {
			
			if(param.getGauche() == null)		//On regarde quelle touche est entree
				param.setGauche(getEventKey());	//Et on l'ajoute
			else if(param.getDroite() == null)
				param.setDroite(getEventKey());
			//Si les deux touches sont deja saisies, on ne fait rien
			//Aucune restriction sur les touches possibles pour le moment
		}
	}
	
	//Initialiser les contr�les avec les joueurs au debut d'une partie
	/**
	 * Initialisation des contr�les d'une partie : r�cup�re les contr�les et les associe aux objets Joueur correspondants?
	 * @param p	Objet partie o� sont enregistr�s les joueurs.
	 */
	public void initControles(Partie p) {
		this.partie = p;	//R�cup�rer la liste des joueurs
		
		Joueur j = new Joueur(p);
		Iterator<Joueur> i = this.partie.getJoueurs().iterator();		//Pour parcourir la liste de joueurs
		
		while(i.hasNext()) {
			j = i.next();
			this.controles.put(j.getToucheG(), j);
			this.controles.put(j.getToucheD(), j);
		}
	}
	
	/*
	 * Echap : quitter le jeu, fonction encore � d�finir
	 * Espace : plus tard, sans doute pour lancer le jeu/pause
	 * v�rifie l'�tat du joueur : ne pas faire d'action sur les joueurs morts
	 *  -> classe enum ETAT : ne traiter que les VIVANTS
	 */
	//Gestion des contr�les en partie
	/**
	 * Gestion des entr�es clavier pendant une partie : permet au joueur de tourner ainsi que l'arr�t et la mise en pause de la partie.
	 */
	public void gestionJeu() {
		Joueur j;
		
		
		//Puis on regarde les �v�nements
		//Re garder les �v�nements permet d'�tre plus r�actif sur l'action quand on appuie/relache une touche
		while(Keyboard.next()) {
			
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {	//Quitter la partie
				this.partie.arreterPartie();
				
			} else if(Keyboard.getEventKey() == Keyboard.KEY_SPACE) {	//Quitter la partie
				this.partie.pause();
			
			} else if(this.controles.containsKey(Keyboard.getEventKey())) {	//Si l'�v�nement concerne les controles d'un joueur
				j = (Joueur) this.controles.get(Keyboard.getEventKey());
				
				if(Keyboard.getEventKey() == j.getToucheG())
				//getEventKeyState = true si on appuie la touche, false si on la relache
					j.setToucheGPresse(Keyboard.getEventKeyState());
				
				else if(Keyboard.getEventKey() == j.getToucheD())
					j.setToucheDPresse(Keyboard.getEventKeyState());
			}
		}
		
		Iterator<Joueur> i = this.partie.getJoueurs().iterator();	//Pour parcourir la liste de joueurs
		
		while(i.hasNext()) {
			j = i.next();
			
			if(j.getEtat() == Etat.VIVANT) {
			
				if(j.isToucheGPresse() && !j.isToucheDPresse())		//Le joueur tourne � gauche
					j.getLigne().tournerGauche();			//Appel de la fonction pour tourner � gauche
				
				else if(j.isToucheDPresse() && !j.isToucheGPresse())	//Le joueur tourne � droite
					j.getLigne().tournerDroite();			//Appel de la fonction pour tourner � droite
				
				else j.getLigne().pasTourner();				//Sinon on tourne pas
				
			}
			
		}
	}
	
}
