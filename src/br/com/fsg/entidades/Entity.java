package br.com.fsg.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.fsg.main.Game;
import br.com.fsg.world.Tile;

public class Entity {
	public static BufferedImage ENEMY_SPRITE = Game.spritesheet.getSprite(6 * Tile.WIDTH, 8 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	public static BufferedImage WEAPON_SPRITE = Game.spritesheet.getSprite(9 * Tile.WIDTH, 15 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	public static BufferedImage AMMO_SPRITE = Game.spritesheet.getSprite(10 * Tile.WIDTH, 15 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	public static BufferedImage POTION_SPRITE = Game.spritesheet.getSprite(11 * Tile.WIDTH, 15 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	
	private BufferedImage sprite;

	protected int x;
	protected int y;
	protected int width;
	protected int height;

	public Entity(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY(int x) {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void tick() {

	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x * Tile.WIDTH, y * Tile.HEIGHT, null);
	}
}
