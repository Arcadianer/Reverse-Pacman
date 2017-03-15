import java.applet.AudioClip;
import java.io.IOException;
import java.lang.reflect.Constructor;

import javax.print.MultiDocPrintService;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * Responsible for the sounds
 */
public class Sounds {
	public static Clip menusound;
	public static Clip death;
	public static Clip gamemusic;
	public static Clip chomp;
	public static Clip plop1;
	public static Clip plop2;
	public static Clip plop3;
	public static Clip plop4;
	public static Clip start;
	public static Clip ppsound;
	public static Clip eatghost;
	//public static boolean newmusic = false;
/**
 * Constructor of the {@link Sounds} Class
 */
	public Sounds() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		menusound = AudioSystem.getClip();
		death = AudioSystem.getClip();
		chomp = AudioSystem.getClip();
		gamemusic = AudioSystem.getClip();
		plop1 = AudioSystem.getClip();
		plop2 = AudioSystem.getClip();
		plop3 = AudioSystem.getClip();
		plop4 = AudioSystem.getClip();
		start =AudioSystem.getClip();
		ppsound=AudioSystem.getClip();
		eatghost=AudioSystem.getClip();

		menusound.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/pacman_beginning.wav")));

		menusound.setFramePosition(0);
		menusound.setLoopPoints(0, -1);

		death.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/pacman_death.wav")));
		death.setFramePosition(0);

		chomp.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/pacman_chomp.wav")));
		chomp.setFramePosition(0);
		chomp.setLoopPoints(0, -1);
		
		start.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/start.wav")));
		chomp.setFramePosition(0);
		chomp.setLoopPoints(0, -1);

		plop1.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/plop1.wav")));
		plop1.setFramePosition(0);
		plop2.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/plop2.wav")));
		plop2.setFramePosition(0);
		plop3.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/plop3.wav")));
		plop3.setFramePosition(0);
		plop4.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/plop4.wav")));
		plop4.setFramePosition(0);
		ppsound.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/ppsound.wav")));
		ppsound.setFramePosition(0);
		eatghost.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/pacman_eatfruit.wav")));
		eatghost.setFramePosition(0);

		
		

	}
	/**
	 * Sets game music from normal to special
	 * @param newmusic false for normal music
	 */
public void setgamemusic(boolean newmusic) throws LineUnavailableException, IOException, UnsupportedAudioFileException{
	
	if (newmusic) {
		int temp = (int) (Math.random() * 3);
		System.out.println("MUSIC " + temp);

		switch (temp) {
		case 0:
			gamemusic.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/music1.wav")));
			break;
		case 1:
			gamemusic.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/music2.wav")));
			break;
		case 2:
			gamemusic.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/music3.wav")));
			break;

		}
	}else{
		int temp = (int) (Math.random() * 3);
		System.out.println("MUSIC " + temp);

		switch (temp) {
		case 0:
			gamemusic.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/music4.wav")));
			break;
		case 1:
			gamemusic.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/music5.wav")));
			break;
		case 2:
			gamemusic.open(AudioSystem.getAudioInputStream(getClass().getResource("sounds/music6.wav")));
			break;

		}
	}
	gamemusic.setFramePosition(0);
	gamemusic.setLoopPoints(0, -1);
}
/**
 * Plays plop Sound
 */
	public static void plop() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		int temp = (int) (Math.random() * 4);

		switch (temp) {
		case 0:
			plop1.setFramePosition(0);
			plop1.start();
			break;
		case 1:
			plop2.setFramePosition(0);
			plop2.start();
			break;
		case 2:
			plop3.setFramePosition(0);
			plop3.start();
			break;

		case 3:
			plop4.setFramePosition(0);
			plop4.start();
			break;

		}
	}

}
