package com.bayviewglen.zork;


public class Weapon extends Item{
	private int damage;
	
	public Weapon(int weight, String name, int damage){
		super(weight, name);
		this.damage = damage;
		
	}
	
	public void attack(Enemy enemy, Player player, Weapon weapon){
		enemy.setHealthPoints(enemy.getHealthPoints() - weapon.getDamage());
	}

	public int getDamage() {
		return damage;
	}

	
}
