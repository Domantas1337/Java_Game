package main;

import java.awt.*;
import javax.swing.*;

import inputs.KeyboardInputs;
import inputs.MyMouseListener;

public class GameScreen extends JPanel {

	private Game game;
	private Dimension size;

	private MyMouseListener myMouseListener;
	private KeyboardInputs keyboardInputs;

	public GameScreen(Game game) {
		this.game = game;
		setPanelSize();
	}

	public void initInputs() {
		myMouseListener = new MyMouseListener(game);
		keyboardInputs = new KeyboardInputs(game, game.getPlaying(), game.getEditor());
		addMouseListener(myMouseListener);
		addMouseMotionListener(myMouseListener);
		addKeyListener(keyboardInputs);
		requestFocus();
	}

	private void setPanelSize() {
		size = new Dimension(640, 800);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.getRender().render(g);
	}

	public Game getGame() {
		return game;
	}
}