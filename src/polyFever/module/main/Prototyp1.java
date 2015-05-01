package polyFever.module.main;

import polyFever.module.affichage.Affichage;
import polyFever.module.moteurDeJeu.Partie;

public class Prototyp1 extends PolyFever {
		
	public static void main(String[] args) {
		new Prototyp1().run(3, 2, true);
	}
	
	private Affichage affichage; // Classe Affichage
	private Partie partie;

	public Prototyp1() {
		super("Prototyp1 (Early Access) WIP - Hello Rectangle Mother Fuckers !!!!!!", 500, 500, false); // on interdit le redimensionnement de la fenetre
		
		this.partie = new Partie(); // Devra etre instanciée par le Menu
		
		this.affichage = new Affichage(super.getWIDTH(), super.getHEIGHT(), this, partie);
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


