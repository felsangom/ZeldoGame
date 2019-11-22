package br.com.fsg.entidades;

import java.awt.image.BufferedImage;

import br.com.fsg.main.Game;
import br.com.fsg.world.World;

public class ArrowShot extends Entity {

	private double speed = 4;
	private boolean right = false;
	private boolean left = false;
	private boolean up = false;
	private boolean down = false;

	public ArrowShot(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);

		if (sprite == Entity.ARROW_RIGHT) {
			right = true;
		} else if (sprite == Entity.ARROW_LEFT) {
			left = true;
		} else if (sprite == Entity.ARROW_UP) {
			up = true;
		} else if (sprite == Entity.ARROW_DOWN) {
			down = true;
		}
	}

	public void tick() {
		if (right && World.isFree((int) (this.getX() + speed), this.getY())) {
			x += speed;
		} else if (left && World.isFree((int) (this.getX() - speed), this.getY())) {
			x -= speed;
		} else if (up && World.isFree(this.getX(), (int) (this.getY() - speed))) {
			y -= speed;
		} else if (down && World.isFree(this.getX(), (int) (this.getY() + speed))) {
			y += speed;
		} else {
			Game.entitiesToRemove.add(this);
			return;
		}

		for (Entity entity : Game.entities) {
			if (entity instanceof Enemy) {
				Enemy enemy = (Enemy) entity;
				if (this.collidingWith(entity)) {
					enemy.life -= 50;
					enemy.tookDamage = true;
					Game.entitiesToRemove.add(this);
					return;
				}
			}
		}
		
		super.tick();
	}
}
