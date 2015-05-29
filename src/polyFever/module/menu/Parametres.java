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
	
	/**
	 * Methode permettant de récupérer le pseudo
	 * @return : String représentant le pseudo
	 */
	
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * Methode permettant de parametrer le pseudo
	 * @param pseudo : String qui deviendra le nouveau pseudo
	 */

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	/**
	 * Methode permettant de récupérer l'ip
	 * @return : String représentant l'ip
	 */

	public String getIp() {
		return ip;
	}

	/**
	 * Methode permettant de parametrer l'adresse ip
	 * @param pseudo : String qui deviendra la nouvelle adresse ip
	 */

	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Methode permettant de récupérer le port
	 * @return : String représentant le port
	 */

	public String getPort() {
		return port;
	}

	/**
	 * Methode permettant de parametrer le port
	 * @param pseudo : String qui deviendra le nouveau port
	 */

	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * Methode permettant de récupérer la touche pour tourner à gauche
	 * @return Touche (définie dans l'API lwjgl Keyboard) utilisée pour tourner à gauche
	 */


	public int getGauche() {
		return gauche;
	}

	/**
	 * Methode permettant de parametrer la touche pour tourner à gauche
	 * @param gauche : Nouvelle touche (définie dans l'API lwjgl Keyboard) qui sera utilisée pour tourner à gauche
	 */

	public void setGauche(int gauche) {
		this.gauche = gauche;
	}

	/**
	 * Methode permettant de récupérer la touche pour tourner à droite
	 * @return Touche (définie dans l'API lwjgl Keyboard) utilisée pour tourner à droite
	 */

	public int getDroite() {
		return droite;
	}

	/**
	 * Methode permettant de parametrer la touche pour tourner à droite
	 * @param gauche : Nouvelle touche (définie dans l'API lwjgl Keyboard) qui sera utilisée pour tourner à droite
	 */

	public void setDroite(int droite) {
		this.droite = droite;
	}

	@Override
	public String toString() {
		return "Parametres [pseudo=" + pseudo + ", ip=" + ip + ", port=" + port
				+ ", gauche=" + gauche + ", droite=" + droite + "]";
	}
	
		
}
