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

	public static BufferedImage ARROW_LEFT = Game.spritesheet.getSprite(12 * Tile.WIDTH, 14 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	public static BufferedImage ARROW_RIGHT = Game.spritesheet.getSprite(13 * Tile.WIDTH, 14 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	public static BufferedImage ARROW_UP = Game.spritesheet.getSprite(14 * Tile.WIDTH, 14 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	public static BufferedImage ARROW_DOWN = Game.spritesheet.getSprite(15 * Tile.WIDTH, 14 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	
	protected BufferedImage sprite;

	protected boolean visible = true;
	protected double collectedTime;

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
		if (this.visible && other.visible) {
			return this.collisionArea.intersects(other.collisionArea);			
		}

		return false;
	}

	public double distanceTo(Entity other) {
		return Math.sqrt(((this.x - other.x) * (this.x - other.x)) + ((this.y - other.y) * (this.y - other.y)));
	}
	
	public void tick() {
		collisionArea = new Rectangle((int) x + 5, (int) y + 5, this.width - 10, this.height - 10);
	}
	
	public void render(Graphics g) {
		if (visible) {
			g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);			
		}
	}
}
