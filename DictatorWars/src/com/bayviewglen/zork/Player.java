package com.bayviewglen.zork;

import java.util.ArrayList;

public class Player extends Character {
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private int weightCarried = 0;
	//can change max weight, just sorta default TODO
	private final int MAX_WEIGHT = super.getStrength() * 10; 
	//test
	//TODO This max weight will be changed to a variable based on character attributes
	
	
	public Player(int healthPoints, int speed, int strength) {
		super(healthPoints, speed, strength);
		inventory.add(new Melee (10, "sword" ,100));
	}
	
	public boolean pickUp(Item item, Room room){
		weightCarried += item.getWeight();
		
		if (weightCarried <= MAX_WEIGHT){
			inventory.add(item);
			return true;
		}else{
			weightCarried -= item.getWeight();
			room.addRoomItems(item);
			return false;
			//will work when parameters in game class are fixed
		}	
	}
	
	//TODO this sort of works... Theoretically
	public void drop(Item item, Room room){
		weightCarried -= item.getWeight();
		room.addRoomItems(removeInventoryItem(item));
	}

	//TODO this too
	private Item removeInventoryItem(Item item) {
		for(int i = 0; i < inventory.size(); i++){
			if (inventory.get(i).equals(item)){
				return inventory.remove(i);
			}
		}
		return null;
	}
	
	//finds an item with a given name in the inventory
	public int getInventoryIndex(String name){
		for(int i = 0; i < inventory.size(); i++){
			if (inventory.get(i).getName().equals(name)){
				return i;
			}
		}
		return -1;
	}
	
	
	public Item getInventoryItem(int index){
		return inventory.get(index);
		
	}
	
	public ArrayList<Item> getInventory(){
		return inventory;
	}
	
	// returns if enemy is dead
	public boolean attackMelee(Enemy enemy, Melee weapon){
		return enemy.setDamage(weapon.getDamage());
	}
	
	// returns if enemy is dead
		public boolean attackRanged(Enemy enemy, Ranged weapon){
			return enemy.setDamage(weapon.getDamage());
		}

	
}
