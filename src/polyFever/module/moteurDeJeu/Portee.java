package polyFever.module.moteurDeJeu;

/*
 * Enumération des différents types de portée d'un bonus
 * ROUGE = affecte les autres joueurs
 * VERT = affecte le joueur ayant activé ce bonus
 */

/**
 * Enumération décrivant la portée d'un Bonus (@see Bonus)
 * Un Bonus peut soit avoir une portée :
 * 		ROUGE : ce Bonus affecte tous les joueurs adverses, mais pas le joueur ayant pris ce Bonus
 * 		VERT : ce Bonus n'affecte que le Joueur ayant pris ce Bonus
 * 
 * @author Frédéric Llorca
 *
 */
public enum Portee {

		ROUGE,
		VERT,
	
}
