package br.ufscar.objects.animated.animals;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import br.ufscar.camera.Camera;
import br.ufscar.cenario.World;
import br.ufscar.geometry.Cube;
import br.ufscar.geometry.Square;
import br.ufscar.util.SoundPlayer;
public class Pig extends Animal{

	Random r = new Random();
	private Cube head;
	private Cube body;
	private Cube[] legs;

	private int rotacionado = 0;
	private float movido = 0;
	private int graus = 0;
	
	private Texture frontOfHeadTexture;
	private Texture albiFaceTexture = World.albiFace();
	private Texture normalFaceTexture;

	private DIRECAO direcao = DIRECAO.zNegativo;

	private enum DIRECAO{
		xPositivo, xNevativo, zPositivo, zNegativo
	}

	private boolean rotacaoDireita = true;

	public Pig(Texture frontOfLegTexture, Texture frontOfHeadTexture,
			Texture skinTexture, Texture tailTexture, String id){

		CURRENT_STATE = STATE.STOPPED;
		CURRENT_ANIMATION = ANIMATIONS.MOVING;

		setId(id);
		
		this.frontOfHeadTexture = frontOfHeadTexture;
		this.normalFaceTexture = frontOfHeadTexture;
		
		head = new Cube(skinTexture,skinTexture, this.frontOfHeadTexture,
				skinTexture,  
				skinTexture, skinTexture);

		body = new Cube(skinTexture,skinTexture,
				skinTexture, tailTexture, 
				skinTexture, skinTexture);

		legs = new Cube[]{
				new Cube(skinTexture,skinTexture, frontOfLegTexture, 
						skinTexture, 
						skinTexture, skinTexture),
						new Cube(skinTexture,skinTexture, frontOfLegTexture, 
								skinTexture, 
								skinTexture, skinTexture),
								new Cube(skinTexture,skinTexture, frontOfLegTexture, 
										skinTexture,
										skinTexture, skinTexture),
										new Cube(skinTexture,skinTexture, frontOfLegTexture, 
												skinTexture,
												skinTexture, skinTexture),
		};

		create();

	}

	public Pig(Texture texture, String id){

		setId(id);
		//this.texture = texture;

		head = new Cube(texture);
		body = new Cube(texture);
		legs = new Cube[]{
				new Cube(texture),
				new Cube(texture),
				new Cube(texture),
				new Cube(texture),
		};

		create();

	}

	private void create(){

		head.translate(0, 1.5f, -0.7f);
		body.scale(1, 1, 2.5f);
		body.translate(0, 1.001f, 0);

		for(int i = 0; i < legs.length; i++){
			legs[i].scale(0.5f, 1f, 0.5f);
		}

		// front legs
		legs[0].translate(0, 0, 0);    // left
		legs[1].translate(0.5f, 0, 0); // right

		// back legs
		legs[2].translate(0.5f, 0, 2f); // left
		legs[3].translate(0, 0, 2f); // right
	}

	@Override
	public void draw() {

		head.draw();
		body.draw();
		for(int i = 0; i < legs.length; i++){
			legs[i].draw();
		}

		startAnimation();
	}

	@Override
	public void scale(float x, float y, float z) {

		ArrayList<Square> listaQuadrados;

		float xOrigem = transladado.x + escalado.x * 0.5f;
		float yOrigem = transladado.y + escalado.y * 1.25f;
		float zOrigem = transladado.z + escalado.y * 1.6f;

		head.translate(-xOrigem, -yOrigem, -zOrigem);
		body.translate(-xOrigem, -yOrigem, -zOrigem);
		for(int i = 0; i < legs.length; i++){
			legs[i].translate(-xOrigem, -yOrigem, -zOrigem);
		}

		listaQuadrados = head.getSquareList();
		for(Square q: listaQuadrados){
			q.scale(x, y, z);
		}

		listaQuadrados = body.getSquareList();
		for(Square q: listaQuadrados){
			q.scale(x, y, z);
		}		
		for(int i = 0; i < legs.length; i++){
			listaQuadrados = legs[i].getSquareList();
			for(Square q: listaQuadrados){
				q.scale(x, y, z);
			}
		}

		escalado.x *= x;
		escalado.y *= y;
		escalado.z *= z;

		float xDestino = transladado.x + escalado.x * 0.5f;
		float yDestino = transladado.y + escalado.y * 1.25f;
		float zDestino = transladado.z + escalado.y * 1.6f;

		head.translate(xDestino, yDestino, zDestino);
		body.translate(xDestino, yDestino, zDestino);
		for(int i = 0; i < legs.length; i++){
			legs[i].translate(xDestino, yDestino, zDestino);
		}

	}

	@Override
	public void translate(float x, float y, float z) {

		transladado.x += x;
		transladado.y += y;
		transladado.z += z;

		head.translate(x, y, z);
		body.translate(x, y, z);

		for(int i = 0; i < legs.length; i++){
			legs[i].translate(x, y, z);
		}

	}

	@Override
	public void lookAround() {

		if(rotacaoDireita)
			head.rotate(1);
		else
			head.rotate(-1);

		if(head.getRotacionado() >= 60){
			rotacaoDireita = false;
			CURRENT_STATE = STATE.STOPPED;
		}
		else if (head.getRotacionado() <= -60){
			rotacaoDireita = true;
			CURRENT_STATE = STATE.STOPPED;
		}


	}

	@Override
	public void moving() {

		switch (direcao) {
		case xNevativo:
			translate(-0.01f, 0, 0);
			break;

		case xPositivo:
			translate(0.01f, 0, 0);
			break;

		case zNegativo:
			translate(0, 0, -0.01f);
			break;

		case zPositivo:
			translate(0, 0, 0.01f);

			break;

		default:
			break;
		}

		movido += 0.01f;

		if(movido >= 2){
			movido = 0;
			CURRENT_STATE = STATE.STOPPED;

		}


	}

	public void rotate(float angle) {

		ArrayList<Square> listaQuadrados;

		float x = 0, y = 0, z = 0;

		y = transladado.y + escalado.y * 1.25f;

		if(direcao == DIRECAO.zNegativo || direcao == DIRECAO.zPositivo){
			x = transladado.x + escalado.x * 0.5f;
			z = transladado.z + escalado.y * 1.6f;
		}
		else {
			x = transladado.x + escalado.x * 1.6f;
			z = transladado.z + escalado.y * 0.5f;
		}

		head.translate(-x, -y, -z);
		body.translate(-x, -y, -z);
		for(int i = 0; i < legs.length; i++){
			legs[i].translate(-x, -y, -z);
		}

		listaQuadrados = head.getSquareList();
		for(Square q: listaQuadrados){
			q.rotate(angle);
		}

		listaQuadrados = body.getSquareList();
		for(Square q: listaQuadrados){
			q.rotate(angle);
		}		
		for(int i = 0; i < legs.length; i++){
			listaQuadrados = legs[i].getSquareList();
			for(Square q: listaQuadrados){
				q.rotate(angle);
			}
		}

		head.translate(x, y, z);
		body.translate(x, y, z);
		for(int i = 0; i < legs.length; i++){
			legs[i].translate(x, y, z);
		}

	}

	//pode girar apenas em 90 graus, -90 graus e 180 graus
	public void turning(int angle){

		if(angle == rotacionado){

			rotacionado = 0;

			CURRENT_STATE = STATE.STOPPED;

			switch (angle) {
			case -90:
				switch (direcao) {
				case xPositivo:
					direcao = DIRECAO.zNegativo;
					break;
				case zPositivo:
					direcao = DIRECAO.xPositivo;
					break;
				case xNevativo:
					direcao = DIRECAO.zPositivo;
					break;
				case zNegativo:
					direcao = DIRECAO.xNevativo;
					break;
				default:
					break;
				}

				break;
			case 90:
				switch (direcao) {
				case xPositivo:
					direcao = DIRECAO.zPositivo;
					break;
				case zPositivo:
					direcao = DIRECAO.xNevativo;
					break;
				case xNevativo:
					direcao = DIRECAO.zNegativo;
					break;
				case zNegativo:
					direcao = DIRECAO.xPositivo;
					break;
				default:
					break;
				}
				break;
			case 180:
				switch (direcao) {
				case xPositivo:
					direcao = DIRECAO.xNevativo;
					break;
				case zPositivo:
					direcao = DIRECAO.zNegativo;
					break;
				case xNevativo:
					direcao = DIRECAO.xPositivo;
					break;
				case zNegativo:
					direcao = DIRECAO.zPositivo; 
					break;
				default:
					break;
				}
				break;

			default:
				break;
			}
		}

		switch (angle) {
		case -90:
			rotate(-1);
			rotacionado--;
			break;
		default:
			rotate(1);
			rotacionado++;
			break;
		}


	}

	@Override
	public void animation() {
		
		audioAnimation();

		if(CURRENT_STATE == STATE.STOPPED){
			int opcao = r.nextInt(3) + 1;
			switch (opcao) {
			case 1:
				CURRENT_ANIMATION = ANIMATIONS.MOVING;
				break;
			case 2:
				CURRENT_ANIMATION = ANIMATIONS.TURNING;
				graus = r.nextInt(3) * 90 - 90;
				break;
			case 3: 
				if(direcao == DIRECAO.zNegativo)
					CURRENT_ANIMATION = ANIMATIONS.LOOKING_AROUND;
				else 
					CURRENT_ANIMATION = ANIMATIONS.MOVING;
				break;
			default:
				break;
			}
			CURRENT_STATE = STATE.ANIMATING;
		}


		switch (CURRENT_STATE) {
		case ANIMATING:

			switch (CURRENT_ANIMATION) {

			case MOVING:
				moving();
				break;
			case LOOKING_AROUND:
				lookAround();
				break;
			case TURNING:
				turning(graus);
				break;

			}
			break;
		default:
			break;

		}
	}
	
	protected void audioAnimation() {
		Vector3f player = Camera.posicaoPlayer();
		float variacaoX = player.x - transladado.x; if(variacaoX < 0) variacaoX *= -1;
		float variacaoZ = player.z - transladado.z; if(variacaoZ < 0) variacaoZ *= -1;
		if(variacaoX  <= 10 && variacaoZ <= 10) {
			if(!SoundPlayer.getInstance().pigSoundIsPlaying()){
				frontOfHeadTexture = albiFaceTexture;
				SoundPlayer.getInstance().playPigSound();
			}
		}
		else 
			frontOfHeadTexture = normalFaceTexture;
		
	}
}
