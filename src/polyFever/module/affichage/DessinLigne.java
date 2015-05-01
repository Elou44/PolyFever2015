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



public class DessinLigne  { // peut être instancier un tableau de DessinLigne dans DessinPlateau (1 par joueur)
	
	private int nbVertex;
	private Affichage affichage;
	private PolyFever polyFever;
	private Partie partie;
	private List<Float> tabVertex;
	private float monTab[];
	private int program, ebo,vbo, uniColor;
	private int elements[];
	private long t_start;
	
	public DessinLigne(int width, int height, Affichage a, PolyFever p, Partie partie)
	{
		this.nbVertex = 6;
		this.affichage = a;
		this.polyFever = p;
		this.partie = partie;
		this.tabVertex = new ArrayList<Float>();
		this.monTab = new float[]{ 
				-0.75f, 0.0f,
				0.0f, 0.0f,
				0.0f, -0.75f,
				-0.75f, -0.75f				
				};
		
		};
			
	
	public void init()
	{
		
		System.out.println("Initialisation pour traçage des Lignes...");
		
		t_start = System.currentTimeMillis();
		
		glClearColor(0, 0, 0, 0);
		
		int vs = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vs, polyFever.readFromFile("example1.1.vert")); // Chargement du vertex shader (vs)
		
		glCompileShader(vs); // Compilation du vertex shader
		
		if(glGetShaderi(vs, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failure in compiling vertex shader. Error log:\n" + glGetShaderInfoLog(vs, glGetShaderi(vs, GL_INFO_LOG_LENGTH)));
			System.exit(0);
		}
		
		int fs = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fs, polyFever.readFromFile("example1.1.frag")); // Chargement du fragment shader (fs)
		
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
		
		uniColor = glGetUniformLocation(program, "triangleColor");
		
		glDetachShader(program, vs);
		glDetachShader(program, fs);
		
		glBindAttribLocation(program, 0, "position");
		
		glDeleteShader(vs);
		glDeleteShader(fs);
		
		
		vbo = glGenBuffers(); // ebo : Elements Buffer Object (plus adapté que les vbo (vertex buffer object pour le dessin de multiple objets)
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);  // Fait en sorte que le ebo soit l'objet actif
		
		glBufferData(GL_ARRAY_BUFFER, (FloatBuffer)BufferUtils.createFloatBuffer(this.monTab.length).put(this.monTab).flip(), GL_STREAM_DRAW); // Est appliqué sur le vbo actif
		
		
		
		
		ebo = glGenBuffers(); // ebo : Elements Buffer Object (plus adapté que les vbo (vertex buffer object pour le dessin de multiple objets)
		
		this.elements = new int[]{
				0, 1, 2,
				2, 3 ,0
		};
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);  // Fait en sorte que le ebo soit l'objet actif
		
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, (IntBuffer)BufferUtils.createIntBuffer(this.elements.length).put(this.elements).flip(), GL_STREAM_DRAW); // Est appliqué sur le vbo actif
		

		
		glBindVertexArray(glGenVertexArrays()); // Création d'un VAO : Vertex Array Object avec glGenVertexArrays() . le VAO stock les liens entre les attributs et les VBO
		// le VAO contient une référence vers le VBO
		
		//glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		
	}
	
	
	public void dessiner()
	{
		//System.out.println("			dessiner dLigne");
		long t_now = System.currentTimeMillis();
		float time = t_now - t_start;
		
		
		glClear(GL_COLOR_BUFFER_BIT);
		
		glUseProgram(program);
		
		
		double newColor = Math.sin(time/1000 + 4.0f);
		//System.out.println(time);
		glUniform3f(uniColor, (float) newColor, 0.0f, 0.0f); // change la couleur du triangle en rouge
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0); // 0 : position a la location 0 par défaut.  A l'appelle de cette fonction les infos vont être stockées dans le VAO courant. 
		
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0); // essayer avec glDrawElements (https://open.gl/drawing)
		
		glDisableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glUseProgram(0);
	}
	
	private void addRectangle(Vector4 v, float angle, float w, float h) // v : point d'encrage (milieu du bord supérieur)
	{
		
		w = w*affichage.getRATIOPIXWIDTH(); // conversion pixel vers float
		h = h*affichage.getRATIOPIXHEIGHT(); // conversion pixel vers float
		
		Vector4 p1 = new Vector4();
		
		double xd =  v.x() + (w/2)*Math.cos(angle);
		float x = (float) xd;
		double yd =  v.y() + (w/2)*Math.sin(angle);
		float y = (float) yd;
		p1.set( x, y, 0.0f, 1.0f);
		
		
		Vector4 p2 = new Vector4();	
		
		xd =  p1.x() - h*Math.cos(Math.PI-angle);
		x = (float) xd;
		yd =  p1.y() + h*Math.sin(Math.PI-angle);
		y = (float) yd;
		p2.set( x, y, 0.0f, 1.0f);
		
		
		Vector4 p4 = new Vector4();
		xd =  v.x() - (w/2)*Math.cos(angle);
		x = (float) xd;
		yd =  v.y() - (w/2)*Math.sin(angle);
		y = (float) yd;
		p4.set( x, y, 0.0f, 1.0f);
		
		
		Vector4 p3 = new Vector4();
		
		xd =  p4.x() - h*Math.cos(Math.PI-angle);
		x = (float) xd;
		yd =  p4.y() + h*Math.sin(Math.PI-angle);
		y = (float) yd;
		p3.set( x, y, 0.0f, 1.0f);
		
		ajouterVector4Rect(tabVertex, p1, p2, p3, p4);
		this.nbVertex += 6;
		
		// Peut etre utiliser matrice de rotation ? 
		
		
	}
	
	private void ajouterVector4Rect(List<Float> tab, Vector4 p1, Vector4 p2, Vector4 p3, Vector4 p4)
	{

		tab.add(p1.x());
		tab.add(p1.y());
		tab.add(p1.z()); // A modifier pour opti ? tab.add(1.0f);
		tab.add(p1.w()); // A modifier pour opti ? tab.add(0.0f);
		
		tab.add(p3.x());
		tab.add(p3.y());
		tab.add(p3.z());
		tab.add(p3.w());
		
		tab.add(p4.x());
		tab.add(p4.y());
		tab.add(p4.z());
		tab.add(p4.w());
		
		
		tab.add(p1.x());
		tab.add(p1.y());
		tab.add(p1.z());
		tab.add(p1.w());
		
		tab.add(p2.x());
		tab.add(p2.y());
		tab.add(p2.z());
		tab.add(p2.w());
		
		tab.add(p3.x());
		tab.add(p3.y());
		tab.add(p3.z());
		tab.add(p3.w());
		
	}

}
