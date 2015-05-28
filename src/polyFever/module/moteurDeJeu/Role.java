package polyFever.module.moteurDeJeu;

/*
 * // Enumération des différents role d'un joueur
 * HOTE de la partie
 * simple CLIENT
 */

/**
 * Enumération Role, décrivant le rôle d'un Joueur (@see Joueur) dans une partie
 * Le Joueur peut soit être :
 * 		HOTE : il est l'hôte de la partie, si il la quitte la partie s'arrête
 * 		CLIENT : il est simple client de la partie, si il quitte la partie, elle continue sans lui
 * 
 * @author Frédéric Llorca
 *
 */
public enum Role {

	HOTE,
	CLIENT,
	
}
