package com.bayviewglen.zork;

public abstract class Enemy extends Character{
	private String name;
	private boolean inRange;
	private int dialogueNum;
	
	public Enemy(int healthPoints, int speed, int strength, int dialogueNum, String name, boolean inRange) {
		super(healthPoints, speed, strength);
		this.name = name;
		this.inRange = inRange;
		this.dialogueNum = dialogueNum;
	}
	
	public String toString(){
		return name;
	}
	
	public boolean getInRange() {
		return inRange;
	}
	public String getName(){
		return name;
	}
	
	public void setInRange(boolean inRange){
		this.inRange = inRange;
	}

	public int getDialogueNum() {
		return dialogueNum;
	}
	
	public abstract boolean attack(Player player);
	
	public void printSpecialAttack(){
		System.out.println("The enemy has used a speical attack! EXTRA DAMAGE!");
	}
	

}
