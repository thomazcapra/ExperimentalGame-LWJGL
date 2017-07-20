package br.ufscar.objects.animated;

import org.lwjgl.util.vector.Vector3f;

public abstract class AnimatedObject {

	public abstract void draw();
	public abstract void scale(float x, float y, float z);
	public abstract void translate(float x, float y, float z);
	
	protected Vector3f transladado = new Vector3f(0, 0, 0);
	protected Vector3f escalado = new Vector3f(1, 1, 1);
	
	public static enum STATE{ ANIMATING, STOPPED }
	
	public STATE CURRENT_STATE = STATE.STOPPED; 
	
	private String id;
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public abstract void animation();
	
	public void startAnimation(){
		animation();
	}
	
}
