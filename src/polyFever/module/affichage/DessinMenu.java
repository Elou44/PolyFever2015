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

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import polyFever.module.main.PolyFever;
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
	private int idTexMenuTheme4pipes, idTexTitlePolyFever;  // indentifiant des textures
	
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
		
				System.out.println("Initialisation pour traçage des Lignes...");
				
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
				vboBuffer = (FloatBuffer)BufferUtils.createFloatBuffer(this.tabVertex.length).put(
						new float[] {-1.0f,1.0f,1.0f,1.0f,1.0f,0.0f,0.0f,
						1.0f,1.0f,1.0f,0.0f,1.0f,1.0f,0.0f,
						1.0f,-1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,
						-1.0f,-1.0f,1.0f,1.0f,1.0f,0.0f,1.0f,
						
						-1.0f,1.0f,1.0f,1.0f,1.0f,0.0f,0.0f,
						0.0f,1.0f,1.0f,0.0f,1.0f,2.0f,0.0f,
						0.0f,0.0f,1.0f,1.0f,1.0f,2.0f,2.0f,
						-1.0f,0.0f,1.0f,1.0f,1.0f,0.0f,2.0f
						
						
						}/*this.tabVertex*/).flip();  // IMPORTANT : TRACER LES JOUEURS AU DEBUT DU BUFFER DE VERTEX
				glBindBuffer(GL_ARRAY_BUFFER, vbo);  // Fait en sorte que le ebo soit l'objet actif

				this.nbVertex += 12 ; 
				glBufferData(GL_ARRAY_BUFFER, vboBuffer, GL_STREAM_DRAW); // Est appliqué sur le vbo actif
				

				ebo = glGenBuffers(); // ebo : Elements Buffer Object (plus adapté que les vbo (vertex buffer object pour le dessin de multiple objets)
				eboBuffer = (IntBuffer)BufferUtils.createIntBuffer(this.elements.length).put(new int[] {
						0, 1, 2,
						   0, 2, 3,
						   
						   4, 5, 6,
						   4, 6, 7
						   
				
				}/*this.elements*/).flip();
				glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);  // Fait en sorte que le ebo soit l'objet actif
				
				
				glBufferData(GL_ELEMENT_ARRAY_BUFFER, eboBuffer, GL_STREAM_DRAW); // Est appliqué sur le vbo actif
				
				
				glBindVertexArray(glGenVertexArrays()); // Création d'un VAO : Vertex Array Object avec glGenVertexArrays() . le VAO stock les liens entre les attributs et les VBO
				// le VAO contient une référence vers le VBO
				
				//glBindBuffer(GL_ARRAY_BUFFER, 0);
				
				
				// CHARGEMENT DES TEXTURES // 

				idTexMenuTheme4pipes = GL11.glGenTextures();
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexMenuTheme4pipes);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 1080, 1080, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/menu_theme_4pipes.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexMenuTheme4pipes);
				
			    
			    idTexTitlePolyFever = GL11.glGenTextures();
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitlePolyFever);
			        //Upload the buffer's content to the VRAM
			        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, 1080, 198, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, PNGtoTex("images/title_PolyFever.png"));
			        //Apply filters
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			    GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitlePolyFever);
				

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
		
		
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexMenuTheme4pipes); // On bind la premiere texture
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0); 
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0); // On met la texture à NULL
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, idTexTitlePolyFever); // On bind la deuxième texture
		glDrawElements(GL_TRIANGLES, 12/*this.nbVertex*/, GL_UNSIGNED_INT,6*4);
		
		glDisableVertexAttribArray(posAttrib);
		glDisableVertexAttribArray(colAttrib);
		glDisableVertexAttribArray(texAttrib);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glUseProgram(0);

	}
	
	public void updateMenu(Menu curMenu){
		//if(curMenu.)
	}

}
