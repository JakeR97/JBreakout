import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundEffect {
		Clip clip;
		
		public SoundEffect(String soundFileName) {
			
			try {
				File file = new File(soundFileName);
				AudioInputStream sound = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(sound);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void play() {
			clip.setFramePosition(0);
			clip.start();
		}
		
		public void stop() {
			clip.stop();
		}
		
		public void loop() {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		
	}