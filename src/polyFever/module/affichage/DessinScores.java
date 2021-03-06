package polyFever.module.affichage;


import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
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
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import polyFever.module.main.PolyFever;
import polyFever.module.moteurDeJeu.Joueur;
import polyFever.module.moteurDeJeu.Partie;
import polyFever.module.util.PNGDecoder;
import polyFever.module.util.PNGDecoder.Format;


/**
 * Classe devant se charger du dessin des scores � l'�cran. (pas encore impl�ment�e)
 * @author Elouarn Lain�
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
	 * program regroupe le vertex shader et le fragment shader (les shaders sont des programmes ex�cut�s sur le GPU)
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
	 * Identifiants des diff�rentes textures du menus
	 */
	private int idTexFig0, idTexFig1, idTexFig2, idTexFig3, idTexFig4, idTexFig5, idTexFig6, idTexFig7, idTexFig8, idTexFig9, idTexTitle, idTexPlayerLogo;  // indentifiant des textures
		
	/**
	 * Tableau de floats contenant toutes les informations des vertices, soit ici 7 floats par vertex
	 *  (2 floats pour la position du vertex, 3 floats pour sa couleur (RGB), et 2 floats pour la position de la texture)
	 */
	private float tabVertex[];
	
	/**
	 * Tableau d'entier contenant l'ordre de tra�age des vertices, ceci afin de limiter le nombre de vertex � utiliser
	 *  ( 4 vertices au lieu de 6 n�cessaires sans cette technique pour dessiner un rectangle)
	 */
	private int elements[];
	
	/**
	 * Longueur r�elle du tableau de vertex.
	 */
	private int lenTabV;
	
	/**
	 * Longueur r�elle du tableau d'elements.
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
	 * FloatBuffer contenant la matrice de projection. La position des vertices est multipli�e 
	 * par cette martrice afin d'annuler l'effet de distortion qui apparait lorsque la fen�tre devient rectangulaire.
	 */
	private FloatBuffer projectionMatrix;
	
	private float[] titleBox;
	private float yOffSet;
	
	private Joueur j;
	
	/**
	 * Liste ordonn� (selon le score) des joueurs de la partie. 
	 */
	private Joueur[] playerListSortedByScore;
	
	
	/**
	 * Constructeur de la classe DessinMenu
	 * Initialisation des attributs. instanciation de 2 tableaux d'un million d'�l�ments chacun 
	 * qui contiendront respectivement les vertex et les elements.
	 * @param a
	 * 		r�f�rence vers l'objet Affichage.
	 * @param p
	 * 		r�f�rence vers l'objet PolyFever.
	 * 
	 * @author Elouarn Lain�
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
		this.playerListSortedByScore = new Joueur[this.partie.getNbJoueurs()];
		
		this.projectionMatrix = this.polyFever.getGlOrtho().getProjectionBuf();
	};	
	
	
	/**
	 * M�thode r�alisant le chargement d'une image au format PNG dans un ByteBuffer.
	 * 
	 * @param path
	 * 			Chemin vers l'image.
	 * @return ByteBuffer
	 * 			Buffer contenant l'image.
	 */
	public ByteBuffer PNGtoTex(InputStream path)
	{
		ByteBuffer pixelData = BufferUtils.createByteBuffer(0); // On initialise le ByteBuffer avec une texture vide
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("polyFever/module/images/menu_theme_4pipes.png")){
		    //Create the PNGDecoder object and decode the texture to a buffer
		    PNGDecoder decoder = new PNGDecoder(path);
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
	 * Initialisation des objets n�cessaires � l'affichage 
	 * (cr�ation du program shader, cr�ation de l'eboBuffer et du vboBuffer, chargemement des textures).
	 */
	public void init()
	{
		
		//partie.envoyerTabVertex(tabVertex, lenTabV); // Envoie de la r�f�rence du tableau � l'objet partie
		
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
				
		
		program = glCreateProgram(); // Cr�ation du programme auquel sera rattach� les shaders
		
	
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

		glDetachShader(program, vs);
		glDetachShader(program, fs);
		
		glBindAttribLocation(program, posAttrib, "position"); // on bind l'attribut position � l'entier posAttrib. 
		glBindAttribLocation(program, colAttrib, "color"); // on bind l'attribut color � l'entier colAttrib.
		glBindAttribLocation(program, texAttrib, "texcoord"); // on bind l'attribut texcoord � l'entier texAttrib.
		

		glDeleteShader(vs);
		glDeleteShader(fs);
		
		
		vbo = glGenBuffers(); // ebo : Elements Buffer Object (plus adapt� que les vbo (vertex buffer object pour le dessin de multiple objets)
		vboBuffer = (FloatBuffer)BufferUtils.createFloatBuffer(this.tabVertex.length).put(this.tabVertex).flip();  // IMPORTANT : TRACER LES JOUEURS AU DEBUT DU BUFFER DE VERTEX
		glBindBuffer(GL_ARRAY_BUFFER, vbo);  // Fait en sorte que le ebo soit l'objet actif

		glBufferData(GL_ARRAY_BUFFER, vboBuffer, GL_STREAM_DRAW); // Est appliqu� sur le vbo actif
		

		ebo = glGenBuffers(); // ebo : Elements Buffer Object (plus adapt� que les vbo (vertex buffer object pour le dessin de multiple objets)
		eboBuffer = (IntBuffer)BufferUtils.createIntBuffer(this.elements.length).put(this.elements).flip();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);  // Fait en sorte que le ebo soit l'objet actif
		
		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, eboBuffer, GL_STREAM_DRAW); // Est appliqu� sur le vbo actif
		
		
		glBindVertexArray(glGenVertexArrays()); // Cr�ation d'un VAO : Vertex Array Object avec glGenVertexArrays() . le VAO stock les liens entre les attributs et les VBO
		// le VAO contient une r�f�rence vers le VBO
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		
		// CHARGEMENT DES TEXTURES // 

		// Les Chiffres
		
		idTexFig0 = GL11.glGenTextures(); // 0
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig0);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex((InputStream) this.getClass().getClassLoader().getResourceAsStream("polyFever/module/images/num0.png")));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig0);
		
	    idTexFig1 = GL11.glGenTextures(); // 1
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig1);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex((InputStream) this.getClass().getClassLoader().getResourceAsStream("polyFever/module/images/num1.png")));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig1);
	    
	    idTexFig2 = GL11.glGenTextures(); // 2
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig2);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex((InputStream) this.getClass().getClassLoader().getResourceAsStream("polyFever/module/images/num2.png")));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig2);
	    
	    idTexFig3 = GL11.glGenTextures(); // 3
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig3);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex((InputStream) this.getClass().getClassLoader().getResourceAsStream("polyFever/module/images/num3.png")));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig3);
	   
	    idTexFig4 = GL11.glGenTextures(); // 4
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig4);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex((InputStream) this.getClass().getClassLoader().getResourceAsStream("polyFever/module/images/num4.png")));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig4);
	    
	    idTexFig5 = GL11.glGenTextures(); // 5
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig5);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex((InputStream) this.getClass().getClassLoader().getResourceAsStream("polyFever/module/images/num5.png")));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig5);
	    
	    idTexFig6 = GL11.glGenTextures(); // 6
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig6);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex((InputStream) this.getClass().getClassLoader().getResourceAsStream("polyFever/module/images/num6.png")));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig6);
	    
	    idTexFig7 = GL11.glGenTextures(); // 7
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig7);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex((InputStream) this.getClass().getClassLoader().getResourceAsStream("polyFever/module/images/num7.png")));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig7);
	    
	    idTexFig8 = GL11.glGenTextures(); // 8
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig8);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex((InputStream) this.getClass().getClassLoader().getResourceAsStream("polyFever/module/images/num8.png")));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig8);
	    
	    idTexFig9 = GL11.glGenTextures(); // 9
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig9);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex((InputStream) this.getClass().getClassLoader().getResourceAsStream("polyFever/module/images/num9.png")));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFig9);
	    
	    idTexTitle = GL11.glGenTextures(); // Title
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitle);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 300, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex((InputStream) this.getClass().getClassLoader().getResourceAsStream("polyFever/module/images/scoresTitle.png")));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitle);
	    
	    idTexPlayerLogo = GL11.glGenTextures(); // Player Logo
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexPlayerLogo);
	        //Upload the buffer's content to the VRAM
	        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 50, 50, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex((InputStream) this.getClass().getClassLoader().getResourceAsStream("polyFever/module/images/playerLogo.png")));
	        //Apply filters
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexPlayerLogo);
	    
	    
	    
	    
	    // FIN CHARGEMENT DES TEXTURES // 
	    
	    initListPlayer(); // On remplie le tableau playerListSortedByScore;
	    updateScores();

	}

	
	/**
	 * <p> 
	 * L'affichage du buffer de vertex se d�roule en plusieurs �tapes : 
	 * <ul>
	 * 	<li>On recharge l'eboBuffer et le vboBuffer dans la VRAM.</li>
	 *  <li>On efface l'�cran.</li>
	 *  <li>On active le program shader.</li>
	 *  <li>On met � jour la matrice de projection du vertex shader.</li>
	 *  <li>On bind l'ebo et le vbo au contexte openGL pour indiquer � ce dernier qu'il devra les utiliser pour l'affichage.</li>
	 *  <li>On indique � openGL comment il doit interpr�ter le buffer de vertex ( 2 premiers floats pour la position, les 3 suivants pour la couleurs et les deux derniers pour la position de la texture.</li>
	 *  <li>On bind la texture voulue pour indiquer � openGL qu'il doit utiliser cette texture lors de l'affichage.</li>
	 *  <li>La m�thode scoreToIdTex permet de renvoyer l'id de la texture � utiliser (pour l'affichage des nombres)
	 *  <li>On trace un certain nombre de vertex avec cette texture</li>
	 *  <li>Avant de changer de texture, on l'unbind.</li>
	 *  <li>On bind une nouvelle texture et on trace d'autres vertices, et ainsi de suite pour tous les �l�ments du menu.</li>
	 *  <li>On d�tache les buffers (ebo et vbo) du contexte openGL.</li>
	 *  <li>On d�tache le program shader du contexte openGL.</li>
	 * </ul>
	 * </p>
	 */
	public void dessiner()
	{
		//System.out.println("		dessiner dScores");
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferSubData(GL_ARRAY_BUFFER, 0, vboBuffer);
		
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, eboBuffer);

		
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

		
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitle); // On bind la premiere texture
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0); 
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // On met la texture � NULL

		for(int i=0; i<this.playerListSortedByScore.length ; i++)
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexPlayerLogo); // On bind la premiere texture
			glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 6*4+(24*4*i));  // On trace le carr�
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // On met la texture � NULL
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, scoreToIdTex(this.playerListSortedByScore[i])[0]); // On bind la premiere texture
			glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 12*4+(24*4*i)); 
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // On met la texture � NULL
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, scoreToIdTex(this.playerListSortedByScore[i])[1]); // On bind la deuxieme texture
			glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 18*4+(24*4*i)); 
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // On met la texture � NULL
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, scoreToIdTex(this.playerListSortedByScore[i])[2]); // On bind latroisieme texture
			glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 24*4+(24*4*i)); 
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // On met la texture � NULL

		}

			

		
		
		
		glDisableVertexAttribArray(posAttrib);
		glDisableVertexAttribArray(colAttrib);
		glDisableVertexAttribArray(texAttrib);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0); 
		
		glUseProgram(0);
	}
	
	
	/**
	 * M�thode permettant de mettre � jour les attributs de la classe n�cessaires � l'affichage des nouveaux scores.
	 * Ceci consiste principalement � remettre de nouveaux vertex dans le tableau de vertex afin d'afficher les nouvelles 
	 * images correspondants aux nouveaux scores.
	 * 
	 * @author Elouarn Lain�
	 */
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
		
		sortPlayerList(); // On trie la liste des joueurs
		
		for(int i=0; i<this.playerListSortedByScore.length ; i++)
		{
			this.addScore(this.playerListSortedByScore[i]);
		}

			

		
		// On met � jour les vertex et elements buffer
		this.vboBuffer.put(this.tabVertex); // On met a jour le buffer VBO 
		this.vboBuffer.clear();
		
		this.eboBuffer.put(this.elements);
		this.eboBuffer.clear();
		
	}
	
	/**
	 * Cette m�thode permet d'ajouter les vertices n�cessaire � l'affichage du score d'un joueur. 
	 * (soit les vertices pour une image, et 3 chiffres) 
	 * @param j
	 * 		Joueur j : un joueur de la liste des joueurs de la partie.
	 */
	public void addScore(Joueur j) {
		
		// Ajout d'un carr� de couleur
		
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
				
	    		1.2f,0.7f-yOffSet,1.0f,1.0f,1.0f,0.0f,0.0f, //1st fig
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
				
	    		1.3f,0.7f-yOffSet,1.0f,1.0f,1.0f,0.0f,0.0f, //2nd fig
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
		
		// Ajout du 3eme ciffre
		
		float tabVertex3rdFig[] = new float[] {
				
	    		1.4f,0.7f-yOffSet,1.0f,1.0f,1.0f,0.0f,0.0f, //3rd fig
	    		1.5f,0.7f-yOffSet,1.0f,1.0f,1.0f,1.0f,0.0f,
	    		1.5f,0.6f-yOffSet,1.0f,1.0f,1.0f,1.0f,1.0f,
	    		1.4f,0.6f-yOffSet,1.0f,1.0f,1.0f,0.0f,1.0f,
		};
		
		addTabToTabVertex(tabVertex3rdFig);
		
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
	
	/**
	 * Cette m�thode permet de retourner les identifiants des textures n�cessaires � l'affichage du score d'un joueur.
	 * @param j
	 * 		Joueur j : un joueur de la liste des joueurs de la partie.
	 * @return Integer[]
	 * 		tableau d'identifiants des 3 textures n�cessaires � l'affichage du score d'un joueur 
	 */
	public Integer[] scoreToIdTex(Joueur j) {
		
		Integer[] tab = new Integer[3];
		String strScore = String.valueOf(j.getScore());
		if(strScore.length()==1) strScore = "00" + strScore;
		if(strScore.length()==2) strScore = "0" + strScore;
		
		for(int i = 0; i<3 ; i++) { // Pour des scores � 2 chiffres
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
	 * M�thode permettant d'initialiser le tableau ordonn� des joueurs de la partie.
	 */
	public void initListPlayer() {
		
		Iterator<Joueur> e = this.partie.getJoueurs().iterator();
		int i = 0;
		while(e.hasNext()) // A d�placer dans Init();
		{
			j = e.next();
			this.playerListSortedByScore[i] = j;
			i++;
		}
		
	}
	
	
	/**
	 * M�thode permettant de trier par ordre d�croissant de score le tableau ordonn� des joueurs de la partie.
	 */
	public void sortPlayerList() {
		
		boolean isSorted = false;
		
		while(!isSorted) // Tant que le tableau de joueur n'est pas tri�
		{
			isSorted = true;
			for(int i = 0; i<this.playerListSortedByScore.length-1; i++) {
				if(this.playerListSortedByScore[i].getScore() < this.playerListSortedByScore[i+1].getScore())
				{
					isSorted = false;
					j = this.playerListSortedByScore[i+1]; // On �change les deux joueurs (trie � bulle)
					this.playerListSortedByScore[i+1] = this.playerListSortedByScore[i];
					this.playerListSortedByScore[i] = j;
				}
			}
		}
		
	}
	
	
	/**
	 * Cette m�thode permet d'ajouter un tableau de float donn� en param�tre 
	 * � la fin du tableau de Vertex. 
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
