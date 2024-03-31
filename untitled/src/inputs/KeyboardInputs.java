package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.Game;
import scenes.Editing;
import scenes.Playing;

public class KeyboardInputs implements KeyListener {

	private Game game;
	private Playing playing;
	private Editing editing;
	public KeyboardInputs(Game game, Playing playing, Editing editing) {
		this.game = game;
		this.playing = playing;
		this.editing = editing;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				playing.getInitPlayer().setLeft(false);
				break;
			case KeyEvent.VK_D:
				playing.getInitPlayer().setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				playing.getInitPlayer().setJump(false);
				break;
			case KeyEvent.VK_R:
				editing.change();
				break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			playing.getInitPlayer().setLeft(true);
			break;
		case KeyEvent.VK_D:
			playing.getInitPlayer().setRight(true);
			break;
		case KeyEvent.VK_SPACE:
			playing.getInitPlayer().setJump(true);
			break;
		}
	}
}