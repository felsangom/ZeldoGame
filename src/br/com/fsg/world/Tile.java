package br.com.fsg.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import br.com.fsg.main.Game;

public class Tile {
	public static int WIDTH = 32;
	public static int HEIGHT = 32;

	private int x, y;
	private BufferedImage sprite;
	
	public static BufferedImage WALL = Game.spritesheet.getSprite(8 * WIDTH, 6 * HEIGHT, WIDTH, HEIGHT);

	public static BufferedImage[] FLOOR = {
		Game.spritesheet.getSprite(14 * WIDTH, HEIGHT, WIDTH, HEIGHT),
		Game.spritesheet.getSprite(15 * WIDTH, HEIGHT, WIDTH, HEIGHT),
		Game.spritesheet.getSprite(14 * WIDTH, 2 * HEIGHT, WIDTH, HEIGHT),
		Game.spritesheet.getSprite(15 * WIDTH, 2 * HEIGHT, WIDTH, HEIGHT)
	};

	public static BufferedImage floor() {
		return FLOOR[new Random().nextInt(FLOOR.length)];
	}

	public static BufferedImage wall() {
		return WALL;
	}

	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, (x * WIDTH) - Camera.x, (y * HEIGHT) - Camera.y, null);
	}
}
