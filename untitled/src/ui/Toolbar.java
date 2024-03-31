package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import objects.Tile;
import scenes.Editing;

import static main.GameStates.*;

public class Toolbar extends Bar {
	private Editing editing;
	private MyButton bMenu,bLevel1, bLevel2, bLevel3, bSave;
	private MyButton bSideAttack, bFrontAttack;
	private Tile selectedTile;

	private Map<MyButton, ArrayList<Tile>> map = new HashMap<MyButton, ArrayList<Tile>>();

	private MyButton bGrass, bWater, bRoad, bFinish, bWaterB, bWaterI;
	private MyButton currentButton;
	private int currentIndex = 0;

	public Toolbar(int x, int y, int width, int height, Editing editing) {
		super(x, y, width, height);
		this.editing = editing;
		initButtons();
	}

	private void initButtons() {
		bMenu = new MyButton("Menu", 2, 640, 100, 20);
		bLevel1 = new MyButton("Level 1", 2, 670, 100, 20);
		bLevel2 = new MyButton("Level 2", 2, 700, 100, 20);
		bLevel3 = new MyButton("Level 3", 2, 730, 100, 20);
		bSave = new MyButton("Save", 2, 760, 100, 20);

		int w = 50;
		int h = 50;
		int xStart = 110;
		int yStart = 650;
		int xOffset = (int) (w * 1.1f);
		int i = 0;

		bRoad = new MyButton("Road", xStart, yStart, w, h, i++);
		bGrass = new MyButton("Grass", xStart + xOffset, yStart, w, h, i++);
		bWater = new MyButton("Water", xStart + xOffset * 2, yStart, w, h, i++);
		bFinish = new  MyButton("Finish", xStart + xOffset * 3, yStart, w, h, i++);
		bSideAttack = new  MyButton("Side attack", xStart, yStart + xOffset, w, h, i++);
		bFrontAttack = new  MyButton("Front attack", xStart + xOffset, yStart + xOffset, w, h, i++);

	}

	private void saveLevel() {
		editing.saveLevel();
	}

	public void draw(Graphics g) {

		g.setColor(new Color(220, 123, 15));
		g.fillRect(x, y, width, height);

		drawButtons(g);
	}

	private void drawButtons(Graphics g) {
		bMenu.draw(g);
		bLevel1.draw(g);
		bLevel2.draw(g);
		bLevel3.draw(g);
		bSave.draw(g);

		drawNormalButton(g, bRoad);
		drawNormalButton(g, bGrass);
		drawNormalButton(g, bWater);
		drawNormalButton(g, bFinish);
		drawNormalButton(g, bSideAttack);
		drawNormalButton(g, bFrontAttack);

		drawSelectedTile(g);
		drawMapButtons(g);

	}

	private void drawNormalButton(Graphics g, MyButton b) {
		g.drawImage(getButtImg(b.getId()), b.x, b.y, b.width, b.height, null);
		drawButtonFeedback(g, b);
	}

	private void drawMapButtons(Graphics g) {
		for (Map.Entry<MyButton, ArrayList<Tile>> entry : map.entrySet()) {
			MyButton b = entry.getKey();
			BufferedImage img = entry.getValue().get(0).getSprite();

			g.drawImage(img, b.x, b.y, b.width, b.height, null);
			drawButtonFeedback(g, b);
		}
	}

	private void drawSelectedTile(Graphics g) {
		if (selectedTile != null) {
			g.drawImage(selectedTile.getSprite(), 550, 650, 50, 50, null);
			g.setColor(Color.black);
			g.drawRect(550, 650, 50, 50);
		}
	}

	public BufferedImage getButtImg(int id) {
		return editing.getGame().getTileManager().getSprite(id);
	}

	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			SetGameState(MENU);
		else if (bLevel1.getBounds().contains(x, y)) {
			SetGameStateEdit(LEVEL1);
			editing.setGameStates(LEVEL1);
		}
		else if (bLevel2.getBounds().contains(x, y)) {
			SetGameStateEdit(LEVEL2);
			editing.setGameStates(LEVEL2);
		}
		else if (bLevel3.getBounds().contains(x, y)) {
			SetGameStateEdit(LEVEL3);
			editing.setGameStates(LEVEL3);
		}
		else if (bSave.getBounds().contains(x, y))
			saveLevel();
		else if (bWater.getBounds().contains(x, y)) {
			selectedTile = editing.getGame().getTileManager().getTile(bWater.getId());
			editing.setSelectedTile(selectedTile);
			return;
		} else if (bGrass.getBounds().contains(x, y)) {
			selectedTile = editing.getGame().getTileManager().getTile(bGrass.getId());
			editing.setSelectedTile(selectedTile);
			return;
		}else if (bRoad.getBounds().contains(x, y)) {
			selectedTile = editing.getGame().getTileManager().getTile(bRoad.getId());
			editing.setSelectedTile(selectedTile);
			return;
		}else if (bFinish.getBounds().contains(x, y)) {
			selectedTile = editing.getGame().getTileManager().getTile(bFinish.getId());
			editing.setSelectedTile(selectedTile);
			return;
		}else if (bSideAttack.getBounds().contains(x, y)) {
			selectedTile = editing.getGame().getTileManager().getTile(bSideAttack.getId());
			editing.setSelectedTile(selectedTile);
			return;
		}else if (bFrontAttack.getBounds().contains(x, y)) {
			selectedTile = editing.getGame().getTileManager().getTile(bFrontAttack.getId());
			editing.setSelectedTile(selectedTile);
			return;
		} else {
			for (MyButton b : map.keySet())
				if (b.getBounds().contains(x, y)) {
					selectedTile = map.get(b).get(0);
					editing.setSelectedTile(selectedTile);
					currentButton = b;
					currentIndex = 0;
					return;
				}
		}

	}

	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		bLevel1.setMouseOver(false);
		bLevel2.setMouseOver(false);
		bLevel3.setMouseOver(false);
		bSave.setMouseOver(false);
		bWater.setMouseOver(false);
		bGrass.setMouseOver(false);
		bFinish.setMouseOver(false);
		bRoad.setMouseOver(false);
		bFrontAttack.setMouseOver(false);
		bSideAttack.setMouseOver(false);

		for (MyButton b : map.keySet())
			b.setMouseOver(false);

		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
		else if (bSave.getBounds().contains(x, y))
			bSave.setMouseOver(true);
		else if (bLevel1.getBounds().contains(x, y))
			bLevel1.setMouseOver(true);
		else if (bLevel2.getBounds().contains(x, y))
			bLevel2.setMouseOver(true);
		else if (bLevel3.getBounds().contains(x, y))
			bLevel3.setMouseOver(true);
		else if (bWater.getBounds().contains(x, y))
			bWater.setMouseOver(true);
		else if (bGrass.getBounds().contains(x, y))
			bGrass.setMouseOver(true);
		else if (bRoad.getBounds().contains(x, y))
			bRoad.setMouseOver(true);
		else if (bFinish.getBounds().contains(x, y))
			bFinish.setMouseOver(true);
		else if (bSideAttack.getBounds().contains(x, y))
			bSideAttack.setMouseOver(true);
		else if (bFrontAttack.getBounds().contains(x, y))
			bFrontAttack.setMouseOver(true);
		else {
			for (MyButton b : map.keySet())
				if (b.getBounds().contains(x, y)) {
					b.setMouseOver(true);
					return;
				}
		}

	}

	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else if (bSave.getBounds().contains(x, y))
			bSave.setMousePressed(true);
		else if (bLevel1.getBounds().contains(x, y))
			bLevel1.setMousePressed(true);
		else if (bLevel2.getBounds().contains(x, y))
			bLevel2.setMousePressed(true);
		else if (bLevel3.getBounds().contains(x, y))
			bLevel3.setMousePressed(true);
		else if (bWater.getBounds().contains(x, y))
			bWater.setMousePressed(true);
		else if (bGrass.getBounds().contains(x, y))
			bGrass.setMousePressed(true);
		else if (bFinish.getBounds().contains(x, y))
			bFinish.setMousePressed(true);
		else if (bRoad.getBounds().contains(x, y))
			bRoad.setMousePressed(true);
		else if (bSideAttack.getBounds().contains(x, y))
			bSideAttack.setMousePressed(true);
		else if (bFrontAttack.getBounds().contains(x, y))
			bFrontAttack.setMousePressed(true);
		else {
			for (MyButton b : map.keySet())
				if (b.getBounds().contains(x, y)) {
					b.setMousePressed(true);
					return;
				}
		}
	}

	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();
		bSave.resetBooleans();
		bLevel1.resetBooleans();
		bLevel2.resetBooleans();
		bLevel3.resetBooleans();
		bGrass.resetBooleans();
		bFinish.resetBooleans();
		bWater.resetBooleans();
		bRoad.resetBooleans();
		bFrontAttack.resetBooleans();
		bSideAttack.resetBooleans();

		for (MyButton b : map.keySet())
			b.resetBooleans();

	}

}
