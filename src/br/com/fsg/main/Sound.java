package br.com.fsg.main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	private AudioClip clip;

	public static final Sound backgroundMusic = new Sound("/sounds/background.wav");
	public static final Sound menuMusic = new Sound("/sounds/menu_background.wav");
	public static final Sound potion = new Sound("/sounds/potion.wav");
	public static final Sound punch = new Sound("/sounds/punch1.wav");
	public static final Sound strongPunch = new Sound("/sounds/punch2.wav");
	public static final Sound missed = new Sound("/sounds/miss.wav");
	public static final Sound arrow = new Sound("/sounds/arrow.wav");
	public static final Sound arrowDamage = new Sound("/sounds/arrow_damage.wav");
	
	private Sound(String path) {
		try {
			clip = Applet.newAudioClip(getClass().getResource(path));
		} catch(Exception e) {}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();			
		} catch(Exception e) {}
	}

	public void stop() {
		try {
			new Thread() {
				public void run() {
					clip.stop();
				}
			}.start();			
		} catch(Exception e) {}
	}

	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		} catch(Exception e) {}
	}
}
