package polyFever.module.moteurDeJeu;
import polyFever.module.main.*;

/* 
 * Classe stockant les informations li�s � une partie
 * Et g�rant les param�tres de la partie
 */

import java.util.*;

public class Partie {

	private int scoreMax;				// Score � atteindre pour terminer une partie
	private int nbJoueurs;				// Nombre de joueurs pr�sents dans la partie
	private Set<Joueur> joueurs;		// Liste des joueurs de la partie (objet Joueur)
	private float dimensionPlateau;		// Dimensions du plateau de jeu
	private List<Bonus> bonusPresents;	// Liste des bonus pr�sents sur le plateau de jeu
	
	// Constructeur
	public Partie()	// Par d�faut
	{
		System.out.println("Instanciation d'un objet Partie...");
		this.scoreMax = 0;								// Score max initialiser � 0 mais � calculer
		this.nbJoueurs = 0;								// Nombre de joueurs initialis� � 0
		this.joueurs = new HashSet<Joueur>();			// Cr�ation de la liste des joueurs
		this.dimensionPlateau = 0.0f;						// Cr�ation du vecteur des dimensions du plateau
		this.bonusPresents = new ArrayList<Bonus>();	// Cr�ation de la liste des bonus
	}

	// M�thodes
	
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
	 * Autres m�thodes de gestion de la partie
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
		/* V�rifier les coordonn�es de chaque joueur; qu'elles soient pas �gales
		 * Collision contre un mur du plateau de jeu (coord > plateau)
		 * Collisison avec un autre trac�
		 * 		- Si collision : - changer l'�tat du joueur mort
		 * 						 - 
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
	
	public void ajouterJoueur(Joueur joueur, PolyFever p)
	{
		// Incr�mentation du nombre de joueurs
		nbJoueurs = nbJoueurs + 1;
		
		// Ajout du joueur dans la liste des joueurs pr�sents dans la partie
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
