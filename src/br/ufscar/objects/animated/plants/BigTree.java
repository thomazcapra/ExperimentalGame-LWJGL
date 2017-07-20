package br.ufscar.objects.animated.plants;

import org.newdawn.slick.opengl.Texture;

import br.ufscar.geometry.Cube;

public class BigTree extends Tree {

	public BigTree(int alturaInicial, Texture trunkTexture, Texture leafTexture) {
		for(int i = 0; i < alturaInicial; i++){
			Cube novo = new Cube(trunkTexture);
			novo.create();
			trunk.add(novo);
		}


	}
}
