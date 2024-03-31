package objects;

public class Tower {

	private int x, y, id, towerType, cdTick, dmg;
	private float range, cooldown;
	private int tier;

	public Tower(int x, int y, int id, int towerType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.towerType = towerType;
		tier = 1;
		setDefaultDmg();
		setDefaultRange();
		setDefaultCooldown();
	}

	public void update() {
		cdTick++;
	}

	public boolean isCooldownOver() {
		return cdTick >= cooldown;
	}

	public void resetCooldown() {
		cdTick = 0;
	}

	private void setDefaultCooldown() {
		cooldown = helpClass.Constants.Towers.GetDefaultCooldown(towerType);
	}

	private void setDefaultRange() {
		range = 20;
	}

	private void setDefaultDmg() {
		dmg = 1;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getTowerType() {
		return towerType;
	}

	public int getDmg() {
		return dmg;
	}

	public int getTier() {
		return tier;
	}

}
