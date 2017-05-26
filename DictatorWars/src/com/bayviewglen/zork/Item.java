package com.bayviewglen.zork;

public  class Item {
	private boolean dropable;
	private int weight;
	private String name;
	
	public Item(int weight, String name){
		this.weight = weight;
		this.name = name;
		if (name.equals("fist"))
			dropable = false;
		else 
			dropable = true;
	}
	
	public Item(String name){
		this.name = name;
	}
	

	public int getWeight() {
		return weight;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean equals(String descr){
		return (name.equals(descr));
	}

	public boolean isDropable() {
		return dropable;
	}

	public void setDropable(boolean dropable) {
		this.dropable = dropable;
	}

}
