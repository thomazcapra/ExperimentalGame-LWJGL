package br.ufscar.objects.statics;

import java.util.ArrayList;
import org.newdawn.slick.opengl.Texture;

import br.ufscar.geometry.Cube;


public class Stone extends StaticObject {

	private ArrayList<Cube> stones = new ArrayList<Cube>();
	private Texture stoneTexture;

	//pedra pequena
	public Stone(Texture stoneTexture, int scaleFactor) {
		this.stoneTexture = stoneTexture;

		Cube novo = new Cube(this.stoneTexture);
		stones.add(novo);
		stones.get(0).scale(scaleFactor, scaleFactor, scaleFactor);
	}

	public void translate(float x, float y, float z) {

		transladado.x += x;
		transladado.y += y;
		transladado.z += z;

		// translada as pedras
		for(Cube s: stones){
			s.translate(x, y, z);
		}
	}


	public void scale(float x, float y, float z) {

		//		Vector3f posicao = transladado;

		// translada para origem
		//		for(Cube s: stones){
		//			s.translate(-transladado.x, -transladado.y, -transladado.z);
		//		}

		//salva valores atual
		escalado.x *= x;
		escalado.y *= y;
		escalado.z *= z;
		// aplica escala
		for(Cube s: stones){
			s.scale(x, y, z);
		}

		// translada para o local
		//		for(Cube s: stones){
		//			s.translate(posicao.x, posicao.y, posicao.z);
		//		}
	}
	public void draw() {

		//desenha todos cubos
		for(Cube s: stones){
			s.draw();
		}
	}

	public void rotate(float angle) {

		//Vector3f posicao = transladado;

		// translada para origem
//		for(Cube s: stones){
//			s.translate(-transladado.x + (-0.5f), -transladado.y + (-0.5f), -transladado.z + (-0.5f));
//		}

		// aplica escala
		for(Cube s: stones){
			s.rotate(angle);
		}

//		// translada para o local
//		for(Cube s: stones){
//			s.translate(posicao.x + (0.5f), posicao.y + (0.5f), posicao.z + (0.5f));
//		}
	}

}
