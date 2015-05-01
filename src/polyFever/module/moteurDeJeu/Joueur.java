package polyFever.module.moteurDeJeu;

/* 
 * Classe stockant les informations liés à un joueur
 * Et gérant les paramètres du joueur
 */

public class Joueur {

	public String pseudo;			// Pseudo du joueur
	// ??? TYPE DES TOUCHES
	private String toucheG;			// Sa touche de jeu, tournant à gauche
	private String toucheD;			// Sa touche de jeu, tournant à droite
	public int score;				// Score du joueur, sur une partie
	public String etat;				// Etat du joueur (mort, vivant, quitté la partie)
	private enum Role				// Enumération des différents role d'un joueur, soit hote de la partie ou simple client
	{
		HOTE,
		CLIENT,
	}
	// COMMENT DEFINIR LE ROLE ??? QUELLE INFORMATION JE VAIS RECEVOIR ? QUEL TYPE ?
	private Role type;				// Variable définissant le role du joueur, accueillant l'énumération
	public Ligne ligne;				// Objet Ligne controlée par le joueur
	public Vector2 position;		// Vector2 donnant la position actuelle du point du joueur
	public double direction;		// Direction du point du joueur (en radians)
	
	// Constructeur
	public Joueur()	// Par défaut
	{
		System.out.println("Instanciation d'un objet Joueur (sp)...");
		this.pseudo = null;
		this.toucheG = "FLECHE_GAUCHE";
		this.toucheD = "FLECHE_DROITE";
		this.score = 0;
		this.etat = "Vivant";
		this.type = Role.CLIENT;
		this.position = new Vector2();
		this.direction = 3*Math.PI/2;
	}
	
	public Joueur(String pseudo, String toucheG, String toucheD)	// Avec paramètres
	{
		this();
		System.out.println("Instanciation d'un objet Joueur (ap)...");
		this.pseudo = pseudo;
		this.toucheG = toucheG;
		this.toucheD = toucheD;
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

	public String getToucheG() {
		return toucheG;
	}

	public void setToucheG(String toucheG) {
		this.toucheG = toucheG;
	}

	public String getToucheD() {
		return toucheD;
	}

	public void setToucheD(String toucheD) {
		this.toucheD = toucheD;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
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

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = direction;
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
		/* Vérifier si le joueur quittant la partie est l'hébergeur de la partie
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
				+ position.x + ", position.y="+ position.y + ", direction=" + direction + "]";
	}
	
}
