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

	private Tile[] tiles;
	public static int totalMapWidth;
	public static int totalMapHeight;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

			totalMapWidth = map.getWidth() * Tile.WIDTH;
			totalMapHeight = map.getHeight() * Tile.HEIGHT;
			
			tiles = new Tile[pixels.length];

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
						Game.entities.add(new Enemy(x, y, Entity.ENEMY_SPRITE));
						break;
					// Pixel ciano = munição
					case 0xFF00E0FF:
						Game.entities.add(new Ammo(x, y, Entity.AMMO_SPRITE));
						break;
					// Pixel verde = Player
					case 0xFF17FF00:
						Game.player.setPosition(x * Tile.WIDTH, y * Tile.HEIGHT);
						break;
					// Pixel rosa = vida
					case 0xFFE800FF:
						Game.entities.add(new Potion(x, y, Entity.POTION_SPRITE));
						break;
					// Pixel amarelo = arma
					case 0xFFFFF700:
						Game.entities.add(new Weapon(x, y, Entity.WEAPON_SPRITE));
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
}
