package com.bayviewglen.zork;

public class Melee extends Weapon{
	//contains all possible Melee weapon names in caps;
	static public final String[] MELEE = {"sword", "axe", "stick"};
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
