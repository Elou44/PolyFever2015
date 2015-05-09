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
	

	public Prototyp1() {
		super("Prototyp1", 1000, 1000, true); // on interdit le redimensionnement de la fenetre
		
		Partie partie = new Partie(); // Devra etre instanciée par le Menu
		Joueur j1 = new Joueur();
		partie.ajouterJoueur(j1,this);
		
		this.affichage = new Affichage(this, partie);
		this.setPartie(partie); // Envoie de l'objet Partie à la classe PolyFever

	}
	
	@Override
	public void init() {
		
		affichage.init();
		
	}
	
	@Override
	public void render() {		
		
		affichage.dessiner();

		
	}
}


