package br.ufscar.objects.animated.plants;

import org.newdawn.slick.opengl.Texture;

import br.ufscar.geometry.Cube;

public class SmallTree extends Tree{

	//arvore padrao
	public SmallTree(Texture leafTexture, Texture trunkTexture, String id) {

		CURRENT_STATE = STATE.ANIMATING;

		setId(id);

		this.leafTexture = leafTexture;
		this.trunkTexture = trunkTexture;

		Cube novo = new Cube(this.trunkTexture);
		novo.create();
		trunk.add(novo);

		//graveto
		for(int i = 0; i < 5; i++){
			Cube novaFolha = new Cube(this.leafTexture);
			novaFolha.create();
			leaf.add(novaFolha);
		}

		// seis folhas
		//leaf.get(0).translate(1, 1, 1); //folha do meio nao precisa existir
		leaf.get(0).translate(1, 1, 0);
		leaf.get(1).translate(0, 1, 1);
		leaf.get(2).translate(2, 1, 1);
		leaf.get(3).translate(1, 1, 2);
		leaf.get(4).translate(1, 2, 1);
		//um unico tronco
		trunk.get(0).translate(1, 0, 1);
	}

	@Override
	public void growUp() {
		growUpGraveto();
	}

	private void growUpGraveto(){

		leaf.get(0).translate(0, -(transladado.y + escalado.y - 1), (transladado.z + escalado.z - 1));
		leaf.get(1).translate((transladado.x + escalado.x - 1), -(transladado.y + escalado.y - 1), 0);
		leaf.get(2).translate(-(transladado.x + escalado.x - 1), -(transladado.y + escalado.y - 1), 0);
		leaf.get(3).translate(0, -(transladado.y + escalado.y - 1), -(transladado.z + escalado.z - 1));
		leaf.get(4).translate(0, -(transladado.y + 2 * escalado.y - 1), 0);

		//aplica escala em todo mundo
		escalado.x *= 1.0005f;
		escalado.y *= 1.001f;
		escalado.z *= 1.0005f;

		//escala nas folhas
		for(Cube f: leaf){
			f.scale(1.0005f, 1.001f, 1.0005f);
		}

		//escalas no tronco
		trunk.get(0).scale(1.0005f, 1.001f, 1.0005f);

		//unico do meio
		leaf.get(0).translate(0, transladado.y + escalado.y - 1, -(transladado.z + escalado.z - 1));
		leaf.get(1).translate(-(transladado.x + escalado.x - 1), transladado.y + escalado.y - 1, 0);
		leaf.get(2).translate(transladado.x + escalado.x - 1, transladado.y + escalado.y - 1, 0);
		leaf.get(3).translate(0, transladado.y + escalado.y - 1, transladado.z + escalado.z - 1);
		leaf.get(4).translate(0, transladado.y + 2 * escalado.y - 1, 0);		
		
		if(escalado.x >= escalaMaxima)
			CURRENT_STATE = STATE.STOPPED;

	}

}
