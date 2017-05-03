package com.bayviewglen.zork;

import java.util.ArrayList;

public class Chest extends Item implements Lockable {
	private static int UNLOCK_TYPE = 1;
	private boolean locked;
	private Item key;
	private ArrayList<Item> inChest;
	
	//TODO MAKE THE KEYS WORK TO UNLOCK CHEST
	public Chest (int weight, String name, boolean locked, Item key, ArrayList<Item> inChest){
		super(weight,name);
		this.locked = locked;
		this.key = key;
		this.inChest = inChest;
	}
	
	
	public boolean isLocked() {
		return locked;
	}

	//returns true if ends up locked
	public boolean unlock(Key key) {
		if (key.getType() == UNLOCK_TYPE){
			//TODO find way to get rid of consumables once used
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
