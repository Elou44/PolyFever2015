package polyFever.module.affichage;
import java.util.*;

import polyFever.module.util.math.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import polyFever.module.main.*;
import polyFever.module.moteurDeJeu.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;







import org.lwjgl.BufferUtils;


/**
 * <p>
 * La classe DessinLignes affiche les différentes lignes et les différents curseurs des joueurs. 
 * </p>
 *  
 * @author Elouarn Lainé 
 *
 */
public class DessinLignes  { // peut être instancier un tableau de DessinLigne dans DessinPlateau (1 par joueur)
	
	/**
	 * Pour des explications sur les attributs de cette classe, 
	 * voir la classe {@link DessinMenu} qui a des attributs similaires.
	 */
	private int nbVertex;

	private Affichage affichage;
	private PolyFever polyFever;
	private Partie partie;
	private Joueur j;
		
	private int program, ebo,vbo, posAttrib, colAttrib, uniColor, projectionUniform;
	
	private float tabVertex[];
	private int elements[];
	private int lenTabV;
	private int lenTabE;
	private int indexTabE;
	FloatBuffer vboBuffer;
	IntBuffer eboBuffer;
	
	/**
	 * Nombre de côtés d'un curseur d'un joueur.
	 */
	private final int NBCOTES = 15; // Nombre de côtés du point du joueur
	private FloatBuffer projectionMatrix;
	private long t_start;
	
	/**
	 * Constructeur de la classe DessinLignes
	 * Initialisation des attributs. instanciation de 2 tableaux d'un million d'éléments chacun 
	 * qui contiendront respectivement les vertex et les elements.
	 * @param a
	 * 		référence vers l'objet Affichage.
	 * @param p
	 * 		référence vers l'objet PolyFever.
	 * @param partie
	 * 		référence vers l'objet Partie.
	 * 
	 * @author Elouarn Lainé
	 */
	public DessinLignes(Affichage a, PolyFever p, Partie partie)
	{

		this.affichage = a;
		this.polyFever = p;
		this.partie = partie;
		this.nbVertex = 0;
		this.lenTabV = 0;
		this.lenTabE = 0;
		this.indexTabE = 0;
		this.tabVertex = new float[1000000];
		this.elements = new int[1000000];
		
		this.projectionMatrix = this.polyFever.getGlOrtho().getProjectionBuf();
	};
		
	
	/**
	 * Initialisation des objets nécessaires à l'affichage 
	 * (création du program shader, création de l'eboBuffer et du vboBuffer, chargemement des textures).
	 */
	public void init()
	{
		
		System.out.println("Initialisation pour traçage des Lignes...");
		t_start = System.currentTimeMillis();
		
		glClearColor(0, 0, 0, 0);
		
		
		int vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, polyFever.readFromFile("vert_shader.vert")); // Chargement du vertex shader (vs)
		
		glCompileShader(vs); // Compilation du vertex shader
		
		if(glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failure in compiling vertex shader. Error log:\n" + glGetShaderInfoLog(vs, glGetShaderi(vs, GL_INFO_LOG_LENGTH)));
			System.exit(0);
		}
		
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, polyFever.readFromFile("frag_shader.frag")); // Chargement du fragment shader (fs)
		
		glCompileShader(fs); // Compilation du fragment shader
		
		if(glGetShaderi(fs, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failure in compiling fragment shader. Error log:\n" + glGetShaderInfoLog(fs, glGetShaderi(fs, GL_INFO_LOG_LENGTH)));
			polyFever.destroy();
		}
				
		
		program = glCreateProgram(); // Création du programme auquel sera rattaché les shaders
		
	
		glAttachShader(program, vs); // On attache le vs au programme
		glAttachShader(program, fs); // On attache le fs au programme
		
		glLinkProgram(program);
		
						
		if(glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
			System.err.println("Failure in linking program. Error log:\n" + glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH)));
			polyFever.destroy();
		}
		
		projectionUniform = glGetUniformLocation(program, "Projection");
		System.out.println(projectionUniform);

		uniColor = glGetUniformLocation(program, "Color");
		posAttrib = glGetAttribLocation(program, "position");
		colAttrib = glGetAttribLocation(program, "color");
		System.out.println("posAttrib: ".concat(String.valueOf(posAttrib)));
		System.out.println("colAttrib: ".concat(String.valueOf(colAttrib)));
		
		
		glDetachShader(program, vs);
		glDetachShader(program, fs);
		
		glBindAttribLocation(program, posAttrib, "position"); // on bind l'attribut position à l'entier posAttrib. 
		glBindAttribLocation(program, colAttrib, "color"); // on bind l'attribut color à l'entier colAttrib.
		
		glDeleteShader(vs);
		glDeleteShader(fs);
		
		
		vbo = glGenBuffers(); // ebo : Elements Buffer Object (plus adapté que les vbo (vertex buffer object pour le dessin de multiple objets)
		vboBuffer = (FloatBuffer)BufferUtils.createFloatBuffer(this.tabVertex.length).put(this.tabVertex).flip();  // IMPORTANT : TRACER LES JOUEURS AU DEBUT DU BUFFER DE VERTEX
		glBindBuffer(GL_ARRAY_BUFFER, vbo);  // Fait en sorte que le ebo soit l'objet actif

		
		glBufferData(GL_ARRAY_BUFFER, vboBuffer, GL_STREAM_DRAW); // Est appliqué sur le vbo actif
		

		ebo = glGenBuffers(); // ebo : Elements Buffer Object (plus adapté que les vbo (vertex buffer object pour le dessin de multiple objets)
		eboBuffer = (IntBuffer)BufferUtils.createIntBuffer(this.elements.length).put(this.elements).flip();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);  // Fait en sorte que le ebo soit l'objet actif
		
		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, eboBuffer, GL_STREAM_DRAW); // Est appliqué sur le vbo actif
		

		// On trace des axes
		/*this.addRectangle(new Vector3(0.0f,-1.0f,1.0f), 1.57f,0.005f, -2.0f, new Vector3(1.0f,1.0f,1.0f));
		this.addRectangle(new Vector3(-1.0f,0.0f,1.0f), 0.0f, 0.005f, -2.0f, new Vector3(1.0f,1.0f,1.0f));*/
		
		//InitDessinJoueurs
		initDessinJoueurs();
		
		// Dessin des bords du plateau
		dessinerBordsPlateau(new Vector2(0.0f,0.0f));
		
		glBindVertexArray(glGenVertexArrays()); // Création d'un VAO : Vertex Array Object avec glGenVertexArrays() . le VAO stock les liens entre les attributs et les VBO
		// le VAO contient une référence vers le VBO
	
	}
	
	
	/**
	 * <p> 
	 * L'affichage du buffer de vertex se déroule en plusieurs étapes : 
	 * <ul>
	 *  <li>On ajoute un rectangle dans le buffer de vertex pour chaque joueur qui vérifie la condition de traçage</li>
	 * 	<li>On recharge l'eboBuffer et le vboBuffer dans la VRAM.</li>
	 *  <li>On efface l'écran.</li>
	 *  <li>On active le program shader.</li>
	 *  <li>On met à jour la matrice de projection du vertex shader.</li>
	 *  <li>On bind l'ebo et le vbo au contexte openGL pour indiquer à ce dernier qu'il devra les utiliser pour l'affichage.</li>
	 *  <li>On indique à openGL comment il doit interpréter le buffer de vertex ( 2 premiers floats pour la position, les 3 suivants pour la couleurs et les deux derniers pour la position de la texture.</li>
	 *  <li>On trace tous les vertices contenues dans le buffer de vertex.</li>
	 *  <li>On détache les buffers (ebo et vbo) du contexte openGL.</li>
	 *  <li>On détache le program shader du contexte openGL.</li>
	 * </ul>
	 * </p>
	 */
	public void dessiner()
	{

		long t_now = System.currentTimeMillis();
		float time = t_now - t_start;
		
			
		Iterator<Joueur> e = this.partie.getJoueurs().iterator();
		int i = 0;
		while(e.hasNext()) // A déplacer dans Init();
		{
			j = e.next();
			if(j.getPosition().z() == 1.0f && j.getEtat() == Etat.VIVANT && !partie.isRoundEnPause())
			{
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse(), i);
				
			}
			i++;
		}
		
		updatePosJoueurs(false); // On bouge le point du joueur
		
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferSubData(GL_ARRAY_BUFFER, 0, vboBuffer);
		
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, eboBuffer);
		
		
		glClear(GL_COLOR_BUFFER_BIT);
		
		glUseProgram(program);
		
		
		
		glUniformMatrix4(projectionUniform, false, (FloatBuffer)projectionMatrix);
		
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo); //TEST

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo); //TEST
		
		//double newColor = Math.sin(time/100 + 4.0f);
		//System.out.println(time);
		//glUniform3f(uniColor, 1.0f, 0.0f, 0.0f); // change la couleur du triangle en rouge
		
		
		glEnableVertexAttribArray(posAttrib);
		glVertexAttribPointer(posAttrib, 2, GL_FLOAT, false,5*4, 0);
		
		glEnableVertexAttribArray(colAttrib);
		glVertexAttribPointer(colAttrib, 3, GL_FLOAT, false,5*4, 2*4);
		
		
		glDrawElements(GL_TRIANGLES, this.nbVertex, GL_UNSIGNED_INT, 0); // essayer avec glDrawElements (https://open.gl/drawing)
		
		glDisableVertexAttribArray(posAttrib);
		glDisableVertexAttribArray(colAttrib);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		glUseProgram(0);
	}
	
	
	/**
	 * Cette méthode permet de calculer la position des vertices d'un rectangle 
	 * (le rectangle étant l'élément qui compose les lignes des joueurs).
	 * @param v
	 * 		objet de type {@link Vector3} : origine du rectangle.
	 * @param angle
	 * 		float représentant l'orientation du rectangle.
	 * @param w
	 * 		float représentant la largeur du rectangle.
	 * @param h
	 * 		float représentant la hauteur du rectangle.
	 * @param c
	 * 		objet de type {@link Vector3} représentant la couleur du rectangle.
	 */
	private void addRectangle(Vector3 v, float angle, float w, float h, int c) // v : point d'encrage (milieu du bord supérieur)
	{
		
		h = h+0.01f; // On allonge un peu le rectangle pour avoir une trace pleine, même lors de courbes sérées.

		
		Vector2 p1 = new Vector2();
		
		double xd =  v.x() + (w/2)*Math.cos((Math.PI/2)-angle);
		float x = (float) xd; // redimenssionner tout le tableau une fois tracé 
		double yd =  v.y() - (w/2)*Math.sin((Math.PI/2)-angle);
		float y = (float) yd;
		p1.set( x, y);

		
		Vector2 p2 = new Vector2();	
		
		xd =  p1.x() - h*Math.cos(angle);
		x = (float) xd;
		yd =  p1.y() - h*Math.sin(angle);
		y = (float) yd;
		p2.set(x, y);

		
		Vector2 p4 = new Vector2();
		xd =  v.x() - (w/2)*Math.cos((Math.PI/2)-angle);
		x = (float) xd;
		yd =  v.y() + (w/2)*Math.sin((Math.PI/2)-angle);
		y = (float) yd;
		p4.set( x, y);

		
		Vector2 p3 = new Vector2();
		
		xd =  p4.x() - h*Math.cos(angle);
		x = (float) xd;
		yd =  p4.y() - h*Math.sin(angle);
		y = (float) yd;
		p3.set( x, y);
		
		ajouterVector2Rect(p1, p2, p3, p4, c);
		this.nbVertex += 6; // L'équivalent des 6 vertex doivent être utilisées pour tracer un rectangle
		
	}
	
	/**
	 * Cette méthode permet d'ajouter dans le vertex buffer les vertices nécessaires à l'affichage d'un rectangle 
	 * (le rectangle étant l'élément qui compose les lignes des joueurs). 
	 * @param p1
	 * 		objet de type {@link Vector2} : premier point du rectangle.
	 * @param p2
	 * 		objet de type {@link Vector2} : deuxieme point du rectangle.
	 * @param p3
	 * 		objet de type {@link Vector2} : troisieme point du rectangle.
	 * @param p4
	 * 		objet de type {@link Vector2} : quatrieme point du rectangle.
	 * @param c
	 * 		objet de type {@link Vector3} representant la couleur du rectangle.
	 */
	private void ajouterVector2Rect(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4, int c)
	{
	
		this.tabVertex[this.lenTabV] = p4.x(); // Top Left
		this.tabVertex[this.lenTabV+1] = p4.y(); 	
		this.tabVertex[this.lenTabV+2] = CouleursLigne.tabCouleurs[c].x(); 
		this.tabVertex[this.lenTabV+3] = CouleursLigne.tabCouleurs[c].y(); 
		this.tabVertex[this.lenTabV+4] = CouleursLigne.tabCouleurs[c].z(); 
		
		this.tabVertex[this.lenTabV+5] = p1.x(); // Top Right
		this.tabVertex[this.lenTabV+6] = p1.y();
		this.tabVertex[this.lenTabV+7] = CouleursLigne.tabCouleurs[c].x(); 
		this.tabVertex[this.lenTabV+8] = CouleursLigne.tabCouleurs[c].y(); 
		this.tabVertex[this.lenTabV+9] = CouleursLigne.tabCouleurs[c].z(); 

		this.tabVertex[this.lenTabV+10] = p2.x(); // Bottom Right
		this.tabVertex[this.lenTabV+11] = p2.y();
		this.tabVertex[this.lenTabV+12] = CouleursLigne.tabCouleurs[c].x(); 
		this.tabVertex[this.lenTabV+13] = CouleursLigne.tabCouleurs[c].y(); 
		this.tabVertex[this.lenTabV+14] = CouleursLigne.tabCouleurs[c].z(); 

		this.tabVertex[this.lenTabV+15] = p3.x(); // Bottom Left
		this.tabVertex[this.lenTabV+16] = p3.y();
		this.tabVertex[this.lenTabV+17] = CouleursLigne.tabCouleurs[c].x(); 
		this.tabVertex[this.lenTabV+18] = CouleursLigne.tabCouleurs[c].y(); 
		this.tabVertex[this.lenTabV+19] = CouleursLigne.tabCouleurs[c].z(); 
		
		
		
		this.partie.envoyerTabVertex(new Vector4(p4.x(),p4.y(),p3.x(),p3.y()),
				new Vector4(p1.x(),p1.y(),p2.x(),p2.y()));
		
		this.lenTabV += 20;

		this.vboBuffer.put(this.tabVertex); // On met a jour le buffer VBO 
		this.vboBuffer.clear();


		
		this.elements[this.lenTabE] = this.indexTabE;
		this.elements[this.lenTabE+1] = this.indexTabE+1;
		this.elements[this.lenTabE+2] = this.indexTabE+2;
		
		this.elements[this.lenTabE+3] = this.indexTabE+2;
		this.elements[this.lenTabE+4] = this.indexTabE+3;
		this.elements[this.lenTabE+5] = this.indexTabE;
		
		
		this.lenTabE += 6;

		this.eboBuffer.put(this.elements);
		this.indexTabE += 4;
		
		this.eboBuffer.clear();
	
	}
	
	
	/**
	 * Méthode permettant d'initialiser le dessin des joueurs (dessin des curseurs des joueurs).
	 */
	public void initDessinJoueurs()
	{
		
		Iterator<Joueur> e = this.partie.getJoueurs().iterator();
		while(e.hasNext()) 
		{
			j = e.next();

			this.initPoint(j.getPosition(),j.getLigne().getEpaisseur()/2);
		}
		
	}
	
	
	/**
	 * Méthode charger de calculer la position des vertices constituant les curseurs des joueurs.
	 * Cette méthode ajoute ensuite les vertices dans le buffer de vertex.
	 * @param p
	 * 		objet de type {@link Vector3} : centre du curseur.
	 * @param r
	 * 		float représentant le rayon du curseur.
	 */
	public void initPoint(Vector3 p, float r) // Ajoute dans le buffer les vertex nécessaires à l'affichage du point d'un joueur.
	{
		double alpha = 2*Math.PI / this.NBCOTES;
		Vector2 tabVertexPoint[] = new Vector2[this.NBCOTES+1]; // +1 pour le vertex central
		
		tabVertexPoint[0] = new Vector2(p.x(),p.y()); // centre du point du joueur
		
		for(int i = 1,j = 0; i<this.NBCOTES+1; i++,j++)
		{
			tabVertexPoint[i] = new Vector2(
					(float)Math.cos(alpha*(j))*r+p.x(),
					(float)Math.sin(alpha*(j))*r+p.y()
					);
		}
		
	
		this.tabVertex[this.lenTabV] = tabVertexPoint[0].x(); // Centre x
		this.tabVertex[this.lenTabV+1] = tabVertexPoint[0].y(); // Centre x
		this.tabVertex[this.lenTabV+2] = 1.0f; 
		this.tabVertex[this.lenTabV+3] = 0.0f; 
		this.tabVertex[this.lenTabV+4] = 0.0f; 
		
		this.lenTabV += 5;
		
		for(int i = 1; i<this.NBCOTES+1; i++) // On ajoute les vertex dans le tableau des vertex
		{
			this.tabVertex[this.lenTabV] = tabVertexPoint[i].x(); // Top Left
			this.tabVertex[this.lenTabV+1] = tabVertexPoint[i].y(); 	
			this.tabVertex[this.lenTabV+2] = 1.0f; 
			this.tabVertex[this.lenTabV+3] = 1.0f; 
			this.tabVertex[this.lenTabV+4] = 1.0f; 
			
			System.out.println("Vertex "+ i + ": " + tabVertexPoint[i].x() + ", " + tabVertexPoint[i].y() );
			
			this.lenTabV += 5;
		}
		
		
		
		
		int indexInitial = this.indexTabE; // indice du centre du point
		
		for(int j = 0; j<this.NBCOTES; j++)
		{
			if(j == this.NBCOTES-1) // dernier tour de boucle
			{
				this.elements[this.lenTabE] = indexInitial;
				this.elements[this.lenTabE+1] = this.indexTabE+1;
				this.elements[this.lenTabE+2] = this.indexTabE+1+j;
				
				System.out.println(String.valueOf(indexInitial) + "-" + String.valueOf(this.indexTabE+1) + "-" + String.valueOf(this.indexTabE+1+j));
			}
			else
			{
				this.elements[this.lenTabE] = indexInitial;
				this.elements[this.lenTabE+1] = this.indexTabE+2+j;
				this.elements[this.lenTabE+2] = this.indexTabE+1+j;
				
				System.out.println(String.valueOf(indexInitial) + "-" + String.valueOf(this.indexTabE+2+j) + "-" + String.valueOf(this.indexTabE+1+j));
			}
			
			this.lenTabE += 3;
		}
		
		this.indexTabE += this.NBCOTES + 1; // + NBCOTES vertex + 1 (centre)
		
		this.nbVertex += (this.NBCOTES)*3; // On doit utiliser l'équivalent de 3*NBCOTES pour tracer le point
	}
	
	
	/**
	 * Méthode mettant à jour la position des curseurs des joueurs par simple translation des positions.
	 * @param isUpdateNeeded
	 * 		boolean indiquant si l'on doit recalculer les positions des vertices des curseurs 
	 * 		(si les curseurs changent de taille par exemple).
	 */
	public void updatePosJoueurs(boolean isUpdateNeeded)
	{
		if(!this.partie.isRoundEnPause())
		{
			int i = 0;
			Iterator<Joueur> e = this.partie.getJoueurs().iterator();
			while(e.hasNext()) // A déplacer dans Init();
			{
				
					
				j = e.next();
				if(j.getEtat() == Etat.VIVANT)
				{
					moveVertexJoueur(j.getAnciennePosition(), j.getPosition(),j.getLigne().getEpaisseur()/2, isUpdateNeeded,  i);
					
				}
				i++;
			}
		}	
	}
	
	/**
	 * Méthode calculant la nouvelle position des vertices des curseurs des joueurs.
	 * @param lastp
	 * 		objet de type {@link Vector3} représentant la dernière position du joueur. 
	 * @param p
	 * 		objet de type {@link Vector3} représentant la position actuelle du joueur. 
	 * @param r
	 * 		float représentant le rayon du curseur.
	 * @param isUpdateNeeded
	 *  	boolean indiquant si l'on doit recalculer les positions des vertices des curseurs 
	 * 		(si les curseurs changent de taille par exemple).
	 * @param i
	 * 		entier représentant l'indice du joueur.
	 */
	public void moveVertexJoueur(Vector3 lastp, Vector3 p, float r, boolean isUpdateNeeded, int i) // i : indice du joueur
	{
		Vector2 vecDiff = new Vector2(p.x()-lastp.x(),p.y()-lastp.y());

		if(isUpdateNeeded) 
		{
			double alpha = 2*Math.PI / this.NBCOTES;
			//System.out.println("player point's vertices have been updated");
			this.tabVertex[0+i*5*(this.NBCOTES+1)] = p.x();
			this.tabVertex[1+i*5*(this.NBCOTES+1)] = p.y();
			for(int j = i*(this.NBCOTES+1)+1, k = 0; j<this.NBCOTES+1+i*(this.NBCOTES+1); j++, k++)
			{
				this.tabVertex[5*j] = (float)Math.cos(alpha*(k))*r+p.x();
				this.tabVertex[(5*j)+1] = (float)Math.sin(alpha*(k))*r+p.y();
			}
			
		}
		else if(!isUpdateNeeded) // Si l'épaisseur n'a pas changé
		{
			for(int j = i*(this.NBCOTES+1); j<this.NBCOTES+1+i*(this.NBCOTES+1); j++)
			{
				this.tabVertex[5*j] += vecDiff.x();
				this.tabVertex[(5*j)+1] += vecDiff.y();
			}

		}
		
		
		this.vboBuffer.put(this.tabVertex); // On met a jour le buffer VBO 
		this.vboBuffer.clear();
		
		this.eboBuffer.put(this.elements);
		this.eboBuffer.clear();
		
		//printTabVertex();

	}
	
	/**
	 * Méthode permettant de nettoyer le tableau de vertex.
	 * Cette méthode est appelée lorsqu'un bonus clean est pris ou encore lorsqu'un nouveau round commence.
	 */
	public void clearTabVertex() {
		
		this.nbVertex = (this.NBCOTES*3)*partie.getNbJoueurs()+4*6; // 4 vertex par bord * 4 bords + 3 vertex par coté * nb joueur
		this.lenTabV = ((this.NBCOTES+1)*partie.getNbJoueurs()+4*4)*5; // NBCOTES+1 vertex par point de joueur * nb le nombre de joueurs + 4 vertex par board (5 floats par vertex)
		this.lenTabE = (this.NBCOTES*3)*partie.getNbJoueurs()+6*4;  // 6 floats par board + 3 vertex par coté * nb joueur
		this.indexTabE = ((this.NBCOTES+1)*partie.getNbJoueurs()+4*4); // NBCOTES+1 vertex par point de joueur * nb le nombre de joueurs + 4 vertex par board
		System.out.println("TabVertex has been cleared !");
		
		//System.out.println("nbVertex: " + nbVertex);
		//System.out.println("lenTabE: " + lenTabE);
		//System.out.println("lenTabV: " + lenTabV);
		//System.out.println("indexTabE: " + indexTabE);
	}
	
	/**
	 * Cette méthode permet l'affichage des bords du plateau de jeu.
	 * @param p
	 * 		objet de type {@link Vector2} : centre du plateau de jeu.
	 */
	public void dessinerBordsPlateau(Vector2 p)
	{
		this.addRectangle(new Vector3(-1.0f-p.x(),1.0f-p.y(),1.0f),(float) Math.PI/2, 0.01f, 2.0f, 3); // LEFT 
		this.addRectangle(new Vector3(1.0f-p.x(),1.0f-p.y(),1.0f),(float) Math.PI/2, 0.01f, 2.0f, 3); // RIGHT
		this.addRectangle(new Vector3(1.0f-p.x(),-1.0f-p.y(),1.0f), 0.0f, 0.02f, 1.99f, 3); // BOTTOM
		this.addRectangle(new Vector3(1.0f-p.x(),1.0f-p.y(),1.0f), 0.0f, 0.01f, 1.99f, 3); // RIGHT
		
		System.out.println("nbVertex: " + nbVertex);
		System.out.println("lenTabE: " + lenTabE);
		System.out.println("lenTabV: " + lenTabV);
		System.out.println("indexTabE: " + indexTabE);
	}
	
	
	/**
	 * Méthode affichant le tableau de vertex (seulement pour le debuguage).
	 */
	public void printTabVertex()
	{
		for(int i = 0; i<80; i++)
		{
			System.out.print(this.tabVertex[i]+", ");
		}
		System.out.println("_______________________________________________");
	}
	
	

}