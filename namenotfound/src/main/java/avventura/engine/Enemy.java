package engine;

public class Enemy extends AdventureCharacter {
	private Integer minDamage, maxDamage;

	public Enemy(Integer minDamage, Integer maxDamage) {
		super();
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
	}

	public Integer getMinDamage() {
		return minDamage;
	}

	public void setMinDamage(Integer minDamage) {
		this.minDamage = minDamage;
	}

	public Integer getMaxDamage() {
		return maxDamage;
	}

	public void setMaxDamage(Integer maxDamage) {
		this.maxDamage = maxDamage;
	}

	public Integer getDamage() {
		Double dam = (Math.random()*(maxDamage-minDamage))+minDamage;
		return dam.intValue();
	}

}
