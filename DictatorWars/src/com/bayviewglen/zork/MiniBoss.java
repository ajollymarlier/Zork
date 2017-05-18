package com.bayviewglen.zork;

public class MiniBoss extends Enemy {

	public MiniBoss(int healthPoints, int speed, int strength, String name, boolean inRange) {
		super(healthPoints, speed, strength, name, inRange);

	}

	// Adds number from 10-15 to strength
	public void attack(Player player){
			int choice = (int) (Math.random()* 4) + 1;
			
			if(choice == 1)
				player.setHealthPoints(player.getHealthPoints() - getStrength() + ((int)(Math.random() * 5) + 10) + player.getDefense());
			else
				player.setHealthPoints(player.getHealthPoints() - getStrength() + ((int)(Math.random() * 5) + 5)); 
		}

}
