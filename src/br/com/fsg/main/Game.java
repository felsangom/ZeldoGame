package br.com.fsg.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import br.com.fsg.entidades.ArrowShot;
import br.com.fsg.entidades.Entity;
import br.com.fsg.entidades.Player;
import br.com.fsg.graficos.Spritesheet;
import br.com.fsg.world.Tile;
import br.com.fsg.world.World;

public class Game extends Canvas implements Runnable, KeyListener {

	private static final long serialVersionUID = 3475332481195704314L;

	public static int NORMAL_STATE = 1;
	public static int GAME_OVER_STATE = 2;
	public static int STATE;
	
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = false;
	public static Random random = new Random();
	public static final int WINDOW_WIDTH = 320;
	public static final int WINDOW_HEIGHT = 240;
	public static final int SCALE = 3;
	public static int MAX_HORIZONTAL_TILES = WINDOW_WIDTH / 32;
	public static int MAX_VERTICAL_TILES = WINDOW_HEIGHT / 32;
	public static int currentLevel = 1;
	public static final int MAX_LEVEL = 2;

	private BufferedImage image = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);

	public static Spritesheet spritesheet;
	public static List<Entity> entities;
	public static List<Entity> entitiesToRemove;
	public static List<ArrowShot> arrowsShot;
	public static int enemiesLeft;

	public static UI ui;
	public static Player player;
	public static World world;
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public Game() {
		addKeyListener(this);
		this.setPreferredSize(new Dimension(WINDOW_WIDTH * SCALE, WINDOW_HEIGHT * SCALE));
		initFrame();
		initGame(currentLevel);
	}

	public static void initGame(int level) {
		STATE = NORMAL_STATE;

		entities = new ArrayList<Entity>();
		arrowsShot = new ArrayList<ArrowShot>();
		entitiesToRemove = new ArrayList<Entity>();
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0, 0, spritesheet.getSprite(0, 8 * Tile.HEIGHT, Tile.WIDTH, Tile.HEIGHT));

		ui = new UI();
		world = new World("/level" + currentLevel + ".png");

		entities.add(player);
	}
	
	public void initFrame() {
		frame = new JFrame("Game");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}

	public synchronized void stop() {
		isRunning = false;

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		if (STATE == NORMAL_STATE) {
			for (Entity entidade : entities) {
				entidade.tick();
			}

			for (ArrowShot arrow : arrowsShot) {
				 arrow.tick();
			}

			for (Entity entity : entitiesToRemove) {
				if (entity instanceof ArrowShot) {
					arrowsShot.remove(entity);
				} else {
					entities.remove(entity);				
				}
			}

			Game.entitiesToRemove.clear();

			if (enemiesLeft == 0) {
				currentLevel += 1;
				if (currentLevel > MAX_LEVEL) {
					currentLevel = 1;
				}

				initGame(currentLevel);
			}
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = image.getGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

		world.render(g);
		
		// Renderização do jogo
		for (Entity entidade : entities) {
			entidade.render(g);
		}

		for (ArrowShot arrow : arrowsShot) {
			arrow.render(g);
		}
		
		ui.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WINDOW_WIDTH * SCALE, WINDOW_HEIGHT * SCALE, null);
		bs.show();
	}
	
	@Override
	public void run() {
		requestFocus();

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();

		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}

			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}

		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {;
			player.down = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_X) {
			player.shoot();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {;
			player.down = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
			player.left = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.right = false;
			player.left = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			player.down = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.up = false;
			player.down = true;
		}

		if (e.getKeyCode() == KeyEvent.VK_X) {
			player.shoot();
		}
	}
}
