package com.bayviewglen.zork;

import java.util.ArrayList;
import java.util.Arrays;

public class Player extends Character {
	private Inventory inventory;
	private int weightCarried = 0;
	private int ATTACK_MODIFIER = 20;
	
	private final int MAX_WEIGHT = 150;
	private final int FISTS_DAMAGE = 5;
	private EquippableItem [] equippedItems = new EquippableItem [5];

	// TODO This max weight will be changed to a variable based on character
	// attributes

	public Player(int healthPoints, int speed, int strength) {
		super(healthPoints, speed, strength);
		inventory = new Inventory();
		//start out with having a fist melee weapon
		//remember for when we add drop command to check fist if droppable
		inventory.addItem(new Melee (0,"fists", FISTS_DAMAGE));
		
	}

	public boolean pickUp(Item item) {

		if (item instanceof Ammo) {
			addAmmo((Ammo) item);
			return true;

		} else if (weightCarried + item.getWeight() <= MAX_WEIGHT) {
			
			inventory.addItem(item);
			weightCarried += item.getWeight();
			//TODO need to remove item from room
			return true;

		} else {
			
			return false;
		}
	}

	// adds number of ammo to int in weapon class
	//TODO the weapon might not change the original weapon
	private void addAmmo(Ammo ammo) {
		ammo.getType().setAmmo(ammo.getType().getAmmo() + ammo.getAmt());
	}
	//bonus melee weapon damage based off of strength
	public boolean attack(Enemy enemy, Melee weapon) {
			System.out.println("You have attacked");
			return enemy.setDamage(weapon.getDamage() * (getStrength() / ATTACK_MODIFIER ));
	}
	
	//bonus ranged weapon damage based off of speed
	public boolean attack(Enemy enemy, Ranged weapon) {
		
		if (weapon.getAmmo() > 0) {
			weapon.setAmmo(weapon.getAmmo() - 1);
			return enemy.setDamage(weapon.getDamage() * (getSpeed() / ATTACK_MODIFIER));
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
		room.getInventory().addItem(inventory.removeItem(name));
		
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
		System.out.println("Your Defense is: " + getDefense());
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
		changeDefense(defenseBoost);
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
		System.out.print("Equipped Items: ");
		String line = "";
		for (int i  = 0; i < equippedItems.length; i++){
			
			if (equippedItems[i] != null){
				if(!line.equals("")){
					line += equippedItems[i].getName() + ", ";
				}else{
					line += equippedItems[i].getName();
				}
			}
			
			
		}
		if(line.equals("")){
			line = "none";
		}
		System.out.println(line);
		
	}
	


}
