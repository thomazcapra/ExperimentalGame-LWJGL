package br.ufscar.objects.animated.plants;

import br.ufscar.objects.animated.AnimatedObject;


public abstract class Plant extends AnimatedObject{

	public enum ANIMATIONS{

		GROWING(1),
		FALLING(2);

		int op;
		private ANIMATIONS(int op){
			this.op = op;

		} 

		public int getState(){
			return op;
		}

	}

	public ANIMATIONS CURRENT_ANIMATION = ANIMATIONS.GROWING; 
	
	// definir metods
	public abstract void growUp();
}
