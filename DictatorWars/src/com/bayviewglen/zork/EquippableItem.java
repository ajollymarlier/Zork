package com.bayviewglen.zork;

public class EquippableItem extends Item{
	private boolean equipped;
	private int healthBoost;
	private int defenseBoost;
	private int speedBoost;
	private int strengthBoost;
	private String type;
	
	static public final String [] EQUIPPABLE = {"helmet", "shield", "shirt"};
	//TODO add defense attribute in player class
	public EquippableItem(int weight, String name, int healthBoost, int defenseBoost, int speedBoost, int strengthBoost, String type){
		super(weight, name);
		this.equipped = false;
		this.healthBoost = healthBoost;
		this.defenseBoost = defenseBoost;
		this.speedBoost = speedBoost;
		this.strengthBoost = strengthBoost;
		this.type = type;
	} 
	
	public void setEquipped(boolean is){
		equipped = is;
	}
	
	public boolean getEquipped(){
		return equipped;
	}
	
	public int getHealthBoost(){
		return healthBoost;
	}
	
	public int getDefenseBoost(){
		return defenseBoost;
	}
	
	public int getSpeedBoost(){
		return speedBoost;
	}
	
	public int getStrengthBoost(){
		return strengthBoost;
	}
	//TODO edit equip method in player class

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
