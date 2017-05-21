package com.bayviewglen.zork;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends Character {
	private Inventory inventory;
	private int weightCarried = 0;
	private int health;
	private int defense;
	private int speed;
	private int strength;
	//TODO just filler base values right now
	private final int baseHealth = 100;
	private final int baseDefense = 5;
	private final int baseSpeed = 5;
	private final int baseStrength = 5;
	private final int MAX_WEIGHT = super.getStrength() * 10;
	private final int FISTS_DAMAGE = 10;
	private final EquippableItem [] equippedItems = new EquippableItem [5];

	// TODO This max weight will be changed to a variable based on character
	// attributes

	public Player(int healthPoints, int speed, int strength) {
		super(healthPoints, speed, strength);
		inventory = new Inventory();
		//start out with having a fist melee weapon
		//remember for when we add drop command to check fist if droppable
		inventory.addItem(new Melee (0,"fists", FISTS_DAMAGE));
		//innitialized with stuff to test out game
		inventory.addItem(new Melee(10, "sword", 30));
		inventory.addItem(new Ranged(10, "bow", 40, 10));
		inventory.addItem(new Key("test" , 3));
		inventory.addItem(new Key("test2" , 2));
		inventory.displayAll();

	}

	public boolean pickUp(Item item) {

		if (item instanceof Ammo) {
			addAmmo((Ammo) item);
			return true;

		} else if (weightCarried + item.getWeight() <= MAX_WEIGHT) {
			if (Arrays.asList(Game.keyWeapons).contains(item.getName())){
				int keyType = -1;
				for (int i  = 0; i < Game.keyWeapons.length; i++)
					if (Game.keyWeapons[i].equals(item.getName())){
						keyType = i + 5;
						break;
					}
				inventory.addItem(new Key(item.getName(), keyType));
				
			}
			inventory.addItem(item);
			weightCarried += item.getWeight();
			//TODO need to remove item from room
			return true;

		} else {
			System.out.print("You are carrying too much weight to pick that up");
			return false;
		}
	}

	// adds number of ammo to int in weapon class
	//TODO the weapon might not change the original weapon
	private void addAmmo(Ammo ammo) {
		ammo.getType().setAmmo(ammo.getType().getAmmo() + ammo.getAmt());
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
	
	public Inventory getInventory(){
		return inventory;
	}
	// TODO this sort of works... Theoretically
	public void drop(String name, Room room) {
		Item droppedItem = inventory.removeItem(name);
		weightCarried -= droppedItem.getWeight();
		room.getInventory().addItem(droppedItem);
	}

	
	public int getDefense(){
		return 0;
	}




//once we are able to equip items need to make sure this stuff works
	public void equip(EquippableItem armour){
		for (int i = 0 ; i < equippedItems.length; i++){
			if (equippedItems[i] instanceof EquippableItem){
				if (equippedItems[i].getType().equals(armour.getType())){
					//TODO ask if it is alright to change gear
					equippedItems[i].setEquipped(false);
					equippedItems[i] = armour;
					armour.setEquipped(true);
				}
			} else if (equippedItems[i] == null){
				equippedItems[i] = armour;
				armour.setEquipped(true);
			}
			
		}
		//TODO double check that the update works
		updatePlayerStats(armour.getHealthBoost(), armour.getDefenseBoost(), armour.getSpeedBoost(), armour.getStrengthBoost());
		
	}

	public void updatePlayerStats(int healthBoost, int defenseBoost, int speedBoost, int strengthBoost) {
		health = baseHealth + healthBoost;
		defense = baseDefense + defenseBoost;
		speed = baseSpeed + speedBoost;
		strength = baseStrength + strengthBoost;
		
	}
	
	
	


}
