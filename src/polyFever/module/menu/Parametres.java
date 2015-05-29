package polyFever.module.menu;

import java.io.Serializable;
/**
 * Classe permettant de stocker les parametres du joueur
 * Les parametres sont compos�s d'un pseudo, une ip, un port et les touches gauche et droite
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
	 * @param pseudo : Pseudonyme par d�faut du joueur
	 * @param ip : IP par d�faut o� se connecter en r�seau
	 * @param port : Port par d�faut o� se connecter en r�seau
	 * @param gauche : Touche par d�faut pour tourner � gauche
	 * @param droite : Touche par d�faut pour tourner � droite
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
	 * Methode permettant de r�cup�rer le pseudo
	 * @return : String repr�sentant le pseudo
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
	 * Methode permettant de r�cup�rer l'ip
	 * @return : String repr�sentant l'ip
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
	 * Methode permettant de r�cup�rer le port
	 * @return : String repr�sentant le port
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
	 * Methode permettant de r�cup�rer la touche pour tourner � gauche
	 * @return Touche (d�finie dans l'API lwjgl Keyboard) utilis�e pour tourner � gauche
	 */


	public int getGauche() {
		return gauche;
	}

	/**
	 * Methode permettant de parametrer la touche pour tourner � gauche
	 * @param gauche : Nouvelle touche (d�finie dans l'API lwjgl Keyboard) qui sera utilis�e pour tourner � gauche
	 */

	public void setGauche(int gauche) {
		this.gauche = gauche;
	}

	/**
	 * Methode permettant de r�cup�rer la touche pour tourner � droite
	 * @return Touche (d�finie dans l'API lwjgl Keyboard) utilis�e pour tourner � droite
	 */

	public int getDroite() {
		return droite;
	}

	/**
	 * Methode permettant de parametrer la touche pour tourner � droite
	 * @param gauche : Nouvelle touche (d�finie dans l'API lwjgl Keyboard) qui sera utilis�e pour tourner � droite
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
