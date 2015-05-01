package polyFever.module.moteurDeJeu;

/**
 * 
 * @author Freddy <3
 * CECI EST UN MAIN TEST POUR LA PARTIE MOTEUR DE JEU
 * Ici se déroule des expériences douteuses sur la manipulation de lignes et de joueurs...
 * 
 * EN COURS :
 * 		Test sur les coordonnées des nouvelles positions renvoyées
 *
 */

public class Main {
	
	public static void main (String [] args)
	{
		System.out.println("\t1 - ***");
		
		Partie game = new Partie();
		
		System.out.println(game.toString());
		
		System.out.println("\n\t2 - ***");
		
		Joueur J1 = new Joueur("Red", "GAUCHE", "DROITE");
		
		System.out.println(J1.toString());
		
		System.out.println("\n\t3 - ***");
		
		game.ajouterJoueur(J1);
		
		System.out.println(game.toString());
		
		System.out.println("\n\t4 - ***");
		
		for(int i = 0; i < 3; i++)
		{
			J1.ligne.pasTourner(J1);
			System.out.println("\n========================\n");
			if(i == 1)
			{
				J1.ligne.tournerDroite(J1);
			}
		}
		
	}

}
