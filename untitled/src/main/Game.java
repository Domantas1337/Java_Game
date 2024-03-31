// Domantas Kakčiukas


package main;


import javax.swing.*;

import helpClass.Constants;
import managers.TileManager;
import scenes.Editing;
import scenes.Menu;
import scenes.Playing;

public class Game extends JFrame implements Runnable {

	private GameScreen gameScreen;
	private Thread gameThread;

	private final double FPS_SET = 120.0;
	private final double UPS_SET = 60.0;

	public static final int GAME_WIDTH = 1600 + 640;
	public static final int GAME_HEIGHT = 640;

	private Render render;
	private Menu menu;
	private Playing playing;
	private Editing editing;
	private GameStates currentLevelState = GameStates.LEVEL1;
	private TileManager tileManager;

	public Game() {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		initClasses();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Your Game");
		add(gameScreen);
		pack();
		setVisible(true);
	}

	private void initClasses() {
		tileManager = new TileManager();
		render = new Render(this);
		gameScreen = new GameScreen(this);
		menu = new Menu(this);
		playing = new Playing(this);
		playing.setDefaultTower(Constants.levelFiles.getFile());
		editing = new Editing(this, GameStates.getGameStateEdit());
	}

	private void start() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	private void updateGame() {
		switch (GameStates.gameState) {
		case EDIT:
			editing.update();
			break;
		case PLAYING:
			if( GameStates.levelState != currentLevelState){
				playing.setDefaultTower(Constants.levelFiles.getFile());
				currentLevelState = GameStates.levelState;
			}
			playing.update();
			break;
		default:
			break;
		}
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.gameScreen.initInputs();
		game.start();
	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();

		int frames = 0;
		int updates = 0;

		long now;

		while (true) {
			now = System.nanoTime();

			if (now - lastFrame >= timePerFrame) {
				repaint();
				lastFrame = now;
				frames++;
			}

			if (now - lastUpdate >= timePerUpdate) {
				updateGame();
				lastUpdate = now;
				updates++;
			}

		}

	}

	public Render getRender() {
		return render;
	}

	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}

	public Editing getEditor() {
		return editing;
	}

	public TileManager getTileManager() {
		return tileManager;
	}
}