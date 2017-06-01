package com.bayviewglen.zork;

public class Key extends ConsumableItem {
	// have a type of key to see if it is key for chest, door etc.
	// chest:1, door:2, add more
	// if type 0 is unlocked
	static public final String[] KEY = {"jewel", "vaporizer", "drop_key", "sparkly_key","bomb", "huge_key", "dragon_key"};

	private int type;
	//all key weight are initialized with 0
	public Key(String name, int type) {
		super(0, name);
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
