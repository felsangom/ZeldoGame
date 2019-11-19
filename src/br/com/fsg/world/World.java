package br.com.fsg.world;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class World {

	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

			for (int pixel : pixels) {
				switch (pixel) {
					// Pixel branco
					case 0xFFFFFFFF:
						System.out.println("branco");
						break;
					// Pixel preto
					case 0xFF000000:
						System.out.println("preto");
						break;
					// Pixel vermelho
					case 0xFFFF0000:
						System.out.println("vermelho");
						break;
					// Pixel ciano
					case 0xFF00E0FF:
						System.out.println("ciano");
						break;
					// Pixel verde
					case 0xFF17FF00:
						System.out.println("verde");
						break;
					// Pixel rosa
					case 0xFFE800FF:
						System.out.println("rosa");
						break;
					// Pixel amarelo
					case 0xFFFFF700:
						System.out.println("amarelo");
						break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
