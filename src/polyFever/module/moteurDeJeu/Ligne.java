package polyFever.module.moteurDeJeu;

/* 
 * Classe stockant les informations liés à une ligne contrôlée par un joueur
 * Et gérant les paramètres de la ligne
 */

import java.util.*;

public class Ligne {

	// LA COULEUR SOUS QUEL TYPE JE DOIS L'ENVOYER ???
	public String couleur;				// Couleur de la ligne
	public Joueur joueur;				// Joueur controlant la ligne
	public int vitesse;					// Vitesse de la ligne (en pixels)
	public int epaisseur;				// Epaisseur du trait
	private double courbe;			// Rayon de courbure de la ligne (en radians)
	public int tpsEnVie;				// Temps passé en vie durant un round (en secondes)
	private List<Vector2> trace;		// Tableau de Vector2, donnant les coordonnées des points passés par la ligne, donnant ainsi le chemin parcouru
	
	// Constructeur
	public Ligne()	// Par défaut
	{
		System.out.println("Instanciation d'un objet Ligne (sp)...");
		this.couleur = "white";
		this.joueur = null;
		this.vitesse = 2;
		this.epaisseur = 1;
		this.courbe = Math.PI / 6;
		this.tpsEnVie = 0;
		this.trace = new ArrayList<Vector2>();
	}
	
	public Ligne(String couleur)	// Avec paramètres
	{
		this();
		System.out.println("Instanciation d'un objet Ligne (ap)...");
		this.couleur = couleur;
	}
	
	// Méthodes

	/*
	 * Assesseurs et mutateurs
	 */
	
	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public int getEpaisseur() {
		return epaisseur;
	}

	public void setEpaisseur(int epaisseur) {
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
	 * Autres méthodes de gestion des lignes
	 */
	
	// VRAIMENT UTILE ????
	public void ajoutCoord(int x, int y)	// Méthode ajoutant la position du point du joueur au tableau du tracé
	{
		/* Ajouter ces coordonnées au tableau des coordonnées
		 */
		System.out.println("Ajout de coordonnées dans le tableau trace : x:"+x+" | y:"+y);
		Vector2 coordonnees = new Vector2(x,y);
		trace.add(coordonnees);
	}
	
	public void tournerDroite(Joueur joueur)	// Méthode calculant la prochaine position, si le joueur veut tourner à droite
	{
		/* Calculer les nouvelles coordonnées
		 * Ajouter les nouvelles coordonnées au tableau des coord
		 * Envoyer ces coordonnées à l'affichage
		 */
		
		System.out.println("TOURNE DROITE / direction : "+joueur.direction+" direction-courbe : "+(joueur.direction-courbe));
		double angleRotation = 0;
		Vector2 nouvPosition = new Vector2();
		
		// Calcul de l'angle de rotation
		if( ((joueur.direction - courbe) >= 0) && ((joueur.direction - courbe) <= (Math.PI/2)) )
		{
			System.out.println("Cas 1");
			angleRotation = Math.abs(joueur.direction - courbe);
		}
		else if( ((joueur.direction - courbe) > (Math.PI/2)) && ((joueur.direction - courbe) <= Math.PI) )
		{
			System.out.println("Cas 2");
			angleRotation = Math.PI - (joueur.direction - courbe);
		}
		else if( ((joueur.direction - courbe) > Math.PI) && ((joueur.direction - courbe) <= (3*Math.PI/2)) )
		{
			System.out.println("Cas 3");
			angleRotation = (joueur.direction - courbe) - Math.PI;
		}
		else if( ((joueur.direction - courbe) > (3*Math.PI/2)))
		{
			System.out.println("Cas 4");
			angleRotation = 2*Math.PI - (joueur.direction - courbe);
		}
		else if((joueur.direction - courbe) < 0)
		{
			System.out.println("Cas 5");
			angleRotation = courbe - joueur.direction;
		}
		System.out.println("Angle de Rotation =  "+angleRotation);
		
		
		// Calcul de la nouvelle position en x
		if( (((joueur.direction - courbe) >= 0) && ((joueur.direction - courbe) <= (Math.PI/2))) || ((joueur.direction - courbe) >= (3*Math.PI/2)) || ((joueur.direction - courbe) < 0) )
		{
			System.out.println("Calcul 1");
			nouvPosition.x = joueur.position.x + (vitesse * Math.cos(angleRotation));
		}
		else if( ((joueur.direction - courbe) > (Math.PI/2)) && ((joueur.direction - courbe) < (3*Math.PI/2)) )
		{
			System.out.println("Calcul 2");
			nouvPosition.x = joueur.position.x - (vitesse * Math.cos(angleRotation));
		}
		System.out.println("nouvPos x =  "+nouvPosition.x);
		
		
		// Calcul de la nouvelle position en y
		if( (((joueur.direction - courbe) >= Math.PI) && ((joueur.direction - courbe) <= (2*Math.PI)))  || ((joueur.direction - courbe) < 0)  )
		{
			System.out.println("Calcul 1");
			nouvPosition.y = joueur.position.y - (vitesse * Math.sin(angleRotation));
		}
		else if( ((joueur.direction - courbe) >= 0) && ((joueur.direction - courbe) < Math.PI) )
		{
			System.out.println("Calcul 2");
			nouvPosition.y = joueur.position.y + (vitesse * Math.sin(angleRotation));
		}
		System.out.println("nouvPos y =  "+nouvPosition.y);
		
		
		// Affectation des nouvelles positions
		joueur.position.x = nouvPosition.x;
		joueur.position.y = nouvPosition.y;
		
		// Remplissage du tableau des tracés
		trace.add(nouvPosition);
		
		// Mise à jour de la direction
		if((joueur.direction - courbe) < 0)
		{
			joueur.direction = (2*Math.PI) - angleRotation;
		}
		else
		{
			joueur.direction = joueur.direction - courbe;
		}
		System.out.println("MAJ direction : "+joueur.direction);
		
	}
	
	public void tournerGauche(Joueur joueur)	// Méthode calculant la prochaine position, si le joueur veut tourner à droite
	{
		/* Calculer les nouvelles coordonnées
		 * Ajouter les nouvelles coordonnées au tableau des coord
		 * Envoyer ces coordonnées à l'affichage
		 */
		
		double angleRotation = 0;
		Vector2 nouvPosition = new Vector2();
		nouvPosition.x = 0;
		nouvPosition.y = 0;
		
		System.out.println("TOURNE GAUCHE / direction : "+joueur.direction);
		
		// Calcul de l'angle de rotation
		if(joueur.direction == 0.0)
		{
			angleRotation = courbe;
			System.out.println("Cas 1");			
		}
		else if( ((joueur.direction + courbe) >= 0) && ((joueur.direction + courbe) <= (Math.PI/2)) )
		{
			angleRotation = joueur.direction + courbe;
			System.out.println("Cas 2");
		}
		else if( ((joueur.direction + courbe) > (Math.PI/2)) && ((joueur.direction + courbe) <= Math.PI) )
		{
			angleRotation = Math.PI - (joueur.direction + courbe);
			System.out.println("Cas 3");
		}
		else if( ((joueur.direction + courbe) > Math.PI) && ((joueur.direction + courbe) <= (3*Math.PI/2)) )
		{
			angleRotation = Math.PI - (2*Math.PI - (joueur.direction + courbe));
			System.out.println("Cas 4");
		}
		else if( ((joueur.direction + courbe) > (3*Math.PI/2)) && ((joueur.direction + courbe) <= (2*Math.PI)) )
		{
			angleRotation = 2*Math.PI - (joueur.direction + courbe);
			System.out.println("Cas 5");
		}
		else if((joueur.direction + courbe) > (2*Math.PI))
		{
			angleRotation = (joueur.direction + courbe) - (2*Math.PI);
			System.out.println("Cas 6");
		}
		
		System.out.println("Angle de Rotation =  "+angleRotation);
		
		// Calcul de la nouvelle position en x
		if( (((joueur.direction + courbe) >= (Math.PI/2)) && ((joueur.direction + courbe) <= (3*Math.PI/2))))
		{
			System.out.println("Calcul 1");
			nouvPosition.x = joueur.position.x - (vitesse * Math.cos(angleRotation));
		}
		else if( (((joueur.direction + courbe) >= 0) && ((joueur.direction + courbe) < (Math.PI/2))) || (((joueur.direction + courbe) > (3*Math.PI/2)) && ((joueur.direction + courbe) <= (2*Math.PI)))  || ((joueur.direction + courbe) > (2*Math.PI)))
		{
			System.out.println("Calcul 2");
			try
			{
				nouvPosition.x = joueur.position.x + (vitesse * Math.cos(angleRotation));
			}
			catch (NullPointerException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("nouvPos x =  "+nouvPosition.x);
		
		// Calcul de la nouvelle position en y
		if( ((joueur.direction + courbe) >= Math.PI) && ((joueur.direction + courbe) <= (2*Math.PI)) )
		{
			System.out.println("Calcul 1");
			nouvPosition.y = joueur.position.y - (vitesse * Math.sin(angleRotation));
		}
		else if( (((joueur.direction + courbe) >= 0) && ((joueur.direction + courbe) < Math.PI))   || ((joueur.direction + courbe) > (2*Math.PI)))
		{
			System.out.println("Calcul 2");
			try
			{
				nouvPosition.y = joueur.position.y + (vitesse * Math.sin(angleRotation));
			}
			catch (NullPointerException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("nouvPos y =  "+nouvPosition.y);
		
		// Affectation des nouvelles positions
		joueur.position.x = nouvPosition.x;
		joueur.position.y = nouvPosition.y;
		
		// Remplissage du tableau des tracés
		trace.add(nouvPosition);
		
		// Mise à jour de la direction
		if((joueur.direction + courbe) > (2*Math.PI))
		{
			joueur.direction = angleRotation;
		}
		else
		{
			joueur.direction = joueur.direction + courbe;
		}
		System.out.println("MAJ direction : "+joueur.direction);
		
	}
	
	public void pasTourner(Joueur joueur)
	{
		/* Calculer les nouvelles coordonnées
		 * Ajouter les nouvelles coordonnées au tableau des coord
		 * Envoyer ces coordonnées à l'affichage
		 */
		
		double angleRotation = 0;
		Vector2 nouvPosition = new Vector2();
		
		System.out.println("PAS TOURNER / direction : "+joueur.direction);
		
		// Calcul de l'angle de rotation
		if( (joueur.direction >= 0) && (joueur.direction <= (Math.PI/2)) )
		{
			System.out.println("Cas 1");
			angleRotation = joueur.direction;
		}
		else if( (joueur.direction > (Math.PI/2)) && (joueur.direction <= Math.PI) )
		{
			System.out.println("Cas 2");
			angleRotation = Math.PI - joueur.direction;
		}
		else if( (joueur.direction > Math.PI) && (joueur.direction <= (3*Math.PI/2)) )
		{
			System.out.println("Cas 3");
			angleRotation = Math.abs(- Math.PI - joueur.direction);
		}
		else if( (joueur.direction > (3*Math.PI/2)) && (joueur.direction <= (2*Math.PI)) )
		{
			System.out.println("Cas 4");
			angleRotation = (2*Math.PI) - joueur.direction;
		}
		System.out.println("Angle de Rotation =  "+angleRotation);
		
		
		
		// Calcul de la nouvelle position en x
		if( ((joueur.direction) > (Math.PI/2)) && ((joueur.direction) <= (3*Math.PI/2)) )
		{
			System.out.println("Calcul 1");
			nouvPosition.x = joueur.position.x - (vitesse * Math.cos(angleRotation));
		}
		else if( (((joueur.direction) >= 0) && ((joueur.direction) <= (Math.PI/2))) || (((joueur.direction) > (3*Math.PI/2)) && ((joueur.direction) <= (2*Math.PI))) )
		{
			System.out.println("Calcul 2");
			nouvPosition.x = joueur.position.x + (vitesse * Math.cos(angleRotation));
		}
		System.out.println("nouvPos x =  "+nouvPosition.x);
		
		
		
		// Calcul de la nouvelle position en y
		if( ((joueur.direction) >= Math.PI) && ((joueur.direction) <= (2*Math.PI)) )
		{
			System.out.println("Calcul 1");
			nouvPosition.y = joueur.position.y - (vitesse * Math.sin(angleRotation));
		}
		else if( ((joueur.direction) >= 0) && ((joueur.direction) < Math.PI) )
		{
			System.out.println("Calcul 2");
			nouvPosition.y = joueur.position.y + (vitesse * Math.sin(angleRotation));
		}
		System.out.println("nouvPos y =  "+nouvPosition.y);
		
		
		// Affectation des nouvelles positions
		joueur.position.x = nouvPosition.x;
		joueur.position.y = nouvPosition.y;
		System.out.println("Nouvelle position : x= "+nouvPosition.x+" ; y= "+nouvPosition.y);
		
		// Remplissage du tableau des tracés
		trace.add(nouvPosition);
		
		// Pas de mise à jour de la direction, vu qu'elle ne change pas
		
	}
	
	public void tracerTrou()
	{
		/* Méthode calculant le moment pour un joueur de tracer un trou
		 * Si trace trou ne pas écrire dans le tableau des coordonnées des lignes !
		 * Juste changer les coordonnées pos_x et pos_y du joueur
		 */
	}

	@Override
	public String toString() {
		return "Ligne [couleur=" + couleur + ", joueur=" + joueur
				+ ", vitesse=" + vitesse + ", epaisseur=" + epaisseur
				+ ", courbe=" + courbe + ", tpsEnVie=" + tpsEnVie + ", trace="
				+ trace + "]";
	}
	
}
