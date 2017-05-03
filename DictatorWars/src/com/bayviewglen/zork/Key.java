package com.bayviewglen.zork;

public class Key extends ConsumableItem {
	// have a type of key to see if it is key for chest, door etc.
	// chest:1, door:2, add more
	private int type;

	public Key(String name, int type) {
		super(0, name);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
