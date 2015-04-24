package polyFever.module.affichage;

public class DessinPlateau {
	
	private DessinBonus dBonus;
	private DessinJoueur dJoueur;
	private DessinLigne dLigne;
	
	public DessinPlateau()
	{
		dBonus = new DessinBonus();
		dJoueur = new DessinJoueur();
		dLigne = new DessinLigne();
	}
	
	public void dessiner()
	{
		System.out.println("		dessiner dPlateau");
		dBonus.dessiner();
		dJoueur.dessiner();
		dLigne.dessiner();
	}

}
