package com.bayviewglen.zork;

public class EffectItem extends ConsumableItem {
	public static final String[] EFFECT_ITEMS = {"potion", "Speedy", "Go_Go_Go", "mini_potion", "tiny_potion", "potion", "super_potion","health_potion", "weak_stimulant", "magma_stimulant", "power_stimulant"};
	private int healthBoost;
	private int defenseBoost;
	private int speedBoost;
	private int strengthBoost;

	public EffectItem(int weight, String name, int healthBoost, int defenseBoost, int speedBoost, int strengthBoost) {
		super(weight, name);
		this.healthBoost = healthBoost;
		this.defenseBoost = defenseBoost;
		this.speedBoost = speedBoost;
		this.strengthBoost = strengthBoost;
	}


	public int getHealthBoost() {
		return healthBoost;
	}

	public int getDefenseBoost() {
		return defenseBoost;
	}

	public int getSpeedBoost() {
		return speedBoost;
	}

	public int getStrengthBoost() {
		return strengthBoost;
	}
}
