package polyFever.module.moteurDeJeu;

import polyFever.module.util.math.Vector3;


/* 
 * Classe stockant les informations li�s � un joueur
 * Et g�rant les param�tres du joueur
 */

public class Joueur {

	private String pseudo;			// Pseudo du joueur
	private int toucheG;			// Sa touche de jeu, tournant � gauche
	private int toucheD;			// Sa touche de jeu, tournant � droite
	private int score;				// Score du joueur, sur une partie
	private Etat etat;				// Etat du joueur
	// COMMENT DEFINIR LE ROLE ??? QUELLE INFORMATION JE VAIS RECEVOIR ? QUEL TYPE ?
	private Role type;				// Variable d�finissant le role du joueur, accueillant l'�num�ration
	private Ligne ligne;			// Objet Ligne control�e par le joueur
	private Vector3 position;		// Vector2 donnant la position actuelle du point du joueur
	private double direction;		// Direction du point du joueur (en radians)
	private boolean toucheGPresse;	// Bool�en disant si la toucheG est enfonc�e
	private boolean toucheDPresse;	// Bool�en disant si la toucheD est enfonc�e
	private Partie partie;			// Objet Partie auquel est rattach� le joueur
	private int grille;				// Entier donnant l'index de la sous grille du plateau de jeu ou se trouve actuellement le joueur
	
	// Constructeur
	public Joueur(Partie partie)	// Par d�faut
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
		this.toucheDPresse = false;
		this.toucheGPresse = false;
		this.partie = partie;
	}
	
	public Joueur(Partie partie, String pseudo, int toucheG, int toucheD, int couleur)	// Avec param�tres
	{
		this(partie);
		System.out.println("Instanciation d'un objet Joueur (ap)...");
		this.pseudo = pseudo;
		this.toucheG = toucheG;
		this.toucheD = toucheD;
		this.ligne.setCouleur(couleur);
	}

	// M�thodes
	
	/* 
	 * Assesseurs et mutateurs
	 */
	
	public int getGrille() {
		return grille;
	}

	public void setGrille(int grille) {
		this.grille = grille;
	}
	
	public Partie getPartie() {
		return partie;
	}

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
		// rafraichissement de la vitesse
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
		return (float) direction;
	}
	
	public boolean isToucheGPresse() {
		return toucheGPresse;
	}

	public void setToucheGPresse(boolean toucheGPresse) {
		this.toucheGPresse = toucheGPresse;
	}

	public boolean isToucheDPresse() {
		return toucheDPresse;
	}

	public void setToucheDPresse(boolean toucheDPresse) {
		this.toucheDPresse = toucheDPresse;
	}
	
	/* 
	 * Autres m�thodes de gestion des joueurs
	 */
	

	public void mettrePause()	// Mise en pause d'une partie
	{
		/* Il faut arr�ter tous les traitements, ne plus envoyer d'informations pour tracer ou afficher
		 * et attendre le signal pour enlever la pause
		 */
	}
	
	public void quitterPartie()	// Quitter la partie par un joueur
	{
		/* 
		 * V�rifier si le joueur quittant la partie est l'h�bergeur de la partie
		 * 		Si oui : il faut faire quitter tout le monde
		 * Si non : - changer l'�tat du joueur en mort
		 * 			- ne plus envoyer d'informations par rapport � ce joueur (liste joueur ayant quitt�s?)
		 * 
		 */
		
		
	}
	
	
	public void priseBonus()
	{
		/* V�rifier si les coordonn�es de la ligne touchent les coordonn�es d'un bonus
		 * Faire disparaitre le bonus du plateau
		 * Changer les param�tres du joueur concern�
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
