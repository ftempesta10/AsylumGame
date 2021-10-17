package engine;

public class Enemy extends AdventureCharacter {
	/**
	 *
	 */
	private static final long serialVersionUID = 1028459431835311903L;
	private Integer minDamage, maxDamage;

	public Enemy(Integer health, String name, String description, String talk, Inventory inv, Item droppable, Integer minDamage, Integer maxDamage) {
		super(health, name, description, talk, inv, droppable);
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
