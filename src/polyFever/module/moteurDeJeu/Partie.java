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
	private static long temps;			// Variable mesurant le temps d'une partie
	
	// Constructeur
	public Partie()	// Par défaut
	{
		System.out.println("Instanciation d'un objet Partie...");
		this.scoreMax = 0;								// Score max initialiser à 0 mais à calculer
		this.nbJoueurs = 0;								// Nombre de joueurs initialisé à 0
		this.joueurs = new HashSet<Joueur>();			// Création de la liste des joueurs
		this.dimensionPlateau = 0.0f;					// Création du vecteur des dimensions du plateau
		this.bonusPresents = new ArrayList<Bonus>();	// Création de la liste des bonus
		Partie.temps = System.currentTimeMillis();		// Définition de l'heure de début de la partie
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
		
		// Calcul des positions de base des joueurs & définition du temps de traçage de trou
		for(Joueur e : joueurs)		// Boucle de parcours de la liste des joueurs
		{
			e.getPosition().set((float) Math.random() * dimensionPlateau, (float) Math.random() * dimensionPlateau, 1);	// Calcul de la position en x et y
			e.getLigne().setTpsTrou((float) (Math.random() * (3.0 - 1.5) + 1.5));	// Calcul du temps de traçage de trou
		}
	}
	
	public void repererCollisions()
	{
		/* Vérifier les coordonnées de chaque joueur; qu'elles soient pas égales
		 * Collision contre un mur du plateau de jeu (coord > plateau)
		 * Collisison avec un autre tracé
		 * 		- Si collision : - appeler la méthode modifier Etat pour mettre à jour l'état du joueur décédé
		 * 						 - appeler la méthode modifier score pour mettre à jour le score des joueurs
		 */
		
		// Parmis tous les joueurs de la partie
		for(Joueur e : joueurs)
		{
			// ### Collision plateau ###
			// Si la position du joueur en x ou en y, est supérieure ou égale à 1 ou inférieure ou égale à -1
			if( e.getPosition().x() >= 1 || e.getPosition().x() <= -1 || e.getPosition().y() >= 1 || e.getPosition().y() <= -1 )
			{
				// On modifie l'état du joueur concerné et on le passe à mort
				this.modifierEtat(e);
			}
		}
	}
	
	public void modifierScore()
	{
		/* Rajouter +1 aux scores des joueurs encore en vie
		 * Voir dans la liste des joueurs
		 * Voir leurs états
		 * et ceux qui sont en vie +1
		 */
		
		// Parmis les joueurs dans la partie
		for(Joueur e : joueurs)
		{
			// Si l'état du joueur est VIVANT
			if(e.getEtat() == Etat.VIVANT)
			{
				e.setScore(e.getScore()+1);	// Alors on augmente son score de +1
			}
		}
		
	}
	
	public void modifierEtat(Joueur mort)
	{
		/* Modifier l'état d'un joueur
		 * Pour passer de vivant | mort | quitté
		 */
		
		// Parmis les joueurs dans la partie
		for(Joueur e : joueurs)
		{
			// Si le joueur étudié est le joueur décédé
			if(e == mort)
			{
				e.setEtat(Etat.MORT);	// Alors on modifie son état en MORT
			}
		}
	}
	
	public void apparaitreBonus()
	{
		/* 
		 * Selon un calcul, instancier des objets bonus
		 * les ajouter dans le tableau des bonus présents
		 */
		
		//long delaiApparitionBonus;	// Délai d'apparition d'un bonus
		
		//if(/* Il est temps de faire apparaitre un bonus */)
		{
			// Je regarde parmis les bonus existants
			// J'en fais apparaitre un au hasard
			
			// Je définis sa position sur le plateau
		}
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
				joueur.setLigne(new Ligne(p)); // ça me semble inutile à priori , autant instancier la ligne dans le constructeur de Joueur, ça complique la compréhension por rien.
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
	
	public void repererBonus()
	{
		/* 
		 * Méthode qui devra repérer si un joueur rentre en contact avec bonus
		 * affectera le bonus au joueur
		 */
	}
	
	/*
	 * Méthode définissant tout ce qu'on fait à chaque tour dans une partie
	 */
	
	public void update()
	{
		// On commence par effectuer les mouvements des joueurs
		// OU ALORS C'EST DEJA FAIT DANS LE GAME LOOP ET FAUT PAS LE FAIRE LA ???
		
		// On repère si des joueurs sont en collision avec une trace ou un mur
		this.repererCollisions();	// Si collisions il y a, alors la méthode repererCollisions se charge de mettre à jour les scores et l'état des joueurs
		
		// On repère si des joueurs prennent un bonus
		//this.repererBonus();	// Si bonus pris il y a, alors la méthode repererBonus se charge de modifier les paramètres des lignes concernées et de vider le tableau des bonus présents
		
		// Voir si on fait apparaitre des bonus ou non
		//this.apparaitreBonus();
		
		// Mettre à jour le traçage des trous pour chaque joueur
		
		
	}

	@Override
	public String toString() {
		return "Partie [scoreMax=" + scoreMax + ", nbJoueurs=" + nbJoueurs
				+ ", joueurs=" + joueurs + ", dimensionPlateau="
				+ dimensionPlateau + ", bonusPresents=" + bonusPresents + "]";
	}
	
}
