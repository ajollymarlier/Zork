package com.bayviewglen.zork;

public class ConsumableItem extends Item{
	private boolean used;
	
	public ConsumableItem(int weight, String name){
		super(weight, name);
		setUsed(false);
	}
	//TODO since consumable once it is used, runs method to get rid of item from inventory
	public void usedItem(){
		setUsed(true);
	
	}
	public boolean getUsed() {
		return used;
	}
	
	public void setUsed(boolean used) {
		this.used = used;
	}
	
	
	
	
	
}
