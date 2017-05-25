package com.bayviewglen.zork;

import java.util.ArrayList;

public class Chest extends Item implements Lockable {
	private  int unlockType;
	private Inventory inventory = new Inventory();
	
	//TODO MAKE THE KEYS WORK TO UNLOCK CHEST
	public Chest (int weight, String name, int unlockType){
		super(weight,name);
		this.unlockType = unlockType;
	}
	
	
	public boolean isLocked() {
		return (unlockType != 0);
	}

	//returns true if ends up locked
	public boolean unlock(Key key) {
		if (key.getType() == unlockType){
			key.setUsed(true);
			unlockType = 0;
			System.out.println("The chest is unlocked");
			return true;
		}
		return false;
			
	}


	public Inventory getInventory() {
		return inventory;
	}


	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}	
	

}
