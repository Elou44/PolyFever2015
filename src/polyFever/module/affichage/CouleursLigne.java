package polyFever.module.affichage;
import polyFever.module.util.math.*;

/**
 * Classe permettant d'acc�der aux diff�rentes couleurs disponible pour les joueurs
 * @author Elouarn Lain�
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
