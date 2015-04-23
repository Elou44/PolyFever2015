package polyFever.module.moteurDeJeu;

public class Joueur {

	private String pseudo;
	private String toucheG;
	private String toucheD;
	private int score;
	private String etat;
	private String role;
	private Ligne ligne;
	
	// Constructeur
	public Joueur()	// Par d�faut
	{
		this.pseudo = null;
		this.toucheG = "FLECHE_GAUCHE";
		this.toucheD = "FLECHE_DROITE";
		this.score = 0;
		this.etat = "Vivant";
		this.role = "Client";
	}
	
	public Joueur(String pseudo, String toucheG, String toucheD, String role)	// Avec param�tres
	{
		this.pseudo = pseudo;
		this.toucheG = toucheG;
		this.toucheD = toucheD;
		this.score = 0;
		this.etat = "Vivant";
		this.role = role;
	}

	// M�thodes
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public mettrePause()	// Mise en pause d'une partie
	{
		/* Il faut arr�ter tous les traitements, ne plus envoyer d'informations pour tracer ou afficher
		 * et attendre le signal pour enlever la pause
		 */
	}
	
	public quitterPartie()	// Quitter la partie par un joueur
	{
		/* V�rifier si le joueur quittant la partie est l'h�bergeur de la partie
		 * 		Si oui : il faut faire quitter tout le monde
		 * Si non : - changer l'�tat du joueur en mort
		 * 			- ne plus envoyer d'informations par rapport � ce joueur (liste joueur ayant quitt�s?)
		 * 
		 */
	}
		
}
