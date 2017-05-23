package com.bayviewglen.zork;

public class Grunt extends Enemy {
	
	public Grunt(int healthPoints, int speed, int strength, String name, boolean inRange) {
		super(healthPoints, speed, strength, name, inRange);
		
	}
	
	//Regular Attack: Does damage from 25-30 + strength
	//Strong Attack: Does damage from 30-35 + strength
	public boolean attack(Player player){
		return player.setDamage(getStrength() + ((int)(Math.random() * 5) + 1)); 
	}
	
	
	
}
