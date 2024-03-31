package objects;

import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage sprite;
	private int id, tileType;

	public Tile(BufferedImage sprite, int id, int tileType) {
		this.sprite = sprite;
		this.id = id;
		this.tileType = tileType;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

	public int getId() {
		return id;
	}

}

