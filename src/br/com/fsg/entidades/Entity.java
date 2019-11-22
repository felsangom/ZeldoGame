package br.com.fsg.entidades;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import br.com.fsg.main.Game;
import br.com.fsg.world.Camera;
import br.com.fsg.world.Tile;

public class Entity {

	public static BufferedImage ENEMY_SPRITE = Game.spritesheet.getSprite(6 * Tile.WIDTH, 8 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	public static BufferedImage WEAPON_SPRITE = Game.spritesheet.getSprite(9 * Tile.WIDTH, 15 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	public static BufferedImage AMMO_SPRITE = Game.spritesheet.getSprite(10 * Tile.WIDTH, 15 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	public static BufferedImage POTION_SPRITE = Game.spritesheet.getSprite(11 * Tile.WIDTH, 15 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	
	private BufferedImage sprite;

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected Rectangle collisionArea;

	public Entity(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		collisionArea = new Rectangle(x + 5, y + 5, this.width - 10, this.height - 10);
	}

	public int getX() {
		return (int) this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int) this.y;
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

	public boolean collidingWith(Entity other) {
		return this.collisionArea.intersects(other.collisionArea);
	}
	
	public void tick() {
		collisionArea = new Rectangle((int) x + 5, (int) y + 5, this.width - 10, this.height - 10);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
