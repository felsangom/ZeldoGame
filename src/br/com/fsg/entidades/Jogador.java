package br.com.fsg.entidades;

import java.awt.image.BufferedImage;

public class Jogador extends Entidade {

	public boolean left, up, right, down;
	public int speed = 2;
	
	public Jogador(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}

	public void tick() {
		if (right)
			x += speed;

		if (left)
			x -= speed;

		if (up)
			y -= speed;

		if (down)
			y += speed;
	}
}
