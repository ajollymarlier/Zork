package com.bayviewglen.zork;

public abstract class Enemy extends Character{
	private String name;
	private boolean inRange;
	
	public Enemy(int healthPoints, int speed, int strength, String name, boolean inRange) {
		super(healthPoints, speed, strength);
		this.name = name;
		this.inRange = inRange;
	}
	
	public void attack(Player player, int strength){
			player.setDamage(strength);
	}
	
	
	public String toString(){
		return name;
	}
	
	public boolean getInRange() {
		return inRange;
	}
	public String getName(){
		return name;
	}
	
	public void setInRange(boolean inRange){
		this.inRange = inRange;
	}
	
	

}
