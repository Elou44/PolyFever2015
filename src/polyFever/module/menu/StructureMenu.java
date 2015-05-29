package polyFever.module.menu;
import polyFever.module.main.*;

public class StructureMenu {
	private Menu curMenu;
	
	/**
	 * Constructeur de la structure du menu
	 * L'attribut curMenu représente le menu courant qui permet de naviguer entre les menus créés
	 * Créé également les boutons associés
	 */
	
	public StructureMenu(PolyFever p) {
		
		Menu home = new Menu(p, "Home");
		home.setPere(home);
		
		Menu play = new Menu(p, "Play");
		home.addFils(play);
		play.setPere(home);
		
		Menu settings = new Menu(p, "Settings");
		home.addFils(settings);
		settings.setPere(home);
		
		MenuFeuille credit = new MenuFeuille(p, "Credit","LLORCA Frederic\n LAINE Elouarn\n GITARD Valentin\n PERSON Ambre");
		home.addFils(credit);
		credit.setPere(home);
		
		Menu quit = new Menu(p, "Quit");
		home.addFils(quit);
		quit.setPere(home);
		
		Menu local = new MenuPlay(p, "Local Multi");
		play.addFils(local);
		local.setPere(play);
		
		Menu lan = new Menu(p, "LAN Multi");
		play.addFils(lan);
		lan.setPere(play);		
		
		Menu host = new Menu(p, "Héberger");
		lan.addFils(host);
		host.setPere(lan);
		
		Menu join = new Menu(p, "Rejoindre");
		lan.addFils(join);
		join.setPere(lan);
		
		MenuPlay partie = new MenuPlay(p, "Jeu");
		local.addFils(partie);
		partie.setPere(local);
		
		
		BoutonMenu bPlay = new BoutonMenu(home,play,0f,0.4f,0.5f,0.1f);
		BoutonMenu bSettings = new BoutonMenu(home,settings,0f,0f,0.5f,0.1f);
		BoutonMenu bCredits = new BoutonMenu(home,credit,0f,-0.4f,0.5f,0.1f);
		BoutonMenu bQuit = new BoutonMenu(home,quit,0f,-0.8f,0.5f,0.1f);
		BoutonRetour bRetHome = new BoutonRetour(home,-0.8f,-0.8f,0.1f,0.1f);

		home.addBouton(bPlay);
		home.addBouton(bSettings);
		home.addBouton(bCredits);
		home.addBouton(bQuit);
		home.addBouton(bRetHome);

		BoutonMenu bLocal = new BoutonMenu(play,local,0f,0.4f,0.5f,0.1f);
		BoutonMenu bMulti = new BoutonMenu(play,lan,0f,0f,0.5f,0.1f);
		BoutonRetour bRetPlay = new BoutonRetour(play,-0.8f,-0.8f,0.1f,0.1f);
		
		play.addBouton(bLocal);
		play.addBouton(bMulti);
		play.addBouton(bRetPlay);

		BoutonMenuPlay bJouer = new BoutonMenuPlay(local,partie,0f,0.4f,0.5f,0.1f);
		BoutonRetour bRetJouer = new BoutonRetour(partie,-0.8f,-0.8f,0.1f,0.1f);

		local.addBouton(bJouer);
		local.addBouton(bRetJouer);

		

		this.curMenu = home;
	}

	/**
	 * Methode permettant de récupérer le menu courant
	 * @return Menu courant
	 */
	
	public Menu getCurMenu() {
		return curMenu;
	}


	
	
	
}
