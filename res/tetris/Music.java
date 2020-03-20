package tetris;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {

	private Clip clip;

	public void MusicPlay(String fileName) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
			clip = AudioSystem.getClip();
			clip.stop();
			clip.open(ais);
			clip.start();
			// clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void MusicStop() {
		clip.stop();
	}

	public void MusicRestart() {
		clip.start();

	}

	public void BGMstart() {

		MusicPlay("res/music/MainBGM.wav");
	}

	public void alStart() {

		MusicPlay("res/music/" + "Move.wav");
	}

}
