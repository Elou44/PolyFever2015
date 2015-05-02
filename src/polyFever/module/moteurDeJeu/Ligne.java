package polyFever.module.moteurDeJeu;

/* 
 * Classe stockant les informations li�s � une ligne contr�l�e par un joueur
 * Et g�rant les param�tres de la ligne
 */

import java.util.*;
import polyFever.module.util.math.Vector2;

public class Ligne {

	// LA COULEUR SOUS QUEL TYPE JE DOIS L'ENVOYER ???
	// ENTIER DE 1 A 24 !!!!!!!!
	private int couleur;				// Couleur de la ligne
	private Joueur joueur;				// Joueur controlant la ligne
	private float vitesse2;				// Vitesse de la ligne (en pixels)
	private float vitesse;  			// Vitesse de la ligne en float
	private float epaisseur;			// Epaisseur du trait
	private double courbe;				// Rayon de courbure de la ligne (en radians)
	private int tpsEnVie;				// Temps pass� en vie durant un round (en secondes)
	private List<Vector2> trace;		// Tableau de Vector2, donnant les coordonn�es des points pass�s par la ligne, donnant ainsi le chemin parcouru
	
	// Constructeur
	public Ligne()	// Par d�faut
	{
		System.out.println("Instanciation d'un objet Ligne (sp)...");
		this.couleur = 0;
		this.joueur = null;
		this.vitesse2 = 3;
		this.vitesse = 0.012f;
		this.epaisseur = 7;
		this.courbe = Math.PI / 20;
		this.tpsEnVie = 0;
		this.trace = new ArrayList<Vector2>();
	}
	
	public Ligne(int couleur)	// Avec param�tres
	{
		this();
		System.out.println("Instanciation d'un objet Ligne (ap)...");
		this.couleur = couleur;
	}
	
	// M�thodes

	/*
	 * Assesseurs et mutateurs
	 */
	
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

	public void setVitesse(float vitesse) {
		this.vitesse = vitesse;
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

	public List<Vector2> getTrace() {
		return trace;
	}

	public void setTrace(List<Vector2> trace) {
		this.trace = trace;
	}
	
	/*
	 * Autres m�thodes de gestion des lignes
	 */
	
	// VRAIMENT UTILE ????
	public void ajoutCoord(int x, int y)	// M�thode ajoutant la position du point du joueur au tableau du trac�
	{
		/* Ajouter ces coordonn�es au tableau des coordonn�es
		 */
		System.out.println("Ajout de coordonn�es dans le tableau trace : x:"+x+" | y:"+y);
		Vector2 coordonnees = new Vector2(x,y);
		trace.add(coordonnees);
	}
	
	public void tournerDroite()	// M�thode calculant la prochaine position, si le joueur veut tourner � droite
	{
		/* Calculer les nouvelles coordonn�es
		 * Ajouter les nouvelles coordonn�es au tableau des coord
		 * Envoyer ces coordonn�es � l'affichage
		 */
		
		System.out.println("TOURNE DROITE / direction : "+joueur.getDirection()+" direction-courbe : "+(joueur.getDirection()-courbe));
		double angleRotation = 0;
		double nouvPositionX = 0;
		double nouvPositionY = 0;
		
		// Calcul de l'angle de rotation
		if( ((joueur.getDirection() - courbe) >= 0) && ((joueur.getDirection() - courbe) <= (Math.PI/2)) )
		{
			System.out.println("Cas 1");
			angleRotation = Math.abs(joueur.getDirection() - courbe);
		}
		else if( ((joueur.getDirection() - courbe) > (Math.PI/2)) && ((joueur.getDirection() - courbe) <= Math.PI) )
		{
			System.out.println("Cas 2");
			angleRotation = Math.PI - (joueur.getDirection() - courbe);
		}
		else if( ((joueur.getDirection() - courbe) > Math.PI) && ((joueur.getDirection() - courbe) <= (3*Math.PI/2)) )
		{
			System.out.println("Cas 3");
			angleRotation = (joueur.getDirection() - courbe) - Math.PI;
		}
		else if( ((joueur.getDirection() - courbe) > (3*Math.PI/2)))
		{
			System.out.println("Cas 4");
			angleRotation = 2*Math.PI - (joueur.getDirection() - courbe);
		}
		else if((joueur.getDirection() - courbe) < 0)
		{
			System.out.println("Cas 5");
			angleRotation = courbe - joueur.getDirection();
		}
		System.out.println("Angle de Rotation =  "+angleRotation);
		
		
		// Calcul de la nouvelle position en x
		if( (((joueur.getDirection() - courbe) >= 0) && ((joueur.getDirection() - courbe) <= (Math.PI/2))) || ((joueur.getDirection() - courbe) >= (3*Math.PI/2)) || ((joueur.getDirection() - courbe) < 0) )
		{
			System.out.println("Calcul 1");
			nouvPositionX = joueur.getPosition().x() + (vitesse * Math.cos(angleRotation));
			joueur.getPosition().x((float)nouvPositionX);
		}
		else if( ((joueur.getDirection() - courbe) > (float)(Math.PI/2)) && ((joueur.getDirection() - courbe) < (float)(3*Math.PI/2)) )
		{
			System.out.println("Calcul 2");
			nouvPositionX = joueur.getPosition().x() - (vitesse * Math.cos(angleRotation));
			
		}
		System.out.println("nouvPos x =  "+nouvPositionX);
		
		
		// Calcul de la nouvelle position en y
		if( (((joueur.getDirection() - courbe) >= Math.PI) && ((joueur.getDirection() - courbe) <= (2*Math.PI)))  || ((joueur.getDirection() - courbe) < 0)  )
		{
			System.out.println("Calcul 1");
			nouvPositionY = joueur.getPosition().y() - (vitesse * Math.sin(angleRotation));
		}
		else if( ((joueur.getDirection() - courbe) >= 0) && ((joueur.getDirection() - courbe) < Math.PI) )
		{
			System.out.println("Calcul 2");
			nouvPositionY = joueur.getPosition().y() + (vitesse * Math.sin(angleRotation));
			
		}
		System.out.println("nouvPos y =  "+nouvPositionY);
		
		// Affectation de la nouvelle position du joueur
		joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY);
		
		// Remplissage du tableau des trac�s
		trace.add(joueur.getPosition());
		
		// Stockage de l'ancien angle direction pour la construction du rectangle formant le trac�
		joueur.setAngleRectangle((float)joueur.getDirection());
		
		// Mise � jour de la direction
		if((joueur.getDirection() - courbe) < 0)
		{
			joueur.setDirection((2*Math.PI) - angleRotation);
		}
		else
		{
			joueur.setDirection(joueur.getDirection() - courbe);
		}
		System.out.println("MAJ direction : "+joueur.getDirection());
		
	}
	
	public void tournerGauche()	// M�thode calculant la prochaine position, si le joueur veut tourner � droite
	{
		/* Calculer les nouvelles coordonn�es
		 * Ajouter les nouvelles coordonn�es au tableau des coord
		 * Envoyer ces coordonn�es � l'affichage
		 */
		
		double angleRotation = 0;
		double nouvPositionX = 0;
		double nouvPositionY = 0;
		
		System.out.println("TOURNE GAUCHE / direction : "+joueur.getDirection());
		
		// Calcul de l'angle de rotation
		if(joueur.getDirection() == 0.0)
		{
			angleRotation = courbe;
			System.out.println("Cas 1");			
		}
		else if( ((joueur.getDirection() + courbe) >= 0) && ((joueur.getDirection() + courbe) <= (Math.PI/2)) )
		{
			angleRotation = joueur.getDirection() + courbe;
			System.out.println("Cas 2");
		}
		else if( ((joueur.getDirection() + courbe) > (Math.PI/2)) && ((joueur.getDirection() + courbe) <= Math.PI) )
		{
			angleRotation = Math.PI - (joueur.getDirection() + courbe);
			System.out.println("Cas 3");
		}
		else if( ((joueur.getDirection() + courbe) > Math.PI) && ((joueur.getDirection() + courbe) <= (3*Math.PI/2)) )
		{
			angleRotation = Math.PI - (2*Math.PI - (joueur.getDirection() + courbe));
			System.out.println("Cas 4");
		}
		else if( ((joueur.getDirection() + courbe) > (3*Math.PI/2)) && ((joueur.getDirection() + courbe) <= (2*Math.PI)) )
		{
			angleRotation = 2*Math.PI - (joueur.getDirection() + courbe);
			System.out.println("Cas 5");
		}
		else if((joueur.getDirection() + courbe) > (2*Math.PI))
		{
			angleRotation = (joueur.getDirection() + courbe) - (2*Math.PI);
			System.out.println("Cas 6");
		}
		
		System.out.println("Angle de Rotation =  "+angleRotation);
		
		// Calcul de la nouvelle position en x
		if( (((joueur.getDirection() + courbe) >= (Math.PI/2)) && ((joueur.getDirection() + courbe) <= (3*Math.PI/2))))
		{
			System.out.println("Calcul 1");
			nouvPositionX = joueur.getPosition().x() - (vitesse * Math.cos(angleRotation));
		}
		else if( (((joueur.getDirection() + courbe) >= 0) && ((joueur.getDirection() + courbe) < (Math.PI/2))) || (((joueur.getDirection() + courbe) > (3*Math.PI/2)) && ((joueur.getDirection() + courbe) <= (2*Math.PI)))  || ((joueur.getDirection() + courbe) > (2*Math.PI)))
		{
			System.out.println("Calcul 2");
			nouvPositionX = joueur.getPosition().x() + (vitesse * Math.cos(angleRotation));
		}
		System.out.println("nouvPos x =  "+nouvPositionX);
		
		// Calcul de la nouvelle position en y
		if( ((joueur.getDirection() + courbe) >= Math.PI) && ((joueur.getDirection() + courbe) <= (2*Math.PI)) )
		{
			System.out.println("Calcul 1");
			nouvPositionY = joueur.getPosition().y() - (vitesse * Math.sin(angleRotation));
		}
		else if( (((joueur.getDirection() + courbe) >= 0) && ((joueur.getDirection() + courbe) < Math.PI))   || ((joueur.getDirection() + courbe) > (2*Math.PI)))
		{
			System.out.println("Calcul 2");
			nouvPositionY = joueur.getPosition().y() + (vitesse * Math.sin(angleRotation));
		}
		System.out.println("nouvPos y =  "+nouvPositionY);
		
		// Affectation de la nouvelle position du joueur
		joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY);
		
		// Remplissage du tableau des trac�s
		trace.add(joueur.getPosition());
		
		// Stockage de l'ancien angle direction pour la construction du rectangle formant le trac�
		joueur.setAngleRectangle((float)joueur.getDirection());
		
		// Mise � jour de la direction
		if((joueur.getDirection() + courbe) > (2*Math.PI))
		{
			joueur.setDirection(angleRotation);
		}
		else
		{
			joueur.setDirection(joueur.getDirection() + courbe);
		}
		System.out.println("MAJ direction : "+joueur.getDirection());
		
	}
	
	public void pasTourner()
	{
		/* Calculer les nouvelles coordonn�es
		 * Ajouter les nouvelles coordonn�es au tableau des coord
		 * Envoyer ces coordonn�es � l'affichage
		 */
		
		double angleRotation = 0;
		double nouvPositionX = 0;
		double nouvPositionY = 0;
		
		System.out.println("PAS TOURNER / direction : "+joueur.getDirection());
		
		// Calcul de l'angle de rotation
		if( (joueur.getDirection() >= 0) && (joueur.getDirection() <= (Math.PI/2)) )
		{
			System.out.println("Cas 1");
			angleRotation = joueur.getDirection();
		}
		else if( (joueur.getDirection() > (Math.PI/2)) && (joueur.getDirection() <= Math.PI) )
		{
			System.out.println("Cas 2");
			angleRotation = Math.PI - joueur.getDirection();
		}
		else if( (joueur.getDirection() > Math.PI) && (joueur.getDirection() <= (3*Math.PI/2)) )
		{
			System.out.println("Cas 3");
			angleRotation = Math.abs(- Math.PI - joueur.getDirection());
		}
		else if( (joueur.getDirection() > (3*Math.PI/2)) && (joueur.getDirection() <= (2*Math.PI)) )
		{
			System.out.println("Cas 4");
			angleRotation = (2*Math.PI) - joueur.getDirection();
		}
		System.out.println("Angle de Rotation =  "+angleRotation);
		
		
		
		// Calcul de la nouvelle position en x
		if( ((joueur.getDirection()) > (Math.PI/2)) && ((joueur.getDirection()) <= (3*Math.PI/2)) )
		{
			System.out.println("Calcul 1");
			nouvPositionX = joueur.getPosition().x() - (vitesse * Math.cos(angleRotation));
		}
		else if( (((joueur.getDirection()) >= 0) && ((joueur.getDirection()) <= (Math.PI/2))) || (((joueur.getDirection()) > (3*Math.PI/2)) && ((joueur.getDirection()) <= (2*Math.PI))) )
		{
			System.out.println("Calcul 2");
			nouvPositionX = joueur.getPosition().x() + (vitesse * Math.cos(angleRotation));
		}
		System.out.println("nouvPos x =  "+nouvPositionX);
		
		// Calcul de la nouvelle position en y
		if( ((joueur.getDirection()) >= Math.PI) && ((joueur.getDirection()) <= (2*Math.PI)) )
		{
			System.out.println("Calcul 1");
			nouvPositionY = joueur.getPosition().y() - (vitesse * Math.sin(angleRotation));
		}
		else if( ((joueur.getDirection()) >= 0) && ((joueur.getDirection()) < Math.PI) )
		{
			System.out.println("Calcul 2");
			nouvPositionY = joueur.getPosition().y() + (vitesse * Math.sin(angleRotation));
		}
		System.out.println("nouvPos y =  "+nouvPositionY);
		
		// Affectation de la nouvelle position du joueur
		joueur.getPosition().set((float)nouvPositionX, (float)nouvPositionY);
		
		// Remplissage du tableau des trac�s
		trace.add(joueur.getPosition());

		// Stockage de l'ancien angle direction pour la construction du rectangle formant le trac�
		joueur.setAngleRectangle((float)joueur.getDirection());
		
		// Pas de mise � jour de la direction, vu qu'elle ne change pas
		
	}
	
	public void tracerTrou()
	{
		/* M�thode calculant le moment pour un joueur de tracer un trou
		 * Si trace trou ne pas �crire dans le tableau des coordonn�es des lignes !
		 * Juste changer les coordonn�es pos_x et pos_y du joueur
		 */
	}

	@Override
	public String toString() {
		return "Ligne [couleur=" + couleur +", vitesse=" + vitesse + ", epaisseur=" + epaisseur
				+ ", courbe=" + courbe + ", tpsEnVie=" + tpsEnVie + ", trace="
				+ trace + "]";
	}
	
}
