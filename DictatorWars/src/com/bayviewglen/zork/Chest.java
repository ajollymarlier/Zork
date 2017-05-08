package com.bayviewglen.zork;

import java.util.ArrayList;

public class Chest extends Item implements Lockable {
	private static int UNLOCK_TYPE = 1;
	private boolean locked;
	private ArrayList<Item> inChest;
	
	//TODO MAKE THE KEYS WORK TO UNLOCK CHEST
	public Chest (int weight, String name, boolean locked, ArrayList<Item> inChest){
		super(weight,name);
		this.locked = locked;
		this.inChest = inChest;
	}
	
	
	public boolean isLocked() {
		return locked;
	}

	//returns true if ends up locked
	public boolean unlock(Key key) {
		if (key.getType() == UNLOCK_TYPE){
			key.usedItem();
			return true;
		}
		return false;
			
	}	
	//returns true if the chest opens up
	public ArrayList<Item> open(){
		if (locked)
			return null;
		else
			return inChest;
	}

}
