package br.ufscar.objects.animated.plants;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import br.ufscar.geometry.Cube;

public class Cactus extends Plant {

	protected ArrayList<Cube> trunk = new  ArrayList<Cube>();
	
	protected Texture upTexture;
	protected Texture trunkTexture;
	
	private final int escalaMaxima = 2;
	
	private int altura;

	public Cactus(Texture trunkTexture, Texture upTexture, int altura, String id) {
		
		CURRENT_STATE = STATE.ANIMATING;
		CURRENT_ANIMATION = ANIMATIONS.GROWING;
	
		this.altura = altura;
		this.upTexture = upTexture;
		this.trunkTexture = trunkTexture;
		
		setId(id);

		for(int i = 0; i < altura; i++){
			Cube novo = new Cube(trunkTexture);
			novo.create();
			novo.translate(0, i, 0);
			trunk.add(novo);

		}

	}

	@Override
	public void growUp() {
				
		for(int i = 0; i < altura; i++){
			trunk.get(i).translate(0, -(transladado.y + i * escalado.y - 1), 0);	 
		}

		//aplica escala em todo mundo
		escalado.x *= 1.0005f;
		escalado.y *= 1.001f;
		escalado.z *= 1.0005f;
		
		scale( 1.0005f,  1.001f,  1.0005f);
		
		for(int i = 0; i < altura; i++){
			trunk.get(i).translate(0, (transladado.y + i * escalado.y - 1), 0); 
		}
		
		if(escalado.x >= escalaMaxima)
			CURRENT_STATE = STATE.STOPPED;
		
	}

	@Override
	public void draw() {
		
		for(Cube c: trunk){
			c.draw();
		}
		
		startAnimation();

	}

	@Override
	public void scale(float x, float y, float z) {
		
		// desenha o tronco
		for(Cube c: trunk){
			c.scale(x, y, z);
		}


	}

	@Override
	public void translate(float x, float y, float z) {
		
		transladado.x += x;
		transladado.y += y;
		transladado.z += z;
		
		for(Cube c: trunk){
			c.translate(x, y, z);
		}

	}

	@Override
	public void animation() {
		switch (CURRENT_STATE) {
		case ANIMATING:
			switch (CURRENT_ANIMATION) {
			case GROWING:
				growUp();
				break;
			default:
				break;
			}
		default:
			break;
			
		}
	}

}
