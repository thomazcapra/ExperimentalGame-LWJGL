package br.ufscar.objects.animated.plants;
import org.newdawn.slick.opengl.Texture;

import br.ufscar.geometry.Cube;

public class MediumTree extends Tree{

	//arvore média
	public MediumTree(Texture leafTexture, Texture trunkTexture, String id) {


		this.leafTexture = leafTexture;
		this.trunkTexture = trunkTexture;

		setId(id);
		CURRENT_STATE = STATE.ANIMATING;

		//quatro troncos, manter a proporção do eixo x e do z
		for(int i = 0; i < 4; i++){
			Cube novo = new Cube(trunkTexture);
			novo.create();
			trunk.add(novo);
		}

		//graveto
		for(int i = 0; i < 17; i++){
			Cube novaFolha = new Cube(leafTexture);
			novaFolha.create();
			leaf.add(novaFolha);
		}

		//ANTES DE TRANSLADAR, TEMOS QUE APLICAR A ESCALA NO CUBO

		leaf.get(0).translate(1, 6, 1);//pico da arvore

		int indice = 1;
		for(int x = 0; x < 3; x++){
			for(int z = 0; z < 3; z++){
				for(int y = 4; y < 6; y++){
					if(x != 1 || z != 1){ //para eliminar as duas folhas internas
						leaf.get(indice).translate(x, y, z);
						indice++;
					}
				}
			}
		}

		//um unico tronco
		trunk.get(0).translate(1, 0, 1);
		trunk.get(1).translate(1, 1, 1);
		trunk.get(2).translate(1, 2, 1);
		trunk.get(3).translate(1, 3, 1);


	}

	@Override
	public void growUp() {
		growUpArvoreMedia();
	}

	private void growUpArvoreMedia(){

		//um unico tronco
		trunk.get(1).translate(0, -(transladado.y + escalado.y - 1), 0);
		trunk.get(2).translate(0, -(transladado.y + 2 * escalado.y - 1), 0);
		trunk.get(3).translate(0, -(transladado.y + 3 * escalado.y - 1), 0);

		leaf.get(0).translate(0, -(transladado.y + 6 * escalado.y - 1) , 0);//pico da arvore


		int indice = 1;
		float variacaoX = 0 , variacaoZ = 0;


		for(int x = 0; x < 3; x++){
			for(int z = 0; z < 3; z++){
				for(int y = 4; y < 6; y++){
					if(x != 1 || z != 1){ //para eliminar as duas folhas internas
						if(x == 0) variacaoX = (escalado.x - 1); if (x == 2) variacaoX = -(transladado.x + escalado.x - 1);
						if(z == 0) variacaoZ = (escalado.z - 1); if(z == 2) variacaoZ = -(transladado.z + escalado.z - 1);
						if(x == 1) variacaoX = 0; if(z == 1) variacaoZ = 0;

						leaf.get(indice).translate(variacaoX, -(transladado.y + (y) * escalado.y - 1), variacaoZ);
						indice++;
					}
				}
			}
		}

		//aplica escala em todo mundo
		escalado.x *= 1.0005f;
		escalado.y *= 1.001f;
		escalado.z *= 1.0005f;

		//escala nas folhas
		for(Cube t: trunk){
			t.scale(1.0005f, 1.001f, 1.0005f);
		}

		for(Cube l: leaf){
			l.scale(1.0005f, 1.001f, 1.0005f);
		}

		indice = 1;
		for(int x = 0; x < 3; x++){
			for(int z = 0; z < 3; z++){
				for(int y = 4; y < 6; y++){
					if(x != 1 || z != 1){ //para eliminar as duas folhas internas
						if(x == 0) variacaoX = -(escalado.x - 1); if (x == 2) variacaoX = (transladado.x + escalado.x - 1);
						if(z == 0) variacaoZ = -(escalado.z - 1); if(z == 2) variacaoZ = (transladado.z +  escalado.z - 1);
						if(x == 1) variacaoX = 0; if(z == 1) variacaoZ = 0;


						leaf.get(indice).translate(variacaoX, (transladado.y + (y) * escalado.y - 1), variacaoZ);
						indice++;
					}
				}
			}
		}


		leaf.get(0).translate(0, (transladado.y + 6 * escalado.y - 1) , 0);//pico da arvore

		//um unico tronco
		trunk.get(1).translate(0, transladado.y + escalado.y - 1, 0);
		trunk.get(2).translate(0, transladado.y + 2 * escalado.y - 1, 0);
		trunk.get(3).translate(0, transladado.y + 3 * escalado.y - 1, 0);

		if(escalado.x >= escalaMaxima)
			CURRENT_STATE = STATE.STOPPED;
	}

}
