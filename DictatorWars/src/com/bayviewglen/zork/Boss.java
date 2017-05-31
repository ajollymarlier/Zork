package com.bayviewglen.zork;

public class Boss extends Enemy {
	private int specialMaxAttack = 30;
	private int attackRange = 5;
	public Boss(int healthPoints, int speed, int strength, int dialogueNum, String name, boolean inRange) {
		super(healthPoints, speed, strength, dialogueNum, name, inRange);
	}
	
	//Regular Attack: Does damage from 25-30 + strength
	//Strong Attack: Does damage from 30-35 + strength while ignoring defense
	//Special Attack: Does damage from 30-35 + strength while ignoring defense and raises strength of Boss by 1-3
	public boolean attack(Player player){
		int choice = (int) (Math.random() * 10) + 1;
		
		if(choice == 10){
			changeStrength((int) (Math.random() * 3) + 1);
			System.out.println(getName() + "'s strength increased!");
			return player.setDamage(getStrength() + ((int)(Math.random() * attackRange) + specialMaxAttack + 1) - player.getDefense()) ;
		}else if(choice == 1 || choice == 2){
			printSpecialAttack();
			return player.setDamage(getStrength() + ((int)(Math.random() * attackRange) + specialMaxAttack + 1));
			
		}else{
			return player.setDamage(getStrength() + ((int)(Math.random() * attackRange) + specialMaxAttack + 1));
		}
		
	}
}
