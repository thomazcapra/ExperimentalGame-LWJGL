package br.ufscar.geometry;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.ARBShaderObjects.*;

public class Square extends Geometry {

	private Vector3f v1;
	private Vector3f v2;
	private Vector3f v3;
	private Vector3f v4;
	private Texture texture;
	//private int program;
	
	public Square(Vector3f v1, Vector3f v2, Vector3f v3, Vector3f v4, Texture texture){
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		this.v4 = v4;
		
		this.texture = texture;
	
	}

	@Override
	public void draw() {
		
		texture.bind();

		glBegin(GL_QUADS);
		
		glTexCoord2f(0, 0);
		glVertex3f(v1.getX(), v1.getY(), v1.getZ());

		glTexCoord2f(1,0);
		glVertex3f(v2.getX(), v2.getY(), v2.getZ());

		glTexCoord2f(1,1);
		glVertex3f(v3.getX(), v3.getY(), v3.getZ());

		glTexCoord2f(0,1);
		glVertex3f(v4.getX(), v4.getY(), v4.getZ());
		
		glEnd();
		
		glUseProgramObjectARB(0);

		
	}

	@Override
	public void scale(float x, float y, float z) {
				
		v1.x *= x; v1.y *= y; v1.z *= z;
		v2.x *= x; v2.y *= y; v2.z *= z;
		v3.x *= x; v3.y *= y; v3.z *= z;
		v4.x *= x; v4.y *= y; v4.z *= z;
		
	}

	@Override
	public void translate(float x, float y, float z) {
		
		v1.x += x; v1.y += y; v1.z += z;
		v2.x += x; v2.y += y; v2.z += z;
		v3.x += x; v3.y += y; v3.z += z;
		v4.x += x; v4.y += y; v4.z += z;
		
	}

	//rotação em torno de Y
	@Override
	public void rotate(float angle) {
			
			Vector3f p1 = new Vector3f();
			Vector3f p2 = new Vector3f();
			Vector3f p3 = new Vector3f();
			Vector3f p4 = new Vector3f();

			
			float r = (float) Math.toRadians(angle);
			float cos = (float) Math.cos(r);
			float sin = (float) Math.sin(r);
							
			p1.x = v1.x * cos + v1.z * -sin;
			p1.y = v1.y;
			p1.z = v1.x * sin + v1.z * cos;
			
			p2.x = v2.x * cos + v2.z * -sin;
			p2.y = v2.y;
			p2.z = v2.x * sin + v2.z * cos;
			
			p3.x = v3.x * cos + v3.z * -sin;
			p3.y = v3.y;
			p3.z = v3.x * sin + v3.z * cos;
			
			p4.x = v4.x * cos + v4.z * -sin;
			p4.y = v4.y;
			p4.z = v4.x * sin + v4.z * cos;
			
			v1 = p1;
			v2 = p2;
			v3 = p3;
			v4 = p4;
				
	}
	

	public Vector3f getVertexZero() {
		return v1;
	}

}
