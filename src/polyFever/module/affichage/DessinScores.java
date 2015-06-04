package polyFever.module.affichage;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.awt.Rectangle;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import polyFever.module.main.PolyFever;
import polyFever.module.menu.Bouton;
import polyFever.module.menu.Menu;
import polyFever.module.moteurDeJeu.Etat;
import polyFever.module.moteurDeJeu.Joueur;
import polyFever.module.moteurDeJeu.Partie;
import polyFever.module.util.PNGDecoder;
import polyFever.module.util.PNGDecoder.Format;


/**
 * Classe devant se charger du dessin des scores à l'écran. (pas encore implémentée)
 * @author Elouarn Lainé
 *
 */
public class DessinScores {
	
	/**
	 * Nombre de vertex contenu dans le tableau de tableau de vertices.
	 */
	//private int nbVertex;


	private PolyFever polyFever;
	
	private Partie partie;
	
	/**
	 * program regroupe le vertex shader et le fragment shader (les shaders sont des programmes exécutés sur le GPU)
	 */
	private int program;
	
	/**
	 * Identifiant de ebo (element buffer object) et du vbo (vertex buffer object) 
	 */
	private int ebo,vbo;
	
	/**
	 * Variables pour faire le lien avec les attributs du vertex shaders
	 */
	private int posAttrib, colAttrib, texAttrib, projectionUniform;
	
	/**
	 * Identifiants des différentes textures du menus
	 */
	private int idTexFig0, idTexFig1, idTexFig2, idTexFig3, idTexFig4, idTexFig5, idTexFig6, idTexFig7, idTexFig8, idTexFig9, idTexTitle, idTexPlayerLogo;  // indentifiant des textures
	
	private int idTexFond, idTexTitre;
	private int tabIdBoutons[];
	private int indiceBouton;
	
	/**
	 * Tableau de floats contenant toutes les informations des vertices, soit ici 7 floats par vertex
	 *  (2 floats pour la position du vertex, 3 floats pour sa couleur (RGB), et 2 floats pour la position de la texture)
	 */
	private float tabVertex[];
	
	/**
	 * Tableau d'entier contenant l'ordre de traçage des vertices, ceci afin de limiter le nombre de vertex à utiliser
	 *  ( 4 vertices au lieu de 6 nécessaires sans cette technique pour dessiner un rectangle)
	 */
	private int elements[];
	
	/**
	 * Longueur réelle du tableau de vertex.
	 */
	private int lenTabV;
	
	/**
	 * Longueur réelle du tableau d'elements.
	 */
	private int lenTabE;
	private int indexTabE;
	/**
	 * FloatBuffer contenant les vertices.
	 */
	FloatBuffer vboBuffer;
	
	/**
	 * IntBuffer contenant les elements
	 */
	IntBuffer eboBuffer;
	
	/**
	 * FloatBuffer contenant la matrice de projection. La position des vertices est multipliée 
	 * par cette martrice afin d'annuler l'effet de distortion qui apparait lorsque la fenêtre devient rectangulaire.
	 */
	private FloatBuffer projectionMatrix;
	
	private float[] titleBox;
	
	private float yOffSet;
	private Joueur j;
	
	
	/**
	 * Constructeur de la classe DessinMenu
	 * Initialisation des attributs. instanciation de 2 tableaux d'un million d'éléments chacun 
	 * qui contiendront respectivement les vertex et les elements.
	 * @param a
	 * 		référence vers l'objet Affichage.
	 * @param p
	 * 		référence vers l'objet PolyFever.
	 * 
	 * @author Elouarn Lainé
	 */
	
	public DessinScores(Affichage a, PolyFever p, Partie partie)
	{
		this.partie = partie;
		this.polyFever = p;
		this.lenTabV = 0;
		this.lenTabE = 0;
		this.indexTabE = 0;
		this.tabVertex = new float[1000000];
		this.elements = new int[1000000];
		
		titleBox = new float[] {1.1f,0.9f,0.6f,0.15f};
		this.yOffSet = 0.0f;
		
		this.projectionMatrix = this.polyFever.getGlOrtho().getProjectionBuf();
	};	
	
	
	/**
	 * Méthode réalisant le chargement d'une image au format PNG dans un ByteBuffer.
	 * 
	 * @param path
	 * 			Chemin vers l'image.
	 * @return ByteBuffer
	 * 			Buffer contenant l'image.
	 */
	public ByteBuffer PNGtoTex(String path)
	{
		ByteBuffer pixelData = BufferUtils.createByteBuffer(0); // On initialise le ByteBuffer avec une texture vide
		try(BufferedInputStream is = new BufferedInputStream(new FileInputStream(path))){
		    //Create the PNGDecoder object and decode the texture to a buffer
		    PNGDecoder decoder = new PNGDecoder(is);
		    int width = decoder.getWidth(), height = decoder.getHeight();
		    pixelData = BufferUtils.createByteBuffer(4*width*height);
		    decoder.decode(pixelData, 4*width, Format.RGBA);
		    pixelData.flip();
		    //Generate and bind the texture   
		    
		}catch(IOException e){
		    e.printStackTrace();
		}
		return pixelData;
		
	}
	
	
	/**
	 * Initialisation des objets nécessaires à l'affichage 
	 * (création du program shader, création de l'eboBuffer et du vboBuffer, chargemement des textures).
	 */
	public void init()
	{
		
		//partie.envoyerTabVertex(tabVertex, lenTabV); // Envoie de la référence du tableau à l'objet partie
		
		System.out.println("Initialisation de l'affichage du MENU...");
		
		glClearColor(0, 0, 0, 0);
						
		int vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, polyFever.readFromFile("vert_shaderM.vert")); // Chargement du vertex shader (vs)
		
		glCompileShader(vs); // Compilation du vertex shader
		
		if(glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failure in compiling vertex shader. Error log:\n" + glGetShaderInfoLog(vs, glGetShaderi(vs, GL_INFO_LOG_LENGTH)));
			System.exit(0);
		}
		
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, polyFever.readFromFile("frag_shaderM.frag")); // Chargement du fragment shader (fs)
		
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

		//uniColor = glGetUniformLocation(program, "Color");
		
		posAttrib = glGetAttribLocation(program, "position");
		colAttrib = glGetAttribLocation(program, "color");
		texAttrib = glGetAttribLocation(program, "texcoord");
		
		System.out.println("posAttrib: ".concat(String.valueOf(posAttrib)));
		System.out.println("colAttrib: ".concat(String.valueOf(colAttrib)));
		System.out.println("texAttrib: ".concat(String.valueOf(texAttrib)));
		//System.out.println("uniColor: ".concat(String.valueOf(uniColor)));
		
		glDetachShader(program, vs);
		glDetachShader(program, fs);
		
		glBindAttribLocation(program, posAttrib, "position"); // on bind l'attribut position à l'entier posAttrib. 
		glBindAttribLocation(program, colAttrib, "color"); // on bind l'attribut color à l'entier colAttrib.
		glBindAttribLocation(program, texAttrib, "texcoord"); // on bind l'attribut texcoord à l'entier texAttrib.
		

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
		
		
		glBindVertexArray(glGenVertexArrays()); // Création d'un VAO : Vertex Array Object avec glGenVertexArrays() . le VAO stock les liens entre les attributs et les VBO
		// le VAO contient une référence vers le VBO
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		
		// CHARGEMENT DES TEXTURES // 

		// Les Chiffres
		
		idTexFig0 = GL11.glGenTextures(); // 0
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig0);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/num0.png"));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig0);
		
	    idTexFig1 = GL11.glGenTextures(); // 1
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig1);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/num1.png"));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig1);
	    
	    idTexFig2 = GL11.glGenTextures(); // 2
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig2);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/num2.png"));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig2);
	    
	    idTexFig3 = GL11.glGenTextures(); // 3
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig3);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/num3.png"));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig3);
	   
	    idTexFig4 = GL11.glGenTextures(); // 4
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig4);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/num4.png"));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig4);
	    
	    idTexFig5 = GL11.glGenTextures(); // 5
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig5);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/num5.png"));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig5);
	    
	    idTexFig6 = GL11.glGenTextures(); // 6
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig6);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/num6.png"));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig6);
	    
	    idTexFig7 = GL11.glGenTextures(); // 7
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig7);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/num7.png"));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig7);
	    
	    idTexFig8 = GL11.glGenTextures(); // 8
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig8);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/num8.png"));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig8);
	    
	    idTexFig9 = GL11.glGenTextures(); // 9
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig9);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/num9.png"));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig9);
	    
	    idTexTitle = GL11.glGenTextures(); // Title
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitle);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 300, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/scoresTitle.png"));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitle);
	    
	    idTexPlayerLogo = GL11.glGenTextures(); // Player Logo
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexPlayerLogo);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/playerLogo.png"));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexPlayerLogo);
	    
	    
	    
	    
	    // FIN CHARGEMENT DES TEXTURES // 
	    
	    updateScores();

	}

	
	public void dessiner()
	{
		//System.out.println("		dessiner dScores");
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferSubData(GL_ARRAY_BUFFER, 0, vboBuffer);
		
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, eboBuffer);
	
		
		//glClear(GL_COLOR_BUFFER_BIT);
		
		glUseProgram(program);
		
		
		glUniformMatrix4(projectionUniform, false, (FloatBuffer)projectionMatrix);
		
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo); 

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo); 
		
		glEnableVertexAttribArray(posAttrib);
		glVertexAttribPointer(posAttrib, 2, GL_FLOAT, false,7*4, 0);
		
		glEnableVertexAttribArray(colAttrib);
		glVertexAttribPointer(colAttrib, 3, GL_FLOAT, false,7*4, 2*4);
		
		glEnableVertexAttribArray(texAttrib);
		glVertexAttribPointer(texAttrib, 2, GL_FLOAT, false, 7*4, 5*4);
		
		//updateScores();
		
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitle); // On bind la premiere texture
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0); 
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // On met la texture à NULL

		int i = 0;
		Iterator<Joueur> e = this.partie.getJoueurs().iterator();
		
		while(e.hasNext()) // A déplacer dans Init();
		{
			j = e.next();

			GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexPlayerLogo); // On bind la premiere texture
			glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 6*4+(18*4*i));  // On trace le carré
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // On met la texture à NULL
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, scoreToIdTex(j)[0]); // On bind la premiere texture
			glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 12*4+(18*4*i)); 
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // On met la texture à NULL
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, scoreToIdTex(j)[1]); // On bind la premiere texture
			glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 18*4+(18*4*i)); 
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // On met la texture à NULL
			i++;

		}
			//System.out.println("IdBouton :" + tabIdBoutons[i]);
			

		
		
		
		glDisableVertexAttribArray(posAttrib);
		glDisableVertexAttribArray(colAttrib);
		glDisableVertexAttribArray(texAttrib);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0); 
		
		glUseProgram(0);
	}
	
	public void updateScores(){


		this.lenTabV = 0;
		this.lenTabE = 0;
		this.indexTabE = 0;
		this.yOffSet = 0.0f;
		
		// On ajoute le titre "Scores"
		float tabVertexScores[] = new float[] {
				
	    		titleBox[0],titleBox[1],1.0f,1.0f,1.0f,0.0f,0.0f, //Title
	    		titleBox[0]+titleBox[2],titleBox[1],1.0f,1.0f,1.0f,1.0f,0.0f,
	    		titleBox[0]+titleBox[2],titleBox[1]-titleBox[3],1.0f,1.0f,1.0f,1.0f,1.0f,
	    		titleBox[0],titleBox[1]-titleBox[3],1.0f,1.0f,1.0f,0.0f,1.0f,
		};
		
		addTabToTabVertex(tabVertexScores);
		
		this.lenTabV += 28;


		this.elements[this.lenTabE] = this.indexTabE;
		this.elements[this.lenTabE+1] = this.indexTabE+1;
		this.elements[this.lenTabE+2] = this.indexTabE+2;
		
		this.elements[this.lenTabE+3] = this.indexTabE;
		this.elements[this.lenTabE+4] = this.indexTabE+2;
		this.elements[this.lenTabE+5] = this.indexTabE+3;
	    
		
		this.lenTabE += 6;
		this.indexTabE += 4;
		
		
		Iterator<Joueur> e = this.partie.getJoueurs().iterator();
		
		while(e.hasNext()) // A déplacer dans Init();
		{
			j = e.next();

			this.addScore(j);

		}
		
		// On met à jour les vertex et elements buffer
		this.vboBuffer.put(this.tabVertex); // On met a jour le buffer VBO 
		this.vboBuffer.clear();
		
		this.eboBuffer.put(this.elements);
		this.eboBuffer.clear();
		
	}
	
	
	public void addScore(Joueur j) {
		
		// Ajout d'un carré de couleur
		
		float r =  CouleursLigne.tabCouleurs[j.getLigne().getCouleur()].x();
		float g =  CouleursLigne.tabCouleurs[j.getLigne().getCouleur()].y();
		float b =  CouleursLigne.tabCouleurs[j.getLigne().getCouleur()].z();
		float tabVertexSquare[] = new float[] {
				
	    		1.1f,0.7f-yOffSet,r,g,b,0.0f,0.0f, //Square
	    		1.2f,0.7f-yOffSet,r,g,b,1.0f,0.0f,
	    		1.2f,0.6f-yOffSet,r,g,b,1.0f,1.0f,
	    		1.1f,0.6f-yOffSet,r,g,b,0.0f,1.0f,
		};
		
		addTabToTabVertex(tabVertexSquare);
		
		this.lenTabV += 28;

		
		this.elements[this.lenTabE] = this.indexTabE;
		this.elements[this.lenTabE+1] = this.indexTabE+1;
		this.elements[this.lenTabE+2] = this.indexTabE+2;
		
		this.elements[this.lenTabE+3] = this.indexTabE;
		this.elements[this.lenTabE+4] = this.indexTabE+2;
		this.elements[this.lenTabE+5] = this.indexTabE+3;
	    

		this.lenTabE += 6;
		this.indexTabE += 4;
		
		// Ajout du premier chiffre
		
		float tabVertex1stFig[] = new float[] {
				
	    		1.2f,0.7f-yOffSet,1.0f,1.0f,1.0f,0.0f,0.0f, //Square
	    		1.3f,0.7f-yOffSet,1.0f,1.0f,1.0f,1.0f,0.0f,
	    		1.3f,0.6f-yOffSet,1.0f,1.0f,1.0f,1.0f,1.0f,
	    		1.2f,0.6f-yOffSet,1.0f,1.0f,1.0f,0.0f,1.0f,
		};
		
		addTabToTabVertex(tabVertex1stFig);
		
		this.lenTabV += 28;
		
		this.elements[this.lenTabE] = this.indexTabE;
		this.elements[this.lenTabE+1] = this.indexTabE+1;
		this.elements[this.lenTabE+2] = this.indexTabE+2;
		
		this.elements[this.lenTabE+3] = this.indexTabE;
		this.elements[this.lenTabE+4] = this.indexTabE+2;
		this.elements[this.lenTabE+5] = this.indexTabE+3;
	    
		this.lenTabE += 6;
		this.indexTabE += 4;
		

		// Ajout du second ciffre
		
		float tabVertex2ndFig[] = new float[] {
				
	    		1.3f,0.7f-yOffSet,1.0f,1.0f,1.0f,0.0f,0.0f, //Square
	    		1.4f,0.7f-yOffSet,1.0f,1.0f,1.0f,1.0f,0.0f,
	    		1.4f,0.6f-yOffSet,1.0f,1.0f,1.0f,1.0f,1.0f,
	    		1.3f,0.6f-yOffSet,1.0f,1.0f,1.0f,0.0f,1.0f,
		};
		
		addTabToTabVertex(tabVertex2ndFig);
		
		this.lenTabV += 28;
	
		this.elements[this.lenTabE] = this.indexTabE;
		this.elements[this.lenTabE+1] = this.indexTabE+1;
		this.elements[this.lenTabE+2] = this.indexTabE+2;
		
		this.elements[this.lenTabE+3] = this.indexTabE;
		this.elements[this.lenTabE+4] = this.indexTabE+2;
		this.elements[this.lenTabE+5] = this.indexTabE+3;
	    
		this.lenTabE += 6;
		this.indexTabE += 4;
		
		
		// On augmente l'offset en y
		this.yOffSet += 0.1f;
	}
	
	public Integer[] scoreToIdTex(Joueur j) {
		
		Integer[] tab = new Integer[2];
		String strScore = String.valueOf(j.getScore());
		if(strScore.length()<2) strScore = "0" + strScore;
		
		for(int i = 0; i<2 ; i++) { // Pour des scores à 2 chiffres
			switch(strScore.charAt(i)) {
				case '0' : tab[i] = idTexFig0;
					break;
				case '1' : tab[i] = idTexFig1;
					break;
				case '2' : tab[i] = idTexFig2;
					break;
				case '3' : tab[i] = idTexFig3;
					break;
				case '4' : tab[i] = idTexFig4;
					break;
				case '5' : tab[i] = idTexFig5;
					break;
				case '6' : tab[i] = idTexFig6;
					break;
				case '7' : tab[i] = idTexFig7;
					break;
				case '8' : tab[i] = idTexFig8;
					break;
				case '9' : tab[i] = idTexFig9;
					break;
			}
						
		}
		
		return tab;
		
	}
	
	
	/**
	 * Cette méthode permet d'ajouter un tableau de float donné en paramètre 
	 * à la fin du tableau de Vertex. 
	 * @param tab
	 * 			Tableau de l'on veut ajouter au tableau de vertex.
	 */
	public void addTabToTabVertex(float tab[])
	{
		for(int i = this.lenTabV ; i<tab.length+this.lenTabV; i++)
		{
			
			this.tabVertex[i] = tab[i-this.lenTabV];
			//System.out.println("i : " +  i);
			
		}
	}
	


}
