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
	private long temps;					// Variable mesurant le temps d'une partie
	private long tpsPause;				// Variable donnant le temps �coul� durant une pause
	private boolean roundEnPause;		// Bool�en indiquant si le round est en pause ou non, permet aussi de d�marrer les parties (false = pas en pause ; true = en pause)
	private boolean jeu;				// Bool�en indiquant si une partie est toujours en cours ou si elle est termin�e
	private long tpsBonus;				// Variable indiquant le temps � partir duquel un bonus peut appraitre
	
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
		this.tpsPause = 0;								// Initialisation du temps d'une pause � 0
		this.trace = new ArrayList<List<Vector4>>();	// Cr�ation de la list trace
		this.roundEnPause = true;						// Initialisation du jeu en "pas en pause"
		this.jeu = true;								// Initialisation de l'�tat de jeu � "en cours"
		this.tpsBonus = (long) (Math.random() * (9000 - 5800) + 5800);	// Initialisation du temps d'apparition d'un bonus
		
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
			e.getLigne().setTpsTrou((long) (Math.random() * (4500 - 3000) + 3000));	// Calcul du temps de tra�age de trou
		}
		System.out.println("Score MAX = "+getScoreMax());
	}
	
	// M�thode faisant d�buter une partie
	public void demarrerPartie()
	{
		// On change l'�tat du jeu � "non en pause"
		this.roundEnPause = false;
	}
	
	// M�thode changeant l'�tat du jeu, de "pause" � "en cours" ou "en cours" � "pause"
	public void pause()
	{
		System.out.println("PAUSE");
		// Si le jeu est d�j� en pause
		if(this.roundEnPause)
		{
			// On calcule le temps �coul� durant la pause
			tpsPause = System.currentTimeMillis() - tpsPause;
			// On rajoute cette dur�e au temps g�n�ral de la partie, pour ne pas perdre le tra�age des trous
			temps = temps + tpsPause;
			// On change l'�tat du jeu � "pas en pause"
			this.roundEnPause = false;
		}
		// Si le jeu n'�tait pas en pause, alors on change l'�tat � "en pause" 
		else { tpsPause = System.currentTimeMillis(); this.roundEnPause = true; }
		
	}
	
	// M�thode initialisant le d�but d'un round
	public void initialiserRound()
	{
		System.out.println("\n\n====> NOUVEAU ROUND <====\n");
		
		// Calcul des positions de base des joueurs & d�finition du temps de tra�age de trou
		for(Joueur e : joueurs)		// Boucle de parcours de la liste des joueurs
		{
			e.getPosition().set((float) (Math.random() * (0.5 + 0.5) - 0.5), (float) (Math.random() * (0.5 + 0.5) - 0.5), 1);	// Calcul de la position en x et y, entre -0.5 et 0.5
			e.getAnciennePosition().set(new Vector3(0f, 0f, 0));	// Remise � z�ro de l'ancienne position
			e.getDroiteJoueur().set(new Vector4());					// Remise � z�ro de la droite joueur
			e.getAncienneDroiteJoueur().set(new Vector4());			// Remise � z�ro de l'ancienne droite joueur
			e.setDirection((double) (Math.random() * 2*Math.PI));	// Calcul d'une direction entre 0 et 2 PI
			e.getLigne().setTpsTrou((long) (Math.random() * (4500 - 3000) + 3000));	// Calcul du temps de tra�age de trou
			e.setEtat(Etat.VIVANT);
		}
		
		// Remise � z�ro des sous grilles
		for(int i = 0; i < this.trace.size(); i++)
		{
			trace.get(i).clear();
		}
		
		// Changement de l'�tat du jeu � "pause" pour attendre le d�part donn�e par un joueur
		this.roundEnPause = true;
	}
	
	// M�thode testant l'intersection de 2 segments
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
	public void repererCollisions()
	{
		/* V�rifier les coordonn�es de chaque joueur; qu'elles soient pas �gales
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
	public void apparaitreBonus()
	{
		/* 
		 * Selon un calcul, instancier des objets bonus
		 * les ajouter dans le tableau des bonus pr�sents
		 */
		
		//long delaiApparitionBonus;	// D�lai d'apparition d'un bonus
		if( ( (System.currentTimeMillis() - temps) >= tpsBonus) )
		{
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
			if( (b.getTpsDepart() + b.getDuree()) <= (System.currentTimeMillis() - temps) )
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
	public void arreterPartie()
	{
		// Mise � l'�tat de pause du jeu
		this.roundEnPause = true;
		
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
	}
	
	// M�hode comptant le nombre de joueurs encore en vie dans la partie
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
	
	public void update()
	{
		// On rep�re si des joueurs sont en collision avec une trace ou un mur
		this.repererCollisions();	// Si collisions il y a, alors la m�thode repererCollisions se charge de mettre � jour les scores et l'�tat des joueurs
		
		// On rep�re si des joueurs prennent un bonus
		this.verifBonus();	// Si bonus pris il y a, alors la m�thode repererBonus se charge de modifier les param�tres des lignes concern�es et de vider le tableau des bonus pr�sents
		
		// Voir si on fait apparaitre des bonus ou non
		this.apparaitreBonus();
		
		// Voir si il ne reste plus qu'un joueur en vie, donc fin du round
		if(nbJoueursVivant() <= 1)
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
						//arreterPartie();
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

	@Override
	public String toString() {
		return "Partie [scoreMax=" + scoreMax + ", nbJoueurs=" + nbJoueurs
				+ ", joueurs=" + joueurs + ", dimensionPlateau="
				+ dimensionPlateau + ", bonusPresents=" + bonusPresents + "]";
	}
	
}
