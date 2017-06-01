package com.bayviewglen.zork;

import java.util.ArrayList;

public class Chest extends Item implements Lockable {
	private  int unlockType;
	private Inventory inventory = new Inventory();
	public static String [] chestNames = {"chest", "container", "supply_drop", "sparkly_chest", "dragon_chest"};
	//TODO MAKE THE KEYS WORK TO UNLOCK CHEST
	public Chest (String name, int unlockType){
		super(name);
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
	
	public int getUnlockType(){
		return unlockType;
	}
	

}
