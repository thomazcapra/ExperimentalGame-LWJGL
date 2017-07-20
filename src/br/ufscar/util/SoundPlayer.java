package br.ufscar.util;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class SoundPlayer {

	private static SoundPlayer instance = new SoundPlayer();
	private Sound musica;
	private Sound pigSound;

	public static SoundPlayer getInstance() { 
		return instance;
	}

	public void createMusic(String path) {
		try {
			instance.musica = new Sound(path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void createPigSound(String path) {
		try {
			instance.pigSound = new Sound(path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void playMusic() {
		musica.play();
	}

	public void playPigSound() {
		//pigSound.play();
		//tom e volume, ambos variam de 0 a 1
		pigSound.play(1, 1.0f);
	}

	public boolean isPlaying() {
		return musica.playing();
	}

	public boolean pigSoundIsPlaying() {
		return pigSound.playing();
	}

	public void stopSound() {
		
		if (isPlaying()) 
			musica.stop();

		if(pigSoundIsPlaying())
			pigSound.stop();

	}
}
