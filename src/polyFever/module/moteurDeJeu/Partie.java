package polyFever.module.moteurDeJeu;
import polyFever.module.main.*;
import polyFever.module.menu.Menu;
import polyFever.module.menu.StructureMenu;
import polyFever.module.util.math.*;

import java.util.*;

/**
 * Ceci est la classe Partie. 
 * Classe créant des objets Partie servant à stocker toutes les informations relatives à une partie de PolyFever.
 * 
 * @param scoreMax Entier donnant le score maximum qu'un joueur doit atteindre pour gagner une partie.
 * @param nbJoueurs Entier donannt le nombre de joueurs ajoutés dans l'objet Partie.
 * @param joueurs
 * 			Tableau Set contenant les objets Joueur de chaque joueur présent dans la partie
 * @param bonusPresents
 * 			Tableau List contenant tous les objets Bonus présents sur le plateau de jeu
 * @param temps
 * 			Long donnant le temps (en millisecondes) du début de la partie (à son instanciation), pour calculer le traçage des trous et apparition des Bonus
 * @param tpsPause
 * 			Long donnant le temps (en millisecondes) du début d'une pause, pour calculer la durée d'une pause et l'ajouter à la variable temps et respecter le traçage des trous et gestion des Bonus
 * @param roundEnPause
 * 			Booléen indiquant si la partie est en pause (true) ou non (false)
 * @param jeu
 * 			Booléen indiquant si une partie est terminée (true) ou non (false)
 * @param tpsBonus
 * 			Long donnant le temps (en millisecondes) à partir duquel un Bonus peut apparaitre
 * @param nbSousGrilles
 * 			Entier constant, indiquant le nombre de sous grilles choisies pour découper le plateau. Utile pour la détection des collisions
 * @param trace
 * 			Tableau List de List contenant les Vector4 des traces laissés par les joueurs. Utile pour la détection des collisions
 * @param p
 * 			Objet polyFever permettant la connexion avec le module Main
 * @param structMenu
 * 			Objet StructureMenu permettant la connexion avec le module Menu, l'arrêt d'une partie et le changement de menu courant
 * 
 * @author Frédéric Llorca
 *
 */

public class Partie {

	private int scoreMax;				// Score à atteindre pour terminer une partie
	private int nbJoueurs;				// Nombre de joueurs présents dans la partie
	private Set<Joueur> joueurs;		// Liste des joueurs de la partie (objet Joueur)
	private List<Bonus> bonusPresents;	// Liste des bonus présents sur le plateau de jeu
	private long temps;					// Variable mesurant le temps d'une partie
	private long tpsPause;				// Variable donnant le temps écoulé durant une pause
	private boolean roundEnPause;		// Booléen indiquant si le round est en pause ou non, permet aussi de démarrer les parties (false = pas en pause ; true = en pause)
	private boolean jeu;				// Booléen indiquant si une partie est toujours en cours ou si elle est terminée
	private long tpsBonus;				// Variable indiquant le temps à partir duquel un bonus peut appraitre
	private final int nbSousGrilles = 16;	// Variable donnant le nombre de sous grilles voulues
	private List<List<Vector4>> trace;		// Tableau de tableau de List de Vector4, donnant les traces sur les 16 sous grilles du plateau
	private PolyFever p;
	private StructureMenu structMenu;	// Objet StructureMenu
	
	// Constructeur
	/**
	 * Constructeur d'un objet Partie
	 * Ce constructeur instancie un objet Partie par défaut, initialisant les tableaux, variables nécessaires au stockage d'une partie.
	 * Génére aussi les sous grilles découpant le plateau de jeu.
	 * Constructeur sans paramètres
	 */
	public Partie(PolyFever p, StructureMenu structMenu)	// Par défaut
	{
		System.out.println("Instanciation d'un objet Partie...");
		this.p = p;
		this.scoreMax = 0;								// Score max initialiser à 0 mais à calculer
		this.nbJoueurs = 0;								// Nombre de joueurs initialisé à 0
		this.joueurs = new HashSet<Joueur>();			// Création de la liste des joueurs
		this.bonusPresents = new ArrayList<Bonus>();	// Création de la liste des bonus
		this.temps = 0;									// Définition de l'heure de début de la partie
		this.tpsPause = 0;								// Initialisation du temps d'une pause à 0
		this.trace = new ArrayList<List<Vector4>>();	// Création de la list trace
		this.roundEnPause = false;						// Initialisation du jeu en "pas en pause"
		this.pause();									// Appel de la méthode pause pour mettre le jeu en pause
		this.jeu = true;								// Initialisation de l'état de jeu à "en cours"
		this.tpsBonus = (long) (Math.random() * (9000 - 5800) + 5800);	// Initialisation du temps d'apparition d'un bonus
		this.structMenu = structMenu;
		
		// Initialisation des tableaux contenant les traces
		for (int i = 0; i < nbSousGrilles; i++)
		{
			trace.add(new ArrayList<Vector4>());
		}
	}

	// Méthodes
	
	/*
	 * Assesseurs et mutateurs
	 */
	/**
	 * Retourne un booléen indiquant si la partie est en cours (true) ou si elle est terminée (false)
	 * @return booléen 
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
	 * Autres méthodes de gestion de la partie
	 */
	
	/**
	 * Méthode initialisant un objet Partie pour le début d'une Partie. 
	 * Initialise le score à atteindre pour gagner, définit le temps de début de la Partie, positionne chaque Joueur avec une direction et un temps de trou de manière aléatoire. 
	 */
	public void initialiserPartie()
	{
		System.out.println("Initialisation de la partie...");
		// Calcul du scoreMax
		scoreMax = (nbJoueurs-1) * 10;
		
		// Initialisation du temps du début de la partie
		this.temps = System.currentTimeMillis();
		
		// Calcul des positions de base des joueurs & définition du temps de traçage de trou
		for(Joueur e : joueurs)		// Boucle de parcours de la liste des joueurs
		{
			e.getPosition().set((float) (Math.random() * (0.5 + 0.5) - 0.5), (float) (Math.random() * (0.5 + 0.5) - 0.5), 1);	// Calcul de la position en x et y, entre -0.5 et 0.5
			e.setDirection((double) (Math.random() * 2*Math.PI));	// Calcul d'une direction entre 0 et 2 PI
			e.getLigne().setTpsTrou((long) (Math.random() * (4500 - 3000) + 3000));	// Calcul du temps de traçage de trou
		}
		System.out.println("Score MAX = "+getScoreMax());
	}
	
	// Méthode changeant l'état du jeu, de "pause" à "en cours" ou "en cours" à "pause"
	/**
	 * Méthode mettant le jeu en pause. Stoppe les Joueurs, l'apparition de Bonus et le traçage des trous. 
	 * 
	 * Principe : 
	 * 
	 * 		A l'activation d'une pause on définit le temps de début pour calculer la durée de la pause et ensuite l'ajouter au temps général de la Partie. 
	 * 		Ceci permet de respecter le traçage des trous par les Joueurs et l'apparition et durée d'effet des Bonus.
	 */
	public void pause()
	{
		System.out.println("PAUSE "+roundEnPause);
		// Si le jeu est déjà en pause
		if(this.roundEnPause && this.jeu)
		{
			// On calcule le temps écoulé durant la pause
			tpsPause = System.currentTimeMillis() - tpsPause;
			// On rajoute cette durée au temps général de la partie, pour ne pas perdre le traçage des trous
			temps = temps + tpsPause;
			// On ajoute cette durée à la durée des bonus pour que l'effet persiste bien le temps voulu
			for(Bonus b : this.bonusPresents)
			{
				b.setTpsDepart(b.getTpsDepart()+tpsPause);
			}
			// On change l'état du jeu à "pas en pause"
			this.roundEnPause = false;
		}
		// Si le jeu n'était pas en pause, alors on change l'état à "en pause" 
		else { tpsPause = System.currentTimeMillis(); this.roundEnPause = true; }
		
	}
	
	// Méthode initialisant le début d'un round
	/**
	 * Méthode initialisant les variables pour un nouveau Round. 
	 * 
	 * Principe : 
	 * 
	 * 		On redéfinit le temps général de la Partie 
	 * 		Repositionnement des Joueurs 
	 * 		Effacement du contenu des sous grilles servant à la détection des collisions 
	 * 		Effacement du contenu du tableau de vertex du module Affichage, permettant l'effacement des traces déjà présentes 
	 */
	public void initialiserRound()
	{
		if(this.jeu)
		{
			System.out.println("=======> NOUVEAU ROUND <=======");
			
			// Remise à zéro du temps pour le début du round
			this.temps = System.currentTimeMillis();
			
			// Calcul des positions de base des joueurs & définition du temps de traçage de trou
			for(Joueur e : joueurs)		// Boucle de parcours de la liste des joueurs
			{
				e.getPosition().set((float) (Math.random() * (0.5 + 0.5) - 0.5), (float) (Math.random() * (0.5 + 0.5) - 0.5), 1);	// Calcul de la position en x et y, entre -0.5 et 0.5
				e.getAnciennePosition().set(new Vector3());	// Remise à zéro de l'ancienne position
				e.getDroiteJoueur().set(new Vector4());					// Remise à zéro de la droite joueur
				e.getAncienneDroiteJoueur().set(new Vector4());			// Remise à zéro de l'ancienne droite joueur
				e.setDirection((double) (Math.random() * 2*Math.PI));	// Calcul d'une direction entre 0 et 2 PI
				e.getLigne().setTpsTrou((long) (Math.random() * (4500 - 3000) + 3000));	// Calcul du temps de traçage de trou
				e.setEtat(Etat.VIVANT);
			}
			
			p.affichage.dJeu.dPlateau.dLigne.updatePosJoueurs(true);
			
			// Remise à zéro des sous grilles
			for(int i = 0; i < this.trace.size(); i++)
			{
				trace.get(i).clear();
			}
			
			// Remise à zéro du tableau de vertex du module Affichage
			p.affichage.dJeu.dPlateau.dLigne.clearTabVertex();
			
			// Changement de l'état du jeu à "pause" pour attendre le départ donnée par un joueur
			this.pause();
		}
	}
	
	// Méthode testant l'intersection de 2 segments
	/**
	 * Méthode permettant de détecter l'intersection de deux lignes. 
	 * 
	 * Principe : 
	 * 		Les indices donnés en paramètres sont les coordonnées du point supposé de collision. 
	 * 		Ensuite selon la position en x et en y de la droite représentant le joueur et la droite de la trace, 
	 * 		on peut déduire qu'il y a eu collision ou non
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
	
	// Méthode de détection des collisions, entre le joueur / bords du plateau et joueur / trace
	/**
	 * Méthode de détection des collisisons. 
	 * 
	 * Principe : 
	 * 		Pour chaque Joueur VIVANT présent dans la partie, on vérifie si sa droite de contact gauche, droite et centrale n'intersecte aucune trace 
	 * se trouvant dans la sous grille courante du Joueur (@see ligneIntersection). 
	 * 		Si une collision est repérée, on change la position du Joueur en lui attribuant la position du point de collision. 
	 * 		On passe l'état du Joueur à MORT (@see setEtat). 
	 * 		On modifie le score des autres Joueurs (@see modifierScore). 
	 * 
	 * Concernant les droites de contact des Joueurs. La droite de contact centrale est le segment formé par la position courante 
	 * du Joueur et sa position précédente. La droite de contact gauche est le segment formé par un point situé à gauche de la 
	 * position courante du Joueur et le point situé de la même façon à gauche de sa position précédente. La droite de contact droite 
	 * est construite selon la même logique que la droite de contact gauche. 
	 */
	public void repererCollisions()
	{
		/* 
		 * Vérifier les coordonnées de chaque joueur; qu'elles soient pas égales
		 * Collision contre un mur du plateau de jeu (coord > plateau)
		 * Collisison avec un autre tracé
		 * 		- Si collision : - appeler la méthode modifier Etat pour mettre à jour l'état du joueur décédé
		 * 						 - appeler la méthode modifier score pour mettre à jour le score des joueurs
		 */
		
		boolean collision = false;
				
		// Parmis tous les joueurs de la partie
		for(Joueur e : joueurs)
		{
			//System.out.println("Score des joueurs : "+e.getScore());
			collision = false;
			
			// Si le joueur étudié est en vie
			if(e.getEtat() == Etat.VIVANT)
			{
				// Vérification de collision avec les bords du plateau
				if( (e.getPosition().x() <= -1 || e.getPosition().x() >= 1 || e.getPosition().y() <= -1 || e.getPosition().y() >= 1) && e.getPosition().z() == 1 && collision == false)
				{
					collision = true;
					
					// Alors on modifie l'état du joueur en "mort"
					e.setEtat(Etat.MORT);	// Alors on modifie son état en MORT
					
					// On met à jour le score des autres joueurs
					this.modifierScore();
				}
				
				// Parmis tous les points présents dans le sous tableau de la grille correspondante à la position du joueur
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
					
					// Si l'on repère une intersection avec la droite de contact gauche, droite ou centrale ET que le Joueur est en train de laisser une trace ET qu'il n'est pas déjà rentré en collision
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
	
						// Définition du point de collision
						if(contactG)
						{
							e.setPosition(new Vector3(indiceC, indiceD, 1));
						}
						else if(contactD)
						{
							e.setPosition(new Vector3(indiceE, indiceF, 1));
						}
						else { e.setPosition(new Vector3(indiceA, indiceB, 1)); }
						
						// Alors on modifie l'état du joueur en "mort"
						e.setEtat(Etat.MORT);	// Alors on modifie son état en MORT
						
						// On met à jour le score des autres joueurs
						this.modifierScore();
						
						System.out.println("Joueur mort : ("+e.getPosition().x()+","+e.getPosition().y()+")");
					}
				}
			}
			collision = false;
		}
	}

	// Méthode incrémentant le score des joueurs en vie
	/**
	 * Méthode incrémentant le score des Joueurs encore en vie dans la partie. 
	 * Cette méthode est appelée à la mort d'un Joueur. 
	 */
	public void modifierScore()
	{
		/* Rajouter +1 aux scores des joueurs encore en vie
		 * Voir dans la liste des joueurs
		 * Voir leurs états
		 * et ceux qui sont en vie +1
		 */
		
		// Parmis les joueurs dans la partie
		for(Joueur e : joueurs)
		{
			// Si l'état du joueur est VIVANT
			if(e.getEtat() == Etat.VIVANT)
			{
				e.setScore(e.getScore()+1);	// Alors on augmente son score de +1
			}
		}
		
	}
	
	// Méthode faisant apparaitre les bonus
	/**
	 * Méthode calculant l'apparition des Bonus sur le plateau de jeu. 
	 * 
	 * Principe : 
	 * 		Selon la valeur du temps écoulé depuis le début de la partie, on définit un temps à partir duquel un Bonus 
	 * pourra apparaître. Une fois ce temps atteint ou dépassé (car un test se fait toutes les 40 millisecondes 
	 * approximativemement) un Bonus apparait à une position aléatoire sr le plateau. Une durée aléatoire lui est aussi 
	 * attribuée. De plus, on stocke l'index de ce Bonus dans le tableau des BonusPresents pour pouvoir facilement l'en retirer 
	 * lorsqu'il aura été pris. Pour finir on définit un nouveau temps à partir duquel un nouveau Bonus pourra apparaitre. 
	 */
	public void apparaitreBonus()
	{
		/* 
		 * Selon un calcul, instancier des objets bonus
		 * les ajouter dans le tableau des bonus présents
		 */
		
		//long delaiApparitionBonus;	// Délai d'apparition d'un bonus
		if( ( (System.currentTimeMillis() - temps) >= tpsBonus) && this.roundEnPause == false)
		{
			System.out.println("BONUS");
			// Instanciation d'un objet Bonus
			Bonus bonus = new BonusEpaisseur();
			
			// Affectation de coordonnées
			float x;
			float y;
			bonus.setCoordonnees(new Vector4(x = ((float) (Math.random() * (0.5 + 0.5) - 0.5)), y = ((float) (Math.random() * (0.5 + 0.5) - 0.5)), x + 0.03f, y + 0.03f));
			this.envoyerTabVertex(bonus.getCoordonnees(), bonus.getCoordonnees());
			
			// Affectation du temps de départ
			bonus.setTpsDepart((System.currentTimeMillis() - temps));
			
			// Affectation d'une durée d'effet
			bonus.setDuree((long) (Math.random() * (4500 - 1800) + 1800));
			
			// Affectation de l'index au bonus
			bonus.setIndexTab(this.bonusPresents.size());
			
			// Ajout du bonus dans le tableau des bonus présents
			this.bonusPresents.add(bonus);
			
			// Je regarde parmis les bonus existants
			// J'en fais apparaitre un au hasard
			
			tpsBonus = tpsBonus + (long) (Math.random() * (10000 - 3000) + 3000);
			System.out.println(this.bonusPresents.size()+"  BONUS "+(System.currentTimeMillis() - temps)+" -- Prochain "+tpsBonus+"\nApparu en ("+bonus.getCoordonnees().x()+","+bonus.getCoordonnees().y()+") pendant "+bonus.getDuree());
		}
	}
	
	// Méthode vérifiant si la durée d'un bonus est dépassée
	/**
	 * Méthode vérifiant si un Bonus a dépassé sa durée d'effet et doit donc être supprimé. 
	 * 
	 * Principe : 
	 * 		Pour chaque Bonus présent sur le plateau de jeu, on vérifie si le temps auquel la Bonus a été pris 
	 * plus sa durée d'effet a été atteint ou dépassé, ce qui implique de supprimer le Bonus.
	 */
	public void verifBonus()
	{
		/* 
		 * Méthode qui devra repérer si un joueur rentre en contact avec bonus
		 * affectera le bonus au joueur
		 */
		
		// Parmis tous les bonus présents sur le plateau
		Iterator<Bonus> it = bonusPresents.iterator();
		
		while(it.hasNext())
		{
			Bonus b = it.next();
			
			// Si le temps de départ + la durée du bonus est inférieure ou égale au temps courant de la partie
			if( (b.getTpsDepart() + b.getDuree()) <= (System.currentTimeMillis() - temps) && this.roundEnPause == false )
			{
				// Arrêt de l'effet sur le joueur
				//b.retablirParametres();
				
				// Alors le bonus ne doit plus faire effet
				it.remove();
				
				System.out.println("Suppression du bonus "+b.getIndexTab()+" à "+(System.currentTimeMillis() - temps));
			}
			
			// Suppression du bonus
			b = null;
			
		}
		
	}
	
	// Méthode ajoutant un objet joueur dans la partie
	/**
	 * Méthode ajoutant un Joueur dans un objet Partie. 
	 * Cette méthode ajoute un joueur dans le tableau des Joueurs de la Partie et initialise une Ligne pour chaque. 
	 * 
	 * @param joueur
	 * @param p
	 */
	public void ajouterJoueur(Joueur joueur, PolyFever p)
	{
		// Incrémentation du nombre de joueurs
		nbJoueurs = nbJoueurs + 1;
		
		// Ajout du joueur dans la liste des joueurs présents dans la partie
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
	
	// Méthode remplissant les tableaux des sous grilles avec les traces des joueurs
	/**
	 * Méthode permettant de récupérer les droites représentant les traces laissés par les Joueurs. 
	 * 
	 * Principe : 
	 * 		Selon les coordonnées en x et en y d'un des points de la droite, on place cette trace dans la sous grille 
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
			// Si le curseur se situe dans la 0ème sous grille
			if(coord[i].x() <= -0.5 && coord[i].y() >= 0.5)
			{
				// On ajoute les coordonnées dans le sous tableau correspondant
				trace.get(0).add(coord[i].copy());
				// Mise à jour de la sous grille actuelle ou se trouve le joueur
			}
			// Si le curseur se situe dans la 1ère sous grille
			else if( (coord[i].x() >= -0.5 && coord[i].x() <= 0) && coord[i].y() >= 0.5)
			{
				trace.get(1).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 2ème sous grille
			else if( (coord[i].x() >= 0 && coord[i].x() <= 0.5) && coord[i].y() >= 0.5)
			{
				trace.get(2).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 3ème sous grille
			else if(coord[i].x() >= 0.5 && coord[i].y() >= 0.5)
			{
				trace.get(3).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 4ème sous grille
			else if(coord[i].x() <= -0.5 && (coord[i].y() >= 0 && coord[i].y() <= 0.5) )
			{
				trace.get(4).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 5ème sous grille
			else if( (coord[i].x() >= -0.5 && coord[i].x() <= 0) && (coord[i].y() >= 0 && coord[i].y() <= 0.5) )
			{
				trace.get(5).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 6ème sous grille
			else if( (coord[i].x() >= 0 && coord[i].x() <= 0.5) && (coord[i].y() >= 0 && coord[i].y() <= 0.5) )
			{
				trace.get(6).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 7ème sous grille
			else if(coord[i].x() >= 0.5 && (coord[i].y() >= 0 && coord[i].y() <= 0.5) )
			{
				trace.get(7).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 8ème sous grille
			else if(coord[i].x() <= -0.5 && (coord[i].y() >= -0.5 && coord[i].y() <= 0) )
			{
				trace.get(8).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 9ème sous grille
			else if( (coord[i].x() >= -0.5 && coord[i].x() <= 0) && (coord[i].y() >= -0.5 && coord[i].y() <= 0) )
			{
				trace.get(9).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 10ème sous grille
			else if( (coord[i].x() >= 0 && coord[i].x() <= 0.5) && (coord[i].y() >= -0.5 && coord[i].y() <= 0) )
			{
				trace.get(10).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 11ème sous grille
			else if(coord[i].x() >= 0.5 && (coord[i].y() >= -0.5 && coord[i].y() <= 0) )
			{
				trace.get(11).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 12ème sous grille
			else if(coord[i].x() <= -0.5 && coord[i].y() <= -0.5)
			{
				trace.get(12).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 13ème sous grille
			else if( (coord[i].x() >= -0.5 && coord[i].x() <= 0) && coord[i].y() <= -0.5)
			{
				trace.get(13).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 14ème sous grille
			else if( (coord[i].x() >= 0 && coord[i].x() <= 0.5) && coord[i].y() <= -0.5)
			{
				trace.get(14).add(coord[i].copy());
			}
			// Si le curseur se situe dans la 15ème sous grille
			else if(coord[i].x() >= 0.5 && coord[i].y() <= -0.5)
			{
				trace.get(15).add(coord[i].copy());
			}
		}	
		//System.out.println("J'envoie droiteA : p4 ("+coord[0].x()+","+coord[0].y()+") ; p3 ("+coord[0].z()+","+coord[0].w()+")\n");
		//System.out.println("J'envoie droiteB : p1 ("+coord[1].x()+","+coord[1].y()+") ; p2 ("+coord[1].z()+","+coord[1].w()+")\n");
		
	}
	
	// Méthode vérifiant si un joueur ayant atteint le scoreMax a gagné la partie ou non
	/**
	 * Méthode vérifiant si un Joueur ayant atteint le score maximum a vraiment gagné la partie. 
	 * C'est à dire que cette méthode vérifie que le Joueur a bien au moins deux points de différence avec le deuxième 
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
	
	// Méthode annonçant la fin d'une partie
	/**
	 * Méthode arrêtant proprement la partie. 
	 * Cette méthode donne une référence null à tous les objets relatifs à la partie pour que le 
	 * ramasse-miettes les suppriment définitivement. On change le statut de la partie à false pour 
	 * signifier aux autres modules que la partie est terminée. 
	 */
	public void arreterPartie()
	{
		// Mise à l'état de pause du jeu
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
		
		// Changement de l'état de la partie
		this.jeu = false;
		
		// Mise à vrai du booléen indiquant que l'on se trouve dans le Menu
		Menu.isMenu = true;
		
		// Changement du menu courant
		this.structMenu.setCurMenu(this.structMenu.getM_home());
		p.getAffichage().dMenu.updateMenu(structMenu.getCurMenu()); // On appelle la méthode de l'affichage qui va dessiner le nouveau menu
	}
	
	// Méhode comptant le nombre de joueurs encore en vie dans la partie
	/**
	 * Méthode comptant le nombre de Joueurs encore en vie dans la partie. 
	 * 
	 * @return nombre de Joueurs vivants 
	 */
	private int nbJoueursVivant()
	{
		int nb = 0;		// Définition d'une variable accueillant le nombre de joueurs
		
		// PArmis tous les joueurs
		for(Joueur e : joueurs)
		{
			// Si le joueur est en vie
			if(e.getEtat() == Etat.VIVANT)
			{
				// Incrémentation de nb
				nb++;
			}
		}
		
		return nb;
	}
	
	
	/*
	 * Méthode définissant tout ce qu'on fait à chaque tour dans une partie
	 */
	/**
	 * Méthode réalisant les tests et traitements nécessaires à chaque tour de boucle du jeu. 
	 * 
	 * Principe : 
	 * 		On commence par détecter si il y a des collisions (@see repererCollisions). 
	 * 		On vérifie si des Bonus ne doivent plus avoir effet (@see verifBonus). 
	 * 		On vérifie si l'on doit faire apparaître un Bonus (@see apparaitreBonus). 
	 * 		Si il ne reste plus qu'un Joueur en vie alors c'est la fin d'un Round et il faut 
	 * vérifier si l'un des Joueurs a gagné la partie. 
	 */
	public void update()
	{
		// On repère si des joueurs sont en collision avec une trace ou un mur
		this.repererCollisions();	// Si collisions il y a, alors la méthode repererCollisions se charge de mettre à jour les scores et l'état des joueurs
		
		// On repère si des joueurs prennent un bonus
		this.verifBonus();	// Si bonus pris il y a, alors la méthode repererBonus se charge de modifier les paramètres des lignes concernées et de vider le tableau des bonus présents
		
		// Voir si on fait apparaitre des bonus ou non
		this.apparaitreBonus();
		
		// Voir si il ne reste plus qu'un joueur en vie, donc fin du round
		if(nbJoueursVivant() <= 1 && this.jeu)
		{
			/* 
			 * Si il ne reste plus qu'un joueur en vie
			 * 	Vérifier si le score max a été atteint
			 * 		Si oui : vérifier que l'écart avec le 2ème et de plus de 2 points
			 * 		Si non : repartir sur un nouveau round
			 */
			boolean nvRound = false;
			
			// Parmis tous les joueurs
			for(Joueur e : joueurs)
			{
				// Si le score du joueur est sup ou égal au scoreMax
				if(e.getScore() >= scoreMax)
				{
					// On vérifie si il a plus de 2 points par rapport aux autres joueurs
					if(verifGagnant(e))
					{
						System.out.println("Grand gagnant");
						arreterPartie();
						// FIN DE LA PARTIE
					}
				}
				// Si non, alors on initialise un nouveau round, si on ne l'a pas déjà fait
				else if(!nvRound)
				{
					this.initialiserRound();
					nvRound = true;
				}
			}
		}
		
	}

	/**
	 * Méthode renvoyant une chaine de caractères décrivant un objet Partie
	 */
	@Override
	public String toString() {
		return "Partie [scoreMax=" + scoreMax + ", nbJoueurs=" + nbJoueurs
				+ ", joueurs=" + joueurs + ", bonusPresents=" + bonusPresents + "]";
	}
	
}
