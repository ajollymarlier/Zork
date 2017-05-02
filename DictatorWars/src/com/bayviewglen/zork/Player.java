package com.bayviewglen.zork;

import java.util.ArrayList;

public class Player extends Character {
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private ArrayList<Ammo> ammoBag = new ArrayList<Ammo>();
	private int weightCarried = 0;

	// can change max weight, just sorta default TODO
	private final int MAX_WEIGHT = super.getStrength() * 10;


	public Player(int healthPoints, int speed, int strength) {
		super(healthPoints, speed, strength);
	}

	public boolean pickUp(Item item, Room room) {
		weightCarried += item.getWeight();

		if (weightCarried <= MAX_WEIGHT && item instanceof Ammo) {
			//TODO need to increase amt of ammo object if it is already present
			ammoBag.add((Ammo) item);
			return true;
			
		} else if (weightCarried <= MAX_WEIGHT) {
			inventory.add(item);
			return true;
			
		} else {
			weightCarried -= item.getWeight();
			room.addRoomItems(item);
			return false;
			// will work when parameters in game class are fixed
		}
	}

	// TODO this sort of works... Theoretically
	public void drop(Item item, Room room) {
		weightCarried -= item.getWeight();
		room.addRoomItems(findItem(item));
	}

	// TODO this too
	private Item findItem(Item item) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).equals(item)) {
				return inventory.remove(i);
			}
		}
		return null;
	}

}
