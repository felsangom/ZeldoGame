package br.com.fsg.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class World {

	private Tile[] tiles;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

			tiles = new Tile[pixels.length];

			int x, y = 0;
			int maxX = (int) Math.sqrt(pixels.length);

			for (int indice = 0; indice < pixels.length; indice++) {
				x = indice % maxX;
				if (indice > 0 && indice % maxX == 0)
					y ++; 

				int pixel = pixels[indice];
				
				switch (pixel) {
					// Pixel branco = parede
					case 0xFFFFFFFF:
						tiles[indice] = new WallTile(x, y, Tile.wall());
						break;
					// Pixel preto = grama
					case 0xFF000000:
						tiles[indice] = new WallTile(x, y, Tile.floor());
						break;
					// Pixel vermelho
					case 0xFFFF0000:
						tiles[indice] = new WallTile(x, y, Tile.floor());
						break;
					// Pixel ciano
					case 0xFF00E0FF:
						tiles[indice] = new WallTile(x, y, Tile.floor());
						break;
					// Pixel verde = Player
					case 0xFF17FF00:
						tiles[indice] = new WallTile(x, y, Tile.floor());
						break;
					// Pixel rosa
					case 0xFFE800FF:
						tiles[indice] = new WallTile(x, y, Tile.floor());
						break;
					// Pixel amarelo
					case 0xFFFFF700:
						tiles[indice] = new WallTile(x, y, Tile.floor());
						break;
					// Chão
					default:
						tiles[indice] = new WallTile(x, y, Tile.floor());
						break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public void render(Graphics g) {
		for (int indice = 0; indice < tiles.length; indice++) {
			Tile tile = tiles[indice];
			tile.render(g);
		}
	}
}
