package com.bayviewglen.zork;

import java.util.ArrayList;

public class Player extends Character {
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private int weightCarried = 0;
	private final int MAX_WEIGHT = 50;
	
	
	public Player(int healthPoints, int speed, int strength) {
		super(healthPoints, speed, strength);
	}
	
	public void pickUp(Item item, Room room){
		weightCarried += item.getWeight();
		
		if (weightCarried <= MAX_WEIGHT){
			inventory.add(item);
			//remove item
		}else{
			weightCarried -= item.getWeight();
			room.addRoomItems(item);
		}	
	}
	

	
}
