package br.com.fsg.entidades;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.fsg.main.Game;

public class Jogador extends Entidade {

	public boolean left, up, right, down;
	public int speed = 2;

	private BufferedImage[] playerRight;
	private BufferedImage[] playerLeft;
	private BufferedImage[] playerUp;
	private BufferedImage[] playerDown;
	private BufferedImage lastRendered;

	private int frames = 0;
	private int maxFrames = 5;
	private int spriteIndex = 0;
	private int maxSpriteIndex = 2;
	private boolean increasingAnimation = true;
	
	public Jogador(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);

		playerRight = new BufferedImage[3];
		playerRight[0] = Game.spritesheet.getSprite(0, 10 * 32, width, height);
		playerRight[1] = Game.spritesheet.getSprite(1 * 32, 10 * 32, width, height);
		playerRight[2] = Game.spritesheet.getSprite(2 * 32, 10 * 32, width, height);

		playerLeft = new BufferedImage[3];
		playerLeft[0] = Game.spritesheet.getSprite(0, 9 * 32, width, height);
		playerLeft[1] = Game.spritesheet.getSprite(1 * 32, 9 * 32, width, height);
		playerLeft[2] = Game.spritesheet.getSprite(2 * 32, 9 * 32, width, height);

		playerUp = new BufferedImage[3];
		playerUp[0] = Game.spritesheet.getSprite(0, 11 * 32, width, height);
		playerUp[1] = Game.spritesheet.getSprite(1 * 32, 11 * 32, width, height);
		playerUp[2] = Game.spritesheet.getSprite(2 * 32, 11 * 32, width, height);

		playerDown = new BufferedImage[3];
		playerDown[0] = Game.spritesheet.getSprite(0, 8 * 32, width, height);
		playerDown[1] = Game.spritesheet.getSprite(1 * 32, 8 * 32, width, height);
		playerDown[2] = Game.spritesheet.getSprite(2 * 32, 8 * 32, width, height);
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
