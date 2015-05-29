package polyFever.module.evenements;

import java.util.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import polyFever.module.moteurDeJeu.*;
import polyFever.module.menu.*;
import polyFever.module.main.*;


//implementation : http://pastebin.com/i7TYjrbM

/**
 * Classe gérant les contrôles des joueurs et les entrées utilisateurs dans les menus.
 * N'est modifié par aucune autre classe : ne contient aucun getter/setter.
 * @author Valentin
 *
 */
public class Evenements {
	private Partie partie;			//Partie
	private Hashtable controles;	//Liste des touches associes aux joueurs
	private StringBuilder entree;	//Entree utilisateur
	private PolyFever polyfever;	//Objet PolyFever
	
	//Rappel : vous m'appelez pour les menus, donc vous n'initialiserez jamais directement avec la liste des joueurs
	/**
	 * Constructeur par défaut.
	 * Sera appelé dés le début de l'éxécution pour la navugation dans les menus, lorsque les contrôles et le nombre de joueurs seront encore inconnus.
	 * @param p Objet PolyFever, pour récupérer le ratio des pixels pour le traitement de la souris.
	 */
	public Evenements(PolyFever p) {
		System.out.println("Instanciation d'un objet Evenements...");
		this.controles = new Hashtable();		//table associant les joueurs a leurs controles
		this.entree = new StringBuilder();		//buffer pour les entrees utilisateurs
		this.polyfever = p;						//pour récupérer le ratio des pixels
	}
	
	
	
	/**
	 * Regarde si les coordonnées sont dans la hitbox donnée.
	 * @param x La coordonnée x absolue en int (en heut à gauche de la fenêtre).
	 * @param y La coordonnée y absolue en int (en heut à gauche de la fenêtre).
	 * @return La nouvelle coordonnée selon openGL.
	 */
	private boolean isInsideHitbox(int x, int y, Bouton b) {
		float coord1, coord2;
		coord1 = ((float) x) * polyfever.getRATIO();
		coord2 = ((float) y) * polyfever.getRATIO();
		return (((coord1 >= b.getHitbox().getX()-(b.getHitbox().getLargeur()/2)) && (coord2 <= b.getHitbox().getX()+(b.getHitbox().getLargeur()/2)))
				&& ((coord2 >= b.getHitbox().getY()-(b.getHitbox().getHauteur()/2)) && (coord2 <= b.getHitbox().getY()+(b.getHitbox().getHauteur()/2))));
	}
	
	/**
	 * Traite les clics de l'utilisateur dans le menu, et regarde s'ils sont dans la hitbox d'un bouton. Effectue l'action appropriée le cas échéant.
	 * @param boutons Ensemble des objets Bouton du menu concerné, contenant les hitbox.
	 */
	public void gestionMenu(Set<Bouton> boutons) {
		//Renvoie les coordonnees de la souris quand l'utilisateur clic
		//0 = left mouse button ; 1 = right mouse button
		Bouton b;
		
		while(Mouse.next()) {
			
			if(Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) { //Clic gauche avec la souris
				
				Iterator<Bouton> i = boutons.iterator();
				while(i.hasNext()) { //On parcours les Bouton de l'ensemble passe en parametre
					b = i.next();
					
					if(isInsideHitbox(Mouse.getEventX(), Mouse.getEventY(), b)) //Le clic est dans la hitbox
						b.action();
					
				}
			}
		}
	}
	
	/**
	 * Gestion des entrées au clavier des utilisateurs : saisie de pseudo, adresse IP, port.
	 * @param param	L'objet Parametres dans lequel modifier la chaine correspondante.
	 */
	public void entreeUtilisateurPseudo(Parametres param) {
		//Renvoie la chaine saisie par l'utilisateur (pour les pseudos et les adresses)
		//Choix : ESCAPE pour arreter la saisie, RETURN pour la valider, BACK pour effacer
		while(Keyboard.next()) {
			
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
				param.setPseudo(new String());		//On renvoie une chaine vide quand l'utilisateur annule la saisie
			
			} else if(Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
				this.entree.deleteCharAt(this.entree.length()-1);	//On efface le dernier caractere
			
			} else if(Keyboard.getEventKey() == Keyboard.KEY_BACK) {
				param.setPseudo(this.entree.toString());	//On envoie la chaine courante (en String)
				
			} else this.entree.append(Keyboard.getEventCharacter());	//On ajoute le caractere associe a la touche
			
		}
		//getEventCharacter envoie le caractere (en char) associe (fonctionne avec Keyboard.next, les evenements)
		//getKeyName envoie le nom de la Key saisie en parametre (en String) (fonctionne avec l'int KEY_xxx)
	}

	/**
	 * Gestion des entrées au clavier des utilisateurs : saisie de pseudo, adresse IP, port.
	 * @param param	L'objet Parametres dans lequel modifier la chaine correspondante.
	 */
	public void entreeUtilisateurIp(Parametres param) {
		//Renvoie la chaine saisie par l'utilisateur (pour les pseudos et les adresses)
		//Choix : ESCAPE pour arreter la saisie, RETURN pour la valider, BACK pour effacer
		while(Keyboard.next()) {
			
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
				param.setIp(new String());		//On renvoie une chaine vide quand l'utilisateur annule la saisie
			
			} else if(Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
				this.entree.deleteCharAt(this.entree.length()-1);	//On efface le dernier caractere
			
			} else if(Keyboard.getEventKey() == Keyboard.KEY_BACK) {
				param.setPseudo(this.entree.toString());	//On envoie la chaine courante (en String)
				
			} else this.entree.append(Keyboard.getEventCharacter());	//On ajoute le caractere associe a la touche
			
		}
		//getEventCharacter envoie le caractere (en char) associe (fonctionne avec Keyboard.next, les evenements)
		//getKeyName envoie le nom de la Key saisie en parametre (en String) (fonctionne avec l'int KEY_xxx)
	}

	/**
	 * Gestion des entrées au clavier des utilisateurs : saisie de pseudo, adresse IP, port.
	 * @param param	L'objet Parametres dans lequel modifier la chaine correspondante.
	 */
	public void entreeUtilisateurPort(Parametres param) {
		//Renvoie la chaine saisie par l'utilisateur (pour les pseudos et les adresses)
		//Choix : ESCAPE pour arreter la saisie, RETURN pour la valider, BACK pour effacer
		while(Keyboard.next()) {
			
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
				param.setPort(new String());		//On renvoie une chaine vide quand l'utilisateur annule la saisie
			
			} else if(Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
				this.entree.deleteCharAt(this.entree.length()-1);	//On efface le dernier caractere
			
			} else if(Keyboard.getEventKey() == Keyboard.KEY_BACK) {
				param.setPseudo(this.entree.toString());	//On envoie la chaine courante (en String)
				
			} else this.entree.append(Keyboard.getEventCharacter());	//On ajoute le caractere associe a la touche
			
		}
		//getEventCharacter envoie le caractere (en char) associe (fonctionne avec Keyboard.next, les evenements)
		//getKeyName envoie le nom de la Key saisie en parametre (en String) (fonctionne avec l'int KEY_xxx)
	}
	

	/**
	 * Fonction qui permet aux joueurs de choisir leurs touches.
	 * @param param	Objet Parametres où modifier les touches.
	 */
	//Retourne les entiers KEY_xxx : utiliser Keyboard.getKeyName()
	public void entreeControles(Parametres param) {
		//Renvoie les deux premiers caractères valides entrés par l'utilisateur
		while(Keyboard.next()) {
			
			if(param.getGauche() == (Integer) null)		//On regarde quelle touche est entree
				param.setGauche(Keyboard.getEventKey());	//Et on l'ajoute
			else if(param.getDroite() == (Integer) null)
				param.setDroite(Keyboard.getEventKey());
			//Si les deux touches sont deja saisies, on ne fait rien
			//Aucune restriction sur les touches possibles pour le moment
		}
	}
	
	//Initialiser les contrôles avec les joueurs au debut d'une partie
	/**
	 * Initialisation des contrôles d'une partie : récupère les contrôles et les associe aux objets Joueur correspondants?
	 * @param p	Objet partie où sont enregistrés les joueurs.
	 */
	public void initControles(Partie p) {
		this.partie = p;	//Récupérer la liste des joueurs
		
		Joueur j;
		Iterator<Joueur> i = this.partie.getJoueurs().iterator();		//Pour parcourir la liste de joueurs
		
		while(i.hasNext()) {
			j = i.next();
			this.controles.put(j.getToucheG(), j);
			this.controles.put(j.getToucheD(), j);
		}
	}
	
	/*
	 * Echap : quitter le jeu, fonction encore à définir
	 * Espace : plus tard, sans doute pour lancer le jeu/pause
	 * vérifie l'état du joueur : ne pas faire d'action sur les joueurs morts
	 *  -> classe enum ETAT : ne traiter que les VIVANTS
	 */
	//Gestion des contrôles en partie
	/**
	 * Gestion des entrées clavier pendant une partie : permet au joueur de tourner ainsi que l'arrêt et la mise en pause de la partie.
	 */
	public void gestionJeu() {
		Joueur j;
		
		
		//Puis on regarde les évènements
		//Re garder les évènements permet d'être plus réactif sur l'action quand on appuie/relache une touche
		while(Keyboard.next()) {
			
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) {	//Quitter la partie
				this.partie.arreterPartie();
				
			} else if(Keyboard.getEventKey() == Keyboard.KEY_SPACE && Keyboard.getEventKeyState()) {	//Quitter la partie
				this.partie.pause();
			
			} else if(this.controles.containsKey(Keyboard.getEventKey())) {	//Si l'évènement concerne les controles d'un joueur
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
			
			if(j.getEtat() == Etat.VIVANT &&  !this.partie.isRoundEnPause()) {
			
				if(j.isToucheGPresse() && !j.isToucheDPresse())		//Le joueur tourne à gauche
					j.getLigne().tournerGauche();			//Appel de la fonction pour tourner à gauche
				
				else if(j.isToucheDPresse() && !j.isToucheGPresse())	//Le joueur tourne à droite
					j.getLigne().tournerDroite();			//Appel de la fonction pour tourner à droite
				
				else j.getLigne().pasTourner();				//Sinon on tourne pas
				
			}
			
		}
	}
	
}
