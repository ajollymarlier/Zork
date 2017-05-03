package com.bayviewglen.zork;

import java.util.ArrayList;

public class Player extends Character {
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private ArrayList<Ammo> ammoBag = new ArrayList<Ammo>();
	private int weightCarried = 0;
	// can change max weight, just sorta default TODO
	private final int MAX_WEIGHT = super.getStrength() * 10;
	// test
	// TODO This max weight will be changed to a variable based on character
	// attributes

	public Player(int healthPoints, int speed, int strength) {
		super(healthPoints, speed, strength);
		inventory.add(new Melee(10, "sword", 100));
	}

	public boolean pickUp(Item item, Room room) {
		weightCarried += item.getWeight();

		if (weightCarried <= MAX_WEIGHT && item instanceof Ammo) {
			addAmmo((Ammo) item);
			return true;

		} else if (weightCarried <= MAX_WEIGHT) {
			inventory.add(item);
			return true;

		} else {
			weightCarried -= item.getWeight();
			room.addRoomItems(item);
			return false;
		}
	}

	// adds number of ammo to existing number of ammo in list if already there
	// adds new ammo if not
	private void addAmmo(Ammo item) {
		if (ammoBag.contains(item)) {
			ammoBag.get(ammoBag.indexOf(item)).setAmt(ammoBag.get(ammoBag.indexOf(item)).getAmt() + item.getAmt());
			
		} else {
			ammoBag.add(item);
		}

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
		if (enemy.getInRange()) {
			System.out.println("You have attacked");
			return enemy.setDamage(weapon.getDamage());
		} else {
			System.out.println("Enemy is out of range for a Melee Weapon");
			return false;
		}

	}

	public boolean attack(Enemy enemy, Ranged weapon) {
		//TODO find more efficient way to do this
		boolean hasAmmo = false;
		
		// TODO Rewrote equals method to check String of Ammo type vs Weapon name
		for (Ammo x : ammoBag) {
			if (weapon.equals(x)) {
				x.setAmt(x.getAmt() - 1);
				hasAmmo = true;
			}

			if (x.getAmt() == 0) {
				ammoBag.remove(x);
			}
		}
		
		if (hasAmmo){
			return enemy.setDamage(weapon.getDamage());
		}else{
			System.out.println("You don't have ammo for that weapon");
			return false;
		}
		
	}



}
