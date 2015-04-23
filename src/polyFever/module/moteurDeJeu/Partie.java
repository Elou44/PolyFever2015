package polyFever.module.moteurDeJeu;

public class Partie {

	private int scoreMax;
	private int nbJoueurs;
	private Hashset<int> joueurs;
	private int longueurPlateau;
	private int largeurPlateau;
	private Bonus bonusPresents;	// Liste des bonus présents sur le plateau de jeu (liste normale peut y avoir des doublons)
	
	// Constructeur
	public Partie()	// Par défaut
	{
		this.scoreMax = 0;
		this.nbJoueurs = 0;
		this.longueurPlateau = 980;
		this.largeurPlateau = 980;
	}

	// Méthodes
	public int getScoreMax() {
		return scoreMax;
	}

	public void setScoreMax(int scoreMax) {
		this.scoreMax = scoreMax;
	}

	public int getNbJoueurs() {
		return nbJoueurs;
	}

	public void setNbJoueurs(int nbJoueurs) {
		this.nbJoueurs = nbJoueurs;
	}

	public int getLongueurPlateau() {
		return longueurPlateau;
	}

	public void setLongueurPlateau(int longueurPlateau) {
		this.longueurPlateau = longueurPlateau;
	}

	public int getLargeurPlateau() {
		return largeurPlateau;
	}

	public void setLargeurPlateau(int largeurPlateau) {
		this.largeurPlateau = largeurPlateau;
	}
	
	public void repererCollisions()
	{
		/* Vérifier les coordonnées de chaque joueur; qu'elles soient pas égales
		 * Collision contre un mur du plateau de jeu (coord > plateau)
		 * Collisison avec un autre tracé
		 * 		- Si collision : - changer l'état du joueur mort
		 * 						 - 
		 */
	}
	
	public void calculScoreMax()
	{
		/* Calculer le score maximum à atteindre selon le nombre de joueurs
		 * Changer valeur de l'attribut scoreMax
		 */
	}
	
	public void definitionPositionsBase()
	{
		/* Calculer les positions de base des joueurs
		 * Calculer un x et un y
		 * Changer la valeur des coordonnées de chaque joueur
		 */
	}
	
	public void calculerTaillePlateau()
	{
		/* Calculer la taille du plateau selon le nombre de joueurs
		 * Changer la valeur des attributs de l'obhet plateau
		 */
	}
	
	public void modifierScore()
	{
		/* Rajouter +1 aux scores des joueurs encore en vie
		 * Voir dans la liste des joueurs
		 * Voir leurs états
		 * et ceux qui sont en vie
		 */
	}
	
	public void modifierEtat()
	{
		/* Modifier l'état d'un joueur
		 * Pour passer de vivant | mort | quitté
		 */
	}
	
	public void apparaitreBonus()
	{
		/* Selon un calcul, instancier des objets bonus
		 * les ajouter dans le tableau des bonus présents
		 * l'affichage à accès au tableau des bonus présents donc coooool
		 */
	}
	
}
