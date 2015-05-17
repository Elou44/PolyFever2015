package polyFever.module.moteurDeJeu;
import polyFever.module.main.*;
import polyFever.module.util.math.Vector4;

import java.util.*;

public class Partie {

	private int scoreMax;				// Score à atteindre pour terminer une partie
	private int nbJoueurs;				// Nombre de joueurs présents dans la partie
	private Set<Joueur> joueurs;		// Liste des joueurs de la partie (objet Joueur)
	private float dimensionPlateau;		// Dimensions du plateau de jeu
	private List<Bonus> bonusPresents;	// Liste des bonus présents sur le plateau de jeu
	private static long temps;			// Variable mesurant le temps d'une partie
	private float tabVertex[];			// Référence vers le tableau de vertex du module Affichage (dans DessinLigne)
	
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
	public Partie()	// Par défaut
	{
		System.out.println("Instanciation d'un objet Partie...");
		this.scoreMax = 0;								// Score max initialiser à 0 mais à calculer
		this.nbJoueurs = 0;								// Nombre de joueurs initialisé à 0
		this.joueurs = new HashSet<Joueur>();			// Création de la liste des joueurs
		this.dimensionPlateau = 0.0f;					// Création du vecteur des dimensions du plateau
		this.bonusPresents = new ArrayList<Bonus>();	// Création de la liste des bonus
		Partie.temps = System.currentTimeMillis();		// Définition de l'heure de début de la partie
		this.trace = new ArrayList<List<Vector4>>();	// Création de la list trace
		
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
	 * Autres méthodes de gestion de la partie
	 */
	
	public void initialiserPartie()
	{
		System.out.println("Initialisation de la partie...");
		// Calcul du scoreMax
		scoreMax = (nbJoueurs-1) * 10;
		
		// Calcul des dimensions du plateau
		dimensionPlateau = nbJoueurs * 100;
		
		// Calcul des positions de base des joueurs & définition du temps de traçage de trou
		for(Joueur e : joueurs)		// Boucle de parcours de la liste des joueurs
		{
			e.getPosition().set((float) (Math.random() * (0.5 + 0.5) - 0.5), (float) (Math.random() * (0.5 + 0.5) - 0.5), 1);	// Calcul de la position en x et y, entre -0.5 et 0.5
			e.setDirection((double) (Math.random() * 2*Math.PI));	// Calcul d'une direction entre 0 et 2 PI
			e.getLigne().setTpsTrou((float) (Math.random() * (3.0 - 1.5) + 1.5));	// Calcul du temps de traçage de trou
		}
		System.out.println("Score MAX = "+getScoreMax());
	}
	
	public void repererCollisions()
	{
		/* Vérifier les coordonnées de chaque joueur; qu'elles soient pas égales
		 * Collision contre un mur du plateau de jeu (coord > plateau)
		 * Collisison avec un autre tracé
		 * 		- Si collision : - appeler la méthode modifier Etat pour mettre à jour l'état du joueur décédé
		 * 						 - appeler la méthode modifier score pour mettre à jour le score des joueurs
		 */
		
		// Parmis tous les joueurs de la partie
		for(Joueur e : joueurs)
		{
			//System.out.println("Collisions - Position du joueur x: "+e.getPosition().x()+" y: "+e.getPosition().y());
			// ### Collision plateau ###
			// Si la position du joueur en x ou en y, est supérieure ou égale à 1 ou inférieure ou égale à -1
			if( e.getPosition().x() >= 1 || e.getPosition().x() <= -1 || e.getPosition().y() >= 1 || e.getPosition().y() <= -1 )
			{
				System.out.println("==> Mort contre plateau \n");
				// On modifie l'état du joueur concerné et on le passe à mort
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
			
			// Parmis tous les points présents dans le sous tableau de la grille correspondante à la position du joueur
			for(Vector4 pointGrille : this.getTrace().get(e.getGrille()))
			{
				// Si la position du joueur est la même que la position du point tracé
				//if( ( ( (((float) ((int) ((e.getPosition().x())*100))) / 100) >= ( (((float) ((int) ((pointGrille.x())*100))) / 100) - 0.01 ) ) && ( (((float) ((int) ((e.getPosition().x())*100))) / 100) <= ( (((float) ((int) ((pointGrille.x())*100))) / 100) + 0.01 ) ) ) && ( ( (((float) ((int) ((e.getPosition().y())*100))) / 100) >= ( (((float) ((int) ((pointGrille.y())*100))) / 100) - 0.01 ) ) && ( (((float) ((int) ((e.getPosition().y())*100))) / 100) <= ( (((float) ((int) ((pointGrille.y())*100))) / 100) + 0.01 ) ) ) )
				if( ( (((float) ((int) ((e.getPosition().x())*100))) / 100) == (((float) ((int) ((pointGrille.x())*100))) / 100) ) && ( (((float) ((int) ((e.getPosition().y())*100))) / 100) == (((float) ((int) ((pointGrille.y())*100))) / 100) ) )
				{
					//System.out.println("==> Mort contre une trace en position "+(((float) ((int) ((pointGrille.x())*100))) / 100)+" et "+(((float) ((int) ((pointGrille.y())*100))) / 100)+"\n");
					// Alors on modifie l'état du joueur en "mort"
					this.modifierEtat(e);
					
					// On met à jour le score des autres joueurs
					this.modifierScore();
				}
			}
		}
	}
	
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
	
	public void modifierEtat(Joueur mort)
	{
		/* Modifier l'état d'un joueur
		 * Pour passer de vivant | mort | quitté
		 */
		
		System.out.println("Joueur mort "+mort.toString());
		
		// Parmis les joueurs dans la partie
		for(Joueur e : joueurs)
		{
			// Si le joueur étudié est le joueur décédé
			if(e == mort)
			{
				e.setEtat(Etat.MORT);	// Alors on modifie son état en MORT
			}
		}
	}
	
	public void apparaitreBonus()
	{
		/* 
		 * Selon un calcul, instancier des objets bonus
		 * les ajouter dans le tableau des bonus présents
		 */
		
		//long delaiApparitionBonus;	// Délai d'apparition d'un bonus
		
		//if(/* Il est temps de faire apparaitre un bonus */)
		{
			// Je regarde parmis les bonus existants
			// J'en fais apparaitre un au hasard
			
			// Je définis sa position sur le plateau
		}
	}
	
	public void ajouterJoueur(Joueur joueur, PolyFever p)
	{
		// Incrémentation du nombre de joueurs
		nbJoueurs = nbJoueurs + 1;
		
		// Ajout du joueur dans la liste des joueurs présents dans la partie
		joueurs.add(joueur);
		
		Iterator<Joueur> i = joueurs.iterator();
		
		// Instanciation d'une ligne pour ce joueur
		while(i.hasNext())
		{
			Joueur player = i.next();
			if(player == joueur)
			{
				joueur.setLigne(new Ligne(p)); // ça me semble inutile à priori , autant instancier la ligne dans le constructeur de Joueur, ça complique la compréhension por rien.
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
		 * Méthode qui devra repérer si un joueur rentre en contact avec bonus
		 * affectera le bonus au joueur
		 */
	}
	
	// Méthode ajoutant les coordonnées du curseur du joueur dans le tableau des traces de la sous grille correspondante à sa position
	public void ajouterCoord(Vector4 coord)
	{
		// Si le curseur se situe dans la 0ème sous grille
		if(coord.x() <= -0.5 && coord.y() >= 0.5)
		{
			// On ajoute les coordonnées dans le sous tableau correspondant
			trace.get(0).add(coord.copy());
			// Mise à jour de la sous grille actuelle ou se trouve le joueur
		}
		// Si le curseur se situe dans la 1ère sous grille
		else if( (coord.x() >= -0.5 && coord.x() <= 0) && coord.y() >= 0.5)
		{
			trace.get(1).add(coord.copy());
		}
		// Si le curseur se situe dans la 2ème sous grille
		else if( (coord.x() >= 0 && coord.x() <= 0.5) && coord.y() >= 0.5)
		{
			trace.get(2).add(coord.copy());
		}
		// Si le curseur se situe dans la 3ème sous grille
		else if(coord.x() >= 0.5 && coord.y() >= 0.5)
		{
			trace.get(3).add(coord.copy());
		}
		// Si le curseur se situe dans la 4ème sous grille
		else if(coord.x() <= -0.5 && (coord.y() >= 0 && coord.y() <= 0.5) )
		{
			trace.get(4).add(coord.copy());
		}
		// Si le curseur se situe dans la 5ème sous grille
		else if( (coord.x() >= -0.5 && coord.x() <= 0) && (coord.y() >= 0 && coord.y() <= 0.5) )
		{
			trace.get(5).add(coord.copy());
		}
		// Si le curseur se situe dans la 6ème sous grille
		else if( (coord.x() >= 0 && coord.x() <= 0.5) && (coord.y() >= 0 && coord.y() <= 0.5) )
		{
			trace.get(6).add(coord.copy());
		}
		// Si le curseur se situe dans la 7ème sous grille
		else if(coord.x() >= 0.5 && (coord.y() >= 0 && coord.y() <= 0.5) )
		{
			trace.get(7).add(coord.copy());
		}
		// Si le curseur se situe dans la 8ème sous grille
		else if(coord.x() <= -0.5 && (coord.y() >= -0.5 && coord.y() <= 0) )
		{
			trace.get(8).add(coord.copy());
		}
		// Si le curseur se situe dans la 9ème sous grille
		else if( (coord.x() >= -0.5 && coord.x() <= 0) && (coord.y() >= -0.5 && coord.y() <= 0) )
		{
			trace.get(9).add(coord.copy());
		}
		// Si le curseur se situe dans la 10ème sous grille
		else if( (coord.x() >= 0 && coord.x() <= 0.5) && (coord.y() >= -0.5 && coord.y() <= 0) )
		{
			trace.get(10).add(coord.copy());
		}
		// Si le curseur se situe dans la 11ème sous grille
		else if(coord.x() >= 0.5 && (coord.y() >= -0.5 && coord.y() <= 0) )
		{
			trace.get(11).add(coord.copy());
		}
		// Si le curseur se situe dans la 12ème sous grille
		else if(coord.x() <= -0.5 && coord.y() <= -0.5)
		{
			trace.get(12).add(coord.copy());
		}
		// Si le curseur se situe dans la 13ème sous grille
		else if( (coord.x() >= -0.5 && coord.x() <= 0) && coord.y() <= -0.5)
		{
			trace.get(13).add(coord.copy());
		}
		// Si le curseur se situe dans la 14ème sous grille
		else if( (coord.x() >= 0 && coord.x() <= 0.5) && coord.y() <= -0.5)
		{
			trace.get(14).add(coord.copy());
		}
		// Si le curseur se situe dans la 15ème sous grille
		else if(coord.x() >= 0.5 && coord.y() <= -0.5)
		{
			trace.get(15).add(coord.copy());
		}
		
/*
		System.out.println("Ligne - Ajout de coordonnées en ("+coord.x()+","+coord.y()+") dans la grille "+this.getJoueur().getGrille());
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
	 * Méthode définissant tout ce qu'on fait à chaque tour dans une partie
	 */
	
	public void update()
	{
		// On remplit les sous grilles avec la nouvelle trace
		int taille = this.tabVertex.length;
		
		//System.out.println("\t\t### TAILLE VERTEX : "+taille);
		
		
		// On repère si des joueurs sont en collision avec une trace ou un mur
		//this.repererCollisions();	// Si collisions il y a, alors la méthode repererCollisions se charge de mettre à jour les scores et l'état des joueurs
		
		// On repère si des joueurs prennent un bonus
		//this.repererBonus();	// Si bonus pris il y a, alors la méthode repererBonus se charge de modifier les paramètres des lignes concernées et de vider le tableau des bonus présents
		
		// Voir si on fait apparaitre des bonus ou non
		//this.apparaitreBonus();
		
		// Mettre à jour le traçage des trous pour chaque joueur
		
		
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
