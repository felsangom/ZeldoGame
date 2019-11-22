package br.com.fsg.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class UI {

	private String pause = "Pausa";
	private String resume = "Pressione ESC para voltar ao jogo";
	private String gameOver = "Game Over";
	private String startOver = "Pressione ENTER para reiniciar";
	private Font lifeFont = new Font("arial", Font.BOLD, 9);
	private Font ammoFont = new Font("arial", Font.BOLD, 10);
	private Font overlayTitleFont = new Font("arial", Font.BOLD, 25);
	private Font overlaySubtitleFont = new Font("arial", Font.BOLD, 15);
	private Color gameOverBackgroundColor = new Color(0, 0, 0, 100);
	
	public void render(Graphics g) {
		if (Game.STATE == Game.NORMAL_STATE) {
			renderGameUI(g);
		} else if (Game.STATE == Game.PAUSE_STATE) {
			renderGameUI(g);
			renderGamePause(g);
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

	public void renderGamePause(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(gameOverBackgroundColor);
		g2.fillRect(0, 0, Game.WINDOW_WIDTH * Game.SCALE, Game.WINDOW_HEIGHT * Game.SCALE);

		g.setFont(overlayTitleFont);
		g.setColor(Color.WHITE);
		FontMetrics metrics = g.getFontMetrics(overlayTitleFont);
		int pauseWidth = metrics.stringWidth(pause);
		int pauseHeight = metrics.getHeight();
		g.drawString(pause, (Game.WINDOW_WIDTH / 2) - (pauseWidth / 2), (Game.WINDOW_HEIGHT / 2) - (pauseHeight / 2));

		g.setFont(overlaySubtitleFont);
		metrics = g.getFontMetrics(overlaySubtitleFont);
		pauseWidth = metrics.stringWidth(resume);
		g.drawString(resume, (Game.WINDOW_WIDTH / 2) - (pauseWidth / 2), (Game.WINDOW_HEIGHT / 2) + 10);
	}
	
	public void renderGameOver(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(gameOverBackgroundColor);
		g2.fillRect(0, 0, Game.WINDOW_WIDTH * Game.SCALE, Game.WINDOW_HEIGHT * Game.SCALE);

		g.setFont(overlayTitleFont);
		g.setColor(Color.WHITE);
		FontMetrics metrics = g.getFontMetrics(overlayTitleFont);
		int gameOverWidth = metrics.stringWidth(gameOver);
		int gameOverHeight = metrics.getHeight();
		g.drawString(gameOver, (Game.WINDOW_WIDTH / 2) - (gameOverWidth / 2), (Game.WINDOW_HEIGHT / 2) - (gameOverHeight / 2));

		g.setFont(overlaySubtitleFont);
		metrics = g.getFontMetrics(overlaySubtitleFont);
		gameOverWidth = metrics.stringWidth(startOver);
		g.drawString(startOver, (Game.WINDOW_WIDTH / 2) - (gameOverWidth / 2), (Game.WINDOW_HEIGHT / 2) + 10);
	}
}
