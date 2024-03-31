package objects;

import java.awt.image.BufferedImage;

public class Tile {

	private BufferedImage[] sprite;
	private int id, tileType;

	public Tile(BufferedImage sprite, int id, int tileType) {
		this.sprite = new BufferedImage[1];
		this.sprite[0] = sprite;
		this.id = id;
		this.tileType = tileType;
	}

	public BufferedImage getSprite() {
		return sprite[0];
	}

	public int getId() {
		return id;
	}

}

