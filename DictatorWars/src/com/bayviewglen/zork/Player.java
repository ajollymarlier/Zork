package com.bayviewglen.zork;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends Character {
	private Inventory inventory;
	private int weightCarried = 0;
	
	
	private final int MAX_WEIGHT = super.getStrength() * 10;
	private final int FISTS_DAMAGE = 10;
	private EquippableItem [] equippedItems = new EquippableItem [5];

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
		inventory.addItem(new EffectItem(10, "potion", 10,10,10,10));
		inventory.addItem(new EquippableItem(10, "shirt", 10,10,10,10, "chest"));
		inventory.addItem(new EquippableItem(10, "chestplate", 50,10,10,10, "chest"));
		inventory.addItem(new EquippableItem(10, "gloves", 5,5,5,5, "gloves"));
		
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
	// TODO also dont have a fix for if the item doesnt exist. Might be treated in other classes
	public void drop(String name, Room room) {
		if(inventory.getItem(name) instanceof Melee){
			room.getInventory().add((Melee)inventory.getItem(name));
					
		}else if(inventory.getItem(name) instanceof Ranged){
			room.getInventory().add((Ranged)inventory.getItem(name));
			
		}else if(inventory.getItem(name) instanceof Key){
			room.getInventory().add((Key)inventory.getItem(name));
			
		}else if(inventory.getItem(name) instanceof EquippableItem){
			room.getInventory().add((EquippableItem)inventory.getItem(name));
			
		}else if(inventory.getItem(name) instanceof EffectItem){
			room.getInventory().add(((EffectItem)inventory.getItem(name)));
			
		}else{
			room.getInventory().addItem(inventory.getItem(name));
		}
	}

	
	public int getDefense(){
		return 0;
	}




//returns true if a piece of equipment had to switched out for another
	public boolean equip(EquippableItem armour){
		for (int i = 0 ; i < equippedItems.length; i++){
			if (equippedItems[i] instanceof EquippableItem){
				if (equippedItems[i].getType().equals(armour.getType())){
					System.out.println("You have taken off your " + equippedItems[i].getName() + " and . . . ");
					equippedItems[i].setEquipped(false);
					updatePlayerStats(-equippedItems[i].getHealthBoost(), -equippedItems[i].getDefenseBoost(), -equippedItems[i].getSpeedBoost(), -equippedItems[i].getStrengthBoost());
					equippedItems[i] = armour;
					armour.setEquipped(true);
					updatePlayerStats(armour.getHealthBoost(), armour.getDefenseBoost(), armour.getSpeedBoost(), armour.getStrengthBoost());
					return true;
				}
			} else if (equippedItems[i] == null){
				equippedItems[i] = armour;
				armour.setEquipped(true);
				updatePlayerStats(armour.getHealthBoost(), armour.getDefenseBoost(), armour.getSpeedBoost(), armour.getStrengthBoost());
				return false;
			}
			
			
		}
		
		
		return false;
		
	}
	
	public void unequip (String armour){
		for (int i = 0 ; i < equippedItems.length; i++){
			if (equippedItems[i] instanceof EquippableItem){
				if (equippedItems[i].getName().equals(armour)){
					updatePlayerStats(-equippedItems[i].getHealthBoost(), -equippedItems[i].getDefenseBoost(), -equippedItems[i].getSpeedBoost(), -equippedItems[i].getStrengthBoost());
					equippedItems[i].setEquipped(false);
					equippedItems[i] = null;
				}
			}
		}
	}
	public void displayStats(){
		System.out.println("Your Health is: " + getHealthPoints());
		System.out.println("Your Strength is: " + getStrength());
		System.out.println("Your Speed is: " + getSpeed());
	}
	
	public void use(EffectItem item){
		updatePlayerStats(item.getHealthBoost(), item.getDefenseBoost(), item.getSpeedBoost(), item.getStrengthBoost());
		inventory.removeItem(item.getName());
		displayStats();
	}
	
	public void updatePlayerStats(int healthBoost, int defenseBoost, int speedBoost, int strengthBoost) {
		changeHealthPoints(healthBoost);
		changeSpeed(speedBoost);
		changeStrength(strengthBoost);
		//defense boost
		
	}
	
	public EquippableItem [] getEquippedItems (){
		return equippedItems;
	}
	
	public boolean typeAlreadyEquipped(String type){
		for (int i  = 0; i < equippedItems.length; i++){
			if (equippedItems[i] != null)
			if (equippedItems[i].getType().equals(type))
				return true;
		}
		return false;
		
	}

	public void displayInventory() {
		System.out.print("Equipped Items are: ");
		String line = "";
		for (int i  = 0; i < equippedItems.length; i++){
			if (equippedItems[i] != null){
					line += equippedItems[i].getName() + ", ";
			}
		}
		System.out.println(line.substring(0, line.lastIndexOf(",")) + ".");
		
	}
	


}
