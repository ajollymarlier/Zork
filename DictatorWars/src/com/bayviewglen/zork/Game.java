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

	// TODO all worlds must start in the ship room
	private Parser parser;
	private Room currentRoom;
	private boolean worldThree = false;
	final private String WORLD_THREE_UNLOCK = "End Room One";
	final private String END_ROOM = "End Room";
	String[] enemyDialogue;
	// This is a MASTER object that contains all of the rooms and is easily
	// accessible.
	// The key will be the name of the room -> no spaces (Use all caps and
	// underscore -> Great Room would have a key of GREAT_ROOM
	// In a hashmap keys are case sensitive.
	// masterRoomMap.get("GREAT_ROOM") will return the Room Object that is the
	// Great Room (assuming you have one).
	private ArrayList<HashMap<String, Room>> worlds = new ArrayList<HashMap<String, Room>>();
	private String currentWorld;
	private ArrayList<String> worldNames = new ArrayList<String>();
	// initalizes the main character TODO change to modifiy for races and stuff,
	// default stats
	Player player;
	boolean inBattle = false;

	// TODO planet names must be all lowercase
	// construct the game
	public Game() {
		// initialize player
		player = new Player(100, 20, 20);
		// starts you with key to get start gear
		player.getInventory().add(new Key("drop_key", 5));
		try {
			// Loads world 1
			initRooms("data/WorldOne.dat", 0);
			worldNames.add("dunia");


			// loads world 3
			initRooms("data/WorldThree.dat", 1);
			worldNames.add("yanma");

			// loads enemy dialogue
			initEnemyDialogue("data/EnemyDialogue.dat");

			// Starts player in the first room in the first world TODO
			currentRoom = worlds.get(0).get("SHIP_ROOM");
			currentWorld = worldNames.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parser = new Parser();
	}

	private void initRooms(String fileName, int world) throws Exception {
		worlds.add(new HashMap<String, Room>());
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
				String roomDescription = roomScanner.nextLine();
				room.setDescription(roomDescription.split(":")[1].replaceAll("<br>", "\n"));
				// Stores in string array of each than parses after
				String[] roomItemsString = roomScanner.nextLine().trim().split(":")[1].split(",");
				// finds out the type of item and adds it in
				if (!roomItemsString[0].trim().equals("none")) {
					itemMaker(roomItemsString, room);
				}
				// Read enemies
				String[] enemies = roomScanner.nextLine().trim().split(":")[1].split(",");
				int counter = 0;
				if (!enemies[0].trim().equals("none")) {
					for (String s : enemies) {
						String currentEnemyType = enemies[counter].trim().split("-")[0];
						String enemyName = enemies[counter].trim().split("-")[1];
						String inRange = enemies[counter].trim().split("-")[2];
						int healthPoints = Integer.parseInt(enemies[counter].trim().split("-")[3]);
						int speed = Integer.parseInt(enemies[counter].trim().split("-")[4]);
						int strength = Integer.parseInt(enemies[counter].trim().split("-")[5]);
						int dialogueNum = Integer.parseInt(enemies[counter].trim().split("-")[6]);
						if (currentEnemyType.equals("G")) {
							room.addRoomEnemy(new Grunt(healthPoints, speed, strength, dialogueNum, enemyName,
									inRange.equals("C")));
						} else if (currentEnemyType.equals("M")) {
							room.addRoomEnemy(new MiniBoss(healthPoints, speed, strength, dialogueNum, enemyName,
									inRange.equals("C")));
						} else if (currentEnemyType.equals("B")) {
							room.addRoomEnemy(new Boss(healthPoints, speed, strength, dialogueNum, enemyName,
									inRange.equals("C")));
						}
						counter++;
					}
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
				worlds.get(world).put(roomName.toUpperCase().substring(10).trim().replaceAll(" ", "_"), room);
			}

			for (String key : worlds.get(world).keySet()) {
				Room roomTemp = worlds.get(world).get(key);
				HashMap<String, String> tempExits = exits.get(key);
				for (String s : tempExits.keySet()) {
					// s = direction
					// value is the room.
					String roomName2 = tempExits.get(s.trim());
					Room exitRoom = worlds.get(world).get(roomName2.toUpperCase().replaceAll(" ", "_"));
					roomTemp.setExit(s.trim().charAt(0), exitRoom);
				}
			}
			roomScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Creates items and puts it into the correct room inventory
	public void itemMaker(String[] line, Room room) {
		for (int i = 0; i < line.length; i++) {
			String itemType = line[i].trim().split("-")[0];
			String name = line[i].trim().split("-")[1];
			if (itemType.equals("C")) {
				int lockType = Integer.parseInt(line[i].trim().split("-")[2]);
				String[] chestItems = line[i].trim().split("\\|")[1].trim().split(";");
				Chest chest = new Chest(name, lockType);
				room.getInventory().add(chest);
				if (!chestItems[0].trim().equals("none")) {
					chestMaker(chestItems, chest);
				}
			} else if (itemType.equals("K")) {
				int type = Integer.parseInt(line[i].trim().split("-")[2]);
				room.getInventory().add(new Key(name, type));
			} else {
				int weight = Integer.parseInt(line[i].trim().split("-")[2]);
				if (itemType.equals("M") || itemType.equals("R")) {
					int damage = Integer.parseInt(line[i].trim().split("-")[3]);
					if (itemType.equals("M")) {
						room.getInventory().add(new Melee(weight, name, damage));
					} else {
						int ammo = Integer.parseInt(line[i].trim().split("-")[4]);
						room.getInventory().add(new Ranged(weight, name, damage, ammo));
					}
				} else if (itemType.equals("E") || itemType.equals("Q")) {
					int healthBoost = Integer.parseInt(line[i].trim().split("-")[3]);
					int defenseBoost = Integer.parseInt(line[i].trim().split("-")[4]);
					int speedBoost = Integer.parseInt(line[i].trim().split("-")[5]);
					int strengthBoost = Integer.parseInt(line[i].trim().split("-")[6]);
					if (itemType.equals("E")) {
						room.getInventory().add(
								new EffectItem(weight, name, healthBoost, defenseBoost, speedBoost, strengthBoost));
					} else {
						String type = line[i].trim().split("-")[7];
						room.getInventory().add(new EquippableItem(weight, name, healthBoost, defenseBoost, speedBoost,
								strengthBoost, type));
					}
				}
			}
		}
	}

	// Creates items and puts it into the correct chest inventory
	public void chestMaker(String[] line, Chest chest) {
		for (int i = 0; i < line.length; i++) {
			String itemType = line[i].trim().split("-")[0];
			String name = line[i].trim().split("-")[1];
			if (itemType.equals("K")) {
				int type = Integer.parseInt(line[i].trim().split("-")[2]);
				chest.getInventory().add(new Key(name, type));
			} else {
				int weight = Integer.parseInt(line[i].trim().split("-")[2]);
				if (itemType.equals("M") || itemType.equals("R")) {
					int damage = Integer.parseInt(line[i].trim().split("-")[3]);
					if (itemType.equals("M")) {
						chest.getInventory().add(new Melee(weight, name, damage));
					} else {
						int ammo = Integer.parseInt(line[i].trim().split("-")[4]);
						chest.getInventory().add(new Ranged(weight, name, damage, ammo));
					}
				} else if (itemType.equals("E") || itemType.equals("Q")) {
					int healthBoost = Integer.parseInt(line[i].trim().split("-")[3]);
					int defenseBoost = Integer.parseInt(line[i].trim().split("-")[4]);
					int speedBoost = Integer.parseInt(line[i].trim().split("-")[5]);
					int strengthBoost = Integer.parseInt(line[i].trim().split("-")[6]);
					if (itemType.equals("E")) {
						chest.getInventory().add(
								new EffectItem(weight, name, healthBoost, defenseBoost, speedBoost, strengthBoost));
					} else {
						String type = line[i].trim().split("-")[7];
						chest.getInventory().add(new EquippableItem(weight, name, healthBoost, defenseBoost, speedBoost,
								strengthBoost, type));

					}
				}
			}
		}
	}

	// loads up the enemy dialogue into an array
	private void initEnemyDialogue(String fileName) {
		Scanner dialogueScanner;
		try {
			dialogueScanner = new Scanner(new File(fileName));
			int numLines = Integer.parseInt(dialogueScanner.nextLine());
			enemyDialogue = new String[numLines];
			for (int i = 0; i < numLines; i++) {
				enemyDialogue[i] = dialogueScanner.nextLine();
			}
			dialogueScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Main play routine. Loops until end of play.
	 * 
	 * @throws InterruptedException
	 */
	public void play() throws InterruptedException {
		printWelcome();
		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.
		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);

			if (currentRoom.getRoomName().equals(END_ROOM)) {
				finished = true;
				System.out.println("You Win!");
			}
		}
		System.out.println("Thank you for playing. Good bye.");
		Thread.sleep(2000);
	}

	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome() {
		System.out.println();
		System.out.println("Welcome to Centaurus A");
		System.out.println("Type 'help' if you need help.");
		System.out.println();
		for (int i = 0; i < 4; i++) {
			System.out.println("." + "\n");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		printIntro();
		System.out.println(currentRoom.longDescription());
	}

	private void printIntro() {
		System.out.println(
				"As you exit the ship you are hit with a blast of warm and fresh air, it almost reminds you of a place you haven’t been in a\nlong time, home. Being a bounty hunter, you have been all around the galaxy, you have made friends and you have made enemies\nbut none of that matters now. This is your last hunt, but also your biggest.");
		System.out.println();
		System.out.println();
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
		switch (commandWord) {

		case "help":
			if (inBattle) {
				printHelp(true);
				break;
			} else
				printHelp();
			break;
		case "check":
			check(command);
			break;
		case "use":
			use(command);
			break;
		case "equip":
			equip(command);
			break;
		case "unequip":
			unequip(command);
			break;
		case "attack":
			return attack(command);
		default:
			if (!inBattle) {
				switch (commandWord) {
				case "go":
					goRoom(command);
					break;
				case "walk":
					goRoom(command);
					break;
				case "quit":
					return true;
				case "grab":
					grab(command);
					break;
				case "drop":
					drop(command);
					break;
				case "unlock":
					unlock(command);
					break;
				case "teleport":
					teleport(command);
					break;
				}
			} else {
				System.out.println("You must attack or you will DIE! What are you doing with your life?");
			}
			break;
		}
		return false;
	}

	private void teleport(Command command) {
		boolean possible = true;
		if (!command.hasThirdWord()) {
			System.out.println("Where do you want to teleport to?");
			System.out.print("Worlds: ");

			for (int i = 0; i < worlds.size(); i++) {
				if (!worldNames.get(i).equals(currentWorld))
					System.out.print(worldNames.get(i));
			}
			System.out.println("");
		} else if (command.getThirdWord().equals(currentWorld)) {
			System.out.println("You are already in that world");
		} else {
			if (!worldThree && command.getThirdWord().equals("yanma")) {
				System.out.println("How did you know about that place...");
				System.out.println("Either way you can't go there yet");
				possible = false;
			}

			try {
				if (possible) {
					currentWorld = command.getThirdWord();
					currentRoom = worlds.get(worldNames.indexOf(currentWorld)).get("SHIP_ROOM");
					System.out.println("You teleported to " + currentWorld.toUpperCase());
					System.out.println(currentRoom.longDescription());
				}
			} catch (Exception e) {
				System.out.println("That place does not exist... fool...");
			}

		}
	}
	
	private void unequip(Command command) {
		if (!command.hasSecondWord())
			System.out.println("You must include what you want to unequip");
		else if (!player.getInventory().isInInventory(command.getSecondWord()))
			System.out.println("That item is not in your inventory.");
		else if (!(player.getInventory().getItem(command.getSecondWord()) instanceof EquippableItem))
			System.out.println("That is not something you can unequip.");
		else if (!((EquippableItem) (player.getInventory().getItem(command.getSecondWord()))).getEquipped()) {
			System.out.println("That item is already unequipped.");
		} else {
			player.unequip(command.getSecondWord());
			System.out.println("You have unequipped " + command.getSecondWord());
		}
	}

	private void equip(Command command) {
		if (!command.hasSecondWord())
			System.out.println("You must include what you want to equip");
		else if (!player.getInventory().isInInventory(command.getSecondWord()))
			System.out.println("That item is not in your inventory.");
		else if (!(player.getInventory().getItem(command.getSecondWord()) instanceof EquippableItem))
			System.out.println("That is not something you can equip.");
		else if (((EquippableItem) (player.getInventory().getItem(command.getSecondWord()))).getEquipped()) {
			System.out.println("That item is already equipped.");
		} else {
			player.equip(((EquippableItem) (player.getInventory().getItem(command.getSecondWord()))));
			System.out.println("You have equipped " + command.getSecondWord());
		}
	}

	private void use(Command command) {
		if (!command.hasSecondWord())
			System.out.println("You must include what you want to use");
		else if (!player.getInventory().isInInventory(command.getSecondWord()))
			System.out.println("You are not carrying that item");
		else {
			EffectItem chosenItem = player.getInventory().getEffectItem(command.getSecondWord());
			if (chosenItem == null) {
				System.out.println("You can't use this!");
			} else {
				player.use(chosenItem);
			}
		}
	}

	private void drop(Command command) {
		if (!command.hasSecondWord())
			System.out.println("You must include what you want to drop");
		else if (!player.getInventory().isInInventory(command.getSecondWord().trim()))
			System.out.println("You are not carrying that item");
		else {

			if (command.getSecondWord().equals("fists"))
				System.out.println("That is not physically possible");
			if (command.getSecondWord().equals("rock"))
				System.out.println("There are rocks to throw everywhere, can't drop this item");
			else {
				if (player.getInventory().getItem(command.getSecondWord()) instanceof EquippableItem)
					if (((EquippableItem) (player.getInventory().getItem(command.getSecondWord()))).getEquipped())
						player.unequip(command.getSecondWord());
				player.drop(command.getSecondWord(), currentRoom);
				System.out.println("You dropped: " + command.getSecondWord());
			}

		}
	}

	private void grab(Command command) {
		if (!command.hasSecondWord())
			System.out.println("You must include what you want to grab");
		else if (command.hasThirdWord() && Arrays.asList(Chest.chestNames).indexOf(command.getThirdWord()) != -1) {
			if (!currentRoom.getInventory().isInInventory(command.getThirdWord()))
				System.out.println("There is not a " + command.getThirdWord() + " in the room.");
			else if (((Chest) (currentRoom.getInventory().getItem(command.getThirdWord()))).isLocked())
				System.out.println("The " + command.getThirdWord() + " is locked closed.");
			else {
				if (!((Chest) (currentRoom.getInventory().getItem(command.getThirdWord()))).getInventory()
						.isInInventory(command.getSecondWord()))
					System.out.println("That item is not in the " + command.getThirdWord());
				
				else if(command.getSecondWord().equals("everything")){
					//TODO add pickup everything from chest
					
				} else {
					boolean works = player.pickUp(((Chest) (currentRoom.getInventory().getItem(command.getThirdWord())))
							.getInventory().removeItem(command.getSecondWord()));
					if (works)
						System.out.println("You obtained: " + command.getSecondWord());
					else
						System.out.println("You are already carrying too much");
				}
			}
		} else if (!currentRoom.getInventory().isInInventory(command.getSecondWord()))
			System.out.println("That item is not in the room");
		else if (Arrays.asList(Chest.chestNames).indexOf(command.getSecondWord()) != -1) {
			System.out.println(
					"You can't pick up " + command.getSecondWord() + "s. They are too heavy for your noodle arms.");

		} else {
			boolean works = player.pickUp(currentRoom.getInventory().removeItem(command.getSecondWord()));
			if (works)
				System.out.println("You obtained: " + command.getSecondWord());
			else
				System.out.println("You are already carrying too much");
		}
	}

	private void check(Command command) {
		if (!command.hasSecondWord()) {
			System.out.println("You must include what you want to check");
		} else {
			// gives a brief description of items in a room
			if (command.getSecondWord().equals("room")) {
				System.out.print("Items in room: ");
				currentRoom.getInventory().displayAll();
			}
			// checks the items in a chest
			else if (Arrays.asList(Chest.chestNames).indexOf(command.getSecondWord()) != -1) {
				Chest chest = (Chest) (currentRoom.getInventory().getItem(command.getSecondWord()));
				if (chest == null) {
					System.out.println("The " + command.getSecondWord() + " is not in the room!");
				} else if (!(chest instanceof Chest)) {
					System.out.println("That is not a " + command.getSecondWord());
				} else if (chest.isLocked()) {
					System.out.println("The " + command.getSecondWord() + " is locked. You cannot see inside it.");
				} else {
					System.out.print("Items in " + command.getSecondWord() + ": ");
					chest.getInventory().displayAll();
				}
			}
			// displays player stats or weapon stats
			else if (command.getSecondWord().equals("stats")) {
				if (!command.hasThirdWord())
					player.displayStats();
				else if (!player.getInventory().isInInventory(command.getThirdWord()))
					System.out.println("That is not in your inventory");
				else if (!(player.getInventory().getItem(command.getThirdWord()) instanceof Weapon))
					System.out.println("That is not a weapon you can check.");
				else
					System.out.println("The " + command.getThirdWord() + " has: "
							+ ((Weapon) (player.getInventory().getItem(command.getThirdWord()))).getDamage()
							+ " attack");

			}
			// displays player inventory
			else if (command.getSecondWord().equals("inventory")) {
				System.out.print("Items in your inventory:");
				player.getInventory().displayAll();
			}

			// displays player's clothing
			else if (command.getSecondWord().equals("body")) {
				// System.out.print("Items in your inventory:");
				player.displayInventory();
			}
			// displays amount of ammo of a ranged weapon
			else if (command.getSecondWord().equals("ammo")) {
				if (!command.hasThirdWord()) {
					System.out.println("You must include what Ranged weapon you would like to check the ammo on");
				} else if ((player.getInventory().getItem(command.getThirdWord())) == null) {
					System.out.println("You do not have that weapon!");
				} else if (!(player.getInventory().getItem(command.getThirdWord()) instanceof Ranged)) {
					System.out.println("That weapon does not have ammo!");
				} else {
					System.out.println("That weapon has "
							+ ((Ranged) (player.getInventory().getItem(command.getThirdWord()))).getAmmo() + " ammo");
				}
			} else {
				System.out.println("I do not understand what you are saying.");
			}
		}
	}

	// allow
	private void unlock(Command command) {
		// testing code that displays all exits in hashmap
		/*
		 * for (String i : currentRoom.getExits().keySet()){
		 * System.out.println(i); }
		 */
		if (!command.hasSecondWord())
			System.out.println("You must include what you want to 'unlock'");
		else if (Arrays.asList(Chest.chestNames).indexOf(command.getSecondWord()) == -1) {
			if (!currentRoom.getExits().keySet().contains(command.getSecondWord().trim()))
				System.out.println(command.getSecondWord() + " does not exist");
			else if (!command.hasThirdWord())
				System.out.println("You must include what you want to unlock " + command.getSecondWord() + " with");
			else if (!player.getInventory().isInInventory(command.getThirdWord())) {
				System.out.println("That key is not in your inventory.");
			} else if (!currentRoom.getExits().get(command.getSecondWord().trim())
					.unlock(player.getInventory().getKey(command.getThirdWord().trim()))) {
				System.out.println("That is not the right type of key");
			} else {
				currentRoom.getExits().get(command.getSecondWord().trim())
						.unlock(player.getInventory().getKey(command.getThirdWord().trim()));
				(player.getInventory().getKey(command.getThirdWord())).setUsed(true);
				player.getInventory().checkKeyInventoryUsed();
				System.out.println("The way is clear!");
			}
		} else if (Arrays.asList(Chest.chestNames).indexOf(command.getSecondWord()) != -1) {
			if (!currentRoom.getInventory().isInInventory(command.getSecondWord()))
				System.out.println("There is no " + command.getSecondWord() + " in the area");
			else if (!command.hasThirdWord())
				System.out.println("You must include what you want to unlock " + command.getSecondWord() + " with");
			else {
				Key chosenKey = player.getInventory().getKey(command.getThirdWord());
				if (chosenKey == null) {
					System.out.println("You do not have that key!");
				} else if (((Chest) (currentRoom.getInventory().getItem(command.getSecondWord()))).unlock(chosenKey)) {
					System.out.println("The " + command.getSecondWord() + " is unlocked!");
					System.out.print("Items in chest: ");
					((Chest) currentRoom.getInventory().getItem(command.getSecondWord())).getInventory().displayAll();
				} else {
					System.out.println("That is not the right type of key.");
				}
			}
		}

	}

	/**
	 * Print out some help information. Here we print some stupid, cryptic
	 * message and a list of the command words.
	 */
	private void printHelp() {
		System.out.println("You look around again,");
		System.out.println(currentRoom.longDescription());
		System.out.println();
		System.out.println("You look at yourself and think of what you are able to do. You can: ");
		parser.showCommands();
	}

	private void printHelp(boolean comabt) {
		System.out.println("You are in combat! You must fight to survive. You quickly look around the area:");
		System.out.println(currentRoom.longDescription());
		System.out.println();
		System.out.println("Quickly think of all the things you can do quickly: ");
		parser.showCombatCommands();
	}

	/**
	 * Try to go to one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 */
	private void goRoom(Command command) {
		if (!command.hasSecondWord()) {
			// if there is no second word, we don't know where to go...
			System.out.println("You must include a direction");
			return;
		}
		String dirWanted = command.getSecondWord();
		String direction = command.getSecondWord();

		// allows player to write a letter instead of direction
		if (command.getSecondWord().equals("n")) {
			direction = "north";
		} else if (command.getSecondWord().equals("s")) {
			direction = "south";
		} else if (command.getSecondWord().equals("w")) {
			direction = "west";
		} else if (command.getSecondWord().equals("e")) {
			direction = "east";
		}

		// Try to leave current room.
		Room nextRoom = currentRoom.nextRoom(direction);

		if (nextRoom == null)
			System.out.println("There is no door!");
		else if (nextRoom.isLocked())
			System.out.println("The way is obstructed");
		else {
			currentRoom = nextRoom;
			System.out.println(currentRoom.longDescription());
			// after walking into a new room enemies show up
			checkTeleport();
			checkItemsAdd();
			showEnemies();
		}
	}

	private void checkItemsAdd() {
		if (currentRoom.getRoomName().equals("Village")) {
			System.out.println("\n You obtained the vaporizer");
			player.getInventory().add(new Key("vaporizer", 1));
		}
		
	}

	private void checkTeleport() {
		if (currentRoom.getRoomName().equals(WORLD_THREE_UNLOCK)) {
			System.out.println("You can now teleport to Yanma");
			worldThree = true;
		}

	}

	// show enemies in the room, starting with the first enemy
	private void showEnemies() {
		if (currentRoom.getRoomEnemies().size() == 0) {
			return;
		}
		Enemy currentEnemy = currentRoom.getRoomEnemies().get(0);
		System.out.println();
		boolean inRange = currentEnemy.getInRange();
		if (inRange) {
			System.out.println("A vile creature named " + currentEnemy.getName() + " has appeared!");
		} else {
			System.out.println("A vile creature named " + currentEnemy.getName() + " is a little bit away");
		}
		System.out
				.println(currentEnemy.getName() + " screams, \"" + enemyDialogue[currentEnemy.getDialogueNum()] + "\"");
		System.out.println("You are now engaged in battle!");
		inBattle = true;

	}

	// controls a round of battle
	private boolean attack(Command command) {
		if (!inBattle) {
			System.out.println("There is no enemy, I can't believe you've done this!");
		} else {
			if (processPlayerAttack(command)) {
				return false;
			}
			Enemy currEnemy = currentRoom.getRoomEnemies().get(0);

			System.out.println("\nYour health points are " + player.getHealthPoints());
			System.out.println(currEnemy.getName() + "'s health points are " + currEnemy.getHealthPoints() + "\n");
			if (currEnemy.getInRange()) {
				boolean isDead = processEnemyAttack();
				if (isDead) {
					System.out.println("You have died");
					return true;
				}
			} else {
				System.out.println(currEnemy.getName() + " is running towards you! Quick, Attack!");
				currEnemy.setInRange(true);
			}
			System.out.println("\nYour health points are " + player.getHealthPoints());
			System.out.println(currEnemy.getName() + "'s health points are " + currEnemy.getHealthPoints() + "\n");
		}
		return false;
	}

	private boolean processPlayerAttack(Command command) {
		Enemy currentEnemy = currentRoom.getRoomEnemies().get(0);
		boolean badCommand = true;
		while (badCommand) {

			if (!command.hasSecondWord()) {
				System.out.println("You must include what you want to attack");
			} else if (!currentEnemy.getName().toLowerCase().equals((command.getSecondWord()))) {
				System.out.println("That enemy is not in the area");
			} else if (!command.hasThirdWord()) {
				System.out.println("You must include what you want to attack with");
			} else if (!player.getInventory().isInInventory(command.getThirdWord())) {
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
					} else {
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
			// if it is a bad command get a new command
			if (badCommand) {
				command = parser.getCommand();
			}
		}
		return false;

	}

	private boolean processEnemyAttack() {
		Enemy currEnemy = currentRoom.getRoomEnemies().get(0);
		boolean playerDead = currEnemy.attack(player);

		System.out.println(currEnemy.getName() + " attacks!!!");
		return playerDead;
	}

	private void processDeadEnemy() {
		System.out.println("You have killed " + currentRoom.removeRoomEnemy(0).getName());
		if (currentRoom.getRoomEnemies().size() == 0) {
			System.out.println("You have vanquished all enemies here");
		}
		inBattle = false;
		showEnemies();
	}

}