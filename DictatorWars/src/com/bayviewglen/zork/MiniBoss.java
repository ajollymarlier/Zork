package com.bayviewglen.zork;

public class MiniBoss extends Enemy {
	private int specialMaxAttack = 12;
	private int maxAttack = 10;
	private int attackRange = 5;
	
	public MiniBoss(int healthPoints, int speed, int strength, String name, boolean inRange) {
		super(healthPoints, speed, strength, name, inRange);

	}

	//Regular Attack: Does damage from 10-15 + strength
	//Strong Attack: Does damage from 12-17 + strength while ignoring defense
	public boolean attack(Player player){
			int choice = (int) (Math.random()* 4) + 1;
			
			if(choice == 1)
				return player.setDamage(getStrength() + ((int)(Math.random() * attackRange) + specialMaxAttack + 1) + player.getDefense());
			else
				return player.setDamage(getStrength() + ((int)(Math.random() * attackRange) + maxAttack + 1));
		}

}
