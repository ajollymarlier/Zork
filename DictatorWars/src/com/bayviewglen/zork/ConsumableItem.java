package com.bayviewglen.zork;

public abstract class ConsumableItem extends Item{
	private boolean used;
	
	public ConsumableItem(int weight, String name){
		super(weight, name);
		setUsed(false);
	}
	
	
	public boolean getUsed() {
		return used;
	}
	
	public void setUsed(boolean used) {
		this.used = used;
	}
	
	
	
	
	
}
