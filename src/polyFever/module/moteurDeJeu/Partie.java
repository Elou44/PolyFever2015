package polyFever.module.moteurDeJeu;

public class Partie {

	private int scoreMax;
	private int nbJoueurs;
	private Hashset<int> joueurs;
	private int longueurPlateau;
	private int largeurPlateau;
	private Bonus bonusPresents;	// Liste des bonus pr�sents sur le plateau de jeu (liste normale peut y avoir des doublons)
	
	// Constructeur
	public Partie()	// Par d�faut
	{
		this.scoreMax = 0;
		this.nbJoueurs = 0;
		this.longueurPlateau = 980;
		this.largeurPlateau = 980;
	}

	// M�thodes
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
		/* V�rifier les coordonn�es de chaque joueur; qu'elles soient pas �gales
		 * Collision contre un mur du plateau de jeu (coord > plateau)
		 * Collisison avec un autre trac�
		 * 		- Si collision : - changer l'�tat du joueur mort
		 * 						 - 
		 */
	}
	
	public void calculScoreMax()
	{
		/* Calculer le score maximum � atteindre selon le nombre de joueurs
		 * Changer valeur de l'attribut scoreMax
		 */
	}
	
	public void definitionPositionsBase()
	{
		/* Calculer les positions de base des joueurs
		 * Calculer un x et un y
		 * Changer la valeur des coordonn�es de chaque joueur
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
		 * Voir leurs �tats
		 * et ceux qui sont en vie
		 */
	}
	
	public void modifierEtat()
	{
		/* Modifier l'�tat d'un joueur
		 * Pour passer de vivant | mort | quitt�
		 */
	}
	
	public void apparaitreBonus()
	{
		/* Selon un calcul, instancier des objets bonus
		 * les ajouter dans le tableau des bonus pr�sents
		 * l'affichage � acc�s au tableau des bonus pr�sents donc coooool
		 */
	}
	
}
