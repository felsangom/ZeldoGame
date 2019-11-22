package br.com.fsg.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.fsg.main.Game;
import br.com.fsg.world.Camera;
import br.com.fsg.world.World;

public class Player extends Entity {

	public int maxLife = 100;
	public int life = maxLife;
	public int ammo = 0;

	public int speed = 2;
	public boolean left, up, right, down;

	private BufferedImage lastRendered;

	private int frames = 0;
	private int maxFrames = 7;
	private int spriteIndex = 0;
	private int maxSpriteIndex = 2;
	private boolean increasingAnimation = true;
	
	private BufferedImage[] playerRight = {
		Game.spritesheet.getSprite(0, 10 * 32, width, height),
		Game.spritesheet.getSprite(1 * 32, 10 * 32, width, height),
		Game.spritesheet.getSprite(2 * 32, 10 * 32, width, height)
	};

	private BufferedImage[] playerLeft = {
		Game.spritesheet.getSprite(0, 9 * 32, width, height),
		Game.spritesheet.getSprite(1 * 32, 9 * 32, width, height),
		Game.spritesheet.getSprite(2 * 32, 9 * 32, width, height)
	};

	private BufferedImage[] playerUp = {
		Game.spritesheet.getSprite(0, 11 * 32, width, height),
		Game.spritesheet.getSprite(1 * 32, 11 * 32, width, height),
		Game.spritesheet.getSprite(2 * 32, 11 * 32, width, height)
	};

	private BufferedImage[] playerDown = {
		Game.spritesheet.getSprite(0, 8 * 32, width, height),
		Game.spritesheet.getSprite(1 * 32, 8 * 32, width, height),
		Game.spritesheet.getSprite(2 * 32, 8 * 32, width, height)
	};

	public Player(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}

	public void tick() {
		if (right && World.isFree(this.getX() + speed, this.getY())) {
			x += speed;
		}

		if (left && World.isFree(this.getX() - speed, this.getY())) {
			x -= speed;
		}

		if (up && World.isFree(this.getX(), this.getY() - speed)) {
			y -= speed;
		}

		if (down && World.isFree(this.getX(), this.getY() + speed)) {
			y += speed;
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

		collectItem();
		
		Camera.x = Camera.clamp(this.getX() - (Game.WINDOW_WIDTH / 2), 0, World.totalMapWidth - Game.WINDOW_WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.WINDOW_HEIGHT / 2), 0, World.totalMapHeight - Game.WINDOW_HEIGHT);

		super.tick();
	}

	private void collectItem() {
		for (Entity entity : Game.entities) {
			if (entity.collidingWith(Game.player)) {
				if (entity instanceof Potion) {
					if (Game.player.life + Potion.restore <= Game.player.maxLife) {
						Game.player.life += Potion.restore;
					} else {
						Game.player.life = Game.player.maxLife;
					}

					Game.entitiesToRemove.add(entity);
				} else if (entity instanceof Ammo) {
					Game.player.ammo += 2;
					Game.entitiesToRemove.add(entity);
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if (right) {
			lastRendered = playerRight[spriteIndex]; 
		} else if (left) {
			lastRendered = playerLeft[spriteIndex];
		}

		if (up) {
			lastRendered = playerUp[spriteIndex];
		} else if (down) {
			lastRendered = playerDown[spriteIndex];
		}

		if (lastRendered == null) {
			lastRendered = playerDown[0];
		}

		g.drawImage(lastRendered, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
