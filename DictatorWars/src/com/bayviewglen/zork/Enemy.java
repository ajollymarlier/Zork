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
	
	
	

}
