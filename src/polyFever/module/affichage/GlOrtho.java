package polyFever.module.affichage;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;


// Matrice de projection pour compenser la distortion lorsque la fenetre est rectangulaire.
/**
 * Cette classe contient une matrice de projection qui compense la distortion des vertices
 * lorsque la fen�tre � un ratio diff�rent de 1 (fen�tre non carr�e).
 * 
 * @author Elouarn Lain�
 *
 */
public class GlOrtho {
	
	private float projection[];
	private FloatBuffer projBuffer;
	
	/**
	 * Constructeur de la classe GlOrtho
	 * @param left
	 * 		float : abscisse du bord gauche de la fen�tre.
	 * @param right
	 * 		float : abscisse du bord droit de la fen�tre.
	 * @param bottom
	 * 		float : ordonn�e du bord inf�rieur de la fen�tre.
	 * @param top
	 * 		float : ordonn�e du bord sup�rieur de la fen�tre.
	 * @param near
	 * @param far
	 */
	public GlOrtho(float left, float right, float bottom, float top, float near, float far)
	{
		this.projection = new float[16];
		this.projBuffer = (FloatBuffer)BufferUtils.createFloatBuffer(16).put(this.projection).flip();
		setGlOrtho(left, right, bottom, top, near, far);
		
	}
	
	/**
	 * Cette m�thode calcule la nouvelle matrice de projection en fonction
	 * des valeurs rentr�es en param�tre. Cette m�thode est appel�e � chaque fois
	 * que la fen�tre est redimensionn�e.
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param near
	 * @param far
	 */
	public void setGlOrtho(float left, float right, float bottom, float top, float near, float far)
	{
		float a = 2.0f / (right - left);
        float b = 2.0f / (top - bottom);
        float c = -2.0f / (far - near);

        float tx = - (right + left)/(right - left);
        float ty = - (top + bottom)/(top - bottom);
        float tz = - (far + near)/(far - near);

        this.projection[0] = a;
        this.projection[1] = 0.0f;
        this.projection[2] = 0.0f;
        this.projection[3] = 0.0f;
        
        this.projection[4] = 0.0f;
        this.projection[5] = b;
        this.projection[6] = 0.0f;
        this.projection[7] = 0.0f;
        
        this.projection[8] = 0.0f;
        this.projection[9] = 0.0f;
        this.projection[10] = c;
        this.projection[11] = 0.0f;
        
        this.projection[12] = tx;
        this.projection[13] = ty;
        this.projection[14] = tz;
        this.projection[15] = 1.0f;
        
        this.projBuffer.put(this.projection);
        projBuffer.flip();
        
        System.out.println("La matrice de projection a �t� cr�� avec succ�s...");
       
	}
	
	/**
	 * Cette m�thode renvoie la matrice de projection sous forme d'un buffer de floats.
	 * @return FloatBuffer
	 */
	public FloatBuffer getProjectionBuf()
	{
		System.out.println("le buffer de projection est envoy�");
		return(this.projBuffer);
	}

}
