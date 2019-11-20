package br.com.fsg.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.fsg.main.Game;

public class Player extends Entity {

	public boolean left, up, right, down;
	public int speed = 2;

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

	private BufferedImage lastRendered;

	private int frames = 0;
	private int maxFrames = 5;
	private int spriteIndex = 0;
	private int maxSpriteIndex = 2;
	private boolean increasingAnimation = true;
	
	public Player(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}

	public void tick() {
	
		if (right) {
			x += speed;
		}

		if (left) {
			x -= speed;
		}

		if (up) {
			y -= speed;
		}

		if (down) {
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

		g.drawImage(lastRendered, x, y, null);
	}
}
