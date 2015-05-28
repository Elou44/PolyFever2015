package polyFever.module.moteurDeJeu;

/*
 * Etat d'un joueur 
 * MORT
 * VIVANT
 * QUITTE la partie
 */

/**
 * Enumération Etat, décrivant le statut d'un Joueur (@see Joueur)
 * Le Joueur peut soit être :
 * 		MORT : il ne peut plus se déplacer sur le plateau de jeu avant un nouveau round
 * 		VIVANT : une nouvelle position est calculée à chaque mouvement
 * 		QUITTE : ce Joueur a quitté la partie, son curseur et sa ligne ne sont plus tracés
 * 
 * @author Frédéric Llorca
 *
 */
public enum Etat {

	MORT,
	VIVANT,
	QUITTE,
	
}
