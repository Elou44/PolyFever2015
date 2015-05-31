package polyFever.module.moteurDeJeu;
import polyFever.module.main.*;
import polyFever.module.menu.Menu;
import polyFever.module.menu.StructureMenu;
import polyFever.module.util.math.*;

import java.util.*;

/**
 * Ceci est la classe Partie. 
 * Classe cr�ant des objets Partie servant � stocker toutes les informations relatives � une partie de PolyFever.
 * 
 * @param scoreMax Entier donnant le score maximum qu'un joueur doit atteindre pour gagner une partie.
 * @param nbJoueurs Entier donannt le nombre de joueurs ajout�s dans l'objet Partie.
 * @param joueurs
 * 			Tableau Set contenant les objets Joueur de chaque joueur pr�sent dans la partie
 * @param bonusPresents
 * 			Tableau List contenant tous les objets Bonus pr�sents sur le plateau de jeu
 * @param temps
 * 			Long donnant le temps (en millisecondes) du d�but de la partie (� son instanciation), pour calculer le tra�age des trous et apparition des Bonus
 * @param tpsPause
 * 			Long donnant le temps (en millisecondes) du d�but d'une pause, pour calculer la dur�e d'une pause et l'ajouter � la variable temps et respecter le tra�age des trous et gestion des Bonus
 * @param roundEnPause
 * 			Bool�en indiquant si la partie est en pause (true) ou non (false)
 * @param jeu
 * 			Bool�en indiquant si une partie est termin�e (true) ou non (false)
 * @param tpsBonus
 * 			Long donnant le temps (en millisecondes) � partir duquel un Bonus peut apparaitre
 * @param nbSousGrilles
 * 			Entier constant, indiquant le nombre de sous grilles choisies pour d�couper le plateau. Utile pour la d�tection des collisions
 * @param trace
 * 			Tableau List de List contenant les Vector4 des traces laiss�s par les joueurs. Utile pour la d�tection des collisions
 * @param p
 * 			Objet polyFever permettant la connexion avec le module Main
 * @param structMenu
 * 			Objet StructureMenu permettant la connexion avec le module Menu, l'arr�t d'une partie et le changement de menu courant
 * 
 * @author Fr�d�ric Llorca
 *
 */

public class Partie {

	private int scoreMax;				// Score � atteindre pour terminer une partie
	private int nbJoueurs;				// Nombre de joueurs pr�sents dans la partie
	private Set<Joueur> joueurs;		// Liste des joueurs de la partie (objet Joueur)
	private List<Bonus> bonusPresents;	// Liste des bonus pr�sents sur le plateau de jeu
	private long temps;					// Variable mesurant le temps d'une partie
	private long tpsPause;				// Variable donnant le temps �coul� durant une pause
	private boolean roundEnPause;		// Bool�en indiquant si le round est en pause ou non, permet aussi de d�marrer les parties (false = pas en pause ; true = en pause)
	private boolean jeu;				// Bool�en indiquant si une partie est toujours en cours ou si elle est termin�e
	private long tpsBonus;				// Variable indiquant le temps � partir duquel un bonus peut appraitre
	private final int nbSousGrilles = 16;	// Variable donnant le nombre de sous grilles voulues
	private List<List<Vector4>> trace;		// Tableau de tableau de List de Vector4, donnant les traces sur les 16 sous grilles du plateau
	private PolyFever p;
	private StructureMenu structMenu;	// Objet StructureMenu
	
	// Constructeur
	/**
	 * Constructeur d'un objet Partie
	 * Ce constructeur instancie un objet Partie par d�faut, initialisant les tableaux, variables n�cessaires au stockage d'une partie.
	 * G�n�re aussi les sous grilles d�coupant le plateau de jeu.
	 * Constructeur sans param�tres
	 */
	public Partie(PolyFever p, StructureMenu structMenu)	// Par d�faut
	{
		System.out.println("Instanciation d'un objet Partie...");
		this.p = p;
		this.scoreMax = 0;								// Score max initialiser � 0 mais � calculer
		this.nbJoueurs = 0;								// Nombre de joueurs initialis� � 0
		this.joueurs = new HashSet<Joueur>();			// Cr�ation de la liste des joueurs
		this.bonusPresents = new ArrayList<Bonus>();	// Cr�ation de la liste des bonus
		this.temps = 0;									// D�finition de l'heure de d�but de la partie
		this.tpsPause = 0;								// Initialisation du temps d'une pause � 0
		this.trace = new ArrayList<List<Vector4>>();	// Cr�ation de la list trace
		this.roundEnPause = false;						// Initialisation du jeu en "pas en pause"
		this.pause();									// Appel de la m�thode pause pour mettre le jeu en pause
		this.jeu = true;								// Initialisation de l'�tat de jeu � "en cours"
		this.tpsBonus = (long) (Math.random() * (9000 - 5800) + 5800);	// Initialisation du temps d'apparition d'un bonus
		this.structMenu = structMenu;
		
		// Initialisation des tableaux contenant les traces
		for (int i = 0; i < nbSousGrilles; i++)
		{
			trace.add(new ArrayList<Vector4>());
		}
	}

	// M�thodes
	
	/*
	 * Assesseurs et mutateurs
	 */
	/**
	 * Retourne un bool�en indiquant si la partie est en cours (true) ou si elle est termin�e (false)
	 * @return bool�en 
	 */
	public boolean isJeu() {
		return jeu;
	}

	public boolean isRoundEnPause() {
		return roundEnPause;
	}

	public void setRoundEnPause(boolean roundEnPause) {
		this.roundEnPause = roundEnPause;
	}
	
	public long getTemps() {
		return temps;
	}

	public void setTemps(long temps) {
		this.temps = temps;
	}

	public int getScoreMax() {
		return scoreMax;
	}

	public void setScoreMax(int scoreMax) {
		this.scoreMax = scoreMax;
	}

	public int getNbJoueurs() {
		return nbJoueurs;
	}

	public void setNbJoueurs(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
	}

	public Set<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(Set<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	public List<Bonus> getBonusPresents() {
		return bonusPresents;
	}

	public void setBonusPresents(List<Bonus> bonusPresents) {
		this.bonusPresents = bonusPresents;
	}
	
	public List<List<Vector4>> getTrace() {
		return trace;
	}

	public void setTrace(List<List<Vector4>> trace) {
		this.trace = trace;
	}
	
	/*
	 * Autres m�thodes de gestion de la partie
	 */
	
	/**
	 * M�thode initialisant un objet Partie pour le d�but d'une Partie. 
	 * Initialise le score � atteindre pour gagner, d�finit le temps de d�but de la Partie, positionne chaque Joueur avec une direction et un temps de trou de mani�re al�atoire. 
	 */
	public void initialiserPartie()
	{
		System.out.println("Initialisation de la partie...");
		// Calcul du scoreMax
		scoreMax = (nbJoueurs-1) * 10;
		
		// Initialisation du temps du d�but de la partie
		this.temps = System.currentTimeMillis();
		
		// Calcul des positions de base des joueurs & d�finition du temps de tra�age de trou
		for(Joueur e : joueurs)		// Boucle de parcours de la liste des joueurs
		{
			e.getPosition().set((float) (Math.random() * (0.5 + 0.5) - 0.5), (float) (Math.random() * (0.5 + 0.5) - 0.5), 1);	// Calcul de la position en x et y, entre -0.5 et 0.5
			e.setDirection((double) (Math.random() * 2*Math.PI));	// Calcul d'une direction entre 0 et 2 PI
			e.getLigne().setTpsTrou((long) (Math.random() * (4500 - 3000) + 3000));	// Calcul du temps de tra�age de trou
		}
		System.out.println("Score MAX = "+getScoreMax());
	}
	
	// M�thode changeant l'�tat du jeu, de "pause" � "en cours" ou "en cours" � "pause"
	/**
	 * M�thode mettant le jeu en pause. Stoppe les Joueurs, l'apparition de Bonus et le tra�age des trous. 
	 * 
	 * Principe : 
	 * 
	 * 		A l'activation d'une pause on d�finit le temps de d�but pour calculer la dur�e de la pause et ensuite l'ajouter au temps g�n�ral de la Partie. 
	 * 		Ceci permet de respecter le tra�age des trous par les Joueurs et l'apparition et dur�e d'effet des Bonus.
	 */
	public void pause()
	{
		System.out.println("PAUSE "+roundEnPause);
		// Si le jeu est d�j� en pause
		if(this.roundEnPause && this.jeu)
		{
			// On calcule le temps �coul� durant la pause
			tpsPause = System.currentTimeMillis() - tpsPause;
			// On rajoute cette dur�e au temps g�n�ral de la partie, pour ne pas perdre le tra�age des trous
			temps = temps + tpsPause;
			// On ajoute cette dur�e � la dur�e des bonus pour que l'effet persiste bien le temps voulu
			for(Bonus b : this.bonusPresents)
			{
				b.setTpsDepart(b.getTpsDepart()+tpsPause);
			}
			// On change l'�tat du jeu � "pas en pause"
			this.roundEnPause = false;
		}
		// Si le jeu n'�tait pas en pause, alors on change l'�tat � "en pause" 
		else { tpsPause = System.currentTimeMillis(); this.roundEnPause = true; }
		
	}
	
	// M�thode initialisant le d�but d'un round
	/**
	 * M�thode initialisant les variables pour un nouveau Round. 
	 * 
	 * Principe : 
	 * 
	 * 		On red�finit le temps g�n�ral de la Partie 
	 * 		Repositionnement des Joueurs 
	 * 		Effacement du contenu des sous grilles servant � la d�tection des collisions 
	 * 		Effacement du contenu du tableau de vertex du module Affichage, permettant l'effacement des traces d�j� pr�sentes 
	 */
	public void initialiserRound()
	{
		if(this.jeu)
		{
			System.out.println("=======> NOUVEAU ROUND <=======");
			
			// Remise � z�ro du temps pour le d�but du round
			this.temps = System.currentTimeMillis();
			
			// Calcul des positions de base des joueurs & d�finition du temps de tra�age de trou
			for(Joueur e : joueurs)		// Boucle de parcours de la liste des joueurs
			{
				e.getPosition().set((float) (Math.random() * (0.5 + 0.5) - 0.5), (float) (Math.random() * (0.5 + 0.5) - 0.5), 1);	// Calcul de la position en x et y, entre -0.5 et 0.5
				e.getAnciennePosition().set(new Vector3());	// Remise � z�ro de l'ancienne position
				e.getDroiteJoueur().set(new Vector4());					// Remise � z�ro de la droite joueur
				e.getAncienneDroiteJoueur().set(new Vector4());			// Remise � z�ro de l'ancienne droite joueur
				e.setDirection((double) (Math.random() * 2*Math.PI));	// Calcul d'une direction entre 0 et 2 PI
				e.getLigne().setTpsTrou((long) (Math.random() * (4500 - 3000) + 3000));	// Calcul du temps de tra�age de trou
				e.setEtat(Etat.VIVANT);
			}
			
			p.affichage.dJeu.dPlateau.dLigne.updatePosJoueurs(true);
			
			// Remise � z�ro des sous grilles
			for(int i = 0; i < this.trace.size(); i++)
			{
				trace.get(i).clear();
			}
			
			// Remise � z�ro du tableau de vertex du module Affichage
			p.affichage.dJeu.dPlateau.dLigne.clearTabVertex();
			
			// Changement de l'�tat du jeu � "pause" pour attendre le d�part donn�e par un joueur
			this.pause();
		}
	}
	
	// M�thode testant l'intersection de 2 segments
	/**
	 * M�thode permettant de d�tecter l'intersection de deux lignes. 
	 * 
	 * Principe : 
	 * 		Les indices donn�s en param�tres sont les coordonn�es du point suppos� de collision. 
	 * 		Ensuite selon la position en x et en y de la droite repr�sentant le joueur et la droite de la trace, 
	 * 		on peut d�duire qu'il y a eu collision ou non
	 * 
	 * @param indiceA
	 * @param indiceB
	 * @param joueur
	 * @param trace
	 * @return
	 */
	private boolean ligneIntersection(float indiceA, float indiceB, Vector4 joueur, Vector4 trace)
	{
		if(indiceA == 0.0 || indiceB == 0.0)
		{
			return false;
		}
		else
		{
	        if (joueur.x() >= joueur.z()) {
	            if (!(joueur.z() <= indiceA && indiceA <= joueur.x())) {return false;}
	        } else {
	            if (!(joueur.x() <= indiceA && indiceA <= joueur.z())) {return false;}
	        }
	        if (joueur.y() >= joueur.w()) {
	            if (!(joueur.w() <= indiceB && indiceB <= joueur.y())) {return false;}
	        } else {
	            if (!(joueur.y() <= indiceB && indiceB <= joueur.w())) {return false;}
	        }
	        if (trace.x() >= trace.z()) {
	            if (!(trace.z() <= indiceA && indiceA <= trace.x())) {return false;}
	        } else {
	            if (!(trace.x() <= indiceA && indiceA <= trace.z())) {return false;}
	        }
	        if (trace.y() >= trace.w()) {
	            if (!(trace.w() <= indiceB && indiceB <= trace.y())) {return false;}
	        } else {
	            if (!(trace.y() <= indiceB && indiceB <= trace.w())) {return false;}
	        }
		}
		return true;
	}
	
	// M�thode de d�tection des collisions, entre le joueur / bords du plateau et joueur / trace
	/**
	 * M�thode de d�tection des collisisons. 
	 * 
	 * Principe : 
	 * 		Pour chaque Joueur VIVANT pr�sent dans la partie, on v�rifie si sa droite de contact gauche, droite et centrale n'intersecte aucune trace 
	 * se trouvant dans la sous grille courante du Joueur (@see ligneIntersection). 
	 * 		Si une collision est rep�r�e, on change la position du Joueur en lui attribuant la position du point de collision. 
	 * 		On passe l'�tat du Joueur � MORT (@see setEtat). 
	 * 		On modifie le score des autres Joueurs (@see modifierScore). 
	 * 
	 * Concernant les droites de contact des Joueurs. La droite de contact centrale est le segment form� par la position courante 
	 * du Joueur et sa position pr�c�dente. La droite de contact gauche est le segment form� par un point situ� � gauche de la 
	 * position courante du Joueur et le point situ� de la m�me fa�on � gauche de sa position pr�c�dente. La droite de contact droite 
	 * est construite selon la m�me logique que la droite de contact gauche. 
	 */
	public void repererCollisions()
	{
		/* 
		 * V�rifier les coordonn�es de chaque joueur; qu'elles soient pas �gales
		 * Collision contre un mur du plateau de jeu (coord > plateau)
		 * Collisison avec un autre trac�
		 * 		- Si collision : - appeler la m�thode modifier Etat pour mettre � jour l'�tat du joueur d�c�d�
		 * 						 - appeler la m�thode modifier score pour mettre � jour le score des joueurs
		 */
		
		boolean collision = false;
				
		// Parmis tous les joueurs de la partie
		for(Joueur e : joueurs)
		{
			//System.out.println("Score des joueurs : "+e.getScore());
			collision = false;
			
			// Si le joueur �tudi� est en vie
			if(e.getEtat() == Etat.VIVANT)
			{
				// V�rification de collision avec les bords du plateau
				if( (e.getPosition().x() <= -1 || e.getPosition().x() >= 1 || e.getPosition().y() <= -1 || e.getPosition().y() >= 1) && e.getPosition().z() == 1 && collision == false)
				{
					collision = true;
					
					// Alors on modifie l'�tat du joueur en "mort"
					e.setEtat(Etat.MORT);	// Alors on modifie son �tat en MORT
					
					// On met � jour le score des autres joueurs
					this.modifierScore();
				}
				
				// Parmis tous les points pr�sents dans le sous tableau de la grille correspondante � la position du joueur
				for(Vector4 pointGrille : this.getTrace().get(e.getGrille()))
				{
					Vector4 droite = new Vector4();
					Vector4 droiteG = new Vector4();
					Vector4 droiteD = new Vector4();
					
					if(e.getDirection() >= 0 && e.getDirection() <= (Math.PI/2))
					{
						droite.set(e.getAnciennePosition().x()+(e.getLigne().getEpaisseur()/2), e.getAnciennePosition().y()+(e.getLigne().getEpaisseur()/2), e.getPosition().x()+(e.getLigne().getEpaisseur()/2), e.getPosition().y()+(e.getLigne().getEpaisseur()/2));
						droiteG.set(e.getAncienneDroiteJoueur().z()+(e.getLigne().getEpaisseur()/32), e.getAncienneDroiteJoueur().w()-(e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().z()+(e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().w()-(e.getLigne().getEpaisseur()/32));
						droiteD.set(e.getAncienneDroiteJoueur().x()+(e.getLigne().getEpaisseur()/32), e.getAncienneDroiteJoueur().y()-(e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().x()+(e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().y()-(e.getLigne().getEpaisseur()/32));
					}
					else if(e.getDirection() > (Math.PI/2) && e.getDirection() <= Math.PI)
					{
						droite.set(e.getAnciennePosition().x()-(e.getLigne().getEpaisseur()/2), e.getAnciennePosition().y()+(e.getLigne().getEpaisseur()/2), e.getPosition().x()-(e.getLigne().getEpaisseur()/2), e.getPosition().y()+(e.getLigne().getEpaisseur()/2));
						droiteG.set(e.getAncienneDroiteJoueur().z() + (e.getLigne().getEpaisseur()/32), e.getAncienneDroiteJoueur().w() + (e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().z() + (e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().w() + (e.getLigne().getEpaisseur()/32));
						droiteD.set(e.getAncienneDroiteJoueur().x() - (e.getLigne().getEpaisseur()/32), e.getAncienneDroiteJoueur().y() - (e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().x() - (e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().y() - (e.getLigne().getEpaisseur()/32));
					}
					else if(e.getDirection() > Math.PI && e.getDirection() <= (3*Math.PI/2))
					{
						droite.set(e.getAnciennePosition().x()-(e.getLigne().getEpaisseur()/2), e.getAnciennePosition().y()-(e.getLigne().getEpaisseur()/2), e.getPosition().x()-(e.getLigne().getEpaisseur()/2), e.getPosition().y()-(e.getLigne().getEpaisseur()/2));
						droiteG.set(e.getAncienneDroiteJoueur().z() - (e.getLigne().getEpaisseur()/32), e.getAncienneDroiteJoueur().w() + (e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().z() - (e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().w() + (e.getLigne().getEpaisseur()/32));
						droiteD.set(e.getAncienneDroiteJoueur().x() + (e.getLigne().getEpaisseur()/32), e.getAncienneDroiteJoueur().y() - (e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().x() + (e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().y() - (e.getLigne().getEpaisseur()/32));
					}
					else if(e.getDirection() > (3*Math.PI/2) && e.getDirection() <= (2*Math.PI))
					{
						droite.set(e.getAnciennePosition().x()+(e.getLigne().getEpaisseur()/2), e.getAnciennePosition().y()-(e.getLigne().getEpaisseur()/2), e.getPosition().x()+(e.getLigne().getEpaisseur()/2), e.getPosition().y()-(e.getLigne().getEpaisseur()/2));
						droiteG.set(e.getAncienneDroiteJoueur().z() - (e.getLigne().getEpaisseur()/32), e.getAncienneDroiteJoueur().w() - (e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().z() - (e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().w() - (e.getLigne().getEpaisseur()/32));
						droiteD.set(e.getAncienneDroiteJoueur().x() + (e.getLigne().getEpaisseur()/32), e.getAncienneDroiteJoueur().y() + (e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().x() + (e.getLigne().getEpaisseur()/32), e.getDroiteJoueur().y() + (e.getLigne().getEpaisseur()/32));
					}
					
					float denominateur = ( ( (droite.x() - droite.z()) * (pointGrille.y() - pointGrille.w()) ) - ( (droite.y() - droite.w()) * (pointGrille.x() - pointGrille.z()) ) );
					float indiceA = ( ( ( (droite.x() * droite.w()) - (droite.y() * droite.z()) ) * (pointGrille.x() - pointGrille.z()) ) - ( (droite.x() - droite.z()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur;
					float indiceB = ( ( ( (droite.x() * droite.w()) - (droite.y() * droite.z()) ) * (pointGrille.y() - pointGrille.w()) ) - ( (droite.y() - droite.w()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur;
	
					float denominateur2 = ( ( (droiteG.x() - droiteG.z()) * (pointGrille.y() - pointGrille.w()) ) - ( (droiteG.y() - droiteG.w()) * (pointGrille.x() - pointGrille.z()) ) );
					float indiceC = ( ( ( (droiteG.x() * droiteG.w()) - (droiteG.y() * droiteG.z()) ) * (pointGrille.x() - pointGrille.z()) ) - ( (droiteG.x() - droiteG.z()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur2;
					float indiceD = ( ( ( (droiteG.x() * droiteG.w()) - (droiteG.y() * droiteG.z()) ) * (pointGrille.y() - pointGrille.w()) ) - ( (droiteG.y() - droiteG.w()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur2;
					boolean contactG = false;
					
					float denominateur3 = ( ( (droiteD.x() - droiteD.z()) * (pointGrille.y() - pointGrille.w()) ) - ( (droiteD.y() - droiteD.w()) * (pointGrille.x() - pointGrille.z()) ) );
					float indiceE = ( ( ( (droiteD.x() * droiteD.w()) - (droiteD.y() * droiteD.z()) ) * (pointGrille.x() - pointGrille.z()) ) - ( (droiteD.x() - droiteD.z()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur3;
					float indiceF = ( ( ( (droiteD.x() * droiteD.w()) - (droiteD.y() * droiteD.z()) ) * (pointGrille.y() - pointGrille.w()) ) - ( (droiteD.y() - droiteD.w()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur3;
					boolean contactD = false;
					
					// Si l'on rep�re une intersection avec la droite de contact gauche, droite ou centrale ET que le Joueur est en train de laisser une trace ET qu'il n'est pas d�j� rentr� en collision
					if( ( (contactG = ligneIntersection(indiceC, indiceD, droiteG, pointGrille)) || (contactD = ligneIntersection(indiceE, indiceF, droiteD, pointGrille)) || ligneIntersection(indiceA, indiceB, droite, pointGrille) ) && e.getPosition().z() == 1 && collision == false)
					{
						collision = true;
						/*
						System.out.println("Ancienne pos : ("+droite.x()+","+droite.y()+") ; Nouvelle pos : ("+droite.z()+","+droite.w()+")");
						System.out.println("INDICE A = "+indiceA);
						System.out.println("INDICE B = "+indiceB);
						System.out.println("INDICE C = "+indiceC);
						System.out.println("INDICE D = "+indiceD);
						System.out.println("INDICE E = "+indiceE);
						System.out.println("INDICE F = "+indiceF);
						System.out.println("Droite TRACE : ("+pointGrille.x()+","+pointGrille.y()+") et ("+pointGrille.z()+","+pointGrille.w()+")");*/
	
						// D�finition du point de collision
						if(contactG)
						{
							e.setPosition(new Vector3(indiceC, indiceD, 1));
						}
						else if(contactD)
						{
							e.setPosition(new Vector3(indiceE, indiceF, 1));
						}
						else { e.setPosition(new Vector3(indiceA, indiceB, 1)); }
						
						// Alors on modifie l'�tat du joueur en "mort"
						e.setEtat(Etat.MORT);	// Alors on modifie son �tat en MORT
						
						// On met � jour le score des autres joueurs
						this.modifierScore();
						
						System.out.println("Joueur mort : ("+e.getPosition().x()+","+e.getPosition().y()+")");
					}
				}
			}
			collision = false;
		}
	}

	// M�thode incr�mentant le score des joueurs en vie
	/**
	 * M�thode incr�mentant le score des Joueurs encore en vie dans la partie. 
	 * Cette m�thode est appel�e � la mort d'un Joueur. 
	 */
	public void modifierScore()
	{
		/* Rajouter +1 aux scores des joueurs encore en vie
		 * Voir dans la liste des joueurs
		 * Voir leurs �tats
		 * et ceux qui sont en vie +1
		 */
		
		// Parmis les joueurs dans la partie
		for(Joueur e : joueurs)
		{
			// Si l'�tat du joueur est VIVANT
			if(e.getEtat() == Etat.VIVANT)
			{
				e.setScore(e.getScore()+1);	// Alors on augmente son score de +1
			}
		}
		
	}
	
	// M�thode faisant apparaitre les bonus
	/**
	 * M�thode calculant l'apparition des Bonus sur le plateau de jeu. 
	 * 
	 * Principe : 
	 * 		Selon la valeur du temps �coul� depuis le d�but de la partie, on d�finit un temps � partir duquel un Bonus 
	 * pourra appara�tre. Une fois ce temps atteint ou d�pass� (car un test se fait toutes les 40 millisecondes 
	 * approximativemement) un Bonus apparait � une position al�atoire sr le plateau. Une dur�e al�atoire lui est aussi 
	 * attribu�e. De plus, on stocke l'index de ce Bonus dans le tableau des BonusPresents pour pouvoir facilement l'en retirer 
	 * lorsqu'il aura �t� pris. Pour finir on d�finit un nouveau temps � partir duquel un nouveau Bonus pourra apparaitre. 
	 */
	public void apparaitreBonus()
	{
		/* 
		 * Selon un calcul, instancier des objets bonus
		 * les ajouter dans le tableau des bonus pr�sents
		 */
		
		//long delaiApparitionBonus;	// D�lai d'apparition d'un bonus
		if( ( (System.currentTimeMillis() - temps) >= tpsBonus) && this.roundEnPause == false)
		{
			System.out.println("BONUS");
			// Instanciation d'un objet Bonus
			Bonus bonus = new BonusEpaisseur();
			
			// Affectation de coordonn�es
			float x;
			float y;
			bonus.setCoordonnees(new Vector4(x = ((float) (Math.random() * (0.5 + 0.5) - 0.5)), y = ((float) (Math.random() * (0.5 + 0.5) - 0.5)), x + 0.03f, y + 0.03f));
			this.envoyerTabVertex(bonus.getCoordonnees(), bonus.getCoordonnees());
			
			// Affectation du temps de d�part
			bonus.setTpsDepart((System.currentTimeMillis() - temps));
			
			// Affectation d'une dur�e d'effet
			bonus.setDuree((long) (Math.random() * (4500 - 1800) + 1800));
			
			// Affectation de l'index au bonus
			bonus.setIndexTab(this.bonusPresents.size());
			
			// Ajout du bonus dans le tableau des bonus pr�sents
			this.bonusPresents.add(bonus);
			
			// Je regarde parmis les bonus existants
			// J'en fais apparaitre un au hasard
			
			tpsBonus = tpsBonus + (long) (Math.random() * (10000 - 3000) + 3000);
			System.out.println(this.bonusPresents.size()+"  BONUS "+(System.currentTimeMillis() - temps)+" -- Prochain "+tpsBonus+"\nApparu en ("+bonus.getCoordonnees().x()+","+bonus.getCoordonnees().y()+") pendant "+bonus.getDuree());
		}
	}
	
	// M�thode v�rifiant si la dur�e d'un bonus est d�pass�e
	/**
	 * M�thode v�rifiant si un Bonus a d�pass� sa dur�e d'effet et doit donc �tre supprim�. 
	 * 
	 * Principe : 
	 * 		Pour chaque Bonus pr�sent sur le plateau de jeu, on v�rifie si le temps auquel la Bonus a �t� pris 
	 * plus sa dur�e d'effet a �t� atteint ou d�pass�, ce qui implique de supprimer le Bonus.
	 */
	public void verifBonus()
	{
		/* 
		 * M�thode qui devra rep�rer si un joueur rentre en contact avec bonus
		 * affectera le bonus au joueur
		 */
		
		// Parmis tous les bonus pr�sents sur le plateau
		Iterator<Bonus> it = bonusPresents.iterator();
		
		while(it.hasNext())
		{
			Bonus b = it.next();
			
			// Si le temps de d�part + la dur�e du bonus est inf�rieure ou �gale au temps courant de la partie
			if( (b.getTpsDepart() + b.getDuree()) <= (System.currentTimeMillis() - temps) && this.roundEnPause == false )
			{
				// Arr�t de l'effet sur le joueur
				//b.retablirParametres();
				
				// Alors le bonus ne doit plus faire effet
				it.remove();
				
				System.out.println("Suppression du bonus "+b.getIndexTab()+" � "+(System.currentTimeMillis() - temps));
			}
			
			// Suppression du bonus
			b = null;
			
		}
		
	}
	
	// M�thode ajoutant un objet joueur dans la partie
	/**
	 * M�thode ajoutant un Joueur dans un objet Partie. 
	 * Cette m�thode ajoute un joueur dans le tableau des Joueurs de la Partie et initialise une Ligne pour chaque. 
	 * 
	 * @param joueur
	 * @param p
	 */
	public void ajouterJoueur(Joueur joueur, PolyFever p)
	{
		// Incr�mentation du nombre de joueurs
		nbJoueurs = nbJoueurs + 1;
		
		// Ajout du joueur dans la liste des joueurs pr�sents dans la partie
		joueurs.add(joueur);
		
		// Instanciation d'une ligne pour le joueur
		joueur.setLigne(new Ligne(p));
		
		System.out.println("\t\tANCIEN : "+joueur.getLigne().toString()+"\n\t\tNB JOUEURS : "+joueurs.size());
		
		// Connection de la ligne avec le joueur
		try {
			joueur.getLigne().setJoueur(joueur);
		}
		catch (StackOverflowError e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	// M�thode remplissant les tableaux des sous grilles avec les traces des joueurs
	/**
	 * M�thode permettant de r�cup�rer les droites repr�sentant les traces laiss�s par les Joueurs. 
	 * 
	 * Principe : 
	 * 		Selon les coordonn�es en x et en y d'un des points de la droite, on place cette trace dans la sous grille 
	 * correspondante. 
	 * 
	 * @param droiteA
	 * @param droiteB
	 */
	public void envoyerTabVertex(Vector4 droiteA, Vector4 droiteB)
	{		
		Vector4[] coord = new Vector4[2];
		coord[0] = droiteA;
		coord[1] = droiteB;
		
		for(int i = 0; i < 2; i++)
		{
			// Si le curseur se situe dans la 0�me sous grille
			if(coord[i].x() <= -0.5 && coord[i].y() >= 0.5)
			{
				// On ajoute les coordonn�es dans le sous tableau correspondant
				trace.get(0).add(coord[i].copy());
				// Mise � jour de la sous grille actuelle ou se trouve le joueur
			}
			// Si le curseur se situe dans la 1�re sous grille
			else if( (coord[i].x() >= -0.5 && coord[i].x() <= 0) && coord[i].y() >= 0.5)
			{
				trace.get(1).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 2�me sous grille
			else if( (coord[i].x() >= 0 && coord[i].x() <= 0.5) && coord[i].y() >= 0.5)
			{
				trace.get(2).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 3�me sous grille
			else if(coord[i].x() >= 0.5 && coord[i].y() >= 0.5)
			{
				trace.get(3).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 4�me sous grille
			else if(coord[i].x() <= -0.5 && (coord[i].y() >= 0 && coord[i].y() <= 0.5) )
			{
				trace.get(4).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 5�me sous grille
			else if( (coord[i].x() >= -0.5 && coord[i].x() <= 0) && (coord[i].y() >= 0 && coord[i].y() <= 0.5) )
			{
				trace.get(5).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 6�me sous grille
			else if( (coord[i].x() >= 0 && coord[i].x() <= 0.5) && (coord[i].y() >= 0 && coord[i].y() <= 0.5) )
			{
				trace.get(6).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 7�me sous grille
			else if(coord[i].x() >= 0.5 && (coord[i].y() >= 0 && coord[i].y() <= 0.5) )
			{
				trace.get(7).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 8�me sous grille
			else if(coord[i].x() <= -0.5 && (coord[i].y() >= -0.5 && coord[i].y() <= 0) )
			{
				trace.get(8).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 9�me sous grille
			else if( (coord[i].x() >= -0.5 && coord[i].x() <= 0) && (coord[i].y() >= -0.5 && coord[i].y() <= 0) )
			{
				trace.get(9).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 10�me sous grille
			else if( (coord[i].x() >= 0 && coord[i].x() <= 0.5) && (coord[i].y() >= -0.5 && coord[i].y() <= 0) )
			{
				trace.get(10).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 11�me sous grille
			else if(coord[i].x() >= 0.5 && (coord[i].y() >= -0.5 && coord[i].y() <= 0) )
			{
				trace.get(11).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 12�me sous grille
			else if(coord[i].x() <= -0.5 && coord[i].y() <= -0.5)
			{
				trace.get(12).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 13�me sous grille
			else if( (coord[i].x() >= -0.5 && coord[i].x() <= 0) && coord[i].y() <= -0.5)
			{
				trace.get(13).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 14�me sous grille
			else if( (coord[i].x() >= 0 && coord[i].x() <= 0.5) && coord[i].y() <= -0.5)
			{
				trace.get(14).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 15�me sous grille
			else if(coord[i].x() >= 0.5 && coord[i].y() <= -0.5)
			{
				trace.get(15).add(coord[i].copy());
			}
		}	
		//System.out.println("J'envoie droiteA : p4 ("+coord[0].x()+","+coord[0].y()+") ; p3 ("+coord[0].z()+","+coord[0].w()+")\n");
		//System.out.println("J'envoie droiteB : p1 ("+coord[1].x()+","+coord[1].y()+") ; p2 ("+coord[1].z()+","+coord[1].w()+")\n");
		
	}
	
	// M�thode v�rifiant si un joueur ayant atteint le scoreMax a gagn� la partie ou non
	/**
	 * M�thode v�rifiant si un Joueur ayant atteint le score maximum a vraiment gagn� la partie. 
	 * C'est � dire que cette m�thode v�rifie que le Joueur a bien au moins deux points de diff�rence avec le deuxi�me 
	 * Joueur ayant le plus de points. 
	 * 
	 * @param joueur
	 * @return
	 */
	private boolean verifGagnant(Joueur joueur)
	{
		for(Joueur a : joueurs)
		{
			if(joueur != a && joueur.getScore() < a.getScore()+2)
			{
				return false;
			}
		}
		return true;
	}
	
	// M�thode annon�ant la fin d'une partie
	/**
	 * M�thode arr�tant proprement la partie. 
	 * Cette m�thode donne une r�f�rence null � tous les objets relatifs � la partie pour que le 
	 * ramasse-miettes les suppriment d�finitivement. On change le statut de la partie � false pour 
	 * signifier aux autres modules que la partie est termin�e. 
	 */
	public void arreterPartie()
	{
		// Mise � l'�tat de pause du jeu
		//this.pause();
		
		// "Suppression" des joueurs et de leurs lignes, pour que le ramasse-miettes les suppriment vraiment
		for(Joueur e : joueurs)
		{
			e.setLigne(null);
			e = null;
		}
		
		// "Suppression" des bonus
		for(Bonus b : bonusPresents)
		{
			b = null;
		}
		
		// Changement de l'�tat de la partie
		this.jeu = false;
		
		// Mise � vrai du bool�en indiquant que l'on se trouve dans le Menu
		Menu.isMenu = true;
		
		// Changement du menu courant
		this.structMenu.setCurMenu(this.structMenu.getM_home());
		p.getAffichage().dMenu.updateMenu(structMenu.getCurMenu()); // On appelle la m�thode de l'affichage qui va dessiner le nouveau menu
	}
	
	// M�hode comptant le nombre de joueurs encore en vie dans la partie
	/**
	 * M�thode comptant le nombre de Joueurs encore en vie dans la partie. 
	 * 
	 * @return nombre de Joueurs vivants 
	 */
	private int nbJoueursVivant()
	{
		int nb = 0;		// D�finition d'une variable accueillant le nombre de joueurs
		
		// PArmis tous les joueurs
		for(Joueur e : joueurs)
		{
			// Si le joueur est en vie
			if(e.getEtat() == Etat.VIVANT)
			{
				// Incr�mentation de nb
				nb++;
			}
		}
		
		return nb;
	}
	
	
	/*
	 * M�thode d�finissant tout ce qu'on fait � chaque tour dans une partie
	 */
	/**
	 * M�thode r�alisant les tests et traitements n�cessaires � chaque tour de boucle du jeu. 
	 * 
	 * Principe : 
	 * 		On commence par d�tecter si il y a des collisions (@see repererCollisions). 
	 * 		On v�rifie si des Bonus ne doivent plus avoir effet (@see verifBonus). 
	 * 		On v�rifie si l'on doit faire appara�tre un Bonus (@see apparaitreBonus). 
	 * 		Si il ne reste plus qu'un Joueur en vie alors c'est la fin d'un Round et il faut 
	 * v�rifier si l'un des Joueurs a gagn� la partie. 
	 */
	public void update()
	{
		// On rep�re si des joueurs sont en collision avec une trace ou un mur
		this.repererCollisions();	// Si collisions il y a, alors la m�thode repererCollisions se charge de mettre � jour les scores et l'�tat des joueurs
		
		// On rep�re si des joueurs prennent un bonus
		this.verifBonus();	// Si bonus pris il y a, alors la m�thode repererBonus se charge de modifier les param�tres des lignes concern�es et de vider le tableau des bonus pr�sents
		
		// Voir si on fait apparaitre des bonus ou non
		this.apparaitreBonus();
		
		// Voir si il ne reste plus qu'un joueur en vie, donc fin du round
		if(nbJoueursVivant() <= 1 && this.jeu)
		{
			/* 
			 * Si il ne reste plus qu'un joueur en vie
			 * 	V�rifier si le score max a �t� atteint
			 * 		Si oui : v�rifier que l'�cart avec le 2�me et de plus de 2 points
			 * 		Si non : repartir sur un nouveau round
			 */
			boolean nvRound = false;
			
			// Parmis tous les joueurs
			for(Joueur e : joueurs)
			{
				// Si le score du joueur est sup ou �gal au scoreMax
				if(e.getScore() >= scoreMax)
				{
					// On v�rifie si il a plus de 2 points par rapport aux autres joueurs
					if(verifGagnant(e))
					{
						System.out.println("Grand gagnant");
						arreterPartie();
						// FIN DE LA PARTIE
					}
				}
				// Si non, alors on initialise un nouveau round, si on ne l'a pas d�j� fait
				else if(!nvRound)
				{
					this.initialiserRound();
					nvRound = true;
				}
			}
		}
		
	}

	/**
	 * M�thode renvoyant une chaine de caract�res d�crivant un objet Partie
	 */
	@Override
	public String toString() {
		return "Partie [scoreMax=" + scoreMax + ", nbJoueurs=" + nbJoueurs
				+ ", joueurs=" + joueurs + ", bonusPresents=" + bonusPresents + "]";
	}
	
}
