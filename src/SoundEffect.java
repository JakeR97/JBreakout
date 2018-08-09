import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/** This class creates the sound effects and music for the
 * game, and provides multiple functionalities for the 
 * sounds 
 * 
 * @author reardj3
 * @version 1.0.2 (August 9th, 2018)
 *
 */

public class SoundEffect {
		Clip clip;
		
		/** The constructor created the audio clip from an InputStream */
		public SoundEffect(InputStream soundFileName) {
			try {	
				InputStream buffIn = new BufferedInputStream(soundFileName);
				AudioInputStream sound = AudioSystem.getAudioInputStream(buffIn);
				clip = AudioSystem.getClip();
				clip.open(sound);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** Play the audio clip once */
		public void play() {
			clip.setFramePosition(0);
			clip.start();
		}
		
		/** Stop the audioclip */
		public void stop() {
			clip.stop();
		}
		
		/** Play the audio clip repeatedly on loop */
		public void loop() {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		
		
	}