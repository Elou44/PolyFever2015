package polyFever.module.menu;
import polyFever.module.main.*;

public class StructureMenu {
	private Menu curMenu;
	private MenuPlay m_partie;
	private Menu m_home;
	
	
	/**
	 * Constructeur de la structure du menu
	 * L'attribut curMenu représente le menu courant qui permet de naviguer entre les menus créés
	 * Créé également les boutons associés
	 */
	
	public StructureMenu(PolyFever p) {
		
		/*
		 * Indice des images : 
		 * 1 : images/menu_theme_4pipes.png
		 * 2 : images/menu_theme_2pipes.png
		 * 
		 * 1 : images/title_PolyFever.png
		 * 2 : images/title_Play.png
		 * 3 : images/title_LAN.png
		 * 
		 * 1 : images/bouton_play.png
		 * 2 : images/bouton_settings.png
		 * 3 : images/bouton_credits.png
		 * 4 : images/bouton_quit.png
		 * 5 : images/bouton_lan_multi.png
		 * 6 : images/bouton_local_multi.png
		 * 7 : images/bouton_host.png
		 * 8 : images/bouton_retour.png
		 */
		
		m_home = new Menu(this, p, "Home", 1, 1);
		m_home.setPere(m_home);
		
		Menu m_play = new Menu(this, p, "Play", 2, 2);
		m_home.addFils(m_play);
		m_play.setPere(m_home);
		
		Menu m_settings = new Menu(this, p, "Settings", 2, 2);
		m_home.addFils(m_settings);
		m_settings.setPere(m_home);
		
		MenuFeuille m_credits = new MenuFeuille(this, p, "Credit","LLORCA Frederic\n LAINE Elouarn\n GITARD Valentin\n PERSON Ambre" , 2, 2);
		m_home.addFils(m_credits);
		m_credits.setPere(m_home);
		
		Menu m_quit = new Menu(this, p, "Quit", 2, 2);
		m_home.addFils(m_quit);
		m_quit.setPere(m_home);
		
		/*Menu m_local = new MenuPlay(this, p, "LocalMulti" , 2, 2);
		m_play.addFils(m_local);
		m_local.setPere(m_play);*/
		
		Menu m_lan = new Menu(this, p, "LANMulti", 3, 2);
		m_play.addFils(m_lan);
		m_lan.setPere(m_play);		
		
		Menu m_host = new Menu(this, p, "Host", 3, 2);
		m_lan.addFils(m_host);
		m_host.setPere(m_lan);
		
		Menu m_join = new Menu(this, p, "Join", 3, 2);
		m_lan.addFils(m_join);
		m_join.setPere(m_lan);
		
		m_partie = new MenuPlay(this, p, "Game", 2, 2);
		m_play.addFils(m_partie);
		m_partie.setPere(m_play);
		
		
		BoutonMenu bPlay = new BoutonMenu(m_home,m_play,0f,0.4f,0.5f,0.1f, 1);
		BoutonMenu bSettings = new BoutonMenu(m_home,m_settings,0f,0f,0.5f,0.1f, 2);
		BoutonMenu bCredits = new BoutonMenu(m_home,m_credits,0f,-0.4f,0.5f,0.1f, 3);
		BoutonQuitter bQuit = new BoutonQuitter(m_home,0f,-0.8f,0.5f,0.1f, 4);
		

		m_home.addBouton(bPlay);
		m_home.addBouton(bSettings);
		m_home.addBouton(bCredits);
		m_home.addBouton(bQuit);
		
		
		BoutonRetour bRetCredits = new BoutonRetour(m_credits,-0.8f,-0.8f,0.1f,0.1f, 8);
		m_credits.addBouton(bRetCredits);

		BoutonMenu bLocal = new BoutonMenu(m_play,m_partie,0f,0.4f,0.5f,0.1f, 6); // Tu as oublié des menu (consulter le document Archive sur le drive)
		BoutonMenu bLAN = new BoutonMenu(m_play,m_lan,0f,0f,0.5f,0.1f, 5);
		BoutonRetour bRetPlay = new BoutonRetour(m_play,-0.8f,-0.8f,0.1f,0.1f, 8); 
		
		m_play.addBouton(bLocal);
		m_play.addBouton(bLAN);
		m_play.addBouton(bRetPlay);

		/*BoutonMenu bJouer = new BoutonMenu(m_local,m_partie,0f,0.4f,0.5f,0.1f, 1);
		BoutonRetour bRetJouer = new BoutonRetour(m_partie,-0.8f,-0.8f,0.1f,0.1f, 8);

		m_local.addBouton(bJouer);
		m_local.addBouton(bRetJouer);*/
		
		BoutonRetour bRetLAN = new BoutonRetour(m_lan,-0.8f,-0.8f,0.1f,0.1f, 8); 
		m_lan.addBouton(bRetLAN);
		
		BoutonMenuPlay bLancerPartie = new BoutonMenuPlay(m_partie,m_partie,0f,0.4f,0.5f,0.1f, 1);
		
		m_partie.addBouton(bLancerPartie);

		

		this.curMenu = m_home;//m_home;
		System.out.println("NULL POINTER : ======= " + p.getAffichage().dMenu);
		
	}

	/**
	 * Methode permettant de récupérer le menu courant
	 * @return Menu courant
	 */
	
	public Menu getCurMenu() {
		return curMenu;
	}
	
	public void setCurMenu(Menu m)
	{
		curMenu = m;
	}
	
	public MenuPlay getMenuPlay(){
		return m_partie;
	}
	
	public Menu getM_home()
	{
		return m_home;
	}


	
	
	
}
