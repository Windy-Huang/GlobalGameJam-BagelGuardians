package ui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[5];

    public Sound() {
        soundURL[0] = getClass().getResource("/whip.wav");
        soundURL[1] = getClass().getResource("/Level2.7.wav");
        soundURL[2] = getClass().getResource("/beep.wav");
        soundURL[3] = getClass().getResource("/crying.wav");
    }

    public void setFile(int i) {
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {}
    }

    public void playSoundEffect(int i) {
        setFile(i);
        clip.start();
    }

    public void playBackgroundMusic(int i) {
        setFile(i);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
