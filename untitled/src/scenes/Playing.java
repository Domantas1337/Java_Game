package scenes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import PlayerPack.InitPlayer;
import helpClass.Constants;
import helpClass.LoadSave;
import main.Camera;
import main.Game;
import main.GameStates;
import managers.ProjectileManager;
import managers.TowerManager;
import objects.Tower;
import ui.ActionBar;

public class Playing extends GameScene implements SceneMethods {

	private int[][] lvl;

	private ActionBar actionBar;
	private int mouseX, mouseY;

	private TowerManager towerManager;
	private ProjectileManager projManager;

	private boolean gamePaused;
	private boolean gameFinished = false;
	private InitPlayer initPlayer;
	public static int levelShift;
	private GameStates currentState;
	private Camera camera;

	public Playing(Game game) {
		super(game);
		camera = new Camera(0, 0);
		currentState = GameStates.levelState;
		startLevel(GameStates.levelState);
	}

	public void setDefaultTower(File file){
		int ID[][] = LoadSave.GetLevelData(Constants.levelFiles.getFile());
		towerManager = new TowerManager(this, initPlayer);

		for (int j = 0; j < Game.GAME_WIDTH / 32; j++){
			for (int i = 0; i < 20; i++) {
				if(ID[i][j] == 4){
					System.out.println(5);
					towerManager.addTower(new Tower(0, 0, -1, Constants.Towers.CANNON), j*32, i*32);
				}
				if(ID[i][j] == 5 ){
					System.out.println(6);
					towerManager.addTower(new Tower(0, 0, -1, Constants.Towers.ARCHER), j*32, i*32);
				}
			}
		}

	}

	public void startLevel(GameStates states){

		if(states != currentState || states == GameStates.LEVEL1) {
			loadLevel(Constants.levelFiles.getFile());
			initPlayer = new InitPlayer(this, (float)24, (float)24,  LoadSave.GetLevelData(Constants.levelFiles.getFile()), GameStates.levelState);
			currentState = states;
			camera.setX(0);
			camera.setY(0);
			setDefaultTower(Constants.levelFiles.getFile());
		}

		actionBar = new ActionBar(0, 640, 640, 160, this);
		towerManager = new TowerManager(this, initPlayer);
		projManager = new ProjectileManager(this, initPlayer);
	}

	public InitPlayer getInitPlayer(){
		return initPlayer;
	}

	private void loadLevel(File file) {
		lvl = LoadSave.GetLevelData(file);
	}

	public void update() {
		if (!gamePaused && GameStates.gameState == GameStates.PLAYING) {

			if(initPlayer != null)
				camera.update(initPlayer);
			if(initPlayer.isAlive() == false) {
				camera.setX(0);
				camera.setY(0);
				return;
			}

			towerManager.update();
			projManager.update();
			initPlayer.update();

			if(isFinished()){
				initPlayer.playerSpeed = 0f;
			}
		}

	}

	@Override
	public void render(Graphics g) {
		Graphics2D graphics2D = (Graphics2D) g;

		graphics2D.translate(camera.getX(), camera.getY());
		drawLevel(g);
		towerManager.draw(g);
		projManager.draw(g);
		g.drawImage(initPlayer.getAnimations(), initPlayer.hitboxX(), initPlayer.hitboxY(), Constants.forPlayer.WIDTH, Constants.forPlayer.HEIGHT, null);
		drawHighlight(g);
		graphics2D.translate(-camera.getX(), -camera.getY());

		actionBar.draw(g);
	}

	private void drawHighlight(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(mouseX, mouseY, 32, 32);
	}


	private void drawLevel(Graphics g) {
		BufferedImage images[] = TowerManager.getTowerImgs();

		for (int y = 0; y < 20; y++) {
			for (int x = 0; x < Game.GAME_WIDTH / 32; x++) {
				int id = lvl[y][x];
				if(id == 3){
					g.drawImage(getSprite(1), x*32, y*32, null);
				}
				g.drawImage(getSprite(id), x * 32, y * 32, null);

				if( id == 4 || id == 5){
					g.drawImage(getSprite(1), x*32, y*32, null);
				}
			}
		}
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (y >= 640) {
			actionBar.mouseClicked(x, y);
		}
	}

	public void shootEnemy(Tower t) {
		projManager.newProjectile(t);
	}

	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}

	public void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}

	public boolean isFinished(){
		return  gameFinished;
	}

	@Override
	public void mouseMoved(int x, int y) {
	}

	@Override
	public void mousePressed(int x, int y) {
		if (y >= 640)
			actionBar.mousePressed(x, y);
	}

	@Override
	public void mouseReleased(int x, int y) {
		actionBar.mouseReleased(x, y);
	}

	@Override
	public void mouseDragged(int x, int y) {
	}

	public boolean isGamePaused() {
		return gamePaused;
	}

	public void removeOneLife(int DMG) {
		actionBar.removeOneLife(DMG);
	}

}