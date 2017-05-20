package com.bayviewglen.zork;

public class EffectItem extends ConsumableItem{
	private String type;
	
	//This is a test
	
	public EffectItem(int weight, String name, String type){
		super(weight, name);
		this.type = type;
	}
	
	public void use(){
		//TODO boosts player attribute based on type listed in if statement
	}
	
}
