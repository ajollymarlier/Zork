package com.bayviewglen.zork;

public class Ammo extends Item{
	private int amt;
	
	public Ammo(int weight, String name, int amt) {
		super(weight, name);
		this.amt = amt;
	}
	
	public void setAmt(int amt){
		this.amt = amt;
	}
	
	public int getAmt(){
		return amt;
	}
	 
}
