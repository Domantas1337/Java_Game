package managers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import PlayerPack.InitPlayer;
import helpClass.Constants;
import helpClass.LoadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

import javax.imageio.ImageIO;

import static helpClass.Constants.Towers.*;
import static helpClass.Constants.Projectiles.*;

public class ProjectileManager {

	private Playing playing;
	private InitPlayer player;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Explosion> explosions = new ArrayList<>();
	private BufferedImage[] proj_imgs, explo_imgs;
	private int proj_id = 0;

	public ProjectileManager(Playing playing, InitPlayer player) {
		this.playing = playing;
		this.player = player;
		importImgs();
	}

	private BufferedImage getProjectileImage() {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("projectile.png");

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	private void importImgs() {
		BufferedImage atlas = getProjectileImage();
		proj_imgs = new BufferedImage[3];

		for (int i = 0; i < 3; i++) {
			proj_imgs[i] = atlas.getSubimage(i * 32, 32 * 18, 32, 32);
		}
		importExplosion(atlas);
	}

	private void importExplosion(BufferedImage atlas) {
		explo_imgs = new BufferedImage[7];

		for (int i = 0; i < 3; i++) {
			explo_imgs[i] = atlas.getSubimage(i * 32, 32 * 18, 32, 32);
		}
	}

	public void newProjectile(Tower t) {
		int type = getProjType(t);

		int xDist = (int) (t.getX() - player.getPlayerY());
		int yDist = (int) (t.getY() - player.getPlayerX());
		int totDist = Math.abs(xDist) + Math.abs(yDist);

		float xSpeed = 0;
		float ySpeed = 0;

		if (type == ARROW) {
			xSpeed = helpClass.Constants.Projectiles.GetSpeed(type);
			ySpeed = 0;
		}
		if (type == BOMB) {
		System.out.println("yes");
			xSpeed = 0;
			ySpeed = helpClass.Constants.Projectiles.GetSpeed(type);
		}
		if(type == CHAINS){
			xSpeed = 2;
			ySpeed = Constants.Projectiles.GetSpeed(type);
		}

		if (t.getX() > player.getPlayerX()) {
			xSpeed *= -1;
		}
		if (t.getY() > player.getPlayerY()) {
			ySpeed *= -1;
		}

		float rotate = 0;

		if (type == ARROW) {
			float arcValue = (float) Math.atan(yDist / (float) xDist);
			rotate = (float) Math.toDegrees(arcValue);

			if (xDist < 0) {
				rotate += 180;
			}
		}

		for (Projectile p : projectiles) {
			if (!p.isActive()) {
				if (p.getProjectileType() == type) {
					p.reuse(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), rotate);
					return;
				}
			}
		}
		projectiles.add(new Projectile(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), rotate, proj_id++, type));

	}

	public void update() {
		int i = 1;
		for (Projectile p : projectiles) {
			if (p.isActive()) {
				p.move();
				if (isProjHittingEnemy(p)) {
					p.setActive(false);
					if (p.getProjectileType() == BOMB) {
						explosions.add(new Explosion(p.getPos()));
						explodeOnEnemies(p);
					}
				} else if (isProjOutsideBounds(p)) {
					p.setActive(false);
				}
			}
		}

		for (Explosion e : explosions) {
			if (e.getIndex() < 7) {
				e.update();
			}
		}

	}

	private void explodeOnEnemies(Projectile p) {

			float radius = 40.0f;

			float xDist = Math.abs(p.getPos().x - player.getPlayerX());
			float yDist = Math.abs(p.getPos().y - player.getPlayerY());

			float realDist = (float) Math.hypot(xDist, yDist);

			if (realDist <= radius) {
				player.hurt(p.getDmg());
			}
	}

	private boolean isProjHittingEnemy(Projectile p) {
		if (player.isAlive()) {
			if (player.getHitbox().contains(p.getPos())) {
				player.hurt(p.getDmg());
				return true;
			}
		}
		return false;
	}

	private boolean isProjOutsideBounds(Projectile p) {
		if (p.getPos().x - player.hitboxX() < 310)
			return false;

		if (p.getPos().x >= 0)
			if (p.getPos().x <= 640)
				if (p.getPos().y >= 0)
					if (p.getPos().y <= 800)
						return false;
		return true;
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (Projectile p : projectiles) {
			if(p.getPos().x < 100)
				p.setActive(false);

			if (p.isActive()) {
				if (p.getProjectileType() == ARROW) {
					g2d.translate(p.getPos().x, p.getPos().y);
					g2d.rotate(Math.toRadians(p.getRotation()));
					g2d.drawImage(proj_imgs[p.getProjectileType()], -16, -16, null);
					g2d.rotate(-Math.toRadians(p.getRotation()));
					g2d.translate(-p.getPos().x, -p.getPos().y);
				} else {
					g2d.drawImage(proj_imgs[p.getProjectileType()], (int) p.getPos().x - 16, (int) p.getPos().y, null);
				}
			}
		}
		drawExplosions(g2d);

	}

	private void drawExplosions(Graphics2D g2d) {
		for (Explosion e : explosions)
			if (e.getIndex() < 7)
				g2d.drawImage(explo_imgs[e.getIndex()], (int) e.getPos().x - 16, (int) e.getPos().y - 16, null);
	}

	private int getProjType(Tower t) {
		switch (t.getTowerType()) {
		case ARCHER:
			return ARROW;
		case CANNON:
			return BOMB;
		case WIZARD:
			return CHAINS;
		}
		return 0;
	}

	public class Explosion {

		private Point2D.Float pos;
		private int exploTick, exploIndex;

		public Explosion(Point2D.Float pos) {
			this.pos = pos;
		}

		public void update() {
			exploTick++;
			if (exploTick >= 6) {
				exploTick = 0;
				exploIndex++;
			}
		}

		public int getIndex() {
			return exploIndex;
		}

		public Point2D.Float getPos() {
			return pos;
		}
	}

	public void reset() {
		projectiles.clear();
		explosions.clear();

		proj_id = 0;
	}

}
