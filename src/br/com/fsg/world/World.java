package br.com.fsg.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import br.com.fsg.entidades.Ammo;
import br.com.fsg.entidades.Enemy;
import br.com.fsg.entidades.Entity;
import br.com.fsg.entidades.Potion;
import br.com.fsg.entidades.Weapon;
import br.com.fsg.main.Game;

public class World {

	private static Tile[] tiles;
	public static int totalMapWidth;
	public static int totalMapHeight;
	public static int mapPixelsWidth;
	public static int mapPixelsHeight;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			mapPixelsWidth = map.getWidth();
			mapPixelsHeight = map.getHeight();

			int[] pixels = new int[mapPixelsWidth * mapPixelsHeight];
			map.getRGB(0, 0, mapPixelsWidth, mapPixelsHeight, pixels, 0, mapPixelsWidth);


			totalMapWidth = mapPixelsWidth * Tile.WIDTH;
			totalMapHeight = mapPixelsHeight * Tile.HEIGHT;
			
			tiles = new Tile[pixels.length];

			Game.enemiesLeft = 0;
			
			int x, y = 0;
			int maxX = (int) Math.sqrt(pixels.length);

			for (int indice = 0; indice < pixels.length; indice++) {
				x = indice % maxX;
				if (indice > 0 && indice % maxX == 0)
					y ++; 

				tiles[indice] = new FloorTile(x, y, Tile.floor());
				int pixel = pixels[indice];
				
				switch (pixel) {
					// Pixel branco = parede
					case 0xFFFFFFFF:
						tiles[indice] = new WallTile(x, y, Tile.wall());
						break;
					// Pixel preto = grama
					case 0xFF000000:
						tiles[indice] = new FloorTile(x, y, Tile.floor());
						break;
					// Pixel vermelho = inimigo
					case 0xFFFF0000:
						Game.entities.add(new Enemy(x * Tile.WIDTH, y * Tile.HEIGHT, Entity.ENEMY_SPRITE));
						Game.enemiesLeft++;
						break;
					// Pixel ciano = munição
					case 0xFF00E0FF:
						Game.entities.add(new Ammo(x * Tile.WIDTH, y * Tile.HEIGHT, Entity.AMMO_SPRITE));
						break;
					// Pixel verde = Player
					case 0xFF17FF00:
						Game.player.setPosition(x * Tile.WIDTH, y * Tile.HEIGHT);
						break;
					// Pixel rosa = vida
					case 0xFFE800FF:
						Game.entities.add(new Potion(x * Tile.WIDTH, y * Tile.HEIGHT, Entity.POTION_SPRITE));
						break;
					// Pixel amarelo = arma
					case 0xFFFFF700:
						Game.entities.add(new Weapon(x * Tile.WIDTH, y * Tile.HEIGHT, Entity.WEAPON_SPRITE));
						break;
					// Chão
					default:
						tiles[indice] = new FloorTile(x, y, Tile.floor());
						break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void render(Graphics g) {
		int x, y = 0;
		int maxX = (int) Math.sqrt(tiles.length);

		int startX = Camera.x / Tile.WIDTH;
		int startY = Camera.y / Tile.HEIGHT;
		int endX = startX + Game.MAX_HORIZONTAL_TILES + 1;
		int endY = startY + Game.MAX_VERTICAL_TILES + 1;
	
		for (int indice = 0; indice < tiles.length; indice++) {
			x = indice % maxX;
			if (indice > 0 && indice % maxX == 0)
				y++;

			if (x >= startX && x <= endX && y >= startY && y <= endY) {
				Tile tile = tiles[indice];
				tile.render(g);
			}
		}
	}

	public static boolean isFree(int nextX, int nextY) {
		int x1 = nextX / Tile.WIDTH;
		int y1 = nextY / Tile.HEIGHT;

		int x2 = (nextX + Tile.WIDTH - 1) / Tile.WIDTH;
		int y2 = nextY / Tile.HEIGHT;

		int x3 = nextX / Tile.WIDTH;
		int y3 = (nextY + Tile.HEIGHT - 1) / Tile.HEIGHT;

		int x4 = (nextX + Tile.WIDTH - 1) / Tile.WIDTH;
		int y4 = (nextY + Tile.HEIGHT - 1) / Tile.HEIGHT;

		return !(
			(tiles[x1 + (y1 * mapPixelsWidth)] instanceof WallTile) ||
			(tiles[x2 + (y2 * mapPixelsWidth)] instanceof WallTile) ||
			(tiles[x3 + (y3 * mapPixelsWidth)] instanceof WallTile) ||
			(tiles[x4 + (y4 * mapPixelsWidth)] instanceof WallTile)
		);
	}
}
