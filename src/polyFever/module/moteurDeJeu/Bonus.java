package polyFever.module.moteurDeJeu;

import java.util.List;

public abstract class Bonus {

	protected String nom;
	public enum couleur
	{
		rouge,
		vert,
	}
	public List<Integer> coordonnees;
	protected Joueur joueur;
	
	// Méthodes
	public void modifierParametres(){};
	
}
