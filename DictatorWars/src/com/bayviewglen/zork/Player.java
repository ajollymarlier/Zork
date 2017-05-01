package com.bayviewglen.zork;

import java.util.ArrayList;

public class Player extends Character {
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private int weightCarried = 0;
	private final int MAX_WEIGHT = (int) Math.sqrt((getStrength() * 4));
	//TODO This max weight will be changed to a variable based on character attributes
	
	
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
			//will work when parameters in game class are fixed
		}	
	}
	
	//TODO this sort of works... Theoretically
	public void drop(Item item, Room room){
		weightCarried -= item.getWeight();
		room.addRoomItems(findItem(item));
	}

	//TODO this too
	private Item findItem(Item item) {
		for(int i = 0; i < inventory.size(); i++){
			if (inventory.get(i).equals(item)){
				return inventory.remove(i);
			}
		}
		return null;
	}
	
	
	

	
}
