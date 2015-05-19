package polyFever.module.evenements;

import java.util.*;

import org.lwjgl.input.Keyboard;

import polyFever.module.moteurDeJeu.*;

/* 
 * POUR LES EVENEMENTS FAUDRAIT PEUT ETRE AVOIR PLUSIEURS CONSTRUCTEURS, GENRE 1 POUR CHAQUE TYPE DE GESTION
 * C'EST A DIRE UNE GESTION POUR QUAND EN JEU
 * UNE GESTION POUR QUAND DANS LE MENU
 */

public class Evenements {
	private Partie partie;			//Partie
	private Hashtable controles;	//Liste des touches associes aux joueurs
	private StringBuilder entree;	//Entree utilisateur
	
	//Rappel : vous m'appelez pour les menus, donc vous m'initialiserez jamais directement avec la liste des joueurs
	public Evenements() {
		this.partie = new Partie();				//partie geree
		this.controles = new Hashtable();		//table associant les joueurs a leurs controles
		this.entree = new StringBuilder();		//buffer pour les entrees utilisateurs
	}
	/*
	//Retourne les coordonnées du clic au menu, donc c'est un couple
	public void gestionMenu() {
		//Renvoie les coordonnées de la souris quand l'utilisateur clic
	}
	
	public void entreeUtilisateur() {
		//Renvoie la chaine saisie par l'utilisateur (pour les pseudos et les adresses)
		//Arrête d'enregistrer quand l'utilisateur appuie sur Entrée
	}
	
	//Retourne les deux touches pour tourner entrées par un joueur, donc c'est un couple
	//Retourne les entiers KEY_xxx : utiliser Keyboard.getKeyName()
	public void entreeControles() {
		//Renvoie les deux premiers caractères valides entrés par l'utilisateur
	}
	*/
	//Initialiser les contrôles avec les joueurs au debut d'une partie
	public void initControles(Partie p) {
		this.partie = p;	//Récupérer la liste des joueurs
		
		Joueur j = new Joueur(p);
		Iterator<Joueur> i = this.partie.getJoueurs().iterator();		//Pour parcourir la liste de joueurs
		
		while(i.hasNext()) {
			j = i.next();
			this.controles.put(j.getToucheG(), j);
			this.controles.put(j.getToucheD(), j);
		}
	}
	
	//Gestion des contrôles en partie
	public void gestionJeu() {
		Joueur j;
		/*
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))	//Si on appuie sur echap
													//Quitter la partie (à compléter)
		*/
		//Puis on regarde les évènements
		//Re garder les évènements permet d'être plus réactif sur l'action quand on appuie/relache une touche
		while(Keyboard.next()) {
			if(this.controles.containsKey(Keyboard.getEventKey())) {	//Si l'évènement concerne les controles d'un joueur
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
			
			if(j.isToucheGPresse() && !j.isToucheDPresse())		//Le joueur tourne à gauche
				j.getLigne().tournerGauche();			//Appel de la fonction pour tourner à droite
			
			else if(j.isToucheDPresse() && !j.isToucheGPresse())	//Le joueur tourne à droite
				j.getLigne().tournerDroite();			//Appel de la fonction pour tourner à gauche
			
			else j.getLigne().pasTourner();				//Sinon on tourne pas
			
		}
	}
	
}
