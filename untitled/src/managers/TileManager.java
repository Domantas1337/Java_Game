package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import helpClass.Utilz;
import objects.Tile;
import static helpClass.Constants.Tiles.*;

public class TileManager {

	public Tile GRASS, WATER, ROAD, FINISH, ATTACK_SIDE, ATTACK_FRONT;

	private BufferedImage atlas, wallTexture, finishTexture;
	public ArrayList<Tile> tiles = new ArrayList<>();

	public TileManager() {
		atlas = Utilz.getBufferedImage("Grass.png");
		wallTexture = Utilz.getBufferedImage("Floor.png");
		finishTexture = Utilz.getBufferedImage("finish.png");
		createTiles();
	}

	private void createTiles() {

		int id = 0;

		BufferedImage atlas = Utilz.getBufferedImage("Danger.png");
		BufferedImage[] towerImgs = new BufferedImage[3];
		towerImgs[1] = atlas.getSubimage( 16, 32, 32, 32);
		towerImgs[0] = atlas.getSubimage(  96 - 16,128 + 16 + 16, 32, 32);

		tiles.add(ROAD = new Tile(getWallSprite(7, 5), id++, ROAD_TILE));
		tiles.add(GRASS = new Tile(getSprite(0, 0), id++, GRASS_TILE));
		tiles.add(WATER = new Tile(getWallSprite(7, 6), id++, WATER_TILE));
		tiles.add(FINISH = new Tile(getFinishSprite(0, 2), id++, FINISH_TILE));
		tiles.add(ATTACK_SIDE = new Tile(towerImgs[0], id++, ATTACK_SIDE_TILE));
		tiles.add(ATTACK_FRONT = new Tile(towerImgs[1], id++, ATTACK_FRONT_TILE));
	}

	public Tile getTile(int id) {
		return tiles.get(id);
	}

	public BufferedImage getSprite(int id) {
		return tiles.get(id).getSprite();
	}

	private BufferedImage getSprite(int x, int y) {
		return atlas.getSubimage(x * 32, y * 32, 32, 32);
	}

	private BufferedImage getWallSprite(int x, int y) {
		return wallTexture.getSubimage(x * 32, y * 32, 32, 32);
	}

	private BufferedImage getFinishSprite(int x, int y){
		return finishTexture.getSubimage(x * 32, y * 32, 32, 32);
	}
}
