package polyFever.module.main;

import static org.lwjgl.opengl.GL11.*;

import java.util.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import polyFever.module.moteurDeJeu.Partie;
import polyFever.module.util.*;
import polyFever.module.moteurDeJeu.*;
import polyFever.module.affichage.*;

/**
 * This class defines an entry point for OpenGL programs as it handles context creation and the game loop.
 * Entry point classes extend GLProgram and must implement the <code>init()</code> and <code>render()</code> methods.
 * The <code>update(long deltaTime)</code> can be overridden for variable time-steps.
 * 
 * @author Roi Atalla
 */
public abstract class PolyFever {
	
	
	private int fps;
	private int realFps;
	private final int WIDTH;
	private final int HEIGHT;
	private float RATIO; // Largeur d'un pixel en float
	private boolean isAAAvailable = true; // l'Anti-Aliasing est t-il disponible ? Oui par défaut
	
	private Partie partie;
	private GlOrtho glOrtho;
	
	private final int MSAA = 8; // AntiAliasing x8 
	private String name;
	
	boolean isLeftHeld, isRightHeld;
	
	/**
	 * Initializes the application in fullscreen mode.
	 * 
	 * @param vsync Enables/disables the vertical-sync feature, where the rendering is in sync with the monitor's refresh rate.
	 *        With v-sync off, there is no framerate cap and the gameloop will run as fast as the hardware can handle.
	 *        A framerate can be set with the <code>setFPS(int fps)</code> method.
	 */
	public PolyFever(boolean vsync, int width, int height) {
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true"); // Pour faire fonctionner le programme sur les vieux ordis
		System.out.println("Création du context openGL...");
		
		this.glOrtho = new GlOrtho(-1.0f*((float)width/(float)height),1.0f*((float)width/(float)height),-1.0f,1.0f,-1.0f,1.0f);
		
		isLeftHeld = false;
		isRightHeld = false;
		
		
		this.partie = null;
		this.WIDTH = width; 
		this.HEIGHT = height;
		
		try {
			Display.setFullscreen(true);
			Display.setVSyncEnabled(vsync);
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public int getWIDTH() {
		
		if(Display.getWidth() != WIDTH) // Si la fenetre a ete redimenssionne depuis la creation
		{
			return Display.getWidth();
		}
		else
		{
			return WIDTH;
		}
				
	}

	public int getHEIGHT() {
		
		if(Display.getHeight() != HEIGHT) // Si la fenetre a ete redimenssionne depuis la creation
		{
			return Display.getHeight();
		}
		else
		{
			return HEIGHT;
		}
	}
	
	public void setPartie(Partie p)
	{
		this.partie = p;
	}
	
	public boolean getIsAAAvailable()
	{
		return this.isAAAvailable;
	}

	/**
	 * Initializes a windowed application. The framerate is set to 60 and can be modified using <code>setFPS(int fps)</code>.
	 * 
	 * @param name The title of the window.
	 * @param width The width of the window.
	 * @param height The height of the window.
	 * @param resizable Enables/disables the ability to resize the window.
	 */
	public PolyFever(String name, int width, int height, boolean resizable) {
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true"); // Pour faire fonctionner le programme sur les vieux ordis
		System.out.println("Création du context openGL...");
		
		
		this.name = name;
		this.partie = null;
		Display.setTitle(this.name);
		this.WIDTH = width; 
		this.HEIGHT = height;
		this.RATIO = (float) width/(float) height;
		
		this.glOrtho = new GlOrtho(-1.0f*RATIO,1.0f*RATIO,-1.0f,1.0f,-1.0f,1.0f);

		try {
			Display.setDisplayMode(new DisplayMode(width, height));
		} catch(Exception exc) {
			exc.printStackTrace();
		}
		
		Display.setResizable(resizable);
		
		fps = 30;
		
		
	}
	
	public float getRATIO() {
		return RATIO;
	}
	
	
	public GlOrtho getGlOrtho() // Retourne l'objet glOrtho.
	{
		System.out.println("le pb est ici");
		return(this.glOrtho);
	}

	/**
	 * Sets the framerate of the game loop.
	 * 
	 * @param fps The desired framerate, in frames per second. It must be a positive integer or 0.
	 *        Specifying 0 means no rendering cap. Specifying a negative value will result in undefined behavior.
	 */
	public void setFPS(int fps) {
		this.fps = fps;
	}
	
	/**
	 * Returns the framerate of the game loop.
	 * 
	 * @return The framerate, in frames per second.
	 */
	public int getFPS() {
		return fps;
	}
	
	public int getRealFPS(){
		return this.realFps;
	}
	
	public void setRealFPS(int newFPS){
		 this.realFps = newFPS;
	}
	
	/**
	 * Initializes the context with default settings, which is generally the latest OpenGL version supported and compatibility
	 * profile if supported. For Mac OS X, the core profile must be specifically requested therefore this method is not
	 * sufficient.
	 * 
	 * This method does not return until the game loop ends.
	 * 
	 * Equivalent to <code>run(false)</code>.
	 */
	public final void run() {
		run(false);
	}
	
	/**
	 * Initializes the context depending on the value of <code>core</code>. Supplying <code>false</code> is equivalent to
	 * simply calling <code>run()</code>, while <code>true</code> requests the Core profile if available.
	 * 
	 * This method does not return until the game loop ends.
	 * 
	 * @param core <code>True</code> requests the Core profile, while <code>false</code> keeps default settings.
	 */
	public final void run(boolean core) {
		run(core, new PixelFormat());
	}
	
	/**
	 * Initializes the context depending on the value of <code>core</code> and the supplied PixelFormat.
	 * 
	 * This method does not return until the game loop ends.
	 * 
	 * @param core <code>True</code> requests the Core profile, while <code>false</code> keeps default settings.
	 * @param format The PixelFormat specifying the buffers.
	 */
	public final void run(boolean core, PixelFormat format) {
		run(format, core ? new ContextAttribs(3, 2).withProfileCore(true) : null);
	}
	
	/**
	 * Initializes the context requesting the specified OpenGL version in the format: <code>major.minor</code>
	 * 
	 * This method does not return until the game loop ends.
	 * 
	 * @param major
	 * @param minor
	 */
	public final void run(int major, int minor) {
		run(major, minor, false);
	}
	
	/**
	 * Initializes the context depending on the value of <code>core</code> and the specified OpenGL version in the format: <code>major.minor</code>
	 * 
	 * This method does not return until the game loop ends.
	 * 
	 * @param major
	 * @param minor
	 * @param core <code>True</code> requests the Core profile, while <code>false</code> requests the Compatibility profile.
	 */
	public final void run(int major, int minor, boolean core) {
		run(major, minor, core, new PixelFormat());
	}
	
	/**
	 * Initializes the context depending on the value of <code>core</code>, the supplied PixelFormat, and the specified
	 * OpenGL version in the format: <code>major.minor</code>
	 * 
	 * This method does not return until the game loop ends.
	 * 
	 * @param major
	 * @param minor
	 * @param core <<code>True</code> requests the Core profile, while <code>false</code> requests the Compatibility profile.
	 * @param format The PixelFormat specifying the buffers.
	 */
	public final void run(int major, int minor, boolean core, PixelFormat format) {
		run(format, core ? new ContextAttribs(major, minor).withProfileCore(core) : new ContextAttribs(major, minor));
	}
	
	/**
	 * Initializes the context with default settings and the supplied PixelFormat.
	 * 
	 * This method does not return until the game loop ends.
	 * 
	 * @param format The PixelFormat specifying the buffers.
	 */
	public final void run(PixelFormat format) {
		run(format, null);
	}
	
	/**
	 * Initializes the context with its attributes supplied.
	 * 
	 * This method does not return until the game loop ends.
	 * 
	 * @param attribs The context attributes.
	 */
	public final void run(ContextAttribs attribs) {
		run(new PixelFormat(), attribs);
	}
	
	/**
	 * Initializes the context with its attributes and PixelFormat supplied.
	 * 
	 * This method does not return until the game loop ends.
	 * 
	 * @param format The PixelFormat specifying the buffers.
	 * @param attribs The context attributes.
	 */
	public final void run(PixelFormat format, ContextAttribs attribs) {
		boolean tryAgain = true;
		while(tryAgain)
		{
			try {
				if(this.isAAAvailable) // Si oui, on créé un écran avec l'antiAliasing activé
				{
					Display.create(format.withSamples(this.MSAA), attribs);
					System.out.println("AA disponible");
					break;
				}
				else // Si non, on désactive l'AA
				{
					tryAgain = false;
					Display.create(format, attribs);
					System.out.println("AA non disponible");
					break;
				}
				
				
			} catch(Exception exc) {
				exc.printStackTrace();
				this.isAAAvailable = false;
				if(!tryAgain) // Si aucune des deux solutions n'est disponible , on ferme la fenetre
				{
					System.out.println("ton pc c'est de la merde");
					System.exit(1);	
				}
				
			}
		}
		
		gameLoop();
	}
	
	private void gameLoop() {
		try {
			init();
			
			Utils.checkGLError("init");
			
			resized();
			
			Utils.checkGLError("resized");
			
			long lastTime, lastFPS;
			lastTime = lastFPS = System.nanoTime();
			int frames = 0;
			
			while(!shouldStop()) {
				long deltaTime = System.nanoTime() - lastTime;
				lastTime += deltaTime;
				
				if(Display.wasResized())
					resized();
				
				while(Keyboard.next()) {
					if(Keyboard.getEventKeyState())
						keyPressed(Keyboard.getEventKey(), Keyboard.getEventCharacter());
					else
					{
						keyReleased(Keyboard.getEventKey(), Keyboard.getEventCharacter());
						
						
					}

				}
				
				if(Keyboard.next()== false && !isLeftHeld && !isRightHeld)
				{
					Iterator<Joueur> e = this.partie.getJoueurs().iterator();
					while(e.hasNext())
					{
						e.next().getLigne().pasTourner();
					}
				}
				
				traitementEvenements(isLeftHeld, isRightHeld);
				
				update(deltaTime);
				
				Utils.checkGLError("update");
				
				render();
				
				Utils.checkGLError("render");
				
				Display.update();
				
				frames++;
				if(System.nanoTime() - lastFPS >= 1e9) {
					//System.out.println("FPS:                                                        ".concat(String.valueOf(frames)));
					lastFPS += 1e9;
					setRealFPS(frames);
					Display.setTitle(this.name+" FPS:".concat(String.valueOf(getRealFPS())));
					frames = 0;
				}
				
				Display.sync(fps);
			}
		} catch(Throwable exc) {
			exc.printStackTrace();
		} finally {
			destroy();
		}
	}
	

	
	/**
	 * Called at most once after one of the <code>run</code> methods are called. This method
	 * must be implemented by user code.
	 */
	public abstract void init();
	
	/**
	 * Called when the window is resized. This method updates the <code>glViewport</code> but may be overridden
	 * with custom code. Make sure to call <code>super.resized()</code> if overriding, or remember to manually update
	 * the <code>glViewport</code>!
	 */
	public void resized() {
		glViewport(0, 0, getWIDTH(), getHEIGHT());

		this.RATIO = (float) getWIDTH() / (float) getHEIGHT();
		glOrtho.setGlOrtho(-1.0f*RATIO,1.0f*RATIO,-1.0f,1.0f,-1.0f,1.0f);
		
		System.out.println("Resized RATIO: ".concat(String.valueOf(this.RATIO)));

	}
	
	/**
	 * Consistently polled once per frame to test whether the game loop should stop. This
	 * method checks if either the window's close button has been clicked, ALT-F4 has been pressed, or if the ESCAPE key is pressed.
	 * 
	 * @return Returns <code>true</code> if the game loop should stop, otherwise <code>false</code>.
	 */
	public boolean shouldStop() {
		return Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
	}
	
	/**
	 * Called when a key has been pressed.
	 * 
	 * @param key The @see org.lwjgl.input.Keyboard keycode of the pressed key.
	 * @param c The literal character of the pressed key.
	 */
	public void keyPressed(int key, char c) {
		
		//System.out.println(isLeftHeld);
		//System.out.println(isRightHeld);
		
		if(key == Keyboard.KEY_LEFT)
		{
			//System.out.println("Gauche");
			isLeftHeld = true;
			isRightHeld = false;
			
		}
		else if(key == Keyboard.KEY_RIGHT)
		{
			//System.out.println("Droite");	
			isLeftHeld = false;
			isRightHeld = true;
			
		}

		
	}
	
	/**
	 * Called when a key has been released.
	 *
	 * @param key The @see org.lwjgl.input.Keyboard keycode of the released key.
	 * @param c The literal character of the released key.
	 */
	public void keyReleased(int key, char c) 
	{
		
		if(key == Keyboard.KEY_LEFT)
		{
			//System.out.println("Relaché Gauche");
			isLeftHeld = false;
			
		}
		else if(key == Keyboard.KEY_RIGHT)
		{
			//System.out.println("Relaché Droite");
			isRightHeld = false;
			
		}
		
	}
	
	public void traitementEvenements(boolean isLeftHeld, boolean isRightHeld)
	{
		
		if(isLeftHeld && !isRightHeld)
		{
			//System.out.println("Gauche");
			Iterator<Joueur> e = this.partie.getJoueurs().iterator();
			while(e.hasNext())
			{
				e.next().getLigne().tournerGauche();
			}
		}
		else if(isRightHeld && !isLeftHeld)
		{
			
			//System.out.println("Droite");
			Iterator<Joueur> e = this.partie.getJoueurs().iterator();
			while(e.hasNext())
			{
				e.next().getLigne().tournerDroite();
			}
			
		}
		
	}
	

	
	/**
	 * Called once per frame and given the elapsed time since the last call
	 * to this method.
	 * 
	 * @param deltaTime The elapsed time since the last call to this method, in nanoseconds.
	 */
	public void update(long deltaTime) {}
	
	/**
	 * Called once per frame and must be implemented by user code. Render operations should
	 * occur here.
	 */
	public abstract void render();
	
	/**
	 * Destroys the context.
	 */
	public void destroy() {
		Display.destroy();
	}
	
	/**
	 * Returns the entire contents of the file in a String. The file should be specified as relative to the user class
	 * that extends GLProgram.
	 * 
	 * @param file The file path, relative to the user class extending GLProgram.
	 * @return The entire contents of the file.
	 */
	public String readFromFile(String file) { 
		try {
			return Utils.readFully(getClass().getResourceAsStream(file));
		} catch(Exception exc) {
			throw new RuntimeException("Failure reading file " + file, exc);
		}
	}
}