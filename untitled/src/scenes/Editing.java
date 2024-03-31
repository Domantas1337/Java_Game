package scenes;

import java.awt.Graphics;

import helpClass.Constants;
import helpClass.LoadSave;
import main.Game;
import main.GameStates;
import managers.TileManager;
import objects.Tile;
import ui.Toolbar;

public class
Editing extends GameScene implements SceneMethods {

	private int lvl[][];
	private Tile selectedTile;
	private int mouseX, mouseY;
	private int lastTileX, lastTileY, lastTileId;
	private boolean drawSelect;
	private Toolbar toolbar;
	public GameStates states;
	private TileManager tileManager;
	private int screenShift = 0;

	public Editing(Game game, GameStates states) {
		super(game);
		this.states = states;
		loadDefaultLevel();
		toolbar = new Toolbar(0, 640, 640, 160, this);
		tileManager = new TileManager();
	}

	public void setGameStates(GameStates state){
		states = GameStates.getGameStateEdit();
		loadDefaultLevel();
	}

	private void loadDefaultLevel() {
		if(states == GameStates.LEVEL1)
			lvl = LoadSave.GetLevelData(Constants.levelFiles.lvlFile);
		if(states == GameStates.LEVEL2)
			lvl = LoadSave.GetLevelData(Constants.levelFiles.lvlFile2);
		if(states == GameStates.LEVEL3)
			lvl = LoadSave.GetLevelData(Constants.levelFiles.lvlFile3);
	}

	public void update() {
	}

	public void change(){
		if(screenShift != Game.GAME_WIDTH / 32 - 20)
			screenShift++;
	}

	@Override
	public void render(Graphics g) {
		drawLevel(g);
		toolbar.draw(g);
		drawSelectedTile(g);
	}

	private void drawLevel(Graphics g) {
		for (int y = 0; y < 20; y++) {
			for (int x = screenShift; x < 20 + screenShift; x++) {
				int id = lvl[y][x];
				g.drawImage(getSprite(id), (x - screenShift) * 32, y * 32, null);
			}
		}
	}

	private void drawSelectedTile(Graphics g) {
		if (selectedTile != null && drawSelect) {
			g.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
		}
	}

	public void saveLevel() {
		if(states == GameStates.LEVEL1)
			LoadSave.SaveLevel(lvl, Constants.levelFiles.lvlFile);
		if(states == GameStates.LEVEL2)
			LoadSave.SaveLevel(lvl,Constants.levelFiles.lvlFile2);
		if(states == GameStates.LEVEL3)
			LoadSave.SaveLevel(lvl,Constants.levelFiles.lvlFile3);
	}

	public void setSelectedTile(Tile tile) {
		this.selectedTile = tile;
		drawSelect = true;
	}

	private void changeTile(int x, int y) {
		if (selectedTile != null) {
			int tileX = (x + screenShift * 32) / 32;
			int tileY = y / 32;

			if (selectedTile.getId() >= 0) {
				if (lastTileX == tileX && lastTileY == tileY && lastTileId == selectedTile.getId())
					return;

				lastTileX = tileX;
				lastTileY = tileY;
				lastTileId = selectedTile.getId();

				lvl[tileY][tileX] = selectedTile.getId();
			}
		}
	}

	@Override
	public void mouseClicked(int x, int y) {
		if (y >= 640) {
			toolbar.mouseClicked(x, y);
		} else {
			changeTile(mouseX, mouseY);
		}
	}

	@Override
	public void mouseMoved(int x, int y) {
		if (y >= 640) {
			toolbar.mouseMoved(x, y);
			drawSelect = false;
		} else {
			drawSelect = true;
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}
	}

	@Override
	public void mousePressed(int x, int y) {
		if (y >= 640) {
			toolbar.mousePressed(x, y);
		}
	}

	@Override
	public void mouseReleased(int x, int y) {
		toolbar.mouseReleased(x, y);
	}

	@Override
	public void mouseDragged(int x, int y) {
		if (y >= 640) {

		} else {
			changeTile(x, y);
		}
	}


}
