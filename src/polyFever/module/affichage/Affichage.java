package polyFever.module.affichage;

public class Affichage {
	
	private DessinJeu dJeu;
	private DessinMenu dMenu;
	
	public Affichage()
	{
		dJeu = new DessinJeu();
		dMenu = new DessinMenu();
	}
	
	
	public void dessiner()
	{
		System.out.println("dessiner Affichage");
		dJeu.dessiner();
		dMenu.dessiner();
	}

}
