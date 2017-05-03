package com.bayviewglen.zork;

public class Ammo extends Item{
	private int amt;
	private Ranged type;
	
	public Ammo(int weight, String name, int amt, Ranged type) {
		super(weight, name);
		this.amt = amt;
		this.type = type;
	}
	
	public void setAmt(int amt){
		this.amt = amt;
	}
	
	public int getAmt(){
		return amt;
	}
	
	public Ranged getType(){
		return type;
	}
	
	 
}
