package com.bayviewglen.zork;

public  class Item {
	private int weight;
	private String name;
	
	public Item(int weight, String name){
		this.weight = weight;
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}
	
	public String getName(){
		return name;
	}

}
