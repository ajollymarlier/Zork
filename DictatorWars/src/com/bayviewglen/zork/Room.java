package com.bayviewglen.zork;
/*
 * Class Room - a room in an adventure game.
 *
 * Author:  Michael Kolling
 * Version: 1.1
 * Date:    August 2000
 * 
 * This class is part of Zork. Zork is a simple, text based adventure game.
 *
 * "Room" represents one location in the scenery of the game.  It is 
 * connected to at most four other rooms via exits.  The exits are labelled
 * north, east, south, west.  For each direction, the room stores a reference
 * to the neighbouring room, or null if there is no exit in that direction.
 */

import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

class Room implements Lockable
{
	private String roomName;
    private String description;
    private int unlockType;
    private Inventory inventory = new Inventory();
    
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private HashMap<String, Room> exits;        // stores exits of this room.

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     */
    public HashMap<String, Room> getExits (){
    	return exits;
    }
    
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    public Room() {
		// default constructor.
    	roomName = "DEFAULT ROOM";
    	description = "DEFAULT DESCRIPTION";
    	exits = new HashMap<String, Room>();
	}

    public void setExit(char direction, Room r) throws Exception{
    	String dir= "";
    	switch (direction){
    	case 'E': dir = "east";break;
    	case 'W': dir = "west";break;
    	case 'S': dir = "south";break;
    	case 'N': dir = "north";break;
    	case 'U': dir = "up";break;
    	case 'D': dir = "down";break;
    	default: throw new Exception("Invalid Direction");
    	
    	}
    	
    	exits.put(dir, r);
    }
    
	/**
     * Define the exits of this room.  Every direction either leads to
     * another room or is null (no exit there).
     */
    public void setExits(Room north, Room east, Room south, Room west, Room up, Room down) 
    {
        if(north != null)
            exits.put("north", north);
        if(east != null)
            exits.put("east", east);
        if(south != null)
            exits.put("south", south);
        if(west != null)
            exits.put("west", west);
        if(up != null)
            exits.put("up", up);
        if(up != null)
            exits.put("down", down);
        
    }

    /**
     * Return the description of the room (the one that was defined in the
     * constructor).
     */
    public String shortDescription()
    {
        return "Room: " + roomName +"\n\n" + description;
    }

    /**
     * Return a long description of this room, on the form:
     *     You are in the kitchen.
     *     Exits: north west
     */
    public String longDescription()
    {
    	
        return "Room: " + roomName +"\n\n" + description + "\n" + exitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west ".
     */
    private String exitString()
    {
        String returnString = "Exits:";
		Set keys = exits.keySet();
        for(Iterator iter = keys.iterator(); iter.hasNext(); )
            returnString += " " + iter.next();
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     */
    public Room nextRoom(String direction) 
    {
        return (Room)exits.get(direction);
    }

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public Inventory getInventory(){
		return inventory;
	}
		
	//Add enemies to a room
	public void addRoomEnemy(Enemy enemy){
		enemies.add(enemy);
	}
	
	public ArrayList<Enemy> getRoomEnemies(){
		return enemies;
	}
	
	public Enemy removeRoomEnemy(int index){
		return enemies.remove(index);
	}
	public int getEnemyIndex( String name){
		for (int i = 0; i < enemies.size(); i++){

			if (enemies.get(i).getName().equals(name)){
				return i;
			}
		}
		return -1;
	}
	@Override
	public boolean isLocked() {
		if (unlockType != 0)
			return true;
		return false;
	}

	@Override
	public boolean unlock(Key key) {
		if (key.getType() == unlockType){
			unlockType = 0;
			return true;
		}
		return false;
	}
	
	public int getUnlockType(){
		return unlockType;
	}
	
	public void setUnlockType(int unlockType){
		this.unlockType = unlockType;
	}



	

	
	
	
	
}