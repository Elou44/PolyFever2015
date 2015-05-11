package polyFever.module.moteurDeJeu;
import polyFever.module.main.*;
import polyFever.module.util.math.Vector3;

import java.util.*;

public class Partie {

	private int scoreMax;				// Score � atteindre pour terminer une partie
	private int nbJoueurs;				// Nombre de joueurs pr�sents dans la partie
	private Set<Joueur> joueurs;		// Liste des joueurs de la partie (objet Joueur)
	private float dimensionPlateau;		// Dimensions du plateau de jeu
	private List<Bonus> bonusPresents;	// Liste des bonus pr�sents sur le plateau de jeu
	private static long temps;			// Variable mesurant le temps d'une partie
	private List<List<Vector3>> trace;		// Tableau de tableau de Vector3, donnant les traces sur les 16 sous grilles du plateau
	private List<Vector3> grille0;		// Tableau de Vector3, donnant les traces sur la sous grille 0
	private List<Vector3> grille1;		// Tableau de Vector3, donnant les traces sur la sous grille 1
	private List<Vector3> grille2;		// Tableau de Vector3, donnant les traces sur la sous grille 2
	private List<Vector3> grille3;		// Tableau de Vector3, donnant les traces sur la sous grille 3
	private List<Vector3> grille4;		// Tableau de Vector3, donnant les traces sur la sous grille 4
	private List<Vector3> grille5;		// Tableau de Vector3, donnant les traces sur la sous grille 5
	private List<Vector3> grille6;		// Tableau de Vector3, donnant les traces sur la sous grille 6
	private List<Vector3> grille7;		// Tableau de Vector3, donnant les traces sur la sous grille 7
	private List<Vector3> grille8;		// Tableau de Vector3, donnant les traces sur la sous grille 8
	private List<Vector3> grille9;		// Tableau de Vector3, donnant les traces sur la sous grille 9
	private List<Vector3> grille10;		// Tableau de Vector3, donnant les traces sur la sous grille 10
	private List<Vector3> grille11;		// Tableau de Vector3, donnant les traces sur la sous grille 11
	private List<Vector3> grille12;		// Tableau de Vector3, donnant les traces sur la sous grille 12
	private List<Vector3> grille13;		// Tableau de Vector3, donnant les traces sur la sous grille 13
	private List<Vector3> grille14;		// Tableau de Vector3, donnant les traces sur la sous grille 14
	private List<Vector3> grille15;		// Tableau de Vector3, donnant les traces sur la sous grille 15
	
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
		
		// Initialisation des tableaux contenant les traces
		this.trace = new ArrayList<List<Vector3>>();
		this.grille0 = new ArrayList<Vector3>();
		this.grille1 = new ArrayList<Vector3>();
		this.grille2 = new ArrayList<Vector3>();
		this.grille3 = new ArrayList<Vector3>();
		this.grille4 = new ArrayList<Vector3>();
		this.grille5 = new ArrayList<Vector3>();
		this.grille6 = new ArrayList<Vector3>();
		this.grille7 = new ArrayList<Vector3>();
		this.grille8 = new ArrayList<Vector3>();
		this.grille9 = new ArrayList<Vector3>();
		this.grille10 = new ArrayList<Vector3>();
		this.grille11 = new ArrayList<Vector3>();
		this.grille12 = new ArrayList<Vector3>();
		this.grille13 = new ArrayList<Vector3>();
		this.grille14 = new ArrayList<Vector3>();
		this.grille15 = new ArrayList<Vector3>();
		
		// Remplissage du tableau trace de toutes ses sous grilles
		trace.add(grille0);
		trace.add(grille1);
		trace.add(grille2);
		trace.add(grille3);
		trace.add(grille4);
		trace.add(grille5);
		trace.add(grille6);
		trace.add(grille7);
		trace.add(grille8);
		trace.add(grille9);
		trace.add(grille10);
		trace.add(grille11);
		trace.add(grille12);
		trace.add(grille13);
		trace.add(grille14);
		trace.add(grille15);
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

	public List<List<Vector3>> getTrace() {
		return trace;
	}

	public void setTrace(List<List<Vector3>> trace) {
		this.trace = trace;
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
			System.out.println("Collisions - Position du joueur x: "+e.getPosition().x()+" y: "+e.getPosition().y());
			// ### Collision plateau ###
			// Si la position du joueur en x ou en y, est sup�rieure ou �gale � 1 ou inf�rieure ou �gale � -1
			if( e.getPosition().x() >= 1 || e.getPosition().x() <= -1 || e.getPosition().y() >= 1 || e.getPosition().y() <= -1 )
			{
				System.out.println("==> Mort contre plateau \n");
				// On modifie l'�tat du joueur concern� et on le passe � mort
				this.modifierEtat(e);
			}
			
			// ### Collision trace ###

			System.out.println("Collisions - CONTENU grille "+e.getGrille()+" de taille "+e.getPartie().getTrace().get(e.getGrille()).size()+": ");
			Iterator<Vector3> it = this.getTrace().get(e.getGrille()).iterator();
			
			while(it.hasNext())
			{
				Vector3 position = new Vector3();
				position = it.next();
				System.out.println("("+position.x()+","+position.y()+"), ");
			}
			
			// Parmis tous les points pr�sents dans le sous tableau de la grille correspondante � la position du joueur
			for(Vector3 pointGrille : this.getTrace().get(e.getGrille()))
			{
				// Si la position du joueur est la m�me que la position du point trac�
				//if( ( ( (((float) ((int) ((e.getPosition().x())*100))) / 100) >= ( (((float) ((int) ((pointGrille.x())*100))) / 100) - 0.01 ) ) && ( (((float) ((int) ((e.getPosition().x())*100))) / 100) <= ( (((float) ((int) ((pointGrille.x())*100))) / 100) + 0.01 ) ) ) && ( ( (((float) ((int) ((e.getPosition().y())*100))) / 100) >= ( (((float) ((int) ((pointGrille.y())*100))) / 100) - 0.01 ) ) && ( (((float) ((int) ((e.getPosition().y())*100))) / 100) <= ( (((float) ((int) ((pointGrille.y())*100))) / 100) + 0.01 ) ) ) )
				if( ( (((float) ((int) ((e.getPosition().x())*100))) / 100) == (((float) ((int) ((pointGrille.x())*100))) / 100) ) && ( (((float) ((int) ((e.getPosition().y())*100))) / 100) == (((float) ((int) ((pointGrille.y())*100))) / 100) ) )
				{
					System.out.println("==> Mort contre une trace en position "+(((float) ((int) ((pointGrille.x())*100))) / 100)+" et "+(((float) ((int) ((pointGrille.y())*100))) / 100)+"\n");
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
	
	/*
	 * M�thode d�finissant tout ce qu'on fait � chaque tour dans une partie
	 */
	
	public void update()
	{
		// On commence par effectuer les mouvements des joueurs
		// OU ALORS C'EST DEJA FAIT DANS LE GAME LOOP ET FAUT PAS LE FAIRE LA ???
		
		// On rep�re si des joueurs sont en collision avec une trace ou un mur
		this.repererCollisions();	// Si collisions il y a, alors la m�thode repererCollisions se charge de mettre � jour les scores et l'�tat des joueurs
		
		// On rep�re si des joueurs prennent un bonus
		//this.repererBonus();	// Si bonus pris il y a, alors la m�thode repererBonus se charge de modifier les param�tres des lignes concern�es et de vider le tableau des bonus pr�sents
		
		// Voir si on fait apparaitre des bonus ou non
		//this.apparaitreBonus();
		
		// Mettre � jour le tra�age des trous pour chaque joueur
		
		
	}

	@Override
	public String toString() {
		return "Partie [scoreMax=" + scoreMax + ", nbJoueurs=" + nbJoueurs
				+ ", joueurs=" + joueurs + ", dimensionPlateau="
				+ dimensionPlateau + ", bonusPresents=" + bonusPresents + "]";
	}
	
}
