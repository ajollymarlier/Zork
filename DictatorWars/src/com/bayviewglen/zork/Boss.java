package com.bayviewglen.zork;

public class Boss extends Enemy {
	//small change
	public Boss(int healthPoints, int speed, int strength, String name, boolean inRange) {
		super(healthPoints, speed, strength, name, inRange);
	}
	
	//Regular Attack: Does damage from 25-30 + strength
	//Strong Attack: Does damage from 30-35 + strength while ignoring defense
	//Special Attack: Does damage from 30-35 + strength while ignoring defense and raises strength of Boss by 1-3
	public boolean attack(Player player){
		int choice = (int) (Math.random() * 10) + 1;
		
		if(choice == 10){
			changeStrength((int) (Math.random() * 3) + 1);
			System.out.println(getName() + "'s strength increased!");
			return player.setDamage(getStrength() + ((int)(Math.random() * 5) + 30 + 1) + player.getDefense()) ;
		}else if(choice == 1 || choice == 2){
			return player.setDamage(getStrength() + ((int)(Math.random() * 5) + 30 + 1) + player.getDefense());
			
		}else{
			return player.setDamage(getStrength() + ((int)(Math.random() * 5) + 25 + 1));
		}
		
	}
}
