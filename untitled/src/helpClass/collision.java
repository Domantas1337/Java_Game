package helpClass;

import main.Game;

import java.awt.geom.Rectangle2D;

public class collision {
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		if (x < 0 || x >= Game.GAME_WIDTH)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;


		float xIndex = x /  Constants.PlayerConstants.TILES_SIZE;
		float yIndex = y /  Constants.PlayerConstants.TILES_SIZE;

		int value = lvlData[(int) yIndex][(int) xIndex];

		if (value == 0  || value == 2 || value < 0 ){
			return true;
		}

		return false;
	}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int) (hitbox.x / Constants.PlayerConstants.TILES_SIZE);
		if (xSpeed > 0) {
			int tileXPos = currentTile *  Constants.PlayerConstants.TILES_SIZE;
			int xOffset = (int) ( Constants.PlayerConstants.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
		} else {
			return currentTile *  Constants.PlayerConstants.TILES_SIZE;
		}
	}

	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int) (hitbox.y /  Constants.PlayerConstants.TILES_SIZE) ;
		if (airSpeed > 0) {
			int tileYPos = currentTile *  Constants.PlayerConstants.TILES_SIZE;
			int yOffset = (int) (Constants.PlayerConstants.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		} else {
			return currentTile * Constants.PlayerConstants.TILES_SIZE;
		}
	}

	public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		if (!IsSolid(hitbox.x, hitbox.y + hitbox.height, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height, lvlData))
				return false;

		return true;
	}

}
