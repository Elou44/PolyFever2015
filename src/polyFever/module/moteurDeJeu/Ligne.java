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
	private float vitesse;  			// Vitesse de la ligne en float
	private float vitesseInit;
	private float epaisseur;			// Epaisseur du trait
	private double courbe;				// Rayon de courbure de la ligne (en radians)
	private int tpsEnVie;				// Temps passé en vie durant un round (en secondes)
	private long tpsTrou;				// Temps définissant le moment ou la ligne trace un trou (en millisecondes)
	private PolyFever polyfever;		// Connexion à l'objet PolyFever
	private final long longueurTrou;	// Temps en millisecondes correspondant au traçage d'un trou
	
	// Constructeur
	public Ligne(PolyFever p)	// Par défaut
	{
		//System.out.println("Instanciation d'un objet Ligne (sp)...");
		this.couleur = 0;
		this.joueur = null;
		this.vitesse = 0.009f;
		this.vitesseInit = 0.009f;
		this.polyfever = p;
		this.epaisseur = 0.015f;
		this.courbe = Math.PI /35;//35;
		this.tpsEnVie = 0;
		this.longueurTrou = (long) (2.3/vitesse);//230;
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

	public PolyFever getPolyfever() {
		return polyfever;
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
	
	public long getTpsTrou () {
		return this.tpsTrou;
	}
	
	public void setTpsTrou (long temps) {
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
		
		this.vitesse = this.vitesseInit*(30.0f/realFPS); 
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
			
		// Affectation de la nouvelle position du joueur et savoir si trace un trou ou non
		//System.out.println("TROU ? "+(System.currentTimeMillis() - joueur.getPartie().getTemps()));
		if( (((System.currentTimeMillis() - joueur.getPartie().getTemps()) % tpsTrou) >= 0) && (((System.currentTimeMillis() - joueur.getPartie().getTemps()) % tpsTrou) <= longueurTrou))
		{
			//System.out.println("TROU !");
			joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 0);
		}
		else { joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 1); }
		
		// Mise à jour de la sous grille courante
		joueur.majGrille(joueur.getPosition());
		
		// Mise à jour de la droite du joueur, permettant la détection de collision
		joueur.setDroiteJoueur(new Vector4( (float) (joueur.getPosition().x() + (epaisseur/2)*Math.cos((Math.PI/2) - joueur.getAngleRectangle())),
				(float) (joueur.getPosition().y() - (epaisseur/2)*Math.sin((Math.PI/2) - joueur.getAngleRectangle())),
				(float) (joueur.getPosition().x() - (epaisseur/2)*Math.cos((Math.PI/2) - joueur.getAngleRectangle())),
				(float) (joueur.getPosition().y() + (epaisseur/2)*Math.sin((Math.PI/2) - joueur.getAngleRectangle())) ));	
			
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
		
		// Affectation de la nouvelle position du joueur et savoir si trace un trou ou non
		//System.out.println("TROU ? "+(System.currentTimeMillis() - joueur.getPartie().getTemps()));
		if( (((System.currentTimeMillis() - joueur.getPartie().getTemps()) % tpsTrou) >= 0) && (((System.currentTimeMillis() - joueur.getPartie().getTemps()) % tpsTrou) <= longueurTrou))
		{
			//System.out.println("TROU !");
			joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 0);
		}
		else { joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 1); }
	
		// Mise à jour de la sous grille courante
		joueur.majGrille(joueur.getPosition());
			
		// Mise à jour de la droite du joueur, permettant la détection de collision
		joueur.setDroiteJoueur(new Vector4( (float) (joueur.getPosition().x() + (epaisseur/2)*Math.cos((Math.PI/2) - joueur.getAngleRectangle())),
				(float) (joueur.getPosition().y() - (epaisseur/2)*Math.sin((Math.PI/2) - joueur.getAngleRectangle())),
				(float) (joueur.getPosition().x() - (epaisseur/2)*Math.cos((Math.PI/2) - joueur.getAngleRectangle())),
				(float) (joueur.getPosition().y() + (epaisseur/2)*Math.sin((Math.PI/2) - joueur.getAngleRectangle())) ));
			
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
				
		// Affectation de la nouvelle position du joueur et savoir si trace un trou ou non
		//System.out.println("TROU ? "+(System.currentTimeMillis() - joueur.getPartie().getTemps()));
		if( (((System.currentTimeMillis() - joueur.getPartie().getTemps()) % tpsTrou) >= 0) && (((System.currentTimeMillis() - joueur.getPartie().getTemps()) % tpsTrou) <= longueurTrou))
		{
			//System.out.println("TROU !");
			joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 0);
		}
		else { joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 1); }
		
		// Mis à jour de la sous grille courante
		joueur.majGrille(joueur.getPosition());
		
		// Pas de mise à jour de la direction, vu qu'elle ne change pas
		
		// Mise à jour de la droite du joueur, permettant la détection de collision
		joueur.setDroiteJoueur(new Vector4( (float) (joueur.getPosition().x() + (epaisseur/2)*Math.cos((Math.PI/2) - joueur.getAngleRectangle())),
				(float) (joueur.getPosition().y() - (epaisseur/2)*Math.sin((Math.PI/2) - joueur.getAngleRectangle())),
				(float) (joueur.getPosition().x() - (epaisseur/2)*Math.cos((Math.PI/2) - joueur.getAngleRectangle())),
				(float) (joueur.getPosition().y() + (epaisseur/2)*Math.sin((Math.PI/2) - joueur.getAngleRectangle())) ));
		
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
