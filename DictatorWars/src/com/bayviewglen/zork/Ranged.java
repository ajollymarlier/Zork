package com.bayviewglen.zork;

public class Ranged extends Weapon{
	
	public Ranged(int weight, String name, int damage){
		super(weight, name, damage);
	}
	
	
	// attcks enemy regardless of range boolean but has ammo
	/*
	public void attack(Enemy enemy, Player player, Weapon weapon){
		if (ammo <= 0){
			super.attack(enemy, player, weapon);;
		}else{
			System.out.println(weapon.getName() + " is out of ammo");
		}
	}
	*/
}
