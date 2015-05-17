package polyFever.module.moteurDeJeu;
import polyFever.module.main.*;
import polyFever.module.util.math.Vector4;

import java.util.*;

public class Partie {

	private int scoreMax;				// Score � atteindre pour terminer une partie
	private int nbJoueurs;				// Nombre de joueurs pr�sents dans la partie
	private Set<Joueur> joueurs;		// Liste des joueurs de la partie (objet Joueur)
	private float dimensionPlateau;		// Dimensions du plateau de jeu
	private List<Bonus> bonusPresents;	// Liste des bonus pr�sents sur le plateau de jeu
	private static long temps;			// Variable mesurant le temps d'une partie
	private float tabVertex[];			// R�f�rence vers le tableau de vertex du module Affichage (dans DessinLigne)
	
	private final int nbSousGrilles = 16;	// Variable donnant le nombre de sous grilles voulues
	
	private List<List<Vector4>> trace;		// Tableau de tableau de List de Vector4, donnant les traces sur les 16 sous grilles du plateau
	/*private List<Vector2> grille0;	// Tableau de Vector2, donnant les traces sur la sous grille 0
	private List<Vector2> grille1;		// Tableau de Vector2, donnant les traces sur la sous grille 1
	private List<Vector2> grille2;		// Tableau de Vector2, donnant les traces sur la sous grille 2
	private List<Vector2> grille3;		// Tableau de Vector2, donnant les traces sur la sous grille 3
	private List<Vector2> grille4;		// Tableau de Vector2, donnant les traces sur la sous grille 4
	private List<Vector2> grille5;		// Tableau de Vector2, donnant les traces sur la sous grille 5
	private List<Vector2> grille6;		// Tableau de Vector2, donnant les traces sur la sous grille 6
	private List<Vector2> grille7;		// Tableau de Vector2, donnant les traces sur la sous grille 7
	private List<Vector2> grille8;		// Tableau de Vector2, donnant les traces sur la sous grille 8
	private List<Vector2> grille9;		// Tableau de Vector2, donnant les traces sur la sous grille 9
	private List<Vector2> grille10;		// Tableau de Vector2, donnant les traces sur la sous grille 10
	private List<Vector2> grille11;		// Tableau de Vector2, donnant les traces sur la sous grille 11
	private List<Vector2> grille12;		// Tableau de Vector2, donnant les traces sur la sous grille 12
	private List<Vector2> grille13;		// Tableau de Vector2, donnant les traces sur la sous grille 13
	private List<Vector2> grille14;		// Tableau de Vector2, donnant les traces sur la sous grille 14
	private List<Vector2> grille15;		// Tableau de Vector2, donnant les traces sur la sous grille 15*/
	
	// Constructeur
	public Partie()	// Par d�faut
	{
		System.out.println("Instanciation d'un objet Partie...");
		this.scoreMax = 0;								// Score max initialiser � 0 mais � calculer
		this.nbJoueurs = 0;								// Nombre de joueurs initialis� � 0
		this.joueurs = new HashSet<Joueur>();			// Cr�ation de la liste des joueurs
		this.dimensionPlateau = 0.0f;					// Cr�ation du vecteur des dimensions du plateau
		this.bonusPresents = new ArrayList<Bonus>();	// Cr�ation de la liste des bonus
		Partie.temps = System.currentTimeMillis();		// D�finition de l'heure de d�but de la partie
		this.trace = new ArrayList<List<Vector4>>();	// Cr�ation de la list trace
		
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
	
	public static long getTemps() {
		return temps;
	}

	public static void setTemps(long temps) {
		Partie.temps = temps;
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

	public float getDimensionPlateau() {
		return dimensionPlateau;
	}

	public void setDimensionPlateau(float dimensionPlateau) {
		this.dimensionPlateau = dimensionPlateau;
	}

	public List<Bonus> getBonusPresents() {
		return bonusPresents;
	}

	public void setBonusPresents(List<Bonus> bonusPresents) {
		this.bonusPresents = bonusPresents;
	}
	
	public float[] getTabVertex() {
		return tabVertex;
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
	
	public void initialiserPartie()
	{
		System.out.println("Initialisation de la partie...");
		// Calcul du scoreMax
		scoreMax = (nbJoueurs-1) * 10;
		
		// Calcul des dimensions du plateau
		dimensionPlateau = nbJoueurs * 100;
		
		// Calcul des positions de base des joueurs & d�finition du temps de tra�age de trou
		for(Joueur e : joueurs)		// Boucle de parcours de la liste des joueurs
		{
			e.getPosition().set((float) (Math.random() * (0.5 + 0.5) - 0.5), (float) (Math.random() * (0.5 + 0.5) - 0.5), 1);	// Calcul de la position en x et y, entre -0.5 et 0.5
			e.setDirection((double) (Math.random() * 2*Math.PI));	// Calcul d'une direction entre 0 et 2 PI
			e.getLigne().setTpsTrou((float) (Math.random() * (3.0 - 1.5) + 1.5));	// Calcul du temps de tra�age de trou
		}
		System.out.println("Score MAX = "+getScoreMax());
	}
	
	public void repererCollisions()
	{
		/* V�rifier les coordonn�es de chaque joueur; qu'elles soient pas �gales
		 * Collision contre un mur du plateau de jeu (coord > plateau)
		 * Collisison avec un autre trac�
		 * 		- Si collision : - appeler la m�thode modifier Etat pour mettre � jour l'�tat du joueur d�c�d�
		 * 						 - appeler la m�thode modifier score pour mettre � jour le score des joueurs
		 */
		
		// Parmis tous les joueurs de la partie
		for(Joueur e : joueurs)
		{
			//System.out.println("Collisions - Position du joueur x: "+e.getPosition().x()+" y: "+e.getPosition().y());
			// ### Collision plateau ###
			// Si la position du joueur en x ou en y, est sup�rieure ou �gale � 1 ou inf�rieure ou �gale � -1
			if( e.getPosition().x() >= 1 || e.getPosition().x() <= -1 || e.getPosition().y() >= 1 || e.getPosition().y() <= -1 )
			{
				System.out.println("==> Mort contre plateau \n");
				// On modifie l'�tat du joueur concern� et on le passe � mort
				this.modifierEtat(e);
			}
			
			// ### Collision trace ###

			//System.out.println("Collisions - CONTENU grille "+e.getGrille()+" de taille "+e.getPartie().getTrace().get(e.getGrille()).size()+": ");
			Iterator<Vector4> it = this.getTrace().get(e.getGrille()).iterator();
			/*
			while(it.hasNext())
			{
				Vector2 position = new Vector2();
				position = it.next();
				System.out.println("("+position.x()+","+position.y()+"), ");
			}*/
			
			// Parmis tous les points pr�sents dans le sous tableau de la grille correspondante � la position du joueur
			for(Vector4 pointGrille : this.getTrace().get(e.getGrille()))
			{
				// Si la position du joueur est la m�me que la position du point trac�
				//if( ( ( (((float) ((int) ((e.getPosition().x())*100))) / 100) >= ( (((float) ((int) ((pointGrille.x())*100))) / 100) - 0.01 ) ) && ( (((float) ((int) ((e.getPosition().x())*100))) / 100) <= ( (((float) ((int) ((pointGrille.x())*100))) / 100) + 0.01 ) ) ) && ( ( (((float) ((int) ((e.getPosition().y())*100))) / 100) >= ( (((float) ((int) ((pointGrille.y())*100))) / 100) - 0.01 ) ) && ( (((float) ((int) ((e.getPosition().y())*100))) / 100) <= ( (((float) ((int) ((pointGrille.y())*100))) / 100) + 0.01 ) ) ) )
				if( ( (((float) ((int) ((e.getPosition().x())*100))) / 100) == (((float) ((int) ((pointGrille.x())*100))) / 100) ) && ( (((float) ((int) ((e.getPosition().y())*100))) / 100) == (((float) ((int) ((pointGrille.y())*100))) / 100) ) )
				{
					//System.out.println("==> Mort contre une trace en position "+(((float) ((int) ((pointGrille.x())*100))) / 100)+" et "+(((float) ((int) ((pointGrille.y())*100))) / 100)+"\n");
					// Alors on modifie l'�tat du joueur en "mort"
					this.modifierEtat(e);
					
					// On met � jour le score des autres joueurs
					this.modifierScore();
				}
			}
		}
	}
	
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
	
	public void modifierEtat(Joueur mort)
	{
		/* Modifier l'�tat d'un joueur
		 * Pour passer de vivant | mort | quitt�
		 */
		
		System.out.println("Joueur mort "+mort.toString());
		
		// Parmis les joueurs dans la partie
		for(Joueur e : joueurs)
		{
			// Si le joueur �tudi� est le joueur d�c�d�
			if(e == mort)
			{
				e.setEtat(Etat.MORT);	// Alors on modifie son �tat en MORT
			}
		}
	}
	
	public void apparaitreBonus()
	{
		/* 
		 * Selon un calcul, instancier des objets bonus
		 * les ajouter dans le tableau des bonus pr�sents
		 */
		
		//long delaiApparitionBonus;	// D�lai d'apparition d'un bonus
		
		//if(/* Il est temps de faire apparaitre un bonus */)
		{
			// Je regarde parmis les bonus existants
			// J'en fais apparaitre un au hasard
			
			// Je d�finis sa position sur le plateau
		}
	}
	
	public void ajouterJoueur(Joueur joueur, PolyFever p)
	{
		// Incr�mentation du nombre de joueurs
		nbJoueurs = nbJoueurs + 1;
		
		// Ajout du joueur dans la liste des joueurs pr�sents dans la partie
		joueurs.add(joueur);
		
		Iterator<Joueur> i = joueurs.iterator();
		
		// Instanciation d'une ligne pour ce joueur
		while(i.hasNext())
		{
			Joueur player = i.next();
			if(player == joueur)
			{
				joueur.setLigne(new Ligne(p)); // �a me semble inutile � priori , autant instancier la ligne dans le constructeur de Joueur, �a complique la compr�hension por rien.
			}
		}
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
		
		//System.out.println("\t\t\tNEW : "+joueur.getLigne().toString());

	}
	
	public void repererBonus()
	{
		/* 
		 * M�thode qui devra rep�rer si un joueur rentre en contact avec bonus
		 * affectera le bonus au joueur
		 */
	}
	
	// M�thode ajoutant les coordonn�es du curseur du joueur dans le tableau des traces de la sous grille correspondante � sa position
	public void ajouterCoord(Vector4 coord)
	{
		// Si le curseur se situe dans la 0�me sous grille
		if(coord.x() <= -0.5 && coord.y() >= 0.5)
		{
			// On ajoute les coordonn�es dans le sous tableau correspondant
			trace.get(0).add(coord.copy());
			// Mise � jour de la sous grille actuelle ou se trouve le joueur
		}
		// Si le curseur se situe dans la 1�re sous grille
		else if( (coord.x() >= -0.5 && coord.x() <= 0) && coord.y() >= 0.5)
		{
			trace.get(1).add(coord.copy());
		}
		// Si le curseur se situe dans la 2�me sous grille
		else if( (coord.x() >= 0 && coord.x() <= 0.5) && coord.y() >= 0.5)
		{
			trace.get(2).add(coord.copy());
		}
		// Si le curseur se situe dans la 3�me sous grille
		else if(coord.x() >= 0.5 && coord.y() >= 0.5)
		{
			trace.get(3).add(coord.copy());
		}
		// Si le curseur se situe dans la 4�me sous grille
		else if(coord.x() <= -0.5 && (coord.y() >= 0 && coord.y() <= 0.5) )
		{
			trace.get(4).add(coord.copy());
		}
		// Si le curseur se situe dans la 5�me sous grille
		else if( (coord.x() >= -0.5 && coord.x() <= 0) && (coord.y() >= 0 && coord.y() <= 0.5) )
		{
			trace.get(5).add(coord.copy());
		}
		// Si le curseur se situe dans la 6�me sous grille
		else if( (coord.x() >= 0 && coord.x() <= 0.5) && (coord.y() >= 0 && coord.y() <= 0.5) )
		{
			trace.get(6).add(coord.copy());
		}
		// Si le curseur se situe dans la 7�me sous grille
		else if(coord.x() >= 0.5 && (coord.y() >= 0 && coord.y() <= 0.5) )
		{
			trace.get(7).add(coord.copy());
		}
		// Si le curseur se situe dans la 8�me sous grille
		else if(coord.x() <= -0.5 && (coord.y() >= -0.5 && coord.y() <= 0) )
		{
			trace.get(8).add(coord.copy());
		}
		// Si le curseur se situe dans la 9�me sous grille
		else if( (coord.x() >= -0.5 && coord.x() <= 0) && (coord.y() >= -0.5 && coord.y() <= 0) )
		{
			trace.get(9).add(coord.copy());
		}
		// Si le curseur se situe dans la 10�me sous grille
		else if( (coord.x() >= 0 && coord.x() <= 0.5) && (coord.y() >= -0.5 && coord.y() <= 0) )
		{
			trace.get(10).add(coord.copy());
		}
		// Si le curseur se situe dans la 11�me sous grille
		else if(coord.x() >= 0.5 && (coord.y() >= -0.5 && coord.y() <= 0) )
		{
			trace.get(11).add(coord.copy());
		}
		// Si le curseur se situe dans la 12�me sous grille
		else if(coord.x() <= -0.5 && coord.y() <= -0.5)
		{
			trace.get(12).add(coord.copy());
		}
		// Si le curseur se situe dans la 13�me sous grille
		else if( (coord.x() >= -0.5 && coord.x() <= 0) && coord.y() <= -0.5)
		{
			trace.get(13).add(coord.copy());
		}
		// Si le curseur se situe dans la 14�me sous grille
		else if( (coord.x() >= 0 && coord.x() <= 0.5) && coord.y() <= -0.5)
		{
			trace.get(14).add(coord.copy());
		}
		// Si le curseur se situe dans la 15�me sous grille
		else if(coord.x() >= 0.5 && coord.y() <= -0.5)
		{
			trace.get(15).add(coord.copy());
		}
		
/*
		System.out.println("Ligne - Ajout de coordonn�es en ("+coord.x()+","+coord.y()+") dans la grille "+this.getJoueur().getGrille());
		System.out.println("Ligne - CONTENU grille "+this.getJoueur().getGrille()+" de taille "+trace.get(joueur.getGrille()).size()+" : ");
		Iterator<Vector3> it = joueur.getPartie().getTrace().get(joueur.getGrille()).iterator();
		
		while(it.hasNext())
		{
			Vector3 pos = new Vector3();
			pos = it.next();
			System.out.println("("+pos.x()+","+pos.y()+")");
		}*/
	}
	
	/*
	 * M�thode d�finissant tout ce qu'on fait � chaque tour dans une partie
	 */
	
	public void update()
	{
		// On remplit les sous grilles avec la nouvelle trace
		int taille = this.tabVertex.length;
		
		//System.out.println("\t\t### TAILLE VERTEX : "+taille);
		
		
		// On rep�re si des joueurs sont en collision avec une trace ou un mur
		//this.repererCollisions();	// Si collisions il y a, alors la m�thode repererCollisions se charge de mettre � jour les scores et l'�tat des joueurs
		
		// On rep�re si des joueurs prennent un bonus
		//this.repererBonus();	// Si bonus pris il y a, alors la m�thode repererBonus se charge de modifier les param�tres des lignes concern�es et de vider le tableau des bonus pr�sents
		
		// Voir si on fait apparaitre des bonus ou non
		//this.apparaitreBonus();
		
		// Mettre � jour le tra�age des trous pour chaque joueur
		
		
	}
	
	public void envoyerTabVertex(float tab[])
	{
		this.tabVertex = tab;
	}

	@Override
	public String toString() {
		return "Partie [scoreMax=" + scoreMax + ", nbJoueurs=" + nbJoueurs
				+ ", joueurs=" + joueurs + ", dimensionPlateau="
				+ dimensionPlateau + ", bonusPresents=" + bonusPresents + "]";
	}
	
}
