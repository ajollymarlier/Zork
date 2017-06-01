package com.bayviewglen.zork;

public class Ranged extends Weapon {
	static public final String[] RANGED = {"gun", "bow", "boomerang", "pistol", "dragonslayer_sniper", "laser-gun", "uzi", "galactic_cannon", "rock"};

	private int ammo;

	public Ranged(int weight, String name, int damage, int ammo) {
		super(weight, name, damage);
		this.ammo = ammo;
	}

	public String getName() {
		return super.getName();
	}
	
	public int getAmmo(){
		return ammo;
	}
	
	public void setAmmo(int ammo){
		this.ammo = ammo;
	}
}
