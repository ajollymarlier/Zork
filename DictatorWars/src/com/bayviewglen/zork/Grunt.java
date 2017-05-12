package com.bayviewglen.zork;

public class Grunt extends Enemy {
	
	public Grunt(int healthPoints, int speed, int strength, String name, boolean inRange) {
		super(healthPoints, speed, strength, name, inRange);
		
	}
	
	//Adds number from 1-5 to strength
	public void attack(Player player){
		player.setHealthPoints(player.getHealthPoints() - getStrength() + ((int)(Math.random() * 5) + 1)); 
	}
	
	
	
}
