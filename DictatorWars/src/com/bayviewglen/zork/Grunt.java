package com.bayviewglen.zork;

public class Grunt extends Enemy {
	
	public Grunt(int healthPoints, int speed, int strength, String name, boolean inRange) {
		super(healthPoints, speed, strength, name, inRange);
		
	}
	
	//Adds number from 1-5 to strength
	public boolean attack(Player player){
		return player.setDamage(getStrength() + ((int)(Math.random() * 5) + 1)); 
	}
	
	
	
}
