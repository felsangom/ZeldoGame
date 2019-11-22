package br.com.fsg.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UI {

	private Font lifeFont = new Font("arial", Font.BOLD, 9);
	private Font ammoFont = new Font("arial", Font.BOLD, 10);
	
	public void render(Graphics g) {
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
}
