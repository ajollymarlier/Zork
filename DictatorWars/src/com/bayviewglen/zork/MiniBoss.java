package com.bayviewglen.zork;

public class MiniBoss extends Enemy {

	public MiniBoss(int healthPoints, int speed, int strength, String name, boolean inRange) {
		super(healthPoints, speed, strength, name, inRange);

	}

	//Regular Attack: Does damage from 10-15 + strength
	//Strong Attack: Does damage from 12-15 + strength while ignoring defense
	public boolean attack(Player player){
			int choice = (int) (Math.random()* 4) + 1;
			
			if(choice == 1)
				return player.setDamage(getStrength() + ((int)(Math.random() * 3) + 12 + 1) + player.getDefense());
			else
				return player.setDamage(getStrength() + ((int)(Math.random() * 5) + 10 + 1));
		}

}
