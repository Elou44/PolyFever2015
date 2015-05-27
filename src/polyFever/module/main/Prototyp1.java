package polyFever.module.main;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;

import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import org.lwjgl.input.Keyboard;
import polyFever.module.affichage.Affichage;
import polyFever.module.moteurDeJeu.*;
import polyFever.module.evenements.Evenements;

public class Prototyp1 extends PolyFever {
	
	public static void main(String[] args) {
		new Prototyp1().run(3, 2, true);
	}
	
	private Affichage affichage; // Classe Affichage
	private Partie partie;

	

	public Prototyp1() {
		super("Prototyp1", 900, 600, true); // on interdit le redimensionnement de la fenetre

		Partie partie = new Partie(); // Devra etre instanciée par le Menu
		Joueur j1 = new Joueur(partie, "joueurTest", Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT, 0);
        Evenements ev = new Evenements();
		partie.ajouterJoueur(j1,this);
		Joueur j2 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j2, this);
		ev.initControles(partie);
		
		System.out.println("Création de l'objet Affichage...");
		this.affichage = new Affichage();
		System.out.println("Affichage créé avec succès...");
		this.partie = partie;	// Sans ça j'ai un pointeur nul quand j'appelle ma méthode update :/ TON setPartie n'a pas l'air de bien définir la partie
		this.setPartie(partie); // Envoie de l'objet Partie à la classe PolyFever
		this.setEvenements(ev);

	}
	
	@Override
	public void init() {
		
		affichage.initMenu(this, partie);
		partie.initialiserPartie();
		affichage.initJeu(this, partie);
		
		
	}
	
	@Override
	public void render() {		
		//System.out.println("\n\n=================== NOUVELLE BOUCLE ===================\n\n");
		partie.update();
		
		affichage.dessinerMenu();
		
		affichage.dessinerJeu();
		
				
	}

}


