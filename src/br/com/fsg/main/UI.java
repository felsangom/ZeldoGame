package br.com.fsg.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

public class UI {

	private String gameName = "Zeldo";

	public static String[] menuItems = { "Novo jogo", "Sair" };
	public static int currentSelection = Game.NEW_GAME;

	private String pause = "Pausa";
	private String resume = "Pressione ESC para voltar ao jogo";
	private String quit = "Pressione Q para voltar ao menu";
	private String gameOver = "Game Over";
	private String startOver = "Pressione ENTER para reiniciar";

	private Font menuItemFont = new Font("arial", Font.BOLD, 15);
	private Font lifeFont = new Font("arial", Font.BOLD, 9);
	private Font ammoFont = new Font("arial", Font.BOLD, 10);
	private Font overlayTitleFont = new Font("arial", Font.BOLD, 25);
	private Font overlaySubtitleFont = new Font("arial", Font.BOLD, 15);

	private Color gameOverBackgroundColor = new Color(0, 0, 0, 100);

	private Font gameNameFont;

	public void render(Graphics g) {
		if (gameNameFont == null) {
			try {
				gameNameFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/zelda.otf"));
				gameNameFont = gameNameFont.deriveFont(Font.BOLD, 50);
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (Game.STATE == Game.MENU_STATE) {
			renderGameMenu(g);
		} else if (Game.STATE == Game.NORMAL_STATE) {
			renderGameUI(g);
		} else if (Game.STATE == Game.PAUSE_STATE) {
			renderGameUI(g);
			renderGamePause(g);
		} else if (Game.STATE == Game.GAME_OVER_STATE) {
			renderGameUI(g);
			renderGameOver(g);
		}
	}

	public void renderGameMenu(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WINDOW_WIDTH * Game.SCALE, Game.WINDOW_HEIGHT * Game.SCALE);

		g.setFont(gameNameFont);
		FontMetrics metrics = g.getFontMetrics(gameNameFont);
		int gameNameWidth = metrics.stringWidth(gameName);
		g.setColor(Color.MAGENTA);
		g.drawString(gameName, (Game.WINDOW_WIDTH / 2) - (gameNameWidth / 2) + 1, 51);
		g.setColor(Color.WHITE);
		g.drawString(gameName, (Game.WINDOW_WIDTH / 2) - (gameNameWidth / 2), 50);


		int menuItemStartPosition = 100;
		g.setFont(menuItemFont);

		for (int index = 0; index < menuItems.length; index++) {
			String menuItem = menuItems[index];
			metrics = g.getFontMetrics(menuItemFont);
			int menuItemWidth = metrics.stringWidth(menuItem);
			int xPosition = (Game.WINDOW_WIDTH / 2) - (menuItemWidth / 2);

			g.setColor(Color.WHITE);
			g.drawString(menuItem, xPosition, menuItemStartPosition);

			if (currentSelection == index) {
				g.setColor(Color.RED);
				g.drawRect(xPosition - 5, menuItemStartPosition - 15, menuItemWidth + 10, 20);
			}

			menuItemStartPosition += 25;
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
		FontMetrics metrics = g.getFontMetrics(overlayTitleFont);
		int pauseWidth = metrics.stringWidth(pause);
		int pauseHeight = metrics.getHeight();

		g.setColor(Color.BLACK);
		g.drawString(pause, (Game.WINDOW_WIDTH / 2) - (pauseWidth / 2) + 1, (Game.WINDOW_HEIGHT / 2) - (pauseHeight / 2) + 1);
		g.setColor(Color.WHITE);
		g.drawString(pause, (Game.WINDOW_WIDTH / 2) - (pauseWidth / 2), (Game.WINDOW_HEIGHT / 2) - (pauseHeight / 2));

		g.setFont(overlaySubtitleFont);
		metrics = g.getFontMetrics(overlaySubtitleFont);
		pauseWidth = metrics.stringWidth(resume);

		g.setColor(Color.BLACK);
		g.drawString(resume, (Game.WINDOW_WIDTH / 2) - (pauseWidth / 2) + 1, (Game.WINDOW_HEIGHT / 2) + 11);
		g.setColor(Color.WHITE);
		g.drawString(resume, (Game.WINDOW_WIDTH / 2) - (pauseWidth / 2), (Game.WINDOW_HEIGHT / 2) + 10);

		int quitWidth = metrics.stringWidth(quit);

		g.setColor(Color.BLACK);
		g.drawString(quit, (Game.WINDOW_WIDTH / 2) - (quitWidth / 2) + 1, (Game.WINDOW_HEIGHT / 2) + 31);
		g.setColor(Color.WHITE);
		g.drawString(quit, (Game.WINDOW_WIDTH / 2) - (quitWidth / 2), (Game.WINDOW_HEIGHT / 2) + 30);
	}
	
	public void renderGameOver(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(gameOverBackgroundColor);
		g2.fillRect(0, 0, Game.WINDOW_WIDTH * Game.SCALE, Game.WINDOW_HEIGHT * Game.SCALE);

		g.setFont(overlayTitleFont);
		FontMetrics metrics = g.getFontMetrics(overlayTitleFont);
		int gameOverWidth = metrics.stringWidth(gameOver);
		int gameOverHeight = metrics.getHeight();

		g.setColor(Color.BLACK);
		g.drawString(gameOver, (Game.WINDOW_WIDTH / 2) - (gameOverWidth / 2) + 1, (Game.WINDOW_HEIGHT / 2) - (gameOverHeight / 2) + 1);
		g.setColor(Color.WHITE);
		g.drawString(gameOver, (Game.WINDOW_WIDTH / 2) - (gameOverWidth / 2), (Game.WINDOW_HEIGHT / 2) - (gameOverHeight / 2));

		g.setFont(overlaySubtitleFont);
		metrics = g.getFontMetrics(overlaySubtitleFont);
		gameOverWidth = metrics.stringWidth(startOver);
		
		g.setColor(Color.BLACK);
		g.drawString(startOver, (Game.WINDOW_WIDTH / 2) - (gameOverWidth / 2) + 1, (Game.WINDOW_HEIGHT / 2) + 11);
		g.setColor(Color.WHITE);
		g.drawString(startOver, (Game.WINDOW_WIDTH / 2) - (gameOverWidth / 2), (Game.WINDOW_HEIGHT / 2) + 10);

		int quitWidth = metrics.stringWidth(quit);

		g.setColor(Color.BLACK);
		g.drawString(quit, (Game.WINDOW_WIDTH / 2) - (quitWidth / 2) + 1, (Game.WINDOW_HEIGHT / 2) + 31);
		g.setColor(Color.WHITE);
		g.drawString(quit, (Game.WINDOW_WIDTH / 2) - (quitWidth / 2), (Game.WINDOW_HEIGHT / 2) + 30);
	}
}
