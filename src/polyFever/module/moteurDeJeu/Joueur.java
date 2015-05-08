package polyFever.module.moteurDeJeu;

import polyFever.module.util.math.Vector3;

/* 
 * Classe stockant les informations liés à un joueur
 * Et gérant les paramètres du joueur
 */

public class Joueur {

	// ATTRIBUT POUR NE PAS TRACER DANS JOUEUR OU LIGNE ????
	private String pseudo;			// Pseudo du joueur
	// ??? TYPE DES TOUCHES -> APPREMMENT PLUS DES INT ???
	private int toucheG;			// Sa touche de jeu, tournant à gauche
	private int toucheD;			// Sa touche de jeu, tournant à droite
	private int score;				// Score du joueur, sur une partie
	private Etat etat;				// Etat du joueur
	// COMMENT DEFINIR LE ROLE ??? QUELLE INFORMATION JE VAIS RECEVOIR ? QUEL TYPE ?
	private Role type;				// Variable définissant le role du joueur, accueillant l'énumération
	private Ligne ligne;			// Objet Ligne controlée par le joueur
	private Vector3 position;		// Vector2 donnant la position actuelle du point du joueur
	private float angleRectangle;	// Angle utilisé pour l'affichage des rectangles du tracé de la ligne
	private double direction;		// Direction du point du joueur (en radians)
	private boolean toucheGPresse;	// Booléen disant si la toucheG est enfoncée
	private boolean toucheDPresse;	// Booléen disant si la toucheD est enfoncée
	
	// Constructeur
	public Joueur()	// Par défaut
	{
		System.out.println("Instanciation d'un objet Joueur (sp)...");
		this.pseudo = null;
		this.toucheG = 0;
		this.toucheD = 0;
		this.score = 0;
		this.etat = Etat.VIVANT;
		this.type = Role.CLIENT;
		this.position = new Vector3();
		this.direction = Math.PI/2;
		this.angleRectangle = (float) Math.PI/2;
		this.toucheDPresse = false;
		this.toucheGPresse = false;
	}
	
	public Joueur(String pseudo, int toucheG, int toucheD, int couleur)	// Avec paramètres
	{
		this();
		System.out.println("Instanciation d'un objet Joueur (ap)...");
		this.pseudo = pseudo;
		this.toucheG = toucheG;
		this.toucheD = toucheD;
		this.ligne.setCouleur(couleur);
	}

	// Méthodes
	
	/* 
	 * Assesseurs et mutateurs
	 */
	
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getToucheG() {
		return toucheG;
	}

	public void setToucheG(int toucheG) {
		this.toucheG = toucheG;
	}

	public int getToucheD() {
		return toucheD;
	}

	public void setToucheD(int toucheD) {
		this.toucheD = toucheD;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public Role getType() {
		return type;
	}

	public void setType(Role type) {
		this.type = type;
	}

	public Ligne getLigne() {
		return ligne;
	}

	public void setLigne(Ligne ligne) {
		this.ligne = ligne;
	}

	public Vector3 getPosition() {
		return position;
	}

	public void setPosition(Vector3 position) {
		this.position = position;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
	}

	public float getAngleRectangle() {
		return angleRectangle;
	}

	public void setAngleRectangle(float angleRectangle) {
		this.angleRectangle = angleRectangle;
	}
	
	/* 
	 * Autres méthodes de gestion des joueurs
	 */
	
	public void mettrePause()	// Mise en pause d'une partie
	{
		/* Il faut arrêter tous les traitements, ne plus envoyer d'informations pour tracer ou afficher
		 * et attendre le signal pour enlever la pause
		 */
	}
	
	public void quitterPartie()	// Quitter la partie par un joueur
	{
		/* 
		 * Vérifier si le joueur quittant la partie est l'hébergeur de la partie
		 * 		Si oui : il faut faire quitter tout le monde
		 * Si non : - changer l'état du joueur en mort
		 * 			- ne plus envoyer d'informations par rapport à ce joueur (liste joueur ayant quittés?)
		 * 
		 */
		
		
	}
	
	
	public void priseBonus()
	{
		/* Vérifier si les coordonnées de la ligne touchent les coordonnées d'un bonus
		 * Faire disparaitre le bonus du plateau
		 * Changer les paramètres du joueur concerné
		 */
	}

	@Override
	public String toString() {
		return "Joueur [pseudo=" + pseudo + ", toucheG=" + toucheG
				+ ", toucheD=" + toucheD + ", score=" + score + ", etat="
				+ etat + ", type=" + type + ", ligne=" + ligne + ", position.x="
				+ position.x() + ", position.y="+ position.y() + ", direction=" + direction + "]";
	}
	
}
