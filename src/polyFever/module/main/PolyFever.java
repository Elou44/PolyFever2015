package polyFever.module.main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;
import polyFever.module.menu.Menu;
import polyFever.module.util.*;
import polyFever.module.affichage.*;
import polyFever.module.evenements.Evenements;


/**
 * This class defines an entry point for OpenGL programs as it handles context creation and the game loop.
 * Entry point classes extend GLProgram and must implement the <code>init()</code> and <code>render()</code> methods.
 * The <code>update(long deltaTime)</code> can be overridden for variable time-steps.
 * 
 * Cette classe permet de cr�er le contexte openGL (m�thode gameLoop()). 
 * Le gameLoop fait en sorte qu'il y ait autant de rendu par seconde que ce que sp�cifie l'attribut fps.
 * Cela permet d'avoir un frame rate constant.  
 * 
 * @author Roi Atalla, Elouarn Lain�
 */
public abstract class PolyFever {
	
	
	private int fps; // Frame rate � atteindre
	private int realFps; // Frame rate r�el
	private final int WIDTH; // largeur de la fen�tre en pixel
	private final int HEIGHT; // hauteur de la fen�tre en pixel
	private float RATIO; // ratio de la fen�tre (WIDTH/HEIGTH)
	private float PXtoFLOAT_X; // Largeur d'un pixel dans le rep�re d'openGL
	private float PXtoFLOAT_Y; // Hauteur d'un pixel dans le rep�re d'openGL
	private boolean isAAAvailable = true; // l'Anti-Aliasing est t-il disponible ? Oui par d�faut
	
	public Affichage affichage;
	private GlOrtho glOrtho;
	private Evenements evenements; // Classe de gestion des �v�nements
	
	private final int MSAA = 8; // AntiAliasing x8 
	private String name; // nom de la fen�tre
	private boolean isOpen; // Boolean permettant de fermer la fen�tre
	
	/**
	 * Initializes the application in fullscreen mode.
	 * 
	 * @param vsync Enables/disables the vertical-sync feature, where the rendering is in sync with the monitor's refresh rate.
	 *        With v-sync off, there is no framerate cap and the gameloop will run as fast as the hardware can handle.
	 *        A framerate can be set with the <code>setFPS(int fps)</code> method.
	 */
	public PolyFever(boolean vsync, int width, int height) {
		System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true"); // Pour faire fonctionner le programme sur les vieux ordis
		System.out.println("Cr�ation du context openGL...");
		
		this.glOrtho = new GlOrtho(-1.0f*((float)width/(float)height),1.0f*((float)width/(float)height),-1.0f,1.0f,-1.0f,1.0f);
		
		this.affichage = null;
		this.evenements = null;
		this.WIDTH = width; 
		this.HEIGHT = height;
		this.PXtoFLOAT_Y = 2/HEIGHT;
		this.PXtoFLOAT_X = (2*RATIO)/WIDTH;
		this.isOpen = true;
		
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
	
	
	public void setAffichage(Affichage a)
	{
		this.affichage = a;
	}
	
	public Affichage getAffichage()
	{
		return affichage;
	}
	
	public void setEvenements(Evenements e)
    {
            this.evenements = e;
    }
	
	public Evenements getEvenements()
	{
		return evenements;
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
		System.out.println("Cr�ation du context openGL...");
		
		
		this.name = name;
		this.affichage = null;
		this.evenements = null;
		Display.setTitle(this.name);
		this.WIDTH = width; 
		this.HEIGHT = height;
		this.RATIO = (float) width/(float) height;
		this.PXtoFLOAT_Y = 2/HEIGHT;
		this.PXtoFLOAT_X = (2*RATIO)/WIDTH;
		this.isOpen = true;
		
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
	
	public float getPXtoFLOAT_X() {
		return PXtoFLOAT_X;
	}
	
	public float getPXtoFLOAT_Y() {
		return PXtoFLOAT_Y;
	}
	
	public GlOrtho getGlOrtho() // Retourne l'objet glOrtho.
	{
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
	 * Cette m�thode qui initialise le contexte openGL v�rifie dans un premier temps
	 * si la carte graphique de l'ordinateur g�re l'anti-cr�nelage. Si l'ordinateur ne
	 * g�re pas l'anti-cr�nelage la m�thode cr�er un autre contexte sans anti-cr�nelage.
	 * Si l'ordinateur ne peut pas cr�er le contexte sans anti-cr�nelage, le programme se ferme.
	 * 
	 * @param format The PixelFormat specifying the buffers.
	 * @param attribs The context attributes.
	 * 
	 * @author Roi Attala, Elouarn Lain�
	 */
	public final void run(PixelFormat format, ContextAttribs attribs) {
		
		boolean tryAgain = true;
		while(tryAgain)
		{
			try {
				if(this.isAAAvailable) // Si oui, on cr�� un �cran avec l'antiAliasing activ�
				{
					Display.create(format.withSamples(this.MSAA), attribs);
					System.out.println("can handle AA");
					break;
				}
				else // Si non, on d�sactive l'AA
				{
					tryAgain = false;
					Display.create(format, attribs);
					System.out.println("can't handle AA");
					break;
				}
				
				
			} catch(Exception exc) {
				exc.printStackTrace();
				this.isAAAvailable = false;
				if(!tryAgain) // Si aucune des deux solutions n'est disponible , on ferme la fenetre
				{
					System.out.println("your computer is unable to run PolyFever, try to update your drivers or buy a new computer !");
					System.exit(1);	
				}
				
			}
		}
		

		gameLoop();
	}
	
	

	/**
	 * Cette m�thode g�re la boucle principale du programme. 
	 * C'est aussi dans cette boucle que sont appel�es les m�thodes g�rant les �v�nements.
	 * Cette boucle tourne � un frame rate constant d�fini par l'attribut "fps".
	 * L'affichage est r�alis�e par l'interm�diaire de la m�thode render, qui est red�finie dans la classe Prototyp1.
	 */
	private void gameLoop() {
		try {
			init();
			
			Utils.checkGLError("init");
			
			resized();
			
			Utils.checkGLError("resized");
			
			long lastTime, lastFPS;
			lastTime = lastFPS = System.nanoTime();
			int frames = 0;
			
			while(!shouldStop() && isOpen) {
				long deltaTime = System.nanoTime() - lastTime;
				lastTime += deltaTime;
				
				if(Display.wasResized())
					resized();
				
				// Gestion des Evenements
				if(Menu.isMenu())
				{
					this.evenements.gestionMenu();
				}
				else
				{
					this.evenements.gestionJeu();
				}
				
				
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
	 * 
	 * Cette m�thode est appel�e � chaque redimensionnement de fen�tre. 
	 * Elle met � jour : le viewPort, la valeur du RATIO, la largeur et la hauteur d'un pixel dans le rep�re d'openGL et
	 * appelle la m�thode qui va recalculer la matrice de projection. 
	 */
	public void resized() {
		GL11.glViewport(0, 0, getWIDTH(), getHEIGHT());

		this.RATIO = (float) getWIDTH() / (float) getHEIGHT();
		this.PXtoFLOAT_Y = 2/(float) getHEIGHT();
		this.PXtoFLOAT_X = (2*RATIO)/(float) getWIDTH();
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
	 * Cette m�thode change le boolean isOpen � false, ce qui a pour cons�quence de fermer le programme.
	 */
	public void closeGame() { // Ferme le jeu
		this.isOpen = false;
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