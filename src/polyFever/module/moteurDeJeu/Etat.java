package polyFever.module.moteurDeJeu;

/*
 * Etat d'un joueur 
 * MORT
 * VIVANT
 * QUITTE la partie
 */

/**
 * Enum�ration Etat, d�crivant le statut d'un Joueur (@see Joueur)
 * Le Joueur peut soit �tre :
 * 		MORT : il ne peut plus se d�placer sur le plateau de jeu avant un nouveau round
 * 		VIVANT : une nouvelle position est calcul�e � chaque mouvement
 * 		QUITTE : ce Joueur a quitt� la partie, son curseur et sa ligne ne sont plus trac�s
 * 
 * @author Fr�d�ric Llorca
 *
 */
public enum Etat {

	MORT,
	VIVANT,
	QUITTE,
	
}
