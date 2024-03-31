package scenes;

import java.awt.image.BufferedImage;

import main.Game;

public class GameScene {

	protected Game game;
	public GameScene(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	protected BufferedImage getSprite(int spriteID) {
		return game.getTileManager().getSprite(spriteID);
	}
}
