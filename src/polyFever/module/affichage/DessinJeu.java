package polyFever.module.affichage;

public class DessinJeu {
	
	private DessinPlateau dPlateau;
	private DessinScores dScores;
	
	
	public DessinJeu()
	{
		dPlateau = new DessinPlateau();
		dScores = new DessinScores();
	}
	
	public void dessiner()
	{
		System.out.println("	dessiner dJeu");
		dPlateau.dessiner();
		dScores.dessiner();
	}
	
}
