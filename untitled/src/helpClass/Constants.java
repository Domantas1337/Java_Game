package helpClass;

import main.GameStates;

import java.io.File;

public class Constants {

public static class PlayerConstants {
	public static final int IDLE = 0;
	public static final int RUNNING = 1;
	public static final int JUMP = 2;
	public static final int FALLING = 3;
	public static final int GROUND = 4;
	public static final int HIT = 5;;

		public final static int TILES_DEFAULT_SIZE = 32;
		public final static float SCALE = 2f;
		public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE);

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {
			case RUNNING:
				return 6;
			case IDLE:
				return 5;
			case HIT:
				return 4;
			case JUMP:
				return 3;
			case GROUND:
				return 2;
			case FALLING:
			default:
				return 1;
			}
		}
	}

	public static class levelFiles{
		public static File lvlFile = new File("level.csv");
		public static File lvlFile2 = new File("level2.csv");
		public static File lvlFile3 = new File("level3.csv");

		public static File getFile(){
			if(GameStates.levelState == GameStates.LEVEL1)
				return lvlFile;
			if(GameStates.levelState == GameStates.LEVEL2)
				return lvlFile2;
			if(GameStates.levelState == GameStates.LEVEL3)
				return lvlFile3;
			return null;
		}

	}

	public static class Projectiles {
		public static final int ARROW = 0;
		public static final int CHAINS = 1;
		public static final int BOMB = 2;

		public static float GetSpeed(int type) {
			switch (type) {
			case ARROW:
				return 8f;
			case BOMB:
				return 7f;
			}
			return 0f;
		}
	}

	public static class Towers {
		public static final int CANNON = 0;
		public static final int ARCHER = 1;
		public static final int WIZARD = 2;

		public static float GetDefaultCooldown(int towerType) {
			switch (towerType) {
			case CANNON:
				return 35;
			case ARCHER:
				return 60;
			case WIZARD:
				return 50;
			}

			return 0;
		}
	}

	public static class forPlayer{
		public static final int HEALTH = 1;
		public static final int WIDTH = 50;
		public static final int HEIGHT = 37;
	}

	public static class Tiles {
		public static final int WATER_TILE = 0;
		public static final int GRASS_TILE = 1;
		public static final int ROAD_TILE = 2;
		public static final int DIRT_TILE = 3;
		public static final int FINISH_TILE = 4;
		public static final int ATTACK_SIDE_TILE = 5;
		public static final int ATTACK_FRONT_TILE = 6;
	}

}
