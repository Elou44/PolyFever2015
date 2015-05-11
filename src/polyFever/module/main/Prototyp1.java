package polyFever.module.main;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import polyFever.module.affichage.Affichage;
import polyFever.module.moteurDeJeu.*;

public class Prototyp1 extends PolyFever {
		
	public static void main(String[] args) {
		new Prototyp1().run(3, 2, true);
	}
	
	private Affichage affichage; // Classe Affichage
	private Partie partie;
	private int i = 1;
	

	public Prototyp1() {
		super("Prototyp1", 300, 300, true); // on interdit le redimensionnement de la fenetre

		Partie partie = new Partie(); // Devra etre instanciée par le Menu
		Joueur j1 = new Joueur(partie);
		partie.ajouterJoueur(j1,this);
		
		this.affichage = new Affichage(this, partie);
		this.partie = partie;	// Sans ça j'ai un pointeur nul quand j'appelle ma méthode update :/ TON setPartie n'a pas l'air de bien définir la partie
		this.setPartie(partie); // Envoie de l'objet Partie à la classe PolyFever

	}
	
	@Override
	public void init() {
		
		affichage.init();
		
	}
	
	@Override
	public void render() {		
		//System.out.println("\n\n=================== NOUVELLE BOUCLE ===================\n\n");
		if(i == 1)
		{
			partie.initialiserPartie();
			i++;
		}
		affichage.dessiner();
		try
		{
			partie.update();
		}
		catch (Exception NullPointerExcpeption)
		{
			System.out.println("Pointeur nul");
		}
		
		
	}

}


