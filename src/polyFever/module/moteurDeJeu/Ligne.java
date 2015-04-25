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
	public int vitesse;					// Vitesse de la ligne
	public int epaisseur;				// Epaisseur du trait
	protected int courbe;				// Rayon de courbure de la ligne
	public int tpsEnVie;				// Temps passé en vie durant un round (en secondes)
	private List<Vector2> trace;		// Tableau de Vector2, donnant les coordonnées des points passés par la ligne, donnant ainsi le chemin parcouru
	
	// Constructeur
	public Ligne()	// Par défaut
	{
		System.out.println("Instanciation d'un objet Ligne (sp)...");
		this.couleur = "white";
		this.joueur = null;
		this.vitesse = 1;
		this.epaisseur = 1;
		this.courbe = 45;
		this.tpsEnVie = 0;
		this.trace = new ArrayList<Vector2>();
	}
	
	public Ligne(String couleur, Joueur joueur)	// Avec paramètres
	{
		super();
		System.out.println("Instanciation d'un objet Ligne (ap)...");
		this.couleur = couleur;
		this.joueur = joueur;
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

	public int getCourbe() {
		return courbe;
	}

	public void setCourbe(int courbe) {
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
		System.out.println("Au prochain carrefour tourner à droite...");
	}
	
	public void tournerGauche(Joueur joueur)	// Méthode calculant la prochaine position, si le joueur veut tourner à droite
	{
		/* Calculer les nouvelles coordonnées
		 * Ajouter les nouvelles coordonnées au tableau des coord
		 * Envoyer ces coordonnées à l'affichage
		 */
		System.out.println("Au prochain rond-point prendre la 3ème sortie à gauche...");
	}
	
	public void tracerTrou()
	{
		/* Méthode calculant le moment pour un joueur de tracer un trou
		 * Si trace trou ne pas écrire dans le tableau des coordonnées des lignes !
		 * Juste changer les coordonnées pos_x et pos_y du joueur
		 */
	}
	
}
