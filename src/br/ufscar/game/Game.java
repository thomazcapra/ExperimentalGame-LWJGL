package br.ufscar.game;
import java.io.File;
import java.nio.FloatBuffer;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import br.ufscar.camera.Camera;
import br.ufscar.cenario.World;
import br.ufscar.util.ShaderBuilder;
import br.ufscar.util.SoundPlayer;

public class Game {

	final static int width = 1366, height = 768;
	final static int frameRate = 60;
	//PARAMETROS DO MUNDO
	final int alturaMundo = 256;
	final int larguraMundo = 512;
	final int profundidadeMundo = 512;

	// ceu e chao;
	private final Texture skyTexture = loadTextures("resources/images/texturas/mundo/siege_up.png");
	private final Texture floorTexture = loadTextures("resources/images/texturas/mundo/siege_dn.png");
	//paredes
	private final Texture backWallTexture = loadTextures("resources/images/texturas/mundo/siege_rt.png"); 
	private final Texture frontWallTexture = loadTextures("resources/images/texturas/mundo/siege_lf.png"); 
	private final Texture rightWallTexture = loadTextures("resources/images/texturas/mundo/siege_bk.png");
	private final Texture leftWallTexture = loadTextures("resources/images/texturas/mundo/siege_ft.png");
	//audio
	private final String caminhoMusica = "resources" + File.separatorChar + "audio" + File.separatorChar + "Living_Mice.wav";
	private final String somDoPorco = "resources" + File.separatorChar + "audio" + File.separatorChar + "albi.wav";


	private Vector3f posicaoInicial = new  Vector3f(30, 0, 30);

	private Camera camera;
	private World mundo;

	private boolean[] keys = new boolean[256];

	public static void main(String[] args) throws LWJGLException {

		Display.setDisplayMode(new DisplayMode(width,height));
		Display.create();
		Game game = new Game();
		game.init3D();

		// Game loop
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			game.render();
			game.update();
			Display.update();
			Display.sync(frameRate);
		}
		SoundPlayer.getInstance().stopSound();
		Display.destroy();
		System.exit(0);
	}

	public Game(){

		try {
			SoundPlayer.getInstance().createMusic(caminhoMusica);	
			SoundPlayer.getInstance().createPigSound(somDoPorco);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mundo = new World(skyTexture, floorTexture, backWallTexture, frontWallTexture, rightWallTexture, leftWallTexture, larguraMundo, alturaMundo, profundidadeMundo);
		// camera
		camera = new Camera(this, posicaoInicial);


	}

	public void render(){

		clearScreen();

		camera.translatePostion();

		mundo.draw();

	}

	public void update(){
		if(!SoundPlayer.getInstance().isPlaying())
			SoundPlayer.getInstance().playMusic();
		mapKeys();
		camera.update();

	}



	public void init3D(){

		//Start 3D Stuff
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GLU.gluPerspective((float) 100, width / height, 0.001f, 1000);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		GL11.glClearDepth(1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);

		//ativar transparencia dos png nas texturas
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);

		//ativar iluminação
		//ativaIluminacao();

	}

	public void ativaIluminacao(){ 
		//ativar luz
		//						   r     g     b     a
		float[] bufferAmbient = { 0.5f, 0.5f, 0.5f, 1.0f };
		FloatBuffer LightAmbient = br.ufscar.util.BufferUtils.createFloatBuffer(bufferAmbient);//{ 0.5f, 0.5f, 0.5f, 1.0f }; // Ambient Light Values ( NEW )

		float[] bufferDiffuse = { 1.0f, 1.0f, 1.0f, 1.0f };
		FloatBuffer LightDiffuse = br.ufscar.util.BufferUtils.createFloatBuffer(bufferDiffuse);//	GLfloat LightDiffuse[]= { 1.0f, 1.0f, 1.0f, 1.0f }; // Diffuse Light Values ( NEW )

		//By default, the light in OpenGL is white and is coming from the Z direction.
		float[] bufferPosition = {1.0f, 1.0f, 1.0f, 1.0f};
		FloatBuffer LightPosition = br.ufscar.util.BufferUtils.createFloatBuffer(bufferPosition); // Light Position ( NEW )

		ShaderBuilder a = new ShaderBuilder();
		int vert = a.createVertexShader("resources/shaders/shader.vert");
		int frag = a.createFragmentShader("resources/shaders/shader.frag");

		a.addShader(vert);
		a.addShader(frag);

		int program = a.createProgram();

		ARBShaderObjects.glUniform4fARB(program, bufferPosition[0], bufferPosition[1], bufferPosition[2], bufferPosition[3]);;

		ARBShaderObjects.glUseProgramObjectARB(program);

		GL11.glEnable(GL11.GL_LIGHT0);

		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, LightPosition);            // Position The Light
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, LightAmbient);             // Setup The Ambient Light
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, LightDiffuse);             // Setup The Diffuse Light

		GL11.glEnable(GL11.GL_LIGHTING);      // Enable Lighting
	}

	public void clearScreen(){
		//Clear the screen
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
	}

	public boolean[] getKeys(){
		return keys;
	}

	public static Texture loadTextures(String path){

		int firstIndex = path.lastIndexOf(".") + 1;
		int lastIndex = path.length();
		String type = path.substring( firstIndex, lastIndex );

		try{
			Texture texture = TextureLoader.getTexture(type, ResourceLoader.getResourceAsStream(path));
			return texture;
			//texFloor = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream("resources/images/floor.jpg"));
		}catch(Exception e){
			System.err.println("Failed to load texture: ");
			e.printStackTrace();
		}

		return null;
	}

	public Vector3f dimensoesMundo() {
		return new Vector3f(larguraMundo, alturaMundo, profundidadeMundo);
	}

	private void mapKeys(){
		//Update keys
		for(int i = 0;i < keys.length; i++){
			keys[i] = Keyboard.isKeyDown(i);
		}

				if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
					if(!SoundPlayer.getInstance().pigSoundIsPlaying()){
						SoundPlayer.getInstance().playPigSound();
					}
				}
		//
		//		if(Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)){
		//			System.out.println("SUB");
		//
		//			for(int i = 0; i < 20; i++) {
		//				br.ufscar.objects.Pig obj = (Pig) mundo.getAnimatedObject("Porco " + i);
		//			}
		//			System.out.println("Albi audio");
		//			SoundPlayer.getInstance().playOutroAudio();
		//		}

	}
}
