package polyFever.module.moteurDeJeu;

/* 
 * Classe stockant les informations li�s � une ligne contr�l�e par un joueur
 * Et g�rant les param�tres de la ligne
 */

import java.util.Iterator;

import polyFever.module.util.math.Vector3;
import polyFever.module.main.*;

public class Ligne {

	// COULEUR AFFECTEE LORS DE L'INSTANCIATION D'UN JOUEUR
	private int couleur;				// Couleur de la ligne
	private Joueur joueur;				// Joueur controlant la ligne
	private float vitesse2;				// Vitesse de la ligne (en pixels)
	private float vitesse;  			// Vitesse de la ligne en float
	private float epaisseur;			// Epaisseur du trait
	private double courbe;				// Rayon de courbure de la ligne (en radians)
	private int tpsEnVie;				// Temps pass� en vie durant un round (en secondes)
	private float tpsTrou;				// Temps d�finissant le moment ou la ligne trace un trou (en secondes, entre 1.5 et 3.0 secondes)
	private PolyFever polyfever;
	
	// Constructeur
	public Ligne(PolyFever p)	// Par d�faut
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
	
	public Ligne(int couleur, PolyFever p)	// Avec param�tres
	{
		this(p);
		//System.out.println("Instanciation d'un objet Ligne (ap)...");
		this.couleur = couleur;
	}
	
	// M�thodes

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
	 * Autres m�thodes de gestion des lignes
	 */
	
	// M�thode ajoutant les coordonn�es du curseur du joueur dans le tableau des traces de la sous grille correspondante � sa position
	public void ajouterCoord(Vector3 coord)
	{
		// Si le curseur se situe dans la 0�me sous grille
		if(coord.x() <= -0.5 && coord.y() >= 0.5)
		{
			// On ajoute les coordonn�es dans le sous tableau correspondant
			joueur.getPartie().getTrace().get(0).add(coord.copy());
			// Mise � jour de la sous grille actuelle ou se trouve le joueur
			joueur.setGrille(0);
		}
		// Si le curseur se situe dans la 1�re sous grille
		else if( (coord.x() >= -0.5 && coord.x() <= 0) && coord.y() >= 0.5)
		{
			joueur.getPartie().getTrace().get(1).add(coord.copy());
			joueur.setGrille(1);
		}
		// Si le curseur se situe dans la 2�me sous grille
		else if( (coord.x() >= 0 && coord.x() <= 0.5) && coord.y() >= 0.5)
		{
			joueur.getPartie().getTrace().get(2).add(coord.copy());
			joueur.setGrille(2);
		}
		// Si le curseur se situe dans la 3�me sous grille
		else if(coord.x() >= 0.5 && coord.y() >= 0.5)
		{
			joueur.getPartie().getTrace().get(3).add(coord.copy());
			joueur.setGrille(3);
		}
		// Si le curseur se situe dans la 4�me sous grille
		else if(coord.x() <= -0.5 && (coord.y() >= 0 && coord.y() <= 0.5) )
		{
			joueur.getPartie().getTrace().get(4).add(coord.copy());
			joueur.setGrille(4);
		}
		// Si le curseur se situe dans la 5�me sous grille
		else if( (coord.x() >= -0.5 && coord.x() <= 0) && (coord.y() >= 0 && coord.y() <= 0.5) )
		{
			joueur.getPartie().getTrace().get(5).add(coord.copy());
			joueur.setGrille(5);
		}
		// Si le curseur se situe dans la 6�me sous grille
		else if( (coord.x() >= 0 && coord.x() <= 0.5) && (coord.y() >= 0 && coord.y() <= 0.5) )
		{
			joueur.getPartie().getTrace().get(6).add(coord.copy());
			joueur.setGrille(6);
		}
		// Si le curseur se situe dans la 7�me sous grille
		else if(coord.x() >= 0.5 && (coord.y() >= 0 && coord.y() <= 0.5) )
		{
			joueur.getPartie().getTrace().get(7).add(coord.copy());
			joueur.setGrille(7);
		}
		// Si le curseur se situe dans la 8�me sous grille
		else if(coord.x() <= -0.5 && (coord.y() >= -0.5 && coord.y() <= 0) )
		{
			joueur.getPartie().getTrace().get(8).add(coord.copy());
			joueur.setGrille(8);
		}
		// Si le curseur se situe dans la 9�me sous grille
		else if( (coord.x() >= -0.5 && coord.x() <= 0) && (coord.y() >= -0.5 && coord.y() <= 0) )
		{
			joueur.getPartie().getTrace().get(9).add(coord.copy());
			joueur.setGrille(9);
		}
		// Si le curseur se situe dans la 10�me sous grille
		else if( (coord.x() >= 0 && coord.x() <= 0.5) && (coord.y() >= -0.5 && coord.y() <= 0) )
		{
			joueur.getPartie().getTrace().get(10).add(coord.copy());
			joueur.setGrille(10);
		}
		// Si le curseur se situe dans la 11�me sous grille
		else if(coord.x() >= 0.5 && (coord.y() >= -0.5 && coord.y() <= 0) )
		{
			joueur.getPartie().getTrace().get(11).add(coord.copy());
			joueur.setGrille(11);
		}
		// Si le curseur se situe dans la 12�me sous grille
		else if(coord.x() <= -0.5 && coord.y() <= -0.5)
		{
			joueur.getPartie().getTrace().get(12).add(coord.copy());
			joueur.setGrille(12);
		}
		// Si le curseur se situe dans la 13�me sous grille
		else if( (coord.x() >= -0.5 && coord.x() <= 0) && coord.y() <= -0.5)
		{
			joueur.getPartie().getTrace().get(13).add(coord.copy());
			joueur.setGrille(13);
		}
		// Si le curseur se situe dans la 14�me sous grille
		else if( (coord.x() >= 0 && coord.x() <= 0.5) && coord.y() <= -0.5)
		{
			joueur.getPartie().getTrace().get(14).add(coord.copy());
			joueur.setGrille(14);
		}
		// Si le curseur se situe dans la 15�me sous grille
		else if(coord.x() >= 0.5 && coord.y() <= -0.5)
		{
			joueur.getPartie().getTrace().get(15).add(coord.copy());
			joueur.setGrille(15);
		}
		
/*
		System.out.println("Ligne - Ajout de coordonn�es en ("+coord.x()+","+coord.y()+") dans la grille "+this.getJoueur().getGrille());
		System.out.println("Ligne - CONTENU grille "+this.getJoueur().getGrille()+" de taille "+joueur.getPartie().getTrace().get(joueur.getGrille()).size()+" : ");
		Iterator<Vector3> it = joueur.getPartie().getTrace().get(joueur.getGrille()).iterator();
		
		while(it.hasNext())
		{
			Vector3 pos = new Vector3();
			pos = it.next();
			System.out.println("("+pos.x()+","+pos.y()+")");
		}*/
	}
	
	public void majVitessesCourbe() {
		/*
		 * Mise � jour des Vitesses (vitesse et vitesse2) -> r�alis� � chaque frame (sachant que les fps sont maj toutes les secondes, peu utile)
		 */
		
		
		float realFPS = (float) polyfever.getRealFPS();
		if(realFPS == 0.0f) realFPS = (float) polyfever.getFPS();
		
		

		this.vitesse = 0.009f*(30.0f/realFPS); 
		this.vitesse2 = 0.009f*(30.0f/realFPS);
		this.courbe = Math.PI/35*(30.0f/realFPS);
		
		//System.out.println("vitesse: ".concat(String.valueOf(vitesse)));
		//System.out.println("vitesse2: ".concat(String.valueOf(vitesse2)));

	}
	
	public void tournerDroite()	// M�thode calculant la prochaine position, si le joueur veut tourner � droite
	{
		/* Calculer les nouvelles coordonn�es
		 * Ajouter les nouvelles coordonn�es au tableau des coord
		 * Envoyer ces coordonn�es � l'affichage
		 */
		
		majVitessesCourbe(); // mise � jour des vitesses en fonction du frameRate
		
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
		
		// Remplissage du tableau des trac�s
		// A VOIR SI LE Z DU VECTOR 3 EST 0 OU 1, SI 1 ON L'AJOUTE, SI 0, ON L'AJOUTE PAS
		this.ajouterCoord(joueur.getPosition());
		
		// Affectation de la nouvelle position du joueur
		joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 1);
		
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
	
	public void tournerGauche()	// M�thode calculant la prochaine position, si le joueur veut tourner � droite
	{
		/* Calculer les nouvelles coordonn�es
		 * Ajouter les nouvelles coordonn�es au tableau des coord
		 * Envoyer ces coordonn�es � l'affichage
		 */
		
		majVitessesCourbe(); // mise � jour des vitesses en fonction du frameRate
		
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
		
		// Remplissage du tableau des trac�s
		// A VOIR SI LE Z DU VECTOR 3 EST 0 OU 1, SI 1 ON L'AJOUTE, SI 0, ON L'AJOUTE PAS
		this.ajouterCoord(joueur.getPosition());
		
		// Affectation de la nouvelle position du joueur
		joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 1);
	
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
	
	public void pasTourner()
	{
		/* Calculer les nouvelles coordonn�es
		 * Ajouter les nouvelles coordonn�es au tableau des coord
		 * Envoyer ces coordonn�es � l'affichage
		 */
		
		majVitessesCourbe(); // mise � jour des vitesses en fonction du frameRate
		
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
		
		// Remplissage du tableau des trac�s
		// A VOIR SI LE Z DU VECTOR 3 EST 0 OU 1, SI 1 ON L'AJOUTE, SI 0, ON L'AJOUTE PAS
		//System.out.println("Ligne - Demande ajout ("+joueur.getPosition().x()+","+joueur.getPosition().y()+")");
		this.ajouterCoord(joueur.getPosition());
				
		// Affectation de la nouvelle position du joueur
		joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY, 1);
		
		// Pas de mise � jour de la direction, vu qu'elle ne change pas
		
	}
	
	public void tracerTrou()
	{
		/* 
		 * M�thode calculant le moment pour un joueur de tracer un trou
		 * Si trace trou ne pas �crire dans le tableau des coordonn�es des lignes !
		 * Juste changer les coordonn�es pos_x et pos_y du joueur
		 * 
		 * Principe :
		 * A l'initialisation du joueur on d�finit un temps constant pour le reste de la partie
		 * Ce temps d�finira tous les combiens un trou sera trac�
		 * Ce temps sera al�atoire (sinon si tous les joueurs ont le m�me temps, ils traceraient des trous au m�me moment
		 * Quand le temps vient, on change le dernier bit du Vector Position en 0 pour pas tracer
		 */
		
		
	}

	@Override
	public String toString() {
		return "Ligne [couleur=" + couleur +", vitesse=" + vitesse + ", epaisseur=" + epaisseur
				+ ", courbe=" + courbe + ", tpsEnVie=" + tpsEnVie +"]";
	}
	
}
