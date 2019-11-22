package br.com.fsg.entidades;

import java.awt.image.BufferedImage;

public class Ammo extends Entity {

	public Ammo(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}

	public void tick() {
		respawn();
		super.tick();
	}

	public void respawn() {
		if (!visible) {
			if (System.currentTimeMillis() >= collectedTime + 10000) {
				visible = true;
			}
		}		
	}
}
