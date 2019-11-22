package br.com.fsg.entidades;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import br.com.fsg.main.Game;
import br.com.fsg.world.Camera;
import br.com.fsg.world.Tile;
import br.com.fsg.world.World;

public class Player extends Entity {

	public int maxLife = 100;
	public int life = maxLife;
	public int ammo = 0;
	public boolean hasWeapon = false;
	public boolean tookDamage = false;

	public int speed = 2;
	public boolean left, up, right, down = false;
	public boolean wasLeft = false;
	public boolean wasRight = false;
	public boolean wasUp = false;
	public boolean wasDown = false;

	private BufferedImage lastRendered;
	private BufferedImage lastRenderedWeapon;

	private int frames = 0;
	private int maxFrames = 7;
	private int blinkingFrames = 0;
	private int spriteIndex = 0;
	private int maxSpriteIndex = 2;
	private boolean increasingAnimation = true;

	private BufferedImage blink = new BufferedImage(Tile.WIDTH, Tile.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	private BufferedImage lastSpriteBeforeBlink;
	private BufferedImage lastWeaponBeforeBlink;
	private BufferedImage weaponUp = Game.spritesheet.getSprite(15 * Tile.WIDTH, 15 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	private BufferedImage weaponDown = Game.spritesheet.getSprite(12 * Tile.WIDTH, 15 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	private BufferedImage weaponRight = Game.spritesheet.getSprite(14 * Tile.WIDTH, 15 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	private BufferedImage weaponLeft = Game.spritesheet.getSprite(13 * Tile.WIDTH, 15 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT);
	private BufferedImage arrowSprite;
	
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
		blink.setRGB((int) x, (int) y, (new Color(0.0f, 0.0f, 0.0f, 0.0f)).getRGB());
	}

	public void shoot() {
		if (hasWeapon && ammo > 0) {
			int arrowX = getX();
			int arrowY = getY();

			if (wasRight) {
				arrowX = getX() + Tile.WIDTH / 2;
				arrowY = getY();
				arrowSprite = Entity.ARROW_RIGHT;
			} else if (wasLeft) {
				arrowX = getX() - Tile.WIDTH / 2;
				arrowY = getY();
				arrowSprite = Entity.ARROW_LEFT;
			}

			if (wasUp) {
				arrowX = getX();
				arrowY = getY() - Tile.HEIGHT / 2;
				arrowSprite = Entity.ARROW_UP;
			} else if (wasDown) {
				arrowX = getX();
				arrowY = getY() + Tile.HEIGHT / 2;
				arrowSprite = Entity.ARROW_DOWN;
			}
				

			Game.arrowsShot.add(new ArrowShot(arrowX, arrowY, arrowSprite));

			ammo--;
		}
	}
	
	public void tick() {
		if (right) {
			wasRight = true;
			wasLeft = false;
			wasUp = false;
			wasDown = false;

			if (World.isFree(this.getX() + speed, this.getY()))
				x += speed;
		}

		if (left) {
			wasRight = false;
			wasLeft = true;
			wasUp = false;
			wasDown = false;

			if (World.isFree(this.getX() - speed, this.getY()))
				x -= speed;
		}

		if (up) {
			wasRight = false;
			wasLeft = false;
			wasUp = true;
			wasDown = false;

			if (World.isFree(this.getX(), this.getY() - speed))
				y -= speed;
		}

		if (down) {
			wasRight = false;
			wasLeft = false;
			wasUp = false;
			wasDown = true;

			if (World.isFree(this.getX(), this.getY() + speed))
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

		if (tookDamage) {
			blinkingFrames++;
			if (blinkingFrames == 60) {
				blinkingFrames = 0;
				tookDamage = false;
			}
		}
		
		collectItem();
		checkGameOver();
		
		Camera.x = Camera.clamp(this.getX() - (Game.WINDOW_WIDTH / 2), 0, World.totalMapWidth - Game.WINDOW_WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.WINDOW_HEIGHT / 2), 0, World.totalMapHeight - Game.WINDOW_HEIGHT);

		super.tick();
	}

	private void collectItem() {
		for (Entity entity : Game.entities) {
			if (entity.collidingWith(Game.player)) {
				if (entity instanceof Potion) {
					if (Game.player.life < Game.player.maxLife) {
						if (Game.player.life + Potion.restore <= Game.player.maxLife) {
							Game.player.life += Potion.restore;
						} else {
							Game.player.life = Game.player.maxLife;
						}

						Game.entitiesToRemove.add(entity);
					}

				} else if (entity instanceof Ammo) {
					ammo += 2;
					Game.entitiesToRemove.add(entity);
				} else if (entity instanceof Weapon) {
					hasWeapon = true;
					Game.entitiesToRemove.add(entity);
				}
			}
		}
	}

	private void checkGameOver() {
		if (life <= 0) {
			Game.initGame();
		}
	}
	
	public void render(Graphics g) {
		if (tookDamage && blinkingFrames % 10 == 0) {
			lastSpriteBeforeBlink = lastRendered;
			lastWeaponBeforeBlink = lastRenderedWeapon;

			lastRendered = blink;
			lastRenderedWeapon = blink;
		} else {
			if (right) {
				lastRendered = playerRight[spriteIndex]; 
				lastRenderedWeapon = weaponRight;
			} else if (left) {
				lastRendered = playerLeft[spriteIndex];
				lastRenderedWeapon = weaponLeft;
			}

			if (up) {
				lastRendered = playerUp[spriteIndex];
				lastRenderedWeapon = weaponUp;
			} else if (down) {
				lastRendered = playerDown[spriteIndex];
				lastRenderedWeapon = weaponDown;
			}

			if (lastRendered == null || lastRendered == blink) {
				if (lastSpriteBeforeBlink != null && lastWeaponBeforeBlink != null) {
					lastRendered = lastSpriteBeforeBlink;
					lastRenderedWeapon = lastWeaponBeforeBlink;					
				} else {
					lastRendered = playerDown[0];
					lastRenderedWeapon = weaponDown;
				}
			}			
		}

		g.drawImage(lastRendered, this.getX() - Camera.x, this.getY() - Camera.y, null);
		if (hasWeapon) {
			g.drawImage(lastRenderedWeapon, this.getX() - Camera.x, this.getY() - Camera.y, null);			
		}
	}
}
