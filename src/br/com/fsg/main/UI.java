package br.com.fsg.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(4, 4, 101, 11);

		g.setColor(Color.RED);
		g.fillRect(5, 5, Game.player.maxLife, 10);

		g.setColor(Color.GREEN);
		g.fillRect(5, 5, (Game.player.life * 100) / Game.player.maxLife, 10);

		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 9));
		g.drawString(Game.player.life + "/" + Game.player.maxLife, 40, 14);
	}
}
