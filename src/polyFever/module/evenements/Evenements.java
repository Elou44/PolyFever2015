package polyFever.module.evenements;

import polyFever.lwgjl.*
import java.util.*;
import polyFever.module.moteurDeJeu.*;

/* 
 * POUR LES EVENEMENTS FAUDRAIT PEUT ETRE AVOIR PLUSIEURS CONSTRUCTEURS, GENRE 1 POUR CHAQUE TYPE DE GESTION
 * C'EST A DIRE UNE GESTION POUR QUAND EN JEU
 * UNE GESTION POUR QUAND DANS LE MENU
 */

public class Evenements {
	private Set<Joueur> joueurs;
	
	//Rappel : vous m'appelez pour les menus, donc vous m'initialiserez jamais directement avec la liste des joueurs
	public Evenements() {
		this.joueurs = new HashSet<Joueur>();
	}
	
	public void setJoueurs(Set<Joueur> joueurs) {
		this.joueurs = joueurs;
	}
	
	//Retourne les coordonnées du clic au menu, donc c'est un couple
	public List<float> gestionMenu() {
		//Renvoie les coordonnées de la souris quand l'utilisateur clic
	}
	
	public String entreeUtilisateur() {
		//Renvoie la chaine saisie par l'utilisateur (pour les pseudos et les adresses)
		//Arrête d'enregistrer quand l'utilisateur appuie sur Entrée
	}
	
	//Retourne les deux touches pour tourner entrées par un joueur, donc c'est un couple
	public List<char> entreeControles() {
		//Renvoie les deux premiers caractères valides entrés par l'utilisateur
	}
	
	//Gestion des contrôles en partie
	//Pour le moment j'utilise directement LEFT et RIGHT, je reflechie encore à comment
	//convertir un String en Keyboard.KEY_<insert key here>
	public void gestionJeu(Partie partie) {
		this.joueurs = this.setJoueurs(partie.getJoueurs());	//Récupérer la liste des joueurs
		boolean leftKeyDown, rightKeyDown;	//Savoir si le joueur est en train d'appuyer sur ses touches
		
		Iterator i = this.joueurs.listIterator();		//Pour parcourir la liste de joueurs
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))	//Si on appuie sur espace
			partie.initialiserPartie();				//Lancer la partie
		//Comment savoir si la partie n'est pas déjà initialisée !? sinon des qu'on appuie sur espace, ça reset...
		
		if(Keyboard.isKeyDown(Keyboard.Key_ESCAPE))	//Si on appuie sur echap
													//Quitter la partie (à compléter)
		
		//Parcours des joueurs
		while(i.hasNext()) {
			i.next();
			
			
			//Deux versions : la version isKeyDown (vu que vous m'appelez à chaque frame ça doit être bon)
			leftKeyDown = Keyboard.isKeyDown(Keyboard.KEY_LEFT);		//Si le joueur appuie sur ses touches
			rightKeyDown = Keyboard.isKeyDown(Keyboard.KEY_RIGHT);		//on le mémorise
			
			
			/*
			 * la version getEvent, j'ai vu qu'elle était meilleure et qu'elle empêchait de rater 
			 * des appuis de touche trop rapides
			 * par contre si vous voulez l'utiliser il me faut une variable dans Joueur pour stocker
			 * si un joueur est appuyé ou pas
			 * -> là je regarde quand la touche est appuyée, et quand elle est relachée, mais je sais pas
			 * 		dans quelle état elle est si aucun des deux n'arrive
			 
			while(Keyboard.hasNext()) {		//On regarde les évènements
				if(Keyboard.getEventKey() == Keyboard.KEY_LEFT)	//Si l'évènement concerne les controles du joueurs
					//getEvenState = true si on appuie la touche, false si on la relache
					i.leftKeyDown = Keyboard.getEventState();
				
				if(Keyboard.getEventKey() == Keyboard.KEY_RIGHT)
					i.rightKeyDown = Keyboard.getEventState();
			}
			*/
			
			if(leftKeyDown && !rightKeyDown)		//Le joueur tourne à droite
				i.getLigne().tournerGauche();		//Appel de la fonction pour tourner à droite
			
			else if(rightKeyDown && !leftKeyDown)	//Le joueur tourne à gauche
				i.getLigne().tournerDroite();		//Appel de la fonction pour tourner à gauche
			
			else i.getLigne().pasTourner();			//Sinon on tourne pas
		}
		
	}
	
}
