package br.ufscar.objects.statics;

import org.lwjgl.util.vector.Vector3f;

public abstract class StaticObject {


	public abstract void draw();
	public abstract void scale(float x, float y, float z);
	public abstract void translate(float x, float y, float z);
	
	protected Vector3f transladado = new Vector3f(0, 0, 0);
	protected Vector3f escalado = new Vector3f(1, 1, 1);
}
