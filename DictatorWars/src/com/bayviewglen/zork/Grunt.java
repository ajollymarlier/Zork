package com.bayviewglen.zork;

public class Grunt extends Enemy {
	private int attackRange = 5;
	
	public Grunt(int healthPoints, int speed, int strength, int dialogueNum, String name, boolean inRange) {
		super(healthPoints, speed, strength,dialogueNum, name, inRange);
		
	}
	
	//Regular Attack: Does damage from 1-5 + strength
	public boolean attack(Player player){
		int damage = getStrength() + ((int)(Math.random() * attackRange) + 1) - player.getDefense();
		if(damage < 0){
			damage = 5;
		}
		return player.setDamage(damage); 
	}
	
	
	
}
