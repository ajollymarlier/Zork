package com.bayviewglen.zork;

public class Chest extends Item implements Lockable {
	private boolean locked;
	private Item key;
	
	//TODO MAKE THE KEYS WORK TO UNLOCK CHEST
	public Chest (int weight, String name, boolean locked, Item key){
		super(weight,name);
		this.locked = locked;
		this.key = key;
	}
	
	
	public boolean isLocked() {
		return locked;
	}

	//returns true if ends up locked
	public boolean unlock(Item key) {
		// TODO Auto-generated method stub
		return false;
	}
	//returns true if it ends up locked
	public boolean lock(Item key){
		// TODO Auto-generated method stub
		return false;
	}

}
