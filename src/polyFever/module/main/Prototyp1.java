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

		Partie partie = new Partie(this); // Devra etre instanciée par le Menu
		Joueur j1 = new Joueur(partie, "joueurTest", Keyboard.KEY_LEFT, Keyboard.KEY_RIGHT);
        Evenements ev = new Evenements(this);
		partie.ajouterJoueur(j1,this);
		Joueur j2 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z);
		partie.ajouterJoueur(j2, this);
		Joueur j3 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z);
		partie.ajouterJoueur(j3, this);
		/*Joueur j4 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j4, this);
		Joueur j5 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j5, this);
		Joueur j6 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j6, this);
		Joueur j7 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j7, this);
		Joueur j8 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j8, this);
		Joueur j9 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j9, this);
		Joueur j10 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j10, this);
		Joueur j11 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j11, this);
		Joueur j12 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j12, this);
		Joueur j13 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j13, this);
		Joueur j14 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j14, this);
		Joueur j15 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j15, this);
		Joueur j16 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j16, this);
		Joueur j17 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j17, this);
		Joueur j18 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j18, this);
		Joueur j19 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j19, this);
		Joueur j20 = new Joueur(partie, "Joueur test2", Keyboard.KEY_A, Keyboard.KEY_Z, 0);
		partie.ajouterJoueur(j20, this);*/
		ev.initControles(partie);
		
		System.out.println("Création de l'objet Affichage...");
		this.affichage = new Affichage();
		System.out.println("Affichage créé avec succès...");
		this.partie = partie;	// Sans ça j'ai un pointeur nul quand j'appelle ma méthode update :/ TON setPartie n'a pas l'air de bien définir la partie
		this.setPartie(partie); // Envoie de l'objet Partie à la classe PolyFever
		this.setEvenements(ev);
		this.setAffichage(affichage);
		

	}
	
	@Override
	public void init() {
		
		affichage.initMenu(this);
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


