package polyFever.module.moteurDeJeu;

import polyFever.module.util.math.*;


/* 
 * Classe stockant les informations li�s � un joueur
 * Et g�rant les param�tres du joueur
 */

/**
 * Ceci est la classe Joueur
 * Classe stockant les informations li�s � un joueur
 * Et g�rant les param�tres du joueur
 * 
 * @param pseudo
 * 			Chaine de caract�res accueillant le pseudo du joueur
 * @param toucheG
 * 			Entier stockant la valeur de la touche clavier permettant au joueur de tourner � gauche
 * @param toucheD
 * 			Entier stockant la valeur de la touche clavier permettant au joueur de tourner � droite
 * @param score
 * 			Entier stockant le score du joueur
 * @param etat
 * 			Variable accueillant la valeur d'une �num�ration Etat, indiquant le statut du joueur, soit MORT, VIVANT ou QUITTE
 * @param type
 * 			Variable accueillant la valeur d'une �num�ration Role, indiquant le r�le du joueur dans la partie, soit HOTE ou CLIENT
 * @param ligne
 * 			Objet Ligne que le joueur contr�le
 * @param position
 * 			Vector3 indiquant la position du joueur sur le plateau de jeu, en x, en y et si il laisse une trace (z=1) ou non (z=0)
 * @param direction
 * 			Double indiquant la direction du joueur, en radians (entre 0 et 2PI)
 * @param toucheGPresse
 * 			Bool�en indiquant si la toucheG du joueur est d�clench� (true) ou relach�e (false)
 * @param toucheDPresse
 * 			Bool�en indiquant si la toucheD du joueur est d�clench� (true) ou relach�e (false)
 * @param partie
 * 			Objet Partie auquel est rattach� le joueur
 * @param grille
 * 			Entier donnant l'index de la sous grille du plateau de jeu ou se trouve actuellement le joueur
 * @param anciennePosition
 * 			Vector3 donnant la position ant�rieure � la position courante du joueur
 * @param DroiteJoueur
 * 			Vector4 donant les coordonn�es des points formant la droite de contact du joueur
 * @param ancienneDroiteJoueur
 * 			Vector4 donant les coordonn�es des pr�c�dent points formant la collision de contact du joueur
 * @param redimension
 * 			Bool�en indiquant si l'�paisseur de la ligne du joueur a �t� modifi�e
 * 
 * @author Fr�d�ric Llorca
 *
 */
public class Joueur {

	private String pseudo;				// Pseudo du joueur
	private int toucheG;				// Sa touche de jeu, tournant � gauche
	private int toucheD;				// Sa touche de jeu, tournant � droite
	private int score;					// Score du joueur, sur une partie
	private Etat etat;					// Etat du joueur
	// COMMENT DEFINIR LE ROLE ??? QUELLE INFORMATION JE VAIS RECEVOIR ? QUEL TYPE ?
	private Role type;					// Variable d�finissant le role du joueur, accueillant l'�num�ration
	private Ligne ligne;				// Objet Ligne control�e par le joueur
	private Vector3 position;			// Vector3 donnant la position actuelle du point du joueur et si la trace est un trou ou non
	private double direction;			// Direction du point du joueur (en radians)
	private boolean toucheGPresse;		// Bool�en disant si la toucheG est enfonc�e
	private boolean toucheDPresse;		// Bool�en disant si la toucheD est enfonc�e
	private Partie partie;				// Objet Partie auquel est rattach� le joueur
	private int grille;					// Entier donnant l'index de la sous grille du plateau de jeu ou se trouve actuellement le joueur
	private Vector3 anciennePosition;	// Position ant�rieure � la position courante du joueur
	private Vector4 droiteJoueur;		// Coordonn�es des points formant la droite de contact du joueur
	private Vector4 ancienneDroiteJoueur;	// Coordonn�es des pr�c�dent points formant la collision de contact du joueur
	private boolean redimension;		// Bool�en indiquant si l'�paisseur de la ligne du joueur a �t� modifi�e
	
	// Constructeur
	/**
	 * Constructeur d'un objet Joueur
	 * Prend en param�tre un objet Partie auquel rattach� le joueur
	 * Ce constructeur initialise les variables n�cessaires � l'utilisation d'un joueur
	 * 
	 * @param partie
	 */
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
		this.anciennePosition = new Vector3();
		this.ancienneDroiteJoueur = new Vector4();
		this.droiteJoueur = new Vector4();
		this.redimension = false;
	}

	/**
	 * Constructeur d'un objet Joueur
	 * Ce constructeur prend en param�tres un objet Partie, le pseudo du Joueur, ses touches de jeu ainsi que sa couleur
	 * 
	 * @param partie
	 * @param pseudo
	 * @param toucheG
	 * @param toucheD
	 * @param couleur
	 */
	public Joueur(Partie partie, String pseudo, int toucheG, int toucheD, int couleur)	// Avec param�tres
	{
		this(partie);
		System.out.println("Instanciation d'un objet Joueur (ap)...");
		this.pseudo = pseudo;
		this.toucheG = toucheG;
		this.toucheD = toucheD;
		//this.ligne.setCouleur(couleur);
	}

	// M�thodes
	
	/* 
	 * Assesseurs et mutateurs
	 */
	
	public boolean isRedimension() {
		return redimension;
	}

	public void setRedimension(boolean redimension) {
		this.redimension = redimension;
	}
	
	public Vector4 getAncienneDroiteJoueur() {
		return ancienneDroiteJoueur;
	}

	public void setAncienneDroiteJoueur(Vector4 ancienneDroiteJoueur) {
		this.ancienneDroiteJoueur = ancienneDroiteJoueur;
	}
	
	public Vector4 getDroiteJoueur() {
		return droiteJoueur;
	}

	public void setDroiteJoueur(Vector4 droiteJoueur) {
		this.droiteJoueur = droiteJoueur;
	}
	
	public Vector3 getAnciennePosition() {
		return anciennePosition;
	}

	public void setAnciennePosition(Vector3 anciennePosition) {
		this.anciennePosition = anciennePosition;
	}
	
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
		
	/**
	 * M�thode permettant � un joueur pr�sent dans la partie de la quitter sans pour autant arr�ter la partie enti�rement
	 * 
	 * Principe :
	 *		V�rifier si le joueur quittant la partie est l'h�bergeur de la partie
	 * 		Si oui : il faut faire quitter tout le monde
	 * 		Si non : - changer l'�tat du joueur en mort
	 * 			- ne plus envoyer d'informations par rapport � ce joueur (liste joueur ayant quitt�s?)
	 */
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

	/**
	 * M�thode mettant � jour l'attribut "grille" d'un Joueur selon sa position courante
	 * 
	 * Principe :
	 * 		Avec la position on regarde dans quelle sous grille il se trouve
	 * 		On modifie son attribut "grille"
	 */
	// M�thode mettant � jour l'attribut "grille" du joueur selon sa position
	public void majGrille()
	{
		// Si le curseur se situe dans la 0�me sous grille
		if(this.position.x() <= -0.5 && this.position.y() >= 0.5)
		{
			// Mise � jour de la sous grille actuelle ou se trouve le joueur
			this.setGrille(0);
		}
		// Si le curseur se situe dans la 1�re sous grille
		else if( (this.position.x() >= -0.5 && this.position.x() <= 0) && this.position.y() >= 0.5)
		{
			this.setGrille(1);
		}
		// Si le curseur se situe dans la 2�me sous grille
		else if( (this.position.x() >= 0 && this.position.x() <= 0.5) && this.position.y() >= 0.5)
		{
			this.setGrille(2);
		}
		// Si le curseur se situe dans la 3�me sous grille
		else if(this.position.x() >= 0.5 && this.position.y() >= 0.5)
		{
			this.setGrille(3);
		}
		// Si le curseur se situe dans la 4�me sous grille
		else if(this.position.x() <= -0.5 && (this.position.y() >= 0 && this.position.y() <= 0.5) )
		{
			this.setGrille(4);
		}
		// Si le curseur se situe dans la 5�me sous grille
		else if( (this.position.x() >= -0.5 && this.position.x() <= 0) && (this.position.y() >= 0 && this.position.y() <= 0.5) )
		{
			this.setGrille(5);
		}
		// Si le curseur se situe dans la 6�me sous grille
		else if( (this.position.x() >= 0 && this.position.x() <= 0.5) && (this.position.y() >= 0 && this.position.y() <= 0.5) )
		{
			this.setGrille(6);
		}
		// Si le curseur se situe dans la 7�me sous grille
		else if(this.position.x() >= 0.5 && (this.position.y() >= 0 && this.position.y() <= 0.5) )
		{
			this.setGrille(7);
		}
		// Si le curseur se situe dans la 8�me sous grille
		else if(this.position.x() <= -0.5 && (this.position.y() >= -0.5 && this.position.y() <= 0) )
		{
			this.setGrille(8);
		}
		// Si le curseur se situe dans la 9�me sous grille
		else if( (this.position.x() >= -0.5 && this.position.x() <= 0) && (this.position.y() >= -0.5 && this.position.y() <= 0) )
		{
			this.setGrille(9);
		}
		// Si le curseur se situe dans la 10�me sous grille
		else if( (this.position.x() >= 0 && this.position.x() <= 0.5) && (this.position.y() >= -0.5 && this.position.y() <= 0) )
		{
			this.setGrille(10);
		}
		// Si le curseur se situe dans la 11�me sous grille
		else if(this.position.x() >= 0.5 && (this.position.y() >= -0.5 && this.position.y() <= 0) )
		{
			this.setGrille(11);
		}
		// Si le curseur se situe dans la 12�me sous grille
		else if(this.position.x() <= -0.5 && this.position.y() <= -0.5)
		{
			this.setGrille(12);
		}
		// Si le curseur se situe dans la 13�me sous grille
		else if( (this.position.x() >= -0.5 && this.position.x() <= 0) && this.position.y() <= -0.5)
		{
			this.setGrille(13);
		}
		// Si le curseur se situe dans la 14�me sous grille
		else if( (this.position.x() >= 0 && this.position.x() <= 0.5) && this.position.y() <= -0.5)
		{
			this.setGrille(14);
		}
		// Si le curseur se situe dans la 15�me sous grille
		else if(this.position.x() >= 0.5 && this.position.y() <= -0.5)
		{
			this.setGrille(15);
		}
	}
	
	/**
	 * M�thode renvoyant une chaine de caract�res d�crivant un objet Joueur
	 */
	@Override
	public String toString() {
		return "Joueur [pseudo=" + pseudo + ", toucheG=" + toucheG
				+ ", toucheD=" + toucheD + ", score=" + score + ", etat="
				+ etat + ", type=" + type + ", ligne=" + ligne + ", position.x="
				+ position.x() + ", position.y="+ position.y() + ", direction=" + direction + "]";
	}
	
}
