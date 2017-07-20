package br.ufscar.objects.animated.plants;

import java.util.ArrayList;



import org.lwjgl.Sys;
//import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import br.ufscar.geometry.Cube;
import br.ufscar.objects.animated.AnimatedObject;


public class Cloud extends AnimatedObject {

	private ArrayList<Cube> clouds = new ArrayList<Cube>();
	private Texture cloudTexture;
	
	private static boolean translacaoNegatia = false;

	//pedra pequena
	public Cloud(Texture cloudTexture, String id) {
		
		CURRENT_STATE = STATE.ANIMATING;
		
		this.cloudTexture = cloudTexture;

		setId(id);
		
		Cube novo = new Cube(this.cloudTexture);
		clouds.add(novo);
	}

	public void translate(float x, float y, float z) {

		transladado.x += x;
		transladado.y += y;
		transladado.z += z;

		// translada as pedras
		for(Cube c: clouds){
			c.translate(x, y, z);
		}
	}

	@Override
	public void scale(float x, float y, float z) {

		escalado.x *= x;
		escalado.y *= y;
		escalado.z *= z;

//		Vector3f posicao = transladado;
//
//		// translada para origem
//		for(Cube c: clouds){
//			c.translate(-transladado.x, -transladado.y, -transladado.z);
//		}

		// aplica escala
		for(Cube c: clouds){
			c.scale(x, y, z);
		}

//		// translada para o local
//		for(Cube c: clouds){
//			c.translate(posicao.x, posicao.y, posicao.z);
//		}
	}
	public void draw() {

		//desenha todos cubos
		for(Cube c: clouds){
			c.draw();
		}
		
		startAnimation();
	}

	public void floating() {
		translacao();
//		// com o ponto na origem, tenho que verificar a escala x 1,1,1 para saber até onde o quadrado chega, e depois somar com o transladado
//		//if(this.clouds.get(0).getOrigem())
//		verificaColisaoComCenario();
//		if(translacaoNegatia)
//			translate(-0.1f, -0.0001f, -0.1f);
//		else
//			translate(0.1f, 0.0001f, 0.1f);

	}

	//BOUDING BOX  QUE NÃO ESTOU USANDO MAIS
	public void verificaColisaoComCenario(){
		
		Vector3f verticeOrigem= new Vector3f(transladado.x, transladado.y, transladado.z);
		Vector3f verticeExtremo = new Vector3f(escalado.x + transladado.x, escalado.y + transladado.y, escalado.z + transladado.z);

		if(translacaoNegatia) {
			if(verticeOrigem.x <= 5.0f || verticeOrigem.y <= 5.0f || verticeOrigem.z <= 5.0f){ 
				translacaoNegatia = false;
			}
		}
		else {

			if(verticeExtremo.x >= 512 || verticeExtremo.y >= 256 || verticeExtremo.z >= 512){ 
				translacaoNegatia = true;
			}
			
		}
		
	}
	
	public void translacao() {
				
		float x = (float) Math.sin(getTime() * 0.001f) / 20;
		float z = (float) Math.cos(getTime() * 0.001f) / 20;
		translate(x, 0.0f, z);
	}
	 private static long getTime() {
	        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	    }

	@Override
	public void animation() {
		switch (CURRENT_STATE) {
		case ANIMATING:
			floating();
			break;
		default:
			break;
		}
	}
	 

}
