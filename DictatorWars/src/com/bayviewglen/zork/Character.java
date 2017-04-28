package com.bayviewglen.zork;

public abstract class Character {
	private int healthPoints;
	private int speed;
	private int strength;


	public Character(int healthPoints, int speed, int strength){
		this.healthPoints = healthPoints;
		this.speed = speed;
		this.strength = strength;
	}


	public int getHealthPoints() {
		return healthPoints;
	}


	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}
	
	//returns true if dead
	public boolean setDamage(int hitPoint) {
		healthPoints -= hitPoint;
		return healthPoints <= 0;

	}

	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public int getStrength() {
		return strength;
	}


	public void setStrength(int strength) {
		this.strength = strength;
	}

}
