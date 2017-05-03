package com.bayviewglen.zork;

public class Ranged extends Weapon {

	public Ranged(int weight, String name, int damage) {
		super(weight, name, damage);
	}

	public boolean equals(Ammo ammo) {
		//Checks the String of the ammo type and sees if it equals the String of weapon name
		if (ammo.getType().getName().equals(super.getName()))
			return true;

		return false;
	}

	public String getName() {
		return super.getName();
	}
}
