package com.bayviewglen.zork;


public class Weapon extends Item{
	private int damage;
	
	public Weapon(int weight, String name, int damage){
		super(weight, name);
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}
	
	

	
}
