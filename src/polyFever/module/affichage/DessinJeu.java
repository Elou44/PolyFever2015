package polyFever.module.affichage;

public class DessinJeu {
	
	private DessinPlateau dPlateau;
	private DessinScores dScores;
	
	
	public DessinJeu(int width, int height)
	{
		dPlateau = new DessinPlateau(width, height);
		dScores = new DessinScores();
	}
	
	public void dessiner(int vbo)
	{
		//System.out.println("	dessiner dJeu");
		dPlateau.dessiner(vbo);
		dScores.dessiner();
	}
	
}
