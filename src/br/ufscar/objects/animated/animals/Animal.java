package br.ufscar.objects.animated.animals;

import br.ufscar.objects.animated.AnimatedObject;

public abstract class Animal extends AnimatedObject {


	public enum ANIMATIONS{
		MOVING, LOOKING_AROUND, TURNING;
	}
	
	public ANIMATIONS CURRENT_ANIMATION = ANIMATIONS.MOVING; 
	
	// animations
	public abstract void lookAround();
	public abstract void moving();
	
}
