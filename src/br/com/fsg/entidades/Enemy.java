package br.com.fsg.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.fsg.main.Game;
import br.com.fsg.world.Camera;
import br.com.fsg.world.Tile;
import br.com.fsg.world.World;

public class Enemy extends Entity {

	public boolean left, up, right, down;
	private double minSpeed = 0.6;
	private double maxSpeed = 1.4;
	private double speed;

	private BufferedImage[] enemyRight = {
		Game.spritesheet.getSprite(6 * Tile.WIDTH, 10 * Tile.HEIGHT, width, height),
		Game.spritesheet.getSprite(7 * Tile.WIDTH, 10 * Tile.HEIGHT, width, height),
		Game.spritesheet.getSprite(8 * Tile.WIDTH, 10 * Tile.HEIGHT, width, height)
	};

	private BufferedImage[] enemyLeft = {
		Game.spritesheet.getSprite(6 * Tile.WIDTH, 9 * Tile.HEIGHT, width, height),
		Game.spritesheet.getSprite(7 * Tile.WIDTH, 9 * Tile.HEIGHT, width, height),
		Game.spritesheet.getSprite(8 * Tile.WIDTH, 9 * Tile.HEIGHT, width, height)
	};

	private BufferedImage[] enemyUp = {
		Game.spritesheet.getSprite(6 * Tile.WIDTH, 11 * Tile.HEIGHT, width, height),
		Game.spritesheet.getSprite(7 * Tile.WIDTH, 11 * Tile.HEIGHT, width, height),
		Game.spritesheet.getSprite(8 * Tile.WIDTH, 11 * Tile.HEIGHT, width, height)
	};

	private BufferedImage[] enemyDown = {
		Game.spritesheet.getSprite(6 * Tile.WIDTH, 8 * Tile.HEIGHT, width, height),
		Game.spritesheet.getSprite(7 * Tile.WIDTH, 8 * Tile.HEIGHT, width, height),
		Game.spritesheet.getSprite(8 * Tile.WIDTH, 8 * Tile.HEIGHT, width, height)
	};

	private BufferedImage lastRendered;

	private int frames = 0;
	private int maxFrames = 7;
	private int spriteIndex = 0;
	private int maxSpriteIndex = 2;
	private boolean increasingAnimation = true;
	private double lastTimeAttacked;
	private int attackSpeed;
	
	public Enemy(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
		speed = minSpeed + (maxSpeed - minSpeed) * Game.random.nextDouble();
		attackSpeed = Game.random.nextInt((1000 - 800) + 1) + 800;
		lastTimeAttacked = System.currentTimeMillis();
	}

	@Override
	public void tick() {
		if (collidingWith(Game.player)) {
			if (Game.random.nextInt(100) < 10) {
				if (System.currentTimeMillis() - lastTimeAttacked >= attackSpeed) {
					lastTimeAttacked = System.currentTimeMillis();

					int damage = Game.random.nextInt(15);
					if (damage > 0) {
						Game.player.tookDamage = true;
						Game.player.life -= damage;
					} else {
						// Errou o ataque
					}
				}
			}

			return;
		}

		if (Game.random.nextInt(10) < 3) {
			if ((int) x < Game.player.x && World.isFree((int) (x + speed), (int) y)) {
				x += speed;
				right = true;
				left = false;
			} else if ((int) x > Game.player.x && World.isFree((int) (x - speed), (int) y)) {
				x -= speed;
				right = false;
				left = true;
			}

			if ((int) y < Game.player.y && World.isFree((int) x, (int) (y + speed))) {
				y += speed;
				up = false;
				down = true;
			} else if ((int) y > Game.player.y && World.isFree((int) x, (int) (y - speed))) {
				y -= speed;
				up = true;
				down = false;
			}

			if (right || left || up || down) {
				frames++;
				if (frames == maxFrames) {
					frames = 0;

					if (spriteIndex == maxSpriteIndex) {
						increasingAnimation = false;
					} else if (spriteIndex == 0) {
						increasingAnimation = true;
					}

					if (increasingAnimation) {
						spriteIndex++;
					} else {
						spriteIndex--;
					}
				}
			}

			if ((int) x == Game.player.x) {
				left = false;
				right = false;
			}

			if ((int) y == Game.player.y) {
				up = false;
				down = false;
			}
		}

		super.tick();
	}

	public void render(Graphics g) {
		if (up) {
			lastRendered = enemyUp[spriteIndex];
		} else if (down) {
			lastRendered = enemyDown[spriteIndex];
		} else if (right) {
			lastRendered = enemyRight[spriteIndex]; 
		} else if (left) {
			lastRendered = enemyLeft[spriteIndex];
		}

		if (lastRendered == null) {
			lastRendered = enemyDown[0];
		}

		g.drawImage(lastRendered, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
