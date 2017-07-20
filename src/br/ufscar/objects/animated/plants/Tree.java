package br.ufscar.objects.animated.plants;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

import br.ufscar.geometry.Cube;

public abstract class Tree extends Plant {

	/**        ESCALAS MAXIMAS DE CRESCIMENTO       **/
	protected static float escalaMaxima = 2.0f;
//	protected static float escalaMaximaEixoXFolha = 2.0f;
//	protected static float escalaMaximaEixoXTronco = 2.0f;

	protected ArrayList<Cube> leaf = new ArrayList<Cube>();
	protected ArrayList<Cube> trunk = new  ArrayList<Cube>();
	protected Texture leafTexture;
	protected Texture trunkTexture;

	public Texture getLeafTexture() {
		return leafTexture;
	}

	public void setLeafTexture(Texture leafTexture) {
		this.leafTexture = leafTexture;
	}

	public Texture getTrunkTexture() {
		return trunkTexture;
	}


	// metodos
	public void scaleTrunk(float x, float y, float z) {

		// desenha o tronco
		for(Cube c: trunk){
			c.scale(x, y, z);
		}

	}

	public void scaleLeaf(float x, float y, float z) {

		//verificar escala da folha
		for(Cube f: leaf){
			f.scale(x, y, z);
		}

	}

	public void translate(float x, float y, float z) {

		transladado.x += x;
		transladado.y += y;
		transladado.z += z;

		// translada o tronco
		for(Cube c: trunk){
			c.translate(x, y, z);
		}

		// translada as folhas
		for(Cube f: leaf){
			f.translate(x, y, z);
		}
	}

	public void draw() {

		//desenha todos cubos
		for(Cube c: trunk){
			c.draw();
		}

		for(Cube f: leaf){
			f.draw();
		}
		
		startAnimation();

	}

	@Override
	public void scale(float x, float y, float z) {

		escalado.x *= x;
		escalado.y *= y;
		escalado.z *= z;

		//if(escalado.x < escalaMaximaEixoXTronco)
			scaleTrunk(x, y, z);
		//if(escalado.x < escalaMaximaEixoXFolha)
			scaleLeaf(x, y, z);
		
	}

	public void fall(){
		
	}

	@Override
	public void animation() {
		
		switch (CURRENT_STATE) {
		case ANIMATING:

			switch (CURRENT_ANIMATION) {

			case GROWING:
				growUp();
				break;
			case FALLING:
				fall();
				break;

			}
			break;
		default:
			break;

		}
	}

	@Override
	public void growUp() {
		// TODO Auto-generated method stub
		
	}
	

}
