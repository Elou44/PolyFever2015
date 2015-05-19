package polyFever.module.moteurDeJeu;
import polyFever.module.main.*;
import polyFever.module.util.math.*;

import java.util.*;

public class Partie {

	private int scoreMax;				// Score � atteindre pour terminer une partie
	private int nbJoueurs;				// Nombre de joueurs pr�sents dans la partie
	private Set<Joueur> joueurs;		// Liste des joueurs de la partie (objet Joueur)
	private float dimensionPlateau;		// Dimensions du plateau de jeu
	private List<Bonus> bonusPresents;	// Liste des bonus pr�sents sur le plateau de jeu
	private long temps;			// Variable mesurant le temps d'une partie
	private float tabVertex[];			// R�f�rence vers le tableau de vertex du module Affichage (dans DessinLigne)
	private int indexVertex;			// Index de remplissage du tableau de vertex
	
	private final int nbSousGrilles = 16;	// Variable donnant le nombre de sous grilles voulues
	
	private List<List<Vector4>> trace;		// Tableau de tableau de List de Vector4, donnant les traces sur les 16 sous grilles du plateau
	
	// Constructeur
	public Partie()	// Par d�faut
	{
		System.out.println("Instanciation d'un objet Partie...");
		this.scoreMax = 0;								// Score max initialiser � 0 mais � calculer
		this.nbJoueurs = 0;								// Nombre de joueurs initialis� � 0
		this.joueurs = new HashSet<Joueur>();			// Cr�ation de la liste des joueurs
		this.dimensionPlateau = 0.0f;					// Cr�ation du vecteur des dimensions du plateau
		this.bonusPresents = new ArrayList<Bonus>();	// Cr�ation de la liste des bonus
		this.temps = System.currentTimeMillis();		// D�finition de l'heure de d�but de la partie
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
			//e.getPosition().set((float) (Math.random() * (0.5 + 0.5) - 0.5), (float) (Math.random() * (0.5 + 0.5) - 0.5), 1);	// Calcul de la position en x et y, entre -0.5 et 0.5
			e.getPosition().set(-0.9f,-0.9f,1);
			//e.setDirection((double) (Math.random() * 2*Math.PI));	// Calcul d'une direction entre 0 et 2 PI
			e.setDirection(Math.PI/2);
			e.getLigne().setTpsTrou((long) (Math.random() * (4500 - 3000) + 3000));	// Calcul du temps de tra�age de trou
		}
		System.out.println("Score MAX = "+getScoreMax());
	}
	
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
			/*
			Iterator<Vector4> it = this.getTrace().get(e.getGrille()).iterator();
			while(it.hasNext())
			{
				Vector4 position = new Vector4();
				position = it.next();
				System.out.println("GRILLE "+e.getGrille()+" "+this.getTrace().get(e.getGrille()).size()+"("+position.x()+","+position.y()+"), ("+position.z()+","+position.w()+")\n");
			}*/
			
			// Parmis tous les points pr�sents dans le sous tableau de la grille correspondante � la position du joueur
			for(Vector4 pointGrille : this.getTrace().get(e.getGrille()))
			{				
				Vector4 droite = new Vector4(e.getAnciennePosition().x(), e.getAnciennePosition().y(), e.getPosition().x(), e.getPosition().y());
				
				float denominateur = ( ( (droite.x() - droite.z()) * (pointGrille.y() - pointGrille.w()) ) - ( (droite.y() - droite.w()) * (pointGrille.x() - pointGrille.z()) ) );
				float indiceA = ( ( ( (droite.x() * droite.w()) - (droite.y() * droite.z()) ) * (pointGrille.x() - pointGrille.z()) ) - ( (droite.x() - droite.z()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur;
				float indiceB = ( ( ( (droite.x() * droite.w()) - (droite.y() * droite.z()) ) * (pointGrille.y() - pointGrille.w()) ) - ( (droite.y() - droite.w()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur;

				// Si la position du joueur est la m�me que la position du point trac�
				if(ligneIntersection(indiceA, indiceB, droite, pointGrille) && e.getPosition().z() == 1)
				{
					System.out.println("Ancienne pos : ("+droite.x()+","+droite.y()+") ; Nouvelle pos : ("+droite.z()+","+droite.w()+")");
					System.out.println("INDICE A = "+indiceA);
					System.out.println("INDICE B = "+indiceB);
					System.out.println("Droite TRACE : ("+pointGrille.x()+","+pointGrille.y()+") et ("+pointGrille.z()+","+pointGrille.w()+")");

					// D�finition du point de collision
					e.setPosition(new Vector3(indiceA, indiceB, 1));
					
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
	
	/*
	 * M�thode d�finissant tout ce qu'on fait � chaque tour dans une partie
	 */
	
	public void update()
	{
		// On remplit les sous grilles avec la nouvelle trace
		//int taille = this.tabVertex.length;
		
		//System.out.println("\t\t### TAILLE VERTEX : "+indexVertex);
		
		
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
