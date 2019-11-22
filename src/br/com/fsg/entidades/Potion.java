package br.com.fsg.entidades;

import java.awt.image.BufferedImage;

public class Potion extends Entity {

	public static int restore = 10;
	
	public Potion(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}
}
