package com.bayviewglen.zork;

public class Melee extends Weapon{

	public Melee(int weight, String name, int damage) {
		super(weight, name, damage);
	}
	
	/*public void attack(Enemy enemy, Player player, Weapon weapon){
		if(enemy.getInRange()){
			super.attack(enemy, player, weapon);
		}else{
			System.out.println(enemy + " is out of range");
		}
	}
	*/
	
}
