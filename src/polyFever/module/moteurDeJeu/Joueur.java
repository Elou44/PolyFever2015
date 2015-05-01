package polyFever.module.moteurDeJeu;

/* 
 * Classe stockant les informations li�s � un joueur
 * Et g�rant les param�tres du joueur
 */

public class Joueur {

	public String pseudo;			// Pseudo du joueur
	// ??? TYPE DES TOUCHES
	private String toucheG;			// Sa touche de jeu, tournant � gauche
	private String toucheD;			// Sa touche de jeu, tournant � droite
	public int score;				// Score du joueur, sur une partie
	public String etat;				// Etat du joueur (mort, vivant, quitt� la partie)
	private enum Role				// Enum�ration des diff�rents role d'un joueur, soit hote de la partie ou simple client
	{
		HOTE,
		CLIENT,
	}
	// COMMENT DEFINIR LE ROLE ??? QUELLE INFORMATION JE VAIS RECEVOIR ? QUEL TYPE ?
	private Role type;				// Variable d�finissant le role du joueur, accueillant l'�num�ration
	public Ligne ligne;				// Objet Ligne control�e par le joueur
	public Vector2 position;		// Vector2 donnant la position actuelle du point du joueur
	public double direction;		// Direction du point du joueur (en radians)
	
	// Constructeur
	public Joueur()	// Par d�faut
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
	
	public Joueur(String pseudo, String toucheG, String toucheD)	// Avec param�tres
	{
		this();
		System.out.println("Instanciation d'un objet Joueur (ap)...");
		this.pseudo = pseudo;
		this.toucheG = toucheG;
		this.toucheD = toucheD;
	}

	// M�thodes
	
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
		/* V�rifier si le joueur quittant la partie est l'h�bergeur de la partie
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
				+ position.x + ", position.y="+ position.y + ", direction=" + direction + "]";
	}
	
}
