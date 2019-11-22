package br.com.fsg.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class UI {

	private String gameOver = "Game Over";
	private Font lifeFont = new Font("arial", Font.BOLD, 9);
	private Font ammoFont = new Font("arial", Font.BOLD, 10);
	private Font gameOverFont = new Font("arial", Font.BOLD, 25);
	private Color gameOverBackgroundColor = new Color(0, 0, 0, 100);
	
	public void render(Graphics g) {
		if (Game.STATE == Game.NORMAL_STATE) {
			renderGameUI(g);
		} else if (Game.STATE == Game.GAME_OVER_STATE) {
			renderGameUI(g);
			renderGameOver(g);
		}
	}

	public void renderGameUI(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(4, 4, 101, 11);

		g.setColor(Color.RED);
		g.fillRect(5, 5, Game.player.maxLife, 10);

		g.setColor(Color.GREEN);
		g.fillRect(5, 5, (Game.player.life * 100) / Game.player.maxLife, 10);

		g.setColor(Color.WHITE);
		g.setFont(lifeFont);
		g.drawString(Game.player.life + "/" + Game.player.maxLife, 40, 14);

		g.setFont(ammoFont);
		g.setColor(Color.BLACK);
		g.drawString("Munição: " + Game.player.ammo, 251, 14);
		g.setColor(Color.WHITE);
		g.drawString("Munição: " + Game.player.ammo, 250, 13);		
	}

	public void renderGameOver(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(gameOverBackgroundColor);
		g2.fillRect(0, 0, Game.WINDOW_WIDTH * Game.SCALE, Game.WINDOW_HEIGHT * Game.SCALE);

		g.setFont(gameOverFont);
		g.setColor(Color.WHITE);
		FontMetrics metrics = g.getFontMetrics(gameOverFont);
		Rectangle2D gameOverBounds = metrics.getStringBounds(gameOver, g);
		int gameOverWidth = (int) gameOverBounds.getWidth();
		int gameOverHeight = (int) gameOverBounds.getHeight();
		g.drawString(gameOver, (Game.WINDOW_WIDTH / 2) + (gameOverWidth / 2), (Game.WINDOW_HEIGHT / 2) - (gameOverHeight / 2));
	}
}
