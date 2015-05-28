package polyFever.module.moteurDeJeu;

/*
 * // Enum�ration des diff�rents role d'un joueur
 * HOTE de la partie
 * simple CLIENT
 */

/**
 * Enum�ration Role, d�crivant le r�le d'un Joueur (@see Joueur) dans une partie
 * Le Joueur peut soit �tre :
 * 		HOTE : il est l'h�te de la partie, si il la quitte la partie s'arr�te
 * 		CLIENT : il est simple client de la partie, si il quitte la partie, elle continue sans lui
 * 
 * @author Fr�d�ric Llorca
 *
 */
public enum Role {

	HOTE,
	CLIENT,
	
}
