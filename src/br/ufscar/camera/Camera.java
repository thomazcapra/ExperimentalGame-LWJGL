package br.ufscar.camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import br.ufscar.game.Game;


public class Camera {

	//vetor de posicao
	static Vector3f vector;
	Vector3f rotation = new Vector3f();
	//vetor da posicao anterior
	Vector3f vectorPrevious = new Vector3f();

	//movimentos
	boolean moveForward = false, moveBackward = false;
	boolean strafeLeft = false, strafeRight = false;
	boolean jump = false, down = false;
	
	//velocidade 
	static final float speed = 0.5f;
	
	Game game;

	//construtor que faz respawn na origem (0, 0, 0)
	public Camera(Game game){
		vector = new Vector3f();
		this.game = game;
		Mouse.setGrabbed(true);
	}

	//construtor que que permite respawn e qualquer lugar
	public Camera(Game game, Vector3f startIn){
		vector = startIn;
		this.game = game;
		Mouse.setGrabbed(true);
	}
	
	public void update(){
		
		//atualiza as posicoes anteriores
		updatePrevious();
		
		//entrada teclado
		input();
		
		//movimentos que atualiza a posicao atual
		updateVector();
	}

	public void updateVector(){
		if(moveForward){
			vector.x -= (float) (Math.sin(-rotation.y * Math.PI / 180) * speed);
			vector.z -= (float) (Math.cos(-rotation.y * Math.PI / 180) * speed);
		}
		if(moveBackward){
			vector.x += (float) (Math.sin(-rotation.y * Math.PI / 180) * speed);
			vector.z += (float) (Math.cos(-rotation.y * Math.PI / 180) * speed);
		}
		if(strafeLeft){
			vector.x += (float) (Math.sin((-rotation.y - 90) * Math.PI / 180) * speed);
			vector.z += (float) (Math.cos((-rotation.y - 90) * Math.PI / 180) * speed);
		}
		if(strafeRight){
			vector.x += (float) (Math.sin((-rotation.y + 90) * Math.PI / 180) * speed);
			vector.z += (float) (Math.cos((-rotation.y + 90) * Math.PI / 180) * speed);
		}
		if(jump){
			vector.y += (float) (Math.sin((-rotation.z + 90) * Math.PI / 180) * speed);
			vector.y += (float) (Math.cos((-rotation.x + 90) * Math.PI / 180) * speed);	
		}
		if(down){
			vector.y -= (float) (Math.sin((-rotation.z + 90) * Math.PI / 180) * speed);
			vector.y -= (float) (Math.cos((-rotation.x + 90) * Math.PI / 180) * speed);	
		}
	}

	public void translatePostion(){
		//This is the code that changes 3D perspective to the camera's perspective.
		GL11.glRotatef(rotation.x, 1, 0, 0);
		GL11.glRotatef(rotation.y, 0, 1, 0);
		GL11.glRotatef(rotation.z, 0, 0, 1);
		//-vector.y-2.4f means that your y is your feet, and y+2.4 is your head.
		GL11.glTranslatef(-vector.x, -vector.y-2.4f, -vector.z);
	}

	public void updatePrevious(){
		
		//Update last position (for collisions (later))
		if(vector.x <= 1)
			vector.x = 1;
		else if(vector.x >=  game.dimensoesMundo().x){
			vectorPrevious.x = game.dimensoesMundo().x -10;
			vector.x = game.dimensoesMundo().x -10;
		}
		else
			vectorPrevious.x = vector.x;

		if(vector.y <= 0)
			vector.y = 0;
		else if(vector.y >=  game.dimensoesMundo().y){
			vectorPrevious.y = game.dimensoesMundo().y -5;
			vector.y = game.dimensoesMundo().y -5;
		}
		else
			vectorPrevious.y = vector.y;
				
		if(vector.z <= 1)
			vector.z = 1;
		else if(vector.z >=  game.dimensoesMundo().z){
			vectorPrevious.z = game.dimensoesMundo().z -5;
			vector.z = game.dimensoesMundo().z -5;
		}
		else
			vectorPrevious.z = vector.z;
	}

	//entradas do teclado do mouse
	public void input(){
		//Keyboard Input for Movement
		moveForward = game.getKeys()[Keyboard.KEY_W];
		moveBackward = game.getKeys()[Keyboard.KEY_S];
		strafeLeft = game.getKeys()[Keyboard.KEY_A];
		strafeRight = game.getKeys()[Keyboard.KEY_D];
		jump = game.getKeys()[Keyboard.KEY_SPACE];
		down = game.getKeys()[Keyboard.KEY_LCONTROL];

		//Se o foco está no jogo
		if(Mouse.isGrabbed()){
			float mouseDX = Mouse.getDX() * 0.8f * 0.16f;
			float mouseDY = Mouse.getDY() * 0.8f * 0.16f;
			
			//normalizar a rotação em 360 graus com base no eixo y, (movimentando x e z)
			if (rotation.y + mouseDX >= 360) {
				//se passar então subtraimos
				rotation.y = rotation.y + mouseDX - 360;
				
			} else if (rotation.y + mouseDX < 0) {
				//se for negativa então invertemos
				rotation.y = 360 - rotation.y + mouseDX;
				
			} else {
				//caso contrario, só rotacionamos com base na variacao do eixo x do movimento
				rotation.y += mouseDX;
			}
			
			//rotacao de apenas 180 graus no eixo x como base (rotaca em relacao a y)
			if (rotation.x - mouseDY >= -89 && rotation.x - mouseDY <= 89) {
				//se rotacao com 180 graus
				rotation.x += -mouseDY;
				
			} else if (rotation.x - mouseDY < -89) {
				//se o angulo de visao - a rotacao for menor que 89 significa que estamos tentando olhar muito para baixo
				//quando isso acontece a rotacao de x recebe o extremo de inclinaçã negativa que é de -89 graus, -90 graus daria para
				//enxergar o chão
				rotation.x = -89;
				
			} else if (rotation.x - mouseDY > 89) {
				//inverso da de cima
				rotation.x = 89;
			}
		}
	}
	
	public static Vector3f posicaoPlayer() {
		return vector;
	}
}
