package polyFever.module.moteurDeJeu;

public class Ligne {

	private String couleur;
	private Joueur joueur;
	private int vitesse;
	private int epaisseur;
	private int courbe;
	private int tpsEnVie;
	//private List trace;
	
	// Constructeur
	public Ligne()	// Par d�faut
	{
		this.couleur = "white";
		this.joueur = null;
		this.vitesse = 1;
		this.epaisseur = 1;
		this.courbe = 45;
		this.tpsEnVie = 0;
	}
	
	public Ligne(String couleur, Joueur joueur)	// Avec param�tres
	{
		super();
		this.couleur = couleur;
		this.joueur = joueur;
	}
	
	// M�thodes
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
	
	public void ajoutCoord(int x, int y)
	{
		/* Ajouter ces coordonn�es au tableau des coordonn�es
		 */
	}
	
	public void tournerDroite(Joueur joueur)
	{
		/* Calculer les nouvelles coordonn�es
		 * Ajouter les nouvelles coordonn�es au tableau des coord
		 * Envoyer ces coordonn�es � l'affichage
		 */
	}
	
	public void tournerGauche(Joueur joueur)
	{
		/* Calculer les nouvelles coordonn�es
		 * Ajouter les nouvelles coordonn�es au tableau des coord
		 * Envoyer ces coordonn�es � l'affichage
		 */
	}
	
	public void tracerTrou()
	{
		/* M�thode calculant le moment pour un joueur de tracer un trou
		 * Si trace trou ne pas �crire dans le tableau des coordonn�es des lignes !
		 * Juste changer les coordonn�es pos_x et pos_y du joueur
		 */
	}
	
	public void priseBonus()
	{
		/* V�rifier si les coordonn�es de la ligne touchent les coordonn�es d'un bonus
		 * Faire disparaitre le bonus du plateau
		 * Changer les param�tres du joueur concern�
		 */
	}
	
}
