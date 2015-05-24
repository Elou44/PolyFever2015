package polyFever.module.moteurDeJeu;
import polyFever.module.main.*;
import polyFever.module.util.math.*;

import java.util.*;

public class Partie {

	private int scoreMax;				// Score à atteindre pour terminer une partie
	private int nbJoueurs;				// Nombre de joueurs présents dans la partie
	private Set<Joueur> joueurs;		// Liste des joueurs de la partie (objet Joueur)
	private float dimensionPlateau;		// Dimensions du plateau de jeu
	private List<Bonus> bonusPresents;	// Liste des bonus présents sur le plateau de jeu
	private long temps;					// Variable mesurant le temps d'une partie
	private boolean roundEnPause;		// Booléen indiquant si le round est en pause ou non, permet aussi de démarrer les parties (false = pas en pause ; true = en pause)
	private boolean jeu;				// Booléen indiquant si une partie est toujours en cours ou si elle est terminée
	
	private final int nbSousGrilles = 16;	// Variable donnant le nombre de sous grilles voulues
	
	private List<List<Vector4>> trace;		// Tableau de tableau de List de Vector4, donnant les traces sur les 16 sous grilles du plateau
	
	// Constructeur
	public Partie()	// Par défaut
	{
		System.out.println("Instanciation d'un objet Partie...");
		this.scoreMax = 0;								// Score max initialiser à 0 mais à calculer
		this.nbJoueurs = 0;								// Nombre de joueurs initialisé à 0
		this.joueurs = new HashSet<Joueur>();			// Création de la liste des joueurs
		this.dimensionPlateau = 0.0f;					// Création du vecteur des dimensions du plateau
		this.bonusPresents = new ArrayList<Bonus>();	// Création de la liste des bonus
		this.temps = System.currentTimeMillis();		// Définition de l'heure de début de la partie
		this.trace = new ArrayList<List<Vector4>>();	// Création de la list trace
		this.roundEnPause = false;						// Initialisation du jeu en "pas en pause"
		this.jeu = true;								// Initialisation de l'état de jeu à "en cours"
		
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
			e.getLigne().setTpsTrou((long) (Math.random() * (4500 - 3000) + 3000));	// Calcul du temps de traçage de trou
		}
		System.out.println("Score MAX = "+getScoreMax());
	}
	
	// Méthode faisant débuter une partie
	public void demarrerPartie()
	{
		// On change l'état du jeu à "non en pause"
		this.roundEnPause = false;
	}
	
	// Méthode changeant l'état du jeu, de "pause" à "en cours" ou "en cours" à "pause"
	public void pause()
	{
		if(this.roundEnPause)
		{
			this.roundEnPause = false;
		}
		else { this.roundEnPause = true; }
		System.out.println("Jeu en "+roundEnPause);
	}
	
	// Méthode initialisant le début d'un round
	public void initialiserRound()
	{
		//System.out.println("\n\n====> NOUVEAU ROUND <====\n");
		
		// Calcul des positions de base des joueurs & définition du temps de traçage de trou
		for(Joueur e : joueurs)		// Boucle de parcours de la liste des joueurs
		{
			e.getPosition().set((float) (Math.random() * (0.5 + 0.5) - 0.5), (float) (Math.random() * (0.5 + 0.5) - 0.5), 1);	// Calcul de la position en x et y, entre -0.5 et 0.5
			e.setDirection((double) (Math.random() * 2*Math.PI));	// Calcul d'une direction entre 0 et 2 PI
			e.getLigne().setTpsTrou((long) (Math.random() * (4500 - 3000) + 3000));	// Calcul du temps de traçage de trou
		}
		
		// Changement de l'état du jeu à "pause" pour attendre le départ donnée par un joueur
		this.roundEnPause = true;
	}
	
	private boolean circleIntersection(float epaisseur, Vector3 position, Vector4 trace)
	{
		// compute the euclidean distance between A and B
		float LAB;
		LAB = (float) Math.sqrt( Math.pow(trace.z() - trace.x(), 2) + Math.pow(trace.w() - trace.y(), 2) );

		// compute the direction vector D from A to B
		float Dx, Dy;
		Dx = (trace.z() - trace.x())/LAB;
		Dy = (trace.w() - trace.y())/LAB;

		// Now the line equation is x = Dx*t + Ax, y = Dy*t + Ay with 0 <= t <= 1.

		// compute the value t of the closest point to the circle center (Cx, Cy)
		float t;
		t = Dx * (position.x() - trace.x()) + Dy * (position.y() - trace.y());

		// This is the projection of C on the line from A to B.

		// compute the coordinates of the point E on line and closest to C
		float Ex, Ey;
		Ex = t * Dx + trace.x();
		Ey = t * Dy + trace.y();

		// compute the euclidean distance from E to C
		float LEC;
		LEC = (float) Math.sqrt( Math.pow(Ex - position.x(), 2) + Math.pow(Ey - position.y(), 2) );

		// test if the line intersects the circle
		if( LEC < (epaisseur/2) )
		{
		    // compute distance from t to circle intersection point
			float dt;
		    dt = (float) Math.sqrt(Math.pow(epaisseur/2, 2) - Math.pow(LEC, 2));

		    // compute first intersection point
		    float Fx, Fy;
		    Fx = (t-dt)*Dx + trace.x();
		    Fy = (t-dt)*Dy + trace.y();

		    // compute second intersection point
		    float Gx, Gy;
		    Gx = (t+dt)*Dx + trace.x();
		    Gy = (t+dt)*Dy + trace.y();
		    
		    return true;
		}
		// else test if the line is tangent to circle
		else if( LEC == (epaisseur/2) ) // tangent point to circle is E
		{
			return true;
		}
		else // line doesn't touch circle
		{ return false; }
	}
	
	// Méthode testant l'intersection de 2 segments
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
	
	private Vector2 pointPlusProche(Vector3 position, Vector4 droiteJoueur, Vector4 pointGrille)
	{
		Vector2 segV = new Vector2();
		segV.x(Math.abs(droiteJoueur.x() - droiteJoueur.z()));
		segV.y(Math.abs(droiteJoueur.z() - droiteJoueur.w()));
		
		Vector2 ptV = new Vector2();
		ptV.x(Math.abs(position.x() - pointGrille.x()));
		ptV.y(Math.abs(position.y() - pointGrille.y()));
		
		Vector2 segVUnit = new Vector2();
		segVUnit.x(segV.x() / segV.length());
		segVUnit.y(segV.y() / segV.length());
		
		float projV;
		projV = ptV.dot(segVUnit);
		
		if(projV <= 0)
		{
			return new Vector2(droiteJoueur.x(), droiteJoueur.y());
		}
		else if (projV >= segV.length())
		{
			return new Vector2(droiteJoueur.z(), droiteJoueur.w());
		}
		
		Vector2 proj = new Vector2();
		proj.x(segVUnit.x() * projV);
		proj.y(segVUnit.y() * projV);
		
		Vector2 pres = new Vector2();
		pres.x(proj.x() + droiteJoueur.x());
		pres.y(proj.y() + droiteJoueur.y());
		
		return pres;
	}
	
	/*private boolean collision(Vector2 position, Vector2 pointProche, Vector4 droiteJoueur, float epaisseur)
	{
		
	}*/
	
	// Méthode de détection des collisions, entre le joueur / bords du plateau et joueur / trace
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
			if(e.getEtat() == Etat.VIVANT)
			{
				//System.out.println("Collisions - Position du joueur x: "+e.getPosition().x()+" y: "+e.getPosition().y());
				// ### Collision plateau ###
				// Si la position du joueur en x ou en y, est supérieure ou égale à 1 ou inférieure ou égale à -1
				/*if( e.getPosition().x() >= 1 || e.getPosition().x() <= -1 || e.getPosition().y() >= 1 || e.getPosition().y() <= -1 )
				{
					System.out.println("==> Mort contre plateau \n");
					// On modifie l'état du joueur concerné et on le passe à mort
					e.setEtat(Etat.MORT);	// Alors on modifie son état en MORT
					
					// On met à jour le score des autres joueurs
					this.modifierScore();
				}*/
				
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
				
				// Parmis tous les points présents dans le sous tableau de la grille correspondante à la position du joueur
				for(Vector4 pointGrille : this.getTrace().get(e.getGrille()))
				{
					Vector4 droite = new Vector4();
					
					if(e.getDirection() >= 0 && e.getDirection() <= (Math.PI/2))
					{
						droite.set(e.getAnciennePosition().x()+(e.getLigne().getEpaisseur()/2), e.getAnciennePosition().y()+(e.getLigne().getEpaisseur()/2), e.getPosition().x()+(e.getLigne().getEpaisseur()/2), e.getPosition().y()+(e.getLigne().getEpaisseur()/2));
					}
					else if(e.getDirection() > (Math.PI/2) && e.getDirection() <= Math.PI)
					{
						droite.set(e.getAnciennePosition().x()-(e.getLigne().getEpaisseur()/2), e.getAnciennePosition().y()+(e.getLigne().getEpaisseur()/2), e.getPosition().x()-(e.getLigne().getEpaisseur()/2), e.getPosition().y()+(e.getLigne().getEpaisseur()/2));
					}
					else if(e.getDirection() > Math.PI && e.getDirection() <= (3*Math.PI/2))
					{
						droite.set(e.getAnciennePosition().x()-(e.getLigne().getEpaisseur()/2), e.getAnciennePosition().y()-(e.getLigne().getEpaisseur()/2), e.getPosition().x()-(e.getLigne().getEpaisseur()/2), e.getPosition().y()-(e.getLigne().getEpaisseur()/2));
					}
					else if(e.getDirection() > (3*Math.PI/2) && e.getDirection() <= (2*Math.PI))
					{
						droite.set(e.getAnciennePosition().x()+(e.getLigne().getEpaisseur()/2), e.getAnciennePosition().y()-(e.getLigne().getEpaisseur()/2), e.getPosition().x()+(e.getLigne().getEpaisseur()/2), e.getPosition().y()-(e.getLigne().getEpaisseur()/2));
					}
					
					float denominateur = ( ( (droite.x() - droite.z()) * (pointGrille.y() - pointGrille.w()) ) - ( (droite.y() - droite.w()) * (pointGrille.x() - pointGrille.z()) ) );
					float indiceA = ( ( ( (droite.x() * droite.w()) - (droite.y() * droite.z()) ) * (pointGrille.x() - pointGrille.z()) ) - ( (droite.x() - droite.z()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur;
					float indiceB = ( ( ( (droite.x() * droite.w()) - (droite.y() * droite.z()) ) * (pointGrille.y() - pointGrille.w()) ) - ( (droite.y() - droite.w()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur;
	
					/*
					float denominateur2 = ( ( (e.getAncienneDroiteJoueur().z() - e.getDroiteJoueur().z()) * (pointGrille.y() - pointGrille.w()) ) - ( (e.getAncienneDroiteJoueur().w() - e.getDroiteJoueur().w()) * (pointGrille.x() - pointGrille.z()) ) );
					float indiceC = ( ( ( (e.getAncienneDroiteJoueur().z() * e.getDroiteJoueur().w()) - (e.getAncienneDroiteJoueur().w() * e.getDroiteJoueur().z()) ) * (pointGrille.x() - pointGrille.z()) ) - ( (e.getAncienneDroiteJoueur().z() - e.getDroiteJoueur().z()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur2;
					float indiceD = ( ( ( (e.getAncienneDroiteJoueur().z() * e.getDroiteJoueur().w()) - (e.getAncienneDroiteJoueur().w() * e.getDroiteJoueur().z()) ) * (pointGrille.y() - pointGrille.w()) ) - ( (e.getAncienneDroiteJoueur().w() - e.getDroiteJoueur().w()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur2;
					boolean contactD = false;*/
					
					float denominateur2 = ( ( (e.getDroiteJoueur().x() - e.getDroiteJoueur().z()) * (pointGrille.y() - pointGrille.w()) ) - ( (e.getDroiteJoueur().y() - e.getDroiteJoueur().w()) * (pointGrille.x() - pointGrille.z()) ) );
					float indiceC = ( ( ( (e.getDroiteJoueur().x() * e.getDroiteJoueur().w()) - (e.getDroiteJoueur().y() * e.getDroiteJoueur().z()) ) * (pointGrille.x() - pointGrille.z()) ) - ( (e.getDroiteJoueur().x() - e.getDroiteJoueur().z()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur2;
					float indiceD = ( ( ( (e.getDroiteJoueur().x() * e.getDroiteJoueur().w()) - (e.getDroiteJoueur().y() * e.getDroiteJoueur().z()) ) * (pointGrille.y() - pointGrille.w()) ) - ( (e.getDroiteJoueur().y() - e.getDroiteJoueur().w()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur2;
					boolean contactD = false;
					
					float denominateur3 = ( ( (e.getDroiteJoueur().x() - e.getAncienneDroiteJoueur().x()) * (pointGrille.y() - pointGrille.w()) ) - ( (e.getDroiteJoueur().y() - e.getAncienneDroiteJoueur().y()) * (pointGrille.x() - pointGrille.z()) ) );
					float indiceE = ( ( ( (e.getDroiteJoueur().x() * e.getAncienneDroiteJoueur().y()) - (e.getDroiteJoueur().y() * e.getAncienneDroiteJoueur().x()) ) * (pointGrille.x() - pointGrille.z()) ) - ( (e.getDroiteJoueur().x() - e.getAncienneDroiteJoueur().x()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur3;
					float indiceF = ( ( ( (e.getDroiteJoueur().x() * e.getAncienneDroiteJoueur().y()) - (e.getDroiteJoueur().y() * e.getAncienneDroiteJoueur().x()) ) * (pointGrille.y() - pointGrille.w()) ) - ( (e.getDroiteJoueur().y() - e.getAncienneDroiteJoueur().y()) * ( (pointGrille.x() * pointGrille.w()) - (pointGrille.y() * pointGrille.z()) ) ) ) / denominateur3;
					boolean contactG = false;			
					
					/*Vector2 pres = new Vector2();
					pres = pointPlusProche(e.getPosition(), e.getDroiteJoueur(), pointGrille);
					
					Vector2 vec = new Vector2();
					vec.x(Math.abs(pres.x() - e.getPosition().x()));
					vec.y(Math.abs(pres.y() - e.getPosition().y()));
					
					if(vec.length() >= (e.getLigne().getEpaisseur()/2) && ( pointGrille.x() != e.getDroiteJoueur().x() || pointGrille.z() != e.getDroiteJoueur().z()) )
					{
						System.out.println("COLLISION");
					}*/
					
					// Si la position du joueur est la même que la position du point tracé
					//if( ( (contactG = ligneIntersection(indiceE, indiceF, new Vector4(e.getDroiteJoueur().x(), e.getDroiteJoueur().y(), e.getAncienneDroiteJoueur().x(), e.getAncienneDroiteJoueur().y()), pointGrille)) || (contactD = ligneIntersection(indiceC, indiceD, new Vector4(e.getDroiteJoueur().z(), e.getDroiteJoueur().w(), e.getAncienneDroiteJoueur().z(), e.getAncienneDroiteJoueur().w()), pointGrille)) || ligneIntersection(indiceA, indiceB, droite, pointGrille) ) && e.getPosition().z() == 1 && ( (pointGrille.x() != e.getDroiteJoueur().x()) || (pointGrille.z() != e.getDroiteJoueur().z())))
					//if(circleIntersection(e.getLigne().getEpaisseur(), e.getPosition(), pointGrille) && e.getPosition().z() == 1 && ( (pointGrille.x() != e.getDroiteJoueur().x()) || (pointGrille.x() != e.getDroiteJoueur().z()) ) )
					if( ( /*(contactD = ligneIntersection(indiceC, indiceD, e.getDroiteJoueur(), pointGrille)) || */ligneIntersection(indiceA, indiceB, droite, pointGrille) ) && e.getPosition().z() == 1)
					{
						System.out.println("Ancienne pos : ("+droite.x()+","+droite.y()+") ; Nouvelle pos : ("+droite.z()+","+droite.w()+")");
						System.out.println("INDICE A = "+indiceA);
						System.out.println("INDICE B = "+indiceB);
						System.out.println("INDICE C = "+indiceC);
						System.out.println("INDICE D = "+indiceD);
						System.out.println("Droite TRACE : ("+pointGrille.x()+","+pointGrille.y()+") et ("+pointGrille.z()+","+pointGrille.w()+")");
	
						// Définition du point de collision
						if(contactG)
						{
							System.out.println("MORT PAR façade G");
							e.setPosition(new Vector3(indiceE, indiceF, 1));
						}
						else if(contactD)
						{
							System.out.println("MORT PAR façade D");
							e.setPosition(new Vector3(indiceC, indiceD, 1));
						}
						else { e.setPosition(new Vector3(indiceA, indiceB, 1)); }
						
						// Alors on modifie l'état du joueur en "mort"
						e.setEtat(Etat.MORT);	// Alors on modifie son état en MORT
						
						// On met à jour le score des autres joueurs
						this.modifierScore();
						
						System.out.println("POS actuelle : ("+e.getPosition().x()+","+e.getPosition().y()+")");
					}
				}
			}
		}
	}

	// Méthode incrémentant le score des joueurs en vie
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
	
	// Méthode ajoutant un objet joueur dans la partie
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

	}
	
	public void repererBonus()
	{
		/* 
		 * Méthode qui devra repérer si un joueur rentre en contact avec bonus
		 * affectera le bonus au joueur
		 */
	}
	
	// Méthode remplissant les tableaux des sous grilles avec les traces des joueurs
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
	public void arreterPartie()
	{
		// Mise à l'état de pause du jeu
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
		
		// Changement de l'état de la partie
		this.jeu = false;
	}
	
	// Méhode comptant le nombre de joueurs encore en vie dans la partie
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
	
	public void update()
	{
		// On repère si des joueurs sont en collision avec une trace ou un mur
		this.repererCollisions();	// Si collisions il y a, alors la méthode repererCollisions se charge de mettre à jour les scores et l'état des joueurs
		
		// On repère si des joueurs prennent un bonus
		//this.repererBonus();	// Si bonus pris il y a, alors la méthode repererBonus se charge de modifier les paramètres des lignes concernées et de vider le tableau des bonus présents
		
		// Voir si on fait apparaitre des bonus ou non
		//this.apparaitreBonus();
		
		// Voir si il ne reste plus qu'un joueur en vie, donc fin du round
		if(nbJoueursVivant() <= 1)
		{
			/* 
			 * Si il ne reste plus qu'un joueur en vie
			 * 	Vérifier si le score max a été atteint
			 * 		Si oui : vérifier que l'écart avec le 2ème et de plus de 2 points
			 * 		Si non : repartir sur un nouveau round
			 */
			
			// Parmis tous les joueurs
			for(Joueur e : joueurs)
			{
				// Si le score du joueur est sup ou égal au scoreMax
				if(e.getScore() >= scoreMax)
				{
					// On vérifie si il a plus de 2 points par rapport aux autres joueurs
					if(verifGagnant(e))
					{
						//arreterPartie();
						// FIN DE LA PARTIE
					}
				}
				// Si non, alors on initialise un nouveau round
				else { this.initialiserRound(); }
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
