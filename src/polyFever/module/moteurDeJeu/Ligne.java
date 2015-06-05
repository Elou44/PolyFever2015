package polyFever.module.moteurDeJeu;

/* 
 * Classe stockant les informations li�s � une ligne contr�l�e par un joueur
 * Et g�rant les param�tres de la ligne
 */

import polyFever.module.util.math.Vector4;
import polyFever.module.main.*;

/**
 * Ceci est la classe Ligne
 * Classe stockant les informations li�s � une ligne contr�l�e par un joueur
 * Et g�rant les param�tres de la ligne
 * 
 * @param couleur
 * 			Entier indiquant la couleur de la ligne
 * @param joueur
 * 			Objet Joueur controlant la ligne
 * @param vitesse
 * 			Float indiquant la vitesse de la ligne
 * @param vitesseInit
 * 			Float indiquant la vitesse initiale de la ligne. Utile au module Affichage
 * @param epaisseur
 * 			Float donnant l'�paisseur du trait
 * @param courbe
 * 			Double donnant le rayon de courbure de la ligne (en radians)
 * @param tpsEnVie
 * 			Long donnant le temps pass� en vie durant un round (en millisecondes)
 * @param tpsTrou
 * 			Long donnant le temps d�finissant le moment ou la ligne trace un trou (en millisecondes)
 * @param polyFever
 * 			Objet PolyFever permettant l'association � l'objet PolyFever. Utile au module Affichage, notamment pour que le tra�age de la ligne ne se fasse pas en fontion des FPS du joueur
 * @param longueurTrou
 * 			Long constant, donant le temps en millisecondes correspondant au tra�age d'un trou
 * 
 * @author Fr�d�ric Llorca
 *
 */
public class Ligne {

	// COULEUR AFFECTEE LORS DE L'INSTANCIATION D'UN JOUEUR
	private int color;
	private Joueur joueur;				// Joueur controlant la ligne
	private float vitesse;  			// Vitesse de la ligne en float
	private float vitesseInit;
	private float epaisseur;			// Epaisseur du trait
	private double courbe;				// Rayon de courbure de la ligne (en radians)
	private long tpsEnVie;				// Temps pass� en vie durant un round (en secondes)
	private long tpsTrou;				// Temps d�finissant le moment ou la ligne trace un trou (en millisecondes)
	private PolyFever polyfever;		// Connexion � l'objet PolyFever
	private final long longueurTrou;	// Temps en millisecondes correspondant au tra�age d'un trou
	
	// Constructeur
	/**
	 * Constructeur d'un objet Ligne
	 * Prend en param�tre un objet PolyFever permettant son association au module Affichage
	 * Ce constructeur initialise les variables n�cessaires � l'utilisation d'une ligne
	 * 
	 * @param polyFever
	 */
	public Ligne(PolyFever p, int col)	// Par d�faut
	{
		//System.out.println("Instanciation d'un objet Ligne (sp)...");
		this.color = col;
		this.joueur = null;
		this.vitesse = 0.009f;
		this.vitesseInit = 0.009f;
		this.polyfever = p;
		this.epaisseur = 0.017f;
		this.courbe = Math.PI /35;//35;
		this.tpsEnVie = 0;
		this.longueurTrou = (long) (2.3/vitesse);//230;
	}
	
	// M�thodes

	/*
	 * Assesseurs et mutateurs
	 */

	public PolyFever getPolyfever() {
		return polyfever;
	}

	public void setVitesse(float d) {
		this.vitesse = d;
	}
	
	public int getCouleur() {
		return color;
	}

	public void setCouleur(int couleur) {
		this.color = couleur;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public float getVitesse() {
		return vitesse;
	}

	public float getEpaisseur() {
		return epaisseur;
	}

	public void setEpaisseur(float epaisseur) {
		this.epaisseur = epaisseur;
	}

	public double getCourbe() {
		return courbe;
	}

	public void setCourbe(double courbe) {
		this.courbe = courbe;
	}

	public long getTpsEnVie() {
		return tpsEnVie;
	}

	public void setTpsEnVie(long tpsEnVie) {
		this.tpsEnVie = tpsEnVie;
	}
	
	public long getTpsTrou () {
		return this.tpsTrou;
	}
	
	public void setTpsTrou (long temps) {
		this.tpsTrou = temps;
	}
	
	/*
	 * Autres m�thodes de gestion des lignes
	 */
	
	/**
	 * M�thode 
	 * 
	 * @return Ne retourne rien
	 */
	public void majVitessesCourbe() {
		/*
		 * Mise � jour des Vitesses (vitesse et vitesse2) -> r�alis� � chaque frame (sachant que les fps sont maj toutes les secondes, peu utile)
		 */
		
		
		float realFPS = (float) polyfever.getRealFPS();
		if(realFPS == 0.0f) realFPS = (float) polyfever.getFPS();
		
		this.vitesse = this.vitesseInit*(30.0f/realFPS); 
		this.courbe = Math.PI/35*(30.0f/realFPS);
		
		//System.out.println("vitesse: ".concat(String.valueOf(vitesse)));
		//System.out.println("vitesse2: ".concat(String.valueOf(vitesse2)));

	}
	
	/**
	 * M�thode calculant la nouvelle position d'un joueur si ce dernier veut tourner � droite
	 * 
	 * Principe :
	 * 		On calcule tout d'abord l'angle de rotation, l'angle que fait la nouvelle direction avec l'axe des abscisses
	 * 		Gr�ce � cet angle et le th�or�me de Pythagore, on calcule la translation en x et en y n�cessaire pour atteindre la nouvelle position
	 * 		On ajoute ces valeurs � la position courante du Joueur et ainsi d�finir une nouvelle position
	 * 		Selon le temps de la partie, on remplira le z de la nouvelle position � 1 si la ligne doit laisser une trace ou bien � 0 si la ligne ne doit pas en laisser
	 * 			Pour cela on v�rifie juste si le modulo du temps de la partie et de l'attribut tpsTrou du Joueur est comprise entre 0 et l'attribut longueurTrou
	 * 		On met � jour la grille courante du Joueur
	 * 		On met � jour la droiteJoueur
	 * 		On met � jour la direction du Joueur
	 */
	public void tournerDroite()	// M�thode calculant la prochaine position, si le joueur veut tourner � droite
	{
		/* Calculer les nouvelles coordonn�es
		 * Ajouter les nouvelles coordonn�es au tableau des coord
		 * Envoyer ces coordonn�es � l'affichage
		 */
		
		// Si le jeu n'est pas en pause alors on calcule la nouvelle position, sinon on ne change pas la derni�re position
		if(this.getJoueur().getPartie().isRoundEnPause() == false)
		{
		
			majVitessesCourbe(); // mise � jour des vitesses en fonction du frameRate
			joueur.setAnciennePosition(joueur.getPosition().copy());
			joueur.setAncienneDroiteJoueur(joueur.getDroiteJoueur().copy());
			
			////System.out.println("TOURNE DROITE / direction : "+joueur.getDirection()+" direction-courbe : "+(joueur.getDirection()-courbe));
			double angleRotation = 0;
			double nouvPositionX = 0;
			double nouvPositionY = 0;
			
			// Calcul de l'angle de rotation
			if( ((joueur.getDirection() - courbe) >= 0) && ((joueur.getDirection() - courbe) <= (Math.PI/2)) )
			{
				////System.out.println("Cas 1");
				angleRotation = Math.abs(joueur.getDirection() - courbe);
			}
			else if( ((joueur.getDirection() - courbe) > (Math.PI/2)) && ((joueur.getDirection() - courbe) <= Math.PI) )
			{
				////System.out.println("Cas 2");
				angleRotation = Math.PI - (joueur.getDirection() - courbe);
			}
			else if( ((joueur.getDirection() - courbe) > Math.PI) && ((joueur.getDirection() - courbe) <= (3*Math.PI/2)) )
			{
				////System.out.println("Cas 3");
				angleRotation = (joueur.getDirection() - courbe) - Math.PI;
			}
			else if( ((joueur.getDirection() - courbe) > (3*Math.PI/2)))
			{
				////System.out.println("Cas 4");
				angleRotation = 2*Math.PI - (joueur.getDirection() - courbe);
			}
			else if((joueur.getDirection() - courbe) < 0)
			{
				//System.out.println("Cas 5");
				angleRotation = courbe - joueur.getDirection();
			}
			//System.out.println("Angle de Rotation =  "+angleRotation);
			
			
			// Calcul de la nouvelle position en x
			if( (((joueur.getDirection() - courbe) >= 0) && ((joueur.getDirection() - courbe) <= (Math.PI/2))) || ((joueur.getDirection() - courbe) >= (3*Math.PI/2)) || ((joueur.getDirection() - courbe) < 0) )
			{
				//System.out.println("Calcul 1");
				nouvPositionX = joueur.getPosition().x() + (vitesse * Math.cos(angleRotation));
			}
			else if( ((joueur.getDirection() - courbe) > (Math.PI/2)) && ((joueur.getDirection() - courbe) < (3*Math.PI/2)) )
			{
				//System.out.println("Calcul 2");
				nouvPositionX = joueur.getPosition().x() - (vitesse * Math.cos(angleRotation));
				
			}
			//System.out.println("nouvPos x =  "+nouvPositionX);
			
			
			// Calcul de la nouvelle position en y
			if( (((joueur.getDirection() - courbe) >= Math.PI) && ((joueur.getDirection() - courbe) <= (2*Math.PI)))  || ((joueur.getDirection() - courbe) < 0)  )
			{
				//System.out.println("Calcul 1");
				nouvPositionY = joueur.getPosition().y() - (vitesse * Math.sin(angleRotation));
			}
			else if( ((joueur.getDirection() - courbe) >= 0) && ((joueur.getDirection() - courbe) < Math.PI) )
			{
				//System.out.println("Calcul 2");
				nouvPositionY = joueur.getPosition().y() + (vitesse * Math.sin(angleRotation));
				
			}
			//System.out.println("nouvPos y =  "+nouvPositionY);
				
			// Affectation de la nouvelle position du joueur et savoir si trace un trou ou non
			//System.out.println("TROU ? "+(System.currentTimeMillis() - joueur.getPartie().getTemps()));
			if( (((System.currentTimeMillis() - joueur.getPartie().getTemps()) % tpsTrou) >= 0) && (((System.currentTimeMillis() - joueur.getPartie().getTemps()) % tpsTrou) <= longueurTrou))
			{
				//System.out.println("TROU !");
				joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 0);
			}
			else { joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 1); }
			
			// Mise � jour de la sous grille courante
			joueur.majGrille();
			
			// Mise � jour de la droite du joueur, permettant la d�tection de collision
			joueur.setDroiteJoueur(new Vector4( (float) (joueur.getPosition().x() + (epaisseur/2)*Math.cos((Math.PI/2) - joueur.getAngleRectangle())),
					(float) (joueur.getPosition().y() - (epaisseur/2)*Math.sin((Math.PI/2) - joueur.getAngleRectangle())),
					(float) (joueur.getPosition().x() - (epaisseur/2)*Math.cos((Math.PI/2) - joueur.getAngleRectangle())),
					(float) (joueur.getPosition().y() + (epaisseur/2)*Math.sin((Math.PI/2) - joueur.getAngleRectangle())) ));
							
			// Mise � jour de la direction
			if((joueur.getDirection() - courbe) < 0)
			{
				joueur.setDirection((2*Math.PI) - angleRotation);
			}
			else
			{
				joueur.setDirection(joueur.getDirection() - courbe);
			}
			//System.out.println("MAJ direction : "+joueur.getDirection());
		}
				
	}
	
	/**
	 * M�thode calculant la nouvelle position d'un joueur si ce dernier veut tourner � gauche
	 * 
	 * Principe :
	 * 		On calcule tout d'abord l'angle de rotation, l'angle que fait la nouvelle direction avec l'axe des abscisses
	 * 		Gr�ce � cet angle et le th�or�me de Pythagore, on calcule la translation en x et en y n�cessaire pour atteindre la nouvelle position
	 * 		On ajoute ces valeurs � la position courante du Joueur et ainsi d�finir une nouvelle position
	 * 		Selon le temps de la partie, on remplira le z de la nouvelle position � 1 si la ligne doit laisser une trace ou bien � 0 si la ligne ne doit pas en laisser
	 * 			Pour cela on v�rifie juste si le modulo du temps de la partie et de l'attribut tpsTrou du Joueur est comprise entre 0 et l'attribut longueurTrou
	 * 		On met � jour la grille courante du Joueur
	 * 		On met � jour la droiteJoueur
	 * 		On met � jour la direction du Joueur
	 */
	public void tournerGauche()	// M�thode calculant la prochaine position, si le joueur veut tourner � droite
	{
		/* Calculer les nouvelles coordonn�es
		 * Ajouter les nouvelles coordonn�es au tableau des coord
		 * Envoyer ces coordonn�es � l'affichage
		 */
		
		// Si le jeu n'est pas en pause alors on calcule la nouvelle position, sinon on ne change pas la derni�re position
		if(this.getJoueur().getPartie().isRoundEnPause() == false)
		{
		
			majVitessesCourbe(); // mise � jour des vitesses en fonction du frameRate
			joueur.setAnciennePosition(joueur.getPosition().copy());
			joueur.setAncienneDroiteJoueur(joueur.getDroiteJoueur().copy());
			
			double angleRotation = 0;
			double nouvPositionX = 0;
			double nouvPositionY = 0;
			
			//System.out.println("TOURNE GAUCHE / direction : "+joueur.getDirection());
			
			// Calcul de l'angle de rotation
			if(joueur.getDirection() == 0.0)
			{
				angleRotation = courbe;
				//System.out.println("Cas 1");			
			}
			else if( ((joueur.getDirection() + courbe) >= 0) && ((joueur.getDirection() + courbe) <= (Math.PI/2)) )
			{
				angleRotation = joueur.getDirection() + courbe;
				//System.out.println("Cas 2");
			}
			else if( ((joueur.getDirection() + courbe) > (Math.PI/2)) && ((joueur.getDirection() + courbe) <= Math.PI) )
			{
				angleRotation = Math.PI - (joueur.getDirection() + courbe);
				//System.out.println("Cas 3");
			}
			else if( ((joueur.getDirection() + courbe) > Math.PI) && ((joueur.getDirection() + courbe) <= (3*Math.PI/2)) )
			{
				angleRotation = Math.PI - (2*Math.PI - (joueur.getDirection() + courbe));
				//System.out.println("Cas 4");
			}
			else if( ((joueur.getDirection() + courbe) > (3*Math.PI/2)) && ((joueur.getDirection() + courbe) <= (2*Math.PI)) )
			{
				angleRotation = 2*Math.PI - (joueur.getDirection() + courbe);
				//System.out.println("Cas 5");
			}
			else if((joueur.getDirection() + courbe) > (2*Math.PI))
			{
				angleRotation = (joueur.getDirection() + courbe) - (2*Math.PI);
				//System.out.println("Cas 6");
			}
			
			//System.out.println("Angle de Rotation =  "+angleRotation);
			
			// Calcul de la nouvelle position en x
			if( (((joueur.getDirection() + courbe) >= (Math.PI/2)) && ((joueur.getDirection() + courbe) <= (3*Math.PI/2))))
			{
				//System.out.println("Calcul 1");
				nouvPositionX = joueur.getPosition().x() - (vitesse * Math.cos(angleRotation));
			}
			else if( (((joueur.getDirection() + courbe) >= 0) && ((joueur.getDirection() + courbe) < (Math.PI/2))) || (((joueur.getDirection() + courbe) > (3*Math.PI/2)) && ((joueur.getDirection() + courbe) <= (2*Math.PI)))  || ((joueur.getDirection() + courbe) > (2*Math.PI)))
			{
				//System.out.println("Calcul 2");
				nouvPositionX = joueur.getPosition().x() + (vitesse * Math.cos(angleRotation));
			}
			//System.out.println("nouvPos x =  "+nouvPositionX);
			
			// Calcul de la nouvelle position en y
			if( ((joueur.getDirection() + courbe) >= Math.PI) && ((joueur.getDirection() + courbe) <= (2*Math.PI)) )
			{
				//System.out.println("Calcul 1");
				nouvPositionY = joueur.getPosition().y() - (vitesse * Math.sin(angleRotation));
			}
			else if( (((joueur.getDirection() + courbe) >= 0) && ((joueur.getDirection() + courbe) < Math.PI))   || ((joueur.getDirection() + courbe) > (2*Math.PI)))
			{
				//System.out.println("Calcul 2");
				nouvPositionY = joueur.getPosition().y() + (vitesse * Math.sin(angleRotation));
			}
			//System.out.println("nouvPos y =  "+nouvPositionY);
			
			// Affectation de la nouvelle position du joueur et savoir si trace un trou ou non
			//System.out.println("TROU ? "+(System.currentTimeMillis() - joueur.getPartie().getTemps()));
			if( (((System.currentTimeMillis() - joueur.getPartie().getTemps()) % tpsTrou) >= 0) && (((System.currentTimeMillis() - joueur.getPartie().getTemps()) % tpsTrou) <= longueurTrou))
			{
				//System.out.println("TROU !");
				joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 0);
			}
			else { joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 1); }
		
			// Mise � jour de la sous grille courante
			joueur.majGrille();
			
			// Mise � jour de la droite du joueur, permettant la d�tection de collision
			joueur.setDroiteJoueur(new Vector4( (float) (joueur.getPosition().x() + (epaisseur/2)*Math.cos((Math.PI/2) - joueur.getAngleRectangle())),
					(float) (joueur.getPosition().y() - (epaisseur/2)*Math.sin((Math.PI/2) - joueur.getAngleRectangle())),
					(float) (joueur.getPosition().x() - (epaisseur/2)*Math.cos((Math.PI/2) - joueur.getAngleRectangle())),
					(float) (joueur.getPosition().y() + (epaisseur/2)*Math.sin((Math.PI/2) - joueur.getAngleRectangle())) ));
						
			// Mise � jour de la direction
			if((joueur.getDirection() + courbe) > (2*Math.PI))
			{
				joueur.setDirection(angleRotation);
			}
			else
			{
				joueur.setDirection(joueur.getDirection() + courbe);
			}
			//System.out.println("MAJ direction : "+joueur.getDirection());
		}
		
	}
	
	/**
	 * M�thode calculant la nouvelle position d'un joueur si ce dernier ne veut pas tourner
	 * 
	 * Principe :
	 * 		On calcule tout d'abord l'angle de rotation, l'angle que fait la nouvelle direction avec l'axe des abscisses
	 * 		Gr�ce � cet angle et le th�or�me de Pythagore, on calcule la translation en x et en y n�cessaire pour atteindre la nouvelle position
	 * 		On ajoute ces valeurs � la position courante du Joueur et ainsi d�finir une nouvelle position
	 * 		Selon le temps de la partie, on remplira le z de la nouvelle position � 1 si la ligne doit laisser une trace ou bien � 0 si la ligne ne doit pas en laisser
	 * 			Pour cela on v�rifie juste si le modulo du temps de la partie et de l'attribut tpsTrou du Joueur est comprise entre 0 et l'attribut longueurTrou
	 * 		On met � jour la grille courante du Joueur
	 * 		On met � jour la droiteJoueur
	 * 		On ne met pas � jour la direction du Joueur puisqu'elle ne change pas
	 */
	public void pasTourner()
	{
		/* Calculer les nouvelles coordonn�es
		 * Ajouter les nouvelles coordonn�es au tableau des coord
		 * Envoyer ces coordonn�es � l'affichage
		 */
		
		// Si le jeu n'est pas en pause alors on calcule la nouvelle position, sinon on ne change pas la derni�re position
		if(this.getJoueur().getPartie().isRoundEnPause() == false)
		{
		
			majVitessesCourbe(); // mise � jour des vitesses en fonction du frameRate
			joueur.setAnciennePosition(joueur.getPosition().copy());
			joueur.setAncienneDroiteJoueur(joueur.getDroiteJoueur().copy());
			
			double angleRotation = 0;
			double nouvPositionX = 0;
			double nouvPositionY = 0;
			
			//System.out.println("PAS TOURNER / direction : "+joueur.getDirection());
			
			// Calcul de l'angle de rotation
			if( (joueur.getDirection() >= 0) && (joueur.getDirection() <= (Math.PI/2)) )
			{
				//System.out.println("Cas 1");
				angleRotation = joueur.getDirection();
			}
			else if( (joueur.getDirection() > (Math.PI/2)) && (joueur.getDirection() <= Math.PI) )
			{
				//System.out.println("Cas 2");
				angleRotation = Math.PI - joueur.getDirection();
			}
			else if( (joueur.getDirection() > Math.PI) && (joueur.getDirection() <= (3*Math.PI/2)) )
			{
				//System.out.println("Cas 3");
				angleRotation = Math.abs(- Math.PI - joueur.getDirection());
			}
			else if( (joueur.getDirection() > (3*Math.PI/2)) && (joueur.getDirection() <= (2*Math.PI)) )
			{
				//System.out.println("Cas 4");
				angleRotation = (2*Math.PI) - joueur.getDirection();
			}
			//System.out.println("Angle de Rotation =  "+angleRotation);
			
			
			
			// Calcul de la nouvelle position en x
			if( ((joueur.getDirection()) > (Math.PI/2)) && ((joueur.getDirection()) <= (3*Math.PI/2)) )
			{
				//System.out.println("Calcul 1");
				nouvPositionX = joueur.getPosition().x() - (vitesse * Math.cos(angleRotation));
			}
			else if( (((joueur.getDirection()) >= 0) && ((joueur.getDirection()) <= (Math.PI/2))) || (((joueur.getDirection()) > (3*Math.PI/2)) && ((joueur.getDirection()) <= (2*Math.PI))) )
			{
				//System.out.println("Calcul 2");
				nouvPositionX = joueur.getPosition().x() + (vitesse * Math.cos(angleRotation));
			}
			//System.out.println("nouvPos x =  "+nouvPositionX);
			
			// Calcul de la nouvelle position en y
			if( ((joueur.getDirection()) >= Math.PI) && ((joueur.getDirection()) <= (2*Math.PI)) )
			{
				//System.out.println("Calcul 1");
				nouvPositionY = joueur.getPosition().y() - (vitesse * Math.sin(angleRotation));
			}
			else if( ((joueur.getDirection()) >= 0) && ((joueur.getDirection()) < Math.PI) )
			{
				//System.out.println("Calcul 2");
				nouvPositionY = joueur.getPosition().y() + (vitesse * Math.sin(angleRotation));
			}
			//System.out.println("nouvPos y =  "+nouvPositionY);
					
			// Affectation de la nouvelle position du joueur et savoir si trace un trou ou non
			//System.out.println("TROU ? "+(System.currentTimeMillis() - joueur.getPartie().getTemps()));
			if( (((System.currentTimeMillis() - joueur.getPartie().getTemps()) % tpsTrou) >= 0) && (((System.currentTimeMillis() - joueur.getPartie().getTemps()) % tpsTrou) <= longueurTrou))
			{
				//System.out.println("TROU !");
				joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 0);
			}
			else { joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 1); }
			
			// Mis � jour de la sous grille courante
			joueur.majGrille();
			
			// Pas de mise � jour de la direction, vu qu'elle ne change pas
			
			// Mise � jour de la droite du joueur, permettant la d�tection de collision
			joueur.setDroiteJoueur(new Vector4( (float) (joueur.getPosition().x() + (epaisseur/2)*Math.cos((Math.PI/2) - joueur.getAngleRectangle())),
					(float) (joueur.getPosition().y() - (epaisseur/2)*Math.sin((Math.PI/2) - joueur.getAngleRectangle())),
					(float) (joueur.getPosition().x() - (epaisseur/2)*Math.cos((Math.PI/2) - joueur.getAngleRectangle())),
					(float) (joueur.getPosition().y() + (epaisseur/2)*Math.sin((Math.PI/2) - joueur.getAngleRectangle())) ));
		}
		
	}

	/**
	 * M�thode renvoyant une chaine de caract�res d�crivant un objet Ligne
	 */
	@Override
	public String toString() {
		return "Ligne [couleur=" + color +", vitesse=" + vitesse + ", epaisseur=" + epaisseur
				+ ", courbe=" + courbe + ", tpsEnVie=" + tpsEnVie +"]";
	}
	
}
