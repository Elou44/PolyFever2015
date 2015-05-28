package polyFever.module.moteurDeJeu;

/*
 * Enum�ration des diff�rents types de port�e d'un bonus
 * ROUGE = affecte les autres joueurs
 * VERT = affecte le joueur ayant activ� ce bonus
 */

/**
 * Enum�ration d�crivant la port�e d'un Bonus (@see Bonus)
 * Un Bonus peut soit avoir une port�e :
 * 		ROUGE : ce Bonus affecte tous les joueurs adverses, mais pas le joueur ayant pris ce Bonus
 * 		VERT : ce Bonus n'affecte que le Joueur ayant pris ce Bonus
 * 
 * @author Fr�d�ric Llorca
 *
 */
public enum Portee {

		ROUGE,
		VERT,
	
}
