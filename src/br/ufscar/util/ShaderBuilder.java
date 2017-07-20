package br.ufscar.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
//import static org.lwjgl.opengl.EXTGeometryShader4.*;

/*
 * @author Jesley Caceres Marcelino
 * 
 * Before you use this class, you need know which methods to use.
 * 1: Write your shaders code.
 * 2: Load Shader, using loadShader
 * 3: Create Shader Object 
 * 3: Attach source code to a shader object 
 * 4: Compile Shader
 * 5: Link Shader, attach shader objects to a program
 */
public class ShaderBuilder {
	
	private ArrayList<Integer> shaders;

	// create our shader, just vertex and fragment shaders only
	public ShaderBuilder(){
		shaders = new ArrayList<Integer>();
	}

	public int createVertexShader(String vertexShaderPath){
		
		
		String src = loadShader(vertexShaderPath);
		int vertexShaderObject = ARBShaderObjects.glCreateShaderObjectARB(GL_VERTEX_SHADER);
		ARBShaderObjects.glShaderSourceARB(vertexShaderObject, src);
		compileShader(vertexShaderObject);
		
		return vertexShaderObject;
	}

	public int createFragmentShader(String fragmentShaderPath){
		
		String src = loadShader(fragmentShaderPath);
		int fragmentShaderObject = ARBShaderObjects.glCreateShaderObjectARB(GL_FRAGMENT_SHADER);
		ARBShaderObjects.glShaderSourceARB(fragmentShaderObject, src);
		compileShader(fragmentShaderObject);
		
		return fragmentShaderObject;
	}
	
	private void compileShader(int shaderObject){

		ARBShaderObjects.glCompileShaderARB(shaderObject);

		// if failed to compile
		if (ARBShaderObjects.glGetObjectParameteriARB(shaderObject, 
			ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL_FALSE)
				throw new RuntimeException("Error compiling shader(" + shaderObject + "): " + getLogInfo(shaderObject)); 
	}

	// link shaders in a program.			
	// if error, returns -1
	public int createProgram(){

		int programObject = glCreateProgram();
		
		// attach shaders to program
		for(int i : shaders){
			glAttachShader(programObject, i);
		}
		
		glLinkProgram(programObject);
		if (ARBShaderObjects.glGetObjectParameteriARB(programObject, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(getLogInfo(programObject));
			return -1;
		}

		ARBShaderObjects.glValidateProgramARB(programObject);
		if (ARBShaderObjects.glGetObjectParameteriARB(programObject, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
			System.err.println(getLogInfo(programObject));
			return -1;
		}
		
		return programObject;
	}
	
	// read the file's content, and put it on a string
	private String loadShader(String shaderPath){

		StringBuilder sb = new StringBuilder();
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(shaderPath);
			br = new BufferedReader(fr);

			String linha = br.readLine();
			while(linha != null){
				sb.append(linha);
				linha = br.readLine();
			}

			if(br != null)
				br.close();

			if(fr != null)
				fr.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();	
		}

		return sb.toString();
	}
	
	private static String getLogInfo(int obj) {
		return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
	}
	
	public void addShader(int shaderObject) {
		shaders.add(shaderObject);
	}

}
