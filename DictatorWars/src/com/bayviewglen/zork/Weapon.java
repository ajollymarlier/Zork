package com.bayviewglen.zork;


public class Weapon extends Item{
	private int damage;
	
	public Weapon(int weight, String name){
		super(weight, name);
	}
	
	public void attack(Enemy enemy, Player player, Weapon weapon){
		enemy.setHealthPoints(enemy.getHealthPoints() - weapon.getDamage());
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
}
