package polyFever.module.affichage;

public class DessinPlateau {
	
	private DessinBonus dBonus;
	private DessinJoueur dJoueur;
	private DessinLigne dLigne;
	
	public DessinPlateau(int width, int height)
	{
		dBonus = new DessinBonus();
		dJoueur = new DessinJoueur();
		dLigne = new DessinLigne(width, height);
	}
	
	public void dessiner(int vbo)
	{
		//System.out.println("		dessiner dPlateau");
		dBonus.dessiner();
		dJoueur.dessiner();
		dLigne.dessiner(vbo);
	}

}
