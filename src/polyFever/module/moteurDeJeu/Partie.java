package polyFever.module.moteurDeJeu;
import polyFever.module.main.*;

/* 
 * Classe stockant les informations liés à une partie
 * Et gérant les paramètres de la partie
 */

import java.util.*;

public class Partie {

	private int scoreMax;				// Score à atteindre pour terminer une partie
	private int nbJoueurs;				// Nombre de joueurs présents dans la partie
	private Set<Joueur> joueurs;		// Liste des joueurs de la partie (objet Joueur)
	private float dimensionPlateau;		// Dimensions du plateau de jeu
	private List<Bonus> bonusPresents;	// Liste des bonus présents sur le plateau de jeu
	
	// Constructeur
	public Partie()	// Par défaut
	{
		System.out.println("Instanciation d'un objet Partie...");
		this.scoreMax = 0;								// Score max initialiser à 0 mais à calculer
		this.nbJoueurs = 0;								// Nombre de joueurs initialisé à 0
		this.joueurs = new HashSet<Joueur>();			// Création de la liste des joueurs
		this.dimensionPlateau = 0.0f;						// Création du vecteur des dimensions du plateau
		this.bonusPresents = new ArrayList<Bonus>();	// Création de la liste des bonus
	}

	// Méthodes
	
	/*
	 * Assesseurs et mutateurs
	 */
	
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

	public Set<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(Set<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	public float getDimensionPlateau() {
		return dimensionPlateau;
	}

	public void setDimensionPlateau(float dimensionPlateau) {
		this.dimensionPlateau = dimensionPlateau;
	}

	public List<Bonus> getBonusPresents() {
		return bonusPresents;
	}

	public void setBonusPresents(List<Bonus> bonusPresents) {
		this.bonusPresents = bonusPresents;
	}
	
	/*
	 * Autres méthodes de gestion de la partie
	 */
	
	public void initialiserPartie()
	{
		System.out.println("Initialisation de la partie...");
		// Calcul du scoreMax
		scoreMax = (nbJoueurs-1) * 10;
		
		// Calcul des dimensions du plateau
		dimensionPlateau = nbJoueurs * 100;
		
		// Calcul des positions de base des joueurs		
		for(Joueur e : joueurs)		// Boucle de parcours de la liste des joueurs
		{
			e.getPosition().set((float) Math.random() * dimensionPlateau, (float) Math.random() * dimensionPlateau);	// Calcul de la position en x et y
		}
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
	
	public void ajouterJoueur(Joueur joueur, PolyFever p)
	{
		// Incrémentation du nombre de joueurs
		nbJoueurs = nbJoueurs + 1;
		
		// Ajout du joueur dans la liste des joueurs présents dans la partie
		joueurs.add(joueur);
		
		Iterator<Joueur> i = joueurs.iterator();
		
		// Instanciation d'une ligne pour ce joueur
		while(i.hasNext())
		{
			Joueur player = i.next();
			if(player == joueur)
			{
				joueur.setLigne(new Ligne(p));
			}
		}
		System.out.println("\t\tANCIEN : "+joueur.getLigne().toString());
		
		// Connection de la ligne avec le joueur
		try {
			joueur.getLigne().setJoueur(joueur);
		}
		catch (StackOverflowError e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("\t\t\tNEW : "+joueur.getLigne().toString());

	}

	@Override
	public String toString() {
		return "Partie [scoreMax=" + scoreMax + ", nbJoueurs=" + nbJoueurs
				+ ", joueurs=" + joueurs + ", dimensionPlateau="
				+ dimensionPlateau + ", bonusPresents=" + bonusPresents + "]";
	}
	
}
