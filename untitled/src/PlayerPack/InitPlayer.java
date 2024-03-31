package PlayerPack;

import helpClass.Constants;
import helpClass.LoadSave;
import helpClass.Utilz;
import main.GameStates;
import scenes.Playing;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static helpClass.Constants.PlayerConstants.*;
import static helpClass.collision.*;

public class InitPlayer extends Entity{

	public static float SCALE = 2f;

	public BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 25;
	private int playerAction = IDLE;
	private boolean moving = false;
	private boolean left, up, right, down, jump;
	public float playerSpeed = 2.5f;
	private int[][] lvl;
	private float xDrawOffset = 21 * SCALE;
	private float yDrawOffset = 4 * SCALE;
	public int spawnX = 32, spawnY = 512 - 64;

	private float airSpeed = 0f;
	private float gravity = 0.077f * SCALE;
	private float jumpSpeed = -2.25f * SCALE;
	private float fallSpeedAfterCollision = 0.5f * SCALE;
	private boolean inAir = false;


    Playing playing;

    public static float x, y;

	private boolean alive;

    protected int health;
	private GameStates state;

    public InitPlayer(Playing playing, float x, float y, int[][] level, GameStates gameState){

        super(200, 200, (int) (20 ), (int) (27));
        loadAnimations();
		initHitbox(spawnX, spawnY, 20  , 27);

		this.lvl = level;
        this.playing = playing;
        this.state = state;
		this.x = x;
		this.y = y;
		this.alive = true;
		this.health = Constants.forPlayer.HEALTH;

    }

	public void update(){
		updatePos();
		updateAnimationTick();
		setAnimation();
		changeLevel();
		fall();
	}

	public BufferedImage getAnimations(){
		return animations[playerAction][aniIndex];
	}

	public int hitboxX(){
		return (int) (hitbox.x - 10);
	}
	public int hitboxY(){
		return (int) (hitbox.y);
	}

	private void updateAnimationTick() {
		if (++aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex = (aniIndex + 1) % GetSpriteAmount(playerAction);
		}
	}

	private void setAnimation() {
		int startAni = playerAction;

		if (moving) {
			playerAction = RUNNING;
		}
		else {
			playerAction = IDLE;
		}

		if (inAir) {
			if (airSpeed < 0) {
				playerAction = JUMP;
			}
			else {
				playerAction = FALLING;
			}
		}

		if (startAni != playerAction) {
			resetAniTick();
		}
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;

		// Handle jump logic.
		if (jump) jump();

		// Determine horizontal speed based on input.
		float xSpeed = 0;
		if (left) xSpeed -= playerSpeed;
		if (right) xSpeed += playerSpeed;

		// Update in-air status if not on the floor.
		if (!IsEntityOnFloor(hitbox, lvl)) {
			inAir = true;
		}

		if (inAir) {
			// Temporarily calculate the next Y position without applying it.
			float nextY = hitbox.y + airSpeed;
			// Check if the entity can move to the next Y position.
			if (CanMoveHere(hitbox.x, nextY, hitbox.width, hitbox.height, lvl)) {
				// Apply the movement since there's no collision.
				hitbox.y = nextY;
				airSpeed += gravity; // Continue applying gravity if in air.
			} else {
				// Collision detected, adjust Y position and reset airSpeed accordingly.
				if (airSpeed > 0) { // Falling down.
					hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed) - 1; // Adjust Y position, the "-1" might need tuning.
				} else { // Moving up.
					// For moving up, you might need a similar method that correctly positions the entity just below the ceiling it hits.
				}
				airSpeed = 0; // Stop vertical movement.
				inAir = false; // The entity has landed or hit a ceiling, so it's not in air anymore.
			}
		}

		// Handle horizontal movement...
		if (xSpeed != 0 || inAir) {
			updateXPos(xSpeed);
			moving = true;
		}
	}
	private void jump() {
		if (!inAir) {
			inAir = true;
			airSpeed = jumpSpeed;
		}
	}

	private void resetInAirIfNeeded() {
		// Implement logic to reset `inAir` based on conditions not visible in the original snippet,
		// for example, if airSpeed has been reset to 0 indicating landing.
		if (airSpeed == 0) {
			inAir = false;
		}
	}


	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvl)) {
			hitbox.x += xSpeed;
		} else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}

	}

	public void changeLevel(){
		Rectangle rectangle = new Rectangle((int) hitbox.x, (int) hitbox.y, 32, 32);
			if (lvl[(int) (hitbox.y / 32)][(int) (hitbox.x / 32)] == 3 && getHitbox().intersects(rectangle)) {
				if (GameStates.levelState == GameStates.LEVEL1) {
					GameStates.levelState = GameStates.LEVEL2;
				} else if(GameStates.levelState == GameStates.LEVEL2) {
					GameStates.levelState = GameStates.LEVEL3;
				}
				else {
					playing.setGameFinished(true);
				}

				if(playing.isFinished() != true) {
					playing.startLevel(GameStates.levelState);
					hitbox.x = 200;
					hitbox.y = 200;

					setlvl();
					update();
				}
			}
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public void setlvl(){
		lvl = LoadSave.GetLevelData(Constants.levelFiles.getFile());
	}

	public int[][] getlvl(){
		return lvl;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void hurt(int dmg) {
		this.health -= dmg;
		playing.removeOneLife(dmg);

		alive = false;
		playing.update();
		respawn();
	}

	public void fall(){
		if(hitbox.y > 570) {
			alive = false;
			playing.update();
			this.health -= 1;
			playing.removeOneLife(1);
			respawn();
		}
	}

	private void respawn(){
		hitbox.x = spawnX;
		hitbox.y = spawnY;
		this.health = 1;
		alive = true;
	}

	private void loadAnimations() {

		BufferedImage img = Utilz.getBufferedImage("player_sprites.png");

		animations = new BufferedImage[9][6];
		for (int j = 0; j < animations.length; j++) {
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
			}
		}
	}

	public boolean isAlive(){
		return alive;
	}

	public float getHitx() {return hitbox.x;}

	public float getPlayerX(){
		return x;
	}
	public float getPlayerY(){
		return y;
	}
}

