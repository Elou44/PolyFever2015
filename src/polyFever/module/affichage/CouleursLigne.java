package polyFever.module.affichage;
import polyFever.module.util.math.*;

/**
 * Classe permettant d'accéder aux différentes couleurs disponible pour les joueurs
 * @author Elouarn Lainé
 *
 */
public class CouleursLigne {
	
	/**
	 * Tableau de {@link Vector3} contenant les couleurs disponibles.
	 */
	public static final Vector3 tabCouleurs[] = new Vector3[] {
			
			new Vector3(1.0f,0.0f,0.0f), // Rouge
			new Vector3(0.0f,1.0f,0.0f), // Vert
			new Vector3(0.0f,0.0f,1.0f), // Bleu
			new Vector3(1.0f,1.0f,0.0f) // jaune
			
	};

}
