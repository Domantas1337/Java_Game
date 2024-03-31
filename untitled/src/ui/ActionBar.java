package ui;

import static main.GameStates.*;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;

import helpClass.Constants;
import objects.Tower;
import scenes.Playing;

public class ActionBar extends Bar {

	private Playing playing;
	private MyButton bMenu, bPause;

	private MyButton[] towerButtons;

	private DecimalFormat formatter;

	private int deaths = 0;
	private int lives = Constants.forPlayer.HEALTH;

	public ActionBar(int x, int y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		formatter = new DecimalFormat("0.0");

		initButtons();
	}

	private void initButtons() {

		bMenu = new MyButton("Menu", 2, 642, 100, 30);
		bPause = new MyButton("Pause", 2, 682, 100, 30);

		towerButtons = new MyButton[3];

		int w = 50;
		int h = 50;
		int xStart = 110;
		int yStart = 650;
		int xOffset = (int) (w * 1.1f);

		for (int i = 0; i < towerButtons.length; i++)
			towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, w, h, i);

	}

	private void addDeaths(){
		deaths++;
	}

	public void removeOneLife(int DMG) {
		lives -= DMG;

		if(lives <= 0){
			addDeaths();
		}
	}

	private void drawButtons(Graphics g) {
		bMenu.draw(g);
		bPause.draw(g);
	}

	public void draw(Graphics g) {

		g.setColor(new Color(11 + 3, 137, 6+19));
		g.fillRect(x, y, width, height);

		drawButtons(g);

		if(playing.isFinished()){
			g.setColor(Color.black);;
			g.drawString("Finished!", 300, 690);
		}
		if (playing.isGamePaused()) {
			g.setColor(Color.black);
			g.drawString("Game is Paused!", 110, 790);
		}

		g.setColor(Color.black);
		g.drawString("Deaths: " + deaths, 110, 655);
	}

	private void togglePause() {
		playing.setGamePaused(!playing.isGamePaused());

		if (playing.isGamePaused())
			bPause.setText("Unpause");
		else
			bPause.setText("Pause");
	}

	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			SetGameState(MENU);
		else if (bPause.getBounds().contains(x, y))
			togglePause();
	}



	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else if (bPause.getBounds().contains(x, y))
			bPause.setMousePressed(true);
		else {

			for (MyButton b : towerButtons)
				if (b.getBounds().contains(x, y)) {
					b.setMousePressed(true);
					return;
				}
		}

	}

	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();
		bPause.resetBooleans();
		for (MyButton b : towerButtons)
			b.resetBooleans();
	}

}
