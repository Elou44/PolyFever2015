package polyFever.module.menu;

import java.io.Serializable;
/**
 * Classe permettant de stocker les parametres du joueur
 * Les parametres sont composés d'un pseudo, une ip, un port et les touches gauche et droite
 * @author Ambre
 *
 */
public class Parametres implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected String pseudo;
	protected String ip;
	protected String port;
	protected int gauche;
	protected int droite;
	
	/**
	 * Constructeur necessitant les informations de parametrage
	 * @param pseudo : Pseudonyme par défaut du joueur
	 * @param ip : IP par défaut où se connecter en réseau
	 * @param port : Port par défaut où se connecter en réseau
	 * @param gauche : Touche par défaut pour tourner à gauche
	 * @param droite : Touche par défaut pour tourner à droite
	 */
	
	public Parametres(String pseudo, String ip, String port, int gauche,
			int droite) {
		this.pseudo = pseudo;
		this.ip = ip;
		this.port = port;
		this.gauche = gauche;
		this.droite = droite;
	}
	
	/**
	 * Constructeur ne necessitant pas de parametres
	 */
	
	public Parametres() {
		this.pseudo = null;
		this.ip = null;
		this.port = null;
		this.gauche = (Integer) null;
		this.droite = (Integer) null;
	}
	
	
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public int getGauche() {
		return gauche;
	}

	public void setGauche(int gauche) {
		this.gauche = gauche;
	}

	public int getDroite() {
		return droite;
	}

	public void setDroite(int droite) {
		this.droite = droite;
	}

	@Override
	public String toString() {
		return "Parametres [pseudo=" + pseudo + ", ip=" + ip + ", port=" + port
				+ ", gauche=" + gauche + ", droite=" + droite + "]";
	}
	
		
}
