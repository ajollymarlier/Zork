package com.bayviewglen.zork;

import java.util.ArrayList;

public class Player extends Character {
	ArrayList<Item> inventory = new ArrayList<Item>();
	
	
	public Player(int healthPoints, int speed, int strength) {
		super(healthPoints, speed, strength);
	}
	
	public void pickUp(Item item){
		inventory.add(item);
		//take item removed from room and adds to inventory
	}
	

	
}
