package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import helpClass.Utilz;
import objects.Tile;
import static helpClass.Constants.Tiles.*;

public class TileManager {

	public Tile GRASS, WATER, ROAD, FINISH, ATTACK_SIDE, ATTACK_FRONT;

	private BufferedImage atlas, wallTexture, finishTexture, dangerAtlas;
	public ArrayList<Tile> tiles = new ArrayList<>();

	public TileManager() {
		atlas = Utilz.getBufferedImage("Grass.png");
		wallTexture = Utilz.getBufferedImage("Floor.png");
		finishTexture = Utilz.getBufferedImage("finish.png");
		dangerAtlas = Utilz.getBufferedImage("Danger.png");
		createTiles();
	}

	private void createTiles() {

		int id = 0;

		BufferedImage[] towerImages = new BufferedImage[2];
		towerImages[1] = dangerAtlas.getSubimage( 16, 32, 32, 32);
		towerImages[0] = dangerAtlas.getSubimage(  96 - 16,128 + 16 + 16, 32, 32);

		tiles.add(ROAD = new Tile(getSubImage(wallTexture,7, 5), id++, ROAD_TILE));
		tiles.add(GRASS = new Tile(getSubImage(atlas,0, 0), id++, GRASS_TILE));
		tiles.add(WATER = new Tile(getSubImage(wallTexture,7, 6), id++, WATER_TILE));
		tiles.add(FINISH = new Tile(getSubImage(finishTexture,0, 2), id++, FINISH_TILE));
		tiles.add(ATTACK_SIDE = new Tile(towerImages[0], id++, ATTACK_SIDE_TILE));
		tiles.add(ATTACK_FRONT = new Tile(towerImages[1], id++, ATTACK_FRONT_TILE));
	}

	public Tile getTile(int id) {
		return tiles.get(id);
	}

	public BufferedImage getSprite(int id) {
		return tiles.get(id).getSprite();
	}


	private BufferedImage getSubImage(BufferedImage source, int x, int y) {
		return source.getSubimage(x * 32, y * 32, 32, 32);
	}



}
