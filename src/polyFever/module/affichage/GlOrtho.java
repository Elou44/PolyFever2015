package polyFever.module.affichage;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;


// Matrice de projection pour compenser la distortion lorsque la fenetre est rectangulaire.
public class GlOrtho {
	
	private float projection[];
	private FloatBuffer projBuffer;
	
	public GlOrtho(float left, float right, float bottom, float top, float near, float far)
	{
		this.projection = new float[16];
		this.projBuffer = (FloatBuffer)BufferUtils.createFloatBuffer(16).put(this.projection).flip();
		setGlOrtho(left, right, bottom, top, near, far);
		
	}
	
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
	
	public FloatBuffer getProjectionBuf()
	{
		System.out.println("le buffer de projection est envoyé");
		return(this.projBuffer);
	}

}
