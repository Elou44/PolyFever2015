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
		
		m_home = new Menu(this, p, "Home", "images/title_PolyFever.png", "images/menu_theme_4pipes.png");
		m_home.setPere(m_home);
		
		Menu m_play = new Menu(this, p, "Play", "images/title_Play.png", "images/menu_theme_2pipes.png");
		m_home.addFils(m_play);
		m_play.setPere(m_home);
		
		Menu m_settings = new Menu(this, p, "Settings", "images/title_Play.png", "images/menu_theme_2pipes.png");
		m_home.addFils(m_settings);
		m_settings.setPere(m_home);
		
		MenuFeuille m_credit = new MenuFeuille(this, p, "Credit","LLORCA Frederic\n LAINE Elouarn\n GITARD Valentin\n PERSON Ambre" , "images/title_Play.png", "images/menu_theme_2pipes.png");
		m_home.addFils(m_credit);
		m_credit.setPere(m_home);
		
		Menu m_quit = new Menu(this, p, "Quit", "images/title_Play.png", "images/menu_theme_2pipes.png");
		m_home.addFils(m_quit);
		m_quit.setPere(m_home);
		
		Menu m_local = new MenuPlay(this, p, "LocalMulti" , "images/title_Play.png", "images/menu_theme_2pipes.png");
		m_play.addFils(m_local);
		m_local.setPere(m_play);
		
		Menu m_lan = new Menu(this, p, "LANMulti", "images/title_LAN.png", "images/menu_theme_2pipes.png");
		m_play.addFils(m_lan);
		m_lan.setPere(m_play);		
		
		Menu m_host = new Menu(this, p, "Host", "images/title_LAN.png", "images/menu_theme_2pipes.png");
		m_lan.addFils(m_host);
		m_host.setPere(m_lan);
		
		Menu m_join = new Menu(this, p, "Join", "images/title_LAN.png", "images/menu_theme_2pipes.png");
		m_lan.addFils(m_join);
		m_join.setPere(m_lan);
		
		m_partie = new MenuPlay(this, p, "Game", "images/title_Play.png", "images/menu_theme_2pipes.png");
		m_local.addFils(m_partie);
		m_partie.setPere(m_local);
		
		
		BoutonMenu bPlay = new BoutonMenu(m_home,m_play,0f,0.4f,0.5f,0.1f, "images/bouton_play.png");
		BoutonMenu bSettings = new BoutonMenu(m_home,m_settings,0f,0f,0.5f,0.1f, "images/bouton_settings.png");
		BoutonMenu bCredits = new BoutonMenu(m_home,m_credit,0f,-0.4f,0.5f,0.1f, "images/bouton_credits.png");
		BoutonMenu bQuit = new BoutonMenu(m_home,m_quit,0f,-0.8f,0.5f,0.1f, "images/bouton_quit.png");
		BoutonRetour bRetHome = new BoutonRetour(m_home,-0.8f,-0.8f,0.1f,0.1f, "images/bouton_play.png"); // A Enlever

		m_home.addBouton(bPlay);
		m_home.addBouton(bSettings);
		m_home.addBouton(bCredits);
		m_home.addBouton(bQuit);
		m_home.addBouton(bRetHome);

		BoutonMenu bLocal = new BoutonMenu(m_play,m_local,0f,0.4f,0.5f,0.1f, "images/bouton_play.png"); // Tu as oublié des menu (consulter le document Archive sur le drive)
		BoutonMenu bMulti = new BoutonMenu(m_play,m_lan,0f,0f,0.5f,0.1f, "images/bouton_lan.png");
		BoutonRetour bRetPlay = new BoutonRetour(m_play,-0.8f,-0.8f,0.1f,0.1f, "images/bouton_play.png"); 
		
		m_play.addBouton(bLocal);
		m_play.addBouton(bMulti);
		m_play.addBouton(bRetPlay);

		BoutonMenuPlay bJouer = new BoutonMenuPlay(m_local,m_partie,0f,0.4f,0.5f,0.1f, "images/bouton_play.png");
		BoutonRetour bRetJouer = new BoutonRetour(m_partie,-0.8f,-0.8f,0.1f,0.1f, "images/bouton_play.png");

		m_local.addBouton(bJouer);
		m_local.addBouton(bRetJouer);

		

		this.curMenu = m_home;
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
