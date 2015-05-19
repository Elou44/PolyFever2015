package polyFever.module.moteurDeJeu;

/* 
 * Classe stockant les informations liés à une ligne contrôlée par un joueur
 * Et gérant les paramètres de la ligne
 */

import java.util.Iterator;

import polyFever.module.util.math.Vector2;
import polyFever.module.util.math.Vector4;
import polyFever.module.main.*;

public class Ligne {

	// COULEUR AFFECTEE LORS DE L'INSTANCIATION D'UN JOUEUR
	private int couleur;				// Couleur de la ligne
	private Joueur joueur;				// Joueur controlant la ligne
	private float vitesse2;				// Vitesse de la ligne (en pixels)
	private float vitesse;  			// Vitesse de la ligne en float
	private float epaisseur;			// Epaisseur du trait
	private double courbe;				// Rayon de courbure de la ligne (en radians)
	private int tpsEnVie;				// Temps passé en vie durant un round (en secondes)
	private float tpsTrou;				// Temps définissant le moment ou la ligne trace un trou (en secondes, entre 1.5 et 3.0 secondes)
	private PolyFever polyfever;
	
	// Constructeur
	public Ligne(PolyFever p)	// Par défaut
	{
		//System.out.println("Instanciation d'un objet Ligne (sp)...");
		this.couleur = 0;
		this.joueur = null;
		this.vitesse2 = 0.009f;
		this.vitesse = 0.009f;
		this.polyfever = p;
		this.epaisseur = 0.01f;
		this.courbe = Math.PI / 35;
		this.tpsEnVie = 0;
	}
	
	public Ligne(int couleur, PolyFever p)	// Avec paramètres
	{
		this(p);
		//System.out.println("Instanciation d'un objet Ligne (ap)...");
		this.couleur = couleur;
	}
	
	// Méthodes

	/*
	 * Assesseurs et mutateurs
	 */
	
	public float getTpsTrou() {
		return tpsTrou;
	}

	public PolyFever getPolyfever() {
		return polyfever;
	}

	public void setVitesse2(float vitesse2) {
		this.vitesse2 = vitesse2;
	}

	public void setVitesse(float vitesse) {
		this.vitesse = vitesse;
	}
	
	public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public float getVitesse2() {
		return vitesse2;
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

	public int getTpsEnVie() {
		return tpsEnVie;
	}

	public void setTpsEnVie(int tpsEnVie) {
		this.tpsEnVie = tpsEnVie;
	}
	
	public void setTpsTrou (float temps) {
		this.tpsTrou = temps;
	}
	
	/*
	 * Autres méthodes de gestion des lignes
	 */
	
	public void majVitessesCourbe() {
		/*
		 * Mise à jour des Vitesses (vitesse et vitesse2) -> réalisé à chaque frame (sachant que les fps sont maj toutes les secondes, peu utile)
		 */
		
		
		float realFPS = (float) polyfever.getRealFPS();
		if(realFPS == 0.0f) realFPS = (float) polyfever.getFPS();
		
		

		this.vitesse = 0.009f*(30.0f/realFPS); 
		this.vitesse2 = 0.009f*(30.0f/realFPS);
		this.courbe = Math.PI/35*(30.0f/realFPS);
		
		//System.out.println("vitesse: ".concat(String.valueOf(vitesse)));
		//System.out.println("vitesse2: ".concat(String.valueOf(vitesse2)));

	}
	
	public void tournerDroite()	// Méthode calculant la prochaine position, si le joueur veut tourner à droite
	{
		/* Calculer les nouvelles coordonnées
		 * Ajouter les nouvelles coordonnées au tableau des coord
		 * Envoyer ces coordonnées à l'affichage
		 */
		
		majVitessesCourbe(); // mise à jour des vitesses en fonction du frameRate
		joueur.setAnciennePosition(joueur.getPosition().copy());
		
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
			
		// Affectation de la nouvelle position du joueur
		joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 1);
		
		// Mise à jour de la sous grille courante
		joueur.majGrille(joueur.getPosition());
			
		// Mise à jour de la direction
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
	
	public void tournerGauche()	// Méthode calculant la prochaine position, si le joueur veut tourner à droite
	{
		/* Calculer les nouvelles coordonnées
		 * Ajouter les nouvelles coordonnées au tableau des coord
		 * Envoyer ces coordonnées à l'affichage
		 */
		
		majVitessesCourbe(); // mise à jour des vitesses en fonction du frameRate
		joueur.setAnciennePosition(joueur.getPosition().copy());
		
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
		
		// Affectation de la nouvelle position du joueur
		joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 1);
	
		// Mise à jour de la sous grille courante
		joueur.majGrille(joueur.getPosition());
			
		// Mise à jour de la direction
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
	
	public void pasTourner()
	{
		/* Calculer les nouvelles coordonnées
		 * Ajouter les nouvelles coordonnées au tableau des coord
		 * Envoyer ces coordonnées à l'affichage
		 */
		
		majVitessesCourbe(); // mise à jour des vitesses en fonction du frameRate
		joueur.setAnciennePosition(joueur.getPosition().copy());
		
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
				
		// Affectation de la nouvelle position du joueur
		joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 1);
		
		// Mis à jour de la sous grille courante
		joueur.majGrille(joueur.getPosition());
		
		// Pas de mise à jour de la direction, vu qu'elle ne change pas
		
		
	}
	
	public void tracerTrou()
	{
		/* 
		 * Méthode calculant le moment pour un joueur de tracer un trou
		 * Si trace trou ne pas écrire dans le tableau des coordonnées des lignes !
		 * Juste changer les coordonnées pos_x et pos_y du joueur
		 * 
		 * Principe :
		 * A l'initialisation du joueur on définit un temps constant pour le reste de la partie
		 * Ce temps définira tous les combiens un trou sera tracé
		 * Ce temps sera aléatoire (sinon si tous les joueurs ont le même temps, ils traceraient des trous au même moment
		 * Quand le temps vient, on change le dernier bit du Vector Position en 0 pour pas tracer
		 */
		
		
	}

	@Override
	public String toString() {
		return "Ligne [couleur=" + couleur +", vitesse=" + vitesse + ", epaisseur=" + epaisseur
				+ ", courbe=" + courbe + ", tpsEnVie=" + tpsEnVie +"]";
	}
	
}
