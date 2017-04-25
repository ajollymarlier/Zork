package com.bayviewglen.zork;

public interface Lockable {
	//version 1.0 
	
	//this interface is used on things that are or can be locked
	//for example doors, chests etc
	
	//checks if object is locked
	public boolean isLocked();
	
	//lock and unlock, returns true if successful
	public boolean unlock();
	public boolean lock();
	 

}
