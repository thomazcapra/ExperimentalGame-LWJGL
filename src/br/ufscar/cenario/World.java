package br.ufscar.cenario;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import br.ufscar.geometry.Cube;
import br.ufscar.objects.animated.AnimatedObject;
import br.ufscar.objects.animated.animals.Pig;
import br.ufscar.objects.animated.plants.Cactus;
import br.ufscar.objects.animated.plants.Cloud;
import br.ufscar.objects.animated.plants.MediumTree;
import br.ufscar.objects.animated.plants.SmallTree;
import br.ufscar.objects.statics.StaticObject;
import br.ufscar.objects.statics.Stone;

public class World extends Cube{

	
	private final static Texture cactusTexture = loadTextures("resources/images/texturas/objetos/cactus.png");
	
	private final static Texture leafTexture = loadTextures("resources/images/texturas/objetos/folha.jpg");
	private final static Texture trunkTexture = loadTextures("resources/images/texturas/objetos/tronco.jpg");
	private final static Texture stoneTexture = loadTextures("resources/images/texturas/objetos/pedra.jpg");
	private final static Texture cloudTexture = loadTextures("resources/images/texturas/objetos/nuvem.png");

	private final static Texture albiFace = loadTextures("resources/images/texturas/animais/porco/porco_cara.jpg");
	
	private final static Texture pigHeadTexture = loadTextures("resources/images/texturas/animais/porco/porco_face1.jpg");
	private final static Texture pigSkinTexture = loadTextures("resources/images/texturas/animais/porco/porco_pele.jpg");
	private final static Texture pigLegTexture = loadTextures("resources/images/texturas/animais/porco/porco_pata.jpg");
	private final static Texture pigTailTexture = loadTextures("resources/images/texturas/animais/porco/porco_rabo.jpg");
	
	
	private static ArrayList<StaticObject> objects;
	private static Hashtable<String, AnimatedObject> animatedObjects;
	
	
	public AnimatedObject getAnimatedObject(String key) {
		return animatedObjects.get(key);
	}
	
	public ArrayList<StaticObject> getStaticObjects() {
		return objects;
	}

	// textura de cada face
	public World(Texture skyTexture, Texture floorTexture, Texture backWallTexture, Texture frontWallTexture,
			Texture rightWallTexture, Texture leftWallTexture) {

		// inicia o cubo com as texturas
		super(skyTexture, floorTexture, backWallTexture, frontWallTexture, rightWallTexture, leftWallTexture);
		
		// inicializa as faces do cubo
		super.create();
		
		// armazena os objetos do mundo
		objects = new ArrayList<StaticObject>();
		animatedObjects = new Hashtable<String, AnimatedObject>();
		
		// define o tamanho do mundo e o coloca na origem
		scale(800, 600, 200);
		translate(0, 0, 0);
		
	}
	
	public World(Texture skyTexture, Texture floorTexture, Texture backWallTexture, Texture frontWallTexture,
			Texture rightWallTexture, Texture leftWallTexture, int width, int height, int depth) {

		// inicia o cubo com as texturas
		super(skyTexture, floorTexture, backWallTexture, frontWallTexture, rightWallTexture, leftWallTexture);
		
		// inicializa as faces do cubo
		super.create();
		
		// armazena os objetos do mundo
		objects = new ArrayList<StaticObject>();
		animatedObjects = new Hashtable<String, AnimatedObject>();

		// define o tamanho do mundo
		scale(width, height, depth);
				
		criaObjetos();
		//pega tempo inicial
		//lastFrame = getTime();
		
	}

	public void draw(){
		super.draw();
		
	//	time();
		
		// desenha os objetos estaticos do mundo
		for(StaticObject obj : objects)
			obj.draw();
		
		Enumeration<AnimatedObject> e = animatedObjects.elements();
		
		while(e.hasMoreElements()) {
			
			AnimatedObject a = e.nextElement();
			a.draw();
			
		}
		
	}
	
	
//	public void time() {		
//		
//		if(lastFrame != getTime()){
//			
//			for(StaticObject obj: objects) {
//				if(obj instanceof Tree){
//					((Tree)obj).growUp();
//				}
//				else if(obj instanceof Stone){
//					((Stone)obj).rotate(1);
//				}
//				else if(obj instanceof Cloud){	
//					((Cloud)obj).translacao();
//				}
//			}
//			getDelta();
//		}
//	}
//	
//	private static float getDelta() {
//        long time = getTime();
//        float delta = (float) (time - lastFrame);
//        lastFrame = time;
//        return delta;
//    }

//    private static long getTime() {
//        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
//    }
// 
    private static void criaObjetos() {
    	Random r = new Random();
  
    	for(int i = 0; i < 20; i++){
			/**  CRIANDO PORCO  **/
    		Pig porco = new Pig(pigLegTexture, pigHeadTexture, pigSkinTexture, pigTailTexture, "Porco " + i);
    		porco.translate(r.nextFloat() * 500, 0, r.nextFloat() * 500);
			/**  CRIANDO GRAVETO  **/    		
    		SmallTree arvorePequena = new SmallTree(leafTexture, trunkTexture, "Arvore " + i);
    		arvorePequena.translate(r.nextFloat() * 500, 0, r.nextFloat() * 500);
			/**  CRIANDO ARVORE MEDIA  **/
    		MediumTree arvoreMedia = new MediumTree(leafTexture, trunkTexture, "ArvoreMedia " + i);
    		arvoreMedia.translate(r.nextFloat() * 500 + 10, 0, r.nextFloat() * 500 + 10);
			/**  CRIANDO NUEVM  **/
    		Cloud nuvem = new Cloud(cloudTexture, "Nuvem " + i);
    		nuvem.translate(r.nextFloat() * 300 + 100, r.nextFloat() * 50 + 50, r.nextFloat() * 300 + 100);
    		nuvem.scale(r.nextFloat() * 10 + 4, r.nextFloat() * 10 + 2, r.nextFloat() * 10 + 4);
    		/**  CRIANDO PEDRAS  **/
    		Stone pedra = new Stone(stoneTexture, r.nextInt(10) + 1);
    		pedra.translate(r.nextFloat() * 500 + 10, 0, r.nextFloat() * 500 + 10);
    		/**  CRIANDO PEDRAS  **/
    		Cactus cacto = new Cactus(cactusTexture, cactusTexture, r.nextInt(10) + 1, "Cacto " + i);
    		cacto.translate(r.nextFloat() * 500 + 10, 0, r.nextFloat() * 500 + 10);
    		
    		animatedObjects.put(porco.getId(), porco);
    		animatedObjects.put(nuvem.getId(), nuvem);
    		animatedObjects.put(arvorePequena.getId(), arvorePequena);
    		animatedObjects.put(arvoreMedia.getId(), arvoreMedia);
    		animatedObjects.put(cacto.getId(), cacto);

    		objects.add(pedra);

    	}
    	// objetos do mundo
    	
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
	
	public static Texture albiFace(){
		return albiFace;
	}
}
