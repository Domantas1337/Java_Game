package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import PlayerPack.InitPlayer;
import helpClass.Utilz;
import objects.Tower;
import scenes.Playing;

public class TowerManager {

	private Playing playing;
	public static BufferedImage[] towerImgs;
	private ArrayList<Tower> towers = new ArrayList<>();
	private int towerAmount = 0;
	private InitPlayer player;

	public TowerManager(Playing playing, InitPlayer player) {
		this.playing = playing;
		this.player = player;
		loadTowerImgs();
	}

	private void loadTowerImgs() {
		BufferedImage atlas = Utilz.getBufferedImage("Danger.png");
		towerImgs = new BufferedImage[3];
		towerImgs[1] = atlas.getSubimage( 16, 32, 32, 32);
		towerImgs[0] = atlas.getSubimage(  96 - 16,128 + 16 + 16, 32, 32);
	}

	public void addTower(Tower selectedTower, int xPos, int yPos) {
		towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
	}

	public void update() {
		for (Tower t : towers) {
			t.update();
			attackEnemyIfClose(t);
		}
	}

	private void attackEnemyIfClose(Tower t) {
		if (player.isAlive()) {
			if (t.isCooldownOver()) {
				playing.shootEnemy(t);
				t.resetCooldown();
			}
		}
	}

	public void draw(Graphics g) {
		for (Tower t : towers) {
			g.drawImage(towerImgs[t.getTowerType()], t.getX(), t.getY(), null);
		}
	}

	public static BufferedImage[] getTowerImgs() {
		return towerImgs;
	}
}
