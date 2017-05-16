package com.bayviewglen.zork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class Game - the main class of the "Zork" game. meme Author: Michael Kolling
 * Version: 1.1 Date: March 2000
 * 
 * This class is the main class of the "Zork" application. Zork is a very
 * simple, text based adventure game. Users can walk around some scenery. That's
 * all. It should really be extended to make it more interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * routine.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates the commands that
 * the parser returns.
 */

class Game {
	public static String [] keyWeapons = {"machete"};
	//names of items that make them into weapons 
	private Parser parser;
	private Room currentRoom;
	// This is a MASTER object that contains all of the rooms and is easily
	// accessible.
	// The key will be the name of the room -> no spaces (Use all caps and
	// underscore -> Great Room would have a key of GREAT_ROOM
	// In a hashmap keys are case sensitive.
	// masterRoomMap.get("GREAT_ROOM") will return the Room Object that is the
	// Great Room (assuming you have one).
	private HashMap<String, Room> masterRoomMap;
	// initalizes the main character TODO change to modifiy for races and stuff,
	// default stats
	Player player = new Player(100, 100, 100);
	boolean inBattle = false;

	private void initRooms(String fileName) throws Exception {
		masterRoomMap = new HashMap<String, Room>();
		Scanner roomScanner;
		try {
			HashMap<String, HashMap<String, String>> exits = new HashMap<String, HashMap<String, String>>();
			roomScanner = new Scanner(new File(fileName));
			while (roomScanner.hasNext()) {
				Room room = new Room();
				// Read the Name
				String roomName = roomScanner.nextLine();
				room.setRoomName(roomName.split(":")[1].trim().split("-")[0]);
				room.setUnlockType(Integer.parseInt(roomScanner.nextLine().split(":")[1].trim()));
				// Read the Description
				//TODO MAKE IT READ IN ROOM LOCKY TYPE, format of data file is problem 
				String roomDescription = roomScanner.nextLine();
				room.setDescription(roomDescription.split(":")[1].replaceAll("<br>", "\n"));
				//Stores in string array of each than parses after
				String s1 = roomScanner.nextLine();
				String [] roomItemsString = s1.trim().split(":")[1].split(",");
				
				// finds out the type of item and adds it in
				for(int i = 0; i< roomItemsString.length; i++){
					int weight = Integer.parseInt(roomItemsString[i].trim().split("-")[0]);
					String name = roomItemsString[i].trim().split("-")[1];
					if(Arrays.asList(Melee.MELEE).indexOf(name) != -1){
						//TODO change damage system
						room.getInventory().add(new Melee(weight, name, weight*2));
					}else if(Arrays.asList(Ranged.RANGED).indexOf(name) != -1){
						room.getInventory().add(new Ranged(weight, name, 20, 40));
					}
					
				}
				
				

				// Read enemies
				String[] enemies = roomScanner.nextLine().trim().split(":")[1].split(",");
				int counter = 0;
				for (String s : enemies) {
					// TODO this doesnt work right now because of the added
					// boolean parameter in the enemy constructor
					String currentEnemyType = enemies[counter].trim().split("-")[0];
					String inRange = enemies[counter].trim().split("-")[1];
					if (currentEnemyType.equals("grunt"))
						room.addRoomEnemy(new Grunt(100, 30, 40, "grunt", inRange.equals("C")));
					else if (currentEnemyType.equals("miniboss"))
						room.addRoomEnemy(new MiniBoss(100, 0, 0, "miniboss", inRange.equals("C")));
					else if (currentEnemyType.equals("boss"))
						room.addRoomEnemy(new Boss(100, 0, 0, "boss", inRange.equals("C")));
					counter++;
				}

				// Read the Exits
				String roomExits = roomScanner.nextLine();
				// An array of strings in the format E-RoomName
				String[] rooms = roomExits.split(":")[1].split(",");
				HashMap<String, String> temp = new HashMap<String, String>();
				for (String s : rooms) {
					temp.put(s.split("-")[0].trim(), s.split("-")[1]);
				}

				exits.put(roomName.substring(10).trim().toUpperCase().replaceAll(" ", "_"), temp);

				// This puts the room we created (Without the exits in the
				// masterMap)
				masterRoomMap.put(roomName.toUpperCase().substring(10).trim().replaceAll(" ", "_"), room);

				// Now we better set the exits.
			}

			for (String key : masterRoomMap.keySet()) {
				Room roomTemp = masterRoomMap.get(key);
				HashMap<String, String> tempExits = exits.get(key);
				for (String s : tempExits.keySet()) {
					// s = direction
					// value is the room.

					String roomName2 = tempExits.get(s.trim());
					Room exitRoom = masterRoomMap.get(roomName2.toUpperCase().replaceAll(" ", "_"));
					roomTemp.setExit(s.trim().charAt(0), exitRoom);

				}

			}

			roomScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}



	/**
	 * Create the game and initialise its internal map.
	 */
	public Game() {
		try {
			initRooms("data/Rooms.dat");
			currentRoom = masterRoomMap.get("SHIP_ROOM");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parser = new Parser();
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		printWelcome();

		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.

		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing.  Good bye.");
	}

	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome() {
		System.out.println();
		System.out.println("Welcome to Zork!");
		System.out.println("Zork is a new, incredibly boring adventure game.");
		System.out.println("Type 'help' if you need help.");
		System.out.println();
		System.out.println(currentRoom.longDescription());
	}

	/**
	 * Given a command, process (that is: execute) the command. If this command
	 * ends the game, true is returned, otherwise false is returned.
	 */
	private boolean processCommand(Command command) {
		if (command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equals("help")) {
			printHelp();
		} else if (commandWord.equals("attack")) {
			if (inBattle) {
				if (attack(command)) {
					return true;
				}
			} else {
				System.out.println("There is no enemy, I can't believe you've done this!");
			}
		} else if (inBattle) {
			System.out.println("You must attack or you will DIE! What are you doing with your life?");
		} else if (commandWord.equals("go")) {
			goRoom(command);
		} else if (commandWord.equals("quit")) {
			if (command.hasSecondWord())
				System.out.println("Quit what?");
			else
				return true; // signal that we want to quit
		} else if (commandWord.equals("eat")) {
			System.out.println("Do you really think you should be eating at a time like this?");
		} else if (commandWord.equals("break")) {
			if (!command.hasSecondWord())
				System.out.println("What do you want to break");
			else
				System.out.println("The break command don't work yet");
		} else if (commandWord.equals("check")) {
			if (!command.hasSecondWord()) {
				System.out.println("What do you want to check?");
			} else {
				if (command.getSecondWord().equals("room")) {
					System.out.print("The items in the room are: ");
					currentRoom.getInventory().displayAll();
				}else if (command.getSecondWord().equals("inventory")) {
					player.getInventory().displayAll();
				} else {
					System.out.println("I do not understand what you are saying.");
				}
			}

			// TODO also minor changes once an inventory is created to allow the
			// item to be removed from the room and added to the inventory
		} else if (commandWord.equals("grab")) {
			if (!command.hasSecondWord())
				System.out.println("What do you want to grab?");
			else if (!currentRoom.getInventory().isInInventory(command.getSecondWord()))
				System.out.println("That item is not in the room");
			else {
				boolean works = player.pickUp(currentRoom.getInventory().removeItem(command.getSecondWord()));
				if (works)
					System.out.println("You obtianed: " + command.getSecondWord());
				else
					System.out.println("You are already carrying too much");
			}
		}
		//TODO writing the unlock mehthod
				else if (commandWord.equals("unlock")){
					processUnlock(command);
				}

		return false;
	}
	//TODO 
		//processes unlock to see if door is unlockable and if the key works
		
		private void processUnlock(Command command) {
			//testing code that displays all exits in hashmap
			/*for (String i : currentRoom.getExits().keySet()){
				System.out.println(i);
			}*/
			if (!command.hasSecondWord())
				System.out.println("What do you want to unlock?");
			else if (!currentRoom.getExits().keySet().contains(command.getSecondWord().trim()))
				System.out.println("There is no door in that direction");	
			else if (!command.hasThirdWord())
				System.out.println("What do you want to use to unlock it?");
			else if (!player.getInventory().isInInventory(command.getThirdWord())){
				System.out.println("That key is not in your inventory.");
			}
			//ok this looks bad, but just trys to unlock door with key and if it does gets rid of key and unlocks door
			else if (!currentRoom.getExits().get(command.getSecondWord().trim()).unlock( player.getInventory().getKey(command.getThirdWord().trim()))){
				System.out.println("That is not the right type of key");
			}
			else {
				//TODO this doesnt check key type properly
				currentRoom.getExits().get(command.getSecondWord().trim()).unlock(player.getInventory().getKey(command.getThirdWord().trim()));
				( player.getInventory().getKey(command.getThirdWord())).setUsed(true);
				player.getInventory().checkKeyInventoryUsed();
				System.out.println("The door is unlocked!");
			}
			
		}

	// implementations of user commands:

	/**
	 * Print out some help information. Here we print some stupid, cryptic
	 * message and a list of the command words.
	 */
	private void printHelp() {
		System.out.println("You are lost. You are alone. You wander");
		System.out.println("around at Monash Uni, Peninsula Campus.");
		System.out.println();
		System.out.println("Your command words are:");
		parser.showCommands();
	}

	/**
	 * Try to go to one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 */
	private void goRoom(Command command) {
		if (!command.hasSecondWord()) {
			// if there is no second word, we don't know where to go...
			System.out.println("Go where?");
			return;
		}

		String direction = command.getSecondWord();

		// Try to leave current room.
		Room nextRoom = currentRoom.nextRoom(direction);

		if (nextRoom == null)
			System.out.println("There is no door!");
		else if (nextRoom.isLocked())
			System.out.println("The door is locked");
		else {
			currentRoom = nextRoom;
			System.out.println(currentRoom.longDescription());
			// after walking into a new room enemies show up
			showEnemies();
		}
	}

	// show enemies in the room, starting with the first enemy
	private void showEnemies() {
		if (currentRoom.getRoomEnemies().size() == 0) {
			System.out.println("You have vanquished all the enemies here!");
			return;
		}

		Enemy currentEnemy = currentRoom.getRoomEnemies().get(0);
		System.out.println();
		boolean inRange = currentEnemy.getInRange();
		if (inRange) {
			System.out.println("A Grunt has appeared!");
		} else {
			System.out.println("A Grunt is at the other side of the room!");
		}
		System.out.println("You are now engaged in battle!");
		inBattle = true;

	}

	private boolean attack(Command command) {
		if (processPlayerAttack(command)) {
			return false;
		}
		Enemy currEnemy = currentRoom.getRoomEnemies().get(0);

		System.out.println("\nYour health points are " + player.getHealthPoints());
		System.out.println("The Grunt's health points are " + currEnemy.getHealthPoints() + "\n");
		if (currEnemy.getInRange()) {
			boolean isDead = processEnemyAttack();
			if (isDead) {
				System.out.println("You have died");
				return true;
			}
		} else {
			System.out.println("The enemy is running towards you! Quick, Attack!");
			currEnemy.setInRange(true);
		}
		System.out.println("\nYour health points are " + player.getHealthPoints());
		System.out.println("The Grunt's health points are " + currEnemy.getHealthPoints() + "\n");
		return false;

	}

	private boolean processPlayerAttack(Command command) {
		Enemy currentEnemy = currentRoom.getRoomEnemies().get(0);
		boolean badCommand = true;
		while (badCommand) {

			if (!command.hasSecondWord()){
				System.out.println("What do you want to attack?");
			}else if (currentRoom.getEnemyIndex(command.getSecondWord()) == -1){
				System.out.println("That enemy is not in the room");
			}else if (!command.hasThirdWord()){
				System.out.println("What do you want to hit them with?");
			}else if (!player.getInventory().isInInventory(command.getThirdWord())) {
				System.out.println("You do not have that!");

			} else {
				Item chosenWeapon = (Item) player.getInventory().getItem(command.getThirdWord());
				// if chosen weapon is a Melee
				if (chosenWeapon instanceof Melee) {
					Melee currentWeapon = (Melee) chosenWeapon;
					// if enemy is in range
					if (currentEnemy.getInRange()) {
						badCommand = false;
						if (player.attack(currentEnemy, currentWeapon)) {
							processDeadEnemy();
							return true;
						}
					} else  {
						System.out.println("Enemy is out of range for a Melee Weapon");
					}
					
				}
				// if chosen weapon is a Ranged
				else if (chosenWeapon instanceof Ranged) {
					badCommand = false;
					Ranged currentWeapon = (Ranged) chosenWeapon;
					if (player.attack(currentEnemy, currentWeapon)) {
						processDeadEnemy();
						return true;
					}

				} 
				// if chosen weapon is not a weapons
				else {
					System.out.println("You can not attack with that");
				}
				
			}
			//if it is a bad command get a new command
			if(badCommand){
				command = parser.getCommand();
			}
		}
		return false;

	}

	private boolean processEnemyAttack() {
		Enemy currEnemy = currentRoom.getRoomEnemies().get(0);
		int damage = 0;
		if (currEnemy instanceof Grunt) {
			damage = (int) (Math.random() * 10);
		} else if (currEnemy instanceof MiniBoss) {
			damage = (int) (Math.random() * 10 + 20);
		} else if (currEnemy instanceof Boss) {
			damage = (int) (Math.random() * 20 + 30);
		}
		System.out.println("the enemy Attacks!!!");
		return player.setDamage(damage);
	}

	private void processDeadEnemy() {
		System.out.println("You have killed " + currentRoom.removeRoomEnemy(0).getName());
		inBattle = false;
		showEnemies();
	}

}