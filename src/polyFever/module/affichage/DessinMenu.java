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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import polyFever.module.main.PolyFever;
import polyFever.module.menu.Bouton;
import polyFever.module.menu.Menu;
import polyFever.module.moteurDeJeu.Joueur;
import polyFever.module.moteurDeJeu.Partie;
import polyFever.module.util.PNGDecoder;
import polyFever.module.util.PNGDecoder.Format;
import polyFever.module.util.math.Vector2;

/**
 * <p>
 * La classe DessinMenu affiche le menu du jeu. 
 * </p>
 *  
 * @author Elouarn Lainé 
 *
 */
public class DessinMenu {
	
	/**
	 * Description des attributs :
	 * - nbVertex : 
	 * - affichage :
	 * - polyFever :
	 * - j :
	 * - program : 
	 * - ebo : 
	 * - vbo :
	 * - posAttrib : 
	 * - colAttrib :
	 * - texAttrib :
	 * - projectionUniform : 
	 * - id : 
	 * - tabVertex :
	 * - elements :
	 * - lenTabV : 
	 * - lenTabE :
	 * - indexTabE : 
	 * - vboBuffer :
	 * - eboBuffer : 
	 * - NBCOTES :
	 * - ProjectionMatrix :
	 * 
	 * @author Elouarn Lainé
	 */
	private int nbVertex;

	private Affichage affichage;
	private PolyFever polyFever;
	private Joueur j;
	
	//private float decalage; // PROVISOIRE ONLY FOR TEST PURPOSE
	
	private int program, ebo,vbo, posAttrib, colAttrib, texAttrib, uniColor, projectionUniform;
	
	private int idTexMenuTheme4pipes, idTexMenuTheme2pipes, idTexTitlePolyFever, idTexTitlePlay, idTexTitleLAN,
	idTexButtonCredits, idTexButtonHost, idTexButtonLAN, idTexButtonLocal, idTexButtonPlay, idTexButtonQuit, idTexButtonSettings, idTexButtonBack;  // indentifiant des textures
	
	private int idTexFond, idTexTitre;
	private int tabIdBoutons[];
	private int indiceBouton;
	
	
	private float tabVertex[];
	private int elements[];
	private int lenTabV;
	private int lenTabE;
	private int indexTabE;
	FloatBuffer vboBuffer;
	IntBuffer eboBuffer;
	
	private final int NBCOTES = 15; // Nombre de côtés du point du joueur
	//private float colDelta;
	
	private FloatBuffer projectionMatrix;
	
	public DessinMenu(Affichage a, PolyFever p)
	{

		//this.colDelta = 0.01f;
		
		
		this.affichage = a;
		this.polyFever = p;
		//this.partie = partie;
		
		
		
		this.nbVertex = 0;
		this.lenTabV = 0;
		this.lenTabE = 0;
		this.indexTabE = 0;
		this.tabVertex = new float[1000000];
		this.elements = new int[1000000];
		
		this.projectionMatrix = this.polyFever.getGlOrtho().getProjectionBuf();
	};	
	
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
	 * 
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
				//glUniformMatrix4(projectionUniform, false, this.projectionMatrix);

				uniColor = glGetUniformLocation(program, "Color");
				
				posAttrib = glGetAttribLocation(program, "position");
				colAttrib = glGetAttribLocation(program, "color");
				texAttrib = glGetAttribLocation(program, "texcoord");
				
				System.out.println("posAttrib: ".concat(String.valueOf(posAttrib)));
				System.out.println("colAttrib: ".concat(String.valueOf(colAttrib)));
				System.out.println("texAttrib: ".concat(String.valueOf(texAttrib)));
				//System.out.println("uniColor: ".concat(String.valueOf(uniColor)));
				
				glDetachShader(program, vs);
				glDetachShader(program, fs);
				
				glBindAttribLocation(program, posAttrib, "position"); // on bind l'attribut position à 0 // PB CERTAINEMENT ICI !!!!!!!!!!!!!!!!!!!!
				glBindAttribLocation(program, colAttrib, "color"); // on bind l'attribut position à // PB CERTAINEMENT ICI !!!!!!!!!!!!!!!!!!!!
				glBindAttribLocation(program, texAttrib, "texcoord");
				
				//PNGtoTex();
				
				
				glDeleteShader(vs);
				glDeleteShader(fs);
				
				/*new float[] {-0.5f,0.5f,1.0f,1.0f,1.0f,
				 * 
							0.5f,0.5f,1.0f,1.0f,1.0f,
							0.5f,-0.5f,1.0f,1.0f,1.0f,
							-0.5f,-0.5f,1.0f,1.0f,1.0f};
				
				new int[] {0, 1, 2
						   0, 2, 3};*/
				
				
				
				
				
				
				vbo = glGenBuffers(); // ebo : Elements Buffer Object (plus adapté que les vbo (vertex buffer object pour le dessin de multiple objets)
				vboBuffer = (FloatBuffer)BufferUtils.createFloatBuffer(this.tabVertex.length).put(this.tabVertex).flip();  // IMPORTANT : TRACER LES JOUEURS AU DEBUT DU BUFFER DE VERTEX
				glBindBuffer(GL_ARRAY_BUFFER, vbo);  // Fait en sorte que le ebo soit l'objet actif

				this.nbVertex += 12 ; 
				glBufferData(GL_ARRAY_BUFFER, vboBuffer, GL_STREAM_DRAW); // Est appliqué sur le vbo actif
				

				ebo = glGenBuffers(); // ebo : Elements Buffer Object (plus adapté que les vbo (vertex buffer object pour le dessin de multiple objets)
				eboBuffer = (IntBuffer)BufferUtils.createIntBuffer(this.elements.length).put(this.elements).flip();
				glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);  // Fait en sorte que le ebo soit l'objet actif
				
				
				glBufferData(GL_ELEMENT_ARRAY_BUFFER, eboBuffer, GL_STREAM_DRAW); // Est appliqué sur le vbo actif
				
				
				glBindVertexArray(glGenVertexArrays()); // Création d'un VAO : Vertex Array Object avec glGenVertexArrays() . le VAO stock les liens entre les attributs et les VBO
				// le VAO contient une référence vers le VBO
				
				glBindBuffer(GL_ARRAY_BUFFER, 0);
				
				
				// CHARGEMENT DES TEXTURES // 

				// Les fonds
				
				idTexMenuTheme4pipes = GL11.glGenTextures(); // 1
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexMenuTheme4pipes);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 1080, 1080, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/menu_theme_4pipes.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexMenuTheme4pipes);
			    
				idTexMenuTheme2pipes = GL11.glGenTextures(); // 2
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexMenuTheme2pipes);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 1080, 1080, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/menu_theme_2pipes.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexMenuTheme2pipes);
			    
			    
			    // Les titres
			    
			    idTexTitlePolyFever = GL11.glGenTextures(); // 3
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitlePolyFever);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 1080, 198, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/title_PolyFever.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitlePolyFever);
			   
			    idTexTitlePlay = GL11.glGenTextures(); // 4
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitlePlay);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 1080, 198, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/title_Play.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitlePlay);
			    
			    idTexTitleLAN = GL11.glGenTextures(); // 5
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitleLAN);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 1080, 198, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/title_LAN.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitleLAN);
			    
			    
			    // Les boutons
			    
			    idTexButtonCredits = GL11.glGenTextures(); // 6
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonCredits);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 387, 123, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/bouton_credits.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonCredits);
			    
			    idTexButtonHost = GL11.glGenTextures(); // 7
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonHost);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 387, 123, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/bouton_host.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonHost);
			    
			    idTexButtonLAN = GL11.glGenTextures(); // 8
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonLAN);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 387, 123, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/bouton_lan_multi.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonLAN);
			    
			    idTexButtonLocal = GL11.glGenTextures(); // 9
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonLocal);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 387, 123, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/bouton_local_multi.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonLocal);
			    
			    idTexButtonPlay = GL11.glGenTextures(); // 10
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonPlay);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 387, 123, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/bouton_play.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonPlay);
			    
			    idTexButtonQuit = GL11.glGenTextures(); // 11
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonQuit);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 387, 123, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/bouton_quit.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonQuit);
			    
			    idTexButtonSettings = GL11.glGenTextures(); // 12
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonSettings);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 387, 123, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/bouton_settings.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonSettings);
			    
			    idTexButtonBack = GL11.glGenTextures(); // 13
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonBack);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 235, 83, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/bouton_retour.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexButtonBack);
			    
			    // FIN CHARGEMENT DES TEXTURES // 
			    
			    

	}
	
	
	public void dessiner()
	{
		//System.out.println("	dessiner dMenu");
		
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
		//glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0); // 0 : position a la location 0 par défaut.  A l'appelle de cette fonction les infos vont être stockées dans le VAO courant. 
		glVertexAttribPointer(posAttrib, 2, GL_FLOAT, false,7*4, 0);
		
		glEnableVertexAttribArray(colAttrib);
		glVertexAttribPointer(colAttrib, 3, GL_FLOAT, false,7*4, 2*4);
		
		glEnableVertexAttribArray(texAttrib);
		glVertexAttribPointer(texAttrib, 2, GL_FLOAT, false, 7*4, 5*4);
		
		
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexFond); // On bind la premiere texture
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0); 
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // On met la texture à NULL
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitre); // On bind la deuxième texture
		glDrawElements(GL_TRIANGLES, 6/*this.nbVertex*/, GL_UNSIGNED_INT,6*4); // Deuxième argument, nombre de vertices à tracer 6 apres 6*4bytes d'offset
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // On met la texture à NULL
		
		for(int i=0; i<this.indiceBouton; i++)
		{
			//System.out.println("IdBouton :" + tabIdBoutons[i]);
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tabIdBoutons[i]); // On bind la premiere texture
			glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 12*4+(6*4*i)); 
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // On met la texture à NULL
		}
		
		
		glDisableVertexAttribArray(posAttrib);
		glDisableVertexAttribArray(colAttrib);
		glDisableVertexAttribArray(texAttrib);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glUseProgram(0);

	}
	
	public void updateMenu(Menu curMenu){

		this.indiceBouton = 0;
		this.tabIdBoutons = new int[] {0,0,0,0,0} ;
		this.lenTabV = 0;
		this.lenTabE = 0;
		this.indexTabE = 0;
	    addMenuAndTitle(curMenu); 
	    
	    Bouton b;
	    Iterator<Bouton> i =curMenu.getBoutons().iterator();
	    while(i.hasNext()) //On parcours les Boutons
	    { 
			b = i.next();
			addButton(b);
		}
	    

	    
	    //this.printTabVertex();
	}
	
	public void addMenuAndTitle(Menu m)
	{

		switch(m.getImgFond()){
		
			case 1: idTexFond = idTexMenuTheme4pipes;
					break;
			
			case 2: idTexFond = idTexMenuTheme2pipes;
					break;
			
		}
		
		
		switch(m.getImgTitre()){
		
			case 1: idTexTitre = idTexTitlePolyFever;
					break;
			
			case 2: idTexTitre = idTexTitlePlay;
					break;
			
			case 3: idTexTitre = idTexTitleLAN; 
					break;
		
		}
		
		//System.out.println("getImgFond() et getImgTitre()" + m.getImgFond() + " " + m.getImgTitre());
		//System.out.println("idTexFond et idTexTitre " + idTexFond + " " + idTexTitre);
		//System.out.println("idTexMenuTheme4pipes et idTexTitlePolyFever " + idTexMenuTheme4pipes + " " + idTexTitlePolyFever);
		
		
		float tabVertexMenu[] = new float[] {
	    		-1.0f,1.0f,1.0f,1.0f,1.0f,0.0f,0.0f, // Fond menu
				1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,0.0f,
				1.0f,-1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,
				-1.0f,-1.0f,1.0f,1.0f,1.0f,0.0f,1.0f,
				
				-1.0f,1.0f,1.0f,1.0f,1.0f,0.0f,0.0f, // Titre menu
				1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,0.0f,
				1.0f,0.6333f,1.0f,1.0f,1.0f,1.0f,1.0f,
				-1.0f,0.6333f,1.0f,1.0f,1.0f,0.0f,1.0f
		};
		
		addTabToTabVertex(tabVertexMenu);
		
		this.lenTabV += 56;
		
	    this.vboBuffer.put(this.tabVertex); // On met a jour le buffer VBO 
		this.vboBuffer.clear();
	    
		this.elements[this.lenTabE] = this.indexTabE;
		this.elements[this.lenTabE+1] = this.indexTabE+1;
		this.elements[this.lenTabE+2] = this.indexTabE+2;
		
		this.elements[this.lenTabE+3] = this.indexTabE;
		this.elements[this.lenTabE+4] = this.indexTabE+2;
		this.elements[this.lenTabE+5] = this.indexTabE+3;
		
		this.elements[this.lenTabE+6] = this.indexTabE+4;
		this.elements[this.lenTabE+7] = this.indexTabE+5;
		this.elements[this.lenTabE+8] = this.indexTabE+6;
		
		this.elements[this.lenTabE+9] = this.indexTabE+4;
		this.elements[this.lenTabE+10] = this.indexTabE+6;
		this.elements[this.lenTabE+11] = this.indexTabE+7;
	    
		this.eboBuffer.put(this.elements);
		this.lenTabE += 12;
		this.eboBuffer.clear();
		
		this.indexTabE += 8;
	}
	
	public void addButton(Bouton b)
	{

		switch(b.getImgBouton()){
		
			case 1: tabIdBoutons[this.indiceBouton] = idTexButtonPlay; //System.out.println("case : "+ tabIdBoutons[this.indiceBouton]);
					break;
			
			case 2: tabIdBoutons[this.indiceBouton] = idTexButtonSettings; //System.out.println("case : "+ tabIdBoutons[this.indiceBouton]);
					break;
			
			case 3: tabIdBoutons[this.indiceBouton] = idTexButtonCredits;  //System.out.println("case : "+ tabIdBoutons[this.indiceBouton]);
					break;
					
			case 4: tabIdBoutons[this.indiceBouton] = idTexButtonQuit;  //System.out.println("case : "+ tabIdBoutons[this.indiceBouton]);
					break;
			
			case 5: tabIdBoutons[this.indiceBouton] = idTexButtonLAN; // System.out.println("case : "+ tabIdBoutons[this.indiceBouton]);
					break;
			
			case 6: tabIdBoutons[this.indiceBouton] = idTexButtonLocal; // System.out.println("case : "+ tabIdBoutons[this.indiceBouton]);
					break;
					
			case 7: tabIdBoutons[this.indiceBouton] = idTexButtonHost; // System.out.println("case : "+ tabIdBoutons[this.indiceBouton]);
					break;
			
			case 8: tabIdBoutons[this.indiceBouton] = idTexButtonBack;  //System.out.println("case : "+ tabIdBoutons[this.indiceBouton]);
					break;
	
		}
		
		float x = b.getX();
		float y = b.getY();
		float w = b.getL();
		float h = b.getH();
		
		
		float tabVertexButton[] = new float [] {
	    		x-(w/2),y+(h/2),1.0f,1.0f,1.0f,0.0f,0.0f, // Fond menu
	    		x+(w/2),y+(h/2),1.0f,1.0f,1.0f,1.0f,0.0f,
	    		x+(w/2),y-(h/2),1.0f,1.0f,1.0f,1.0f,1.0f,
	    		x-(w/2),y-(h/2),1.0f,1.0f,1.0f,0.0f,1.0f
		};
		
		addTabToTabVertex(tabVertexButton);
		
		this.lenTabV += 28;
	    this.vboBuffer.put(this.tabVertex); // On met a jour le buffer VBO 
		this.vboBuffer.clear();
		
		this.elements[this.lenTabE] = this.indexTabE;
		this.elements[this.lenTabE+1] = this.indexTabE+1;
		this.elements[this.lenTabE+2] = this.indexTabE+2;
		
		this.elements[this.lenTabE+3] = this.indexTabE;
		this.elements[this.lenTabE+4] = this.indexTabE+2;
		this.elements[this.lenTabE+5] = this.indexTabE+3;
		
		
		this.eboBuffer.put(this.elements);
		this.lenTabE += 6;
		this.eboBuffer.clear();
		
		this.indexTabE += 4;
		
		this.indiceBouton++;
	}
	
	public void addTabToTabVertex(float tab[])
	{
		for(int i = this.lenTabV ; i<tab.length+this.lenTabV; i++)
		{
			
			this.tabVertex[i] = tab[i-this.lenTabV];
			//System.out.println("i : " +  i);
			
		}
	}
	
	public void printTabVertex()
	{
		for(int i = 0; i<400; i++)
		{
			if(i%7==0) System.out.println();
			if(i%28==0) System.out.println("_______________ new element _______________");
			System.out.print(this.tabVertex[i]+", ");
		}
		System.out.println("_______________________________________________");
	}

}
