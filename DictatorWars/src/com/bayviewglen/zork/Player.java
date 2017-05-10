package com.bayviewglen.zork;

import java.util.ArrayList;

public class Player extends Character {
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private int weightCarried = 0;
	private final int MAX_WEIGHT = super.getStrength() * 10;
	
	// TODO This max weight will be changed to a variable based on character
	// attributes

	public Player(int healthPoints, int speed, int strength) {
		super(healthPoints, speed, strength);
		
		//TODO dunno why this is here
		inventory.add(new Melee(10, "sword", 30));
		inventory.add(new Ranged(10, "bow", 40, 10));

	}

	public boolean pickUp(Item item, Room room) {

		if (item instanceof Ammo) {
			addAmmo((Ammo) item);
			return true;

		} else if (weightCarried + item.getWeight() <= MAX_WEIGHT) {
			inventory.add(item);
			weightCarried += item.getWeight();
			//TODO need to remove item from room
			return true;

		} else {
			System.out.print("You are carrying too much weight to pick that up");
			return false;
		}
	}

	// used to check inventory to see if something has been used or not and
	// remove it TODO IMPLEMENT WITH ITEMS
	// need to run each time item used to check if something has been used
	public void checkInventoryUsed() {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) instanceof ConsumableItem)
				if (((ConsumableItem) inventory.get(i)).getUsed()) {
					inventory.remove(i);
					i--;
				}
		}
	}

	// adds number of ammo to int in weapon class
	//TODO the weapon might not change the original weapon
	private void addAmmo(Ammo ammo) {
		ammo.getType().setAmmo(ammo.getType().getAmmo() + ammo.getAmt());
	}

	// TODO this sort of works... Theoretically
	public void drop(Item item, Room room) {
		weightCarried -= item.getWeight();
		room.addRoomItems(removeInventoryItem(item));
	}

	// TODO this too
	private Item removeInventoryItem(Item item) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).equals(item)) {
				return inventory.remove(i);
			}
		}
		return null;
	}

	// finds an item with a given name in the inventory
	public int getInventoryIndex(String name) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	public Item getInventoryItem(int index) {
		return inventory.get(index);

	}

	public ArrayList<Item> getInventory() {
		return inventory;
	}

	public boolean attack(Enemy enemy, Melee weapon) {
			System.out.println("You have attacked");
			return enemy.setDamage(weapon.getDamage());
	}

	public boolean attack(Enemy enemy, Ranged weapon) {
		
		if (weapon.getAmmo() > 0) {
			weapon.setAmmo(weapon.getAmmo() - 1);
			return enemy.setDamage(weapon.getDamage());
		} else {
			System.out.println("You don't have ammo for that weapon");
			return false;
		}

	}
	public void equip(){
		
	}
	//Todo add resistance 

}
