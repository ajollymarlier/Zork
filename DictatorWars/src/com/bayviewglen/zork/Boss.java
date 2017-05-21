package com.bayviewglen.zork;

public class Boss extends Enemy {
	//small change
	public Boss(int healthPoints, int speed, int strength, String name, boolean inRange) {
		super(healthPoints, speed, strength, name, inRange);
	}

	public boolean attack(Player player){
		int choice = (int) (Math.random() * 10) + 1;
		
		if(choice == 10){
			return player.setDamage(getStrength() + (int)(Math.random() * 5) + 30);
			//TODO poisons player causing bleed damage
		}else if(choice == 1 || choice == 2){
			return player.setDamage(getStrength() + ((int)(Math.random() * 5) + 30) + player.getDefense());
			
		}else{
			return player.setDamage(getStrength() + ((int)(Math.random() * 5) + 25));
		}
		
	}
}
