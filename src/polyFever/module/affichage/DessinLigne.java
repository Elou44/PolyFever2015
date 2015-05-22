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
	private Joueur j;
	
	private float decalage; // PROVISOIRE ONLY FOR TEST PURPOSE
	
	private int program, ebo,vbo, posAttrib, colAttrib, uniColor, projectionUniform;
	
	private float tabVertex[];
	private int elements[];
	private int lenTabV;
	private int lenTabE;
	private int indexTabE;
	FloatBuffer vboBuffer;
	IntBuffer eboBuffer;
	
	private final int NBCOTES = 10; // Nombre de côtés du point du joueur
	private float colDelta;
	
	private FloatBuffer projectionMatrix;

	
	
	private long t_start;
	
	public DessinLigne(Affichage a, PolyFever p, Partie partie, float d)
	{
		this.decalage = d; // PROVISOIRE
		this.colDelta = 0.01f;
		
		
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
		
	
	public void init()
	{
		
		
		//partie.envoyerTabVertex(tabVertex, lenTabV); // Envoie de la référence du tableau à l'objet partie
		
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
		//glUniformMatrix4(projectionUniform, false, this.projectionMatrix);

		uniColor = glGetUniformLocation(program, "Color");
		posAttrib = glGetAttribLocation(program, "position");
		colAttrib = glGetAttribLocation(program, "color");
		System.out.println("posAttrib: ".concat(String.valueOf(posAttrib)));
		System.out.println("colAttrib: ".concat(String.valueOf(colAttrib)));
		
		
		glDetachShader(program, vs);
		glDetachShader(program, fs);
		
		glBindAttribLocation(program, posAttrib, "position"); // on bind l'attribut position à 0 // PB CERTAINEMENT ICI !!!!!!!!!!!!!!!!!!!!
		glBindAttribLocation(program, colAttrib, "color"); // on bind l'attribut position à // PB CERTAINEMENT ICI !!!!!!!!!!!!!!!!!!!!
		
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
		
		// Dessin des bords du plateau
		dessinerBordsPlateau(new Vector2(0.0f,0.0f));
		
		glBindVertexArray(glGenVertexArrays()); // Création d'un VAO : Vertex Array Object avec glGenVertexArrays() . le VAO stock les liens entre les attributs et les VBO
		// le VAO contient une référence vers le VBO
		
		//glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		System.out.println("ça passe ...........");
		

	}
	
	
	public void dessiner()
	{
		//System.out.println("			dessiner dLigne");
		long t_now = System.currentTimeMillis();
		float time = t_now - t_start;
		
		Iterator<Joueur> e = this.partie.getJoueurs().iterator();
		while(e.hasNext()) // A déplacer dans Init();
		{
			j = e.next();
			if(j.getPosition().z() == 1.0f && j.getEtat() == Etat.VIVANT)
			{
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse(), new Vector3(1.0f,0.0f,0.0f));
				this.decalage += 0.03f;
				/*this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,0.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(0.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,1.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				this.decalage += 0.03f;
				this.addRectangle(j.getPosition(), j.getAngleRectangle(), j.getLigne().getEpaisseur(), j.getLigne().getVitesse2(), new Vector3(1.0f,0.0f,1.0f));
				*/
				
				
				this.decalage = 0 ;
			}
		}
		
		
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
		glVertexAttribPointer(posAttrib, 2, GL_FLOAT, false,5*4, 0);
		
		glEnableVertexAttribArray(colAttrib);
		glVertexAttribPointer(colAttrib, 3, GL_FLOAT, false,5*4, 2*4);
		
		
		glDrawElements(GL_TRIANGLES, this.nbVertex, GL_UNSIGNED_INT, 0); // essayer avec glDrawElements (https://open.gl/drawing)
		//glDrawArrays(GL_TRIANGLES, 0, 3);
		
		glDisableVertexAttribArray(posAttrib);
		glDisableVertexAttribArray(colAttrib);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glUseProgram(0);
	}
	
	private void addRectangle(Vector3 v, float angle, float w, float h, Vector3 c) // v : point d'encrage (milieu du bord supérieur)
	{
		
		this.colDelta += 0.001f;
		if(colDelta > 1.0f) colDelta = 0.0f;
		//System.out.println("colDelta: ".concat(String.valueOf(colDelta)));

		h = h+0.01f; // conversion pixel vers float

		
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
		this.nbVertex += 6;
		
	}
	
	private void ajouterVector2Rect(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4, Vector3 c)
	{
	
		this.tabVertex[this.lenTabV] = p4.x()+decalage; // Top Left
		this.tabVertex[this.lenTabV+1] = p4.y(); 	
		this.tabVertex[this.lenTabV+2] = c.x(); 
		this.tabVertex[this.lenTabV+3] = c.y()+colDelta; 
		this.tabVertex[this.lenTabV+4] = c.z(); 
		
		this.tabVertex[this.lenTabV+5] = p1.x()+decalage; // Top Right
		this.tabVertex[this.lenTabV+6] = p1.y();
		this.tabVertex[this.lenTabV+7] = c.x(); 
		this.tabVertex[this.lenTabV+8] = c.y()+colDelta; 
		this.tabVertex[this.lenTabV+9] = c.z(); 

		this.tabVertex[this.lenTabV+10] = p2.x()+decalage; // Bottom Right
		this.tabVertex[this.lenTabV+11] = p2.y();
		this.tabVertex[this.lenTabV+12] = c.x(); 
		this.tabVertex[this.lenTabV+13] = c.y()+colDelta; 
		this.tabVertex[this.lenTabV+14] = c.z(); 

		this.tabVertex[this.lenTabV+15] = p3.x()+decalage; // Bottom Left
		this.tabVertex[this.lenTabV+16] = p3.y();
		this.tabVertex[this.lenTabV+17] = c.x(); 
		this.tabVertex[this.lenTabV+18] = c.y()+colDelta; 
		this.tabVertex[this.lenTabV+19] = c.z(); 
		
		
		
		this.partie.envoyerTabVertex(new Vector4(this.tabVertex[this.lenTabV],this.tabVertex[this.lenTabV+1],this.tabVertex[this.lenTabV+15],this.tabVertex[this.lenTabV+16]),
				new Vector4(this.tabVertex[this.lenTabV+5],this.tabVertex[this.lenTabV+6],this.tabVertex[this.lenTabV+10],this.tabVertex[this.lenTabV+11]));
		
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
	
	public void initDessinJoueurs()
	{
		
		Iterator<Joueur> e = this.partie.getJoueurs().iterator();
		while(e.hasNext()) 
		{
			j = e.next();
			if(j.getPosition().z() == 1.0f && j.getEtat() == Etat.VIVANT)
			{
				this.initPoint(j.getPosition(),j.getLigne().getEpaisseur()/2);
			}
			
		}
		
	}
	
	public void initPoint(Vector3 p, float r) // Ajoute dans le buffer les vertex nécessaires à l'affichage du point d'un joueur.
	{
		double alpha = Math.PI / this.NBCOTES;
		Vector2 tabVertexPoint[] = new Vector2[this.NBCOTES+1]; // +1 pour le vertex central
		
		tabVertexPoint[0] = new Vector2(p.x(),p.y()); // centre du point du joueur
		
		for(int i = 1; i<this.NBCOTES; i++)
		{
			tabVertexPoint[i] = new Vector2(
					(float)Math.cos(alpha*(i-1))*r,
					(float)Math.sin(alpha*(i-1))*r
					);
		}
		
		for(int i = 0; i<this.NBCOTES; i++)
		{
			this.tabVertex[this.lenTabV] = tabVertexPoint[i].x(); // Top Left
			this.tabVertex[this.lenTabV+1] = tabVertexPoint[i].y(); 	
			this.tabVertex[this.lenTabV+2] = 1.0f; 
			this.tabVertex[this.lenTabV+3] = 1.0f; 
			this.tabVertex[this.lenTabV+4] = 1.0f; 
			
			this.lenTabV += 5;
		}
		
		
		
		
	}
	
	
	public void dessinerBordsPlateau(Vector2 p)
	{
		this.addRectangle(new Vector3(-1.0f-p.x(),1.0f-p.y(),1.0f),(float) Math.PI/2, 0.01f, 2.0f, new Vector3(1.0f,1.0f,0.0f)); // LEFT 
		this.addRectangle(new Vector3(1.0f-p.x(),1.0f-p.y(),1.0f),(float) Math.PI/2, 0.01f, 2.0f, new Vector3(1.0f,1.0f,0.0f)); // RIGHT
		this.addRectangle(new Vector3(1.0f-p.x(),-1.0f-p.y(),1.0f), 0.0f, 0.02f, 1.99f, new Vector3(1.0f,1.0f,0.0f)); // BOTTOM
		this.addRectangle(new Vector3(1.0f-p.x(),1.0f-p.y(),1.0f), 0.0f, 0.01f, 1.99f, new Vector3(1.0f,1.0f,0.0f)); // RIGHT
	}
	
	

}