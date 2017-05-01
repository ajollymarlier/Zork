package com.bayviewglen.zork;

public class Ranged extends Weapon{
	private int ammo;
	
	public Ranged(int weight, String name, int damage, int ammo){
		super(weight, name, damage);
		this.ammo = ammo;
	}
	
	// attacks enemy regardless of range boolean but has ammo
	public void attack(Enemy enemy, Player player, Weapon weapon){
		if (ammo <= 0){
			super.attack(enemy, player, weapon);;
		}else{
			System.out.println(weapon.getName() + " is out of ammo");
		}
	}
	
}
