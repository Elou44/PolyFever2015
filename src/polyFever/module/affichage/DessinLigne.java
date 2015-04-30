package polyFever.module.affichage;
import polyFever.module.util.math.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;


public class DessinLigne  { // peut être instancier un tableau de DessinLigne dans DessinPlateau (1 par joueur)
	
	private int nbVertex;
	
	public DessinLigne(int width, int height)
	{
		this.nbVertex = 0;
	}
	
	public void dessiner(int vbo)
	{
		//System.out.println("			dessiner dLigne");
		
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		glBufferData(GL_ARRAY_BUFFER, (FloatBuffer)BufferUtils.createFloatBuffer(24).put(new float[] { -0.75f, 0.0f, 0.0f, 1.0f,
				0.0f, -0.75f, 0.0f, 1.0f,
				-0.75f, -0.75f, 0.0f, 1.0f,

				-0.75f, 0.0f, 0.0f, 1.0f,
				0.0f, -0.75f, 0.0f, 1.0f,
				0.0f, 0.0f, 0.0f, 1.0f}).flip(), GL_STATIC_DRAW);
		
		glBindVertexArray(glGenVertexArrays());
		
		
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
		
		glDrawArrays(GL_TRIANGLES, 0, this.nbVertex);
		
		glDisableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	private void addRectangle(Vector4 v, float angle, float w, float h) // v : point d'encrage (milieu du bord supérieur)
	{
		
		w = w*super.getRATIOPIXWIDTH(); // conversion pixel vers float
		h = h*super.getRATIOPIXHEIGHT(); // conversion pixel vers float
		
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
		
		
		this.nbVertex += 6;
		
		// Peut etre utiliser matrice de rotation ? 
		
		
	}

}
