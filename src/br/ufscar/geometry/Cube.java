package br.ufscar.geometry;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public class Cube extends Geometry{

	//private Vector3f destinoTransladado = new Vector3f(0, 0, 0);

	private Square up;
	private Square down;
	private Square left;
	private Square right;
	private Square front;
	private Square back;

	private Texture upTexture;
	private Texture downTexture;
	//laterais
	private Texture backTexture; 
	private Texture frontTexture; 
	private Texture rightTexture;
	private Texture leftTexture;
	
	private ArrayList<Square> listaQuadrados = new ArrayList<Square>();

	public Cube(Texture texture) {

		upTexture = texture;
		downTexture = texture;

		//laterais
		backTexture = texture;
		frontTexture = texture; 
		rightTexture = texture;
		leftTexture = texture;

		create();

	}

	public Vector3f getOrigem() {
		return down.getVertexZero();
	}

	public Cube(Texture skyTexture, Texture floorTexture, Texture backWallTexture, Texture frontWallTexture,
			Texture rightWallTexture, Texture leftWallTexture) {

		this.upTexture = skyTexture;
		this.downTexture = floorTexture;

		//laterais
		this.backTexture = backWallTexture;
		this.frontTexture = frontWallTexture; 
		this.rightTexture = rightWallTexture;
		this.leftTexture = leftWallTexture;

		create();

	}


	public void create(){
		
		float quaseUm   = 0.99999999999999999999999999f;
		float quaseZero = 0.00000000000000000000000001f;

		//down Vertices
		Vector3f dv[] =  {
				new Vector3f(0, 0, 0),
				new Vector3f(1, 0, 0),
				new Vector3f(1, 0, 1),
				new Vector3f(0, 0, 1)		
		};

		//up Vertices
		Vector3f uv[] = {
				new Vector3f(0, 1, 0),
				new Vector3f(1, 1, 0),
				new Vector3f(1, 1, 1),
				new Vector3f(0, 1, 1)			
		};

		//back Vertices
		Vector3f bv[] = {
				new Vector3f(0, quaseZero, 0),
				new Vector3f(0, quaseUm, 0),
				new Vector3f(1, quaseUm, 0),
				new Vector3f(1, quaseZero, 0)
		};

		//front Vertices
		Vector3f fv[] = {
				new Vector3f(0, quaseZero, 1),
				new Vector3f(0, quaseUm, 1),
				new Vector3f(1, quaseUm, 1),
				new Vector3f(1, quaseZero, 1)			
		};

		//right Vertices
		Vector3f rv[] = {
				new Vector3f(quaseZero, quaseZero, quaseZero),
				new Vector3f(quaseZero, quaseUm, quaseZero),
				new Vector3f(quaseZero, quaseUm, quaseUm),
				new Vector3f(quaseZero, quaseZero, quaseUm)			
		};

		//left Vertices
		Vector3f lv[] = {
				new Vector3f(quaseUm, quaseZero, quaseZero),
				new Vector3f(quaseUm, quaseUm, quaseZero),
				new Vector3f(quaseUm, quaseUm, quaseUm),
				new Vector3f(quaseUm, quaseZero, quaseUm)			
		};

		up = new Square(uv[0], uv[1], uv[2], uv[3], upTexture);
		down = new Square(dv[0], dv[1], dv[2], dv[3], downTexture);
		back = new Square(bv[0], bv[1], bv[2], bv[3], backTexture);
		front = new Square(fv[0], fv[1], fv[2], fv[3], frontTexture);
		left = new Square(lv[0], lv[1], lv[2], lv[3], leftTexture);
		right = new Square(rv[0], rv[1], rv[2], rv[3], rightTexture);
		
		listaQuadrados.add(up);
		listaQuadrados.add(down);
		listaQuadrados.add(back);
		listaQuadrados.add(front);
		listaQuadrados.add(left);
		listaQuadrados.add(right);

	}

	@Override
	public void scale(float x, float y, float z) {

		Vector3f posicaoAtual = transladado;

		//colocar o centro do objeto na origem
		up.translate(-0.5f * escalado.x - posicaoAtual.x, -0.5f * escalado.y - posicaoAtual.y, -0.5f * escalado.z - posicaoAtual.z);
		down.translate(-0.5f * escalado.x - posicaoAtual.x, -0.5f * escalado.y - posicaoAtual.y, -0.5f * escalado.z - posicaoAtual.z);
		back.translate(-0.5f * escalado.x - posicaoAtual.x, -0.5f * escalado.y - posicaoAtual.y, -0.5f * escalado.z - posicaoAtual.z);
		front.translate(-0.5f * escalado.x - posicaoAtual.x, -0.5f * escalado.y - posicaoAtual.y, -0.5f * escalado.z - posicaoAtual.z);
		left.translate(-0.5f * escalado.x - posicaoAtual.x, -0.5f * escalado.y - posicaoAtual.y, -0.5f * escalado.z - posicaoAtual.z);
		right.translate(-0.5f * escalado.x - posicaoAtual.x, -0.5f * escalado.y - posicaoAtual.y, -0.5f * escalado.z - posicaoAtual.z);

		//aplica a escala
		up.scale(x, y, z);
		down.scale(x, y, z);
		back.scale(x, y, z);
		front.scale(x, y, z);
		left.scale(x, y, z);
		right.scale(x, y, z);

		//salva valores atual
		escalado.x *= x;
		escalado.y *= y;
		escalado.z *= z;

		//retorna objeto no lugar atual
		up.translate(+0.5f * escalado.x + posicaoAtual.x, +0.5f * escalado.y + posicaoAtual.y, +0.5f * escalado.z + posicaoAtual.z);
		down.translate(+0.5f * escalado.x + posicaoAtual.x, +0.5f * escalado.y + posicaoAtual.y, +0.5f * escalado.z + posicaoAtual.z);
		back.translate(+0.5f * escalado.x + posicaoAtual.x, +0.5f * escalado.y + posicaoAtual.y, +0.5f * escalado.z + posicaoAtual.z);
		front.translate(+0.5f * escalado.x + posicaoAtual.x, +0.5f * escalado.y + posicaoAtual.y, +0.5f * escalado.z + posicaoAtual.z);
		left.translate(+0.5f * escalado.x + posicaoAtual.x, +0.5f * escalado.y + posicaoAtual.y, +0.5f * escalado.z + posicaoAtual.z);
		right.translate(+0.5f * escalado.x + posicaoAtual.x, +0.5f * escalado.y + posicaoAtual.y, +0.5f * escalado.z + posicaoAtual.z);

	}

	public ArrayList<Square> getSquareList() {
		return listaQuadrados;
	}
	
	@Override
	public void translate(float x, float y, float z) {

		transladado.x += x;
		transladado.y += y;
		transladado.z += z;

		up.translate(x, y, z);
		down.translate(x, y, z);
		back.translate(x, y, z);
		front.translate(x, y, z);
		left.translate(x, y, z);
		right.translate(x, y, z);

	}

	public void draw() {

		up.draw();
		down.draw();
		back.draw();
		front.draw();
		left.draw();
		right.draw();

	}


	@Override
	public void rotate(float angle) {

		rotacionado += angle;
		Vector3f posicaoAtual = transladado;

		//colocar o centro do objeto na origem
		up.translate(-0.5f * escalado.x - posicaoAtual.x, -0.5f * escalado.y - posicaoAtual.y, -0.5f * escalado.z - posicaoAtual.z);
		down.translate(-0.5f * escalado.x - posicaoAtual.x, -0.5f * escalado.y - posicaoAtual.y, -0.5f * escalado.z - posicaoAtual.z);
		back.translate(-0.5f * escalado.x - posicaoAtual.x, -0.5f * escalado.y - posicaoAtual.y, -0.5f * escalado.z - posicaoAtual.z);
		front.translate(-0.5f * escalado.x - posicaoAtual.x, -0.5f * escalado.y - posicaoAtual.y, -0.5f * escalado.z - posicaoAtual.z);
		left.translate(-0.5f * escalado.x - posicaoAtual.x, -0.5f * escalado.y - posicaoAtual.y, -0.5f * escalado.z - posicaoAtual.z);
		right.translate(-0.5f * escalado.x - posicaoAtual.x, -0.5f * escalado.y - posicaoAtual.y, -0.5f * escalado.z - posicaoAtual.z);

		up.rotate(angle);
		down.rotate(angle);
		back.rotate(angle);
		front.rotate(angle);
		left.rotate(angle);
		right.rotate(angle);

		//salva valores atual
		rotacionado += angle;

		//retorna objeto no lugar atual
		up.translate(+0.5f * escalado.x + posicaoAtual.x, +0.5f * escalado.y + posicaoAtual.y, +0.5f * escalado.z + posicaoAtual.z);
		down.translate(+0.5f * escalado.x + posicaoAtual.x, +0.5f * escalado.y + posicaoAtual.y, +0.5f * escalado.z + posicaoAtual.z);
		back.translate(+0.5f * escalado.x + posicaoAtual.x, +0.5f * escalado.y + posicaoAtual.y, +0.5f * escalado.z + posicaoAtual.z);
		front.translate(+0.5f * escalado.x + posicaoAtual.x, +0.5f * escalado.y + posicaoAtual.y, +0.5f * escalado.z + posicaoAtual.z);
		left.translate(+0.5f * escalado.x + posicaoAtual.x, +0.5f * escalado.y + posicaoAtual.y, +0.5f * escalado.z + posicaoAtual.z);
		right.translate(+0.5f * escalado.x + posicaoAtual.x, +0.5f * escalado.y + posicaoAtual.y, +0.5f * escalado.z + posicaoAtual.z);

	}

	public Vector3f origem() {
		return down.getVertexZero();
	}
	
	public int getRotacionado(){
		return rotacionado;
	}

}
