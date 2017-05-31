package com.bayviewglen.zork;

public abstract class Character {
	private int healthPoints;
	private int speed;
	private int strength;
	private int defense;

	public Character(int healthPoints, int speed, int strength){
		this.healthPoints = healthPoints;
		this.speed = speed;
		this.strength = strength;
		this.defense = 0;
	}

	
	public int getHealthPoints() {
		return healthPoints;
	}
	
	public void changeHealthPoints(int healthPoints) {
		this.healthPoints += healthPoints;
	}

	
	//returns true if dead
	public boolean setDamage(int hitPoint) {
		healthPoints -= hitPoint;
		return healthPoints <= 0;
	}

	public int getSpeed() {
		return speed;
	}

	public void changeSpeed(int amount) {
		speed += amount;
	}


	public int getStrength() {
		return strength;
	}

	
	public void changeStrength(int amount) {
		strength += amount;
	}


	public int getDefense() {
		return defense;
	}


	public void setDefense(int defense) {
		this.defense = defense;
	}
	
	public void changeDefense(int amount){
		defense += amount;
	}
	
	
	

}
