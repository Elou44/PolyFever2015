package polyFever.module.affichage;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;


// Matrice de projection pour compenser la distortion lorsque la fenetre est rectangulaire.
/**
 * Cette classe contient une matrice de projection qui compense la distortion des vertices
 * lorsque la fenêtre à un ratio différent de 1 (fenêtre non carrée).
 * 
 * @author Elouarn Lainé
 *
 */
public class GlOrtho {
	
	private float projection[];
	private FloatBuffer projBuffer;
	
	/**
	 * Constructeur de la classe GlOrtho
	 * @param left
	 * 		float : abscisse du bord gauche de la fenêtre.
	 * @param right
	 * 		float : abscisse du bord droit de la fenêtre.
	 * @param bottom
	 * 		float : ordonnée du bord inférieur de la fenêtre.
	 * @param top
	 * 		float : ordonnée du bord supérieur de la fenêtre.
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
	 * Cette méthode calcule la nouvelle matrice de projection en fonction
	 * des valeurs rentrées en paramètre. Cette méthode est appelée à chaque fois
	 * que la fenêtre est redimensionnée.
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
        
        System.out.println("La matrice de projection a été créé avec succès...");
       
	}
	
	/**
	 * Cette méthode renvoie la matrice de projection sous forme d'un buffer de floats.
	 * @return FloatBuffer
	 */
	public FloatBuffer getProjectionBuf()
	{
		System.out.println("le buffer de projection est envoyé");
		return(this.projBuffer);
	}

}
